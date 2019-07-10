package com.exort.association_member_management.repository;

import com.exort.association_member_management.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
}
