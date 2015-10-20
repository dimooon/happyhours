package happyhours.dimooon.com.happyhours.view.fragments.toolbar;

public interface ActionToolBar {

    void setPresenter(ToolbarPresenter presenter);
    void updateTitle(String title);
    void changeVisibilityForAddButton(boolean show);
    void showAddTimerBar(boolean show);
    int getHeight();
    void hide();
    void show();
    void showMyStoryTool();
    void showMainSessionTool();
}
