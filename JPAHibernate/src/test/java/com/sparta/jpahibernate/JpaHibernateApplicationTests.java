package com.sparta.jpahibernate;

import com.sparta.jpahibernate.dao.concretes.*;
import com.sparta.jpahibernate.dto.DepartmentDTO;
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
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional

class JpaHibernateApplicationTests {

    @Autowired
    private EmployeeDAOImpl empDao;

    @Autowired
    private DepartmentDAOImpl deptDao;

    @Autowired
    SalaryRepository salaryRepo;

    @Autowired
    TitleRepository titleRepo;
    @Autowired
    private DepartmentRepository departmentRepository;

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
    void findEmployeeByDepartmentAndDate() {

        LocalDate fromDate = LocalDate.of(1998, 10, 10);
        LocalDate toDate = LocalDate.of(2020, 10, 10);

        String department = "Sales";
        //LocalDate fromDate = "1990-10-10";
        //String toDate = "2020-10-10";
        List<Employee> res = empDao.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);
        Assertions.assertTrue(res.size() == 8830);
    }

    @Test
    void findEmployeeByDepartmentAndDateAfter2000() {

        LocalDate fromDate = LocalDate.of(2000, 10, 10);
        LocalDate toDate = LocalDate.of(2020, 10, 10);

        String department = "Sales";
        List<Employee> res = empDao.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);
        Assertions.assertTrue(res.size() == 269);
    }

    @Test
    void findEmployeesInDevelopmentByDate() {
        LocalDate fromDate = LocalDate.of(1999, 10, 10);
        LocalDate toDate = LocalDate.of(2020, 10, 10);

        String department = "Development";
        List<Employee> res = empDao.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);
        Assertions.assertTrue(res.size() == 2513);
    }

    @Test
    void findEmployeeByDepartmentAndDate3() {

        LocalDate fromDate = LocalDate.of(1000, 10, 10);
        LocalDate toDate = LocalDate.of(1500, 10, 10);

        String department = "Sales";
        //LocalDate fromDate = "1990-10-10";
        //String toDate = "2020-10-10";
        List<Employee> res = empDao.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);
        Assertions.assertTrue(res.isEmpty());
        //Assertions.assertTrue();
    }

    // invalid department
    @Test
    void findEmployeeByDepartmentAndDate4() {

        LocalDate fromDate = LocalDate.of(1999, 10, 10);
        LocalDate toDate = LocalDate.of(2012, 10, 10);

        String department = "Snakes";


        List<Employee> res = empDao.findByDepartmentAndDate(department, fromDate, toDate);
        System.out.println(res);

        Assertions.assertTrue(res == null);


    }

    @Test
    void countNumberOfEmployeesThatLeftADepartmentByYear() {
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
    void findEmployeesByDepartmentByDateYearRange() {
        List<EmpsForDeptsDTO> testlist = deptDao.findNoOfEmployeesForEachDept(
                LocalDate.of(1995, 1, 1),
                LocalDate.of(2005, 12, 31)
        );
        System.out.println(testlist);
    }

    @Test
    void findPayGapByTitleByGender() {
        List<SalaryForTitlesDTO> maleSalary = salaryRepo.list("M");
        List<SalaryForTitlesDTO> femaleSalary = salaryRepo.list("F");
        String result = "";
        double payGap = 0.0;
        for (int i = 0; i < maleSalary.size(); i++) {
            if (maleSalary.get(i).getAvgSalary() > femaleSalary.get(i).getAvgSalary()) {
                payGap = ((maleSalary.get(i).getAvgSalary() - femaleSalary.get(i).getAvgSalary()) * 100) / femaleSalary.get(i).getAvgSalary();
                result = maleSalary.get(i).getTitle() + " has a pay gap of " +
                        String.format("%.3f", payGap)
                        + "% favouring males";
            } else {
                payGap = ((femaleSalary.get(i).getAvgSalary() - maleSalary.get(i).getAvgSalary()) * 100) / maleSalary.get(i).getAvgSalary();
                result = femaleSalary.get(i).getTitle() + " has a pay gap of " +
                        String.format("%.3f", payGap)
                        + " favouring females";
            }
            System.out.println(result);
        }
    }

    @Test
    void departmentCount() {

    }

    @Test
    @Rollback
    void departmentDelete() {

    }

    @Test
    void departmentFindAll() {
        Assertions.assertFalse(deptDao.findAll().isEmpty());
    }

    @Test
    @Rollback
    void departmentUpdate() {

    }

    @Test
    @Rollback
    void departmentSave() {
        deptDao.save(new DepartmentDTO(
                "d010", "Test"
        ));
        Assertions.assertEquals(
                departmentRepository.findDepartmentByDeptName("Test").getDeptName(),
                "Test");
    }



    @Test
    @Rollback
    void departmentDeleteById() {

    }

    @Test
    void a() {

    }

}

