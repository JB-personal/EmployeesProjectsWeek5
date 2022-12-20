package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}