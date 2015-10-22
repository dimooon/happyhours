package happyhours.dimooon.com.happyhours.view.fragments.mainsession.session;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.tools.animation.ColorUtils;
import happyhours.dimooon.com.happyhours.view.custom.progressbar.TimeProgressBar;
import happyhours.dimooon.com.happyhours.view.fragments.storylog.SessionModel;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    private static final String TAG = SessionAdapter.class.getSimpleName();
    private SessionListItemClickListener itemClickListener;
    private SessionModel sessionModel;

    public interface SessionListItemClickListener{
        void onItemClick(View itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static TextView name;
        public static TimeProgressBar value;
        public static View root;
        public static View isHappy;
        public static View happyTaskTextLabel;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.session_list_item_caption);
            value = (TimeProgressBar) v.findViewById(R.id.timeProgressItem);
            root = v.findViewById(R.id.sessions_card_view);
            isHappy = v.findViewById(R.id.isHappy);
            happyTaskTextLabel = v.findViewById(R.id.happyTaskTextLabel);
        }
    }

    public SessionAdapter(SessionListItemClickListener itemClickListener,SessionModel sessionModel) {
        this.itemClickListener = itemClickListener;
        this.sessionModel = sessionModel;
    }

    public void update(){
        sessionModel.update();
    }

    @Override
    public SessionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_list_item_card, parent, false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(v);
                }
            }
        });

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        HappyTimerActivity timerActivity = sessionModel.getTimerActivity(position);

        Log.e(TAG,"bind data to vuew: "+timerActivity);

        holder.name.setText(timerActivity.getTimerName());

        holder.value.setEnabled(false);
        if(!sessionModel.sessionFromLog()){
            holder.root.setBackgroundColor(ColorUtils.getRandomColor());
            holder.happyTaskTextLabel.setVisibility(View.GONE);
            holder.isHappy.setVisibility(View.GONE);
        }else{
            holder.name.setBackgroundColor(Color.TRANSPARENT);
            holder.happyTaskTextLabel.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.value.assignDAO(sessionModel.getDaoFacade());
        holder.value.assignTimerActivity(timerActivity);
        ((CheckBox)holder.isHappy).setChecked(sessionModel.isHappy(timerActivity));

        holder.value.restoreProgress(sessionModel.getActivityValue(timerActivity));
    }
    @Override
    public int getItemCount() {
        Log.e(TAG,"get item count:"+sessionModel.getTimerActivities().size());
        return sessionModel.getTimerActivities().size();
    }
}