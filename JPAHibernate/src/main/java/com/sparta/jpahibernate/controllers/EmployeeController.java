package com.sparta.jpahibernate.controllers;

import com.sparta.jpahibernate.dao.concretes.DepartmentDAOImpl;
import com.sparta.jpahibernate.dao.concretes.EmployeeDAOImpl;
import com.sparta.jpahibernate.dto.DepartmentDTO;
import com.sparta.jpahibernate.dto.EmployeeDTO;
import com.sparta.jpahibernate.repositories.EmployeeRepository;
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

    @GetMapping("/{id}")
    public String getEmployee(@PathVariable("id") int id, Model model) {
        EmployeeDTO employee = employeeDAO.findById(id).orElse(null);
        model.addAttribute("employee", employee);
        return "employeeDisplay";
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
