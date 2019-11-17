package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

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

public class UpdateSavingGoalsAsync extends AsyncTask<String, String, String> {

    private Context context;
    private HomeScreenUser homeScreenUserActivity;
    private String userID;
    private String userName;
    private String flag;
    private String link;
    private String data;

    public UpdateSavingGoalsAsync(Context context, String flag, String userName) {

        this.context = context;
        this.flag = flag;
        this.userName = userName;
        homeScreenUserActivity = (HomeScreenUser)context;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");
        userID = args[0];
        String goalID = args[1];
        String toChange = args[2];

        try {

            link = "https://www.kidzsmartapp.com/databaseAccess/updateSavingGoals.php";

            data = URLEncoder.encode("userid", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");
            data += "&" + URLEncoder.encode("goalid", "UTf-8") + "=" +
                    URLEncoder.encode((goalID), "UTF-8");
            data += "&" + URLEncoder.encode("tochange", "UTF-8") + "=" +
                    URLEncoder.encode(toChange, "UTF-8");
            data += "&" + URLEncoder.encode("flag", "UTF-8") + "=" +
                    URLEncoder.encode(flag, "UTF-8");

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

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("True")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("DigiBank Alert");
            builder.setMessage(resultArray[1] + " Do you want to change it again?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }

            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment homeFrag = new HomeFragmentUser();
                    Bundle args = new Bundle();
                    args.putString("userID", userID);
                    args.putString("userName", userName);
                    homeFrag.setArguments(args);

                    homeScreenUserActivity.getSupportFragmentManager().beginTransaction()
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

                    Fragment homeFrag = new HomeFragmentUser();
                    Bundle args = new Bundle();
                    args.putString("userID", userID);
                    homeFrag.setArguments(args);

                    homeScreenUserActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, homeFrag)
                            .commit();
                }
            });

            AlertDialog quitDialog = builder.create();
            quitDialog.show();
        }
    }
}
