package happyhours.dimooon.com.happyhours;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.database.facade.bean.HappyActivity;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.database.facade.HappyFacade;

public class TestTimerActivityDAO extends AndroidTestCase{

    private static final String TEST_FILE_PREFIX = "test_";
    private static final String TAG = TestTimerActivityDAO.class.getSimpleName();

    private HappyFacade happyDAOFacade = null;

    private static final long MOCK_TIMER_ID = 1l;
    private static final long MOCK_ACTIVITY_ID = 1l;
    private static final long MOCK_TIMER_ACTIVITY_VALUE = 1000l;

    private static final long MOCK_TIMER_ID1 = 2l;
    private static final long MOCK_ACTIVITY_ID1 = 2l;
    private static final long MOCK_TIMER_ACTIVITY_VALUE1 = 1050l;

    public void setUp() throws Exception {

        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
        happyDAOFacade = new HappyFacade(context);

        assertNotNull(happyDAOFacade);
    }

    public void tearDown() throws Exception { happyDAOFacade = null; }

    public void testCreateTimerActivity(){

        HappyTimerActivity timerActivity = createTimerActivity(MOCK_TIMER_ID,MOCK_ACTIVITY_ID,MOCK_TIMER_ACTIVITY_VALUE);
        validateTimerActivity(timerActivity,MOCK_TIMER_ACTIVITY_VALUE);

    }
    public void testGetTimerActivity(){
        HappyTimerActivity happyTimerActivity = createTimerActivity(MOCK_TIMER_ID, MOCK_ACTIVITY_ID, MOCK_TIMER_ACTIVITY_VALUE);
        validateTimerActivity(happyTimerActivity, MOCK_TIMER_ACTIVITY_VALUE);

        happyTimerActivity.setTimerName(happyDAOFacade.getTimer(happyTimerActivity.getId()).getName());

        HappyTimerActivity timerActivityFromDatabase = happyDAOFacade.getTimerActivity(happyTimerActivity.getId());
        validateTimerActivity(timerActivityFromDatabase,MOCK_TIMER_ACTIVITY_VALUE);

        timerActivityFromDatabase.setTimerName(happyDAOFacade.getTimer(timerActivityFromDatabase.getId()).getName());

        assertNotNull(happyTimerActivity);
        assertNotNull(timerActivityFromDatabase);

        assertEquals(happyTimerActivity, timerActivityFromDatabase);
    }
    public void testGetTimerActivities(){
        HappyTimerActivity happyTimerActivity = createTimerActivity(MOCK_TIMER_ID, MOCK_ACTIVITY_ID, MOCK_TIMER_ACTIVITY_VALUE);
        validateTimerActivity(happyTimerActivity, MOCK_TIMER_ACTIVITY_VALUE);

        HappyTimerActivity happyTimerActivity2 = createTimerActivity(MOCK_TIMER_ID1, MOCK_ACTIVITY_ID1, MOCK_TIMER_ACTIVITY_VALUE1);
        validateTimerActivity(happyTimerActivity2, MOCK_TIMER_ACTIVITY_VALUE1);

        ArrayList<HappyTimerActivity> timerActivities = happyDAOFacade.getTimerActivities();
        assertNotNull(timerActivities);

        Log.e(TAG, "timerActivities: " + timerActivities);

        assertTrue(timerActivities.size() > 0);
        assertTrue(timerActivities.size() == 2);

        assertNotSame(happyTimerActivity, happyTimerActivity2);
    }
    public void testUpdateTimerActivity(){

        HappyTimerActivity happyTimerActivity = createTimerActivity(MOCK_TIMER_ID, MOCK_ACTIVITY_ID, MOCK_TIMER_ACTIVITY_VALUE);
        validateTimerActivity(happyTimerActivity, MOCK_TIMER_ACTIVITY_VALUE);

        boolean updated = happyDAOFacade.updateTimerActivity(happyTimerActivity.getId(), happyTimerActivity.getTimerId(), happyTimerActivity.getActivityId(), MOCK_TIMER_ACTIVITY_VALUE1);
        assertTrue(updated);

        HappyTimerActivity updatedTimerActivity = happyDAOFacade.getTimerActivity(happyTimerActivity.getId());
        validateTimerActivity(updatedTimerActivity, MOCK_TIMER_ACTIVITY_VALUE1);

        assertNotSame(happyTimerActivity, updatedTimerActivity);
        assertTrue(happyTimerActivity.getId() == updatedTimerActivity.getId());

        ArrayList<HappyTimerActivity> activities = happyDAOFacade.getTimerActivities();
        assertNotNull(activities);

        assertTrue(activities.size() > 0);
        assertTrue(activities.size() == 1);

    }

    public void testDeleteTimerActivity(){
        HappyTimerActivity happyTimerActivity = createTimerActivity(MOCK_TIMER_ID, MOCK_ACTIVITY_ID, MOCK_TIMER_ACTIVITY_VALUE);
        validateTimerActivity(happyTimerActivity, MOCK_TIMER_ACTIVITY_VALUE);

        boolean deleted = happyDAOFacade.deleteTimerActivity(happyTimerActivity.getId());
        assertTrue(deleted);

        ArrayList<HappyTimerActivity> timerActivities = happyDAOFacade.getTimerActivities();
        assertNotNull(timerActivities);

        assertTrue(timerActivities.size() == 0);
    }

    private HappyTimerActivity createTimerActivity(long timerId,long activityId,long value){
        return happyDAOFacade.getTimerActivity(happyDAOFacade.createTimerActivity(timerId, activityId, value,0));
    }

    private void validateTimerActivity(HappyTimerActivity timerActivity, long value){
        assertNotNull(timerActivity);
        assertTrue(timerActivity.getActivityValue() == value);
    }
}