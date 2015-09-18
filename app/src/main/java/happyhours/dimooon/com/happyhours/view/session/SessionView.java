package happyhours.dimooon.com.happyhours.view.session;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;
import happyhours.dimooon.com.happyhours.tools.DateUtils;
import happyhours.dimooon.com.happyhours.model.database.manager.DatabaseSessionManager;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.timer.SessionTimer;
import happyhours.dimooon.com.happyhours.view.custom.ObservableSeekBar;
import happyhours.dimooon.com.happyhours.view.fragments.dialog.CreateTimerDialog;
import happyhours.dimooon.com.happyhours.view.fragments.adapters.SessionAdapter;

public class SessionView extends LinearLayout implements ISessionView{

    private RecyclerView sessionTimersList;
    private View sessionListAddNewTimerView;
    private SessionAdapter adapter;

    private HappySession session;
    private SessionTimer sessionTimer;

    private Activity activity;

    private CreateTimerDialog createTimerDialog;

    public SessionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SessionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSession(Activity activity,HappySession session){

        this.activity = activity;

        if(isSessionStarted()){
            return;
        }

        this.session = session;
        initView();
    }

    @Override
    public void purgeSession(){
        this.session = null;
    }

    @Override
    public boolean isSessionStarted(){
        return this.session!=null;
    }

    @Override
    public void startSession(){
        if(sessionTimer!=null){
            sessionTimer.startTimerCount();
        }
    }

    @Override
    public void stopSession(){
        if(sessionTimer!=null){
            sessionTimer.stopTimerCount();
        }
    }

    private void initView(){

        inflate(getContext(), R.layout.view_session, this);

        initDateView();

        sessionTimer = initSessionTimer();

        SessionManager manager = initSessionManager();

        initSessionActivityList(manager);
        initAddTimerButton(manager);

    }

    private void initDateView(){

        ((TextView) findViewById(R.id.caption)).setText(
                getContext().getString(R.string.session_view_name_and_date_pattern,
                        session.getName(), DateUtils.getDate(session.getTimestamp())));
    }

    private SessionTimer initSessionTimer() {
        return new SessionTimer((ObservableSeekBar) findViewById(R.id.sessionMainProgress));
    }

    private SessionManager initSessionManager() {
        return new DatabaseSessionManager(getContext());
    }

    private void initSessionActivityList(SessionManager manager) {

        ArrayList<HappyTimerActivity> timers = manager.getTimerActivities(session);

        sessionTimersList = (RecyclerView) findViewById(R.id.sessionTimersList);
        sessionTimersList.setHasFixedSize(true);

        sessionTimersList.setLayoutManager(new StaggeredGridLayoutManager(3,SHOW_DIVIDER_BEGINNING));

        adapter = new SessionAdapter(timers, new SessionAdapter.SessionListItemClickListener() {
            @Override
            public void onItemClick(View itemView) {
                sessionTimer.attach((ObservableSeekBar)itemView.findViewById(R.id.session_list_item_value));
                sessionTimer.start((ObservableSeekBar)itemView.findViewById(R.id.session_list_item_value));
            }
        },manager);

        sessionTimersList.setAdapter(adapter);

    }

    private void initAddTimerButton(final SessionManager manager) {
        sessionListAddNewTimerView = findViewById(R.id.sessionListAddNewTimerView);
        sessionListAddNewTimerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTimerDialog(manager);
            }
        });
    }

    private void showAddTimerDialog(final SessionManager sessionManager){

        createTimerDialog = new CreateTimerDialog();
        createTimerDialog.show(this.activity, new CreateTimerDialog.CreateTimerDialogListener() {
            @Override
            public void onNewItemSelected(HappyTimer timer) {
                createTimerDialog.dismiss();
                sessionManager.addTimerToSession(session,timer);
                adapter.setData(sessionManager.getTimerActivities(session));
                adapter.notifyDataSetChanged();
            }
        });
    }
}