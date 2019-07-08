package exort.activity.repository;

import exort.activity.entity.ActivitySignUp;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActivitySignUpRepository extends CrudRepository<ActivitySignUp, Long> {

    void saveAndFlush(ActivitySignUp activitySignUp);

//    List<ActivitySignUp> findAllByActivity_id(Long activity_id);

    ActivitySignUp findActivitySignUpById(Long id);

    List<ActivitySignUp> findAllByActivity_idAndState(Long activity_id, int state);
}
