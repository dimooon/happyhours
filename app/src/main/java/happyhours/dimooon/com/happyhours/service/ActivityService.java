package happyhours.dimooon.com.happyhours.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.timer.SessionTimer;
import happyhours.dimooon.com.happyhours.model.timer.TimerUpdatedListener;

/**
 * Created by dmytro on 10/22/15.
 */
public class ActivityService extends Service {

    private static final String TAG = ActivityService.class.getSimpleName();

    private final ActivityServiceBinder binder = new ActivityServiceBinder();
    private SessionTimer sessionTimer = null;

    private Looper serviceLooper;
    private ActivityServiceHandler activityServiceHandler;
    private HappySession session;

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ActivitServiceThread", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        serviceLooper = thread.getLooper();
        activityServiceHandler = new ActivityServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        Message msg = activityServiceHandler.obtainMessage();
        msg.arg1 = startId;
        activityServiceHandler.sendMessage(msg);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        session = null;
    }

    public HappySession getSession() {
        return session;
    }

    public class ActivityServiceBinder extends Binder {
        public ActivityService getService(){
            return ActivityService.this;
        }
    }

    public void startTimerCount() {
        sessionTimer.startTimerCount();
    }

    public void stopTimerCount() {
        if(sessionTimer!=null){
            sessionTimer.stopTimerCount();
        }
    }

    public void attach(TimerUpdatedListener timerUpdatedListener) {
        sessionTimer.attach(timerUpdatedListener);
    }

    public void initSession(HappySession session,TimerUpdatedListener timerUpdatedListener){
        this.session = session;
        sessionTimer = new SessionTimer(timerUpdatedListener);
    }

    public boolean restoreSession(){
        return session!=null;
    }

    private final class ActivityServiceHandler extends Handler {

        public ActivityServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG,"Started");

            while (true){

                Log.e(TAG,"booom");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}