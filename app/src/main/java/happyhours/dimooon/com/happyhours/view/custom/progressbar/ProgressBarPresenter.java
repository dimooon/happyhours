package happyhours.dimooon.com.happyhours.view.custom.progressbar;

import happyhours.dimooon.com.happyhours.model.timer.TimerUpdatedListener;

/**
 * Created by dmytro on 10/23/15.
 */
public class ProgressBarPresenter implements TimerUpdatedListener{
    private ProgressBarModel model;
    private ProgressBar progressBarView;

    public ProgressBarPresenter(ProgressBarModel model, ProgressBar progressBarView) {
        this.model = model;
        this.progressBarView = progressBarView;
    }

    @Override
    public void publishValue(long value) {

        model.storeProgress((int) value);

        if(progressBarView!=null){
            progressBarView.publishValue((int) value);
        }
    }
}