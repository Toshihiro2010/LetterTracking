package com.stecon.patipan_on.lettertracking.model;

/**
 * Created by patipan_on on 11/17/2017.
 */

public class DatabaseUser {

    public static final String TABLE_NAME = "User";

    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_STATUS = "status";


    public static String strCreate = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_USERNAME + " TEXT,"
            + COL_PASSWORD + " TEXT,"
            + COL_STATUS + " INTEGER DEFAULT 0);";



}
