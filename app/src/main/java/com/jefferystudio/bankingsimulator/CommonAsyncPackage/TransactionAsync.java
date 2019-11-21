package com.jefferystudio.bankingsimulator.CommonAsyncPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.jefferystudio.bankingsimulator.DepositPackage.DepositAHFragment;
import com.jefferystudio.bankingsimulator.DepositPackage.DepositConfirmBankerFragment;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsAllFragment;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.UpdateSavingGoalsAsync;
import com.jefferystudio.bankingsimulator.TransferFundsPackage.TransferAmountFragment;
import com.jefferystudio.bankingsimulator.WithdrawalPackage.WithdrawalAHFragment;
import com.jefferystudio.bankingsimulator.WithdrawalPackage.WithdrawalConfirmFragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TransactionAsync extends AsyncTask <String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
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
    private String amountToUpdate;
    private String goalID;

    public TransactionAsync(Context context, String flag, String userName) {

        this.context = context;
        this.flag = flag;
        this.userName = userName;

        if (flag.equals("DepositUser") || flag.equals("WithdrawalUser") || flag.equals("TransferFundsUser")) {

            homeScreenUserActivity = (HomeScreenUser) context;
        }
        else if(flag.equals("DepositBankerFragment")) {

            homeScreenBankerActivity = (HomeScreenBanker) context;
        }
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Performing transaction...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        if(flag.equals("DepositUser") || flag.equals("DepositBankerFragment") || flag.equals("DepositUserRedeem")) {

            userID = args[0];
            depositAmount = args[1];

            if(flag.equals("DepositBankerFragment")) {

                bankerID = args[2];
            }

            try{
                String link="https://www.kidzsmartapp.com/databaseAccess/depositUser.php";
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
        else if(flag.equals("WithdrawalUser") || flag.equals("WithdrawalUserNoShow")) {

            userID = args[0];
            withdrawAmount = args[1];

            if(flag.equals("WithdrawalUserNoShow")) {

                amountToUpdate = args[2];
                goalID = args[3];
            }

            try{
                String link="https://www.kidzsmartapp.com/databaseAccess/withdrawalUser.php";
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
                String link = "https://www.kidzsmartapp.com/databaseAccess/transferfundsUser.php";
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

        progDialog.dismiss();
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

            //call UpdateTransAsync()
            if(flag.equals("WithdrawalUser")) {

                new UpdateTransAsync(context, "WithdrawFunds").execute(userID, withdrawAmount);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("DigiBank Alert");
            builder.setMessage(resultArray[1] + " \n\nDo you want to perform another transaction?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment currentFragment = homeScreenUserActivity.getSupportFragmentManager().findFragmentById(R.id.frame_layout);

                    Bundle newArgs = new Bundle();
                    newArgs.putString("userID", userID);
                    newArgs.putString("userName", userName);

                    if(flag.equals("DepositUser")) {

                        Fragment depositFrag = new DepositAHFragment();
                        depositFrag.setArguments(newArgs);

                        homeScreenUserActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, depositFrag)
                                .commit();
                    }
                    else if(flag.equals("WithdrawalUser")) {

                        Fragment withdrawalFrag = new WithdrawalAHFragment();
                        withdrawalFrag.setArguments(newArgs);

                        homeScreenUserActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, withdrawalFrag)
                                .commit();
                    }
                    else if(flag.equals("TransferFundsUser")) {

                        Fragment transferFrag = new TransferAmountFragment();
                        transferFrag.setArguments(newArgs);

                        homeScreenUserActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, transferFrag)
                                .commit();
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
        else if(resultArray[0].equals("True") && flag.equals("DepositBankerFragment")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("DigiBank Alert");
            builder.setMessage(resultArray[1] + " \n\nDo you want to perform another transaction?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment currentFragment = homeScreenBankerActivity.getSupportFragmentManager().findFragmentById(R.id.frame_layout);

                    ((DepositConfirmBankerFragment)currentFragment).recall();
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
        if(resultArray[0].equals("True") && flag.equals("WithdrawalUserNoShow")) {

            new UpdateSavingGoalsAsync(context, "SaveMoney", userName)
                        .execute(userID, goalID, amountToUpdate);


        }
        else if(resultArray[0].equals("False")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("DigiBank Alert");
            builder.setMessage(resultArray[1] + " \nDo you want to retry your action?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                //if retry action
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment currentFragment = homeScreenUserActivity.getSupportFragmentManager().findFragmentById(R.id.frame_layout);

                    //link back to the first withdrawal page
                    if(flag.equals("WithdrawalUser")) {

                        ((WithdrawalConfirmFragment)currentFragment).recall();
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
    }
}
