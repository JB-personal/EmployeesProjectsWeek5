package com.sparta.jpahibernate;

import com.sparta.jpahibernate.controllers.EmployeeController;
import com.sparta.jpahibernate.dao.concretes.EmployeeDAOImpl;
import com.sparta.jpahibernate.dao.interfaces.EmployeeDAO;
import com.sparta.jpahibernate.dto.EmployeeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EmployeeControllerTesting {

    public EmployeeController empControl;

    @Autowired
    private EmployeeDAOImpl empDAO;

    @Test
    @Rollback
    @DisplayName("Delete employees by ID")
    void deleteByID(){
        empControl = new EmployeeController();
        Optional<EmployeeDTO> optEmp = empDAO.findById(7);
        System.out.println(optEmp.toString());

        //optEmp.ifPresent(employeeDTO -> empControl.delete(employeeDTO));
        if (optEmp.isPresent()){
            empControl.deleteById(7);
        }
        Assertions.assertFalse(empDAO.findById(7).isPresent());

    }
}
