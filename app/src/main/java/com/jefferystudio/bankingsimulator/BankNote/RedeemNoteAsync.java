package com.jefferystudio.bankingsimulator.BankNote;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RedeemNoteAsync extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String issueCode;
    private String bankerID;
    private String accountholderID;

    public RedeemNoteAsync(Context context, String issueCode, String bankerID, String accountholderID) {

        this.context = context;
        this.issueCode = issueCode;
        this.bankerID = bankerID;
        this.accountholderID = accountholderID;
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
    protected String doInBackground(String... strings) {

        StringBuffer sb = new StringBuffer("");

        try {

            String link = "https://www.kidzsmartapp.com/databaseAccess/issueNotes.php";
            String data = URLEncoder.encode("issuecode", "UTF-8") + "=" +
                    URLEncoder.encode(issueCode, "UTF-8");
            data += "&" + URLEncoder.encode("bankerid", "UTF-8") + "=" +
                    URLEncoder.encode(bankerID, "UTF-8");
            data += "&" + URLEncoder.encode("accountholderid", "UTF-8") + "=" +
                    URLEncoder.encode(accountholderID, "UTF-8");


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
    }
}
