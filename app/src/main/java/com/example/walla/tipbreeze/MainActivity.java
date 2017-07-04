package com.example.walla.tipbreeze;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static TextView selectedPercent;
    public static TextView startPercent;
    public static TextView endPercent;

    private static final int OPEN_CAMERA = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selectedPercent = (TextView)findViewById(R.id.selectedPercent);
        startPercent = (TextView)findViewById(R.id.startPercent);
        endPercent = (TextView)findViewById(R.id.endPercent);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera,OPEN_CAMERA);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Button calculate = (Button) findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //call onclick method
                calculateOnClick();
            }
        });

        SeekBar percent = (SeekBar) findViewById(R.id.seekBar);
        percent.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener(){
                    TextView tvPercent = selectedPercent;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        int percent  = 15 + i/10;
                        tvPercent.setText(percent+"%");
                        calculateOnClick();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                View alertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_record_name_alert, null);
                final EditText enterRecordName = (EditText)alertView.findViewById(R.id.enterRecordName);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("New Record")
                        .setView(alertView)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TextView tvTip = (TextView)findViewById(R.id.tip);
                                TextView tvPayTotal = (TextView)findViewById(R.id.payTotal);

                                String name = enterRecordName.getText().toString();
                                float tip = Float.parseFloat(tvTip.getText().toString());
                                float payTotal = Float.parseFloat(tvPayTotal.getText().toString());

                                insertRecord(name, tip, payTotal);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        TextView more = (TextView) findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PercentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void insertRecord(String name, float tip, float payTotal) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.RECORD_NAME, name);
        contentValues.put(DBOpenHelper.RECORD_TIP, tip);
        contentValues.put(DBOpenHelper.RECORD_PAYTOTAL,payTotal);
        Uri recordUri = getContentResolver().insert(RecordsProvider.CONTENT_URI, contentValues);
        Log.d("MainActivity", "Inserted record " + recordUri.getLastPathSegment());
    }

    private void calculateOnClick(){

        //create references to get values
        EditText et = (EditText)findViewById(R.id.billTotal);
        EditText et2 = (EditText)findViewById(R.id.numFriends);
        TextView tv = (TextView)findViewById(R.id.selectedPercent);

        //values for calculation
        float billTotal = 0;
        int numFriends = 1;

        String strBillTotal = et.getText().toString().trim();
        String strNumFriends = et2.getText().toString().trim();

        if (!strBillTotal.equals("")){
            billTotal = Float.parseFloat(et.getText().toString());
        }
        if (!strNumFriends.equals("")){
            numFriends = Integer.parseInt(et2.getText().toString());
        }

        float selectedPercent =
                Float.parseFloat(tv.getText().toString().replaceAll("%",""))/100;

        //call method with values for calculation
        calculateTipAndPayTotal(billTotal,numFriends,selectedPercent);
    }

    private void calculateTipAndPayTotal(float billTotal, int numFriends, float selectedPercent) {

        //calculate tip and payTotal
        float tip = billTotal * selectedPercent / numFriends;
        float payTotal = tip + billTotal / numFriends;

        //get references to display calculate values
        TextView tvTip = (TextView)findViewById(R.id.tip);
        TextView tvPayTotal = (TextView)findViewById(R.id.payTotal);



        //turn values to display strings
        String strTip = String.format("%.2f",tip);
        String strPayTotal = String.format("%.2f",payTotal);

        //set textviews
        tvTip.setText(strTip);
        tvPayTotal.setText(strPayTotal);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                Intent intentSetting = new Intent(this, SettingsActivity.class);
                startActivity(intentSetting);
                return true;
            case R.id.action_about:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tip_calculator){
            Intent intentTipCalculator = new Intent(this, MainActivity.class);
            startActivity(intentTipCalculator);
        } else if (id == R.id.nav_camera) {
            Intent intentCamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intentCamera, OPEN_CAMERA);
        } else if (id == R.id.nav_percent){
            Intent intentPercent = new Intent(this, PercentActivity.class);
            startActivity(intentPercent);
        } else if (id == R.id.nav_record) {
            Intent intentRecords = new Intent(this, RecordsActivity.class);
            startActivity(intentRecords);
        } else if (id == R.id.nav_summary) {
            Intent intentSummary = new Intent(this, SummaryActivity.class);
            startActivity(intentSummary);
        } else if (id == R.id.nav_settings) {
            Intent intentSetting = new Intent(this, SettingsActivity.class);
            startActivity(intentSetting);
        } else if (id == R.id.nav_about){
            Intent intentAbout = new Intent(this, AboutActivity.class);
            startActivity(intentAbout);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
