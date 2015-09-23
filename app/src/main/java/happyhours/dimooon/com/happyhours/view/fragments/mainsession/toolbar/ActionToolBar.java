package happyhours.dimooon.com.happyhours.view.fragments.mainsession.toolbar;

import android.app.Activity;

public interface ActionToolBar {

    void setPresenter(ToolbarPresenter presenter);
    void initView(Activity activity);
    void updateTitle(String title);
    void changeVisibilityForAddButton(boolean show);
    void showAddTimerBar(boolean show);
    int getHeight();
}
