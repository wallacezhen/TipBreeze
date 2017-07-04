package com.example.walla.tipbreeze;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PercentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int rbID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(rbID);
                int rbIndex = radioGroup.indexOfChild(radioButton);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.percent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_donePercent:

                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg1);
                int rbID = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(rbID);
                int rbIndex = radioGroup.indexOfChild(radioButton);


                TextView selectedPercent = MainActivity.selectedPercent;
                TextView startPercent = MainActivity.startPercent;
                TextView endPercent = MainActivity.endPercent;

                switch ( rbIndex ){
                    case 0:
                        TextView selectedPercent1 = (TextView)findViewById(R.id.selectedPercent1);
                        TextView startPercent1 = (TextView)findViewById(R.id.startPercent1);
                        TextView endPercent1 = (TextView)findViewById(R.id.endPercent1);

                        if (selectedPercent1.getText().toString().trim().equals("") ||
                                startPercent1.getText().toString().trim().equals("") ||
                                endPercent1.getText().toString().trim().equals("") ){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                            alertDialogBuilder.setTitle("Error Message")
                                    .setMessage("Please enter values for all three fields.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                        else {
                            selectedPercent.setText(selectedPercent1.getText().toString().trim()+"%");
                            startPercent.setText(startPercent1.getText().toString().trim()+"%");
                            endPercent.setText(endPercent1.getText().toString().trim()+"%");

                            setResult(RESULT_OK);
                            super.onBackPressed();
                            finish();
                        }
                        break;

                    case 1:
                        TextView selectedPercent2 = (TextView)findViewById(R.id.selectedPercent2);
                        selectedPercent.setText(selectedPercent2.getText().toString().trim());

                        TextView startPercent2 = (TextView)findViewById(R.id.startPercent2);
                        startPercent.setText(startPercent2.getText().toString().trim());

                        TextView endPercent2 = (TextView)findViewById(R.id.endPercent2);
                        endPercent.setText(endPercent2.getText().toString().trim());

                        setResult(RESULT_OK);
                        super.onBackPressed();
                        finish();
                        break;

                    case 2:
                        TextView selectedPercent3 = (TextView)findViewById(R.id.selectedPercent3);
                        selectedPercent.setText(selectedPercent3.getText().toString().trim());

                        TextView startPercent3 = (TextView)findViewById(R.id.startPercent3);
                        startPercent.setText(startPercent3.getText().toString().trim());

                        TextView endPercent3 = (TextView)findViewById(R.id.endPercent3);
                        endPercent.setText(endPercent3.getText().toString().trim());

                        setResult(RESULT_OK);
                        super.onBackPressed();
                        finish();
                        break;

                    case 3:
                        TextView selectedPercent4 = (TextView)findViewById(R.id.selectedPercent4);
                        selectedPercent.setText(selectedPercent4.getText().toString().trim());

                        TextView startPercent4 = (TextView)findViewById(R.id.startPercent4);
                        startPercent.setText(startPercent4.getText().toString().trim());

                        TextView endPercent4 = (TextView)findViewById(R.id.endPercent4);
                        endPercent.setText(endPercent4.getText().toString().trim());

                        setResult(RESULT_OK);
                        super.onBackPressed();
                        finish();
                        break;

                    case 4:
                        TextView selectedPercent5 = (TextView)findViewById(R.id.selectedPercent5);
                        selectedPercent.setText(selectedPercent5.getText().toString().trim());

                        TextView startPercent5 = (TextView)findViewById(R.id.startPercent5);
                        startPercent.setText(startPercent5.getText().toString().trim());

                        TextView endPercent5 = (TextView)findViewById(R.id.endPercent5);
                        endPercent.setText(endPercent5.getText().toString().trim());

                        setResult(RESULT_OK);
                        super.onBackPressed();
                        finish();
                        break;

                    default:
                        setResult(RESULT_OK);
                        super.onBackPressed();
                        finish();
                        break;
                }

        }
        return super.onOptionsItemSelected(item);
    }
}
