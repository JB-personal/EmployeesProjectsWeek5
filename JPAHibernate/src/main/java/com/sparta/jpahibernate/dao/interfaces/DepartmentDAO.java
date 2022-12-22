package com.sparta.jpahibernate.dao.interfaces;

import com.sparta.jpahibernate.dto.DepartmentDTO;
import com.sparta.jpahibernate.dto.EmpsForDeptsDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DepartmentDAO extends DAO<DepartmentDTO> {
    void deleteById(String id);

    Optional<DepartmentDTO> findById(String id);

    boolean existsById(String id);

    void update(String id, String[] params);

    List<EmpsForDeptsDTO> findNoOfEmployeesForEachDept(LocalDate fromDate, LocalDate toDate);

    DepartmentDTO findDepartmentByDeptName(String deptName);

}
