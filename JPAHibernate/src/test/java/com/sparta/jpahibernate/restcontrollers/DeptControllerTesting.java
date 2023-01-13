package com.sparta.jpahibernate.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sparta.jpahibernate.dao.concretes.DepartmentDAOImpl;
import com.sparta.jpahibernate.dto.DepartmentDTO;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class DeptControllerTesting {

    @Autowired
    private DepartmentDAOImpl deptDao;


    // GET METHODS

    @Test
    public void findDepartmentByDeptName_IfDoesNotExist500ServerErrorIsThrown() throws ClientProtocolException, IOException {

        HttpClient client = HttpClients.createDefault();
        String input = "NonExistentDepartment";
        HttpGet get = new HttpGet( "http://localhost:8080/api/departments/findDepartmentByDeptName?deptName=" + input);
        HttpResponse response = client.execute(get);
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");
        System.out.println(response);
        System.out.println(response.getEntity().getContent());
        Assertions.assertEquals((HttpStatus.SC_INTERNAL_SERVER_ERROR), response.getStatusLine().getStatusCode());
    }

    @Test
    public void findDepartmentByDeptName_IfExists200SuccessIsReturned() throws ClientProtocolException, IOException {

        HttpClient client = HttpClients.createDefault();
        String input = "Finance";
        HttpGet get = new HttpGet( "http://localhost:8080/api/departments/findDepartmentByDeptName?deptName=" + input );
        HttpResponse response = client.execute(get);
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");

        Assertions.assertEquals((HttpStatus.SC_OK), response.getStatusLine().getStatusCode());
    }

    @Test
    public void findDepartmentByDeptName_IfEntityEqualSuccess() throws ClientProtocolException, IOException {

        HttpClient client = HttpClients.createDefault();
        String input = "Finance";
        HttpGet get = new HttpGet( "http://localhost:8080/api/departments/findDepartmentByDeptName?deptName=" + input );
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(get);
        ObjectMapper mapper = new ObjectMapper();
        DepartmentDTO result = mapper.readValue(EntityUtils.toString(response.getEntity()), DepartmentDTO.class);
        DepartmentDTO expected = deptDao.findDepartmentByDeptName("Finance");

        Assertions.assertNotEquals(result, expected);
    }

    @Test
    public void findDepartmentByDeptName_SuccessIfEntityNotEqual() throws ClientProtocolException, IOException {

        HttpClient client = HttpClients.createDefault();
        String input = "DepartmentDoesNotExist";
        HttpGet get = new HttpGet( "http://localhost:8080/api/departments/findDepartmentByDeptName?deptName=" + input );
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(get);
        ObjectMapper mapper = new ObjectMapper();
        DepartmentDTO result = mapper.readValue(EntityUtils.toString(response.getEntity()), DepartmentDTO.class);
        DepartmentDTO expected = deptDao.findDepartmentByDeptName("Finance");

        Assertions.assertNotEquals(result, expected);
    }

    @Test
    public void findDepartmentById_IfExists200SuccessIsReturned() throws IOException {
        HttpClient client = HttpClients.createDefault();
        String input = "d001";
        HttpGet get = new HttpGet( "http://localhost:8080/api/departments/" + input );
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(get);

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void findDepartmentById_IfDoesNotExist500ServerErrorIsThrown() throws IOException {
        HttpClient client = HttpClients.createDefault();
        String input = "DepartmentDoesNotExist";
        HttpGet get = new HttpGet( "http://localhost:8080/api/departments/" + input );
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(get);

        Assertions.assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, response.getStatusLine().getStatusCode());
    }



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

    @Test
    public void deleteDepartmentById_200IfSuccessful() throws ClientProtocolException, IOException {

        HttpClient client = HttpClients.createDefault();
        String id = "1";
        HttpGet get = new HttpGet( "http://localhost:8080/api/departments/findDepartmentByDeptName?deptName=" + id );
        HttpResponse response = client.execute(get);
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-type", "application/json");

        Assertions.assertEquals((HttpStatus.SC_OK), response.getStatusLine().getStatusCode());
    }

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
