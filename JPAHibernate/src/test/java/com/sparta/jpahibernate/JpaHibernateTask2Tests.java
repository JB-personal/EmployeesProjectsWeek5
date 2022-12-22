package com.sparta.jpahibernate;

import com.sparta.jpahibernate.entities.Department;
import com.sparta.jpahibernate.entities.DeptEmpId;
import com.sparta.jpahibernate.entities.Employee;
import com.sparta.jpahibernate.repositories.DepartmentRepository;
import com.sparta.jpahibernate.repositories.DeptEmpRepository;
import com.sparta.jpahibernate.repositories.SalaryRepository;
import com.sparta.jpahibernate.repositories.TitleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class JpaHibernateTask2Tests {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DeptEmpRepository deptEmpRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private TitleRepository titleRepository;


    @Test
    void contextLoads() {
    }

    // What is the average salary for a named department on a given date?
    @Test
    void averageSalaryForDeptOnGivenDate(){

        String year = "2000";
        String yearEnd = "2001";
        String department = "Finance";
        List<Double> averageSalaryForGivenDate = salaryRepository.findAverageSalaryByDepartmentAndGivenDate(department,
                year, yearEnd);
        System.out.println(averageSalaryForGivenDate);
        Assertions.assertEquals(63779.2199, salaryRepository.findAverageSalaryByDepartmentAndGivenDate(department, year,
                yearEnd).get(0));

    }
    // Given a job title name, what is the range of salary values within a given year?

    @Test
    void givenJobTitleFindSalaryRangeWithinGivenYear(){

        String title = "Staff";
        String year = "2000";
        String yearEnd = "2001";

        List<Double> salaryRange = new ArrayList<>();
        salaryRange.add(salaryRepository.findSalaryByTitleWithinGivenYearMin( title, year, yearEnd).get(0));
        salaryRange.add(salaryRepository.findSalaryByTitleWithinGivenYearMax( title, year, yearEnd).get(0));
        System.out.println(salaryRange.toString());
        List<Double> expected = new ArrayList<>(List.of(39186.0, 118492.0));
        Assertions.assertEquals(expected, salaryRange);
    }


    //TODO: add new method 1
    //TODO: add new method 2
    //TODO: add DAO and DTO



}
