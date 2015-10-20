package happyhours.dimooon.com.happyhours.view.fragments.mainsession.session;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;
import happyhours.dimooon.com.happyhours.tools.animation.ColorUtils;
import happyhours.dimooon.com.happyhours.view.custom.progressbar.TimeProgressBar;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    private List<HappyTimerActivity> timers;
    private SessionListItemClickListener itemClickListener;
    private SessionManager manager;
    private boolean colorize;
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

    public SessionAdapter(ArrayList<HappyTimerActivity> timers,SessionListItemClickListener itemClickListener,SessionManager manager) {
        this(timers,itemClickListener,manager,false);
    }

    public SessionAdapter(ArrayList<HappyTimerActivity> timers,SessionListItemClickListener itemClickListener,SessionManager manager, boolean colorize) {
        this.itemClickListener = itemClickListener;
        setData(timers);
        this.manager = manager;
        this.colorize = colorize;
    }

    public void setData(ArrayList<HappyTimerActivity> timers){
        this.timers = timers;
    }

    public void addData(HappyTimerActivity timer){
        this.timers.add(timer);
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
        holder.name.setText(timers.get(position).getTimerName());
        holder.value.restoreProgress(((int) manager.getTimerActivity(timers.get(position).getId()).getActivityValue()));
        holder.value.setEnabled(false);
        if(this.colorize){
            holder.root.setBackgroundColor(ColorUtils.getRandomColor());
            holder.happyTaskTextLabel.setVisibility(View.GONE);
            holder.isHappy.setVisibility(View.GONE);
        }else{
            holder.name.setBackgroundColor(Color.TRANSPARENT);
            holder.happyTaskTextLabel.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.value.assignDAO(manager.getDaoFacade());
        holder.value.assignTimerActivity(timers.get(position));
        ((CheckBox)holder.isHappy).setChecked(manager.getDaoFacade().getTimer(timers.get(position).getTimerId()).getHappy() == 1);
    }
    @Override
    public int getItemCount() {
        return timers.size();
    }
}