package com.jefferystudio.bankingsimulator.WithdrawalPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.TransferFundsPackage.TransferAmountFragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class CheckUserExistsAsync extends AsyncTask<String, String, String> {

    private Context context;
    private TextInputLayout payeeInput;
    private ProgressDialog progDialog;

    public CheckUserExistsAsync(Context context, TextInputLayout payeeInput) {

        this.context = context;
        this.payeeInput = payeeInput;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Checking if payee exists...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        String payeeID = args[0];

        try{
            String link="https://www.kidzsmartapp.com/databaseAccess/checkUserExists.php";
            String data  = URLEncoder.encode("payeeid", "UTF-8") + "=" +
                    URLEncoder.encode(payeeID, "UTF-8");

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

            //elist.add(e);
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        progDialog.dismiss();
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        String[] resultArray = result.split(",");

        if(resultArray[0].equals("False")) {

            payeeInput.setError("Payee does not exists");

        }
        else if(resultArray[0].equals("True")){

            TransferAmountFragment baseFrag = (TransferAmountFragment)((HomeScreenUser)context).getSupportFragmentManager()
                    .findFragmentById(R.id.frame_layout);

            baseFrag.performCheck();
        }
    }
}
