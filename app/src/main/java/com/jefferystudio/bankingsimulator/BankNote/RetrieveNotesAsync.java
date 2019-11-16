package com.jefferystudio.bankingsimulator.BankNote;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jefferystudio.bankingsimulator.BankNote.IssueNotesRecyclerView.IssueNotesRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.BankNote.IssueNotesRecyclerView.Notes;
import com.jefferystudio.bankingsimulator.BankNote.RedeemNotesRecyclerView.RedeemNotesRecyclerViewAdaptor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RetrieveNotesAsync extends AsyncTask <String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String userID;
    private String flag;
    private RecyclerView issuedNotesView;

    public RetrieveNotesAsync(Context context, String userID, String flag, RecyclerView issuedNotesView) {

        this.context = context;
        this.userID = userID;
        this.flag = flag;
        this.issuedNotesView = issuedNotesView;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Retrieving details...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        StringBuffer sb = new StringBuffer("");

        try {

            String link = "https://www.kidzsmartapp.com/databaseAccess/viewNotes.php";
            String data = URLEncoder.encode("userid", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");
            data += "&" + URLEncoder.encode("flag", "UTF-8") + "=" +
                    URLEncoder.encode(flag, "UTF-8");

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

        ArrayList<Notes> issuedList = new ArrayList<>();

        String[] resultArray = result.split(",");
        int numberOfResults = resultArray.length / 6;

        for(int i = 0; i < numberOfResults; i++) {

            Notes note = new Notes(resultArray[(i * 6)], resultArray[(i * 6) + 1],
                                   resultArray[(i * 6) + 2], resultArray[(i * 6) + 3],
                                   resultArray[(i * 6) + 4], resultArray[(i * 6) + 5]);

            issuedList.add(note);
        }

        if(flag.equals("viewBanker")) {

            IssueNotesRecyclerViewAdaptor adaptor = new IssueNotesRecyclerViewAdaptor(context, issuedList);
            issuedNotesView.setAdapter(adaptor);
            issuedNotesView.setLayoutManager(new LinearLayoutManager(context));
        }
        else if(flag.equals("viewAccountHolder")) {

            RedeemNotesRecyclerViewAdaptor adaptor = new RedeemNotesRecyclerViewAdaptor(context, issuedList);
            issuedNotesView.setAdapter(adaptor);
            issuedNotesView.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
