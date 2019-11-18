package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView.SavingGoalsRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView.SavingGoal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RetrieveSavingGoalsAsync extends AsyncTask<String, String, String> {

    private Context context;
    private RecyclerView recyclerView;
    private ProgressDialog progDialog;
    private String userID;
    private String username;
    private String currentBalance;

    public RetrieveSavingGoalsAsync(Context context, RecyclerView recyclerView) {

        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Retrieving saving goals...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        userID = args[0];
        username = args[1];
        currentBalance = args[2];

        StringBuffer sb = new StringBuffer("");

        try {

            String link = "https://www.kidzsmartapp.com/databaseAccess/getSavingGoals.php";
            String data = URLEncoder.encode("userid", "UTF-8") + "=" +
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
            while ((line = reader.readLine()) != null) {
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
        ArrayList<SavingGoal> savingGoals = new ArrayList<>();

        String[] resultArray = result.split(",");
        int numberOfResults = resultArray.length / 6;

        for(int i = 0; i < numberOfResults; i++) {

            SavingGoal goal = new SavingGoal(userID, username, currentBalance, resultArray[(i * 6)],
                                             resultArray[(i * 6) + 1], resultArray[(i * 6) + 2],
                                             resultArray[(i * 6) + 3], resultArray[(i * 6) + 4],
                                             resultArray[(i * 6) + 5]);

            savingGoals.add(goal);
        }

        SavingGoalsRecyclerViewAdaptor adaptor = new SavingGoalsRecyclerViewAdaptor(context, savingGoals);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
}
