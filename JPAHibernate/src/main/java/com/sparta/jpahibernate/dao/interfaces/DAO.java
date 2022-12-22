package com.sparta.jpahibernate.dao.interfaces;

import java.util.List;

public interface DAO<T> {
    long count();
    void delete(T dto);
    List<T> findAll();
    void save(T dto);

}