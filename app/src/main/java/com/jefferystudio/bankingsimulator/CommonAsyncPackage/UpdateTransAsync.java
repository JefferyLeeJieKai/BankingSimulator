package com.jefferystudio.bankingsimulator.CommonAsyncPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.jefferystudio.bankingsimulator.BankNote.RedeemBanknoteFragment;
import com.jefferystudio.bankingsimulator.BankNote.RedeemNotesRecyclerView.RedeemNotesRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class UpdateTransAsync extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String flag;
    private String link;
    private String data;
    private String userID;
    private String transacAmt;
    private String payeeID;
    private String purpose;
    private ArrayList<Exception> elist = new ArrayList<Exception>();

    public UpdateTransAsync(Context context, String flag) {

        this.context = context;
        this.flag = flag;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Updating transaction to server...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        if(flag.equals("TransferFunds") || flag.equals("BankerDeposit")) {

            userID = args[0];
            transacAmt = args[1];
            payeeID = args[2];

            link = "https://www.kidzsmartapp.com/databaseAccess/transactionsUpdate.php";

            try {
                data = URLEncoder.encode("flag", "UTF-8") + "=" +
                        URLEncoder.encode(flag, "UTF-8");
                data += "&" + URLEncoder.encode("userid", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("transamt", "UTF-8") + "=" +
                        URLEncoder.encode(transacAmt, "UTF-8");
                data += "&" + URLEncoder.encode("payeeid", "UTF-8") + "=" +
                        URLEncoder.encode(payeeID, "UTF-8");
            }
            catch(Exception e) {

                elist.add(e);
            }
        }
        else {

            userID = args[0];
            transacAmt = args[1];

            link = "https://www.kidzsmartapp.com/databaseAccess/transactionsUpdate.php";

            try {
                data = URLEncoder.encode("flag", "UTF-8") + "=" +
                        URLEncoder.encode(flag, "UTF-8");
                data += "&" + URLEncoder.encode("userid", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("transamt", "UTF-8") + "=" +
                        URLEncoder.encode(transacAmt, "UTF-8");
            }
            catch(Exception e ) {

                elist.add(e);
            }
        }

        try {
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
        catch(Exception e ) {

            elist.add(e);
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        progDialog.dismiss();
        /*for(Exception e : elist) {

            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }*/

        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        if(flag.equals("DepositFundsRedeem")) {

            RedeemBanknoteFragment fragment = (RedeemBanknoteFragment)((HomeScreenUser)context).getSupportFragmentManager()
                    .findFragmentById(R.id.frame_layout);

            RecyclerView recyclerView = fragment.getView().findViewById(R.id.detailsRv);
            RedeemNotesRecyclerViewAdaptor adapter = (RedeemNotesRecyclerViewAdaptor)recyclerView.getAdapter();
            adapter.updateResult(result);
        }
    }
}
