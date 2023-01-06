package com.sparta.jpahibernate.dao.interfaces;

import com.sparta.jpahibernate.dto.EmployeeDTO;
import com.sparta.jpahibernate.dto.UserDTO;

import java.util.Optional;

public interface UserDAO extends DAO<UserDTO>{
    void createUser(UserDTO dto);

    void updateUser(long id, UserDTO newInfo);

    Optional<UserDTO> findById(long id);

    String getEmail(UserDTO dto);

    String getPassword(UserDTO dto);

    String getKey(UserDTO dto);

    String getLevel(UserDTO dto);


}
