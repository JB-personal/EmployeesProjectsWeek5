package com.sparta.jpahibernate.restcontrollers;

import com.sparta.jpahibernate.controllers.EmployeeController;
import com.sparta.jpahibernate.dao.concretes.EmployeeDAOImpl;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;

@SpringBootTest
public class EmpControllerTesting {

    @Autowired
    private EmployeeDAOImpl dao;


    @MockBean
    private EmployeeController emp;
    // GET METHODS

    @Test
    public void getEmployee123() throws ClientProtocolException, IOException {

    }
    @Test
    public void getAllEmployeeSuccess() throws ClientProtocolException, IOException {


    }
    @Test
    public void getEmployeeFail() throws ClientProtocolException, IOException {


    }
//    @Test
//
//    @Test
//
//    @Test


    // POST METHODS

//    @Test
//
//    @Test
//
//    @Test
//
//    @Test
//
//    @Test
//
//    @Test

    // PUT METHODS

//    @Test
//
//    @Test
//
//    @Test
//
//    @Test
//
//    @Test
//
//    @Test

    // PATCH METHODS

//    @Test
//
//    @Test
//
//    @Test
//
//    @Test
//
//    @Test
//
//    @Test

    // DELETE METHODS

//    @Test
//
//    @Test
//
//    @Test
//
//    @Test
//
//    @Test
//
//    @Test
}
