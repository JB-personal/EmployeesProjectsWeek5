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
    
        // get salary from employee id
//    int getSalaryFromEmployeeId(int id);

}