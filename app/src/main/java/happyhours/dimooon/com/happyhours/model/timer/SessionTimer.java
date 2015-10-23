package happyhours.dimooon.com.happyhours.model.timer;

import android.util.Log;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class SessionTimer{

    private static final String TAG = SessionTimer.class.getSimpleName();
    private HashSet<TimerUpdatedListener> listeners = new HashSet<>();
    private static Timer timerCountTask = null;
    private TimerUpdatedListener mainSessionTimer;
    private TimerUpdatedListener lastTimerUpdateListener;
    private boolean active = false;

    public SessionTimer(TimerUpdatedListener mainSessionTimer){
        this.mainSessionTimer = mainSessionTimer;
        attach(this.mainSessionTimer);
    }

    public void attach(TimerUpdatedListener activityTimer){

        Log.e(TAG,"attach: "+(activityTimer!=null));

        if(activityTimer == null){
            return;
        }

        this.listeners.add(activityTimer);
        this.lastTimerUpdateListener = activityTimer;
    }

    public void stop(TimerUpdatedListener activityTimer){
        this.listeners.remove(activityTimer);
    }

    public boolean isActive(){
        return active;
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
        if(timerCountTask!=null){
            stopTimerCount();
        }
        timerCountTask = new Timer();
        timerCountTask.schedule(new TimerTask() {
            @Override
            public void run() {
                update(1);
            }
        },1000,1000);
        active = true;
    }

    public void stopTimerCount(){

        Log.e(TAG,"stop timer: "+timerCountTask);

        if(timerCountTask!=null){
            timerCountTask.cancel();
            timerCountTask.purge();
            timerCountTask = null;
            active = false;
        }
    }
}