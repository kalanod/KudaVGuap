package com.kalanco.cudavguap.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalanco.cudavguap.R;
import com.kalanco.cudavguap.models.Day;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{
    List<Day> list = new ArrayList<>();
    List<LessonsAdapter> adapterList;
    int week;

    @Override
    public String toString() {
        return "" +list;
    }

    public ScheduleAdapter(ArrayList<Day> list, int week) {
        this.list = list;
        this.week = week;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder holder, int position) {
        Day day = list.get(position);

        holder.name.setText(day.getName());
        holder.recyclerView.setAdapter(new LessonsAdapter(day.getLessins(), week));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, desc;
        Button btn_buy;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_day_name);
            recyclerView = itemView.findViewById(R.id.item_day_list);
        }
    }
}