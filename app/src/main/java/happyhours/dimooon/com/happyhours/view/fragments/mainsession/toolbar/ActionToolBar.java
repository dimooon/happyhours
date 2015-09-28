package happyhours.dimooon.com.happyhours.view.fragments.mainsession.toolbar;

import android.app.Activity;

public interface ActionToolBar {

    void setPresenter(ToolbarPresenter presenter);
    void updateTitle(String title);
    void changeVisibilityForAddButton(boolean show);
    void showAddTimerBar(boolean show);
    int getHeight();
    void hide();
    void show();
}
