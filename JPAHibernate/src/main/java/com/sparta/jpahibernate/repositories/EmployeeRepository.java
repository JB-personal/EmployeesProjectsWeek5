package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.dto.EmpsByDeptsDTO;
import com.sparta.jpahibernate.dto.ManagersForDeptsDTO;
import com.sparta.jpahibernate.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByLastName(String lastName);

    @Query("SELECT new com.sparta.jpahibernate.dto.EmpsByDeptsDTO(E.id, E.birthDate, E.firstName, E.lastName, E.gender, E.hireDate, D.deptName) " +
            "FROM Employee E " +
            "JOIN DeptEmp DE ON E.id = DE.empNo.id JOIN Department D ON D.id = DE.deptNo.id " +
            "WHERE D.deptName = :department " +
            "AND DE.fromDate > :fromDate " +
            "AND DE.toDate < :toDate")
    List<EmpsByDeptsDTO> findByDepartmentAndDate(@Param("department") String department, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate );

    @Query(value = "SELECT count(*) FROM employees.employees " +
            "JOIN dept_emp, departments " +
            "WHERE employees.emp_no = dept_emp.emp_no " +
            "AND departments.dept_name = :department " +
            "AND dept_emp.to_date " +
            "BETWEEN :year'-01-01' AND :year'-12-31'", nativeQuery = true )
    int countNumberOfEmployeesLeftDepartmentByYear(@Param("department") String department, @Param("year") String year);

    @Query(value = "SELECT new com.sparta.jpahibernate.dto.ManagersForDeptsDTO(e.firstName, e.lastName, d.deptName) " +
            "FROM DeptManager m " +
            "JOIN m.deptNo d " +
            "JOIN m.empNo e " +
            "WHERE d.deptName = :department " +
            "AND (m.fromDate >= :fromDate OR m.toDate <= :toDate) ")
    List<ManagersForDeptsDTO> findManagersInDepartmentOnGivenYear(
            @Param("department") String department,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate );


}