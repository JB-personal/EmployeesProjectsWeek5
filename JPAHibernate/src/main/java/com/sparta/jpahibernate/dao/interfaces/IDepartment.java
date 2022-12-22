package com.sparta.jpahibernate.dao.interfaces;

import com.sparta.jpahibernate.dto.EmpsForDeptsDTO;
import com.sparta.jpahibernate.dto.*;
import com.sparta.jpahibernate.entities.Department;

import java.time.LocalDate;
import java.util.List;

public interface IDepartment extends DAO<Department> {

    void delete(String id);

    public void update(String id, String[] params);

    public Department getDepartmentById(String id);

    List<EmpsForDeptsDTO> findNoOfEmployeesForEachDept(LocalDate a, LocalDate b);

    List<MgrsForDeptsDTO> findManagersInDepartmentOnGivenYear();
}
