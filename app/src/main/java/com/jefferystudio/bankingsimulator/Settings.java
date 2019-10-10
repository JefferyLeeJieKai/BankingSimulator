package com.jefferystudio.bankingsimulator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Settings extends AppCompatActivity {

    private TextView date;
    private TextView time;
    private TextView timezone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Toolbar homeScreenToolbar = (Toolbar) findViewById(R.id.toolbar);
        homeScreenToolbar.setTitle("Settings");
        setSupportActionBar(homeScreenToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set date
        TimeZone t = TimeZone.getTimeZone("Asia/Singapore");

        Calendar cal = Calendar.getInstance(t);

        String strDate = String.format("%s", cal.get(Calendar.DATE)) + "/" +
                         String.format("%s", cal.get(Calendar.MONTH)) + "/" +
                         String.format("%s", cal.get(Calendar.YEAR));

        date = (TextView) findViewById(R.id.dateLbl);
        date.setText(strDate);

        //set time
        SimpleDateFormat sTime = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String strTime = sTime.format(cal.getTime());

        time = (TextView) findViewById(R.id.timeLbl);
        time.setText(strTime);

        //set timezone
        String strTimezone = t.getDisplayName(false, TimeZone.SHORT) + " -\n" +
                             t.getID();

        timezone = (TextView) findViewById(R.id.timezoneLbl);
        timezone.setText(strTimezone);
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }
}