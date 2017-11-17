package com.stecon.patipan_on.lettertracking.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stecon.patipan_on.lettertracking.model.DatabaseUser;

/**
 * Created by patipan_on on 11/17/2017.
 */

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Letter";
    private static final int DB_VERSION = 1;

    public String userTable = DatabaseUser.TABLE_NAME;


    private String strCreateUserTable = DatabaseUser.strCreate;



    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(strCreateUserTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + userTable);
        onCreate(db);
    }
}
