package com.jefferystudio.bankingsimulator.Registration;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.DepositPackage.DepositConfirmUser;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.LoginScreen;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.PreLogin;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.WithdrawalPackage.WithdrawalConfirm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RegistrationAsync extends AsyncTask<String, String, String> {

    private Activity activity;
    private String name;
    private String email;
    private String username;
    private String password;
    private String gender;
    private String dob;
    private String role;
    private String data;

    public RegistrationAsync(Activity activity) {

        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        name = args[0];
        email = args[1];
        username = args[2];
        password = args[3];
        gender = args[4];
        dob = args [5];
        role = args[6];

        try{

            String link = "http://www.kidzsmart.tk/databaseAccess/registerUser.php";
            data = URLEncoder.encode("name", "UTF-8") + "=" +
                    URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("gender", "UTF-8") + "=" +
                    URLEncoder.encode(gender, "UTF-8");
            data += "&" + URLEncoder.encode("dob", "UTF-8") + "=" +
                    URLEncoder.encode(dob, "UTF-8");
            data += "&" + URLEncoder.encode("role", "UTF-8") + "=" +
                    URLEncoder.encode(role, "UTF-8");

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
        catch(Exception e) {

        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
        if(result.equals("Success")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle("DigiBank Alert");
            builder.setMessage("Account created successfully. A confirmation email will be send to your email shortly");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Intent intent = new Intent(activity, PreLogin.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            });

            AlertDialog quitDialog = builder.create();
            quitDialog.show();
        }
    }
}
