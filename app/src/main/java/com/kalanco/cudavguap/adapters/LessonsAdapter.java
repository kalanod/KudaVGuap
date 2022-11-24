package com.kalanco.cudavguap.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalanco.cudavguap.R;
import com.kalanco.cudavguap.models.Lesson;

import java.util.ArrayList;
import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder>{
    List<Lesson> list = new ArrayList<>();
    int week;

    @Override
    public String toString() {
        return "" +list;
    }

    public LessonsAdapter(ArrayList<Lesson> list, int wee) {
        this.list = list;
        week = wee;
    }

    @NonNull
    @Override
    public LessonsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonsAdapter.ViewHolder holder, int position) {
        Lesson lesson =list.get(position);
        holder.name.setText(lesson.getName());
        holder.time.setText(lesson.getTime());
        holder.place.setText(lesson.getPlace());
        holder.type.setText(lesson.getType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, desc, time, place, type;
        Button btn_buy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lesson_name);
            time = itemView.findViewById(R.id.lesson_time);
            place = itemView.findViewById(R.id.text_place);
            type = itemView.findViewById(R.id.text_type);
        }
    }
}
