package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SettingsGoalsAsync extends AsyncTask<String, String, String> {

    private Context context;
    private HomeScreenUser homeScreenUserActivity;
    private ProgressDialog progDialog;
    private ArrayList<Exception> elist = new ArrayList<>();
    private String flag;
    private String link;
    private String data;
    private String userID;
    private String userName;
    private String savingGoalName;
    private String goalAmount;
    private String goalDeadLine;
    private String goalPriority;

    public SettingsGoalsAsync(Context context, String flag, String userName) {

        this.context = context;
        this.flag = flag;
        this.userName = userName;
        homeScreenUserActivity = (HomeScreenUser) context;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Creating new saving goal...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");
        userID = args[0];

        if(flag.equals("NewSavingGoal")) {

            savingGoalName = args[1];
            goalAmount = args[2];
            goalDeadLine = args[3];
            goalPriority = args[4];

            try {
                link = "https://www.kidzsmartapp.com/databaseAccess/saveGoal.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" +
                       URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("savinggoalname", "UTF-8") + "=" +
                        URLEncoder.encode(savingGoalName, "UTF-8");
                data += "&" + URLEncoder.encode("itemcost", "UTF-8") + "=" +
                        URLEncoder.encode(goalAmount, "UTF-8");
                data += "&" + URLEncoder.encode("deadline", "UTF-8") + "=" +
                        URLEncoder.encode(goalDeadLine, "UTF-8");
                data += "&" + URLEncoder.encode("priority", "UTF-8") + "=" +
                        URLEncoder.encode(goalPriority, "UTF-8");
            }
            catch(Exception e) {


            }
        }

        try{
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

            elist.add(e);
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        progDialog.dismiss();
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        SavingGoalsAddFragment fragment = (SavingGoalsAddFragment)((HomeScreenUser)context)
                                          .getSupportFragmentManager().findFragmentById(R.id.frame_layout);

        fragment.updateResult(result);
    }
}
