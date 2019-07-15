package com.exort.association_member_management.repository;

import com.exort.association_member_management.entity.Application;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ApplicationRepository extends PagingAndSortingRepository<Application, Integer>, JpaSpecificationExecutor<Application> {

}
