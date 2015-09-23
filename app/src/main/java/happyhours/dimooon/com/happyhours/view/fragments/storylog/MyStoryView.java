package happyhours.dimooon.com.happyhours.view.fragments.storylog;

import java.util.List;

import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;

public interface MyStoryView {
    void setPresenter(MyStoryPresenter presenter);
    void init();
    void showStoryLog(List<HappySession> sessions,SessionManager manager);
}
