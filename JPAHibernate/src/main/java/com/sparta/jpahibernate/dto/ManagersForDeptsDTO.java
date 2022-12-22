package com.sparta.jpahibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ManagersForDeptsDTO {
    private String firstName;
    private String lastName;
    private String deptName;

}
