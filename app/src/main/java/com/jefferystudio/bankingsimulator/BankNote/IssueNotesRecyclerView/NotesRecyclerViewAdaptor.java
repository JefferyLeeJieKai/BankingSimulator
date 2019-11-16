import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.BankNote.IssueNotesRecyclerView.Notes;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.DeleteSavingGoalsAsync;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsAll;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NotesRecyclerViewAdaptor extends RecyclerView.Adapter<NotesRecyclerViewAdaptor.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView issueID;
        public TextView ahName;
        public TextView dateIssued;
        public TextView totalBalance;
        public Button searchButton;
        public Button deleteButton;

        public ViewHolder(View itemView) {

            super(itemView);

            issueID = itemView.findViewById(R.id.issueID);
            ahName = itemView.findViewById(R.id.ahUsername);
            dateIssued = itemView.findViewById(R.id.dateIssued);
            totalBalance = itemView.findViewById(R.id.totalbalance);
            searchButton = itemView.findViewById(R.id.viewSavingGoal);
            deleteButton = itemView.findViewById(R.id.deleteSavingGoal);
        }
    }

    private List<Notes> issuedList;
    private Context context;

    public NotesRecyclerViewAdaptor(Context context, List<Notes> issuedList) {

        this.issuedList = issuedList;
        this.context = context;
    }

    @Override
    public NotesRecyclerViewAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.notes_recycler_row_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(NotesRecyclerViewAdaptor.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Notes notesSet = issuedList.get(position);
        final int entryPosition = position;

        // Set item views based on your views and data model
        TextView textView1 = viewHolder.issueID;
        textView1.setText(notesSet.getIssueID());

        TextView textView2 = viewHolder.ahName;
        textView2.setText(notesSet.getAccountholderUsername());

        TextView textView3 = viewHolder.dateIssued;
        textView3.setText(notesSet.getIssuedDate());

        TextView textView4 = viewHolder.totalBalance;
        textView4.setText(notesSet.getTotalBalance());

        Button button = viewHolder.searchButton;
        button.setText("Edit");

        Button searchBtn = viewHolder.searchButton;

        /*searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();


                //Fragment viewGoalFrag = new SavingGoalsView();
                //viewGoalFrag.setArguments(args);

                ((HomeScreenUser)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, viewGoalFrag)
                        .commit();
            }
        });*/

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

                        String result = "";

                        try{
                            result = new DeleteSavingGoalsAsync(context, notesSet.getIssueID())
                                    .execute()
                                    .get(5000, TimeUnit.MILLISECONDS);
                        }
                        catch(Exception e) {

                        }

                        String[] resultArray = result.split(",");

                        if(resultArray[0].equals("Success")) {

                            issuedList.remove(entryPosition);

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("DigiBank Alert");
                            builder.setMessage("Issued banknotes successfully deleted.");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    SavingGoalsAll currentFrag = (SavingGoalsAll) ((HomeScreenUser) context).getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                                    currentFrag.updateAdaptor(entryPosition);
                                }
                            });

                            AlertDialog confirmDialog = builder.create();
                            confirmDialog.show();
                        }
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
}
