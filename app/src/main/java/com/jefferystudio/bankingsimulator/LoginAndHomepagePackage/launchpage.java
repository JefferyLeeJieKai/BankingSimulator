package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jefferystudio.bankingsimulator.R;

import java.util.Timer;
import java.util.TimerTask;

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
                Intent intent = new Intent(launchpage.this, PreLogin.class);
                startActivity(intent);
                finish();
            }
        }, 5000);

    }
}
