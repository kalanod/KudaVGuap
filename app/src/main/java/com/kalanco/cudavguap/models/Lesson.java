package com.kalanco.cudavguap.models;

public class Lesson {
    String name;
    String number;
    String desc;
    String place;
    int week;
    String type;

    public Lesson(String name, String number, String place, int week, String type) {
        this.name = name;
        this.number = number;
        this.place = place;
        this.week = week;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return number;
    }

    public String getPlace() {
        return place;
    }

    public String getType() {
        return type;
    }

    public int getWeek() {
        return week;
    }
}
