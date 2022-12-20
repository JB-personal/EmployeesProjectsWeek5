package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.Salary;
import com.sparta.jpahibernate.entities.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {
    @Query(
            value = "SELECT AVG(S.salary) FROM employees E LEFT JOIN salaries S" +
                    " ON E.emp_no = S.emp_no LEFT JOIN titles T ON E.emp_no = T.emp_no WHERE E.gender = ? " +
                    "GROUP BY T.title ORDER BY T.title", nativeQuery = true
    )
    List<Double> findAvgSalary(String gender);
}