package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.DeptEmp;
import com.sparta.jpahibernate.entities.DeptEmpId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpId> {

    List<DeptEmpId> findByDeptNo(String deptNo);
}