package com.sparta.jpahibernate.controllers;

import com.sparta.jpahibernate.dao.concretes.EmployeeDAOImpl;
import com.sparta.jpahibernate.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/employee")
public class EmployeeController {

    @Autowired
    private EmployeeDAOImpl employeeDAO;

    @GetMapping("/search")
    public String findEmpById(Model model) {
        EmployeeDTO employee = new EmployeeDTO();
        model.addAttribute("employee", employee);
        return "employeeDisplay";
    }

    @PostMapping("/search/success")
    public String findEmpByIdSuccess(@ModelAttribute("employee") EmployeeDTO employee, Model model) {
        employee = employeeDAO.findById(employee.getId()).orElse(null);
        model.addAttribute("employee", employee);
        return "employeeDisplaySuccess";
    }

    @GetMapping("/all")
    public String getAllEmployees(Model model) {
        List<EmployeeDTO> employees = employeeDAO.findAll();
        model.addAttribute("employees", employees);
        return "employeeDisplayAll";
    }

    @GetMapping ("/new")
    public String createEmployee(Model model){
        EmployeeDTO employee = new EmployeeDTO();
        model.addAttribute("employee", employee);
        return "employeeCreate";
    }

    @PostMapping("/new/success")
    public String createEmployeeSuccess(@ModelAttribute("employee")EmployeeDTO employee, Model model){
        employeeDAO.save(employee);
        model.addAttribute("employee", employee);
        return "employeeCreateSuccess";
    }

    @GetMapping("/delete")
    public String deleteEmployee(Model model){
        model.addAttribute("employee", new EmployeeDTO());
        return "employeeDelete";
    }

    @PostMapping("/delete/success")
    public String deleteEmployeeSuccess(@ModelAttribute("employee")EmployeeDTO employee, Model model){
        if(employeeDAO.findById(employee.getId()).isEmpty()) {
            employee = null;
        } else {
            employeeDAO.delete(employee);
        }
        model.addAttribute("employee", employee);
        return "employeeDeleteSuccess";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        EmployeeDTO employee = employeeDAO.findById(id)
                .orElse(null);
        model.addAttribute("employee", employee);
        return "employeeUpdate";
    }

    @PostMapping("/update/success")
    public String updateEmployee(@ModelAttribute("employee") EmployeeDTO employee, Model model) {
        employeeDAO.save(employee);
        model.addAttribute("employee", employee);
        return "employeeUpdateSuccess";
    }
}
