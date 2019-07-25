package exort.associationmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import exort.associationmanager.entity.Association;

public interface AssociationRepository extends MongoRepository<Association,Integer> {

}
