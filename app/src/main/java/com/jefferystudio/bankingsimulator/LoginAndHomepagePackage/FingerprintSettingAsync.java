package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.CommonAsyncPackage.RetrieveProfilePicAsync;
import com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage.BankerSettings;
import com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage.UserSettings;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FingerprintSettingAsync extends AsyncTask<Bundle, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String flag;
    private String userID;
    private Bundle args;
    private ArrayList<String> errorList = new ArrayList<String>();

    public FingerprintSettingAsync(Context context, String flag, String userID) {

        this.context = context;
        this.flag = flag;
        this.userID = userID;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);

        if(flag.equals("enablefingerprintUser") || flag.equals("enablefingerprintBanker")) {

            progDialog.setMessage("Handling fingerprint enable request");
        }
        else if(flag.equals("disablefingerprintUser") || flag.equals("disablefingerprintBanker")) {

            progDialog.setMessage("Handling fingerprint disable request");
        }

        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(Bundle[] bundle) {

        StringBuffer sb = new StringBuffer("");

        try {

            String link = "https://www.kidzsmartapp.com/databaseAccess/handleFingerprint.php";
            String data = URLEncoder.encode("flag", "UTF-8") + "=" +
                    URLEncoder.encode(flag, "UTF-8");
            data += "&" + URLEncoder.encode("userid", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");

            URL url = new URL(link);
            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
        }
        catch (Exception e) {

        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        progDialog.dismiss();

        if(flag.equals("enablefingerprintUser")) {

            ((UserSettings)context).fingerprintEnabledResult(result);
        }
        else if(flag.equals("disablefingerprintUser")) {

            ((UserSettings)context).fingerprintDisabledResult(result);
        }
        else if(flag.equals("enablefingerprintBanker")) {

            ((BankerSettings)context).fingerprintEnabledResult(result);
        }
        else if(flag.equals("disablefingerprintBanker")) {

            ((BankerSettings)context).fingerprintDisabledResult(result);
        }
    }
}
