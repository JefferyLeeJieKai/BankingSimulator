package com.jefferystudio.bankingsimulator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

public class Settings extends AppCompatActivity {

    private TextView date;
    private TextView time;
    private TextView timezone;
    private TextView currency;
    private ImageView editTimeZone;
    private ImageView editCurrency;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Toolbar homeScreenToolbar = (Toolbar) findViewById(R.id.toolbar);
        homeScreenToolbar.setTitle("Settings");
        setSupportActionBar(homeScreenToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //change this to link to database
        String strTZ = "Asia/Singapore";
        String strCurrency = "SGD";

        //call setDateTime() method
        setDateTime(TimeZone.getTimeZone(strTZ));

        //call setCurrency() method
        setCurrency(strCurrency);

        //edit timezone
        editTimeZone = (ImageView) findViewById(R.id.modeEditAutoTimezone);
        editTimeZone.setOnClickListener(new View.OnClickListener() {
            //create an AlertDialog
            @Override
            public void onClick(View v) {
                //get a list of choices
                final ArrayList<String> timezoneChoices = new ArrayList<>(Arrays.asList(TimeZone.getAvailableIDs()));

                //convert to array
                final String[] timezoneArr = timezoneChoices.toArray(new String[timezoneChoices.size()]);

                //set choices
                new AlertDialog.Builder(Settings.this)
                        .setTitle("Edit Timezone")
                        .setItems(timezoneArr, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int choice) {
                                setDateTime(TimeZone.getTimeZone(timezoneArr[choice]));
                            }
                        })
                        .show();
            }
        });

        //edit currency
        editCurrency = (ImageView) findViewById(R.id.modeEditAutoCurrency);
        editCurrency.setOnClickListener(new View.OnClickListener() {
            //create an AlertDialog
            @Override
            public void onClick(View v) {
                //get a list of choices
                final String[] currencyArr = { "SGD", "MYR", "IDR",
                        "USD", "EUR", "GBP", "CAD", "AUD"};

                //set choices
                new AlertDialog.Builder(Settings.this)
                        .setTitle("Edit Currency")
                        .setItems(currencyArr, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int choice) {
                                setCurrency(currencyArr[choice]);
                            }
                        })
                        .show();
            }
        });
    }

    //set currency
    public void setCurrency(String strCurrency)
    {
        currency = (TextView) findViewById(R.id.currencyLbl);
        currency.setText(strCurrency);
    }

    //set date and time
    public void setDateTime(TimeZone t)
    {
        Calendar cal = Calendar.getInstance(t);

        //set date
        String strDate = String.format("%s", cal.get(Calendar.DATE)) + "/" +
                String.format("%s", cal.get(Calendar.MONTH)+1) + "/" +
                String.format("%s", cal.get(Calendar.YEAR));

        date = (TextView) findViewById(R.id.dateLbl);
        date.setText(strDate);

        //set time (24-hours)
        String strTime = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));

        time = (TextView) findViewById(R.id.timeLbl);
        time.setText(strTime);

        //set timezone
        String strTimezone = t.getDisplayName(false, TimeZone.SHORT) +
                " -\n" + t.getID();

        timezone = (TextView) findViewById(R.id.timezoneLbl);
        timezone.setText(strTimezone);
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }
}