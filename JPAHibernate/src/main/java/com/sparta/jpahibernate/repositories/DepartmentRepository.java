
package com.sparta.jpahibernate.repositories;

import com.sparta.jpahibernate.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    Department findByDeptName(String name);
}