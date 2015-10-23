package happyhours.dimooon.com.happyhours.view.fragments.storylog;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.database.data.SessionDataProvider;

/**
 * Created by dmytro on 10/22/15.
 */
public class SessionModel {

    private static final String TAG = SessionModel.class.getSimpleName();
    private HappySession session;
    private SessionDataProvider sessionDataProvider;
    private boolean restoredFromLog = false;

    private ArrayList<HappyTimerActivity> timerActivities = new ArrayList<>();
    private HashMap<HappyTimerActivity, Boolean> happyTimerActivity = new HashMap<>();

    public SessionModel(HappySession session,SessionDataProvider sessionDataProvider,boolean restoredFromLog) {
        this.session = session;
        this.sessionDataProvider = sessionDataProvider;
        this.restoredFromLog = restoredFromLog;
    }

    public void init(){

        Log.e(TAG,"init model for session: "+this.session);

        timerActivities.clear();
        happyTimerActivity.clear();

        timerActivities.addAll(sessionDataProvider.getTimerActivities(session));

        Log.e(TAG, "init model related timer activities: " + this.timerActivities);

        for (HappyTimerActivity timerActivity : timerActivities) {
            happyTimerActivity.put(timerActivity,getTimer(timerActivity).getHappy() == 1);
        }
    }

    public HappyFacade getDaoFacade() {
        return sessionDataProvider.getDaoFacade();
    }

    public HappyTimer getTimer(HappyTimerActivity happyTimerActivity) {
        return getDaoFacade().getTimer(happyTimerActivity.getTimerId());
    }

    public HappyTimerActivity getTimerActivity(int position) {
        return timerActivities.get(position);
    }

    public boolean sessionFromLog() {
        return restoredFromLog;
    }

    public boolean isHappy(HappyTimerActivity timerActivity) {
        return happyTimerActivity.get(timerActivity);
    }

    public int getActivityValue(HappyTimerActivity timerActivity) {
        return (int) timerActivity.getActivityValue();
    }

    public ArrayList<HappyTimerActivity> getTimerActivities(){
        return this.timerActivities;
    }

    public void update() {
        this.init();
    }
}