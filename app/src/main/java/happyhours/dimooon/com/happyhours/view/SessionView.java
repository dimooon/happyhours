package happyhours.dimooon.com.happyhours.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.Utils;
import happyhours.dimooon.com.happyhours.database.SessionManager;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.timer.SessionTimer;
import happyhours.dimooon.com.happyhours.view.fragments.CreateTimerDialog;
import happyhours.dimooon.com.happyhours.view.fragments.adapters.SessionAdapter;

public class SessionView extends LinearLayout{

    private static final String TAG = SessionView.class.getSimpleName();
    private RecyclerView sessionTimersList;
    private View sessionListAddNewTimerView;
    private SessionAdapter adapter;

    private ObservableSeekBar mainProgress;

    private HappySession session;
    private SessionTimer sessionTimer;

    private Activity activity;

    private CreateTimerDialog createTimerDialog;
    private SessionManager sessionManager;

    private TextView dateTextView;

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

    private void initView(){

        inflate(getContext(),R.layout.view_session,this);

        dateTextView = (TextView) findViewById(R.id.caption);

        dateTextView.setText(getContext().getString(R.string.session_view_name_and_date_pattern,session.getName(), Utils.getDate(session.getTimestamp())));

        mainProgress = (ObservableSeekBar) findViewById(R.id.sessionMainProgress);

        sessionTimer = new SessionTimer(mainProgress);

        sessionManager = new SessionManager(getContext());
        ArrayList<HappyTimerActivity> timers = sessionManager.getTimerActivities(session);

        sessionTimersList = (RecyclerView) findViewById(R.id.sessionTimersList);

        sessionTimersList.setHasFixedSize(true);
        sessionTimersList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SessionAdapter(timers, new SessionAdapter.SessionListItemClickListener() {
            @Override
            public void onItemClick(View itemView) {

                Log.e(TAG,"click");
                sessionTimer.attach((ObservableSeekBar)itemView.findViewById(R.id.session_list_item_value));
                sessionTimer.start((ObservableSeekBar)itemView.findViewById(R.id.session_list_item_value));
            }
        },sessionManager);

        sessionTimersList.setAdapter(adapter);

        sessionListAddNewTimerView = findViewById(R.id.sessionListAddNewTimerView);
        sessionListAddNewTimerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTimerDialog();
            }
        });
    }

    public void purgeSession(){
        this.session = null;
    }

    public boolean isSessionStarted(){
        return this.session!=null;
    }

    public void startSession(){
        sessionTimer.startTimerCount();
    }
    public void stopSession(){
        sessionTimer.stopTimerCount();
    }

    private void showAddTimerDialog(){

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