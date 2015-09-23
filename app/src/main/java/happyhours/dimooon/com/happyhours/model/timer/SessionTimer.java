package happyhours.dimooon.com.happyhours.model.timer;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import happyhours.dimooon.com.happyhours.view.custom.TimeProgressBar;

public class SessionTimer{

    private static final String TAG = SessionTimer.class.getSimpleName();
    private HashSet<TimerUpdatedListener> listeners = new HashSet<>();
    private Timer timerCountTask = null;
    private TimerUpdatedListener mainSessionTimer;

    public SessionTimer(TimerUpdatedListener mainSessionTimer){
        this.mainSessionTimer = mainSessionTimer;
        this.mainSessionTimer.active(true);
        attach(this.mainSessionTimer);
    }

    public void attach(TimerUpdatedListener activityTimer){
        this.listeners.add(activityTimer);
    }

    public void start(TimeProgressBar listener){

        for(TimerUpdatedListener currentUpdateListener : listeners){

            boolean activate = currentUpdateListener.equals(listener)||currentUpdateListener.equals(mainSessionTimer);

            currentUpdateListener.active(activate);
        }
    }

    public void stop(TimerUpdatedListener activityTimer){
        this.listeners.remove(activityTimer);
    }

    public void update(long value) {

        for(TimerUpdatedListener observers : listeners){
            boolean timeIsOver = observers.publishValue(value);

            if (timeIsOver){
                stopTimerCount();
                return;
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