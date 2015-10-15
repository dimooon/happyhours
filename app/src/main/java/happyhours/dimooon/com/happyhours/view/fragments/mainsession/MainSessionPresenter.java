package happyhours.dimooon.com.happyhours.view.fragments.mainsession;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;
import happyhours.dimooon.com.happyhours.tools.animation.ZoomTranslateAnimation;
import happyhours.dimooon.com.happyhours.view.custom.HappyEditText;
import happyhours.dimooon.com.happyhours.view.custom.KeyboardViewPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.SessionViewPresenter;

public class MainSessionPresenter {

    private SessionViewPresenter sessionViewPresenter;
    private SessionManager manager;
    private ZoomTranslateAnimation animation;
    private Activity activity;
    private MainSessionView mainSessionView;
    private KeyboardViewPresenter keyboardViewPresenter;

    public MainSessionPresenter(Activity activity,
                                MainSessionView mainSessionView,
                                SessionViewPresenter sessionViewPresenter,
                                SessionManager manager,
                                KeyboardViewPresenter keyboardViewPresenter) {

        this.sessionViewPresenter = sessionViewPresenter;
        this.manager = manager;
        this.activity = activity;
        this.mainSessionView = mainSessionView;
        this.keyboardViewPresenter = keyboardViewPresenter;

        createStartButtonAnimation();

        mainSessionView.getToolbar().show();
    }

    public void changeSessionState(boolean start, final View startButton, final View stopButton){
        if(start){
            if(!sessionViewPresenter.isSessionStarted()){
                showCreateSessionDialog();
                return;
            }
            animateAndStartSession();
        }else{
            handleStopSession(startButton);
        }

        mainSessionView.getAddNewTimerButton().setVisibility(start ? View.VISIBLE : View.INVISIBLE);
        sessionViewPresenter.getSessionView().getRootView().setVisibility(start ? View.VISIBLE : View.INVISIBLE);
    }

    public boolean sessionStarted(){
        return sessionViewPresenter.isSessionStarted();
    }

    private void showCreateSessionDialog(){

        mainSessionView.getStartSessionButton().setVisibility(View.GONE);
        mainSessionView.getStartSessionLayout().setVisibility(View.VISIBLE);

        final HappyEditText create_session_name;
        create_session_name = (HappyEditText) mainSessionView.getStartSessionLayout().findViewById(R.id.create_session_name);

        create_session_name.bindToKeyboard(keyboardViewPresenter);

        mainSessionView.getStartSessionLayout().findViewById(R.id.startSessionOkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(create_session_name.getText())) {
                    create_session_name.setError("No way to go without session name!");
                    return;
                }
                HappySession session = manager.startNewSession(create_session_name.getText().toString());
                create_session_name.unbindFromKeyboard();
                sessionViewPresenter.showSession(session);
                changeSessionState(true, mainSessionView.getStartSessionButton(), mainSessionView.getStopSessionButton());

                mainSessionView.getStartSessionButton().setVisibility(View.VISIBLE);
                mainSessionView.getStartSessionLayout().setVisibility(View.GONE);
            }
        });

        mainSessionView.getStartSessionLayout().findViewById(R.id.startSessionCancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainSessionView.getStartSessionButton().setVisibility(View.VISIBLE);
                mainSessionView.getStartSessionLayout().setVisibility(View.GONE);
                create_session_name.unbindFromKeyboard();
            }
        });

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