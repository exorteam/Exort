package exort.comment.repository;

import exort.comment.entity.AssociationBalance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssociationBalanceRepository extends MongoRepository<AssociationBalance, String> {
    public boolean existsByAssociationId(String associationId);
    public AssociationBalance findByAssociationId(String associationId);
}