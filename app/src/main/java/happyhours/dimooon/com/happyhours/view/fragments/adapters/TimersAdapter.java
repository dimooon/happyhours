package happyhours.dimooon.com.happyhours.view.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;

public class TimersAdapter extends RecyclerView.Adapter<TimersAdapter.ViewHolder> {

    private static List<HappyTimer> timers;
    private static CreateTimerController.CreateTimerDialogListener listener;
    private static TimersAdapter instance;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public int position;
        public ViewHolder(View v) {
            super(v);
        name = (TextView) v.findViewById(android.R.id.text1);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onNewItemSelected(timers.get(position));
                        timers.remove(timers.get(position));
                        instance.notifyDataSetChanged();
                    }
                }
            });

        }
    }

    public TimersAdapter(ArrayList<HappyTimer> timers) {
        this.timers = timers;
        instance = this;
    }

    public void setListener(CreateTimerController.CreateTimerDialogListener listener){
        this.listener = listener;
    }

    @Override
    public TimersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.test_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(timers.get(position).getName());
        holder.position = position;
    }
    @Override
    public int getItemCount() {
        return timers.size();
    }
}