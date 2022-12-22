package com.sparta.jpahibernate;

import com.sparta.jpahibernate.dao.concretes.*;
import com.sparta.jpahibernate.dto.EmpByDeptDTO;
import com.sparta.jpahibernate.dto.EmployeeDTO;
import com.sparta.jpahibernate.dto.EmpsForDeptsDTO;
import com.sparta.jpahibernate.dto.SalaryForTitlesDTO;
import com.sparta.jpahibernate.entities.Department;
import com.sparta.jpahibernate.entities.Employee;
import com.sparta.jpahibernate.repositories.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
class JpaHibernateApplicationTests {

    @Autowired
    private EmployeeDAO empDao;

    @Autowired
    private DepartmentDAO deptDao;

    @Autowired
    SalaryRepository salaryRepo;

    @Autowired
    TitleRepository titleRepo;

    @Test
    void contextLoads() {
    }


    @Test
    void findEmployeeByLastName() {

        List<Employee> res = empDao.findByLastName("Simmel");
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
        List<EmpByDeptDTO> res = empDao.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res + " " + res.size());
        Assertions.assertTrue(res.size() == 1358);
    }

    @Test
    void findEmployeeByDepartmentAndDateAfter2000(){

        LocalDate fromDate = LocalDate.of(2000,10,10);
        LocalDate toDate = LocalDate.of(2020,10,10);

        String department = "Sales";
        List<EmpByDeptDTO> res = empDao.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res + " " + res.size());
        Assertions.assertTrue(res.size() == 19);
    }
    @Test
    void findEmployeesInDevelopmentByDate()
    {
        LocalDate fromDate = LocalDate.of(1999,10,10);
        LocalDate toDate = LocalDate.of(2020,10,10);

        String department = "Development";
        List<EmpByDeptDTO> res = empDao.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res + " " + res.size());
        Assertions.assertTrue(res.size() == 553);
    }

    @Test
    void findEmployeeByDepartmentAndDate3(){

        LocalDate fromDate = LocalDate.of(1000,10,10);
        LocalDate toDate = LocalDate.of(1500,10,10);

        String department = "Sales";
        //LocalDate fromDate = "1990-10-10";
        //String toDate = "2020-10-10";
        List<EmpByDeptDTO> res = empDao.findByDepartmentAndDate(department, fromDate, toDate);
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


        List<EmpByDeptDTO> res = empDao.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);

        Assertions.assertTrue( res.size() == 0 );


    }
    @Test
    void countNumberOfEmployeesThatLeftADepartmentByYear(){
        String year = "2000";
        String department = "Sales";
        int res = empDao.countNumberOfEmployeesLeftDepartmentByYear(department, year);
        System.out.println(res);
        Assertions.assertTrue(res == 11224);

    }

//    @Test
//    void countNumberOfEmployeesLeftDepartmentByYear(){
//        int res = empDao.countNumberOfEmployeesLeftDepartmentByYear();
//        Assertions.assertTrue( res == 11224);
//    }

    @Test
    void findEmployeesByDepartmentByDateYearRange(){
        List<EmpsForDeptsDTO> testlist = deptDao.findNoOfEmployeesForEachDept(
                LocalDate.of(1995,1,1),
                LocalDate.of(2005,12,31)
        );
        System.out.println(testlist);
    }

    @Test
    void findPayGapByTitleByGender(){
        List<SalaryForTitlesDTO> maleSalary = salaryRepo.list("M");
        List<SalaryForTitlesDTO> femaleSalary = salaryRepo.list("F");
        System.out.println(maleSalary);
        System.out.println(femaleSalary);
    }

    @Test
    void test10(){
        Department dept = deptDao.getDepartmentById("d009");
        System.out.println(dept);
    }
}
