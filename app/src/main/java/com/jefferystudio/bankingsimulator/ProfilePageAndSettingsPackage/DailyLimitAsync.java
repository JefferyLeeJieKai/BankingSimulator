package com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class DailyLimitAsync extends AsyncTask<String, String, String> {

    private Context context;
    private String flag;
    private Spinner dailyLimitSpinner;
    private ProgressDialog progDialog;
    private String data;
    private String newDailyLimit;
    private String newCurrentLimit;

    public DailyLimitAsync(Context context, String flag, Spinner dailyLimitSpinner) {

        this.context = context;
        this.flag = flag;
        this.dailyLimitSpinner = dailyLimitSpinner;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);

        if(flag.equals("RetrieveLimit")) {

            progDialog.setMessage("Retrieving daily limit...");
        }
        else if(flag.equals("UpdateLimit")) {

            progDialog.setMessage("Updating daily limit...");
        }

        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        String userID = args[0];

        try {

            String link = "https://www.kidzsmartapp.com/databaseAccess/dailyLimitSettings.php";
            data = URLEncoder.encode("userid", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");
            data += "&" + URLEncoder.encode("flag", "UTF-8") + "=" +
                    URLEncoder.encode(flag, "UTF-8");

            if(flag.equals("UpdateLimit")) {

                newDailyLimit = args[1];
                newCurrentLimit = args[2];

                data += "&" + URLEncoder.encode("tochangedaily", "UTF-8") + "=" +
                        URLEncoder.encode(newDailyLimit, "UTF-8");
                data += "&" + URLEncoder.encode("tochangecurrent", "UTF-8") + "=" +
                        URLEncoder.encode(newCurrentLimit, "UTF-8");
            }

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
    protected void onPostExecute(String result) {

        progDialog.dismiss();
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        String[] resultArray = result.split(",");

        if(flag.equals("RetrieveLimit") && !resultArray[0].equals("Fail")) {

            int position = checkPosition(resultArray[0]);

            dailyLimitSpinner.setSelection(position);

            ((UserSettings)context).updateDailyLimit(Float.valueOf(resultArray[0]), Float.valueOf(resultArray[1]), true);
        }
        else if(flag.equals("UpdateLimit")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("DigiBank Alert");

            if(resultArray[0].equals("Success")) {

                builder.setMessage("Daily Limit successfully changed!");
                ((UserSettings)context).updateDailyLimit(Float.valueOf(newDailyLimit), Float.valueOf(newCurrentLimit), true);
            }
            else if(resultArray[0].equals("Fail")) {

                builder.setMessage("Error occurred when changing daily limit. Please try again");

                int position = checkPosition(((UserSettings)context).getCurrentDailyLimit());
                ((UserSettings)context).setInitialUpdate(false);
                dailyLimitSpinner.setSelection(position);
            }

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });

            AlertDialog showResultDialog = builder.create();
            showResultDialog.show();
        }
    }

    private int checkPosition(String value) {

        int position = 0;

        if(value.equals("100.00")) {

        }
        else if(value.equals("250.00")) {

            position = 1;
        }
        else if(value.equals("500.00")) {

            position = 2;
        }
        else if(value.equals("800.00")) {

            position = 3;
        }
        else if(value.equals("1500.00")) {

            position = 4;
        }
        else if(value.equals("3000.00")) {

            position = 5;
        }

        return position;
    }
}
