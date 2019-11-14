package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FingerprintAsync extends AsyncTask<String,String,String> {

    private Context context;
    private String flag;
    private String userID;

    public FingerprintAsync(Context context, String flag, String userID) {

        this.context = context;
        this.flag = flag;
        this.userID = userID;
    }

    @Override
    protected String doInBackground(String... strings) {

        StringBuffer sb = new StringBuffer("");

        try {

            String link="http://www.kidzsmart.tk/databaseAccess/handleFingerprint.php";
            String data  = URLEncoder.encode("flag", "UTF-8") + "=" +
                    URLEncoder.encode(flag, "UTF-8");
            data += "&" + URLEncoder.encode("userid", "UTF-8") + "=" +
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
        catch(Exception e) {

        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
