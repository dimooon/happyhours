package happyhours.dimooon.com.happyhours.view.fragments.mainsession.session;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import happyhours.dimooon.com.happyhours.view.custom.ObservableSeekBar;

public interface ISessionView {

    void setPresenter(SessionViewPresenter presenter);
    TextView getCaption();
    ObservableSeekBar getMainProgressBar();
    RecyclerView getSessionTimersList();
    View getRootView();

}
