package happyhours.dimooon.com.happyhours.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.database.SessionManager;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.view.SessionView;

public class MainFragment extends Fragment {

    private View startButton;
    private View stopButton;
    private SessionView sessionView;
    private StartSessionDialog.CreateSessionDialogListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startButton = getView().findViewById(R.id.startSessionButton);
        stopButton = getView().findViewById(R.id.stopSessionButton);
        sessionView = (SessionView) getView().findViewById(R.id.sessionView);

        listener = new StartSessionDialog.CreateSessionDialogListener() {
            @Override
            public void onSessionCreated(HappySession session) {
                sessionView.setSession(MainFragment.this.getActivity(),session);
                changeSessionState(true);
            }
        };

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSessionState(R.id.startSessionButton == view.getId());
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSessionState(R.id.startSessionButton == view.getId());
            }
        });
    }

    private void changeSessionState(boolean start){

        if(start){
            if(!sessionView.isSessionStarted()){
                new StartSessionDialog().show(getActivity(),new SessionManager(getActivity()),listener);
                return;
            }
            sessionView.startSession();
        }else{
            sessionView.stopSession();

            if(sessionView.isSessionStarted()){
                ((Button)startButton).setText("Back to Work");
            }
        }

        startButton.setVisibility(start ? View.GONE : View.VISIBLE);
        stopButton.setVisibility(start ? View.VISIBLE : View.GONE);
        sessionView.setVisibility(start ? View.VISIBLE : View.GONE);
    }
}