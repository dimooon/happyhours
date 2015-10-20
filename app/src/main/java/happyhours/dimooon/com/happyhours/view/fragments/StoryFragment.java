package happyhours.dimooon.com.happyhours.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;
import happyhours.dimooon.com.happyhours.view.fragments.storylog.StoryView;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.ActionToolBar;
import happyhours.dimooon.com.happyhours.view.fragments.storylog.MyStoryPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.storylog.MyStoryView;

@SuppressLint("ValidFragment")
public class StoryFragment extends Fragment implements SelectableFragment {

    private SessionManager manager;
    private ActionToolBar toolbar;

    @SuppressLint("ValidFragment")
    public StoryFragment(SessionManager manager) {
        super();
        this.manager = manager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer_session_lists, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StoryView myStoryView = new MyStoryView(getActivity());
        MyStoryPresenter storyPresenter = new MyStoryPresenter(getActivity(),myStoryView,manager);
        myStoryView.setPresenter(storyPresenter);

        myStoryView.showStoryLogs();

        toolbar = ((ActionToolBar) getActivity().findViewById(R.id.toolbar));
    }

    @Override
    public void onSelected() {
        toolbar.updateTitle("Main Story");
        toolbar.showMyStoryTool();
    }
}