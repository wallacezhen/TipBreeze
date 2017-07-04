package com.example.walla.tipbreeze;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by walla on 8/17/2016.
 */
public class SummaryCursorAdapter extends CursorAdapter {

    public SummaryCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.summary_list_item, parent, false
        );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String tipAvg = cursor.getString(
                cursor.getColumnIndex("TipAvg"));
        String tipTotal = cursor.getString(
                cursor.getColumnIndex("TipTotal"));
        String payTotalAvg = cursor.getString(
                cursor.getColumnIndex("PayTotalAvg"));
        String payTotalTotal = cursor.getString(
                cursor.getColumnIndex("PayTotalTotal"));

        TextView summaryTipAvg = (TextView) view.findViewById(R.id.summaryTipAvg);
        summaryTipAvg.setText("$"+tipAvg);

        TextView summaryTipTotal = (TextView) view.findViewById(R.id.summaryTipTotal);
        summaryTipTotal.setText("$"+tipTotal);

        TextView summaryPayTotalAvg = (TextView) view.findViewById(R.id.summaryPayTotalAvg);
        summaryPayTotalAvg.setText("$"+payTotalAvg);

        TextView summaryPayTotalTotal = (TextView) view.findViewById(R.id.summaryPayTotalTotal);
        summaryPayTotalTotal.setText("$"+payTotalTotal);
    }
}
