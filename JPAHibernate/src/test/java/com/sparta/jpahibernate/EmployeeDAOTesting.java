package com.sparta.jpahibernate;

import com.sparta.jpahibernate.dao.concretes.EmployeeDAOImpl;
import com.sparta.jpahibernate.dto.EmployeeDTO;
import com.sparta.jpahibernate.dto.EmpsByDeptsDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class EmployeeDAOTesting {

    @Autowired
    private EmployeeDAOImpl empDao;

    @Test
    @DisplayName("Count Employees In Employee Table, Expected Count Is 300,026")
    void employeeCount(){
        long numberOfEmp = empDao.count();
        long expectedCount = 300000;
        Assertions.assertTrue(numberOfEmp >= expectedCount);
    }

    @Test
    @Rollback
    @DisplayName("Delete employee from table, ")
    void employeeDelete(){
        Optional<EmployeeDTO> optEmp = empDao.findById(10001);
        optEmp.ifPresent(employeeDTO -> empDao.delete(employeeDTO));
        Assertions.assertFalse(empDao.findById(10001).isPresent());
    }

    @Test
    @DisplayName("Find all employees from table")
    void employeeFindAll(){
        List<EmployeeDTO> results = empDao.findAll();
        Assertions.assertTrue(results.size() >= 300000);
    }

    @Test
    @Rollback
    @DisplayName("Save an employee into the database")
    void employeeSave(){
        empDao.save(new EmployeeDTO(50000, LocalDate.of(1977,12,4),
                "Sam", "Johnson",
                "M", LocalDate.of(2010,1,1)));
        Optional<EmployeeDTO> result = empDao.findById(50000);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @Rollback
    @DisplayName("Delete an employee by Id")
    void employeeDeleteById(){
        empDao.deleteById(100001);
        Assertions.assertFalse(empDao.existsById(100001));
    }

    @Test
    @DisplayName("Check to see if an employee exists searching by Id")
    void employeeExistById(){
        Assertions.assertTrue(empDao.existsById(10001));
    }


    @Test
    @DisplayName("Find the pay gap in salaries between men and women")
    void getPayGapSalary(){
        empDao.getPayGapSalary();
    }

    @Test
    @Rollback
    @DisplayName("Update employee")
    void updateEmployee(){
        String[] params = {"10001", "2022-12-22", "TEST", "USER", "M", "2022-12-21"};
        int id = 10001;
        Optional<EmployeeDTO> optEmp = empDao.findById(id);
        if(optEmp.isPresent()){
            EmployeeDTO emp = optEmp.get();
            empDao.update(id, params);
        }
        Optional<EmployeeDTO> optEmpAfterUpdate = empDao.findById(id);
        if(optEmpAfterUpdate.isPresent()){
            EmployeeDTO emp = optEmpAfterUpdate.get();
            Assertions.assertEquals("TEST", emp.getFirstName());
        }
    }

    @Test
    @DisplayName("Find all Employees by department given a year interval")
    void findEmployeesByDepartmentAndDate(){
        String department = "Finance";
        LocalDate fromDate = Date.valueOf("2000-01-01").toLocalDate();
        LocalDate toDate = Date.valueOf("2001-01-01").toLocalDate();
        List<EmpsByDeptsDTO> result = empDao.findByDepartmentAndDate(department,fromDate,toDate);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Find all Employees that left a specific department by a given year")
    void countNumberOfEmployeesLeftDepartmentByGivenYear(){
        String department = "Finance";
        String year = "2000";
        int result = empDao.countNumberOfEmployeesLeftDepartmentByYear(department,year);
        Assertions.assertTrue(result > 0);
    }



    @Test
    @DisplayName("Average salary for a named department on a given date")
    void averageSalaryForDeptOnGivenDate(){
        String year = "2000";
        String yearEnd = "2001";
        String department = "Finance";
        List<Double> averageSalaryForGivenDate = empDao.findAverageSalaryByDepartmentAndGivenDate(department,
                year, yearEnd);
        System.out.println(averageSalaryForGivenDate);
        Assertions.assertEquals(63779.2199, empDao.findAverageSalaryByDepartmentAndGivenDate(department, year,
                yearEnd).get(0));
    }


    @Test
    @DisplayName("Given a job title name, what is the range of salary values within period")
    void givenJobTitleFindSalaryRangeWithinGivenYear(){
        String title = "Staff";
        String year = "2000";
        String yearEnd = "2001";

        List<Double> salaryRange = new ArrayList<>();
        salaryRange.add(empDao.findSalaryByTitleWithinGivenYearMin( title, year, yearEnd).get(0));
        salaryRange.add(empDao.findSalaryByTitleWithinGivenYearMax( title, year, yearEnd).get(0));
        System.out.println(salaryRange.toString());
        List<Double> expected = new ArrayList<>(List.of(39186.0, 118492.0));
        Assertions.assertEquals(expected, salaryRange);
    }
}
