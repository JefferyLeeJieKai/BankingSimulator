package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class getPersonalParticularsAsync extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String studentID;

    public getPersonalParticularsAsync(Context context) {

        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Retrieving particulars...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        studentID = args[0];
        StringBuffer sb = new StringBuffer("");

        try {

            String link = "https://www.kidzsmartapp.com/databaseAccess/getStudentParticulars.php";
            String data = URLEncoder.encode("studentid", "UTF-8") + "=" +
                    URLEncoder.encode(studentID, "UTF-8");

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
    protected void onPostExecute(String s) {

        progDialog.dismiss();
    }
}
