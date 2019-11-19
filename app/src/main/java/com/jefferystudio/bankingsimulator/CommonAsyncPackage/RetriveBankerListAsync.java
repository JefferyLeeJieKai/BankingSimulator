package com.jefferystudio.bankingsimulator.CommonAsyncPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RetriveBankerListAsync extends AsyncTask <String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String userID;

    public RetriveBankerListAsync(Context context) {

        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Retrieving associated accounts...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        userID = args[0];

        try{
            String link="https://www.kidzsmartapp.com/databaseAccess/retrieveBankerList.php";
            String data  = URLEncoder.encode("userid", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");

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

            //elist.add(e);
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        progDialog.dismiss();
        ((HomeScreenBanker)context).updateBankerList(result);
    }
}
