package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.DeptManager;
import com.sparta.jpahibernate.entities.DeptManagerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptManagerRepository extends JpaRepository<DeptManager, DeptManagerId> {
}