package com.sparta.jpahibernate.dao.concretes;

import com.sparta.jpahibernate.dao.interfaces.DAO;
import com.sparta.jpahibernate.dao.interfaces.EmployeeDAO;
import com.sparta.jpahibernate.dto.EmployeeDTO;
import com.sparta.jpahibernate.entities.Employee;
import com.sparta.jpahibernate.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeDAOImpl implements EmployeeDAO {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeDAOImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(EmployeeDTO dto) {

    }

    @Override
    public List<EmployeeDTO> findAll() {
        return null;
    }

    @Override
    public void save(EmployeeDTO employeeDTO) {

    }

    public List<Employee> findByLastName(String lastName) {
        return employeeRepository.findByLastName(lastName);
    }

    public List<Employee> findByDepartmentAndDate(String department, LocalDate fromDate, LocalDate toDate) {
        return employeeRepository.findByDepartmentAndDate(department, fromDate, toDate);
    }

    public int countNumberOfEmployeesLeftDepartmentByYear(String dept, String year) {
        return employeeRepository.countNumberOfEmployeesLeftDepartmentByYear(dept, year);
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }
}
