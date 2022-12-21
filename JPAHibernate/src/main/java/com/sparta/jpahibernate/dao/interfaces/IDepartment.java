package com.sparta.jpahibernate.dao.interfaces;

import com.sparta.jpahibernate.dto.DepartmentDTO;
import com.sparta.jpahibernate.entities.Department;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IDepartment extends DAO<Department> {

    void delete(String id);

    public void update(String id, String[] params);

    List<DepartmentDTO> findNoOfEmployeesForEachDept(LocalDate a, LocalDate b);
}
