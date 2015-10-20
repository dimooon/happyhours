package happyhours.dimooon.com.happyhours.view.fragments.storylog;

import android.support.v7.widget.RecyclerView;

public interface StoryView {
    void setPresenter(MyStoryPresenter presenter);
    MyStoryPresenter getPresenter();
    RecyclerView getStoryList();
    void showStoryLogs();
}
