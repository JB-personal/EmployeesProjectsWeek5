package com.sparta.jpahibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
@AllArgsConstructor
public class SalaryDTO {
    private String title;
    private String gender;
    private Double salary;

    @Override
    public String toString() {
        return title + ", " + gender + ", " + salary;
    }
}
