package com.example.walla.tipbreeze;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class RecordsActivity extends AppCompatActivity

implements LoaderManager.LoaderCallbacks<Cursor>
{

    private RecordsCursorAdapter cursorAdapter;
    private static final int SAVE_RECORD = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBOpenHelper dbHelper = new DBOpenHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        cursorAdapter = new RecordsCursorAdapter(this,null,0);

        ListView recordList = (ListView) findViewById(R.id.recordList);

        recordList.setBackgroundColor(Color.WHITE);
        recordList.setAdapter(cursorAdapter);

        getLoaderManager().initLoader(0,null,this);

    }


    private void restartLoader() {
        getLoaderManager().restartLoader(0,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, RecordsProvider.CONTENT_URI, DBOpenHelper.ALL_COLUMNS,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
