package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.Salary;
import com.sparta.jpahibernate.entities.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {
}