package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.CommonAsyncPackage.RetrieveProfilePicAsync;
import com.jefferystudio.bankingsimulator.R;

import java.io.BufferedReader;
import java.io.File;
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
    private TextInputLayout usernameTextBox;
    private TextInputLayout passwordTextBox;
    private String username;
    private ArrayList<String> errorList = new ArrayList<String>();
    private Dialog dialog;
    private TextView progressBar;

    public SignInAsync(Context context, TextInputLayout usernameTextBox, TextInputLayout passwordTextBox) {

        this.context = context;
        this.usernameTextBox = usernameTextBox;
        this.passwordTextBox = passwordTextBox;
    }

    @Override
    protected void onPreExecute() {

        dialog = new Dialog(context, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loadingicon);
        progressBar = dialog.findViewById(R.id.text_view);
        dialog.show();
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
        catch(Exception e){

            errorList.add(e.getMessage());
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

            publishProgress("70");

            bArgs = new Bundle();
            bArgs.putString("userID", resultArray[1]);
            bArgs.putString("currentBalance", resultArray[2]);
            bArgs.putString("userName", username);

            publishProgress("85");

            if(!resultArray[4].equals("NoImage")) {

                publishProgress("100");

                intent.putExtras(bArgs);

                SharedPreferences myPrefs = context.getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("imageLink", resultArray[4]);
                editor.apply();

                context.startActivity(intent);

                ((Activity) context).finish();
                //}
            }
            else {

                publishProgress("100");

                SharedPreferences myPrefs = context.getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("imageLink", "NoImage");
                editor.apply();

                intent.putExtras(bArgs);

                context.startActivity(intent);

                ((Activity) context).finish();
            }
        }

        return resultPHP;
    }

    protected void onProgressUpdate(String[] args) {

        int progress = Integer.valueOf(args[0]);
        progressBar.setText(progress + "%");
    }

    @Override
    protected void onPostExecute(String result) {

        //Toast.makeText(context, errorList.get(0).toString(), Toast.LENGTH_LONG).show();

        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        dialog.dismiss();
        progressBar.setVisibility(View.GONE);

        String[] resultArray = result.split(",");

        if (resultArray[0].equals("False")) {

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
