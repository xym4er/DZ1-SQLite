package com.chornyiua.geekbrains.dz1_sqlite.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="dbmain";
    public static final int DATABASE_VERSION = 1;
    public static final String COMPANY_TABLE = "company";
    public static final String WORKERS_TABLE = "workers";

    public static final String COMPANY_KEY_ID = "_id";
    public static final String COMPANY_NAME = "name";

    public static final String WORKERS_KEY_ID = "_id";
    public static final String WORKERS_COMPANY_ID = "comp_id";
    public static final String WORKERS_NAME = "name";
    public static final String WORKERS_SALARY = "salary";
    Context context;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+COMPANY_TABLE +" ("
                + COMPANY_KEY_ID+" integer primary key autoincrement,"
                + COMPANY_NAME+" text" + ");");
        db.execSQL("create table "+WORKERS_TABLE +" ("
                + WORKERS_KEY_ID+" integer primary key autoincrement,"
                + WORKERS_NAME+" text,"
                + WORKERS_SALARY+" text,"
                + WORKERS_COMPANY_ID + " integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + COMPANY_TABLE);
        db.execSQL("drop table if exists " + WORKERS_TABLE);
        onCreate(db);
    }
}
