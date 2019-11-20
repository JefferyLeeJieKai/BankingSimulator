package com.jefferystudio.bankingsimulator.Quiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class UpdateScoreAsync extends AsyncTask <String, String, String>{

    private Context context;
    private ProgressDialog progDialog;

    public UpdateScoreAsync(Context context) {

        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Uploading score to database...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        String currentUserID = args[0];
        String currentScore = args[1];

        try {

            String link = "https://www.kidzsmartapp.com/databaseAccess/uploadQuizScore.php";
            String data = URLEncoder.encode("userid", "UTF-8") + "=" +
                    URLEncoder.encode(currentUserID, "UTF-8");
            data += "&" + URLEncoder.encode("score", "UTF-8") + "=" +
                    URLEncoder.encode(currentScore, "UTF-8");

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
        catch(IOException e) {

        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s) {

        progDialog.dismiss();
    }
}
