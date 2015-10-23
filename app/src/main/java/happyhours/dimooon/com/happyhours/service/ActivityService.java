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
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.data.DatabaseSessionDataProvider;
import happyhours.dimooon.com.happyhours.model.database.data.SessionDataProvider;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.timer.SessionTimer;
import happyhours.dimooon.com.happyhours.model.timer.TimerUpdatedListener;
import happyhours.dimooon.com.happyhours.view.custom.progressbar.ProgressBarModel;
import happyhours.dimooon.com.happyhours.view.custom.progressbar.ProgressBarPresenter;

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
        Toast.makeText(this, "service bind", Toast.LENGTH_SHORT).show();
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "service unbind", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "service destroy", Toast.LENGTH_SHORT).show();
        stopTimerCount();
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

    public boolean sessionExists(){
        return session!=null;
    }

    public boolean sessionActive(){ return sessionTimer!=null && sessionTimer.isActive();}

    private final class ActivityServiceHandler extends Handler {

        public ActivityServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG,"Started");

            if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).contains(getApplicationContext().getString(R.string.stored_session_id))){

                long sessionId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getLong(getApplicationContext().getString(R.string.stored_session_id), -1l);
                long activeTaskId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getLong(getString(R.string.active_task_id), -1l);

                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().remove(getApplicationContext().getString(R.string.stored_session_id));

                SessionDataProvider provider = new DatabaseSessionDataProvider(getApplicationContext());
                session = provider.getDaoFacade().getSession(sessionId);

                if(activeTaskId == -1){
                    return;
                }

                HappyTimerActivity activeActivity = provider.getTimerActivity(activeTaskId);

                Log.e(TAG,"active task: "+activeActivity);

                ProgressBarModel activeProgressModel = new ProgressBarModel(activeActivity,provider.getDaoFacade());
                ProgressBarPresenter activeProgressPresenter = new ProgressBarPresenter(activeProgressModel,null);

                if(sessionTimer!=null){
                    sessionTimer.stopTimerCount();
                }

                sessionTimer = new SessionTimer(activeProgressPresenter);
                sessionTimer.startTimerCount();

            }
        }
    }
}