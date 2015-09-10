package happyhours.dimooon.com.happyhours.view.fragments.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.Utils;
import happyhours.dimooon.com.happyhours.database.SessionManager;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.view.ObservableSeekBar;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {

    private static final String TAG = SessionsAdapter.class.getSimpleName();
    private List<HappySession> sessions;
    private Context context;
    private SessionManager manager;
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
        }
    }

    public SessionsAdapter(ArrayList<HappySession> sessions, Context context,SessionManager manager) {
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
        float dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 260 + 50 * manager.getTimerActivities(sessions.get(position)).size(), resource.getDisplayMetrics());

        params.height = (int)dp;

        holder.sessions_rad_view.setLayoutParams(params);
        holder.sessionCardName.setText(sessions.get(position).getName());
        holder.sessionCardDate.setText(Utils.getDate(sessions.get(position).getTimestamp()));
    }
    @Override
    public int getItemCount() {
        return sessions.size();
    }
}