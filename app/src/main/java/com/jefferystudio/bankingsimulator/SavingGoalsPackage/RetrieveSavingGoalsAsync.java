package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.SavingGoalsRecyclerView.RecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.SavingGoalsRecyclerView.SavingGoal;

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
    private String userID;
    private String username;
    private String currentBalance;

    public RetrieveSavingGoalsAsync(Context context, RecyclerView recyclerView) {

        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String[] args) {

        userID = args[0];
        username = args[1];
        currentBalance = args[2];

        StringBuffer sb = new StringBuffer("");

        try {

            String link = "http://www.kidzsmart.tk/kidzsmartApp/databaseAccess/getSavingGoals.php";
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

        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        ArrayList<SavingGoal> savingGoals = new ArrayList<>();

        String[] resultArray = result.split(",");
        int numberOfResults = resultArray.length / 4;

        for(int i = 0; i < numberOfResults; i++) {

            SavingGoal goal = new SavingGoal(userID, username, currentBalance, resultArray[(i * 4)],
                                             resultArray[(i * 4) + 1], resultArray[(i * 4) + 2],
                                             resultArray[(i * 4) + 3]);

            savingGoals.add(goal);
        }

        RecyclerViewAdaptor adaptor = new RecyclerViewAdaptor(context, savingGoals);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
}
