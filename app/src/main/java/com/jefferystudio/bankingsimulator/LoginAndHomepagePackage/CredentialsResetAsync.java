package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class CredentialsResetAsync extends AsyncTask<String, String, String> {

    private Context context;
    private String flag;
    private String accountType;
    private String userID;
    private TextInputLayout firstField;
    private TextInputLayout secondField;
    private TextInputLayout thirdField;
    private boolean passwordsMatch;
    private String data;

    public CredentialsResetAsync(Context context, String flag, String accountType, TextInputLayout firstField,
                                 TextInputLayout secondField, TextInputLayout thirdField) {

        this.context = context;
        this.flag = flag;
        this.accountType = accountType;
        this.firstField = firstField;
        this.secondField = secondField;
        this.thirdField = thirdField;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String[] args) {

        if(flag.equals("ChangePassword")) {
            userID = args[0];
        }

        StringBuffer sb = new StringBuffer("");
        String link = "";

        if(flag.equals("ChangePassword")) {

            String oldPassword = firstField.getEditText().getText().toString().trim();
            String newPassword = secondField.getEditText().getText().toString().trim();
            String confirmPassword = thirdField.getEditText().getText().toString().trim();

            try {

                link = "http://www.kidzsmart.tk/databaseAccess/changePassword.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" +
                       URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("oldpassword", "UTF-8") + "=" +
                        URLEncoder.encode(oldPassword, "UTF-8");
                data += "&" + URLEncoder.encode("newpassword", "UTF-8") + "=" +
                        URLEncoder.encode(newPassword, "UTF-8");
            }
            catch(Exception e) {

            }

            if(newPassword.equals(confirmPassword)) {

                passwordsMatch = true;

                try {
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
            }
            else {

                passwordsMatch = false;
            }
        }
        else if(flag.equals("ForgetPassword")) {

            String username = firstField.getEditText().getText().toString().trim();
            String email = secondField.getEditText().getText().toString().trim();

            try {

                link = "http://www.kidzsmart.tk/databaseAccess/forgetPassword.php";
                data = URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" +
                        URLEncoder.encode(email, "UTF-8");
            }
            catch(Exception e) {

            }
        }
        else if(flag.equals("ForgetUsername")) {

            String email = firstField.getEditText().getText().toString().trim();

            try {

                link = "http://www.kidzsmart.tk/databaseAccess/forgetUsername.php";
                data = URLEncoder.encode("email", "UTF-8") + "=" +
                        URLEncoder.encode(email, "UTF-8");
            }
            catch(Exception e) {

            }
        }

        try {
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
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        //Toast.makeText(context, data, Toast.LENGTH_LONG).show();

        if (flag.equals("ChangePassword")) {

            if (!passwordsMatch) {

                secondField.setError("Password do not match");
                thirdField.setError("Password do not match");
            } else if (passwordsMatch) {

                String[] resultArray = result.split(",");

                if (resultArray[0].equals("Success")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("DigiBank Alert");
                    builder.setMessage("Password changed successfully.\nDo you want to remain on this page?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }

                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (accountType.equals("AccountHolder")) {

                                Fragment homeFrag = new HomeFragmentUser();
                                Bundle args = new Bundle();
                                args.putString("userID", userID);
                                homeFrag.setArguments(args);

                                ((HomeScreenUser) context).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout, homeFrag)
                                        .commit();
                            } else if (accountType.equals("Banker")) {

                                Fragment homeFrag = new HomeFragmentUser();
                                Bundle args = new Bundle();
                                args.putString("userID", userID);
                                homeFrag.setArguments(args);

                                ((HomeScreenBanker) context).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout, homeFrag)
                                        .commit();
                            }
                        }
                    });

                    AlertDialog quitDialog = builder.create();
                    quitDialog.show();
                } else if (resultArray[0].equals("Fail")) {

                    firstField.setError("Please enter the correct password");
                }
            }
        }
        else if(flag.equals("ForgetPassword")) {

            String[] resultArray = result.split(",");

            if(resultArray[0].equals("Fail")) {

                firstField.setError("Username does not exist");
            }

            if(resultArray[1].equals("Fail")) {

                secondField.setError("Email does not exist");
            }

            if(resultArray[0].equals("Success") && resultArray[1].equals("Success")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("DigiBank Alert");
                builder.setMessage("Your password has been reset.\nPlease check your email for your new password.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Activity activity = ((ForgetPassword) context);

                        Intent intent = new Intent(activity, LoginScreen.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });

                AlertDialog quitDialog = builder.create();
                quitDialog.show();
            }
        }
        else if(flag.equals("ForgetUsername")) {

            String[] resultArray = result.split(",");

            if(resultArray[0].equals("Fail")) {

                firstField.setError("Email does not exist");
            }

            if(resultArray[0].equals("Success")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("DigiBank Alert");
                builder.setMessage("An email with your username has been sent. \nPlease check your inbox for the email.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Activity activity = ((ForgetUsername) context);

                        Intent intent = new Intent(activity, LoginScreen.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });

                AlertDialog quitDialog = builder.create();
                quitDialog.show();
            }
        }
    }
}
