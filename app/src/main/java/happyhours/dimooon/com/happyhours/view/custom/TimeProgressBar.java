package happyhours.dimooon.com.happyhours.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.timer.TimerUpdatedListener;

public class TimeProgressBar extends RelativeLayout implements TimerUpdatedListener,ProgressBar {

    private HappyTimerActivity timerActivity;
    private HappyFacade facade;
    private boolean active;

    private SeekBar progressBar;
    private TextView timeProgress;

    public TimeProgressBar(Context context) {
        super(context);
        init();
    }

    public TimeProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){
        inflate(getContext(), R.layout.timer_progress_bar_layout,this);

        progressBar = (SeekBar)findViewById(R.id.progressInBar);
        timeProgress = (TextView) findViewById(R.id.progressInTime);

    }
    @Override
    public boolean publishValue(long value) {

        int progress = progressBar.getProgress() + (int) value;

        if(!active){
            return (!active && progress > 100);
        }

        if(timerActivity!=null&&facade!=null){
            facade.updateTimerActivity(timerActivity.getId(),timerActivity.getTimerId(),timerActivity.getActivityId(),progress);
        }

        progressBar.setProgress(progress);
        updateTime(progress);

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

    @Override
    public void assignTimerActivity(HappyTimerActivity timerActivity){
        this.timerActivity = timerActivity;
    }

    @Override
    public void assignDAO(HappyFacade facade){
        this.facade = facade;
    }

    @Override
    public void restoreProgress(int progress){
        this.progressBar.setProgress(progress);
        updateTime(progress);
    }

    private void updateTime(final int progress){
        post(new Runnable() {
            @Override
            public void run() {
                int passedSeconds = progress * 60 / 100;

                int seconds = passedSeconds % 60;
                int minutes = (passedSeconds / 60) % 60;
                int hours = (passedSeconds / 3600);
                timeProgress.setText(String.format("%02dh:%02dm:%02ds", hours, minutes, seconds));
            }
        });
    }

}
