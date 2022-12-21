package com.sparta.jpahibernate.dao.concretes;

import com.sparta.jpahibernate.dao.interfaces.DAO;
import com.sparta.jpahibernate.entities.Employee;

import java.util.List;
import java.util.Optional;

public class EmployeeDAO implements DAO<Employee> {

    @Override
    public Optional<Employee> get(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public void save(Employee employee) {

    }

    @Override
    public void update(Employee employee, String[] params) {

    }

    @Override
    public void delete(Integer id) {

    }
}
