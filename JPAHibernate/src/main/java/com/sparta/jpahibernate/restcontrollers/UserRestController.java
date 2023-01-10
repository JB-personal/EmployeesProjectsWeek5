package com.sparta.jpahibernate.restcontrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.jpahibernate.dao.concretes.UserDAOImpl;
import com.sparta.jpahibernate.dto.LoginDTO;
import com.sparta.jpahibernate.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserDAOImpl userDao;

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id){
        UserDTO userDTO = userDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No user with this ID could be found"));
        userDao.delete(userDTO);
    }


    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody LoginDTO login)
    {
        UserDTO newUser = new UserDTO();
        UUID uuid = UUID.randomUUID(); //Generates random UUID
        newUser.setEmail(login.getEmail());
        newUser.setPassword(login.getPassword());
        newUser.setKey(String.valueOf(uuid));
        userDao.save(newUser);
        return new ResponseEntity<>("User created successfully with email "
                + newUser.getEmail()
                + " and the generated API key is "
                + newUser.getKey(), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO login) {
        Optional<UserDTO> user = userDao.findByEmail(login.getEmail());
        if(user.isPresent()){
            if(login.getEmail().equals(user.get().getEmail()) && login.getPassword().equals(user.get().getPassword())){
                return new ResponseEntity<>(user.get().getKey(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        "{\"message\":\"Invalid credentials!\"}", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("Account does not exist.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateEmployeePut(@RequestBody UserDTO newState) {
        Optional<UserDTO> original = userDao.findById(newState.getId());
        if (original.isPresent()) {
            userDao.updateUser(newState.getId(), newState);

            return new ResponseEntity<>(userDao.findById(newState.getId()).get(), HttpStatus.OK);
        }
        else {
            userDao.save(newState);
            return new ResponseEntity<>(userDao.findById(newState.getId()).get(), HttpStatus.OK);
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable int id){
        Optional<UserDTO> result = userDao.findById(id);
        ObjectMapper userMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        ResponseEntity<String> returnValue = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        if (result.isPresent()){
            UserDTO userDTO = result.get();
            String userString = null;
            try {
                userString = userMapper.writeValueAsString(userDTO);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            returnValue = new ResponseEntity<>(userString, headers, HttpStatus.OK);
        } else {
            returnValue = new ResponseEntity<>(
                    "{\"message\":\"User " + id + " not found!\"}", headers, HttpStatus.NOT_FOUND
            );
        }
        return returnValue;
    }


}
