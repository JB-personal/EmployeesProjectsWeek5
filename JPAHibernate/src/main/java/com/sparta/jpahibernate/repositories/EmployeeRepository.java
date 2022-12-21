package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.Employee;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    public List<Employee> findByLastName(String lastName);

    //public List<Employee> findByDepartmentAndHireDateAfter(String department, LocalDate hireDate);
    @Query(value = "SELECT employees.emp_no, employees.birth_date, employees.first_name, employees.last_name, employees.gender, employees.hire_date, departments.dept_name " +
            "FROM employees " +
            "JOIN dept_emp, departments " +
            "WHERE employees.emp_no = dept_emp.emp_no " +
            "AND departments.dept_name = :department " +
            "AND dept_emp.from_date > :fromDate " +
            "AND dept_emp.to_date < :toDate", nativeQuery = true )
    public List<Employee> findByDepartmentAndDate(@Param("department") String department, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate );

    @Query(value = "SELECT count(*) FROM employees.employees " +
            "JOIN dept_emp, departments " +
            "WHERE employees.emp_no = dept_emp.emp_no " +
            "AND departments.dept_name = :department " +
            "AND dept_emp.to_date " +
            "BETWEEN :year'-01-01' AND :year'-12-31'", nativeQuery = true )
    // public int countNumberOfEmployeesLeftDepartmentByYear(@Param("department") String department, @Param("yearStart") LocalDate yearStart, @Param("yearEnd") LocalDate yearEnd);
    public int countNumberOfEmployeesLeftDepartmentByYear(@Param("department") String department, @Param("year") String year);

    // TEMP ================================================================
    @Query(value = "SELECT count(*) FROM employees.employees " +
            "JOIN dept_emp, departments " +
            "WHERE employees.emp_no = dept_emp.emp_no " +
            "AND departments.dept_name = :department " +
            "AND dept_emp.to_date " +
            "BETWEEN :sYear" + " AND :eYear", nativeQuery = true )
    // public int countNumberOfEmployeesLeftDepartmentByYear(@Param("department") String department, @Param("yearStart") LocalDate yearStart, @Param("yearEnd") LocalDate yearEnd);
    public int countNumberOfEmployeesLeftDepartmentByYear2(@Param("department") String department, @Param("sYear") LocalDate sYear, @Param("eYear") LocalDate eYear);
}