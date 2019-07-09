package exort.associationmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import exort.associationmanager.entity.AssociationApplication;

public interface AssociationApplicationRepository extends MongoRepository<AssociationApplication,Integer> {

}
