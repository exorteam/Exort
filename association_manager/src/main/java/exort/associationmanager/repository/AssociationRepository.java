package exort.associationmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import exort.associationmanager.entity.Association;

public interface AssociationRepository extends MongoRepository<Association,String> {
	Page<Association> findAll(Pageable page);
}
