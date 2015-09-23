package happyhours.dimooon.com.happyhours.view.fragments.mainsession.session;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import happyhours.dimooon.com.happyhours.view.custom.TimeProgressBar;

public interface ISessionView {

    void setPresenter(SessionViewPresenter presenter);
    TextView getCaption();
    TimeProgressBar getMainProgressBar();
    RecyclerView getSessionTimersList();
    View getRootView();

}
