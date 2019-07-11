package exort.apiserver.repository;

import exort.apiserver.entity.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserInfo,Integer> {

	public UserInfo findByUsername(String username);
	public boolean existsByUsername(String username);
}
