package exort.activity.repository;

import exort.activity.entity.ActivityInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<ActivityInfo,String> {
}
