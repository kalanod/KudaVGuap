package com.kalanco.cudavguap.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Day {
    String name;
    ArrayList<Lesson> lessonsList = new ArrayList<>();
    HashMap<String, ArrayList<Lesson>> lessonsMap = new HashMap<>();


    @Override
    public String toString() {
        return "" + name;
    }

    public String getName() {
        return name;
    }

    public Day(String name) {
        this.name = name;
    }

    public void add(String i, String s, String place, int week, String type) {
        lessonsList.add(new Lesson(i, s, place, week, type));
    }

    public ArrayList<Lesson> getLessins() {
        return lessonsList;
    }
}
