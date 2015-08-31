package happyhours.dimooon.com.happyhours.database.facade.interfaces;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimerActivity;

public interface TimerActivity {
    long createTimerActivity(long timerId,long activityId,long defaultValue,long sessionId);
    HappyTimerActivity getTimerActivity(long id);
    ArrayList<HappyTimerActivity> getTimerActivities();
    ArrayList<HappyTimerActivity> getTimerActivities(long sessionId);
    boolean updateTimerActivity(long itemId,long timerId,long activityId,long value);
    boolean deleteTimerActivity(long id);
}