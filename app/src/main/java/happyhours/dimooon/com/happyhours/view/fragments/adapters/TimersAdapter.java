package happyhours.dimooon.com.happyhours.view.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;

public class TimersAdapter extends RecyclerView.Adapter<TimersAdapter.ViewHolder> {

    private List<HappyTimer> timers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ViewHolder(View v) {
            super(v);
        name = (TextView) v.findViewById(android.R.id.text1);
        }
    }

    public TimersAdapter(ArrayList<HappyTimer> timers) {
        this.timers = timers;
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

    }
    @Override
    public int getItemCount() {
        return timers.size();
    }
}