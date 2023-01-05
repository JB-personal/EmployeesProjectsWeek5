package com.sparta.jpahibernate.controllers;

import com.sparta.jpahibernate.dao.concretes.DepartmentDAOImpl;
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


    @GetMapping("/{id}")
    public DepartmentDTO findDepartmentById(@PathVariable String id) {
        return deptDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No department with the specified ID could be found"));
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id){
        deptDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No department with the specific ID could be found"));
        deptDao.deleteById(id);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteById(@PathVariable String id) {
//        Optional<DepartmentDTO> deptOpt = deptDao.findById(id);
//        ResponseEntity<String> responseEntity;
//        if(deptOpt.isPresent()) {
//            deptDao.deleteById(id);
//            responseEntity = new ResponseEntity<>(
//                    null,
//                    HttpStatus.NO_CONTENT);
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

//    @ExceptionHandler
//    public String reportError(Throwable t) {
//        t = new Throwable("<h1>Error! You messed up.</h1>");
//        return t.getMessage();
//    }

    @PutMapping("")
    public DepartmentDTO updateDepartment(@RequestBody DepartmentDTO newState) {
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
        return revisedState;
    }

    @GetMapping("/count")
    public long getDepartmentCount(){
        return deptDao.count();
    }

    @PostMapping("")
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO newDeptDTO){
        newDeptDTO.setId(newDeptDTO.getId());
        newDeptDTO.setDeptName(newDeptDTO.getDeptName());
        System.out.println(newDeptDTO);
        deptDao.save(newDeptDTO);
        return newDeptDTO;
    }

    @GetMapping("/findAll")
    public List<DepartmentDTO> findAllDepartments(){
        List<DepartmentDTO> departments = deptDao.findAll();
        List<DepartmentDTO> departmentDTOs = new ArrayList<>();
        for (DepartmentDTO dept : departments){
            departmentDTOs.add(new DepartmentDTO(dept.getId(), dept.getDeptName()));
        }
        return departmentDTOs;
    }

    @GetMapping("/findByName/{deptName}")
    public DepartmentDTO findDeptByTitle(@PathVariable String deptName){
        return new DepartmentDTO(deptDao.findDepartmentByDeptName(deptName).getId(),
                deptDao.findDepartmentByDeptName(deptName).getDeptName());
    }

    @GetMapping("/existsById/{id}")
    public boolean existsById(@PathVariable String id){
        Optional<DepartmentDTO> result = deptDao.findById(id);
        return result.isPresent();
    }

    @GetMapping("/findEmployeesForAllDepartments")
    public List<EmpsForDeptsDTO> findNoOfEmployeesForEachDept(
            @RequestParam("fromDate") LocalDate fromDate,
            @RequestParam("toDate") LocalDate toDate) {
        System.out.println(fromDate + " " + toDate);
        return deptDao.findNoOfEmployeesForEachDept(fromDate, toDate);
    }

    @GetMapping("/findDepartmentByDeptName")
    public DepartmentDTO findDeptByDeptName(
            @RequestParam("deptName") String deptName){
        System.out.println(deptName);
        return deptDao.findDepartmentByDeptName(deptName);
    }

}
