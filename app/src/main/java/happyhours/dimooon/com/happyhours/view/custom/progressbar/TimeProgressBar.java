package happyhours.dimooon.com.happyhours.view.custom.progressbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.tools.DateUtils;

public class TimeProgressBar extends RelativeLayout implements ProgressBar {

    private SeekBar progressBar;
    private TextView timeProgress;

    private ProgressBarModel model;
    private ProgressBarPresenter presenter;

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
        inflate(getContext(), R.layout.timer_progress_bar_layout, this);

        progressBar = (SeekBar)findViewById(R.id.progressInBar);
        timeProgress = (TextView) findViewById(R.id.progressInTime);
    }

    public void setModel(ProgressBarModel model){
        this.model = model;

        progressBar.setMax(model.getMaxProgress());
        progressBar.setEnabled(false);
    }

    public void setPresenter(ProgressBarPresenter presenter){
        this.presenter = presenter;
    }

    public ProgressBarPresenter getPresenter(){
        return presenter;
    }

    @Override
    public ProgressBarModel getModel() {
        return this.model;
    }

    public void publishValue(long value) {

        int progress = progressBar.getProgress() + (int) value;

        model.storeProgress(progress);

        progressBar.setProgress(progress);
        updateTime(progress);
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
                timeProgress.setText(DateUtils.getTimeProgress(progress));
            }
        });
    }

}