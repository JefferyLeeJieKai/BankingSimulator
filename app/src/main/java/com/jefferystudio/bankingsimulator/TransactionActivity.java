package com.jefferystudio.bankingsimulator;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class TransactionActivity extends AsyncTask <String, String, String> {

    private Context context;
    private String flag;
    private HomeScreen homeScreenActivity;

    public TransactionActivity(Context context, String flag) {

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

        if(flag.equals("DepositUser")) {

            String userID = args[0];
            String depositAmount = args[1];

            try{
                String link="http://www.kidzsmart.tk/kidzsmartApp/databaseAccess/depositUser.php";
                String data  = URLEncoder.encode("userid", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("mydeposit", "UTF-8") + "=" +
                        URLEncoder.encode(depositAmount, "UTF-8");

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
        }
        else if(flag.equals("WithdrawalUser")) {


        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("True")) {

            homeScreenActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new HomeFragment())
                    .commit();
        }
        else if(resultArray[0].equals("False")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("DigiBank Alert");
            builder.setMessage("Oops seems like an error occurred. Do you want to retry your action?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }

            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    homeScreenActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, new HomeFragment())
                            .commit();
                }
            });

            AlertDialog quitDialog = builder.create();
            quitDialog.show();
        }
    }
}
