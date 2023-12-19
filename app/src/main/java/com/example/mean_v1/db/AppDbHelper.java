package com.example.mean_v1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mean_v1.db.AppContract;

public class AppDbHelper extends SQLiteOpenHelper {

    public static synchronized AppDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new AppDbHelper(context.getApplicationContext());
        }
        return instance;
    }
    private static AppDbHelper instance;
    public static final int DATABASE_VERSION = 1;

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "+ AppContract.DictEntry.TABLE_NAME + " (" +
                    AppContract.DictEntry._ID + "  INTEGER PRIMARY KEY, " +
                    AppContract.DictEntry.COLUMN_NAME_SUBJECT + " TEXT, " +
                    AppContract.DictEntry.COLUMN_NAME_STUDENT_SCORE + " FLOAT, " +
                    AppContract.DictEntry.COLUMN_NAME_MAX_SCORE + " FLOAT, " +
                    AppContract.DictEntry.COLUMN_NAME_WEIGHT + " FLOAT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AppContract.DictEntry.TABLE_NAME;


    private AppDbHelper(Context context) {
        super(context, "myapp.db", null, DATABASE_VERSION);
        //cleanDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void cleanDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }
}
