package com.jefferystudio.bankingsimulator.CommonAsyncPackage;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.jefferystudio.bankingsimulator.DepositPackage.DepositConfirmBanker;
import com.jefferystudio.bankingsimulator.DepositPackage.DepositConfirmUser;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.WithdrawalPackage.WithdrawalConfirm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class TransactionAsync extends AsyncTask <String, String, String> {

    private Context context;
    private String flag;
    private HomeScreenUser homeScreenUserActivity;
    private HomeScreenBanker homeScreenBankerActivity;
    private ArrayList<Exception> elist = new ArrayList<Exception>();
    private String userID;
    private String userName;
    private String bankerID;
    private String payeeID;
    private String depositAmount;
    private String withdrawAmount;
    private String data;

    public TransactionAsync(Context context, String flag, String userName) {

        this.context = context;
        this.flag = flag;
        this.userName = userName;

        if (flag.equals("DepositUser") || flag.equals("WithdrawalUser") || flag.equals("TransferFundsUser")) {

            homeScreenUserActivity = (HomeScreenUser) context;
        }
        else if(flag.equals("DepositBanker")) {

            homeScreenBankerActivity = (HomeScreenBanker) context;
        }
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        if(flag.equals("DepositUser") || flag.equals("DepositBanker")) {

            userID = args[0];
            depositAmount = args[1];

            if(flag.equals("DepositBanker")) {

                bankerID = args[2];
            }

            try{
                String link="http://www.kidzsmart.tk/databaseAccess/depositUser.php";
                data  = URLEncoder.encode("userid", "UTF-8") + "=" +
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

                elist.add(e);
            }
        }
        else if(flag.equals("WithdrawalUser")) {

            userID = args[0];
            withdrawAmount = args[1];

            try{
                String link="http://www.kidzsmart.tk/databaseAccess/withdrawalUser.php";
                data  = URLEncoder.encode("userid", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("mywithdrawal", "UTF-8") + "=" +
                        URLEncoder.encode(withdrawAmount, "UTF-8");

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
        }
        else if(flag.equals("TransferFundsUser")) {

            userID = args[0];
            payeeID = args[1];
            withdrawAmount = args[2];

            try {
                String link = "http://www.kidzsmart.tk/databaseAccess/transferfundsUser.php";
                data = URLEncoder.encode("payeruserid", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("payee", "UTF-8") + "=" +
                        URLEncoder.encode(payeeID, "UTF-8");
                data += "&" + URLEncoder.encode("transfer", "UTF-8") + "=" +
                        URLEncoder.encode(withdrawAmount, "UTF-8");

                URL url = new URL(link);
                URLConnection connection = url.openConnection();

                connection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
            } catch (Exception e) {

                elist.add(e);
            }
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        /*for(Exception e : elist) {

            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
        String test = "UserID: " + userID;

        Toast.makeText(context, test , Toast.LENGTH_LONG).show();
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e) {

        }*/
        //Toast.makeText(context, data, Toast.LENGTH_LONG).show();

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("True") && (flag.equals("DepositUser") || flag.equals("WithdrawalUser") || flag.equals("TransferFundsUser"))) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("DigiBank Alert");
            builder.setMessage(resultArray[1] + " \n\nDo you want to perform another transaction?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment currentFragment = homeScreenUserActivity.getSupportFragmentManager().findFragmentById(R.id.frame_layout);

                    if(flag.equals("DepositUser")) {

                        ((DepositConfirmUser)currentFragment).recall();
                    }
                    else if(flag.equals("WithdrawalUser")) {

                        ((WithdrawalConfirm)currentFragment).recall();
                    }
                    else if(flag.equals("TransferFundsUser")) {


                    }
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
        else if(resultArray[0].equals("True") && flag.equals("DepositBanker")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("DigiBank Alert");
            builder.setMessage(resultArray[1] + " \n\nDo you want to perform another transaction?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment currentFragment = homeScreenBankerActivity.getSupportFragmentManager().findFragmentById(R.id.frame_layout);

                    ((DepositConfirmBanker)currentFragment).recall();
                }

            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment homeFrag = new HomeFragmentBanker();
                    Bundle args = new Bundle();
                    args.putString("userID", bankerID);
                    args.putString("userName", userName);
                    homeFrag.setArguments(args);

                    homeScreenBankerActivity.getSupportFragmentManager().beginTransaction()
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
            builder.setMessage(resultArray[1] + " \nDo you want to retry your action?");
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
    }
}
