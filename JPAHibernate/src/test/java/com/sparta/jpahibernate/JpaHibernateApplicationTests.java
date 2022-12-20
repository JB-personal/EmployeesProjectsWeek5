package com.sparta.jpahibernate;

import com.sparta.jpahibernate.repositories.DepartmentRepository;
import com.sparta.jpahibernate.repositories.DeptEmpRepository;
import com.sparta.jpahibernate.repositories.SalaryRepository;
import com.sparta.jpahibernate.repositories.TitleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.sparta.jpahibernate.entities.Employee;
import com.sparta.jpahibernate.repositories.EmployeeRepository;
import com.sparta.jpahibernate.repositories.EmployeeRepositoryDao;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;


import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
class JpaHibernateApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    DeptEmpRepository deptEmpRepo;
    @Autowired
    DepartmentRepository deptRepo;

    @Autowired
    SalaryRepository salaryRepo;

    @Autowired
    TitleRepository titleRepo;

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

    @Test
    void findNoEmployeesForEachDepartment(){
        List<String> list1 = deptEmpRepo.findDeptName();
        List<Integer> list2 = deptEmpRepo.findNoOfEmployeesForEachDept(
                LocalDate.of(1995,1,1),
                LocalDate.of(2005,1,1)
        );
        for (int i = 0; i < list1.size(); i++){
            System.out.println(list1.get(i) + " department has " + list2.get(i) + " employees.");
        }
    }

    @Test
    void test3(){
        List<String> list1 = titleRepo.findAllTitles();
        String male = "M";
        String female = "F";
        List<Double> males = salaryRepo.findAvgSalary(male);
        List<Double> females = salaryRepo.findAvgSalary(female);
        for (int i = 0; i < list1.size(); i++) {
            if (males.get(i) < females.get(i)) {
                System.out.print("The pay gap for " + list1.get(i) + " is higher for females by ");
                System.out.printf("%.2f", (1-(males.get(i) / females.get(i))));
                System.out.println();
            }
            if (males.get(i) > females.get(i)) {
                System.out.print("The pay gap for " + list1.get(i) + " is higher for males by ");
                System.out.printf("%.2f", (1-(females.get(i) / males.get(i))));
                System.out.println();
            }
        }
    }

}
