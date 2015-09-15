package happyhours.dimooon.com.happyhours.model.database.facade.interfaces;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;

public interface TimerActivity {
    long createTimerActivity(long timerId,long activityId,long defaultValue,long sessionId);
    HappyTimerActivity getTimerActivity(long id);
    ArrayList<HappyTimerActivity> getTimerActivities();
    ArrayList<HappyTimerActivity> getTimerActivities(long sessionId);
    boolean updateTimerActivity(long itemId,long timerId,long activityId,long value);
    boolean deleteTimerActivity(long id);
    long getFullTime(long id);
    long getHappyTime(long id);
    HappyTimerActivity getMostHappyTask(long id);
}