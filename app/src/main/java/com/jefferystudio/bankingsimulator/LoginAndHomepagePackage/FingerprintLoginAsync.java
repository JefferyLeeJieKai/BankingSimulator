package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.CommonAsyncPackage.RetrieveProfilePicAsync;
import com.jefferystudio.bankingsimulator.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FingerprintLoginAsync extends AsyncTask<Bundle, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String flag;
    private String userID;
    private Bundle args;
    private ArrayList<String> errorList = new ArrayList<String>();
    private TextView progressBar;

    public FingerprintLoginAsync(Context context, String flag, String userID) {

        this.context = context;
        this.flag = flag;
        this.userID = userID;
    }

    @Override
    protected void onPreExecute() {

        ((LoginScreen)context).setContentView(R.layout.loadingicon);
        progressBar = ((LoginScreen)context).findViewById(R.id.text_view);
    }

    @Override
    protected String doInBackground(Bundle[] bundle) {

        args = bundle[0];

        StringBuffer sb = new StringBuffer("");

        try {

            String link = "https://www.kidzsmartapp.com/databaseAccess/handleFingerprint.php";
            String data = URLEncoder.encode("flag", "UTF-8") + "=" +
                    URLEncoder.encode(flag, "UTF-8");
            data += "&" + URLEncoder.encode("userid", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");

            publishProgress("20");

            URL url = new URL(link);
            URLConnection connection = url.openConnection();

            publishProgress("30");

            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            publishProgress("40");

            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            publishProgress("50");

        }
        catch (Exception e) {

        }

        String[] resultArray = sb.toString().split(",");

        if (resultArray[0].equals("True")) {

            publishProgress("60");

            Intent intent = null;

            if (resultArray[1].equals("AccountHolder")) {

                intent = new Intent(context, HomeScreenUser.class);

            } else if (resultArray[1].equals("Banker")) {

                intent = new Intent(context.getApplicationContext(), HomeScreenBanker.class);

            }

            if(!resultArray[2].equals("NoImage")) {


                ContextWrapper cw = new ContextWrapper(context);
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File file = new File(directory, "ProfilePicture.jpg");

                String catchString = "";

                try {

                    catchString = new RetrieveProfilePicAsync(context, file, errorList)
                            .execute(resultArray[2])
                            .get(5000, TimeUnit.MILLISECONDS);

                    publishProgress("90");

                } catch (Exception e) {


                }

                publishProgress("100");

                intent.putExtras(args);

                progDialog.dismiss();

                context.startActivity(intent);

                ((Activity) context).finish();
            }
            else {

                intent.putExtras(args);

                context.startActivity(intent);

                Activity login = (Activity) context;
                login.finish();
            }
        }
        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("DigiBank Alert");
            builder.setMessage("Please enable fingerprint login in your settings.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });

            AlertDialog noFingerprintDialog = builder.create();
            noFingerprintDialog.show();
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

    protected void onProgressUpdate(String[] args) {

        int progress = Integer.valueOf(args[0]);
        progressBar.setText(progress + "%");
    }
}
