package com.sparta.jpahibernate.dao.concretes;

import com.sparta.jpahibernate.dao.interfaces.DepartmentDAO;
import com.sparta.jpahibernate.dto.DepartmentDTO;
import com.sparta.jpahibernate.dto.EmpsForDeptsDTO;
import com.sparta.jpahibernate.entities.Department;
import com.sparta.jpahibernate.repositories.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentDAOImpl implements DepartmentDAO {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentDAOImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public long count() {
        return departmentRepository.count();
    }

    @Override
    public void delete(DepartmentDTO dto) {
        Optional<Department> optDept = departmentRepository.findById(dto.getId());
        optDept.ifPresent(departmentRepository::delete);
    }

    @Override
    public boolean existsById(String id) {
        Optional<Department> optDept = departmentRepository.findById(id);
        return optDept.isPresent();
    }

    @Override
    public List<DepartmentDTO> findAll() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOs = new ArrayList<>();
        for (Department department : departments) {
            departmentDTOs.add(new DepartmentDTO(department.getId(), department.getDeptName()));
        }
        return departmentDTOs;
    }

    @Override
    public Optional<DepartmentDTO> findById(String id) {
        Optional<Department> optDept = departmentRepository.findById(id);
        if(optDept.isPresent()){
            Department presentDept = optDept.get();
            DepartmentDTO dept = new DepartmentDTO(
                    presentDept.getId(),
                    presentDept.getDeptName());
            return Optional.of(dept);
        }
        return Optional.empty();
    }

    @Override
    public void save(DepartmentDTO dto) {
        Department dept = new Department();
        dept.setId(dto.getId());
        dept.setDeptName(dto.getDeptName());
        departmentRepository.save(dept);
    }

    @Override
    public void update(String id, String[] params) {
        Optional<Department> optDept = departmentRepository.findById(id);
        if (optDept.isPresent()) {
            Department dept = optDept.get();
            dept.setId(params[0]);
            dept.setDeptName(params[1]);
            departmentRepository.save(dept);
        }
    }

    @Override
    public void deleteById(String id) {
        Optional<Department> optDept = departmentRepository.findById(id);
        if(optDept.isPresent()){
            departmentRepository.deleteById(id);
        }
    }

    @Override
    public List<EmpsForDeptsDTO> findNoOfEmployeesForEachDept(LocalDate fromDate, LocalDate toDate) {
        return departmentRepository.findNoOfEmployeesForEachDept(fromDate, toDate);
    }

    @Override
    public DepartmentDTO findDepartmentByDeptName(String deptName) {
        return new DepartmentDTO(departmentRepository.findDepartmentByDeptName(deptName).getId(),
                departmentRepository.findDepartmentByDeptName(deptName).getDeptName());
    }
}
