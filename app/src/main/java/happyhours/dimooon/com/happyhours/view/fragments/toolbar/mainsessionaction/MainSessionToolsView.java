package happyhours.dimooon.com.happyhours.view.fragments.toolbar.mainsessionaction;

import android.view.View;

/**
 * Created by dmytro on 10/20/15.
 */
public interface MainSessionToolsView {

    public void setPresenter(MainSessionToolPresenter presenter);
    View getStopButton();
    View getAddNewTimerButton();

}
