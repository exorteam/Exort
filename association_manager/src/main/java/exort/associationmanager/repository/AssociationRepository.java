package exort.associationmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import exort.associationmanager.entity.Association;
import org.springframework.data.mongodb.repository.Query;

public interface AssociationRepository extends MongoRepository<Association,Integer> {

    @Query("{'_id':?0}")
    Association findById(String AssoId);

    void deleteById(String AssoId);
    boolean  existsByName(String name);
    public boolean  existsById(String id);


}
