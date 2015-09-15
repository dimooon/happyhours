package happyhours.dimooon.com.happyhours.model.database.facade.interfaces;


import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;

public interface Session {

    long createSession (String name);

    HappySession getSession(long id);
    ArrayList<HappySession> getSessions();

    boolean updateSessionName(long id,String name);
    boolean deleteSession(long id);
}
