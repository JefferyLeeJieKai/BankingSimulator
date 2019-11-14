package com.jefferystudio.bankingsimulator.WithdrawalPackage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.ChangePasswordFragment;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.FingerprintAsync;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Registration.Registration;
import com.jefferystudio.bankingsimulator.ViewTransactionsPackage.ViewTransactions;

import java.util.concurrent.TimeUnit;

public class UserSettings extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Switch switchbtn;
    private Button backbtn;
    private Button btnchange;
    private Bundle args;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);


        args = getIntent().getExtras();
        switchbtn = (Switch)findViewById(R.id.switch1);
        backbtn = (Button) findViewById(R.id.btnback);
        btnchange = (Button) findViewById(R.id.changepass);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle newArgs = new Bundle();
                newArgs.putString("userID", args.getString("userID"));
                newArgs.putString("userName", args.getString("userName"));
                Intent intent = new Intent(getApplicationContext(), HomeScreenUser.class);
                intent.putExtras(newArgs);
                startActivity(intent);
            }
        });

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment changePassword = new ChangePasswordFragment();
                changePassword.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, changePassword);
                transaction.commit();
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

                    result = new FingerprintAsync(this, "enablefingerprint", args.getString("userID"))
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

                result = new FingerprintAsync(this, "disablefingerprint", args.getString("userID"))
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

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

                AlertDialog fingerprintSettingsDialog = builder.create();
                fingerprintSettingsDialog.show();
            }
        }


    }
}
