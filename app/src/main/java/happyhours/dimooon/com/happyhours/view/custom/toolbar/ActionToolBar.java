package happyhours.dimooon.com.happyhours.view.custom.toolbar;

import android.app.Activity;

import happyhours.dimooon.com.happyhours.view.session.SessionView;

public interface ActionToolBar {

    public void initView(Activity activity);
    public void updateTitle(String title);
    public void changeVisibilityForAddButton(boolean show);
    public void addView();
    public void expand();
    public void collapse();
    public void showAddTimerBar(boolean show);
    public void setSessionView(SessionView sessionView);
}
