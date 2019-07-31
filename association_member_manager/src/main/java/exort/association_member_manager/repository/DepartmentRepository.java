package exort.association_member_manager.repository;

import exort.association_member_manager.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer>, PagingAndSortingRepository<Department, Integer> {
    List<Department> findAllByAssociationId(String associationId);

    Department findFirstByAssociationIdOrderByDepartmentIdDesc(String associationId);

    Boolean existsByAssociationIdAndDepartmentId(String associationId, int departmentId);

    Boolean existsByAssociationId(String associationId);

    Department findByAssociationIdAndDepartmentId(String associationId, int departmentId);
}
