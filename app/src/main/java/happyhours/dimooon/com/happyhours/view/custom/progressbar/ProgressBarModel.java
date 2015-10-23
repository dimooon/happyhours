package happyhours.dimooon.com.happyhours.view.custom.progressbar;

import android.util.Log;

import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;

/**
 * Created by dmytro on 10/23/15.
 */
public class ProgressBarModel {

    private static final int PROGRESS_MAX = 60 * 60 * 8;

    private HappyTimerActivity timerActivity;
    private HappyFacade facade;
    private int twinProgressWhenNoView = 0;

    public ProgressBarModel(HappyTimerActivity timerActivity, HappyFacade facade) {
        this.timerActivity = timerActivity;
        this.facade = facade;
    }

    public int getMaxProgress(){
        return PROGRESS_MAX;
    }

    public void storeProgress(int progress) {

        twinProgressWhenNoView = progress;

        if(timerActivity!=null&&facade!=null){
            Log.e(ProgressBarModel.class.getSimpleName(), getName() + "store progress: " + progress+" twin: "+twinProgressWhenNoView);
            facade.updateTimerActivity(timerActivity.getId(),timerActivity.getTimerId(),timerActivity.getActivityId(),progress);
        }
    }

    public String getName() {
        return timerActivity == null ? "Main Progress" : timerActivity.getTimerName();
    }

    public int getTwinProgress(){
        return twinProgressWhenNoView;
    }

    public HappyTimerActivity getTask(){
        return timerActivity;
    }
}