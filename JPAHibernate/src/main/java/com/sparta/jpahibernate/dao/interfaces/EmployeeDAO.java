package com.sparta.jpahibernate.dao.interfaces;

import com.sparta.jpahibernate.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO extends DAO<EmployeeDTO>{

    void deleteById(Integer id);

    boolean existsById(Integer id);

    String getPayGapSalary();

    void update(Integer id, String[] params);

    Optional<EmployeeDTO> findById(Integer id);

    List<Double> findAverageSalaryByDepartmentAndGivenDate(String department, String year, String yearEnd);

    List<Double> findSalaryByTitleWithinGivenYearMax(String department, String year, String yearEnd);

    List<Double> findSalaryByTitleWithinGivenYearMin(String department, String year, String yearEnd);
}
