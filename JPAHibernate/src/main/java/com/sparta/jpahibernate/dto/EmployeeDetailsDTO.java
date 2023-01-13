package com.sparta.jpahibernate.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class EmployeeDetailsDTO {
    private EmployeeDTO employeeDTO;
    private DepartmentDTO departmentDTO;
    private SalaryDTO salaryDTO;
    private TitleDTO titleDTO;
}
