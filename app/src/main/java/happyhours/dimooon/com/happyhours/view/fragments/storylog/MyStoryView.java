package happyhours.dimooon.com.happyhours.view.fragments.storylog;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import happyhours.dimooon.com.happyhours.R;

/**
 * Created by dmytro on 10/16/15.
 */
public class MyStoryView implements StoryView {

    private final RecyclerView sessionsView;
    private MyStoryPresenter presenter;

    public MyStoryView(Activity activity) {
        sessionsView = (RecyclerView) activity.findViewById(R.id.sessions);
        sessionsView.setHasFixedSize(true);
        sessionsView.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void setPresenter(MyStoryPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public MyStoryPresenter getPresenter() {
        return this.presenter;
    }

    @Override
    public RecyclerView getStoryList() {
        return sessionsView;
    }

    @Override
    public void showStoryLogs() {
        if(this.presenter!=null){
            this.presenter.showStoryLogs();
        }
    }

}
