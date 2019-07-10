package com.exort.association_member_management.repository;

import com.exort.association_member_management.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer>, PagingAndSortingRepository<Department,Integer> {
    List<Department> findAllByAssociationId(int associationId);
    List<Department> findAllByAssociationIdAndDepartmentId(int associationId,int departmentId);
    Department findByAssociationIdAndDepartmentId(int associationId,int departmentId);
}
