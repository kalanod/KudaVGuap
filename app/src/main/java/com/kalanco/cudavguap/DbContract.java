package com.kalanco.cudavguap;

import android.provider.BaseColumns;

public class DbContract {
    private DbContract() {
    }
    public static class Employer implements BaseColumns {
        public static final String TABLE_NAME = "employer";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_FOUNDED_DATE = "date";
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_FOUNDED_DATE + " INTEGER" + ")";
    }
}
