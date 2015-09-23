package happyhours.dimooon.com.happyhours.view.fragments.mainsession.session;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;
import happyhours.dimooon.com.happyhours.view.custom.ObservableSeekBar;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    private List<HappyTimerActivity> timers;
    private SessionListItemClickListener itemClickListener;
    private SessionManager manager;

    public interface SessionListItemClickListener{
        void onItemClick(View itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static TextView name;
        public static SeekBar value;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.session_list_item_caption);
            value = (SeekBar) v.findViewById(R.id.session_list_item_value);
        }
    }

    public SessionAdapter(ArrayList<HappyTimerActivity> timers,SessionListItemClickListener itemClickListener,SessionManager manager) {
        this.itemClickListener = itemClickListener;
        setData(timers);
        this.manager = manager;
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
        holder.value.setProgress((int) manager.getTimerActivity(timers.get(position).getId()).getActivityValue());
        holder.value.setEnabled(false);
        ((ObservableSeekBar)holder.value).assignDAO(manager.getDaoFacade());
        ((ObservableSeekBar)holder.value).assignTimerActivity(timers.get(position));

    }
    @Override
    public int getItemCount() {
        return timers.size();
    }
}