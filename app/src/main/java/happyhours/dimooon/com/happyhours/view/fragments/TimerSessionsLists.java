package happyhours.dimooon.com.happyhours.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.manager.DatabaseSessionManager;
import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.view.fragments.adapters.SessionsAdapter;
import happyhours.dimooon.com.happyhours.view.fragments.adapters.TimersAdapter;

public class TimerSessionsLists extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer_session_lists, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView(){
        ArrayList<HappyTimer> timers = new ArrayList<HappyTimer>();

        RecyclerView sessionsView = (RecyclerView) getView().findViewById(R.id.sessions);
        sessionsView.setHasFixedSize(true);
        sessionsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sessionsView.setAdapter(new SessionsAdapter(new HappyFacade(getActivity()).getSessions(), getActivity(), new DatabaseSessionManager(getActivity())));
    }

}