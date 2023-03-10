package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.dto.EmpsForDeptsDTO;
import com.sparta.jpahibernate.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface DepartmentRepository extends JpaRepository<Department, String> {
    @Query(
            value = "SELECT new com.sparta.jpahibernate.dto.EmpsForDeptsDTO(d.deptName, COUNT(de.empNo)) " +
                    "FROM DeptEmp de JOIN Department d ON de.deptNo = d.id WHERE " +
                    "de.fromDate > :fromDate AND de.toDate < :toDate " +
                    "GROUP BY de.deptNo " +
                    "ORDER BY d.id"
    )
    List<EmpsForDeptsDTO> findNoOfEmployeesForEachDept(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    Department findDepartmentByDeptName(String deptName);


}