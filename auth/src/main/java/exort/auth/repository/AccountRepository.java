package exort.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import exort.auth.entity.UserAccount;

public interface AccountRepository extends MongoRepository<UserAccount,Integer> {

	public boolean  existsByUsername(String usr);
	public UserAccount findByUsername(String usr);
}
