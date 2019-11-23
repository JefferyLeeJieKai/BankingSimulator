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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.ChangePassword;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.FingerprintSettingAsync;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.OTP.OTPActivity;
import com.jefferystudio.bankingsimulator.R;


import java.util.concurrent.TimeUnit;

public class UserSettings extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Context context = this;
    private Switch switchbtn;
    private Button backbtn;
    private Button btnchange;
    private Bundle args;
    private TextView fingerEnable;
    private Boolean lock;
    private Spinner dailyLimitSpinner;
    private float dailyLimit;
    private float currentLimit;
    private int currentPosition;
    private String truncatedLimit;
    private float newCurrentLimit;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String FINGER_LOCK = "fingerLock";
    public static final int OTP_CODE = 200;

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

        dailyLimitSpinner = findViewById(R.id.limitspinner);
        final ArrayAdapter<CharSequence> dailyLimitAdapter = ArrayAdapter.createFromResource(this, R.array.dailylimit,
                                                                                       android.R.layout.simple_spinner_item);
        dailyLimitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dailyLimitSpinner.setAdapter(dailyLimitAdapter);
        new DailyLimitAsync(this, "RetrieveLimit", dailyLimitSpinner).execute(args.getString("userID"));

        dailyLimitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if(position != currentPosition) {

                    String newLimit = adapterView.getItemAtPosition(position).toString();
                    /*final String*/ truncatedLimit = newLimit.substring(1);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("DigiBank Alert");
                    builder.setMessage("Do you want to set daily limit to: " + newLimit + "?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            float newLimitFloat = Float.valueOf(truncatedLimit);
                            float currentLimitDiff = newLimitFloat - dailyLimit;
                            newCurrentLimit = currentLimit + currentLimitDiff;

                            if (newCurrentLimit < 0) {

                                newCurrentLimit = 0;
                            }

                            /*new DailyLimitAsync(context, "UpdateLimit", dailyLimitSpinner).execute(args.getString("userID"), truncatedLimit,
                                    Float.toString(newCurrentLimit));*/
                            Intent intent = new Intent(context, OTPActivity.class);
                            args.putString("accountType", "OTP");
                            args.putString("flag", "ChangeLimit");
                            intent.putExtras(args);
                            startActivityForResult(intent, OTP_CODE);
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialogInterface, int i) {

                            dailyLimitSpinner.setSelection(currentPosition);
                        }
                    });

                    AlertDialog confirmDialog = builder.create();
                    confirmDialog.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
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

                new FingerprintSettingAsync(this, "enablefingerprintUser", args.getString("userID")).execute();
            }
        }
        else {
            SharedPreferences myPrefs = getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.clear();
            editor.apply();

            new FingerprintSettingAsync(this, "disablefingerprintUser", args.getString("userID")).execute();
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

    public void updateDailyLimit(float dailyLimit, float currentLimit, int currentPosition) {

        this.dailyLimit = dailyLimit;
        this.currentLimit = currentLimit;
        this.currentPosition = currentPosition;
    }

    public String getCurrentDailyLimit() {

        return Float.toString(dailyLimit);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        super.onActivityResult(requestCode, resultCode, result);

        if (requestCode == OTP_CODE) {

            if (resultCode == RESULT_CANCELED) {


            } else if (resultCode == RESULT_OK) {

                new DailyLimitAsync(context, "UpdateLimit", dailyLimitSpinner).execute(args.getString("userID"), truncatedLimit,
                        Float.toString(newCurrentLimit));
            }
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
