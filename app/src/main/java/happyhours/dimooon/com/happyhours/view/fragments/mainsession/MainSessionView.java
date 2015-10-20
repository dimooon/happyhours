package happyhours.dimooon.com.happyhours.view.fragments.mainsession;

import android.view.View;

import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.ISessionView;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.ActionToolBar;

public interface MainSessionView {

    void setPresenter(MainSessionPresenter presenter);
    View getStopSessionButton();
    View getStartSessionButton();
    View getAddNewTimerButton();
    ISessionView getSessionView();
    View getStartSessionLayout();
    ActionToolBar getToolbar();
}
