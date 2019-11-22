package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView.SavingGoalsRecyclerViewAdaptor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class DeleteSavingGoalsAsync extends AsyncTask<String, String, String> {

    private Context context;
    private String goalID;
    private ProgressDialog progDialog;

    public DeleteSavingGoalsAsync(Context context, String goalID) {

        this.context = context;
        this.goalID = goalID;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Deleting saving goal...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        try {

            String link = "https://www.kidzsmartapp.com/databaseAccess/deleteGoal.php";
            String data = URLEncoder.encode("goalid", "UTF-8") + "=" +
                    URLEncoder.encode(goalID, "UTF-8");


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
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        SavingGoalsAllFragment fragment = (SavingGoalsAllFragment)((HomeScreenUser)context).getSupportFragmentManager()
                                          .findFragmentById(R.id.frame_layout);

        RecyclerView recyclerView = fragment.getView().findViewById(R.id.goalDetailsRv);
        SavingGoalsRecyclerViewAdaptor adapter = (SavingGoalsRecyclerViewAdaptor)recyclerView.getAdapter();
        adapter.updateDelete(result);
    }
}
