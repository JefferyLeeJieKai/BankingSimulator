package com.jefferystudio.bankingsimulator.BankNote.RedeemNotesRecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.BankNote.IssueBanknoteFragment;
import com.jefferystudio.bankingsimulator.BankNote.IssueNotesRecyclerView.Notes;
import com.jefferystudio.bankingsimulator.BankNote.RedeemBanknoteFragment;
import com.jefferystudio.bankingsimulator.BankNote.RedeemNoteAsync;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.TransactionAsync;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateTransAsync;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedeemNotesRecyclerViewAdaptor extends RecyclerView.Adapter<RedeemNotesRecyclerViewAdaptor.ViewHolder> {

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView dateIssued;
    public TextView totalBalance;
    public Button searchButton;
    public Button redeemButton;

    public ViewHolder(View itemView) {

        super(itemView);

        dateIssued = itemView.findViewById(R.id.dateIssued);
        totalBalance = itemView.findViewById(R.id.totalbalance);
        searchButton = itemView.findViewById(R.id.viewNotes);
        redeemButton = itemView.findViewById(R.id.redeemNotes);
    }
}

    private List<Notes> issuedList;
    private Context context;
    private Notes redeemedNote;
    private int deletePosition;

    public RedeemNotesRecyclerViewAdaptor(Context context, List<Notes> issuedList) {

        this.issuedList = issuedList;
        this.context = context;
    }

    @Override
    public RedeemNotesRecyclerViewAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.redeem_notes_recycler_row_layout, parent, false);

        // Return a new holder instance
        RedeemNotesRecyclerViewAdaptor.ViewHolder viewHolder = new RedeemNotesRecyclerViewAdaptor.ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RedeemNotesRecyclerViewAdaptor.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Notes notesSet = issuedList.get(position);
        final int entryPosition = position;

        TextView textView1 = viewHolder.dateIssued;
        textView1.setText(notesSet.getIssuedDate());

        TextView textView2 = viewHolder.totalBalance;
        textView2.setText(notesSet.getTotalBalance());

        Button button = viewHolder.searchButton;
        button.setText("Edit");

        Button searchBtn = viewHolder.searchButton;
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();


                Fragment viewNotesFrag = null;
                //viewGoalFrag.setArguments(args);

                ((HomeScreenUser)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, viewNotesFrag)
                        .commit();
            }
        });

        Button redeemButton = viewHolder.redeemButton;
        redeemButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                redeemedNote = notesSet;
                deletePosition = entryPosition;

                new RedeemNoteAsync(context, notesSet.getIssueID()).execute();
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {

        return issuedList.size();
    }

    public void updateRedeemedAmount(String result) {

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("Success")){

            new TransactionAsync(context,"DepositUserRedeem",redeemedNote.getAccountholderUsername())
                .execute(redeemedNote.getAccountholderID(),redeemedNote.getTotalBalance());
        }
    }

    public void updateTransactions(String result) {

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("True")) {

            new UpdateTransAsync(context, "DepositFundsRedeem")
                    .execute(redeemedNote.getAccountholderID(), redeemedNote.getTotalBalance());
        }
    }

    public void updateResult(String result) {

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("1")) {

            issuedList.remove(deletePosition);

            final RedeemBanknoteFragment currentFrag = (RedeemBanknoteFragment) ((HomeScreenUser) context).getSupportFragmentManager().findFragmentById(R.id.frame_layout);
            currentFrag.updateBalance();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("DigiBank Alert");
            builder.setMessage("Issued banknotes successfully redeemed.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    currentFrag.updateAdaptor(deletePosition);
                }
            });

            AlertDialog confirmDialog = builder.create();
            confirmDialog.show();
        }
    }
}
