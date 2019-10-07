package com.jefferystudio.bankingsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class profilepage extends AppCompatActivity {

    private ImageButton buttonback;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepage);

        buttonback =(ImageButton) findViewById(R.id.backarrow);

        buttonback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                HomeFragment();
            }
        });
    }

    public void HomeFragment(){
        Intent intent = new Intent(getApplicationContext(), HomeFragment.class);
        startActivity(intent);
        finish();
    }
}
