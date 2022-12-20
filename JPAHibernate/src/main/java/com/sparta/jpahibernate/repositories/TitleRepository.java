package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.Title;
import com.sparta.jpahibernate.entities.TitleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, TitleId> {
    @Query(
            value = "SELECT T.title FROM employees E LEFT JOIN salaries S ON E.emp_no = S.emp_no LEFT JOIN titles T ON E.emp_no = T.emp_no GROUP BY T.title ORDER BY T.title", nativeQuery = true
    )
    List<String> findAllTitles();
}