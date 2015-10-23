package happyhours.dimooon.com.happyhours.model.database.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;

public class DatabaseSessionDataProvider implements SessionDataProvider {

    protected HappyFacade daoFacade;

    public DatabaseSessionDataProvider(Context context) {
        daoFacade = new HappyFacade(context);
    }

    public HappySession startNewSession(String name){
        long sessionId = daoFacade.createSession(name);
        daoFacade.createTimerActivity(HappyTimer.DEFAULT_TIMER_ID,0l,0l,sessionId);

      return daoFacade.getSession(sessionId);
    };

    public synchronized ArrayList<HappyTimerActivity> getTimerActivities(HappySession session){

        ArrayList<HappyTimerActivity> timerActivities = daoFacade.getTimerActivities(session.getId());

        for(HappyTimerActivity timerActivity: timerActivities){

            timerActivity.setTimerName(daoFacade.getTimer(timerActivity.getTimerId()).getName());
        }

        return timerActivities;
    }

    public synchronized HappyTimer createTimer(String name,boolean happy){
        return daoFacade.getTimer(daoFacade.createTimer(name,happy));
    }

    public synchronized long addTimerToSession(HappySession session,HappyTimer timer){
        return daoFacade.createTimerActivity(timer.getId(), 0, 0, session.getId());
    }

    public synchronized HappyTimerActivity getTimerActivity(long id){
        return daoFacade.getTimerActivity(id);
    }

    public synchronized HappyFacade getDaoFacade(){
        return daoFacade;
    }

    public synchronized long getFullTimeForSession(HappySession session){
        return daoFacade.getFullTime(session.getId());
    }

    public synchronized long getHappyTimeForSession(HappySession session){

        long happyTime = 0;

        for(HappyTimerActivity activity : getTimerActivities(session)){

            HappyTimer timer = daoFacade.getTimer(activity.getTimerId());

            if(timer.getHappy() == 1){
                happyTime+=activity.getActivityValue();
            }
        }

        return happyTime;
    }

    public synchronized HappyTimerActivity getMostHappyTask(HappySession session){

        ArrayList<HappyTimerActivity> activities =  new ArrayList<>();

        for(HappyTimerActivity activity : getTimerActivities(session)){

            HappyTimer timer = daoFacade.getTimer(activity.getTimerId());

            if(timer.getHappy() == 1){
                activities.add(activity);
            }
        }

        if(activities == null || activities.size() == 0){
            return null;
        }else if(activities!=null && activities.size() == 1){
            return activities.get(0);
        }

        Collections.sort(activities, new Comparator<HappyTimerActivity>() {
            @Override
            public int compare(HappyTimerActivity timerActivity, HappyTimerActivity t1) {

                if(timerActivity.getActivityValue() > t1.getActivityValue()){
                    return -1;
                }else if(t1.getActivityValue() > timerActivity.getActivityValue()){
                    return 1;
                }
                return 0;
            }
        });

        return activities.get(0);
    };

    @Override
    public synchronized ArrayList<HappyTimer> getTimersNotAssignedToSession(HappySession session) {
        ArrayList<HappyTimer> allTimers = daoFacade.getTimers();
        ArrayList<HappyTimerActivity> timerActivities = getTimerActivities(session);

        Iterator<HappyTimer> timerIterator = allTimers.iterator();

        while (timerIterator.hasNext()){
            HappyTimer timer = timerIterator.next();

            for(HappyTimerActivity timerActivity: timerActivities){
                if(timerActivity.getTimerId() == timer.getId()){
                    timerIterator.remove();
                    break;
                }
            }

        }
        return allTimers;
    }
}