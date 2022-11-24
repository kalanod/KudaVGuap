package com.kalanco.cudavguap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kalanco.cudavguap.adapters.LessonsAdapter;
import com.kalanco.cudavguap.adapters.ScheduleAdapter;
import com.kalanco.cudavguap.models.Day;
import com.kalanco.cudavguap.models.Lesson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView list;
    LinearLayoutManager layoutManager;
    Button btnUpdate;
    ArrayList<Lesson> lessonsList;
    ArrayList<Day> days;
    ArrayList<Day> days2;
    ArrayList<Day> daysRes;
    ProgressBar progressBar;
    String[] mass = new String[2];
    String[] mass2 = new String[2];
    ScheduleAdapter scheduleAdapter;
    ScheduleAdapter scheduleAdapter2;
    int te;
    Elements res;
    String resTop;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch weekSwitch;
    int week;
    TextView now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linker();
        lessonsList = new ArrayList<>();
        days = new ArrayList<>();
        days2 = new ArrayList<>();
        daysRes = new ArrayList<>();
        scheduleAdapter = new ScheduleAdapter(daysRes, week);
        //lessonsAdapter = new LessonsAdapter(lessonsList);
        layoutManager = new LinearLayoutManager(this);
        list.setAdapter(scheduleAdapter);
        list.setLayoutManager(layoutManager);
        Task task = new Task();
        task.execute();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.execute();
            }
        });
        weekSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weekSwitch.isChecked()) {
                    week = 1;
                    weekSwitch.setText("нечётная");
                    daysRes = days;
                    scheduleAdapter = new ScheduleAdapter(days, week);
                } else {
                    week = 0;
                    weekSwitch.setText("чётная");
                    daysRes = days2;
                    scheduleAdapter = new ScheduleAdapter(days2, week);
                }
                list.setAdapter(scheduleAdapter);
                scheduleAdapter.notifyDataSetChanged();

            }
        });
    }

    private void updateLessons() {
        new Thread() {
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect("https://guap.ru/rasp/?g=186/").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                res = doc.select(".result");
                resTop = doc.select(".up").text().split("\\.")[0];

                String[] st = res.html().split("<h3>");
                System.out.println(resTop.split(", ")[2].split(" ")[0] + "restop");
                if (resTop.split(", ")[2].split(" ")[0].equals("верхняя")) {
                    week = 1;
                } else {
                    week = 0;
                }
                st[0] = "";
                for (String i : st) {
                    if (i.isEmpty()) continue;
                    mass = i.split("</h3>");
                    Day day = new Day(mass[0]);
                    Day day2 = new Day(mass[0]);
                    String[] fd = mass[1].split("<h4>");
                    for (String j : fd) {
                        if (j.trim().isEmpty()) continue;
                        mass2 = j.split("/h4");
                        String[] fsd = mass2[1].split("<div class=\"study\">");
                        for (String ui : fsd) {
                            String[] finn = ui.split("<em>");
                            if (finn[0].split("</b>").length < 2) continue;
                            System.out.println("+_+_+_+_+_+_+_+_+_+_++_+");
                            System.out.println(Arrays.toString(finn));
                            String week = finn[0].split("title=\"")[finn[0].split("title=\"").length - 1];
                            String type = finn[0].split("<b>")[finn[0].split("<b>").length - 1].split("</b>")[0];
                            te = 2;
                            if (week.charAt(0) == 'в') {
                                te = 0;
                            }
                            if (week.charAt(0) == 'н') {
                                te = 1;
                            }
                            System.out.println("wee" + week + "week3");
                            finn[0] = finn[0].split("b> – ")[finn[0].split("b> – ").length - 1];
                            finn[1] = finn[1].split("</em")[0];
                            if (te == 0 || te == 2) day.add(finn[0], mass2[0], finn[1], te, type);
                            if (te == 1 || te == 2) day2.add(finn[0], mass2[0], finn[1], te, type);
                        }
                    }
                    days.add(day);
                    days2.add(day2);
                }
                System.out.println("----------------------------------------------------------");
                for (Day i : days) System.out.println(i);
                System.out.println("----------------------------------------------------------");
            }
        }.start();
    }

    private void te() {
        Toast.makeText(MainActivity.this, "Закончили", Toast.LENGTH_SHORT).show();
    }

    private void linker() {
        list = findViewById(R.id.list);
        btnUpdate = findViewById(R.id.btn_update);
        weekSwitch = findViewById(R.id.switch1);
        now = findViewById(R.id.text_now);
        progressBar = findViewById(R.id.progressBar);
    }

    class Task extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Document doc = null;
            try {
                doc = Jsoup.connect("https://guap.ru/rasp/?g=186/").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            res = doc.select(".result");
            resTop = doc.select(".up").text().split("\\.")[0];

            String[] st = res.html().split("<h3>");
            System.out.println(resTop.split(", ")[2].split(" ")[0] + "restop");
            if (resTop.split(", ")[2].split(" ")[0].equals("верхняя")) {
                week = 1;
            } else {
                week = 0;
            }
            st[0] = "";
            for (String i : st) {
                if (i.isEmpty()) continue;
                mass = i.split("</h3>");
                Day day = new Day(mass[0]);
                Day day2 = new Day(mass[0]);
                String[] fd = mass[1].split("<h4>");
                for (String j : fd) {
                    if (j.trim().isEmpty()) continue;
                    mass2 = j.split("/h4");
                    String[] fsd = mass2[1].split("<div class=\"study\">");
                    for (String ui : fsd) {
                        String[] finn = ui.split("<em>");
                        if (finn[0].split("</b>").length < 2) continue;
                        System.out.println("+_+_+_+_+_+_+_+_+_+_++_+");
                        System.out.println(Arrays.toString(finn));
                        String week = finn[0].split("title=\"")[finn[0].split("title=\"").length - 1];
                        String type = finn[0].split("<b>")[finn[0].split("<b>").length - 1].split("</b>")[0];
                        te = 2;
                        if (week.charAt(0) == 'в') {
                            te = 0;
                        }
                        if (week.charAt(0) == 'н') {
                            te = 1;
                        }
                        System.out.println("wee" + week + "week3");
                        finn[0] = finn[0].split("b> – ")[finn[0].split("b> – ").length - 1];
                        finn[1] = finn[1].split("</em")[0];
                        if (te == 0 || te == 2) day.add(finn[0], mass2[0], finn[1], te, type);
                        if (te == 1 || te == 2) day2.add(finn[0], mass2[0], finn[1], te, type);
                    }
                }
                days.add(day);
                days2.add(day2);
            }
            System.out.println("----------------------------------------------------------");
            for (Day i : days) System.out.println(i);
            System.out.println("----------------------------------------------------------");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.INVISIBLE);
            if (week == 1) {
                weekSwitch.setChecked(true);
                weekSwitch.setText("нечётная");
                scheduleAdapter = new ScheduleAdapter(days, week);

            } else {
                scheduleAdapter = new ScheduleAdapter(days2, week);
                weekSwitch.setChecked(false);
                weekSwitch.setText("чётная");
            }
            list.setAdapter(scheduleAdapter);

            now.setText(resTop);

            scheduleAdapter.notifyDataSetChanged();

        }
    }
}