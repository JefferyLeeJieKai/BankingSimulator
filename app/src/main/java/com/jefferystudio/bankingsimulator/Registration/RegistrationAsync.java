package com.jefferystudio.bankingsimulator.Registration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.PreLogin;
import com.jefferystudio.bankingsimulator.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RegistrationAsync extends AsyncTask<String, String, String> {

    private Activity activity;
    private ProgressDialog progDialog;
    private String name;
    private String email;
    private String username;
    private String password;
    private String gender;
    private String dob;
    private String role;
    private String data;
    private String flag;

    public RegistrationAsync(Activity activity) {

        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(activity);
        progDialog.setMessage("Registering new user...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
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
        flag = args[7];

        try{

            String link = "https://www.kidzsmartapp.com/databaseAccess/registerUser.php";
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

        progDialog.dismiss();
        String[] resultArray = result.split(",");

        if(resultArray[0].equals("Success") && !flag.equals("fromBanker")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle("DigiBank Alert");
            builder.setMessage("Account created successfully.\nA confirmation email will be send to your email shortly.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

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
        else if(resultArray[0].equals("Success") && flag.equals("fromBanker")) {

            RegistrationFragment fragment = (RegistrationFragment) ((HomeScreenBanker)activity).getSupportFragmentManager()
                    .findFragmentById(R.id.frame_layout);

            fragment.updateStudentList();
        }
    }
}
