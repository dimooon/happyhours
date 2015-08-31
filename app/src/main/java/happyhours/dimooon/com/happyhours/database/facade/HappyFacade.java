package happyhours.dimooon.com.happyhours.database.facade;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.database.facade.bean.HappyActivity;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.database.facade.dao.ActivityDAO;
import happyhours.dimooon.com.happyhours.database.facade.dao.SessionDAO;
import happyhours.dimooon.com.happyhours.database.facade.dao.TimerActivityDAO;
import happyhours.dimooon.com.happyhours.database.facade.dao.TimerDAO;
import happyhours.dimooon.com.happyhours.database.facade.interfaces.Activity;
import happyhours.dimooon.com.happyhours.database.facade.interfaces.Session;
import happyhours.dimooon.com.happyhours.database.facade.interfaces.Timer;
import happyhours.dimooon.com.happyhours.database.facade.interfaces.TimerActivity;
import happyhours.dimooon.com.happyhours.database.helper.HappyDatabaseHelper;

public class HappyFacade implements Timer,Activity,TimerActivity,Session {

    private TimerDAO daoTimer;
    private ActivityDAO daoActivity;
    private TimerActivityDAO daoTimerActivity;
    private SessionDAO daoSession;

    public HappyFacade(Context context) {

        SQLiteDatabase database = new HappyDatabaseHelper(context).getWritableDatabase();

        daoTimer = new TimerDAO(database);
        daoActivity = new ActivityDAO(database);
        daoTimerActivity = new TimerActivityDAO(database);
        daoSession = new SessionDAO(database);
    }

    @Override
    public long createTimer(String name) {

        return daoTimer.createTimer(name);
    }

    @Override
    public HappyTimer getTimer(long id) {
        return daoTimer.getTimer(id);
    }

    @Override
    public ArrayList<HappyTimer> getTimers() {
        return daoTimer.getTimers();
    }

    @Override
    public boolean updateTimer(long id, String name) {
        return daoTimer.updateTimer(id, name);
    }

    @Override
    public boolean deleteTimer(long id) {
        return daoTimer.deleteTimer(id);
    }


    @Override
    public long createActivity(long value) {
        return daoActivity.createActivity(value);
    }

    @Override
    public HappyActivity getActivity(long id) {
        return daoActivity.getActivity(id);
    }

    @Override
    public ArrayList<HappyActivity> getActivities() {
        return daoActivity.getActivities();
    }

    @Override
    public boolean updateActivity(long id, long value) {
        return daoActivity.updateActivity(id, value);
    }

    @Override
    public boolean deleteActivity(long id) {
        return daoActivity.deleteActivity(id);
    }

    @Override
    public long createTimerActivity(long timerId, long activityId, long defaultValue, long sessionId) {
        return daoTimerActivity.createTimerActivity(timerId, activityId, defaultValue,sessionId);
    }

    @Override
    public HappyTimerActivity getTimerActivity(long id) {
        return daoTimerActivity.getTimerActivity(id);
    }

    @Override
    public ArrayList<HappyTimerActivity> getTimerActivities() {
        return daoTimerActivity.getTimerActivities();
    }

    @Override
    public ArrayList<HappyTimerActivity> getTimerActivities(long sessionId) {
        return daoTimerActivity.getTimerActivities(sessionId);
    }

    @Override
    public boolean updateTimerActivity(long itemId, long timerId, long activityId, long value) {
        return daoTimerActivity.updateTimerActivity(itemId, timerId, activityId, value);
    }

    @Override
    public boolean deleteTimerActivity(long id) {
        return daoTimerActivity.deleteTimerActivity(id);
    }

    @Override
    public long createSession(String name) {
        return daoSession.createSession(name);
    }

    @Override
    public HappySession getSession(long id) {
        return daoSession.getSession(id);
    }

    @Override
    public ArrayList<HappySession> getSessions() {
        return daoSession.getSessions();
    }

    @Override
    public boolean updateSessionName(long id,String name) {
        return daoSession.updateSessionName(id,name);
    }

    @Override
    public boolean deleteSession(long id) {
        return daoSession.deleteSession(id);
    }
}