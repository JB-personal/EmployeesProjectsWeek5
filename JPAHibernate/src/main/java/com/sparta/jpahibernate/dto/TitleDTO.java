package com.sparta.jpahibernate.dto;

import com.sparta.jpahibernate.entities.Employee;
import com.sparta.jpahibernate.entities.TitleId;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TitleDTO {
    private TitleId id;
    private Employee empNo;
    private LocalDate toDate;
}
