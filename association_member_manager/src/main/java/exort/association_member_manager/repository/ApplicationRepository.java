package exort.association_member_manager.repository;

import exort.association_member_manager.entity.Application;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ApplicationRepository extends PagingAndSortingRepository<Application, Integer>, JpaSpecificationExecutor<Application> {

}
