package com.kalanco.cudavguap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kalanco.cudavguap.adapters.ScheduleAdapter;
import com.kalanco.cudavguap.db.DbHelper;
import com.kalanco.cudavguap.fragments.AddictionFragment;
import com.kalanco.cudavguap.fragments.ScheduleFragment;
import com.kalanco.cudavguap.models.Day;
import com.kalanco.cudavguap.models.Lesson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    AddictionFragment addictionFragment;
    ScheduleFragment scheduleFragment;
    FragmentManager fragmentManager;
    FloatingActionButton actionButton;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linker();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, scheduleFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frameLayout, addictionFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void linker() {
        scheduleFragment = new ScheduleFragment();
        addictionFragment = new AddictionFragment();
        actionButton = findViewById(R.id.floatingActionButton);
    }

}