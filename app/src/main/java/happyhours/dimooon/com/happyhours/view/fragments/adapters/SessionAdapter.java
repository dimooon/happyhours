package happyhours.dimooon.com.happyhours.view.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimerActivity;
import happyhours.dimooon.com.happyhours.view.ObservableSeekBar;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    private List<HappyTimerActivity> timers;
    private SessionListItemClickListener itemClickListener;

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

    public SessionAdapter(ArrayList<HappyTimerActivity> timers,SessionListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        setData(timers);
    }

    public void setData(ArrayList<HappyTimerActivity> timers){
        this.timers = timers;
    }

    public void addData(HappyTimerActivity timer){
        this.timers.add(timer);
    }

    @Override
    public SessionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_list_item, parent, false);

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
        holder.value.setProgress(((int)timers.get(position).getActivityValue()));
    }
    @Override
    public int getItemCount() {
        return timers.size();
    }
}