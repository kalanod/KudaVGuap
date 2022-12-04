package com.kalanco.cudavguap.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kalanco.cudavguap.R;
import com.kalanco.cudavguap.models.Lesson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllLessonAdapter  extends RecyclerView.Adapter<AllLessonAdapter.ViewHolder>{
    ArrayList<Lesson> list;
    int week;


    public AllLessonAdapter(ArrayList<Lesson> allLessons) {
        this.list = allLessons;

    }

    @NonNull
    public AllLessonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mini_lesson, parent, false);
        return new AllLessonAdapter.ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull AllLessonAdapter.ViewHolder holder, int position) {
        Lesson lesson = list.get(position);
        holder.name.setText(lesson.getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "скоро можно будет добавить её в своё расписание", Toast.LENGTH_SHORT).show();
            }
        });
        holder.place.setText(lesson.getPlace());
        holder.type.setText(lesson.getType());
    }

    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, desc, time, place, type;
        View layout;
        Button btn_buy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lesson_name);
            place = itemView.findViewById(R.id.text_place);
            layout = itemView.findViewById(R.id.card);
            type = itemView.findViewById(R.id.text_type);
        }
    }
}
