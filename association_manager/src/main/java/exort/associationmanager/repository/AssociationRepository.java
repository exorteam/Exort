package exort.associationmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import exort.associationmanager.entity.Association;
import org.springframework.data.mongodb.repository.Query;

public interface AssociationRepository extends MongoRepository<Association,String> {
}
