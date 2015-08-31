package happyhours.dimooon.com.happyhours.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import happyhours.dimooon.com.happyhours.model.timer.TimerUpdatedListener;

public class ObservableSeekBar extends SeekBar implements TimerUpdatedListener{
    private static final String TAG = ObservableSeekBar.class.getCanonicalName();

    public ObservableSeekBar(Context context) {
        super(context);
    }
    private boolean active;

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
    public void publishValue(long value) {
        if(!active){
            return;
        }
        int progress = getProgress() + (int)value;

        Log.e(TAG,"progress: "+progress);

        setProgress(progress);
    }

    @Override
    public void active(boolean active) {
        this.active = active;
    }
}