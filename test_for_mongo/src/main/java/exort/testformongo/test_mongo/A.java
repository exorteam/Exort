package exort.testformongo.test_mongo;

import exort.testformongo.entity.Association;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface A extends MongoRepository<Association,Integer> {
}