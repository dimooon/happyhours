package happyhours.dimooon.com.happyhours;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;

public class TestTimerDAO extends AndroidTestCase{

    private static final String TEST_FILE_PREFIX = "test_";
    private static final String TAG = TestTimerDAO.class.getSimpleName();
    private HappyFacade happyDAOFacade = null;

    private static final String TIMER_MOCK_NAME1 = "TIMER_MOCK_1";
    private static final String TIMER_MOCK_NAME2 = "TIMER_MOCK_2";

    public void setUp() throws Exception {

        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
        happyDAOFacade = new HappyFacade(context);

        assertNotNull(happyDAOFacade);
    }

    public void tearDown() throws Exception {
        happyDAOFacade = null;
    }

    public void testTimerCreate(){
        HappyTimer happyTimer = createTimer(TIMER_MOCK_NAME1,false);
        validateTimer(happyTimer,TIMER_MOCK_NAME1);
    }

    public void testTimerReadAll(){

        HappyTimer happyTimer = createTimer(TIMER_MOCK_NAME1,false);
        validateTimer(happyTimer, TIMER_MOCK_NAME1);

        HappyTimer happyTimer2 = createTimer(TIMER_MOCK_NAME2,false);
        validateTimer(happyTimer2, TIMER_MOCK_NAME2);

        ArrayList<HappyTimer> timers = happyDAOFacade.getTimers();
        assertNotNull(timers);

        Log.e(TAG,"timers: "+timers);

        assertTrue(timers.size() > 0);
        assertTrue(timers.size() == 3);

        assertNotSame(happyTimer, happyTimer2);
    }

    public void testTimerRead(){
        HappyTimer happyTimer = createTimer(TIMER_MOCK_NAME1,false);
        validateTimer(happyTimer,TIMER_MOCK_NAME1);

        HappyTimer timerFromDatabase = happyDAOFacade.getTimer(happyTimer.getId());

        assertNotNull(happyTimer);
        assertNotNull(timerFromDatabase);
        assertEquals(happyTimer, timerFromDatabase);
    }

    public void testTimerUpdate(){
        HappyTimer happyTimer = createTimer(TIMER_MOCK_NAME1,false);
        validateTimer(happyTimer,TIMER_MOCK_NAME1);

        boolean updated = happyDAOFacade.updateTimer(happyTimer.getId(), TIMER_MOCK_NAME2);

        assertTrue(updated);

        HappyTimer updatedHappyTimer = happyDAOFacade.getTimer(happyTimer.getId());
        validateTimer(updatedHappyTimer,TIMER_MOCK_NAME2);

        assertNotSame(happyTimer, updatedHappyTimer);
        assertTrue(happyTimer.getId() == updatedHappyTimer.getId());

        ArrayList<HappyTimer> timers = happyDAOFacade.getTimers();
        assertNotNull(timers);

        assertTrue(timers.size() > 0);
        assertTrue(timers.size() == 2);
    }

    public void testTimerDelete(){
        HappyTimer happyTimer = createTimer(TIMER_MOCK_NAME1,false);
        validateTimer(happyTimer,TIMER_MOCK_NAME1);

        ArrayList<HappyTimer> timers = happyDAOFacade.getTimers();
        assertNotNull(timers);

        assertTrue(timers.size() == 2);

        boolean deleted = happyDAOFacade.deleteTimer(happyTimer.getId());
        assertTrue(deleted);

        timers = happyDAOFacade.getTimers();
        assertNotNull(timers);

        assertTrue(timers.size() == 1);
    }

    private HappyTimer createTimer(String name,boolean happy){

        HappyTimer timer = happyDAOFacade.getTimer(happyDAOFacade.createTimer(name,happy));

        return timer;
    }

    private void validateTimer(HappyTimer timer,String name){
        assertNotNull(timer);
        assertEquals(name,timer.getName());
    }
}