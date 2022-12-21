package com.sparta.jpahibernate.dao.interfaces;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(Integer id);

    List<T> getAll();

    void save(T t);

    void update(T t, String[] params);

    void delete(Integer id);
}