package com.sparta.jpahibernate.dao.interfaces;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    long count();
    void delete(T dto);
    List<T> findAll();
    void save(T dto);

}