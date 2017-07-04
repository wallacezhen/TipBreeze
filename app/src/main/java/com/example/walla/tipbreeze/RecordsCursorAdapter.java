package com.example.walla.tipbreeze;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by walla on 8/9/2016.
 */
public class RecordsCursorAdapter extends CursorAdapter {

    public RecordsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.record_list_item, parent, false
        );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String recordName = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.RECORD_NAME));
        String dateCreated = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.RECORD_CREATED));
        String tip = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.RECORD_TIP));
        String payTotal = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.RECORD_PAYTOTAL));
        
        int pos = recordName.indexOf(15);
        if (pos != -1) {
            recordName = recordName.substring(0, pos) + " ...";
        }
        TextView tvName = (TextView) view.findViewById(R.id.recordName);
        tvName.setText(recordName);

        TextView tvCreated = (TextView) view.findViewById(R.id.recordDateCreated);
        tvCreated.setText(dateCreated);

        TextView tvTip = (TextView) view.findViewById(R.id.recordTip);
        tvTip.setText("Tip: $"+tip);

        TextView tvPayTotal = (TextView) view.findViewById(R.id.recordPayTotal);
        tvPayTotal.setText("Pay Total: $"+payTotal);
    }
}
