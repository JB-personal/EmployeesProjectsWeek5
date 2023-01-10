package com.sparta.jpahibernate.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateHolder {
    private LocalDate fromDate;
    private LocalDate toDate;

}

