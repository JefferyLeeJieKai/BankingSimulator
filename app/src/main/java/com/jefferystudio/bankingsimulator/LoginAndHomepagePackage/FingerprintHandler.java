package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context appContext;

    public FingerprintHandler(Context context) {

        appContext = context;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(appContext,
            Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        Toast.makeText(appContext, "Please place your finger on the fingerprint sensor.", Toast.LENGTH_LONG).show();

        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {

        Toast.makeText(appContext, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {

        Toast.makeText(appContext, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {

        Toast.makeText(appContext, "Authentication failed.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        SharedPreferences pref = appContext.getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);

        if (pref.getString("userID", "NotFound").equals("NotFound")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(appContext);

            builder.setTitle("DigiBank Alert");
            builder.setMessage("Please enable fingerprint login in your settings.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });

            AlertDialog noFingerprintDialog = builder.create();
            noFingerprintDialog.show();
        }
        else {

            Bundle args = new Bundle();
            args.putString("userID", pref.getString("userID", null));
            args.putString("userName", pref.getString("username", null));

            new FingerprintLoginAsync(appContext, "checkfingerprint", args.getString("userID"))
                    .execute(args);

        }

    }
}
