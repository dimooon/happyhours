package happyhours.dimooon.com.happyhours.database.facade.interfaces;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;

public interface Timer {

    long createTimer(String name);
    HappyTimer getTimer(long id);
    ArrayList<HappyTimer> getTimers();
    boolean updateTimer(long id,String name);
    boolean deleteTimer(long id);

}
