package exort.apiserver.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import exort.apiserver.entity.UserInfo;

public interface UserRepository extends MongoRepository<UserInfo,Integer> {

	public UserInfo findByUsername(String username);
	public boolean existsByUsername(String username);
	public List<UserInfo> findByType(int type);
}
