package com.stecon.patipan_on.lettertracking;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stecon.patipan_on.lettertracking.controller.MyDbHelper;

public class MainActivity extends AppCompatActivity {

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
