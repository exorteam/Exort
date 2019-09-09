package exort.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import exort.auth.entity.UserCommunityEntity;

public interface CommunityRepository extends MongoRepository<UserCommunityEntity,Integer> {

}
