package com.sparta.jpahibernate;

import com.sparta.jpahibernate.dao.concretes.DepartmentDAO;
import com.sparta.jpahibernate.repositories.DeptEmpRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class JpaHibernateApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaHibernateApplication.class, args);
    }
}
