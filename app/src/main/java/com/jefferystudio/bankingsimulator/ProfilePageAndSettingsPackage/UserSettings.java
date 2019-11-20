package com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage;

import android.app.Activity;
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

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.ChangePasswordFragment;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.FingerprintLoginAsync;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.FingerprintSettingAsync;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;


import java.util.concurrent.TimeUnit;

public class UserSettings extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Switch switchbtn;
    private Button backbtn;
    private Button btnchange;
    private Bundle args;
    private TextView fingerEnable;
    private Boolean lock;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FINGER_LOCK = "fingerLock";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);


        args = getIntent().getExtras();
        switchbtn = (Switch)findViewById(R.id.switch1);
        backbtn = (Button) findViewById(R.id.btnback);
        btnchange = (Button) findViewById(R.id.changepass);
        fingerEnable = findViewById(R.id.fingerOnOff);
        loadFinger();


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle newArgs = new Bundle();
                newArgs.putString("userID", args.getString("userID"));
                newArgs.putString("userName", args.getString("userName"));
                Intent intent = new Intent(getApplicationContext(), HomeScreenUser.class);
                intent.putExtras(newArgs);
                startActivity(intent);
                finish();
            }
        });

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ChangePasswordFragment.class);
                startActivity(intent);
                finish();
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

                String result = "";

                try {

                    result = new FingerprintSettingAsync(this, "enablefingerprint", args.getString("userID"))
                            .execute()
                            .get(5000, TimeUnit.MILLISECONDS);
                }
                catch(Exception e) {

                }

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
        }

        else{
            SharedPreferences myPrefs = getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.clear();
            editor.apply();

            String result = "";

            try {

                result = new FingerprintSettingAsync(this, "disablefingerprint", args.getString("userID"))
                        .execute()
                        .get(5000, TimeUnit.MILLISECONDS);
            }
            catch(Exception e) {

            }

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

}
