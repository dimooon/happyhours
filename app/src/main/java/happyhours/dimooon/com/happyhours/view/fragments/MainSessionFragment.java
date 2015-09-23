package happyhours.dimooon.com.happyhours.view.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.manager.DatabaseSessionManager;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.ISessionView;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.SessionViewPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.toolbar.ActionToolBar;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.toolbar.ToolbarPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.MainSessionPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.MainSessionView;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.SessionView;

@SuppressLint("ValidFragment")
public class MainSessionFragment extends Fragment implements MainSessionView {

    private View startButton;
    private View stopButton;
    private View addNewTimerButton;

    private ActionToolBar toolbar;

    private MainSessionPresenter presenter;
    private ISessionView sessionView;

    private Activity activity;
    private SessionManager manager;

    @SuppressLint("ValidFragment")
    public MainSessionFragment(Activity activity, SessionManager manager) {
        super();

        this.activity = activity;
        this.manager = manager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initPresenter();
    }

    private void initView(){

        startButton = getActivity().findViewById(R.id.startSessionButton);
        stopButton = getActivity().findViewById(R.id.stopSessionButton);

        addNewTimerButton = getActivity().findViewById(R.id.timersListButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter != null) {
                    presenter.changeSessionState(true, startButton, stopButton);
                }

            }
        });

        toolbar = ((ActionToolBar) getActivity().findViewById(R.id.toolbar));
        sessionView = (SessionView) getView().findViewById(R.id.sessionView);
    }

    private void initPresenter(){
        SessionViewPresenter sessionViewPresenter = new SessionViewPresenter(activity,getSessionView(),manager);
        getSessionView().setPresenter(sessionViewPresenter);

        ToolbarPresenter toolbarPresenter = new ToolbarPresenter(getToolbar(),sessionViewPresenter);
        getToolbar().setPresenter(toolbarPresenter);

        MainSessionPresenter presenter = new MainSessionPresenter(activity,this, sessionViewPresenter,manager);
        setPresenter(presenter);
    }

    @Override
    public void setPresenter(MainSessionPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View getStopSessionButton() {
        return stopButton;
    }

    @Override
    public View getStartSessionButton() {
        return startButton;
    }

    @Override
    public View getAddNewTimerButton() {
        return addNewTimerButton;
    }

    @Override
    public ISessionView getSessionView() { return sessionView;  }

    @Override
    public ActionToolBar getToolbar(){ return toolbar;  }
}