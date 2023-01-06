package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByKey(String key);
}