package com.sparta.jpahibernate.restcontrollers;

import com.sparta.jpahibernate.dao.concretes.EmployeeDAOImpl;
import com.sparta.jpahibernate.dao.concretes.UserDAOImpl;
import com.sparta.jpahibernate.dto.EmployeeDTO;
import com.sparta.jpahibernate.dto.EmpsByDeptsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    @Autowired
    private EmployeeDAOImpl empDao;

    @Autowired
    private UserDAOImpl userDao;

    @PostMapping("/add")
    public ResponseEntity<String> addNewEmployee(@RequestBody EmployeeDTO newEmployee, @RequestParam String apiKey){
        if (userDao.isAdmin(apiKey) || userDao.isUpdate(apiKey)){
            Optional<EmployeeDTO> original = empDao.findById(newEmployee.getId());
            HttpHeaders headers = new HttpHeaders();
            headers.add("content-type", "application/json");
            if (original.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Employee with id: " + newEmployee.getId() + " already exists");
            } else if (Stream.of(newEmployee.getId(), newEmployee.getBirthDate(), newEmployee.getFirstName(),
                            newEmployee.getLastName(), newEmployee.getGender(), newEmployee.getHireDate())
                    .anyMatch(Objects::isNull)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "The employee cannot be null");
            }
            empDao.save(newEmployee);
            return new ResponseEntity<>(newEmployee.toString(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("\"message\":\"User could not be added\"}",
                    HttpStatus.UNAUTHORIZED);
        }

    }

    @PutMapping("/update/create")
    public ResponseEntity<EmployeeDTO> updateEmployeePut(@RequestBody EmployeeDTO newState, @RequestParam String apiKey){
        if (userDao.isAdmin(apiKey) || userDao.isUpdate(apiKey)) {
            Optional<EmployeeDTO> original = empDao.findById(newState.getId());
            if (original.isPresent()) {
                String[] params = new String[]{newState.getId().toString(), newState.getBirthDate().toString(), newState.getFirstName(), newState.getLastName(), newState.getGender(), newState.getHireDate().toString()};
                empDao.update(newState.getId(), params);
                return new ResponseEntity<>(empDao.findById(newState.getId()).get(), HttpStatus.OK);
            } else {
                empDao.save(newState);
                return new ResponseEntity<>(empDao.findById(newState.getId()).get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping("/update")
    public ResponseEntity<EmployeeDTO> updateEmployeePatch(@RequestBody EmployeeDTO newState, @RequestParam String apiKey){
        if (userDao.isAdmin(apiKey) || userDao.isUpdate(apiKey)) {
            String[] params = new String[]{newState.getId().toString(), newState.getBirthDate().toString(), newState.getFirstName(), newState.getLastName(), newState.getGender(), newState.getHireDate().toString()};
            empDao.update(newState.getId(), params);
            System.out.println(empDao.findById(newState.getId()).get());
            return new ResponseEntity<>(empDao.findById(newState.getId()).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public EmployeeDTO findEmployeeById(@PathVariable int id) {
        return empDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No employee with the specified ID could be found"));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> findById(@PathVariable int id) {
//        Optional<EmployeeDTO> employee = empDao.findById(id);
//        ObjectMapper empMap = JsonMapper.builder()
//                .addModule(new JavaTimeModule())
//                .build();
//        HttpHeaders headers = new HttpHeaders();
//        if(employee.isPresent()){
//            EmployeeDTO emp = employee.get();
//            String empString;
//            try {
//                empString = empMap.writeValueAsString(emp);
//            } catch (JsonProcessingException e){
//                throw new RuntimeException(e);
//            }
//            return new ResponseEntity<>(empString, headers, HttpStatus.OK);
//        }
//        return new ResponseEntity<>("{\"message\":\"Employee with id: " + id + " was not found\"}",
//        headers, HttpStatus.NOT_FOUND);
//    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable int id, @RequestParam String apiKey){
        if (userDao.isAdmin(apiKey)) {
            empDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No employee with the specific ID could be found"));
            empDao.deleteById(id);
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteById(@PathVariable int id){
//        Optional <EmployeeDTO> emp = empDao.findById(id);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("content-type", "application/json");
//        if(emp.isPresent()){
//            empDao.deleteById(empDao.findById(id).get().getId());
//            return new ResponseEntity<>("{\"message\":\"Employee " + id+ " successfully deleted\"},", headers, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("{\"message\":\"Employee " + id+ " not found\"},", headers, HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/countNumberOfEmployeesLeftDepartmentByYear")
    public int countNumberOfEmployeesLeftDepartmentByYear(@RequestParam("dep") String dep,
                                                          @RequestParam("year") String year,
                                                          @RequestParam String apiKey){

        System.out.println( "In the year:" + year + "the number of employees left in department:" + dep );
        int res = empDao.countNumberOfEmployeesLeftDepartmentByYear(dep,year);
        System.out.println(res);
        return res;
    }

    @GetMapping("/findByDepartmentAndDate")
    public List<EmpsByDeptsDTO> findByDepartmentAndDate(@RequestParam("dep") String dep,
                                                        @RequestParam("fromDate") LocalDate fromDate,
                                                        @RequestParam("toDate") LocalDate toDate,
                                                        @RequestParam String apiKey){

        return empDao.findByDepartmentAndDate(dep, fromDate, toDate);
    }

    @GetMapping("/findByLastName")
    public List<EmployeeDTO> findByLastName(@RequestParam("lastName") String lastName,
                                            @RequestParam String apiKey){
        return empDao.findByLastName(lastName);
    }
}
