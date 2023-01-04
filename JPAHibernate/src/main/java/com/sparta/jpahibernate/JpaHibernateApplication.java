package com.sparta.jpahibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
public class JpaHibernateApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaHibernateApplication.class, args);
    }
}
