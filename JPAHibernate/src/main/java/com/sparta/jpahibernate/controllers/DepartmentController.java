package com.sparta.jpahibernate.controllers;

import com.sparta.jpahibernate.dao.concretes.DepartmentDAOImpl;
import com.sparta.jpahibernate.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/web/department")
public class DepartmentController {

    @Autowired
    private DepartmentDAOImpl departmentDAO;


    @GetMapping("/search")
    public String findDeptById(Model model) {
        DepartmentDTO department = new DepartmentDTO();
        model.addAttribute("department", department);
        return "departmentDisplay";
    }

    @GetMapping("/name/{deptName}")
    public String findDeptByTitle(@PathVariable String deptName, Model model){
        DepartmentDTO department = departmentDAO.findDepartmentByDeptName(deptName);
        model.addAttribute("department", department);
        return "departmentDisplay";
    }

    @GetMapping("/all")
    public String getAllDepartments(Model model) {
        List<DepartmentDTO> departments = departmentDAO.findAll();
        model.addAttribute("departments", departments);
        return "departmentDisplayAll";
    }

    @GetMapping("/new")
    public String createDepartment(Model model){
        DepartmentDTO department = new DepartmentDTO();
        model.addAttribute("department", department);
        return "departmentCreate";
    }

    @PostMapping("/new/success")
    public String createDepartmentSuccess(@ModelAttribute("department") DepartmentDTO department, Model model){
        departmentDAO.save(department);
        model.addAttribute("department", department);
        return "departmentCreateSuccess";
    }

    @GetMapping("/update/{id}")
    public String updateDepartment(@PathVariable("id") String id, Model model) {
        DepartmentDTO department = departmentDAO.findById(id)
                .orElse(null);
        model.addAttribute("department", department);
        return "departmentUpdate";
    }

    @PostMapping("/update/success")
    public String updateDepartmentSuccess(@ModelAttribute("department") DepartmentDTO department, Model model) {
        department = departmentDAO.findById(department.getId())
                .orElse(null);
        departmentDAO.save(department);
        model.addAttribute("department", department);
        return "departmentUpdateSuccess";
    }

    @GetMapping("/delete")
    public String deleteDepartment(Model model){
        model.addAttribute("department", new DepartmentDTO());
        return "departmentDelete";
    }

    @PostMapping("/delete/success")
    public String deleteDepartmentSuccess(@ModelAttribute("department")DepartmentDTO department, Model model){
        department = departmentDAO.findById(department.getId()).get();
        departmentDAO.deleteById(department.getId());
        model.addAttribute("department", department);
        return "departmentDeleteSuccess";
    }

    @GetMapping("/findemployeesforalldepartments")
    public String findNoOfEmployeesForEachDept(Model model) {
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now();
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        return "departmentDisplayEmployees";
    }

    @PostMapping("/findemployeesforalldepartments/success")
    public String EmpsForEachDept(Model model, @ModelAttribute("fromDate")LocalDate fromDate, @ModelAttribute("toDate")LocalDate toDate){
        List<EmpsForDeptsDTO> list = departmentDAO.findNoOfEmployeesForEachDept(
                fromDate, toDate);
        model.addAttribute("empsForDepts", list);
        return "departmentDisplayEmployeesSuccess";
    }
}
