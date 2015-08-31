package happyhours.dimooon.com.happyhours.view;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.database.SessionManager;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.timer.SessionTimer;
import happyhours.dimooon.com.happyhours.view.fragments.adapters.SessionAdapter;

public class SessionView extends LinearLayout{

    private static final String TAG = SessionView.class.getSimpleName();
    private RecyclerView sessionTimersList;
    private View sessionListAddNewTimerView;
    private SessionAdapter adapter;

    private ObservableSeekBar mainProgress;

    private HappySession session;
    private SessionTimer sessionTimer;

    public SessionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SessionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSession(HappySession session){
        this.session = session;
        initView();
    }

    private void initView(){

        inflate(getContext(),R.layout.view_session,this);

        mainProgress = (ObservableSeekBar) findViewById(R.id.sessionMainProgress);

        sessionTimer = new SessionTimer(mainProgress);

        final SessionManager sessionManager = new SessionManager(getContext());
        ArrayList<HappyTimerActivity> timers = sessionManager.getTimerActivities(session);

        inflate(getContext(), R.layout.view_session, this);
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
        });
        sessionTimersList.setAdapter(adapter);

        sessionListAddNewTimerView = findViewById(R.id.sessionListAddNewTimerView);

        sessionListAddNewTimerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                long id = sessionManager.addTimerToSession(session, sessionManager.createTimer("Debugging"));

                adapter.addData(sessionManager.getTimerActivity(id));
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void startSession(){
        sessionTimer.startTimerCount();
    }

}