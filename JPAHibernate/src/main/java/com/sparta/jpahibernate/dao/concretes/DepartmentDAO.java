package com.sparta.jpahibernate.dao.concretes;

import com.sparta.jpahibernate.dao.interfaces.IDepartment;
import com.sparta.jpahibernate.dto.DepartmentDTO;
import com.sparta.jpahibernate.entities.Department;
import com.sparta.jpahibernate.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentDAO implements IDepartment {

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentDAO(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAll() {
        return null;
    }

    @Override
    public void save(Department department) {

    }

    public void update(String id, String[] params) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            department.get().setId(params[0]);
            department.get().setDeptName(params[1]);
        }
    }

    @Override
    public void delete(String id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public List<DepartmentDTO> findNoOfEmployeesForEachDept(LocalDate a, LocalDate b) {
        return departmentRepository.list(a,b);
    }
}
