package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.CommonAsyncPackage.RetrieveProfilePicAsync;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.RetriveBankerListAsync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SignInAsync extends AsyncTask<String, String, String> {

    private Bundle bArgs;
    private Context context;
    private ProgressDialog progDialog;
    private TextInputLayout usernameTextBox;
    private TextInputLayout passwordTextBox;
    private String username;
    private ArrayList<String> errorList = new ArrayList<String>();

    public SignInAsync(Context context, TextInputLayout usernameTextBox, TextInputLayout passwordTextBox) {

        this.context = context;
        this.usernameTextBox = usernameTextBox;
        this.passwordTextBox = passwordTextBox;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Retrieving Credentials");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");
        username = args[0];
        String password = args[1];

        publishProgress("5");

        try{
            String link="https://www.kidzsmartapp.com/databaseAccess/loginVerification.php";
            String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

            publishProgress("15");

            URL url = new URL(link);
            URLConnection connection = url.openConnection();

            publishProgress("20");

            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

            wr.write(data);
            wr.flush();

            publishProgress("30");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;

            publishProgress("40");

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            publishProgress("50");
        }
        catch(IOException e){

            errorList.add("Unable to communicate with server.");
        }

        String[] resultArray = sb.toString().split(",");
        String resultPHP = resultArray[0];

        if(resultArray[0].equals("True")) {

            Intent intent = null;

            if(resultArray[3].equals("AccountHolder")) {

                intent = new Intent(context.getApplicationContext(), HomeScreenUser.class);
            }
            else if(resultArray[3].equals("Banker")) {

                intent = new Intent(context.getApplicationContext(), HomeScreenBanker.class);
            }

            publishProgress("60");

            bArgs = new Bundle();
            bArgs.putString("userID", resultArray[1]);
            bArgs.putString("currentBalance", resultArray[2]);
            bArgs.putString("userName", username);

            publishProgress("70");

            if(!resultArray[4].equals("NoImage")) {


                ContextWrapper cw = new ContextWrapper(context);
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File file = new File(directory, "ProfilePicture.jpg");

                String catchString = "";

                try {

                    catchString = new RetrieveProfilePicAsync(context, file, errorList)
                            .execute(resultArray[4])
                            .get(5000, TimeUnit.MILLISECONDS);

                    publishProgress("90");

                } catch (Exception e) {


                }

                publishProgress("100");

                intent.putExtras(bArgs);

                progDialog.dismiss();

                context.startActivity(intent);

                ((Activity) context).finish();
            }
            else {

                publishProgress("100");

                intent.putExtras(bArgs);

                progDialog.dismiss();

                context.startActivity(intent);

                ((Activity) context).finish();
            }
        }

        return resultPHP;
    }

    protected void onProgressUpdate(String[] args) {

        int progress = Integer.valueOf(args[0]);
        progDialog.setProgress(progress);

        if(progress == 5) {

            progDialog.setMessage("Credentials Retrieved...");

        }
        else if(progress == 15) {

            progDialog.setMessage("Packing Data for transfer...");

        }
        else if(progress == 20) {

            progDialog.setMessage("Establishing connection...");
        }
        else if(progress == 30) {

            progDialog.setMessage("Sending data over...");
        }
        else if(progress == 40) {

            progDialog.setMessage("Waiting for a response...");
        }
        else if(progress == 50) {

            progDialog.setMessage("Response received...");
        }
        else if(progress == 60) {

            progDialog.setMessage("Login Success!");
        }
        else if(progress == 70) {

            progDialog.setMessage("Preparing to retrieve account info...");
        }
        else if(progress == 90) {

            progDialog.setMessage("Account info retrieved");
        }
        else if(progress == 100) {

            progDialog.setMessage("Logging in in now...");
        }
    }

    @Override
    protected void onPostExecute(String result) {

        progDialog.dismiss();

        //Toast.makeText(context, errorList.get(0).toString(), Toast.LENGTH_LONG).show();

        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        if (result.equals("False")) {

            usernameTextBox.setError("Invalid Username or Password");
            passwordTextBox.setError("Invalid Username or Password");
        }
        else if (result.equals("NotActivated")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("DigiBank Alert");

            builder.setMessage("Account is not activated");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

            AlertDialog notActivatedDialog = builder.create();
            notActivatedDialog.show();
        }
    }
}
