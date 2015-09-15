package happyhours.dimooon.com.happyhours;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;

public class TestSessionDAO extends AndroidTestCase{

    private static final String TAG = TestSessionDAO.class.getSimpleName();
    private static final String TEST_FILE_PREFIX = "test_";

    public static final String MOCK_SESSION_NAME = "MOCK_SESSION_NAME";
    private static final String MOCK_SESSION_NAME_1 = "MOCK_SESSION_NAME_1";
    public static final int ACTIVITY_VALUE = 1000;
    public static final int NEW_VALUE = 10023;
    private HappyFacade happyDAOFacade = null;

    public void setUp() throws Exception {

        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
        happyDAOFacade = new HappyFacade(context);

        assertNotNull(happyDAOFacade);
    }

    public void tearDown() throws Exception {
        happyDAOFacade = null;
    }

    public void testCreateSession(){
        HappySession session = createSession(MOCK_SESSION_NAME);
        validateSession(session,MOCK_SESSION_NAME);
    }

    public void testGetSession(){
        HappySession session = createSession(MOCK_SESSION_NAME);
        validateSession(session,MOCK_SESSION_NAME);


        HappySession sessionFromDatabase = happyDAOFacade.getSession(session.getId());
        validateSession(sessionFromDatabase,MOCK_SESSION_NAME);
    }

    public void testGetSessions(){
        HappySession session = createSession(MOCK_SESSION_NAME);
        validateSession(session,MOCK_SESSION_NAME);

        HappySession session1 = createSession(MOCK_SESSION_NAME_1);
        validateSession(session1,MOCK_SESSION_NAME_1);

        ArrayList<HappySession> sessions = happyDAOFacade.getSessions();

        assertTrue(sessions.size() > 0);
        assertTrue(sessions.size() == 2);

    }

    public void testUpdateSessionName(){
        HappySession session = createSession(MOCK_SESSION_NAME);
        validateSession(session,MOCK_SESSION_NAME);

        happyDAOFacade.updateSessionName(session.getId(), MOCK_SESSION_NAME_1);

        HappySession session1 = happyDAOFacade.getSession(session.getId());

        assertEquals(MOCK_SESSION_NAME_1, session1.getName());
        assertFalse(session.equals(session1));
    }

    public void testDeleteSession(){
        HappySession session = createSession(MOCK_SESSION_NAME);
        validateSession(session, MOCK_SESSION_NAME);

        boolean deleted = happyDAOFacade.deleteSession(session.getId());
        assertTrue(deleted);
    }

    private HappySession createSession(String name){

        HappySession session = happyDAOFacade.getSession(happyDAOFacade.createSession(name));

        return session;
    }

    private void validateSession(HappySession session,String name){
        assertNotNull(session);
        assertEquals(name, session.getName());
    }
}