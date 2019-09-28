package com.jefferystudio.bankingsimulator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class registration2 extends AppCompatActivity {


    private long date;
    private DateFormat format;
    String currentDateandTime;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration2);

        //create a date string.
        String date_n = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        //get hold of textview.
        TextView date  = (TextView) findViewById(R.id.dateview);
        //set it as current date.
        date.setText(date_n);
    }
}
