package happyhours.dimooon.com.happyhours.view.fragments.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.tools.DateUtils;
import happyhours.dimooon.com.happyhours.model.database.manager.DatabaseSessionManager;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {

    private static final String TAG = SessionsAdapter.class.getSimpleName();
    private List<HappySession> sessions;
    private Context context;
    private DatabaseSessionManager manager;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView sessionCardName;
        public TextView sessionCardDate;
        public TextView session_card_full_time;
        public TextView session_card_happy_time;
        public TextView session_card_happy_task;
        public TextView caption;
        public SeekBar sessionMainProgress;
        public View sessionListAddNewTimerView;
        public RecyclerView sessionTimersList;
        public CardView sessions_rad_view;
        public View session_list_item_last;
        public View sessionIncludeLayout;
        public View session_list_item_value;
        public ViewHolder(View v) {
            super(v);

            sessionCardName = (TextView) v.findViewById(R.id.session_card_name);
            sessionCardDate = (TextView) v.findViewById(R.id.session_card_date);
            session_card_full_time = (TextView) v.findViewById(R.id.session_card_full_time);
            session_card_happy_time = (TextView) v.findViewById(R.id.session_card_happy_time);
            session_card_happy_task = (TextView) v.findViewById(R.id.session_card_happy_task);
            caption = (TextView) v.findViewById(R.id.caption);
            sessionMainProgress = (SeekBar) v.findViewById(R.id.sessionMainProgress);
            sessionListAddNewTimerView = (View) v.findViewById(R.id.sessionListAddNewTimerView);
            sessionTimersList = (RecyclerView) v.findViewById(R.id.sessionTimersList);
            sessions_rad_view = (CardView) v.findViewById(R.id.sessions_rad_view);
            session_list_item_last = v.findViewById(R.id.sessionListAddNewTimerView);
            sessionIncludeLayout = v.findViewById(R.id.sessionIncludeLayout);
            session_list_item_value = v.findViewById(R.id.session_list_item_value);
        }
    }

    public SessionsAdapter(ArrayList<HappySession> sessions, Context context,DatabaseSessionManager manager) {
        this.sessions = sessions;
        this.context = context;
        this.manager = manager;
    }

    @Override
    public SessionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_card_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.sessionTimersList.setHasFixedSize(true);
        holder.sessionTimersList.setLayoutManager(new LinearLayoutManager(context));

        holder.sessionTimersList.setAdapter(new SessionAdapter(manager.getTimerActivities(sessions.get(position)), null, manager));

        ViewGroup.LayoutParams params = holder.sessions_rad_view.getLayoutParams();

        Resources resource = context.getResources();
        float dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180 + 70 * manager.getTimerActivities(sessions.get(position)).size(), resource.getDisplayMetrics());

        params.height = (int)dp;

        holder.sessions_rad_view.setLayoutParams(params);
        holder.sessionCardName.setText(sessions.get(position).getName());
        holder.sessionCardDate.setText(DateUtils.getDate(sessions.get(position).getTimestamp()));

        holder.caption.setVisibility(View.GONE);
        holder.session_list_item_last.setVisibility(View.GONE);

        long fullSessionTime = manager.getFullTimeForSession(sessions.get(position));
        String fullSessionTimeString =  String.valueOf(fullSessionTime);

        holder.sessionMainProgress.setProgress((int) fullSessionTime);
        holder.sessionMainProgress.setEnabled(false);

        holder.session_card_full_time.setText(fullSessionTimeString);
        holder.session_card_happy_time.setText(String.valueOf(manager.getHappyTimeForSession(sessions.get(position))));
        HappyTimerActivity mostHappy = manager.getMostHappyTask(sessions.get(position));
        if(mostHappy == null){
            holder.session_card_happy_task.setVisibility(View.GONE);
        }else{
            holder.session_card_happy_task.setText(String.valueOf(mostHappy.getTimerName()));
        }

    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }
}