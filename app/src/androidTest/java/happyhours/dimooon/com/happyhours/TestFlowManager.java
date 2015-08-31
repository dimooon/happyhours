package happyhours.dimooon.com.happyhours;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.database.SessionManager;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimerActivity;

public class TestFlowManager extends AndroidTestCase{

    private static final String TEST_FILE_PREFIX = "test_";
    private static final String TAG = TestFlowManager.class.getSimpleName();

    public static final String CODDING = "Codding";

    private SessionManager manager;

    public void setUp() throws Exception {
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
        manager = new SessionManager(context);
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

        HappyTimer timerCoding = manager.createTimer("Coding");
        HappyTimer timerDebugging = manager.createTimer("Debugging");
        HappyTimer timerFixing = manager.createTimer("Fixing");

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

}