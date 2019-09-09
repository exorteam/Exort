package exort.comment.repository;

import exort.comment.entity.Finance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FinanceRepository extends MongoRepository<Finance,String> {

}
