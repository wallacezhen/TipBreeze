package com.example.walla.tipbreeze;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.GraphicalView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    int PICKED_YEAR, PICKED_MONTH, PICKED_DAY;
    static final int DILOG = 0;

    Spinner dropdown;
    List<String> itemList = new ArrayList<String>();

    private SummaryCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        dropdown = (Spinner)findViewById(R.id.spinner1);
        itemList.add("This Week");
        itemList.add("This Month");
        itemList.add("This Year");
        itemList.add("Pick a Date...");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, itemList);
        dropdown.setAdapter(adapter);

        final DBOpenHelper dbOpenHelper = new DBOpenHelper(this);

        final TextView summaryTipAvg = (TextView)findViewById(R.id.summaryTipAvg);
        final TextView summaryTipTotal = (TextView)findViewById(R.id.summaryTipTotal);
        final TextView summaryPayTotalAvg = (TextView)findViewById(R.id.summaryPayTotalAvg);
        final TextView summaryPayTotalTotal = (TextView)findViewById(R.id.summaryPayTotalTotal);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Calendar c = Calendar.getInstance();

                String tipAvg,tipTotal,payTotalAvg,payTotalTotal;
                String formattedDate, sql;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Cursor cursor;

                String selected = dropdown.getSelectedItem().toString().trim();
                if (dropdown.getSelectedItem().toString().equals("Pick a Date...")){
                    showDialog(DILOG);
                    if (selected.equals("Pick a Date...")){
                        dropdown.setSelection(0);
                    }
                }

                if (dropdown.getSelectedItem().toString().equals("This Week")){

                    c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());

                    formattedDate = format.format(c.getTime());

                    sql = "SELECT RID _id, Sum(Tip) as TipSum, Avg(Tip) as TipAvg, " +
                            "Sum(PayTotal) as PayTotalSum, Avg(PayTotal) as PayTotalAvg " +
                            "FROM Records WHERE DateCreated >= '" + formattedDate + "' ";

                    cursor = dbOpenHelper.getReadableDatabase().rawQuery(sql,null);
                    cursor.moveToFirst();

                    tipAvg = cursor.getString(cursor.getColumnIndex("TipAvg"));
                    tipTotal = cursor.getString(cursor.getColumnIndex("TipSum"));
                    payTotalAvg = cursor.getString(cursor.getColumnIndex("PayTotalAvg"));
                    payTotalTotal = cursor.getString(cursor.getColumnIndex("PayTotalSum"));

                    if (tipAvg == null){
                        tipAvg = "0.00";
                        tipTotal = "0.00";
                        payTotalAvg = "0.00";
                        payTotalTotal = "0.00";
                    }

                    summaryTipAvg.setText("$"+tipAvg);
                    summaryTipTotal.setText("$"+tipTotal);
                    summaryPayTotalAvg.setText("$"+payTotalAvg);
                    summaryPayTotalTotal.setText("$"+payTotalTotal);

                    PieGraph pieGraph = new PieGraph();
                    GraphicalView pieView = pieGraph.getView(SummaryActivity.this,
                            (int)Double.parseDouble(tipAvg), (int)Double.parseDouble(tipTotal),
                            (int)Double.parseDouble(payTotalAvg), (int)Double.parseDouble(payTotalTotal));

                    LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
                    layout.addView(pieView);

                } else if (dropdown.getSelectedItem().toString().equals("This Month")){
                    c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));

                    formattedDate = format.format(c.getTime());

                    sql = "SELECT RID _id, Sum(Tip) as TipSum, Avg(Tip) as TipAvg, " +
                            "Sum(PayTotal) as PayTotalSum, Avg(PayTotal) as PayTotalAvg " +
                            "FROM Records WHERE DateCreated >= '" + formattedDate + "' ";

                    cursor = dbOpenHelper.getReadableDatabase().rawQuery(sql,null);
                    cursor.moveToFirst();

                    tipAvg = cursor.getString(cursor.getColumnIndex("TipAvg"));
                    tipTotal = cursor.getString(cursor.getColumnIndex("TipSum"));
                    payTotalAvg = cursor.getString(cursor.getColumnIndex("PayTotalAvg"));
                    payTotalTotal = cursor.getString(cursor.getColumnIndex("PayTotalSum"));

                    if (tipAvg == null){
                        tipAvg = "0.00";
                        tipTotal = "0.00";
                        payTotalAvg = "0.00";
                        payTotalTotal = "0.00";
                    }

                    summaryTipAvg.setText("$"+tipAvg);
                    summaryTipTotal.setText("$"+tipTotal);
                    summaryPayTotalAvg.setText("$"+payTotalAvg);
                    summaryPayTotalTotal.setText("$"+payTotalTotal);

                } else if (dropdown.getSelectedItem().toString().equals("This Year")){
                    c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));

                    formattedDate = format.format(c.getTime());

                    sql = "SELECT RID _id, Sum(Tip) as TipSum, Avg(Tip) as TipAvg, " +
                            "Sum(PayTotal) as PayTotalSum, Avg(PayTotal) as PayTotalAvg " +
                            "FROM Records WHERE DateCreated >= '" + formattedDate + "' ";

                    cursor = dbOpenHelper.getReadableDatabase().rawQuery(sql,null);
                    cursor.moveToFirst();

                    tipAvg = cursor.getString(cursor.getColumnIndex("TipAvg"));
                    tipTotal = cursor.getString(cursor.getColumnIndex("TipSum"));
                    payTotalAvg = cursor.getString(cursor.getColumnIndex("PayTotalAvg"));
                    payTotalTotal = cursor.getString(cursor.getColumnIndex("PayTotalSum"));

                    if (tipAvg == null){
                        tipAvg = "0.00";
                        tipTotal = "0.00";
                        payTotalAvg = "0.00";
                        payTotalTotal = "0.00";
                    }

                    summaryTipAvg.setText("$"+tipAvg);
                    summaryTipTotal.setText("$"+tipTotal);
                    summaryPayTotalAvg.setText("$"+payTotalAvg);
                    summaryPayTotalTotal.setText("$"+payTotalTotal);

                } else {
                    formattedDate = dropdown.getSelectedItem().toString() + " 00:00:00 ";

                    sql = "SELECT RID _id, Sum(Tip) as TipSum, Avg(Tip) as TipAvg, " +
                            "Sum(PayTotal) as PayTotalSum, Avg(PayTotal) as PayTotalAvg " +
                            "FROM Records WHERE DateCreated >= '" + formattedDate + "' ";

                    cursor = dbOpenHelper.getReadableDatabase().rawQuery(sql,null);
                    cursor.moveToFirst();

                    tipAvg = cursor.getString(cursor.getColumnIndex("TipAvg"));
                    tipTotal = cursor.getString(cursor.getColumnIndex("TipSum"));
                    payTotalAvg = cursor.getString(cursor.getColumnIndex("PayTotalAvg"));
                    payTotalTotal = cursor.getString(cursor.getColumnIndex("PayTotalSum"));

                    if (tipAvg == null){
                        tipAvg = "0.00";
                        tipTotal = "0.00";
                        payTotalAvg = "0.00";
                        payTotalTotal = "0.00";
                    }

                    summaryTipAvg.setText("$"+tipAvg);
                    summaryTipTotal.setText("$"+tipTotal);
                    summaryPayTotalAvg.setText("$"+payTotalAvg);
                    summaryPayTotalTotal.setText("$"+payTotalTotal);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getSummary(String date) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getApplicationContext());

        String sql = "SELECT Sum(Tip) as TipSum, Avg(Tip) as TipAvg, " +
                "Sum(PayTotal) as PayTotalSum, Avg(PayTotal) as PayTotalAvg " +
                "FROM Records WHERE DateCreated >= '" + date + "' GROUP BY RID";
        Cursor cursor = dbOpenHelper.getReadableDatabase().rawQuery(sql,null);
        cursor.setNotificationUri(getApplicationContext().getContentResolver(),
                RecordsProvider.CONTENT_URI);
    }

    @Override
    protected Dialog onCreateDialog (int id){
        if (id == DILOG){
            return new DatePickerDialog(this, dpickerListener,
                    PICKED_YEAR, PICKED_MONTH, PICKED_DAY);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            PICKED_YEAR = year;
            PICKED_MONTH = month+1;
            PICKED_DAY = day;

            itemList = new ArrayList<String>();

            String newItem = new String(month+1+"/"+day+"/"+year);
            itemList.add(newItem);

            itemList.add("This Week");
            itemList.add("This Month");
            itemList.add("This Year");
            itemList.add("Pick a Date...");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SummaryActivity.this,
                    android.R.layout.simple_spinner_dropdown_item, itemList);
            dropdown.setAdapter(adapter);
            dropdown.setSelection(0);
        }
    };
}
