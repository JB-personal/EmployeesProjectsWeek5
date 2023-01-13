package com.sparta.jpahibernate.dao.concretes;

import com.sparta.jpahibernate.dao.interfaces.SalaryDAO;
import com.sparta.jpahibernate.dto.EmployeeDTO;
import com.sparta.jpahibernate.dto.SalaryDTO;
import com.sparta.jpahibernate.entities.Employee;
import com.sparta.jpahibernate.entities.Salary;
import com.sparta.jpahibernate.repositories.SalaryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SalaryDAOImpl implements SalaryDAO {

    @Autowired
    private final SalaryRepository salaryRepository;

    public SalaryDAOImpl(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(SalaryDTO dto) {

    }

    @Override
    public List<SalaryDTO> findAll() {
        return null;
    }

    @Override
    public void save(SalaryDTO dto) {
        Salary salary = new Salary();
        salary.setId(dto.getId());
        salary.setEmpNo(dto.getEmpNo());
        salary.setSalary(dto.getSalary());
        salary.setToDate(dto.getToDate());
        salaryRepository.save(salary);
    }
}
