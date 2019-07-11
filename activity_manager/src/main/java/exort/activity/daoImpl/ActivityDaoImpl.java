package exort.activity.daoImpl;

import exort.activity.dao.ActivityDao;
import exort.activity.entity.Activity;
import exort.activity.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityDaoImpl implements ActivityDao {

    @Autowired
    private ActivityRepository ar;

    @Autowired
    private ActivitySignUpRepository asr;

    @Override
    public void update(Activity activity){
        ar.saveAndFlush(activity);
    }

    @Override
    public Activity getActivity(Long id){
        return ar.findActivitiesById(id);
    }

}
