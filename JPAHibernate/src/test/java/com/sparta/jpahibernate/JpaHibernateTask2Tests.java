package com.sparta.jpahibernate;

import com.sparta.jpahibernate.entities.Department;
import com.sparta.jpahibernate.entities.DeptEmpId;
import com.sparta.jpahibernate.repositories.DepartmentRepository;
import com.sparta.jpahibernate.repositories.DeptEmpRepository;
import com.sparta.jpahibernate.repositories.SalaryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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


    @Test
    void contextLoads() {
    }

    // What is the average salary for a named department on a given date?
//    @Test
//    void averageSalaryForDeptOnGivenDate(){
//        double average = 0;
//
//        // get department by name
//        Department result = departmentRepository.findByDeptName("Finance");
//        // use the department id that corresponds to the name
//        String id = result.getId();
//        // get all employee ids in department
//        List<DeptEmpId> deptEmpId = deptEmpRepository.findByDeptNo(id);
//
//        // create empty array list to add employee salaries in department
//        ArrayList<Integer> salaries = new ArrayList<>();
//        for(DeptEmpId object : deptEmpId){
//            Integer i = object.getEmpNo();
//            int salaryFromId = salaryRepository.getSalaryFromEmployeeId(i);
//            salaries.add(salaryFromId);
//        }
//
//        for(int i: salaries){
//            average += i;
//        }
//        average /= salaries.size();

//        Assertions.assertEquals();


        // TODO: add logic for a given date
        //  figure way to test salary average - currently looking at querying DB. Need a working query:
        //  SELECT * FROM employees.dept_emp INNER JOIN Salaries ON Salaries.emp_no=dept_emp.emp_no WHERE dept_emp.dept_no = "d002";


//    }

    // Given a job title name, what is the range of salary values within a given year?

//    @Test
//    void givenJobTitleFindSalaryRangeWithinGivenYear(){

        // get title by name

        // get all employees with that title

        // check to see that it fits the time frame

        //


//    }



}
