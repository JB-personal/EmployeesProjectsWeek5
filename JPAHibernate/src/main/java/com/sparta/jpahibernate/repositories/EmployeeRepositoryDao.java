package com.sparta.jpahibernate.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeRepositoryDao {


    public static void main(String[] args){

        EmployeeRepositoryDao.countNumberOfEmployeesLeftDepartmentByYear();

    }


    @Autowired
    private EmployeeRepository employeeRepository;

    public static int countNumberOfEmployeesLeftDepartmentByYear(){

        String department = "Sales";

        String year = "2000";
        int numYear = Integer.parseInt(year);

        System.out.println(numYear);

        LocalDate start = LocalDate.of(numYear,01,01);
        LocalDate end = LocalDate.of(numYear,12,31);

        System.out.println( start.getClass() );

       // int res = employeeRepository.countNumberOfEmployeesLeftDepartmentByYear2(department, start, end);

        return 0;
    }

}
