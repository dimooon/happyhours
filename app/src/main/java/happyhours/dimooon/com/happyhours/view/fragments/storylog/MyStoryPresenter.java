package happyhours.dimooon.com.happyhours.view.fragments.storylog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class MyStoryPresenter {

    private StoryView storyView;

    private Context context;
    private StoryLogModel model;

    public MyStoryPresenter(Context context, StoryView view, StoryLogModel model) {
        this.storyView = view;
        this.context = context;
        this.model = model;
    }

    public void showStoryLogs(){
        StoryLogAdapter adapter = new StoryLogAdapter(context, model);
        storyView.getStoryList().setLayoutManager(new LinearLayoutManager(context));
        storyView.getStoryList().setAdapter(adapter);
    }
}
