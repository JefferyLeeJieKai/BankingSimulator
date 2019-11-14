package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class UpdateStudentListAsync extends AsyncTask<String, String, String>{

    private Context context;
    private String bankerID;
    private String username;
    private String classID;
    private String className;

    public UpdateStudentListAsync(Context context, String bankerID, String username, String classID,
                                  String className) {

        this.context = context;
        this.bankerID = bankerID;
        this.username = username;
        this.classID = classID;
        this.className = className;
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        try {

            String link = "http://www.kidzsmart.tk/databaseAccess/addStudent.php";

            String data = URLEncoder.encode("bankerid", "UTF-8") + "=" +
                          URLEncoder.encode(bankerID, "UTF-8");
            data += "&" + URLEncoder.encode("accountholderusername", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("classid", "UTF-8") + "=" +
                    URLEncoder.encode(classID, "UTF-8");
            data += "&" + URLEncoder.encode("classname", "UTF-8") + "=" +
                    URLEncoder.encode(className, "UTF-8");

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

        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}