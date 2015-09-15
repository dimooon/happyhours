package happyhours.dimooon.com.happyhours;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyActivity;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;

public class TestActivityDAO extends AndroidTestCase{

    private static final String TEST_FILE_PREFIX = "test_";
    private static final String TAG = TestActivityDAO.class.getSimpleName();
    private HappyFacade happyDAOFacade = null;

    private static final long TIMER_MOCK_VALUE1 = 129l;
    private static final long TIMER_MOCK_VALUIE2 = 12334l;

    public void setUp() throws Exception {

        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
        happyDAOFacade = new HappyFacade(context);

        assertNotNull(happyDAOFacade);
    }

    public void tearDown() throws Exception {
        happyDAOFacade = null;
    }

    public void testActivityCreate(){
        HappyActivity happyActivity = createActivity(TIMER_MOCK_VALUE1);
        validateActivity(happyActivity, TIMER_MOCK_VALUE1);
    }

    public void testActivityReadAll(){

        HappyActivity happyActivity = createActivity(TIMER_MOCK_VALUE1);
        validateActivity(happyActivity, TIMER_MOCK_VALUE1);

        HappyActivity happyActivity1 = createActivity(TIMER_MOCK_VALUIE2);
        validateActivity(happyActivity1, TIMER_MOCK_VALUIE2);

        ArrayList<HappyActivity> activities = happyDAOFacade.getActivities();
        assertNotNull(activities);

        Log.e(TAG,"activities: "+activities);

        assertTrue(activities.size() > 0);
        assertTrue(activities.size() == 2);

        assertNotSame(happyActivity, happyActivity1);
    }

    public void testActivityRead(){
        HappyActivity happyActivity = createActivity(TIMER_MOCK_VALUE1);
        validateActivity(happyActivity, TIMER_MOCK_VALUE1);

        HappyActivity activityFromDatabase = happyDAOFacade.getActivity(happyActivity.getId());

        assertNotNull(happyActivity);
        assertNotNull(activityFromDatabase);
        assertEquals(happyActivity, activityFromDatabase);
    }

    public void testActivityUpdate(){
        HappyActivity happyActivity = createActivity(TIMER_MOCK_VALUE1);
        validateActivity(happyActivity, TIMER_MOCK_VALUE1);

        boolean updated = happyDAOFacade.updateActivity(happyActivity.getId(), TIMER_MOCK_VALUIE2);

        assertTrue(updated);

        HappyActivity updatedHappyActivity = happyDAOFacade.getActivity(happyActivity.getId());
        validateActivity(updatedHappyActivity, TIMER_MOCK_VALUIE2);

        assertNotSame(happyActivity, updatedHappyActivity);
        assertTrue(happyActivity.getId() == updatedHappyActivity.getId());

        ArrayList<HappyActivity> activities = happyDAOFacade.getActivities();
        assertNotNull(activities);

        assertTrue(activities.size() > 0);
        assertTrue(activities.size() == 1);
    }

    public void testActivityDelete(){
        HappyActivity happyActivity = createActivity(TIMER_MOCK_VALUE1);
        validateActivity(happyActivity, TIMER_MOCK_VALUE1);

        boolean deleted = happyDAOFacade.deleteActivity(happyActivity.getId());
        assertTrue(deleted);

        ArrayList<HappyTimer> timers = happyDAOFacade.getTimers();
        assertNotNull(timers);

        assertTrue(timers.size() == 1);
    }

    private HappyActivity createActivity(long value){
        return happyDAOFacade.getActivity(happyDAOFacade.createActivity(value));
    }

    private void validateActivity(HappyActivity activity, long value){
        assertNotNull(activity);
        assertTrue(activity.getValue() == value);
    }
}