package happyhours.dimooon.com.happyhours.view.fragments.mainsession.session;

import android.app.Activity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;
import happyhours.dimooon.com.happyhours.model.timer.SessionTimer;
import happyhours.dimooon.com.happyhours.tools.DateUtils;
import happyhours.dimooon.com.happyhours.view.custom.TimeProgressBar;
import happyhours.dimooon.com.happyhours.view.dialog.CreateTimerController;

public class SessionViewPresenter {

    private ISessionView sessionView;
    private HappySession session;
    private Activity activity;
    private SessionManager manager;
    private SessionTimer sessionTimer;
    private SessionAdapter adapter;

    public SessionViewPresenter(Activity activity,ISessionView sessionView,SessionManager manager) {
        this.sessionView = sessionView;
        this.activity = activity;
        this.manager = manager;
    }

    public void showSession(HappySession session){

        if(isSessionStarted()){
            return;
        }

        this.session = session;
        initView();
    }

    public boolean isSessionStarted(){
        return this.session!=null;
    }

    public void purgeSession(){
        this.session = null;
    }

    public void startSession(){
        sessionTimer.startTimerCount();
    }

    public void stopSession(){
        if(sessionTimer!=null){
            sessionTimer.stopTimerCount();
        }
    }


    public void showTimersView(){

        CreateTimerController createTimerController = new CreateTimerController();
        createTimerController.show(this.activity, activity.findViewById(R.id.toolbar), session, new CreateTimerController.CreateTimerDialogListener() {
            @Override
            public void onNewItemSelected(HappyTimer timer) {
                manager.addTimerToSession(session, timer);
                adapter.setData(manager.getTimerActivities(session));
                adapter.notifyDataSetChanged();
            }
        });
    }

    public ISessionView getSessionView(){
        return sessionView;
    }

    private void initView(){

        initDateView();

        sessionTimer = initSessionTimer();

        initSessionActivityList(manager);
    }

    private void initDateView(){

        sessionView.getCaption().setText(
                activity.getString(R.string.session_view_name_and_date_pattern,
                        session.getName(), DateUtils.getDate(session.getTimestamp())));
    }

    private SessionTimer initSessionTimer() {
        return new SessionTimer(sessionView.getMainProgressBar());
    }

    private void initSessionActivityList(SessionManager manager) {

        ArrayList<HappyTimerActivity> timers = manager.getTimerActivities(session);

        sessionView.getSessionTimersList().setHasFixedSize(true);
        sessionView.getSessionTimersList().setLayoutManager(new StaggeredGridLayoutManager(3, ((LinearLayout) sessionView).SHOW_DIVIDER_BEGINNING));

        adapter = new SessionAdapter(timers, new SessionAdapter.SessionListItemClickListener() {
            @Override
            public void onItemClick(View itemView) {
                sessionTimer.attach((TimeProgressBar) itemView.findViewById(R.id.timeProgressItem));
                sessionTimer.start((TimeProgressBar) itemView.findViewById(R.id.timeProgressItem));
            }
        }, manager,true);

        sessionView.getSessionTimersList().setAdapter(adapter);

    }

}
