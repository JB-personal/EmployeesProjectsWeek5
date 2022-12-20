package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.DeptEmp;
import com.sparta.jpahibernate.entities.DeptEmpId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpId> {
}