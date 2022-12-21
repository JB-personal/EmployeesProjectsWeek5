package com.sparta.jpahibernate.dao.concretes;

import com.sparta.jpahibernate.dao.interfaces.IDepartment;
import com.sparta.jpahibernate.dto.DeptEmpDTO;
import com.sparta.jpahibernate.entities.Department;
import com.sparta.jpahibernate.repositories.DeptEmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentDAO implements IDepartment {

    final DeptEmpRepository deptEmpRepo;

    public DepartmentDAO(DeptEmpRepository deptEmpRepo) {
        this.deptEmpRepo = deptEmpRepo;
    }

    @Override
    public Optional<Department> get(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Department> getAll() {
        return null;
    }

    @Override
    public void save(Department department) {

    }

    @Override
    public void update(Department department, String[] params) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<DeptEmpDTO> findNoOfEmployeesForEachDept(LocalDate a, LocalDate b) {
        return null;
    }
}
