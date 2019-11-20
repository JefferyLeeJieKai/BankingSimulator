package com.jefferystudio.bankingsimulator.CommonAsyncPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class UpdateBalanceAsync extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private TextView currentUserBalance;
    private TextView currentLimit;
    private ArrayList<Exception> elist = new ArrayList<Exception>();
    private String data;

    public UpdateBalanceAsync(Context context, TextView currentUserBalance, TextView currentLimit) {

        this.context = context;
        this.currentUserBalance = currentUserBalance;
        this.currentLimit = currentLimit;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Updating balance...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");
        String userID = args[0];

        try{
            String link="https://www.kidzsmartapp.com/databaseAccess/checkBalance.php";
            data  = URLEncoder.encode("userid", "UTF-8") + "=" +
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

        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        /*for(Exception e : elist) {

            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }*/
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        progDialog.dismiss();

        String[] resultArray = result.split(",");

        if(currentUserBalance != null) {

            currentUserBalance.setText("Balance: " + resultArray[0]);
        }

        if(currentLimit != null) {

            currentLimit.setText("Remaining limit: " + resultArray[1]);
        }
    }
}
