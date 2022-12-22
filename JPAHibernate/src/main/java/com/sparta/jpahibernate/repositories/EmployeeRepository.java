package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.dto.EmpByDeptDTO;
import com.sparta.jpahibernate.dto.EmployeeDTO;
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
    List<Employee> findByLastName(String lastName);

    @Query("SELECT new com.sparta.jpahibernate.dto.EmpByDeptDTO(E.id, E.birthDate, E.firstName, E.lastName, E.gender, E.hireDate, D.deptName) " +
            "FROM Employee E " +
            "JOIN DeptEmp DE ON E.id = DE.empNo.id " +
            "JOIN Department D ON DE.deptNo = D.id " +
            "WHERE D.deptName = :department " +
            "AND DE.fromDate > :fromDate " +
            "AND DE.toDate < :toDate")
    List<EmpByDeptDTO> findByDepartmentAndDate(@Param("department") String department, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate );

    @Query(value = "SELECT count(*) FROM employees.employees " +
            "JOIN dept_emp, departments " +
            "WHERE employees.emp_no = dept_emp.emp_no " +
            "AND departments.dept_name = :department " +
            "AND dept_emp.to_date " +
            "BETWEEN :year'-01-01' AND :year'-12-31'", nativeQuery = true )
    int countNumberOfEmployeesLeftDepartmentByYear(@Param("department") String department, @Param("year") String year);
}