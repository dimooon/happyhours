package happyhours.dimooon.com.happyhours.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.manager.DatabaseSessionManager;
import happyhours.dimooon.com.happyhours.tools.animation.DropDownAnim;
import happyhours.dimooon.com.happyhours.tools.animation.HeightAnimation;
import happyhours.dimooon.com.happyhours.tools.animation.ZoomTranslateAnimation;
import happyhours.dimooon.com.happyhours.view.dialog.CreateTimerController;
import happyhours.dimooon.com.happyhours.view.dialog.StartSessionDialog;
import happyhours.dimooon.com.happyhours.view.session.SessionView;

public class MainFragment extends Fragment {

    private SessionView sessionView;
    private ZoomTranslateAnimation animation;
    private Toolbar toolbar;

    private View startButton;
    private View stopButton;
    private View addNewTimerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();

        initAnimation();
    }

    private void initView(){

        toolbar = ((Toolbar) getActivity().findViewById(R.id.toolbar));
        ((TextView)toolbar.findViewById(R.id.custom_toolbar_title)).setText("< ------------ back log -");

        addNewTimerButton = ((Button)toolbar.findViewById(R.id.timersListButton));
        addNewTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HeightAnimation anim = new HeightAnimation(toolbar,toolbar.getHeight(),toolbar.getHeight() + toolbar.getHeight()*4);
                anim.setDuration(400);

                sessionView.showAddTimerDialog();

                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        toolbar.findViewById(R.id.toolbar_add_another_layout).setVisibility(View.VISIBLE);
                        
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                toolbar.startAnimation(anim);
            }
        });

        startButton = getView().findViewById(R.id.startSessionButton);
        stopButton = toolbar.findViewById(R.id.stopSessionButton);

        sessionView = (SessionView) getView().findViewById(R.id.sessionView);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSessionState(R.id.startSessionButton == view.getId(), startButton, stopButton);
            }
        });
    }

    private void initAnimation() {
        animation = new ZoomTranslateAnimation(getActivity(), new ZoomTranslateAnimation.AnimatedViewActionListener() {
            @Override
            public void handleAnimatedViewClick() {
                changeSessionState(false, startButton, stopButton);
            }
        });
    }

    private void changeSessionState(boolean start, final View startButton, final View stopButton){

        if(start){
            if(!sessionView.isSessionStarted()){
                showCreateSessionDialog();
                return;
            }

            animateAndStartSession();
        }else{
            handleStopSession(startButton);
        }

        addNewTimerButton.setVisibility(start ? View.VISIBLE : View.INVISIBLE);
        sessionView.setVisibility(start ? View.VISIBLE : View.INVISIBLE);
    }

    private void showCreateSessionDialog(){
        new StartSessionDialog().show(getActivity(), new DatabaseSessionManager(getActivity()), new StartSessionDialog.CreateSessionDialogListener() {

            @Override
            public void onSessionCreated(HappySession session) {
                sessionView.setSession(MainFragment.this.getActivity(), session);
                changeSessionState(true, startButton, stopButton);
            }
        });
        return;
    }

    private void animateAndStartSession() {
        animation.zoomImageFromThumb(getActivity().findViewById(R.id.container), startButton, stopButton);

        sessionView.startSession();
    }

    private void handleStopSession(View startButton) {
        sessionView.stopSession();

        if(sessionView.isSessionStarted()){
            ((Button)startButton).setText("Back to Work");
        }

        startButton.setVisibility(View.VISIBLE);
    }
}