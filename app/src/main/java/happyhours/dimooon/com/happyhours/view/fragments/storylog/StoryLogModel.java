package happyhours.dimooon.com.happyhours.view.fragments.storylog;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionDataProvider;

/**
 * Created by dmytro on 10/22/15.
 */
public class StoryLogModel {

    private static final String TAG = StoryLogModel.class.getSimpleName();
    private SessionDataProvider sessionDataProvider;
    private ArrayList<HappySession> sessions = new ArrayList<>();
    private HashMap<HappySession,Integer> fullTimePerSession = new HashMap<>();
    private HashMap<HappySession,ArrayList<HappyTimerActivity>> timerActivitiesPerSession = new HashMap<>();
    private HashMap<HappySession,Integer> happyTimePerSession = new HashMap<>();
    private HashMap<HappySession,HappyTimerActivity> mostHappyTaskPerSession = new HashMap<>();

    public StoryLogModel(SessionDataProvider sessionDataProvider) {
        this.sessionDataProvider = sessionDataProvider;
    }

    public void init(){

        sessions.addAll(sessionDataProvider.getDaoFacade().getSessions());

        Log.e(TAG, "init story log model for sessions: " + this.sessions);

        for(HappySession session : sessions){
            fullTimePerSession.put(session, (int) sessionDataProvider.getFullTimeForSession(session));
            timerActivitiesPerSession.put(session, sessionDataProvider.getTimerActivities(session));
            happyTimePerSession.put(session, (int) sessionDataProvider.getHappyTimeForSession(session));
            mostHappyTaskPerSession.put(session, sessionDataProvider.getMostHappyTask(session));
        }
    }

    public HappySession getSession(int position) {
        return this.sessions.get(position);
    }

    public int getFullTimeForSession(HappySession session) {
        return this.fullTimePerSession.get(session);
    }

    public ArrayList<HappyTimerActivity> getTimerActivities(HappySession session) {
        return this.timerActivitiesPerSession.get(session);
    }

    public int getHappyTimeForSession(HappySession session) {
        return this.happyTimePerSession.get(session);
    }

    public HappyTimerActivity getMostHappyTask(HappySession session) {
        return this.mostHappyTaskPerSession.get(session);
    }

    public ArrayList<HappySession> getSessions(){
        return this.sessions;
    }

    public SessionDataProvider getSessionDataProvider(){
        return this.sessionDataProvider;
    }
}
