package com.jefferystudio.bankingsimulator.OTP;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jefferystudio.bankingsimulator.R;

public class OTPActivity extends AppCompatActivity {

    private Bundle args;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_activity_frame);

        args = getIntent().getExtras();

        Fragment OTPFragment = new OTPFragment();
        OTPFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.otpFrame, OTPFragment).commit();

    }
}
