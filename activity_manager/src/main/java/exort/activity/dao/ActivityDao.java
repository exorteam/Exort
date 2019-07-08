package exort.activity.dao;

import exort.activity.entity.Activity;

public interface ActivityDao {

    void update(Activity activity);

    Activity getActivity(Long id);
}
