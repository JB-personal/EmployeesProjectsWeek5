package com.sparta.jpahibernate.dao.concretes;

import com.sparta.jpahibernate.dao.interfaces.EmployeeDAO;
import com.sparta.jpahibernate.dto.EmployeeDTO;
import com.sparta.jpahibernate.dto.EmpsByDeptsDTO;
import com.sparta.jpahibernate.dto.SalaryForTitlesDTO;
import com.sparta.jpahibernate.entities.Employee;
import com.sparta.jpahibernate.repositories.EmployeeRepository;
import com.sparta.jpahibernate.repositories.SalaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeDAOImpl implements EmployeeDAO {
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private final SalaryRepository salaryRepository;

    @Autowired
    public EmployeeDAOImpl(EmployeeRepository employeeRepository,
                           SalaryRepository salaryRepository) {
        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
    }

    @Override
    public long count() {
        return employeeRepository.count();
    }

    @Override
    public void delete(EmployeeDTO dto) {
        Optional<Employee> optEmp = employeeRepository.findById(dto.getId());
        optEmp.ifPresent(employeeRepository::delete);
    }

    @Override
    public List<EmployeeDTO> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDTOs.add(new EmployeeDTO(employee.getId(), employee.getBirthDate(), employee.getFirstName(),
                    employee.getLastName(), employee.getGender(), employee.getHireDate()));
        }
        return employeeDTOs;
    }


    @Override
    public void save(EmployeeDTO dto) {
        Employee emp = new Employee();
        emp.setId(dto.getId());
        emp.setFirstName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setGender(dto.getGender());
        emp.setBirthDate(dto.getBirthDate());
        emp.setHireDate(dto.getHireDate());
        employeeRepository.save(emp);
    }

    public List<EmployeeDTO> findByLastName(String lastName) {
        List<Employee> employees = employeeRepository.findByLastName(lastName);
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for(Employee employee : employees) {
            employeeDTOs.add(new EmployeeDTO(employee.getId(), employee.getBirthDate(), employee.getFirstName(),
                    employee.getLastName(), employee.getGender(), employee.getHireDate()));
        }
        return employeeDTOs;
    }

    public List<EmpsByDeptsDTO> findByDepartmentAndDate(String department, LocalDate fromDate, LocalDate toDate) {
        return employeeRepository.findByDepartmentAndDate(department, fromDate, toDate);
    }

    public int countNumberOfEmployeesLeftDepartmentByYear(String dept, String year) {
        return employeeRepository.countNumberOfEmployeesLeftDepartmentByYear(dept, year);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Employee> optEmp = employeeRepository.findById(id);
        if(optEmp.isPresent()){
            employeeRepository.deleteById(id);
        }
    }

    @Override
    public boolean existsById(Integer id) {
        Optional<Employee> optEmp = employeeRepository.findById(id);
        return optEmp.isPresent();
    }

    @Override
    public void getPayGapSalary() {
        List<SalaryForTitlesDTO> maleSalary = salaryRepository.findAvgSalaryByGender("M");
        List<SalaryForTitlesDTO> femaleSalary = salaryRepository.findAvgSalaryByGender("F");
        String result;
        double payGap;
        for (int i = 0; i < maleSalary.size(); i++) {
            if (maleSalary.get(i).getAvgSalary() > femaleSalary.get(i).getAvgSalary()) {
                payGap = ((maleSalary.get(i).getAvgSalary() - femaleSalary.get(i).getAvgSalary()) * 100)
                        / femaleSalary.get(i).getAvgSalary();
                result = maleSalary.get(i).getTitle() + " has a pay gap of " +
                        String.format("%.3f", payGap)
                        + "% favouring males";
            } else {
                payGap = ((femaleSalary.get(i).getAvgSalary() - maleSalary.get(i).getAvgSalary()) * 100)
                        / maleSalary.get(i).getAvgSalary();
                result = femaleSalary.get(i).getTitle() + " has a pay gap of " +
                        String.format("%.3f", payGap)
                        + " favouring females";
            }
            System.out.println(result);
        }
    }

    @Override
    public void update(Integer id, String[] params) {
        Optional<Employee> optEmp = employeeRepository.findById(id);
        if (optEmp.isPresent()) {
            Employee emp = optEmp.get();
            emp.setId(id);
            emp.setBirthDate(Date.valueOf(params[1]).toLocalDate());
            emp.setFirstName(params[2]);
            emp.setLastName(params[3]);
            emp.setGender(params[4]);
            emp.setHireDate(Date.valueOf(params[5]).toLocalDate());
            employeeRepository.save(emp);
        }
    }

    @Override
    public Optional<EmployeeDTO> findById(Integer id) {
        Optional<Employee> optEmp = employeeRepository.findById(id);
        if(optEmp.isPresent()){
            Employee presentEmp = optEmp.get();
            EmployeeDTO emp = new EmployeeDTO(presentEmp.getId(), presentEmp.getBirthDate(),
                    presentEmp.getFirstName(), presentEmp.getLastName(),
                    presentEmp.getGender(), presentEmp.getHireDate());
            return Optional.of(emp);
        }
        return Optional.empty();
    }

    @Override
    public List<Double> findAverageSalaryByDepartmentAndGivenDate(String department, String year, String yearEnd) {
        return salaryRepository.findAverageSalaryByDepartmentAndGivenDate(department, year, yearEnd);
    }

    @Override
    public List<Double> findSalaryByTitleWithinGivenYearMax(String department, String year, String yearEnd) {
        return salaryRepository.findSalaryByTitleWithinGivenYearMax(department, year, yearEnd);
    }

    @Override
    public List<Double> findSalaryByTitleWithinGivenYearMin(String department, String year, String yearEnd) {
        return salaryRepository.findSalaryByTitleWithinGivenYearMin(department, year, yearEnd);
    }
}
