package com.sparta.jpahibernate.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.jpahibernate.dao.concretes.EmployeeDAOImpl;
import com.sparta.jpahibernate.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeDAOImpl empDao;

    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> addNewEmployee(@RequestBody EmployeeDTO newEmployee){
        empDao.save(newEmployee);
        return new ResponseEntity<>(newEmployee, HttpStatus.OK);
    }

    @PutMapping("/update/create")
    public ResponseEntity<EmployeeDTO> updateEmployeePut(@RequestBody EmployeeDTO newState){
        Optional<EmployeeDTO> original = empDao.findById(newState.getId());
        if (original.isPresent()) {
            String[] params = new String[]{newState.getId().toString(), newState.getBirthDate().toString(), newState.getFirstName(), newState.getLastName(), newState.getGender(), newState.getHireDate().toString()};
            empDao.update(newState.getId(), params);
            return new ResponseEntity<>(empDao.findById(newState.getId()).get(), HttpStatus.OK);
        }
        else {
            empDao.save(newState);
            return new ResponseEntity<>(empDao.findById(newState.getId()).get(), HttpStatus.OK);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<EmployeeDTO> updateEmployeePatch(@RequestBody EmployeeDTO newState){
        String[] params = new String[] {newState.getId().toString(), newState.getBirthDate().toString(), newState.getFirstName(), newState.getLastName(), newState.getGender(), newState.getHireDate().toString()};
        empDao.update(newState.getId(), params);
        System.out.println(empDao.findById(newState.getId()).get());
        return new ResponseEntity<>(empDao.findById(newState.getId()).get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public EmployeeDTO findDepartmentById(@PathVariable int id) {
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
    public void deleteById(@PathVariable int id){
        empDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No employee with the specific ID could be found"));
        empDao.deleteById(id);
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
}
