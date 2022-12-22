package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.dto.SalaryForTitlesDTO;
import com.sparta.jpahibernate.entities.Salary;
import com.sparta.jpahibernate.entities.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {
    @Query(
            "SELECT new com.sparta.jpahibernate.dto.SalaryForTitlesDTO(t.id.title, e.gender, AVG(s.salary)) " +
            "FROM Employee e LEFT JOIN Salary s ON e.id = s.id.empNo " +
            "LEFT JOIN Title t ON e.id = t.id.empNo WHERE e.gender = :gender " +
            "GROUP BY t.id.title " +
            "ORDER BY t.id.title "
    )
    List<SalaryForTitlesDTO> list(@Param("gender") String gender);

    @Query(
            value = "SELECT avg(salaries.salary) FROM employees JOIN salaries, departments, dept_emp WHERE " +
                    "employees.emp_no = dept_emp.emp_no AND dept_emp.emp_no = salaries.emp_no AND dept_name = " +
                    ":department AND salaries.from_date > :year'-01-01' AND salaries.to_date < :yearEnd'-01-01'",
            nativeQuery = true )
    List<Double> findAverageSalaryByDepartmentAndGivenDate(@Param("department") String department, @Param("year")
    String year, @Param("yearEnd") String yearEnd);

    @Query(
            value = "SELECT min(salaries.salary) FROM employees JOIN titles, salaries WHERE " +
                    "employees.emp_no = salaries.emp_no AND employees.emp_no = titles.emp_no AND titles.title = :title AND titles.from_date " +
                    "BETWEEN :year'-01-01' AND :yearEnd'-01-01'",
            nativeQuery = true )
    List<Double> findSalaryByTitleWithinGivenYearMin(@Param("title") String department, @Param("year")
    String year, @Param("yearEnd") String yearEnd);

    @Query(
            value = "SELECT max(salaries.salary) FROM employees JOIN titles, salaries WHERE " +
                    "employees.emp_no = salaries.emp_no AND employees.emp_no = titles.emp_no AND titles.title = :title AND titles.from_date " +
                    "BETWEEN :year'-01-01' AND :yearEnd'-01-01'",
            nativeQuery = true )
    List<Double> findSalaryByTitleWithinGivenYearMax(@Param("title") String department, @Param("year")
    String year, @Param("yearEnd") String yearEnd);
}