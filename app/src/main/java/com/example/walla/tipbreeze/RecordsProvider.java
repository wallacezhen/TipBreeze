package com.example.walla.tipbreeze;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by walla on 8/8/2016.
 */
public class RecordsProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.walla.tipbreeze.RecordsProvider";
    private static final String BASE_PATH = "records";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int RECORDS = 1;
    private static final int RECORDS_ID = 2;

    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    public static final String CONTENT_ITEM_TYPE = "Record";

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, RECORDS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH +  "/#", RECORDS_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        if (uriMatcher.match(uri) == RECORDS_ID){
            selection = DBOpenHelper.RECORD_ID + "=" + uri.getLastPathSegment();
        }
        return database.query(DBOpenHelper.TABLE_RECORDS, DBOpenHelper.ALL_COLUMNS,
                selection, null, null, null,
                DBOpenHelper.RECORD_CREATED + " DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long id = database.insert(DBOpenHelper.TABLE_RECORDS,
                null, contentValues);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(DBOpenHelper.TABLE_RECORDS, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return database.update(DBOpenHelper.TABLE_RECORDS,
                contentValues, selection, selectionArgs);
    }
}
