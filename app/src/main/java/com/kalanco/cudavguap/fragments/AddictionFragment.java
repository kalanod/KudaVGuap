package com.kalanco.cudavguap.fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kalanco.cudavguap.DbContract;
import com.kalanco.cudavguap.R;
import com.kalanco.cudavguap.adapters.AllLessonAdapter;
import com.kalanco.cudavguap.adapters.ScheduleAdapter;
import com.kalanco.cudavguap.db.DbHelper;
import com.kalanco.cudavguap.models.Day;
import com.kalanco.cudavguap.models.Lesson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddictionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView list;
    LinearLayoutManager layoutManager;
    Button btnUpdate;
    ArrayList<Lesson> listAllLessons;
    ProgressBar progressBar;
    String[] mass = new String[2];
    String[] mass2 = new String[2];
    AllLessonAdapter allLessonAdapter;
    HashMap<String, ArrayList<Lesson>> allLessons;
    int te;
    Elements res;
    String resTop;
    @SuppressLint("UseSwitchCompatOrMaterialCode")

    public AddictionFragment() {
        // Required empty public constructor
    }

    public static AddictionFragment newInstance(String param1, String param2) {
        AddictionFragment fragment = new AddictionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addiction, container, false);

        list = view.findViewById(R.id.list);
        btnUpdate = view.findViewById(R.id.btn_update);
        progressBar = view.findViewById(R.id.progressBar);
        listAllLessons = new ArrayList<>();
        allLessons = new HashMap<>();
        layoutManager = new LinearLayoutManager(getContext());
        Task task = new Task();
        task.execute();
        allLessonAdapter = new AllLessonAdapter(listAllLessons);
        list.setAdapter(allLessonAdapter);
        list.setLayoutManager(layoutManager);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.execute();
            }
        });


        return view;
    }
    public void addLesson(Lesson s){
        allLessons.putIfAbsent(s.getName(), new ArrayList<Lesson>());
        allLessons.get(s.getName()).add(s);
    }
    public void saveToDb() {
        SQLiteDatabase database = new DbHelper(getContext()).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("a", "b");
        database.insert(DbContract.Schedule.TABLE_NAME, null, values);
    }

    public String readFromDb() {
        SQLiteDatabase database = new DbHelper(getContext()).getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from Schadual where id = ?;", new String[]{"1"});
        return cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Schedule.COLUMN_NAME));
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
                doc = Jsoup.connect("https://guap.ru/rasp/?b=1").get();
            } catch (IOException e) {
                cancel(false);
            }
            res = doc.select(".result");
            resTop = doc.select(".dn").text().split("\\.")[0];

            String[] st = res.html().split("<h3>");
            st[0] = "";
            for (String i : st) {
                if (i.isEmpty()) continue;
                mass = i.split("</h3>");
                String[] fd = mass[1].split("<h4>");
                for (String j : fd) {
                    if (j.trim().isEmpty()) continue;
                    mass2 = j.split("/h4");
                    String[] fsd = mass2[1].split("<div class=\"study\">");
                    for (String ui : fsd) {
                        String[] finn = ui.split("<em>");
                        if (finn[0].split("</b>").length < 2) continue;
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
                        addLesson(new Lesson(finn[0], mass2[0], finn[1], te, type));
                    }
                }
            }
            doc = null;
            try {
                doc = Jsoup.connect("https://guap.ru/rasp/?b=3").get();
            } catch (IOException e) {
                cancel(false);
            }
            res = doc.select(".result");
            resTop = doc.select(".dn").text().split("\\.")[0];

            st = res.html().split("<h3>");
            st[0] = "";
            for (String i : st) {
                if (i.isEmpty()) continue;
                mass = i.split("</h3>");
                String[] fd = mass[1].split("<h4>");
                for (String j : fd) {
                    if (j.trim().isEmpty()) continue;
                    mass2 = j.split("/h4");
                    String[] fsd = mass2[1].split("<div class=\"study\">");
                    for (String ui : fsd) {
                        String[] finn = ui.split("<em>");
                        if (finn[0].split("</b>").length < 2) continue;
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
                        addLesson(new Lesson(finn[0], mass2[0], finn[1], te, type));
                    }
                }
            }
            doc = null;
            try {
                doc = Jsoup.connect("https://guap.ru/rasp/?b=5").get();
            } catch (IOException e) {
                cancel(false);
            }
            res = doc.select(".result");
            resTop = doc.select(".dn").text().split("\\.")[0];

            st = res.html().split("<h3>");
            st[0] = "";
            for (String i : st) {
                if (i.isEmpty()) continue;
                mass = i.split("</h3>");
                String[] fd = mass[1].split("<h4>");
                for (String j : fd) {
                    if (j.trim().isEmpty()) continue;
                    mass2 = j.split("/h4");
                    String[] fsd = mass2[1].split("<div class=\"study\">");
                    for (String ui : fsd) {
                        String[] finn = ui.split("<em>");
                        if (finn[0].split("</b>").length < 2) continue;
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
                        addLesson(new Lesson(finn[0], mass2[0], finn[1], te, type));
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.INVISIBLE);
            for (Map.Entry<String, ArrayList<Lesson>> entry: allLessons.entrySet()){
                for (Lesson lesson : entry.getValue()){
                    if (lesson.getType().equals("Л")){
                        listAllLessons.add(lesson);
                        break;
                    }
                }
            }
            allLessonAdapter.notifyDataSetChanged();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getContext(), "не получилось", Toast.LENGTH_LONG).show();
        }
    }

}