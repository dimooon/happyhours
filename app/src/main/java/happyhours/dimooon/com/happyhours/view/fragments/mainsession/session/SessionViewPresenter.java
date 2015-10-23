package happyhours.dimooon.com.happyhours.view.fragments.mainsession.session;

import android.app.Activity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.data.SessionDataProvider;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.service.ActivityService;
import happyhours.dimooon.com.happyhours.tools.DateUtils;
import happyhours.dimooon.com.happyhours.view.custom.KeyboardViewPresenter;
import happyhours.dimooon.com.happyhours.view.custom.progressbar.ProgressBar;
import happyhours.dimooon.com.happyhours.view.custom.progressbar.ProgressBarModel;
import happyhours.dimooon.com.happyhours.view.custom.progressbar.ProgressBarPresenter;
import happyhours.dimooon.com.happyhours.view.custom.progressbar.TimeProgressBar;
import happyhours.dimooon.com.happyhours.view.fragments.adapters.CreateTimerController;
import happyhours.dimooon.com.happyhours.view.fragments.storylog.SessionModel;

public class SessionViewPresenter {

    private ISessionView sessionView;
    private Activity activity;
    private SessionDataProvider manager;
    private SessionAdapter adapter;
    private KeyboardViewPresenter keyboardViewPresenter;

    private ActivityService activityService;

    public SessionViewPresenter(Activity activity,ISessionView sessionView,SessionDataProvider manager,KeyboardViewPresenter keyboardViewPresenter,ActivityService service) {
        this.sessionView = sessionView;
        this.activity = activity;
        this.manager = manager;
        this.keyboardViewPresenter = keyboardViewPresenter;
        this.activityService = service;
    }

    public void showSession(HappySession session){

        if(isSessionStarted()){

            return;
        }

        initView(session);
    }

    public boolean isSessionStarted(){
        return activityService.sessionExists();
    }

    public void startSession(){
        activityService.startTimerCount();
    }

    public void stopSession(){
        activityService.stopTimerCount();
    }


    public void showTimersView(){

        final HappySession session = activityService.getSession();

        if(session == null){
            return;
        }

        final CreateTimerController createTimerController = new CreateTimerController();
        createTimerController.show(this.activity, activity.findViewById(R.id.toolbar), session, new CreateTimerController.CreateTimerDialogListener() {
            @Override
            public void onNewItemSelected(HappyTimer timer) {
                manager.addTimerToSession(session, timer);
                adapter.update();
                adapter.notifyDataSetChanged();
            }
        },keyboardViewPresenter);
    }

    private void initView(HappySession session){

        initDateView(session);

        initSessionTimer(session);

        initSessionActivityList(session,manager);
    }

    private void initDateView(HappySession session){

        sessionView.getCaption().setText(
                activity.getString(R.string.session_view_name_and_date_pattern,
                        session.getName(), DateUtils.getDate(session.getTimestamp())));
    }

    private void initSessionTimer(HappySession session) {

        ProgressBar progressBarView = sessionView.getMainProgressBar();
        ProgressBarModel model = new ProgressBarModel(null,manager.getDaoFacade());
        ProgressBarPresenter mainProgressBarPresenter = new ProgressBarPresenter(model,progressBarView);

        progressBarView.setModel(model);
        progressBarView.setPresenter(mainProgressBarPresenter);

        activityService.initSession(session, mainProgressBarPresenter);
    }

    private void initSessionActivityList(HappySession session,SessionDataProvider sessionDataProvider) {

        sessionView.getSessionTimersList().setHasFixedSize(true);
        sessionView.getSessionTimersList().setLayoutManager(new StaggeredGridLayoutManager(3, ((LinearLayout) sessionView).SHOW_DIVIDER_BEGINNING));

        SessionModel model = new SessionModel(session,sessionDataProvider,false);
        model.init();

        adapter = new SessionAdapter(new SessionAdapter.SessionListItemClickListener() {
            @Override
            public void onItemClick(View itemView) {
                activityService.attach(((TimeProgressBar) itemView.findViewById(R.id.timeProgressItem)).getPresenter());
            }
        }, model);

        sessionView.getSessionTimersList().setAdapter(adapter);
    }
}