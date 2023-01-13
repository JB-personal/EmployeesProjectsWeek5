package com.sparta.jpahibernate.controllers;

import com.sparta.jpahibernate.dao.concretes.UserDAOImpl;
import com.sparta.jpahibernate.dto.EmployeeDTO;
import com.sparta.jpahibernate.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/web/user")
@SessionAttributes({"/login", "/index", "/loginSuccess"})
public class UserController {

    @Autowired
    private UserDAOImpl userDAO;

    @GetMapping("/login")
    public String handleLogin(){
        return "login";
    }

    @PostMapping("/loginSuccess")
    public String loginSuccess() {
        return "index";
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    // --------------------------------- basic CRUD -------------------------------------------
    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") long id, Model model){
        UserDTO user = userDAO.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "userDisplay";
    }

    @GetMapping("/new")
    public String createUser(Model model){
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "userCreate";
    }

    @PostMapping("/new/success")
    public String createUserSuccess(@ModelAttribute("user") UserDTO user, Model model){
        user.setLastUpdate(Instant.now());
        UUID uuid = UUID.randomUUID();
        user.setKey(uuid.toString());
        userDAO.save(user);
        model.addAttribute("user", user);
        return "userCreateSuccess";
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, Model model){
        UserDTO user = userDAO.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "userUpdate";
    }

    public String updateUser(Model model) {
        EmployeeDTO user = new EmployeeDTO();
        model.addAttribute("user", user);
        return "employeeUpdate";
    }

    @PostMapping("/update/success")
    public String updateUserSuccess(@ModelAttribute("user") UserDTO user, Model model) {
        user = userDAO.findById(user.getId())
                .orElse(null);
        userDAO.save(user);
        model.addAttribute("user", user);
        return "employeeUpdateSuccess";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model){
        List<UserDTO> users = userDAO.findAll();
        model.addAttribute("users", users);
        return "userDisplayAll";
    }

    @GetMapping("/delete")
    public String deleteUser(Model model){
        model.addAttribute("user", new UserDTO() );
        return "userDelete";
    }

    @GetMapping("/delete/success")
    public String deleteUserSuccess(@ModelAttribute("user") UserDTO user, Model model) {
        user = userDAO.findById(user.getId()).get();
        userDAO.delete(user);
        model.addAttribute("user", user);
        return "userDeleteSuccess";
    }

}
