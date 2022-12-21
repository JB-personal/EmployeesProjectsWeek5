package com.sparta.jpahibernate.dao.concretes;

import com.sparta.jpahibernate.dao.interfaces.DAO;
import com.sparta.jpahibernate.entities.Employee;
import com.sparta.jpahibernate.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeDAO implements DAO<Employee> {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeDAO(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public void save(Employee employee) {

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
}
