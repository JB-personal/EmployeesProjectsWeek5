package com.sparta.jpahibernate.controllers;

import com.sparta.jpahibernate.dao.concretes.UserDAOImpl;
import com.sparta.jpahibernate.dao.interfaces.UserDAO;
import com.sparta.jpahibernate.dto.DepartmentDTO;
import com.sparta.jpahibernate.dto.UserDTO;
import com.sparta.jpahibernate.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/web/user")
public class UserController {

    @Autowired
    private UserDAOImpl userDAO;

    // --------------------------------- basic CRUD -------------------------------------------
    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") long id, Model model){
        UserDTO user = userDAO.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "userDisplay";
    }


}
