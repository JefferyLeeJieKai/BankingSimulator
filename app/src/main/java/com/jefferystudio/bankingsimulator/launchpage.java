package com.jefferystudio.bankingsimulator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import static android.support.v4.content.ContextCompat.startActivity;

public class launchpage extends Activity {

    Timer timer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launchpage);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(launchpage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);

    }
}
