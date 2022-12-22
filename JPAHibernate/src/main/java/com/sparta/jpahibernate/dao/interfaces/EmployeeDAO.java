package com.sparta.jpahibernate.dao.interfaces;

import com.sparta.jpahibernate.dto.EmployeeDTO;

public interface EmployeeDAO extends DAO<EmployeeDTO>{

    void deleteById(Integer id);

    boolean existsById(Integer id);
}
