package happyhours.dimooon.com.happyhours.database;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import happyhours.dimooon.com.happyhours.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.database.facade.interfaces.Session;
import happyhours.dimooon.com.happyhours.database.facade.interfaces.TimerActivity;

public class SessionManager {

    private static final String TAG = SessionManager.class.getSimpleName();
    private HappyFacade daoFacade;

    public SessionManager(Context context) {
        daoFacade = new HappyFacade(context);
    }

    public HappySession startNewSession(String name){
        long sessionId = daoFacade.createSession(name);
        daoFacade.createTimerActivity(HappyTimer.DEFAULT_TIMER_ID,0l,0l,sessionId);

      return daoFacade.getSession(sessionId);
    };

    public ArrayList<HappyTimerActivity> getTimerActivities(HappySession session){

        ArrayList<HappyTimerActivity> timerActivities = daoFacade.getTimerActivities(session.getId());

        for(HappyTimerActivity timerActivity: timerActivities){

            timerActivity.setTimerName(daoFacade.getTimer(timerActivity.getTimerId()).getName());

            Log.e(TAG,"timerActivity: "+timerActivity);

        }



        return timerActivities;
    }

    public HappyTimer createTimer(String name){
        return daoFacade.getTimer(daoFacade.createTimer(name));
    }

    public long addTimerToSession(HappySession session,HappyTimer timer){
        return daoFacade.createTimerActivity(timer.getId(), 0, 0, session.getId());
    }

    public HappyTimerActivity getTimerActivity(long id){
        return daoFacade.getTimerActivity(id);
    }

    public HappyFacade getDaoFacade(){
        return daoFacade;
    }
}