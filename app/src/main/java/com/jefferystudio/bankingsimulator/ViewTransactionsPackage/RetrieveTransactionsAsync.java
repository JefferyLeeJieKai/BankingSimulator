package com.jefferystudio.bankingsimulator.ViewTransactionsPackage;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView.SavingGoalsRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView.SavingGoal;
import com.jefferystudio.bankingsimulator.ViewTransactionsPackage.ViewTransactionsRecyclerView.Transaction;
import com.jefferystudio.bankingsimulator.ViewTransactionsPackage.ViewTransactionsRecyclerView.TransactionsRecyclerViewAdaptor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RetrieveTransactionsAsync extends AsyncTask<String, String, String> {

    private Context context;
    private RecyclerView recyclerView;
    private String userID;
    private String username;
    private String currentBalance;

    public RetrieveTransactionsAsync(Context context, RecyclerView recyclerView) {

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

            String link = "http://www.kidzsmart.tk/kidzsmartApp/databaseAccess/getTransactions.php";
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
        ArrayList<Transaction> transactions = new ArrayList<>();

        String[] resultArray = result.split(",");
        int numberOfResults = resultArray.length / 7;

        for (int i = 0; i < numberOfResults; i++) {

            Transaction transaction = new Transaction(userID, username, currentBalance, resultArray[(i * 7)],
                    resultArray[(i * 7) + 1], resultArray[(i * 7) + 2],
                    resultArray[(i * 7) + 3], resultArray[(i * 7) + 4],
                    resultArray[(i * 7) + 5], resultArray[(i * 7) + 6]);

            transactions.add(transaction);
        }

        TransactionsRecyclerViewAdaptor adaptor = new TransactionsRecyclerViewAdaptor(context, transactions);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
}
