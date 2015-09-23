package happyhours.dimooon.com.happyhours.view.fragments;

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
import happyhours.dimooon.com.happyhours.view.fragments.storylog.MyStoryPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.storylog.MyStoryView;
import happyhours.dimooon.com.happyhours.view.fragments.storylog.StoryLogAdapter;

public class MyStoryFragment extends Fragment implements MyStoryView {

    private MyStoryPresenter presenter;
    private RecyclerView sessionsView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer_session_lists, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(presenter!=null){
            presenter.initView();
            presenter.showStoryLogs();
        }
    }

    @Override
    public void init(){

        sessionsView = (RecyclerView) getView().findViewById(R.id.sessions);
        sessionsView.setHasFixedSize(true);
        sessionsView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void showStoryLog(List<HappySession> sessions,SessionManager manager){
        StoryLogAdapter adapter = new StoryLogAdapter(sessions, getActivity(), manager);
        sessionsView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(MyStoryPresenter presenter) {
        this.presenter = presenter;
    }
}