package com.example.walla.tipbreeze;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by walla on 8/8/2016.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    //Constants for db name and version
    private static final String DATABASE_NAME = "records.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for identifying table and columns
    public static final String TABLE_RECORDS = "Records";
    public static final String RECORD_ID = "RID";
    public static final String RECORD_NAME = "RecordName";
    public static final String RECORD_CREATED = "DateCreated";
    public static final String RECORD_TIP = "Tip";
    public static final String RECORD_PAYTOTAL = "PayTotal";


    public static final String[] ALL_COLUMNS =
            {RECORD_ID +" _id", RECORD_NAME, RECORD_CREATED, RECORD_TIP, RECORD_PAYTOTAL};

    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_RECORDS + " ( " +
                    RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RECORD_NAME + " TEXT, " +
                    RECORD_CREATED + " TEXT default CURRENT_TIMESTAMP, " +
                    RECORD_TIP + " FLOAT, " +
                    RECORD_PAYTOTAL + " FLOAT " +
                    ");";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORDS);
        onCreate(sqLiteDatabase);
    }


}
