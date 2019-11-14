package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SignInAsync extends AsyncTask<String, String, String> {

    private Context context;
    private TextInputLayout usernameTextBox;
    private TextInputLayout passwordTextBox;
    private String username;

    public SignInAsync(Context context, TextInputLayout usernameTextBox, TextInputLayout passwordTextBox) {

        this.context = context;
        this.usernameTextBox = usernameTextBox;
        this.passwordTextBox = passwordTextBox;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");
        username = args[0];
        String password = args[1];

        try{
            String link="http://www.kidzsmart.tk/databaseAccess/loginVerification.php";
            String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
        }
        catch(Exception e){

        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        String[] resultArray = result.split(",");

        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        if(resultArray[0].equals("True")) {

            Intent intent = null;

            if(resultArray[3].equals("AccountHolder")) {

                intent = new Intent(context.getApplicationContext(), HomeScreenUser.class);
            }
            else if(resultArray[3].equals("Banker")) {

                intent = new Intent(context.getApplicationContext(), HomeScreenBanker.class);
            }

            Bundle args = new Bundle();
            args.putString("userID", resultArray[1]);
            args.putString("currentBalance", resultArray[2]);
            args.putString("userName", username);
            intent.putExtras(args);

            context.startActivity(intent);
            ((Activity)context).finish();
        }
        else if(resultArray[0].equals("False")){

            usernameTextBox.setError("Invalid Username or Password");
            passwordTextBox.setError("Invalid Username or Password");
        }
        else if(resultArray[0].equals("NotActivated")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("DigiBank Alert");
            builder.setMessage("Account is not activated");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });

            AlertDialog notActivatedDialog = builder.create();
            builder.show();
        }
    }
}
