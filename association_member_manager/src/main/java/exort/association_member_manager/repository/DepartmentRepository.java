package exort.association_member_manager.repository;

import exort.association_member_manager.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer>, PagingAndSortingRepository<Department, Integer> {
    List<Department> findAllByAssociationId(int associationId);

    Department findFirstByAssociationIdOrderByDepartmentIdDesc(int associationId);

    Boolean existsByAssociationIdAndDepartmentId(int associationId, int departmentId);

    Boolean existsByAssociationId(int associationId);

    Department findByAssociationIdAndDepartmentId(int associationId, int departmentId);
}
