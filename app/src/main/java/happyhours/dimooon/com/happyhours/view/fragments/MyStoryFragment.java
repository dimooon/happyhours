package happyhours.dimooon.com.happyhours.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.toolbar.ActionToolBar;
import happyhours.dimooon.com.happyhours.view.fragments.storylog.MyStoryPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.storylog.MyStoryView;
import happyhours.dimooon.com.happyhours.view.fragments.storylog.StoryLogAdapter;

@SuppressLint("ValidFragment")
public class MyStoryFragment extends Fragment implements MyStoryView,SelectableFragment {

    private MyStoryPresenter presenter;
    private RecyclerView sessionsView;

    private SessionManager manager;
    private ActionToolBar toolbar;

    @SuppressLint("ValidFragment")
    public MyStoryFragment(SessionManager manager) {
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

        init();
        initPresenter();

        if(presenter!=null){
            presenter.showStoryLogs();
        }

    }

    private void init(){
        sessionsView = (RecyclerView) getView().findViewById(R.id.sessions);
        sessionsView.setHasFixedSize(true);
        sessionsView.setLayoutManager(new LinearLayoutManager(getActivity()));

        toolbar = ((ActionToolBar) getActivity().findViewById(R.id.toolbar));
    }

    private void initPresenter(){
        MyStoryPresenter storyPresenter = new MyStoryPresenter(getActivity(),this,manager);
        this.setPresenter(storyPresenter);
    }

    @Override
    public void setPresenter(MyStoryPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public RecyclerView getStoryList() {
        return sessionsView;
    }

    @Override
    public void onSelected() {
        toolbar.updateTitle("Main Story");
    }
}