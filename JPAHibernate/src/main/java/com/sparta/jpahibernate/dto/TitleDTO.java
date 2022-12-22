package com.sparta.jpahibernate.dto;

import com.sparta.jpahibernate.entities.Employee;
import com.sparta.jpahibernate.entities.TitleId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TitleDTO {
    private TitleId id;
    private Employee empNo;
    private LocalDate toDate;
}
