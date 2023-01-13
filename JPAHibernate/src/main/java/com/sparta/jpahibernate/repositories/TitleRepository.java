package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.Title;
import com.sparta.jpahibernate.entities.TitleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, TitleId> {
}