package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Registration.RegistrationFragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class UpdateStudentListAsync extends AsyncTask<String, String, String>{

    private Context context;
    private ProgressDialog progDialog;
    private String bankerID;
    private String username;
    private String classID;
    private String className;
    private String flag;

    public UpdateStudentListAsync(Context context, String bankerID, String username, String classID,
                                  String className) {

        this.context = context;
        this.bankerID = bankerID;
        this.username = username;
        this.classID = classID;
        this.className = className;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Adding new student to class...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        try {

            String link = "https://www.kidzsmartapp.com/databaseAccess/addStudent.php";

            String data = URLEncoder.encode("bankerid", "UTF-8") + "=" +
                          URLEncoder.encode(bankerID, "UTF-8");
            data += "&" + URLEncoder.encode("accountholderusername", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("classid", "UTF-8") + "=" +
                    URLEncoder.encode(classID, "UTF-8");
            data += "&" + URLEncoder.encode("classname", "UTF-8") + "=" +
                    URLEncoder.encode(className, "UTF-8");

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
        }
        catch (Exception e) {

        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        progDialog.dismiss();
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        RegistrationFragment fragment = (RegistrationFragment) ((HomeScreenBanker)context).getSupportFragmentManager()
                    .findFragmentById(R.id.frame_layout);

        fragment.updateResult(result);
    }
}
