package happyhours.dimooon.com.happyhours.view.fragments.mainsession.startsession;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.data.SessionDataProvider;
import happyhours.dimooon.com.happyhours.tools.animation.MainSessionStartPauseAnimation;
import happyhours.dimooon.com.happyhours.view.custom.KeyboardViewPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.ISessionView;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.mainsessionaction.MainSessionToolsView;

/**
 * Created by dmytro on 10/20/15.
 */
public class StartSessionViewPresenter {

    private Activity activity;
    private StartSessionView startSessionView;
    private ISessionView sessionView;
    private MainSessionStartPauseAnimation animation;
    private MainSessionToolsView mainSessionToolsView;
    private KeyboardViewPresenter keyboardViewPresenter;
    private SessionDataProvider manager;

    public StartSessionViewPresenter(Activity activity,StartSessionView startSessionView, ISessionView sessionView,MainSessionToolsView mainSessionTools,KeyboardViewPresenter keyboardViewPresenter,SessionDataProvider manager) {
        this.startSessionView = startSessionView;
        this.sessionView = sessionView;
        this.mainSessionToolsView = mainSessionTools;
        this.keyboardViewPresenter = keyboardViewPresenter;
        this.manager = manager;
        this.activity = activity;

        createStartButtonAnimation();
    }

    public void startNewMainSession(){
        changeSessionState(true);
    }
    public void resumeMainSession(){
        changeSessionState(false);
    }

    public void startSessionWithName(){

        Log.e(StartSessionViewPresenter.class.getSimpleName(),"start session with name: "+startSessionView.getCreateSessionName().getText());

        if (TextUtils.isEmpty(startSessionView.getCreateSessionName().getText())) {
            startSessionView.getCreateSessionName().setError("No way to go without session name!");
            return;
        }

        HappySession session = manager.startNewSession(startSessionView.getCreateSessionName().getText().toString());
        startSessionView.getCreateSessionName().unbindFromKeyboard();

        sessionView.getPresenter().showSession(session);
        changeSessionState(true);

        startSessionView.getStartButton().setVisibility(View.VISIBLE);
        startSessionView.getEnterSessionNameLayout().setVisibility(View.GONE);
    }

    public void cancelStartSession(){
        startSessionView.getStartButton().setVisibility(View.VISIBLE);
        startSessionView.getEnterSessionNameLayout().setVisibility(View.GONE);
        startSessionView.getCreateSessionName().unbindFromKeyboard();
    }

    private void changeSessionState(boolean start){
        if(start){
            if(!sessionView.getPresenter().isSessionStarted()){
                showCreateSessionDialog();
                return;
            }
            animateAndStartSession();
        }else{
            handleStopSession(startSessionView.getStartButton());
        }

        mainSessionToolsView.getAddNewTimerButton().setVisibility(start ? View.VISIBLE : View.INVISIBLE);
        sessionView.getRootView().setVisibility(start ? View.VISIBLE : View.GONE);
    }

    private void showCreateSessionDialog(){

        startSessionView.getStartButton().setVisibility(View.GONE);
        startSessionView.getEnterSessionNameLayout().setVisibility(View.VISIBLE);
        startSessionView.getCreateSessionName().bindToKeyboard(keyboardViewPresenter);

    }

    private void animateAndStartSession() {
        animation.animateStartPauseButton(
                activity.findViewById(R.id.container),
                startSessionView.getStartButton(),
                mainSessionToolsView.getStopButton());

        sessionView.getPresenter().startSession();
    }

    private void handleStopSession(View startButton) {
        sessionView.getPresenter().stopSession();

        if(sessionView.getPresenter().isSessionStarted()){
            ((Button)startButton).setText("Back to Work");
        }

        startButton.setVisibility(View.VISIBLE);
    }

    private void createStartButtonAnimation() {
        animation = new MainSessionStartPauseAnimation(startSessionView.getRoot().getContext(), new MainSessionStartPauseAnimation.AnimatedViewActionListener() {
            @Override
            public void handleAnimatedViewClick() {
                resumeMainSession();
            }
        });
    }

}
