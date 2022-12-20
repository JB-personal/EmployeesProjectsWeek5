package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.Salary;
import com.sparta.jpahibernate.entities.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {

    // get salary from employee id
    int getSalaryFromEmployeeId(int id);
}