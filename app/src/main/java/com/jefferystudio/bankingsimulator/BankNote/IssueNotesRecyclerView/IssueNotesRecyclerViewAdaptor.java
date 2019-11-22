package com.jefferystudio.bankingsimulator.BankNote.IssueNotesRecyclerView;

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

import com.jefferystudio.bankingsimulator.BankNote.DeleteNotesAsync;
import com.jefferystudio.bankingsimulator.BankNote.IssueBanknoteFragment;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class IssueNotesRecyclerViewAdaptor extends RecyclerView.Adapter<IssueNotesRecyclerViewAdaptor.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView issueID;
        public TextView ahName;
        public TextView dateIssued;
        public TextView totalBalance;
        public Button searchButton;
        public Button deleteButton;

        public ViewHolder(View itemView) {

            super(itemView);

            ahName = itemView.findViewById(R.id.ahUsername);
            dateIssued = itemView.findViewById(R.id.dateIssued);
            totalBalance = itemView.findViewById(R.id.totalbalance);
            searchButton = itemView.findViewById(R.id.viewNotes);
            deleteButton = itemView.findViewById(R.id.deleteNotes);
        }
    }

    private List<Notes> issuedList;
    private Context context;
    private int deletePosition;

    public IssueNotesRecyclerViewAdaptor(Context context, List<Notes> issuedList) {

        this.issuedList = issuedList;
        this.context = context;
    }

    @Override
    public IssueNotesRecyclerViewAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.issue_notes_recycler_row_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(IssueNotesRecyclerViewAdaptor.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Notes notesSet = issuedList.get(position);
        final int entryPosition = position;

        TextView textView1 = viewHolder.ahName;
        textView1.setText(notesSet.getAccountholderUsername());

        TextView textView2 = viewHolder.dateIssued;
        textView2.setText(notesSet.getIssuedDate());

        TextView textView3 = viewHolder.totalBalance;
        textView3.setText(notesSet.getTotalBalance());

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

        //delete button
        Button deleteBtn = viewHolder.deleteButton;

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = "Are you sure you want to delete Issued id: " + notesSet.getIssueID() + "?";

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Warning!");
                builder.setMessage(msg);

                //yes button selected
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new DeleteNotesAsync(context, notesSet.getIssueID()).execute();
                        deletePosition = entryPosition;
                    }
                });

                //no button selected
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {

        return issuedList.size();
    }

    public void updateResult(String result) {

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("Success")) {

            issuedList.remove(deletePosition);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("DigiBank Alert");
            builder.setMessage("Issued banknotes successfully deleted.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    IssueBanknoteFragment currentFrag = (IssueBanknoteFragment) ((HomeScreenBanker) context).getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                    currentFrag.updateAdaptor(deletePosition);
                }
            });

            AlertDialog confirmDialog = builder.create();
            confirmDialog.show();
        }
    }
}
