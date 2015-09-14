package happyhours.dimooon.com.happyhours.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import happyhours.dimooon.com.happyhours.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.timer.TimerUpdatedListener;

public class ObservableSeekBar extends SeekBar implements TimerUpdatedListener{
    private static final String TAG = ObservableSeekBar.class.getCanonicalName();

    private HappyTimerActivity timerActivity;
    private HappyFacade facade;

    public ObservableSeekBar(Context context) {
        super(context);
    }
    private boolean active;

    public void assignTimerActivity(HappyTimerActivity timerActivity){
        this.timerActivity = timerActivity;
    }

    public void assignDAO(HappyFacade facade){
        this.facade = facade;
    }

    public ObservableSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ObservableSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean publishValue(long value) {

        int progress = getProgress() + (int) value;

        if(!active){
            return !active || progress > 100;
        }

        Log.e(TAG, "progress: " + progress);

        if(timerActivity!=null&&facade!=null){
            facade.updateTimerActivity(timerActivity.getId(),timerActivity.getTimerId(),timerActivity.getActivityId(),progress);
        }

        setProgress(progress);

        return progress > 100;
    }

    @Override
    public void active(boolean active) {
        this.active = active;
    }
}