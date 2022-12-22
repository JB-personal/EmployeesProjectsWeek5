package com.sparta.jpahibernate.dao.concretes;

import com.sparta.jpahibernate.dao.interfaces.IDepartment;
import com.sparta.jpahibernate.dto.EmpsForDeptsDTO;
import com.sparta.jpahibernate.entities.Department;
import com.sparta.jpahibernate.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentDAO implements IDepartment {

    //DepartmentService service = new DepartmentService();

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
    public List<EmpsForDeptsDTO> findNoOfEmployeesForEachDept(LocalDate fromDate, LocalDate toDate) {
        return departmentRepository.findNoOfEmployeesForEachDept(fromDate, toDate);
    }

    @Override
    public Department getDepartmentById(String id) {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()) {
            Department found = department.get();
            return found;
        }
        return null;
    }

    @Override
}
