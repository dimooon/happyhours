package happyhours.dimooon.com.happyhours.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.timer.TimerUpdatedListener;

public class ObservableSeekBar extends SeekBar implements TimerUpdatedListener,ProgressBar {

    private static final String TAG = ObservableSeekBar.class.getSimpleName();
    private HappyTimerActivity timerActivity;
    private HappyFacade facade;
    private boolean active;

    public ObservableSeekBar(Context context) { super(context); }
    public ObservableSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void assignTimerActivity(HappyTimerActivity timerActivity){
        this.timerActivity = timerActivity;
    }

    @Override
    public void assignDAO(HappyFacade facade){
        this.facade = facade;
    }


    @Override
    public boolean publishValue(long value) {

        int progress = getProgress() + (int) value;

        if(!active){
            return (!active && progress > 100);
        }

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

    @Override
    public String getName() {
        return timerActivity == null ? "Main Progress" : timerActivity.getTimerName();
    }
}