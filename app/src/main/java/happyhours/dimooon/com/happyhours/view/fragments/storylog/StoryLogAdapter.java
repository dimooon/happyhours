package happyhours.dimooon.com.happyhours.view.fragments.storylog;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionModel;
import happyhours.dimooon.com.happyhours.tools.DateUtils;
import happyhours.dimooon.com.happyhours.tools.FormatUtils;
import happyhours.dimooon.com.happyhours.view.custom.progressbar.TimeProgressBar;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.SessionAdapter;

public class StoryLogAdapter extends RecyclerView.Adapter<StoryLogAdapter.ViewHolder> {

    private static final String TAG = StoryLogAdapter.class.getSimpleName();
    private List<HappySession> sessions;
    private Context context;
    private SessionModel manager;

    public static final int DEFAULT_HEIGHT = 335;
    public static final int DEFAULT_TIMER_HEIGHT = 90;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView sessionCardName;
        public TextView sessionCardDate;
        public TextView session_card_full_time;
        public TextView session_card_happy_time;
        public TextView session_card_happy_task;
        public TextView caption;
        public TimeProgressBar sessionMainProgress;
        public RecyclerView sessionTimersList;
        public CardView sessions_rad_view;
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
            sessionMainProgress = (TimeProgressBar) v.findViewById(R.id.sessionMainProgress);

            sessionTimersList = (RecyclerView) v.findViewById(R.id.sessionTimersList);
            sessions_rad_view = (CardView) v.findViewById(R.id.sessions_card_view);

            sessionIncludeLayout = v.findViewById(R.id.sessionIncludeLayout);
            session_list_item_value = v.findViewById(R.id.progressInBar);

        }
    }

    public StoryLogAdapter(List<HappySession> sessions, Context context, SessionModel manager) {
        this.sessions = sessions;
        this.context = context;
        this.manager = manager;
    }

    @Override
    public StoryLogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_card_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        int fullTime = (int) manager.getFullTimeForSession(sessions.get(position));

        holder.sessionMainProgress.restoreProgress(fullTime);
        holder.session_card_full_time.setText(DateUtils.getTimeProgress(fullTime));

        ArrayList<HappyTimerActivity> activities = manager.getTimerActivities(sessions.get(position));
        holder.sessionTimersList.setAdapter(new SessionAdapter(activities, null, manager));
        ViewGroup.LayoutParams params = holder.sessions_rad_view.getLayoutParams();
        params.height = FormatUtils.toDip(context, (DEFAULT_HEIGHT + DEFAULT_TIMER_HEIGHT * (activities.size() - 1)));
        holder.sessions_rad_view.setLayoutParams(params);

        holder.session_card_happy_time.setText(DateUtils.getTimeProgress((int) manager.getHappyTimeForSession(sessions.get(position))));

        HappyTimerActivity mostHappy = manager.getMostHappyTask(sessions.get(position));

        if(mostHappy == null){
            holder.session_card_happy_task.setVisibility(View.GONE);
        }else{
            holder.session_card_happy_task.setText(String.valueOf(mostHappy.getTimerName()));
        }

        //new FullTimeTask(holder).execute(position);
        //new SessionActivitiesTask(holder).execute(position);
        //new HappyTimeForSessionTask(holder).execute(position);
        //new MostHappyTask(holder).execute(position);
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    private class FullTimeTask extends AsyncTask<Integer,Void,Long>{
        ViewHolder holder;

        public FullTimeTask(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        protected Long doInBackground(Integer... position) {
            return manager.getFullTimeForSession(sessions.get(position[0]));
        }

        @Override
        protected void onPostExecute(Long fullTime) {
            super.onPostExecute(fullTime);
            holder.sessionMainProgress.restoreProgress(fullTime.intValue());
            holder.session_card_full_time.setText(DateUtils.getTimeProgress(fullTime.intValue()));
        }
    }
    private class SessionActivitiesTask extends AsyncTask<Integer,Void,ArrayList<HappyTimerActivity>>{
        ViewHolder holder;

        public SessionActivitiesTask(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        protected ArrayList<HappyTimerActivity> doInBackground(Integer... position) {
            return manager.getTimerActivities(sessions.get(position[0]));
        }

        @Override
        protected void onPostExecute(ArrayList<HappyTimerActivity> activities) {
            super.onPostExecute(activities);
            holder.sessionTimersList.setAdapter(new SessionAdapter(activities, null, manager));
            ViewGroup.LayoutParams params = holder.sessions_rad_view.getLayoutParams();
            params.height = FormatUtils.toDip(context, (DEFAULT_HEIGHT + DEFAULT_TIMER_HEIGHT * (activities.size() - 1)));
            holder.sessions_rad_view.setLayoutParams(params);

        }
    }
    private class HappyTimeForSessionTask extends AsyncTask<Integer,Void,String>{
        ViewHolder holder;

        public HappyTimeForSessionTask(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        protected String doInBackground(Integer... position) {
            return DateUtils.getTimeProgress((int) manager.getHappyTimeForSession(sessions.get(position[0])));
        }

        @Override
        protected void onPostExecute(String happyTimeForSession) {
            super.onPostExecute(happyTimeForSession);
            holder.session_card_happy_time.setText(happyTimeForSession);

        }
    }
    private class MostHappyTask extends AsyncTask<Integer,Void,HappyTimerActivity>{
        ViewHolder holder;

        public MostHappyTask(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        protected HappyTimerActivity doInBackground(Integer... position) {
            return manager.getMostHappyTask(sessions.get(position[0]));
        }

        @Override
        protected void onPostExecute(HappyTimerActivity mostHappy) {
            super.onPostExecute(mostHappy);

            if(mostHappy == null){
                holder.session_card_happy_task.setVisibility(View.GONE);
            }else{
                holder.session_card_happy_task.setText(String.valueOf(mostHappy.getTimerName()));
            }
        }
    }

}