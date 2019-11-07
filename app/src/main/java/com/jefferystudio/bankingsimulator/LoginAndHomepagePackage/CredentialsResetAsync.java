package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

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
    private TextInputLayout oldPasswordField;
    private TextInputLayout newPasswordField;
    private TextInputLayout confirmPasswordField;
    private boolean passwordsMatch;

    public CredentialsResetAsync(Context context, String flag, String accountType, TextInputLayout oldPassField,
                                 TextInputLayout newPassField, TextInputLayout conPassField) {

        this.context = context;
        this.flag = flag;
        this.accountType = accountType;
        oldPasswordField = oldPassField;
        newPasswordField = newPassField;
        confirmPasswordField = conPassField;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String[] args) {

        userID = args[0];
        StringBuffer sb = new StringBuffer("");
        String link = "";
        String data = "";

        if(flag.equals("ChangePassword")) {

            String oldPassword = oldPasswordField.getEditText().toString().trim();
            String newPassword = newPasswordField.getEditText().toString().trim();
            String confirmPassword = confirmPasswordField.getEditText().toString().trim();

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

            String username = args[0];
            String email = args[1];
        }
        else if(flag.equals("ForgetUsername")) {

            String email = args[0];
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        if(!passwordsMatch) {

            newPasswordField.setError("Password do not match");
            confirmPasswordField.setError("Password do not match");
        }
        else if(passwordsMatch) {

            String[] resultArray = result.split(",");

            if(resultArray[0].equals("Success")) {

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

                        if(accountType.equals("AccountHolder")) {

                            Fragment homeFrag = new HomeFragmentUser();
                            Bundle args = new Bundle();
                            args.putString("userID", userID);
                            homeFrag.setArguments(args);

                            ((HomeScreenUser)context).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_layout, homeFrag)
                                    .commit();
                        }
                        else if(accountType.equals("Banker")) {

                            Fragment homeFrag = new HomeFragmentUser();
                            Bundle args = new Bundle();
                            args.putString("userID", userID);
                            homeFrag.setArguments(args);

                            ((HomeScreenBanker)context).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_layout, homeFrag)
                                    .commit();
                        }
                    }
                });

                AlertDialog quitDialog = builder.create();
                quitDialog.show();
            }
            else if(resultArray[0].equals("Fail")) {

                oldPasswordField.setError("Please enter the correct password");
            }
        }
    }
}
