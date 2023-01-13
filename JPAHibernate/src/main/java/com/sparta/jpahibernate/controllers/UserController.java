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
import java.util.Optional;
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
            if( userDAO.findByEmail(user.getEmail()).isPresent()
                    || user.getEmail() == ""
                    || user.getPassword() == ""){
                model.addAttribute("user", null);
                return "userCreateSuccess";
            }
            user.setLastUpdate(Instant.now());
            UUID uuid = UUID.randomUUID();
            user.setKey(uuid.toString());

            userDAO.save(user);
            user = userDAO.findByEmail(user.getEmail()).get(); //retrieve auto-generated values to user object to return
            model.addAttribute("user", user);
            return "userCreateSuccess";
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, Model model){
        UserDTO user = userDAO.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "userUpdate";
    }

    @PostMapping("/update/success")
    public String updateUserSuccess(@ModelAttribute("user") UserDTO user, Model model) {

        // check if records with given ID exists
        if( user == null ){
            model.addAttribute("user", null);
            return "userUpdateSuccess";
        }

        // Check if record with given email exists
        Optional<UserDTO> existsOpt = userDAO.findByEmail( user.getEmail() );
        UserDTO exists = null;
        if(existsOpt.isPresent()){
            exists = userDAO.findByEmail( user.getEmail() ).get();
            if( user.getEmail().equals(exists.getEmail()) ){
                String errCode = "-1";
                user.setEmail(errCode);
            }
        }

        // create a new key
        UUID uuid = UUID.randomUUID();
        user.setKey( uuid.toString() );

        //update the record
        userDAO.save(user);
        model.addAttribute("user", user);
        return "userUpdateSuccess";
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

    @PostMapping("/delete/success")
    public String deleteUserSuccess(@ModelAttribute("user") UserDTO user, Model model) {
        if(userDAO.findById(user.getId()).isEmpty()) {
            user = null;
        } else {
            userDAO.delete(user);
        }
        model.addAttribute("user", user);
        return "userDeleteSuccess";
    }

}
