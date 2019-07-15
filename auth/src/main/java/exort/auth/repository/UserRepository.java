package exort.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import exort.auth.entity.UserInfo;

public interface UserRepository extends MongoRepository<UserInfo,Integer> {

	public boolean existsByUsername(String usr);
	public UserInfo findByUsername(String usr);
}
