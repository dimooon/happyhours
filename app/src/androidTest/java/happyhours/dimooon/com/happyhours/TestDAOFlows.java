package happyhours.dimooon.com.happyhours;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;

public class TestDAOFlows extends AndroidTestCase{

    private static final String TEST_FILE_PREFIX = "test_";
    private static final String TAG = TestDAOFlows.class.getSimpleName();
    public static final String NEW_MOCK_FLOW_SESSION = "NEW_MOCK_FLOW_SESSION";
    public static final String CODDING = "Codding";
    private HappyFacade happyDAOFacade = null;

    public void setUp() throws Exception {

        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
        happyDAOFacade = new HappyFacade(context);

        assertNotNull(happyDAOFacade);
    }

    public void tearDown() throws Exception {
        happyDAOFacade = null;
    }

    public void testAddTimers(){

        ArrayList<HappyTimer> timers = getValidatedTimerList();

        assertNotNull(timers);
        assertTrue(timers.size() == 3);
    }

    public void testCreateTimerActivityAndAssignTimers(){

        long sessionId = happyDAOFacade.createSession("NEW_MOCK_FLOW_SESSION");
        HappySession session = happyDAOFacade.getSession(sessionId);



    }

    public void testCreateSession(){
        long id = happyDAOFacade.createSession(NEW_MOCK_FLOW_SESSION);

        HappySession session = happyDAOFacade.getSession(id);

        assertNotNull(session);

        assertEquals(NEW_MOCK_FLOW_SESSION, session.getName());
    }

    private ArrayList<HappyTimer> getValidatedTimerList(){
        long timerCodingId = happyDAOFacade.createTimer(CODDING);
        long timerVKId = happyDAOFacade.createTimer("VK");
        long timerEspressoTvId = happyDAOFacade.createTimer("EspressoTV");

        HappyTimer timerCoding = happyDAOFacade.getTimer(timerCodingId);
        HappyTimer timerVK = happyDAOFacade.getTimer(timerVKId);
        HappyTimer timerEspressoTV = happyDAOFacade.getTimer(timerEspressoTvId);

        assertNotNull(timerCoding);
        assertNotNull(timerVK);
        assertNotNull(timerEspressoTV);

        ArrayList<HappyTimer> timers = new ArrayList<>();
        timers.add(timerCoding);
        timers.add(timerVK);
        timers.add(timerEspressoTV);
        return timers;
    }

}