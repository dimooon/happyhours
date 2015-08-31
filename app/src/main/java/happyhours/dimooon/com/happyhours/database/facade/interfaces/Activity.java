package happyhours.dimooon.com.happyhours.database.facade.interfaces;


import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.database.facade.bean.HappyActivity;

public interface Activity {

    long createActivity(long value);
    HappyActivity getActivity(long id);
    ArrayList<HappyActivity> getActivities();
    boolean updateActivity(long id,long value);
    boolean deleteActivity(long id);

}
