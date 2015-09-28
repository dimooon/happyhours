package happyhours.dimooon.com.happyhours.view.fragments.mainsession;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;
import happyhours.dimooon.com.happyhours.tools.animation.HeightAnimation;
import happyhours.dimooon.com.happyhours.tools.animation.ZoomTranslateAnimation;
import happyhours.dimooon.com.happyhours.view.dialog.StartSessionDialog;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.SessionViewPresenter;

public class MainSessionPresenter {

    private SessionViewPresenter sessionViewPresenter;
    private SessionManager manager;
    private ZoomTranslateAnimation animation;
    private Activity activity;
    private MainSessionView mainSessionView;

    public MainSessionPresenter(Activity activity,
                                MainSessionView mainSessionView,
                                SessionViewPresenter sessionViewPresenter,
                                SessionManager manager) {

        this.sessionViewPresenter = sessionViewPresenter;
        this.manager = manager;
        this.activity = activity;
        this.mainSessionView = mainSessionView;

        createStartButtonAnimation();
    }

    public void changeSessionState(boolean start, final View startButton, final View stopButton){
        if(start){
            if(!sessionViewPresenter.isSessionStarted()){
                showCreateSessionDialog();
                return;
            }
            mainSessionView.getToolbar().show();
            animateAndStartSession();
        }else{
            handleStopSession(startButton);
            mainSessionView.getToolbar().hide();
        }

        mainSessionView.getAddNewTimerButton().setVisibility(start ? View.VISIBLE : View.INVISIBLE);
        sessionViewPresenter.getSessionView().getRootView().setVisibility(start ? View.VISIBLE : View.INVISIBLE);
    }

    public boolean sessionStarted(){
        return sessionViewPresenter.isSessionStarted();
    }

    private void showCreateSessionDialog(){
        new StartSessionDialog().show(activity, manager, new StartSessionDialog.CreateSessionDialogListener() {

            @Override
            public void onSessionCreated(HappySession session) {
                sessionViewPresenter.showSession(session);
                changeSessionState(true, mainSessionView.getStartSessionButton(), mainSessionView.getStopSessionButton());
            }
        });
        return;
    }

    private void animateAndStartSession() {
        animation.zoomImageFromThumb(
                activity.findViewById(R.id.container),
                mainSessionView.getStartSessionButton(),
                mainSessionView.getStopSessionButton());

        sessionViewPresenter.startSession();
    }

    private void handleStopSession(View startButton) {
        sessionViewPresenter.stopSession();

        if(sessionViewPresenter.isSessionStarted()){
            ((Button)startButton).setText("Back to Work");
        }

        startButton.setVisibility(View.VISIBLE);
    }

    private void createStartButtonAnimation() {
        animation = new ZoomTranslateAnimation(activity, new ZoomTranslateAnimation.AnimatedViewActionListener() {
            @Override
            public void handleAnimatedViewClick() {
                changeSessionState(false, mainSessionView.getStartSessionButton(), mainSessionView.getStopSessionButton());
            }
        });
    }
}