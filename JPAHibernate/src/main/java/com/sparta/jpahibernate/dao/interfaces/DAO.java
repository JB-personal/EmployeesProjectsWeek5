package com.sparta.jpahibernate.dao.interfaces;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    List<T> getAll();

    void save(T t);

}