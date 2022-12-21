package com.sparta.jpahibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
@AllArgsConstructor
public class DeptEmpDTO {
    private String deptName;
    private Long employees;

    @Override
    public String toString() {
        return deptName + ": " + employees;
    }
}