package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.dto.DeptEmpDTO;
import com.sparta.jpahibernate.entities.DeptEmp;
import com.sparta.jpahibernate.entities.DeptEmpId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpId> {
    @Query(
            value = "SELECT d.dept_name FROM dept_emp de JOIN departments d ON de.dept_no = d.dept_no GROUP BY de.dept_no ORDER BY de.dept_no",
            nativeQuery = true
            )
    List<String> findDeptName();

    @Query(
            value = "SELECT COUNT(de.emp_no) FROM dept_emp de JOIN departments d ON de.dept_no = d.dept_no WHERE " +
                    "from_date > ? AND to_date < ? GROUP BY de.dept_no ORDER BY de.dept_no",
            nativeQuery = true
    )
    List<Integer> findNoOfEmployeesForEachDept(LocalDate fromDate, LocalDate toDate);

//    @Query(
//            value = "SELECT new com.sparta.jpahibernate.dto.DeptEmpDTO(d.deptName, COUNT(de.empNo)) " +
//                    "FROM DeptEmp de JOIN Department d ON de.deptNo = d.id WHERE " +
//                    "de.fromDate > :fromDate AND de.toDate < :toDate " +
//                    "GROUP BY de.deptNo " +
//                    "ORDER BY d.id"
//    )
//    List<DeptEmpDTO> list(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

//    @Query(value = "SELECT d.dept_name as Department, COUNT(de.emp_no) as Employees FROM dept_emp de " +
//            "JOIN departments d ON de.dept_no = d.dept_no WHERE from_date > ? AND to_date < ? GROUP BY de.dept_no",
//            nativeQuery = true)
//    List<String> getDeptEmp(LocalDate fromDate, LocalDate toDate);
}