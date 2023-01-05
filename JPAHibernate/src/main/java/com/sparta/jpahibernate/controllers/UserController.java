package com.sparta.jpahibernate.controllers;

import com.sparta.jpahibernate.dao.concretes.UserDAOImpl;
import com.sparta.jpahibernate.dto.LoginDTO;
import com.sparta.jpahibernate.dto.UserDTO;
import com.sparta.jpahibernate.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserDAOImpl userDAO;

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id){
        UserDTO userDTO = userDAO.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No user with this ID could be found"));
        userDAO.delete(userDTO);
    }


    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody LoginDTO login)
    {
        UserDTO newUser = new UserDTO();
        UUID uuid = UUID.randomUUID(); //Generates random UUID
        newUser.setEmail(login.getEmail());
        newUser.setPassword(login.getPassword());
        newUser.setKey(String.valueOf(uuid));
        userDAO.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateEmployeePut(@RequestBody UserDTO newState) {
        Optional<UserDTO> original = userDAO.findById(newState.getId());
        if (original.isPresent()) {
            userDAO.updateUser(newState.getId(), newState);

            return new ResponseEntity<>(userDAO.findById(newState.getId()).get(), HttpStatus.OK);
        }
        else {
            userDAO.save(newState);
            return new ResponseEntity<>(userDAO.findById(newState.getId()).get(), HttpStatus.OK);
        }

    }


}
