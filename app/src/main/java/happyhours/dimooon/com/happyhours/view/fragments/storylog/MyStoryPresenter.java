package happyhours.dimooon.com.happyhours.view.fragments.storylog;

import android.content.Context;

import happyhours.dimooon.com.happyhours.model.database.manager.SessionModel;

public class MyStoryPresenter {

    private StoryView storyView;
    private SessionModel manager;
    private Context context;

    public MyStoryPresenter(Context context, StoryView view, SessionModel manager) {
        this.storyView = view;
        this.manager = manager;
        this.context = context;
    }

    public void showStoryLogs(){
        StoryLogAdapter adapter = new StoryLogAdapter(manager.getDaoFacade().getSessions(), context, manager);
        storyView.getStoryList().setAdapter(adapter);
    }
}
