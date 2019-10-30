package com.jefferystudio.bankingsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class registration2 extends AppCompatActivity {

    private Button buttonsubmit;

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

        buttonsubmit =(Button) findViewById(R.id.btnsubmit);

        buttonsubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MainActivity();
            }
        });
    }

    public void MainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
