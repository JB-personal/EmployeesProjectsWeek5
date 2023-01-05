package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}