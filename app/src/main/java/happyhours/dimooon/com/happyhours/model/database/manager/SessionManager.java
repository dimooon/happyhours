package happyhours.dimooon.com.happyhours.model.database.manager;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;

public interface SessionManager {

    ArrayList<HappyTimerActivity> getTimerActivities(HappySession session);
    long addTimerToSession(HappySession session, HappyTimer timer);
    HappyTimerActivity getTimerActivity(long id);
    HappyFacade getDaoFacade();
}
