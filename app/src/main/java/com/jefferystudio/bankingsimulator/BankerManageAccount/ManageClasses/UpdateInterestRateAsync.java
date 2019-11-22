package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class UpdateInterestRateAsync extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String studentID;
    private String interestRate;

    public UpdateInterestRateAsync(Context context, String studentID, String interestRate) {

        this.context = context;
        this.studentID = studentID;
        this.interestRate = interestRate;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Updating interest rate...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        try {

            String link = "https://www.kidzsmartapp.com/databaseAccess/editInterestRate.php";
            String data = URLEncoder.encode("studentid", "UTF-8") + "=" +
                    URLEncoder.encode(studentID, "UTF-8");
            data += "&" + URLEncoder.encode("interestrate", "UTF-8") + "=" +
                    URLEncoder.encode(interestRate, "UTF-8");

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


        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        progDialog.dismiss();

        ViewStudentFragment fragment = (ViewStudentFragment)((HomeScreenBanker)context).getSupportFragmentManager()
                                        .findFragmentById(R.id.frame_layout);

        fragment.updateInterestRateResult(result);
    }
}
