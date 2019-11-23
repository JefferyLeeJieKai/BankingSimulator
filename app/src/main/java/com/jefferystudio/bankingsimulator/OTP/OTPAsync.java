package com.jefferystudio.bankingsimulator.OTP;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class OTPAsync extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String flag;
    private String userID;

    public OTPAsync(Context context, String flag) {

        this.context = context;
        this.flag = flag;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Sending OTP...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        userID = args[0];

        try{
            String link="https://www.kidzsmartapp.com/databaseAccess/emailOTP.php";
            String data  = URLEncoder.encode("userid", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");

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
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        progDialog.dismiss();
        Fragment fragment = null;

        if(flag.equals("AccountHolder")) {

            fragment = ((HomeScreenUser)context).getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        }
        else if(flag.equals("Banker")) {

            fragment = ((HomeScreenBanker)context).getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        }
        else if(flag.equals("OTP")) {

            fragment = ((OTPActivity)context).getSupportFragmentManager().findFragmentById(R.id.otpFrame);
        }

        ((OTPFragment)fragment).getActualVerifcationCode(result);
    }
}
