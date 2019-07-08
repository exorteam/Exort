package exort.activity.repository;

import exort.activity.entity.Activity;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
    void saveAndFlush(Activity activity);

    Activity findActivitiesById(Long Id);
}
