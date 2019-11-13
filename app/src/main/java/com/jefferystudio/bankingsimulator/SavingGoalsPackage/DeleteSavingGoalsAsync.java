package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class DeleteSavingGoalsAsync extends AsyncTask<String, String, String> {

    private Context context;
    private String goalID;

    public DeleteSavingGoalsAsync(Context context, String goalID) {

        this.context = context;
        this.goalID = goalID;
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        try {

            String link = "http://www.kidzsmart.tk/databaseAccess/deleteGoal.php";
            String data = URLEncoder.encode("goalid", "UTF-8") + "=" +
                    URLEncoder.encode(goalID, "UTF-8");


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
        catch(Exception e) {

        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
