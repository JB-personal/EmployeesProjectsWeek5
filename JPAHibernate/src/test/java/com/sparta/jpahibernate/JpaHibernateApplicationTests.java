package com.sparta.jpahibernate;

import com.sparta.jpahibernate.entities.Employee;
import com.sparta.jpahibernate.repositories.EmployeeRepository;
import com.sparta.jpahibernate.repositories.EmployeeRepositoryDao;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class JpaHibernateApplicationTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void contextLoads() {

    }

    @Test
    void findEmployeeByLastName() {

        List<Employee> res = employeeRepository.findByLastName("Simmel");
        System.out.println(res);
        Assertions.assertTrue(res.size() >= 1);


    }

    @Test
    void findEmployeeByDepartmentAndDate(){

        LocalDate fromDate = LocalDate.of(1998,10,10);
        LocalDate toDate = LocalDate.of(2020,10,10);

        String department = "Sales";
        //LocalDate fromDate = "1990-10-10";
        //String toDate = "2020-10-10";
        List<Employee> res = employeeRepository.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);
        Assertions.assertTrue(res.size() == 8830);
    }

    @Test
    void findEmployeeByDepartmentAndDateAfter2000(){

        LocalDate fromDate = LocalDate.of(2000,10,10);
        LocalDate toDate = LocalDate.of(2020,10,10);

        String department = "Sales";
        List<Employee> res = employeeRepository.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);
        Assertions.assertTrue(res.size() == 269);
    }
    @Test
    void findEmployeesInDevelopmentByDate()
    {
        LocalDate fromDate = LocalDate.of(1999,10,10);
        LocalDate toDate = LocalDate.of(2020,10,10);

        String department = "Development";
        List<Employee> res = employeeRepository.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);
        Assertions.assertTrue(res.size() == 2513);
    }

    @Test
    void findEmployeeByDepartmentAndDate3(){

        LocalDate fromDate = LocalDate.of(1000,10,10);
        LocalDate toDate = LocalDate.of(1500,10,10);

        String department = "Sales";
        //LocalDate fromDate = "1990-10-10";
        //String toDate = "2020-10-10";
        List<Employee> res = employeeRepository.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);
        Assertions.assertTrue( res.isEmpty() );
        //Assertions.assertTrue();
    }

    // invalid department
    @Test
    void findEmployeeByDepartmentAndDate4(){

        LocalDate fromDate = LocalDate.of(1999,10,10);
        LocalDate toDate = LocalDate.of(2012,10,10);

        String department = "Snakes";


        List<Employee> res = employeeRepository.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);

        Assertions.assertTrue( res == null );


    }
    @Test
    void countNumberOfEmployeesThatLeftADepartmentByYear(){
        String year = "2000";
        String department = "Sales";
        int res = employeeRepository.countNumberOfEmployeesLeftDepartmentByYear(department, year);
        System.out.println(res);
        Assertions.assertTrue(res == 11224);

    }

    @Test
    void countNumberOfEmployeesLeftDepartmentByYear(){
        EmployeeRepositoryDao e =  new EmployeeRepositoryDao();
        int res = e.countNumberOfEmployeesLeftDepartmentByYear();
        Assertions.assertTrue( res == 11224);
    }


}
