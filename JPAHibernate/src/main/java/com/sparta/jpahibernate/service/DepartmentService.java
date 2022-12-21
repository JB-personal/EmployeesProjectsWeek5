package com.sparta.jpahibernate.service;

import com.sparta.jpahibernate.dao.concretes.DepartmentDAO;
import com.sparta.jpahibernate.dto.DepartmentDTO;
import com.sparta.jpahibernate.entities.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDAO departmentDAO;

    // create dto from entity
//    public DepartmentDTO getDepartmentById(String id){
//        Department department = departmentDAO.getDepartmentById(id);
//        DepartmentDTO departmentDTO = new DepartmentDTO();
//        departmentDTO.setId(department.getId());
//        departmentDTO.setDeptName(department.getDeptName());
//        return departmentDTO;
//    }

}
