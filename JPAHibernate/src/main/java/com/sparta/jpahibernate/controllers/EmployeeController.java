package com.sparta.jpahibernate.controllers;

import com.sparta.jpahibernate.dao.concretes.DepartmentDAOImpl;
import com.sparta.jpahibernate.dao.concretes.EmployeeDAOImpl;
import com.sparta.jpahibernate.dao.concretes.SalaryDAOImpl;
import com.sparta.jpahibernate.dao.concretes.TitleDAOImpl;
import com.sparta.jpahibernate.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/web/employee")
@SessionAttributes("employee")
public class EmployeeController {

    @Autowired
    private EmployeeDAOImpl employeeDAO;

    @Autowired
    private DepartmentDAOImpl departmentDAO;

    @Autowired
    private SalaryDAOImpl salaryDAO;

    @Autowired
    private TitleDAOImpl titleDAO;


    @GetMapping("/search")
    public String findEmpById(Model model) {
        EmployeeDTO employee = new EmployeeDTO();
        model.addAttribute("employee", employee);
        return "employeeDisplay";
    }

    @PostMapping("/search/success")
    public String findEmpByIdSuccess(@ModelAttribute("employee") EmployeeDTO employee, Model model) {
        employee = employeeDAO.findById(employee.getId()).orElse(null);
        model.addAttribute("employee", employee);
        return "employeeDisplaySuccess";
    }

    @GetMapping("/all")
    public String getAllEmployees(Model model) {
        List<EmployeeDTO> employees = employeeDAO.findAll();
        model.addAttribute("employees", employees);
        return "employeeDisplayAll";
    }

    @GetMapping ("/new")
    public String createEmployee(Model model){
        EmployeeDTO employee = new EmployeeDTO();
        model.addAttribute("employee", employee);
        return "employeeCreate";
    }

    @PostMapping("/new/success")
    public String createEmployeeSuccess(@ModelAttribute("employee")EmployeeDTO employee, Model model){
        employeeDAO.save(employee);

        model.addAttribute("employee", employee);
        return "employeeCreateSuccess";
    }

    @GetMapping("/delete")
    public String deleteEmployee(Model model){
        model.addAttribute("employee", new EmployeeDTO());
        return "employeeDelete";
    }

    @PostMapping("/delete/success")
    public String deleteEmployeeSuccess(@ModelAttribute("employee")EmployeeDTO employee, Model model){
        if(employeeDAO.findById(employee.getId()).isEmpty()) {
            employee = null;
        } else {
            employeeDAO.delete(employee);
        }
        model.addAttribute("employee", employee);
        return "employeeDeleteSuccess";
    }

    @GetMapping("/update")
    public String updateEmployee(Model model) {
        EmployeeDTO employee = new EmployeeDTO();
        model.addAttribute("employee", employee);
        return "employeeUpdate";
    }

    @PostMapping("/update/success")
    public String updateEmployeeSuccess(@ModelAttribute("employee") EmployeeDTO employee, Model model) {
        employee = employeeDAO.findById(employee.getId())
                .orElse(null);
        employeeDAO.save(employee);
        model.addAttribute("employee", employee);
        return "employeeUpdateSuccess";
    }

    @GetMapping("/countbydepartment")
    public String countNumberOfEmployeesLeftDepartmentByYear(Model model){
        String dept = "";
        String year = "";
        model.addAttribute("dept", dept);
        model.addAttribute("year", year);
        return "employeeCountByDepartment";
    }

    @PostMapping("/countbydepartment/success")
    public String countNumberOfEmployeesLeftDepartmentByYearSuccess(@ModelAttribute("dept")String dept, @ModelAttribute("year")String year, Model model){
        int result = employeeDAO.countNumberOfEmployeesLeftDepartmentByYear(dept,
                year);
        model.addAttribute("result", result);
        return "employeeCountByDepartmentSuccess";
    }


    @GetMapping("/searchbylastname")
    public String findByLastName(Model model){
        EmployeeDTO employee = new EmployeeDTO();
        model.addAttribute("employee", employee);
        return "employeeDisplayByLastName";
    }

    @PostMapping("/searchbylastname/success")
    public String findByLastNameSuccess(@ModelAttribute("lastName")String lastName, Model model) {
        List<EmployeeDTO> employees = employeeDAO.findByLastName(lastName);
        model.addAttribute("employees", employees);
        return "employeeDisplayByLastNameSuccess";
    }

    @GetMapping("/findsalarygap")
    public String findSalaryGapSuccess(Model model){
        model.addAttribute("paygap", employeeDAO.getPayGapSalary());
        return "displayPayGap";
    }

    @GetMapping ("/test/create")
    public String createEmployeeTest(Model model){
        EmployeeDetailsDTO employee = new EmployeeDetailsDTO();
        model.addAttribute("employee", employee);
        return "testReview";
    }

    @PostMapping("/test/create")
    public String createEmployeeSuccessTest(@ModelAttribute("employee")EmployeeDetailsDTO employee, Model model){

        model.addAttribute("employee", employee);
        return "testReview";
    }

    @GetMapping("/test/review")
    public String createEmployeeReview(@ModelAttribute("employee")EmployeeDetailsDTO employee){
        return "testReview";
    }

    @PostMapping("/test/submit")
    public String createEmployeeSubmit(@ModelAttribute("employee")EmployeeDetailsDTO employee, Model model){
        employeeDAO.save(employee.getEmployeeDTO());
        departmentDAO.save(employee.getDepartmentDTO());
        salaryDAO.save(employee.getSalaryDTO());
        model.addAttribute("employee", employee);
        return "testCreateSuccess";
    }
}
