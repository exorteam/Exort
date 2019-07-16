package exort.association_member_management.repository;

import exort.association_member_management.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer>, PagingAndSortingRepository<Department, Integer> {
    List<Department> findAllByAssociationId(int associationId);

    List<Department> findAllByAssociationIdAndDepartmentId(int associationId, int departmentId);

    Boolean existsByAssociationIdAndDepartmentId(int associationId, int departmentId);

    Boolean existsByAssociationId(int associationId);

    Department findByAssociationIdAndDepartmentId(int associationId, int departmentId);
}
