package com.sparta.jpahibernate.controllers;

import com.sparta.jpahibernate.dao.concretes.DepartmentDAOImpl;
import com.sparta.jpahibernate.dao.concretes.UserDAOImpl;
import com.sparta.jpahibernate.dto.DepartmentDTO;
import com.sparta.jpahibernate.dto.EmpsForDeptsDTO;
import com.sparta.jpahibernate.entities.Department;
import com.sparta.jpahibernate.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentDAOImpl deptDao;

    @Autowired
    private UserDAOImpl userDao;


    @GetMapping("/{id}")
    public DepartmentDTO findDepartmentById(@PathVariable String id) {
        return deptDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No department with the specified ID could be found"));
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id, @RequestParam String apiKey){
        if (userDao.isAdmin(apiKey)) {
            deptDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No department with the specific ID could be found"));
            deptDao.deleteById(id);
        }

    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteById(@PathVariable String id) {
//        Optional<DepartmentDTO> deptOpt = deptDao.findById(id);
//        ResponseEntity<String> responseEntity;
//        if(deptOpt.isPresent()) {
//            deptDao.deleteById(id);
//            responseEntity = new ResponseEntity<>(
//                    null,
//                    HttpStatus.OK);
//        } else {
//            responseEntity = new ResponseEntity<>(
//                    "{\"message\":\"Department " + id + " not found\"}",
//                    HttpStatus.NOT_FOUND);
//        }
//        return responseEntity;
//    }

//    @DeleteMapping("/departments")
//    public DepartmentDTO deleteById(@RequestBody DepartmentDTO dept){
//        if (deptDao.findById(dept.getId()).isPresent()){
//            deptDao.deleteById(deptDao.findById(dept.getId()).get().getId());
//        }
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                    "No department with the specified ID could be found");
//    }

    @ExceptionHandler
    public String reportError(Throwable t) {
        t = new Throwable("<h1>Error! You messed up.</h1>");
        return t.getMessage();
    }

    @PutMapping("/put")
    public ResponseEntity<String> updateDepartment(@RequestBody DepartmentDTO newState, @RequestParam String apiKey) {

        if (userDao.isAdmin(apiKey) || userDao.isUpdate(apiKey)) {
            Optional<DepartmentDTO> original = deptDao.findById(newState.getId());
            DepartmentDTO revisedState = null;
            // if the record exists, update it
            if (original.isPresent()){
                revisedState = original.get();
                if(newState.getDeptName() != null){
                    revisedState.setDeptName(newState.getDeptName());
                }
                deptDao.save(revisedState);
            } else {
                // otherwise, insert it
                deptDao.save(newState);
            }
            return new ResponseEntity<>(revisedState.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/count")
    public long getDepartmentCount(){
        return deptDao.count();
    }

    @PostMapping("/post")
    public ResponseEntity<String> createDepartment(@RequestBody DepartmentDTO newDeptDTO, @RequestParam String apiKey) {
        if(userDao.isAdmin(apiKey) || userDao.isUpdate(apiKey)) {
            newDeptDTO.setId(newDeptDTO.getId());
            newDeptDTO.setDeptName(newDeptDTO.getDeptName());
            System.out.println(newDeptDTO);
            deptDao.save(newDeptDTO);
            return new ResponseEntity<>(newDeptDTO.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/findAll")
    public List<DepartmentDTO> findAllDepartments(@RequestParam String apiKey){
        List<DepartmentDTO> departments = deptDao.findAll();
        List<DepartmentDTO> departmentDTOs = new ArrayList<>();
        for (DepartmentDTO dept : departments){
            departmentDTOs.add(new DepartmentDTO(dept.getId(), dept.getDeptName()));
        }
        return departmentDTOs;
    }

    @GetMapping("/findByName/{deptName}")
    public DepartmentDTO findDeptByTitle(@PathVariable String deptName, @RequestParam String apiKey){
        return new DepartmentDTO(deptDao.findDepartmentByDeptName(deptName).getId(),
                deptDao.findDepartmentByDeptName(deptName).getDeptName());
    }

    @GetMapping("/existsById/{id}")
    public boolean existsById(@PathVariable String id, @RequestParam String apiKey){
        Optional<DepartmentDTO> result = deptDao.findById(id);
        return result.isPresent();
    }

    @GetMapping("/findEmployeesForAllDepartments")
    public List<EmpsForDeptsDTO> findNoOfEmployeesForEachDept(
            @RequestParam("fromDate") LocalDate fromDate,
            @RequestParam("toDate") LocalDate toDate,
            @RequestParam String apiKey) {
        System.out.println(fromDate + " " + toDate);
        return deptDao.findNoOfEmployeesForEachDept(fromDate, toDate);
    }

    @GetMapping("/findDepartmentByDeptName")
    public DepartmentDTO findDeptByDeptName(
            @RequestParam("deptName") String deptName,
            @RequestParam String apiKey){
        System.out.println(deptName);
        return deptDao.findDepartmentByDeptName(deptName);
    }

}
