package exort.review_manager.repository.impl;

import exort.review_manager.entity.IdGenEntity;
import exort.review_manager.repository.IdGeneratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class IdGeneratorRepositoryImpl implements IdGeneratorRepository {

    @Autowired
    private MongoTemplate mt;

    @Override
    public long nextId(String name) {
        return mt.findAndModify(
                new Query(Criteria.where("name").is(name)),
                new Update().setOnInsert("name", name).inc("max_id", 1),
                new FindAndModifyOptions().returnNew(true).upsert(true),
                IdGenEntity.class
        ).getMaxId();
    }

    @Override
    public long resetId(String name, long maxId) {
        return mt.findAndModify(
                new Query(Criteria.where("name").is(name)),
                new Update().setOnInsert("name", name).set("max_id", maxId),
                new FindAndModifyOptions().returnNew(true).upsert(true),
                IdGenEntity.class
        ).getMaxId();
    }
}
