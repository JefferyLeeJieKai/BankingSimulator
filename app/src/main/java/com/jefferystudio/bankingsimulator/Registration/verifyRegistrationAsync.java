package com.jefferystudio.bankingsimulator.Registration;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class verifyRegistrationAsync extends AsyncTask <String, String, String> {

    private Activity activity;
    private String username;
    private String email;
    private String data;

    public verifyRegistrationAsync(Activity activity) {

        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        username = args[0];
        email = args[1];

        try{

            String link = "https://www.kidzsmartapp.com/databaseAccess/verifyRegistration.php";
            data = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(email, "UTF-8");

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
        //Toast.makeText(activity, data, Toast.LENGTH_LONG).show();
        //Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
        //((Registration)activity).returnVerification(result);
    }
}
