package com.example.mean_v1.db;

import android.provider.BaseColumns;

public final class AppContract {
    private AppContract(){}

    public static  abstract class DictEntry implements BaseColumns{
        public static final String TABLE_NAME = "GRADES";
        public static final String COLUMN_NAME_SUBJECT = "SUBJECT";
        public static final String COLUMN_NAME_STUDENT_SCORE = "STUDENT_SCORE";
        public static final String COLUMN_NAME_MAX_SCORE = "MAX_SCORE";
        public static final String COLUMN_NAME_WEIGHT = "WEIGHT_SCORE";
    }
}
