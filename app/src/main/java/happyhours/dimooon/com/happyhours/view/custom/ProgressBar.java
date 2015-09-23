package happyhours.dimooon.com.happyhours.view.custom;

import android.widget.SeekBar;

import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;

public interface ProgressBar {
    void assignTimerActivity(HappyTimerActivity timerActivity);
    void assignDAO(HappyFacade facade);
    void restoreProgress(int progress);
}
