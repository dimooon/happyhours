package happyhours.dimooon.com.happyhours.view.fragments.storylog;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;

public interface MyStoryView {
    void setPresenter(MyStoryPresenter presenter);
    RecyclerView getStoryList();
}
