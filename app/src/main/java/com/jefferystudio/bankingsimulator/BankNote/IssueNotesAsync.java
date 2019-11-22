package com.jefferystudio.bankingsimulator.BankNote;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class IssueNotesAsync extends AsyncTask <String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String bankerID;
    private String accountholderCreds;
    private String totalBalance;
    private String bankerUsername;

    public IssueNotesAsync(Context context, String bankerID, String accountholderCreds, String totalBalance) {

        this.context = context;
        this.bankerID = bankerID;
        this.accountholderCreds = accountholderCreds;
        this.totalBalance = totalBalance;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Issuing notes...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        bankerUsername = args[0];

        try {

            String link = "https://www.kidzsmartapp.com/databaseAccess/issueNotes.php";
            String data = URLEncoder.encode("bankerid", "UTF-8") + "=" +
                          URLEncoder.encode(bankerID, "UTF-8");
            data += "&" + URLEncoder.encode("accountholdercreds", "UTF-8") + "=" +
                    URLEncoder.encode(accountholderCreds, "UTF-8");
            data += "&" + URLEncoder.encode("totalbalance", "UTF-8") + "=" +
                    URLEncoder.encode(totalBalance, "UTF-8");

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

        progDialog.dismiss();

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("Success")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("DigiBank Alert");
            builder.setMessage("Notes are issued successfully.\n" +
                    "Do you want to issue another set?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Bundle newArgs = new Bundle();
                    newArgs.putString("userID", bankerID);
                    newArgs.putString("userName", bankerUsername);
                    newArgs.putString("accountType", "Banker");

                    Fragment issueNotesFrag = new IssueBanknoteFragment();
                    issueNotesFrag.setArguments(newArgs);
                    ((HomeScreenBanker)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, issueNotesFrag)
                            .commit();

                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int i) {

                    Bundle newArgs = new Bundle();
                    newArgs.putString("userID", bankerID);
                    newArgs.putString("userName", bankerUsername);
                    newArgs.putString("accountType", "Banker");

                    Fragment homeFrag = new HomeFragmentBanker();
                    homeFrag.setArguments(newArgs);
                    ((HomeScreenBanker)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, homeFrag)
                            .commit();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
        else if(resultArray[0].equals("Fail")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("DigiBank Alert");
            builder.setMessage(resultArray[1]);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
