package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragment;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreen;
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
    private HomeScreen homeScreenActivity;
    private ArrayList<Exception> elist = new ArrayList<>();
    private String flag;
    private String link;
    private String data;
    private String userID;
    private String savingGoalName;
    private String goalAmount;

    public SettingsGoalsAsync(Context context, String flag) {

        this.context = context;
        this.flag = flag;
        homeScreenActivity = (HomeScreen) context;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");
        userID = args[0];

        if(flag.equals("NewSavingGoal")) {

            savingGoalName = args[1];
            goalAmount = args[2];

            try {
                link = "http://www.kidzsmart.tk/kidzsmartApp/databaseAccess/saveGoal.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" +
                       URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("savinggoalname", "UTF-8") + "=" +
                        URLEncoder.encode(savingGoalName, "UTF-8");
                data += "&" + URLEncoder.encode("itemcost", "UTF-8") + "=" +
                        URLEncoder.encode(goalAmount, "UTF-8");

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

        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        String[] resultArray = result.split(",");

        if(resultArray[0].equals("True")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("DigiBank Alert");
            builder.setMessage(resultArray[1] + " Do you want to key in a new goal?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }

            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment homeFrag = new HomeFragment();
                    Bundle args = new Bundle();
                    args.putString("userID", userID);
                    homeFrag.setArguments(args);

                    homeScreenActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, homeFrag)
                            .commit();
                }
            });

            AlertDialog quitDialog = builder.create();
            quitDialog.show();
        }
        else if(resultArray[0].equals("False")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("DigiBank Alert");
            builder.setMessage(resultArray[1] + " Do you want to retry your action?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }

            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment homeFrag = new HomeFragment();
                    Bundle args = new Bundle();
                    args.putString("userID", userID);
                    homeFrag.setArguments(args);

                    homeScreenActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, homeFrag)
                            .commit();
                }
            });

            AlertDialog quitDialog = builder.create();
            quitDialog.show();
        }
    }
}
