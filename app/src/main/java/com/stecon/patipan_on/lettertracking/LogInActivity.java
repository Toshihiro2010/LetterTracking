package com.stecon.patipan_on.lettertracking;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stecon.patipan_on.lettertracking.controller.MyDbHelper;
import com.stecon.patipan_on.lettertracking.model.DatabaseUser;
import com.stecon.patipan_on.lettertracking.model.MyUserModel;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;

    private String strUsername;
    private String strPassword;

    private MyDbHelper myDbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    private String urlPath = "http://172.20.20.57:7777/login";


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

    private class SyncLogin extends AsyncTask<Object, Object, Response> {
        @Override
        protected Response doInBackground(Object... strings) {

            String strUrl = urlPath + "/" +strUsername + "/" + strPassword;
            //String strUrl = "https://sthq20.stecon.co.th/qrgateway/portal/inputtest";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .get()
                    .url(strUrl)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                //Log.d("response => ", response.body().string().toString());
                return response;
            } catch (Exception e) {
//                e.printStackTrace();
                Log.d("IOException => " , e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Response s) {
            super.onPostExecute(s);
            if (s.code() == 200) {
                Gson gson = new Gson();
                try {
                    MyUserModel myUserModel = gson.fromJson(s.body().string(), MyUserModel.class);
                    Toast.makeText(LogInActivity.this, "สวัสดีคุณ " + myUserModel.getUsername(), Toast.LENGTH_SHORT).show();
                    String sqlCheck = "SELECT * FROM "
                            + DatabaseUser.TABLE_NAME
                            + " WHERE "
                            + DatabaseUser.COL_USERNAME
                            + " = '"
                            + myUserModel.getUsername()
                            + "'";
                    cursor = sqLiteDatabase.rawQuery(sqlCheck, null);
                    int check = cursor.getCount();
                    Log.d("check = > ", check + "");
                    if (check > 0) {
                        Log.d("check >", "0");
                        String strSql = "UPDATE " + DatabaseUser.TABLE_NAME
                                + " SET " + DatabaseUser.COL_STATUS + " = 1"
                                + " WHERE "
                                + DatabaseUser.COL_USERNAME
                                + " = '"
                                + strUsername
                                + "'";
                        Log.d("SQL => ", strSql);
                        sqLiteDatabase.execSQL(strSql);
                    } else {
                        Log.d("check =", "0");
                        String strSql = "INSERT INTO " + DatabaseUser.TABLE_NAME + " ("
                                + DatabaseUser.COL_USERNAME + ","
                                + DatabaseUser.COL_PASSWORD + ","
                                + DatabaseUser.COL_STATUS + ") VALUES ('"
                                + strUsername + "','"
                                + strPassword + "',"
                                + "1);";
                        Log.d("SQL => ", strSql);
                        sqLiteDatabase.execSQL(strSql);
                    }





                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("e => ", e.toString());
                } catch (Exception e) {
                    Log.d("Exception e=> ", e.toString());
                }

            } else {
                Toast.makeText(LogInActivity.this, "รหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
