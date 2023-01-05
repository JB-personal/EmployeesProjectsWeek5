package com.sparta.jpahibernate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sparta.jpahibernate.controllers.EmployeeController;
import com.sparta.jpahibernate.dao.concretes.DepartmentDAOImpl;
import com.sparta.jpahibernate.dao.concretes.EmployeeDAOImpl;
import com.sparta.jpahibernate.dto.DepartmentDTO;
import com.sparta.jpahibernate.dto.EmployeeDTO;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

import java.util.Optional;



@SpringBootTest
public class EmployeeControllerTesting {

    public EmployeeController empControl;

    @Autowired
    private EmployeeDAOImpl empDAO;

    @Autowired
    private DepartmentDAOImpl deptDao;

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

    @Test
    @DisplayName("findDepartmentByDeptName GET method")
    public void findDepartmentByDeptName_IfDoesNotExist500ServerErrorIsThrown() throws ClientProtocolException, IOException {

        String input = "ThisDepartmentDoesntExist";
        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/departments/findDepartmentByDeptName?deptName=" + input );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assertions.assertEquals((HttpStatus.SC_INTERNAL_SERVER_ERROR), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    @DisplayName("findDepartmentByDeptName GET method")
    public void findDepartmentByDeptName_IfExists200SuccessIsReturned() throws ClientProtocolException, IOException {

        String input = "Finance";
        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/departments/findDepartmentByDeptName?deptName=" + input );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Assertions.assertEquals((HttpStatus.SC_OK), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    @DisplayName("findDepartmentByDeptName GET method")
    public void findDepartmentByDeptName_CheckIfEntityMatches() throws IOException {
        String input = "Finance";

        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/departments/findDepartmentByDeptName?deptName=" + input );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String result = EntityUtils.toString(httpResponse.getEntity());
        String expected = "{\"id\":\"d002\",\"deptName\":\"Finance\"}";

        Assertions.assertEquals(result, expected);

    }

    @Test
    @DisplayName("findDepartmentByDeptName GET method")
    public void findDepartmentByDeptName_CheckIfEntityDoesNotMatch() throws IOException {
        String input = "Finance";

        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/departments/findDepartmentByDeptName?deptName=" + input );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        ObjectMapper mapper = new ObjectMapper();
        DepartmentDTO dto = mapper.readValue(EntityUtils.toString(httpResponse.getEntity()), DepartmentDTO.class);
        DepartmentDTO dto2 = deptDao.findDepartmentByDeptName("Finance");

//        String result = EntityUtils.toString(httpResponse.getEntity());
//        String expected = "{\"id\":\"d002\",\"deptName\":\"Finance\"}";
//        System.out.println(result);

        Assertions.assertEquals(dto, dto2);

    }

    @Test
    @DisplayName("findDepartmentById GET method")
    public void findDepartmentById_IfDoesNotExist500ServerErrorIsThrown() throws IOException {
        String input = "d095";

        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/departments/" + input);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assertions.assertEquals((HttpStatus.SC_INTERNAL_SERVER_ERROR), httpResponse.getStatusLine().getStatusCode());

    }
    @Test
    @DisplayName("findDepartmentById GET method")
    public void findDepartmentById_IfExists200SuccessIsReturned() throws IOException {
        String input = "d002";

        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/departments/" + input);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        Assertions.assertEquals((HttpStatus.SC_OK), httpResponse.getStatusLine().getStatusCode());

    }

    @Test
    @DisplayName("findDepartmentById GET method")
    public void findDepartmentById_CheckIfEntityMatches() throws IOException {
        String input = "Finance";

        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/departments/findDepartmentByDeptName?deptName=" + input );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String result = EntityUtils.toString(httpResponse.getEntity());
        String expected = "{\"id\":\"d002\",\"deptName\":\"Finance\"}";

        Assertions.assertEquals(result, expected);

    }

    @Test
    @DisplayName("findDepartmentById GET method")
    public void findDepartmentById_CheckIfEntityDoesNotMatch() throws IOException {
        String input = "Human%20Resources";

        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/departments/findDepartmentByDeptName?deptName=" + input );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String result = EntityUtils.toString(httpResponse.getEntity());
        String expected = "{\"id\":\"d002\",\"deptName\":\"Finance\"}";

        Assertions.assertNotEquals(result, expected);

    }

}
