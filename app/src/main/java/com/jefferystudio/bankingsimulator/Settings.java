package com.jefferystudio.bankingsimulator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.Quiz.QuizHistoryBanker;

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
    private TextView limit;
    private Button editLimitButton;

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

        //set limit
        limit = findViewById(R.id.limitLbl);
        //by default
        //if there is any changes made, draw changed amount from database
        limit.setText("2000");

        //limit button
        editLimitButton = findViewById(R.id.editLimitBtn);

        editLimitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDialogAction("New Daily Limit: ", "Daily Limit");

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

    public void getDialogAction(String label, String hint)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Settings.this);
        View v = getLayoutInflater().inflate(R.layout.edit_dialog, null);

        //set label and hint accordingly
        final TextView inputLabel = v.findViewById(R.id.inputLbl);
        inputLabel.setText(label);

        final EditText etInput = v.findViewById(R.id.input);
        etInput.setHint(hint);

        //confirm button
        Button confirm = v.findViewById(R.id.confirmBtn);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!etInput.getText().toString().isEmpty()) {
                    Toast.makeText(Settings.this,
                            etInput.getText().toString(),
                            Toast.LENGTH_SHORT).show();

                    //do something
                }
                else {
                    Toast.makeText(Settings.this,
                            "Please fill in the empty field",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //cancel button
        Button cancel = v.findViewById(R.id.cancelBtn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        builder.setView(v);
        android.app.AlertDialog ad = builder.create();
        ad.show();
        ad.getWindow().setLayout(1000, 1500);
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }
}