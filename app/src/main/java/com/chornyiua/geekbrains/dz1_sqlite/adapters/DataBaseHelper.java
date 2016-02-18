package com.chornyiua.geekbrains.dz1_sqlite.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ChornyiUA on 12.02.2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="dbmain";
    public static final int DATABASE_VERSION = 1;
    public static final String COMPANY_TABLE = "notes";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";

    Context context;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+COMPANY_TABLE +" ("
                + KEY_ID+" integer primary key autoincrement,"
                + KEY_NAME+" text" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + COMPANY_TABLE);

        onCreate(db);

    }
}
