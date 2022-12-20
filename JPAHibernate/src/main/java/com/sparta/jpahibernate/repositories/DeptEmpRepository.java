package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.DeptEmp;
import com.sparta.jpahibernate.entities.DeptEmpId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

}