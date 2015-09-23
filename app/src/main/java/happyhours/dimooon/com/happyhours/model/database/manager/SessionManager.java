package happyhours.dimooon.com.happyhours.model.database.manager;

import java.util.ArrayList;
import java.util.HashSet;

import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;

public interface SessionManager {

    ArrayList<HappyTimerActivity> getTimerActivities(HappySession session);
    long addTimerToSession(HappySession session, HappyTimer timer);
    HappyTimerActivity getTimerActivity(long id);
    HappyFacade getDaoFacade();
    ArrayList<HappyTimer> getTimersNotAssignedToSession(HappySession session);
    long getFullTimeForSession(HappySession session);
    long getHappyTimeForSession(HappySession session);
    HappyTimerActivity getMostHappyTask(HappySession session);
    HappySession startNewSession(String s);
}
