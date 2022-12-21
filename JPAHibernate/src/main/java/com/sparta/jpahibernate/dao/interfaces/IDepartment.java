package com.sparta.jpahibernate.dao.interfaces;

import com.sparta.jpahibernate.dto.DeptEmpDTO;
import com.sparta.jpahibernate.entities.Department;

import java.time.LocalDate;
import java.util.List;

public interface IDepartment extends DAO<Department> {
    List<DeptEmpDTO> findNoOfEmployeesForEachDept(LocalDate a, LocalDate b);
}
