package happyhours.dimooon.com.happyhours;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

import happyhours.dimooon.com.happyhours.model.database.manager.DatabaseSessionManager;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;

public class TestFlowManager extends AndroidTestCase{

    private static final String TEST_FILE_PREFIX = "test_";
    private static final String TAG = TestFlowManager.class.getSimpleName();

    public static final String CODDING = "Codding";

    private DatabaseSessionManager manager;

    public void setUp() throws Exception {
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
        manager = new DatabaseSessionManager(context);
        assertNotNull(manager);
    }

    public void tearDown() throws Exception {
        manager = null;
    }

    public void testStartNewSessionFlow(){

        HappySession session = manager.startNewSession("NEW_SESSION");

        assertNotNull(session);

        ArrayList<HappyTimerActivity> timerActivities;

        timerActivities = manager.getTimerActivities(session);
        assertNotNull(timerActivities);

        assertTrue(timerActivities.size() == 1);

        HappyTimerActivity mainTimerActivity = timerActivities.get(0);

        HappyTimer timerCoding = manager.createTimer("Coding",false);
        HappyTimer timerDebugging = manager.createTimer("Debugging",false);
        HappyTimer timerFixing = manager.createTimer("Fixing",false);

        assertNotNull(timerCoding);
        assertNotNull(timerDebugging);
        assertNotNull(timerFixing);

        manager.addTimerToSession(session,timerCoding);
        manager.addTimerToSession(session,timerDebugging);
        manager.addTimerToSession(session, timerFixing);

        timerActivities = manager.getTimerActivities(session);
        assertNotNull(timerActivities);

        assertTrue(timerActivities.size() == 4);

    }

    public void testSessionTime(){

        HappySession session = manager.startNewSession("NEW_SESSION");

        assertNotNull(session);

        ArrayList<HappyTimerActivity> timerActivities;

        timerActivities = manager.getTimerActivities(session);
        assertNotNull(timerActivities);

        assertTrue(timerActivities.size() == 1);

        HappyTimerActivity mainTimerActivity = timerActivities.get(0);

        HappyTimer timerCoding = manager.createTimer("Coding",false);
        HappyTimer timerDebugging = manager.createTimer("Debugging", false);
        HappyTimer timerFixing = manager.createTimer("Fixing",false);

        assertNotNull(timerCoding);
        assertNotNull(timerDebugging);
        assertNotNull(timerFixing);

        long willBeHappy1 = manager.addTimerToSession(session, timerCoding);
        long willBeHappy2 = manager.addTimerToSession(session, timerDebugging);
        long willBeHappy3 = manager.addTimerToSession(session, timerFixing);

        timerActivities = manager.getTimerActivities(session);
        assertNotNull(timerActivities);

        assertTrue(timerActivities.size() == 4);

        manager.getDaoFacade().updateTimerActivity(willBeHappy1, timerCoding.getId(), 0, 34);
        manager.getDaoFacade().updateTimerActivity(willBeHappy2, timerDebugging.getId(), 0, 33);
        manager.getDaoFacade().updateTimerActivity(willBeHappy3, timerFixing.getId(), 0, 544);

        long sessionTime = manager.getFullTimeForSession(session);

        Log.e(TAG,"sessionTime: "+sessionTime);

        assertFalse(sessionTime == 0);
        assertTrue(sessionTime == 34 + 33 + 544);
    }

    public void testSessionHappyTime(){

        HappySession session = manager.startNewSession("NEW_SESSION");

        assertNotNull(session);

        ArrayList<HappyTimerActivity> timerActivities;

        timerActivities = manager.getTimerActivities(session);
        assertNotNull(timerActivities);

        assertTrue(timerActivities.size() == 1);

        HappyTimerActivity mainTimerActivity = timerActivities.get(0);

        HappyTimer timerCoding = manager.createTimer("Coding",false);
        HappyTimer timerDebugging = manager.createTimer("Debugging",false);
        HappyTimer timerFixing = manager.createTimer("Fixing",false);
        HappyTimer timerRest = manager.createTimer("Rest",true);
        HappyTimer timerDiner = manager.createTimer("Diner",true);

        assertNotNull(timerCoding);
        assertNotNull(timerDebugging);
        assertNotNull(timerFixing);
        assertNotNull(timerRest);
        assertNotNull(timerDiner);

        long willBeHappy1 = manager.addTimerToSession(session, timerCoding);
        long willBeHappy2 = manager.addTimerToSession(session, timerDebugging);
        long willBeHappy3 = manager.addTimerToSession(session, timerFixing);
        long willBeHappy4 = manager.addTimerToSession(session, timerRest);
        long willBeHappy5 = manager.addTimerToSession(session, timerDiner);

        timerActivities = manager.getTimerActivities(session);
        assertNotNull(timerActivities);

        assertTrue(timerActivities.size() == 6);

        manager.getDaoFacade().updateTimerActivity(willBeHappy1, timerCoding.getId(), 0, 34);
        manager.getDaoFacade().updateTimerActivity(willBeHappy2, timerDebugging.getId(), 0, 33);
        manager.getDaoFacade().updateTimerActivity(willBeHappy3, timerFixing.getId(), 0, 544);
        manager.getDaoFacade().updateTimerActivity(willBeHappy4, timerRest.getId(), 0, 524);
        manager.getDaoFacade().updateTimerActivity(willBeHappy5, timerRest.getId(), 0, 234);

        long happyTimeForSession = manager.getHappyTimeForSession(session);

        Log.e(TAG, "sessionTime: " + happyTimeForSession);

        assertFalse(happyTimeForSession == 0);
        assertTrue(happyTimeForSession == 524 + 234);
    }

    public void testSessionMostHappyTask(){

        HappySession session = manager.startNewSession("NEW_SESSION");

        assertNotNull(session);

        ArrayList<HappyTimerActivity> timerActivities;

        timerActivities = manager.getTimerActivities(session);
        assertNotNull(timerActivities);

        assertTrue(timerActivities.size() == 1);

        HappyTimerActivity mainTimerActivity = timerActivities.get(0);

        HappyTimer timerCoding = manager.createTimer("Coding",false);
        HappyTimer timerDebugging = manager.createTimer("Debugging",false);
        HappyTimer timerFixing = manager.createTimer("Fixing",false);
        HappyTimer timerRest = manager.createTimer("Rest",true);
        HappyTimer timerDiner = manager.createTimer("Diner",true);

        assertNotNull(timerCoding);
        assertNotNull(timerDebugging);
        assertNotNull(timerFixing);
        assertNotNull(timerRest);
        assertNotNull(timerDiner);

        long willBeHappy1 = manager.addTimerToSession(session, timerCoding);
        long willBeHappy2 = manager.addTimerToSession(session, timerDebugging);
        long willBeHappy3 = manager.addTimerToSession(session, timerFixing);
        long willBeHappy4 = manager.addTimerToSession(session, timerRest);
        long willBeHappy5 = manager.addTimerToSession(session, timerDiner);

        timerActivities = manager.getTimerActivities(session);
        assertNotNull(timerActivities);

        assertTrue(timerActivities.size() == 6);

        manager.getDaoFacade().updateTimerActivity(willBeHappy1, timerCoding.getId(), 0, 34);
        manager.getDaoFacade().updateTimerActivity(willBeHappy2,timerDebugging.getId(), 0, 33);
        manager.getDaoFacade().updateTimerActivity(willBeHappy3, timerFixing.getId(), 0, 544);
        manager.getDaoFacade().updateTimerActivity(willBeHappy4, timerRest.getId(), 0, 524);
        manager.getDaoFacade().updateTimerActivity(willBeHappy5, timerRest.getId(), 0, 234);

        HappyTimerActivity happyTimeForSession = manager.getMostHappyTask(session);

        Log.e(TAG, "mostHappyTask: " + happyTimeForSession);

        assertFalse(happyTimeForSession.getActivityValue() == 0);
        assertTrue(happyTimeForSession.getActivityValue() == 524);
    }

    public void testGetAllTimersNotAssignedToCurrentSession(){

        HappySession session = manager.startNewSession("NEW_SESSION");

        assertNotNull(session);

        ArrayList<HappyTimerActivity> timerActivities;

        timerActivities = manager.getTimerActivities(session);
        assertNotNull(timerActivities);

        assertTrue(timerActivities.size() == 1);

        HappyTimerActivity mainTimerActivity = timerActivities.get(0);

        HappyTimer timerCoding = manager.createTimer("Coding",false);
        HappyTimer timerDebugging = manager.createTimer("Debugging",false);
        HappyTimer timerFixing = manager.createTimer("Fixing",false);
        HappyTimer timerRest = manager.createTimer("Rest",true);
        HappyTimer timerDiner = manager.createTimer("Diner",true);

        assertNotNull(timerCoding);
        assertNotNull(timerDebugging);
        assertNotNull(timerFixing);
        assertNotNull(timerRest);
        assertNotNull(timerDiner);

        long willBeHappy1 = manager.addTimerToSession(session, timerCoding);
        long willBeHappy2 = manager.addTimerToSession(session, timerDebugging);
        long willBeHappy3 = manager.addTimerToSession(session, timerFixing);
        long willBeHappy4 = manager.addTimerToSession(session, timerRest);
        long willBeHappy5 = manager.addTimerToSession(session, timerDiner);

        timerActivities = manager.getTimerActivities(session);
        assertNotNull(timerActivities);

        assertTrue(timerActivities.size() == 6);

        ArrayList<HappyTimer> allTimers = manager.getDaoFacade().getTimers();

        assertTrue(allTimers != null);
        assertTrue(allTimers.size() > 0);
        assertTrue(allTimers.size() == HappyTimer.defaultTimers.size() + 5);

        ArrayList<HappyTimer> notAssignedTimers =  manager.getTimersNotAssignedToSession(session);

        assertTrue(notAssignedTimers != null);
        assertTrue(notAssignedTimers.size() > 0);

        assertTrue(notAssignedTimers.size() == allTimers.size() - timerActivities.size());
    }

}