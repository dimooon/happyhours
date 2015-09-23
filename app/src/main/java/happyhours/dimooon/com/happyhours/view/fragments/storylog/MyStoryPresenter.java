package happyhours.dimooon.com.happyhours.view.fragments.storylog;

import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;

public class MyStoryPresenter {

    private MyStoryView view;
    private SessionManager manager;

    public MyStoryPresenter(MyStoryView view, SessionManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public void initView(){
        view.init();
    }

    public void showStoryLogs(){
        view.showStoryLog(this.manager.getDaoFacade().getSessions(),this.manager);
    }
}
