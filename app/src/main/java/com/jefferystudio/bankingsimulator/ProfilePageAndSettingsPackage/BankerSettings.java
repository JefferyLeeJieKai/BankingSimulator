package com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.ChangePasswordBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.FingerprintLoginAsync;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.FingerprintSettingAsync;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Settings;


import java.util.concurrent.TimeUnit;

public class BankerSettings extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Switch switchbtn;
    private Button backbtn;
    private Button btnchange;
    private Button datetimebtn;
    private Bundle args;
    private TextView fingerEnable;
    private Boolean lock;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FINGER_LOCK = "fingerLock";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bankersettings);


        args = getIntent().getExtras();
        switchbtn = (Switch)findViewById(R.id.switch1);
        backbtn = (Button) findViewById(R.id.btnback);
        btnchange = (Button) findViewById(R.id.changepass);
        datetimebtn = (Button) findViewById(R.id.btndatetime);
        fingerEnable = findViewById(R.id.fingerOnOff);
        loadFinger();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle newArgs = new Bundle();
                newArgs.putString("userID", args.getString("userID"));
                newArgs.putString("userName", args.getString("userName"));
                Intent intent = new Intent(getApplicationContext(), HomeScreenBanker.class);
                intent.putExtras(newArgs);
                startActivity(intent);
                finish();
            }
        });

        datetimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
            }
        });

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ChangePasswordBanker.class);
                Bundle newArgs = new Bundle();
                newArgs.putString("userID", args.getString("userID"));
                newArgs.putString("userName", args.getString("userName"));
                startActivity(intent);
            }
        });

        switchbtn.setOnCheckedChangeListener(this);
    }


    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        if(switchbtn.isChecked()){
            SharedPreferences pref = getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);

            if(pref.getString("userID", "NotFound").equals("NotFound")) {

                SharedPreferences myPrefs = getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("userID", args.getString("userID"));
                editor.putString("username", args.getString("userName"));
                editor.apply();

                new FingerprintSettingAsync(this, "enablefingerprintBanker", args.getString("userID")).execute();
            }
        }

        else{
            SharedPreferences myPrefs = getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.clear();
            editor.apply();

            new FingerprintSettingAsync(this, "disablefingerprintBanker", args.getString("userID")).execute();
        }
    }

    private void updateFingerUnlock(boolean locknew) {

        lock = locknew;

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(FINGER_LOCK, lock);
        editor.apply();
    }

    private void loadFinger() {
        SharedPreferences pref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        lock = pref.getBoolean(FINGER_LOCK, false);
        switchbtn.setChecked(lock);

        if (lock == true) {
            fingerEnable.setText("FINGERPRINT ENABLED");
        }

        else if (lock == false){
            fingerEnable.setText("FINGERPRINT DISABLED");
        }
    }

    public void fingerprintEnabledResult(String result) {

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("Success")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("DigiBank Alert");
            builder.setMessage("Fingerprint enabled!");
            updateFingerUnlock(true);
            fingerEnable.setText("FINGERPRINT ENABLED");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });

            AlertDialog fingerprintSettingsDialog = builder.create();
            fingerprintSettingsDialog.show();
        }
    }

    public void fingerprintDisabledResult(String result) {

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("Success")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("DigiBank Alert");
            builder.setMessage("Fingerprint disabled!");
            updateFingerUnlock(false);
            fingerEnable.setText("FINGERPRINT DISABLED");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });

            AlertDialog fingerprintSettingsDialog = builder.create();
            fingerprintSettingsDialog.show();
        }
    }
}
