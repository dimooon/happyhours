package happyhours.dimooon.com.happyhours.view.fragments.storylog;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.tools.DateUtils;
import happyhours.dimooon.com.happyhours.tools.FormatUtils;
import happyhours.dimooon.com.happyhours.view.custom.progressbar.ProgressBarModel;
import happyhours.dimooon.com.happyhours.view.custom.progressbar.ProgressBarPresenter;
import happyhours.dimooon.com.happyhours.view.custom.progressbar.TimeProgressBar;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.SessionAdapter;

public class StoryLogAdapter extends RecyclerView.Adapter<StoryLogAdapter.ViewHolder> {

    private static final String TAG = StoryLogAdapter.class.getSimpleName();
    private Context context;
    private StoryLogModel model;

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

    public StoryLogAdapter(Context context, StoryLogModel model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public StoryLogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_card_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Log.e(TAG, "bind to view:");

        holder.caption.setVisibility(View.GONE);
        HappySession session = model.getSession(position);

        holder.sessionCardName.setText(session.getName());

        Log.e(TAG, "bind to view: Session: " + session);
        int fullTime = model.getFullTimeForSession(session);

        Log.e(TAG, "bind to view: Session:fullTime " + fullTime);

        ProgressBarModel progressBarModel = new ProgressBarModel(null,model.getSessionDataProvider().getDaoFacade());
        ProgressBarPresenter presenter = new ProgressBarPresenter(progressBarModel,holder.sessionMainProgress);

        holder.sessionMainProgress.setModel(progressBarModel);
        holder.sessionMainProgress.setPresenter(presenter);

        holder.sessionMainProgress.restoreProgress(fullTime);
        holder.session_card_full_time.setText(DateUtils.getTimeProgress(fullTime));

        ArrayList<HappyTimerActivity> activities = model.getTimerActivities(session);

        Log.e(TAG, "bind to view: Session:activities " + activities);

        SessionModel sessionModel = new SessionModel(session,model.getSessionDataProvider(),true);
        sessionModel.init();

        holder.sessionTimersList.setLayoutManager(new LinearLayoutManager(context));
        holder.sessionTimersList.setAdapter(new SessionAdapter(null, sessionModel));

        ViewGroup.LayoutParams params = holder.sessions_rad_view.getLayoutParams();
        params.height = FormatUtils.toDip(context, (DEFAULT_HEIGHT + DEFAULT_TIMER_HEIGHT * (activities.size() - 1)));
        holder.sessions_rad_view.setLayoutParams(params);

        holder.session_card_happy_time.setText(DateUtils.getTimeProgress(model.getHappyTimeForSession(session)));

        HappyTimerActivity mostHappy = model.getMostHappyTask(session);

        Log.e(TAG,"bind to view: Session:mostHappy "+mostHappy);

        if(mostHappy == null){
            holder.session_card_happy_task.setVisibility(View.GONE);
        }else{
            holder.session_card_happy_task.setText(String.valueOf(mostHappy.getTimerName()));
        }
    }

    @Override
    public int getItemCount() {
        return model.getSessions().size();
    }

}