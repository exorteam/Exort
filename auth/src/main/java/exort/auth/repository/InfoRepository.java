package exort.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import exort.auth.entity.UserInfo;

public interface InfoRepository extends MongoRepository<UserInfo,Integer> {

	Page<UserInfo> findAll(Pageable page);

}
