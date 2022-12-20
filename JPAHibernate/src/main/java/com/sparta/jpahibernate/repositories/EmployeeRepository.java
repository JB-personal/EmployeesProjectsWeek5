package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.DeptEmp;
import com.sparta.jpahibernate.entities.DeptEmpId;
import com.sparta.jpahibernate.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


}