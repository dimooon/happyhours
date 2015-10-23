package happyhours.dimooon.com.happyhours.model.timer;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class SessionTimer{

    private static final String TAG = SessionTimer.class.getSimpleName();
    private HashSet<TimerUpdatedListener> listeners = new HashSet<>();
    private Timer timerCountTask = null;
    private TimerUpdatedListener mainSessionTimer;
    private TimerUpdatedListener lastTimerUpdateListener;

    public SessionTimer(TimerUpdatedListener mainSessionTimer){
        this.mainSessionTimer = mainSessionTimer;
        attach(this.mainSessionTimer);
    }

    public void attach(TimerUpdatedListener activityTimer){
        if(activityTimer == null){
            return;
        }
        this.listeners.add(activityTimer);
        this.lastTimerUpdateListener = activityTimer;
    }

    public void stop(TimerUpdatedListener activityTimer){
        this.listeners.remove(activityTimer);
    }

    public void update(long value) {

        for(TimerUpdatedListener observers : listeners){

            boolean activate = lastTimerUpdateListener.equals(observers)||observers.equals(mainSessionTimer);

            if(activate){
                observers.publishValue(value);
            }
        }
    }

    public void startTimerCount() {
        timerCountTask = new Timer();
        timerCountTask.schedule(new TimerTask() {
            @Override
            public void run() {
                update(1);
            }
        },1000,1000);
    }

    public void stopTimerCount(){
        if(timerCountTask!=null){
            timerCountTask.cancel();
            timerCountTask.purge();
            timerCountTask = null;
        }
    }
}