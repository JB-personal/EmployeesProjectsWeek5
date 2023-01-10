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
}
