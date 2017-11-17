package com.stecon.patipan_on.lettertracking;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stecon.patipan_on.lettertracking.controller.MyDbHelper;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;

    private String strUsername;
    private String strPassword;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        myDbHelper = new MyDbHelper(this);
        sqLiteDatabase = myDbHelper.getWritableDatabase();

        bindWidGet();
        btnLogin.setOnClickListener(this);
    }

    private void bindWidGet() {
        edtUsername = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogIn);
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {
            checkLogin();
        }
    }

    private void checkLogin() {
        strUsername = edtUsername.getText().toString().trim();
        strPassword = edtPassword.getText().toString().trim();

        if (strUsername.equals("") || strPassword.equals("")) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
        } else {

            SyncLogin syncLogin = new SyncLogin();
            syncLogin.execute();

        }

    }

    private class SyncLogin extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}
