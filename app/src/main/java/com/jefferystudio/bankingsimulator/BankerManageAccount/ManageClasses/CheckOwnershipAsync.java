package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class CheckOwnershipAsync extends AsyncTask<String, String, String> {

    private Context context;

    public CheckOwnershipAsync(Context context) {

        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        String input = args[0];
        String bankerID = args[1];
        String classID = args[2];
        String className = args[3];

        try {

            String link = "http://www.kidzsmart.tk/databaseAccess/checkOwnership.php";
            String data = URLEncoder.encode("accountholder", "UTF-8") + "=" +
                    URLEncoder.encode(input, "UTF-8");
            data += "&" + URLEncoder.encode("bankerid", "UTF-8") + "=" +
                    URLEncoder.encode(bankerID, "UTF-8");
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


    }
}
