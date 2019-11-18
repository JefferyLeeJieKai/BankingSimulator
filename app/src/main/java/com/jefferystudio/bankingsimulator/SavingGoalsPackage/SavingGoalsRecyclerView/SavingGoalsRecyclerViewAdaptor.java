package com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView;

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

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.DeleteSavingGoalsAsync;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsAll;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsEdit;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SavingGoalsRecyclerViewAdaptor extends RecyclerView.Adapter<SavingGoalsRecyclerViewAdaptor.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView savingGoal;
        public TextView daysLeft;
        public Button searchButton;
        public Button editButton;
        public Button deleteButton;

        public ViewHolder(View itemView) {

            super(itemView);

            savingGoal = itemView.findViewById(R.id.savingGoalItem);
            daysLeft = itemView.findViewById(R.id.daysLeft);
            searchButton = itemView.findViewById(R.id.viewSavingGoal);
            editButton = itemView.findViewById(R.id.editSavingGoal);
            deleteButton = itemView.findViewById(R.id.deleteSavingGoal);
        }
    }

    private List<SavingGoal> savingGoals;
    private Context context;

    public SavingGoalsRecyclerViewAdaptor(Context context, List<SavingGoal> savingGoals) {

        this.savingGoals = savingGoals;
        this.context = context;
    }

    @Override
    public SavingGoalsRecyclerViewAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.saving_goals_recycler_row_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(SavingGoalsRecyclerViewAdaptor.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final SavingGoal savingGoal = savingGoals.get(position);
        final int entryPosition = position;

        // Set item views based on your views and data model
        TextView textView1 = viewHolder.savingGoal;
        textView1.setText(savingGoal.getGoalName());

        TextView textView2 = viewHolder.daysLeft;
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            Date date1 = myFormat.parse(savingGoal.getDeadline());
            Date date2 = new Date();
            long diff = date1.getTime() - date2.getTime();
            long daysLeft = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            if(daysLeft >= 32) {

                textView2.setTextColor(Color.parseColor("#33cc33"));
                textView2.setText("Days left: " + daysLeft);
            }
            else if (daysLeft >= 15 && daysLeft <= 31) {

                textView2.setTextColor(Color.parseColor("#ff751a"));
                textView2.setText("Days left: " + daysLeft);
            }
            else if (daysLeft < 1){

                textView2.setTextColor(Color.parseColor("#ff0000"));
                textView2.setText("Deadline reached!");
            }
            else {

                textView2.setTextColor(Color.parseColor("#ff0000"));
                textView2.setText("Days left: " + daysLeft);
            }
        }
        catch(Exception e) {

        }

        Button button = viewHolder.editButton;
        button.setText("Edit");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("userID", savingGoal.getUserID());
                args.putString("userName", savingGoal.getUsername());
                args.putString("currentBalance", savingGoal.getCurrentBalance());
                args.putString("currentValue", savingGoal.getCurrentValue());
                args.putString("goalID", savingGoal.getGoalID());
                args.putString("goalName", savingGoal.getGoalName());
                args.putString("itemCost", savingGoal.getItemCost());
                args.putString("deadLine", savingGoal.getDeadline());
                args.putString("priority", savingGoal.getPriority());

                Fragment editGoalFrag = new SavingGoalsEdit();
                editGoalFrag.setArguments(args);

                ((HomeScreenUser)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, editGoalFrag)
                        .commit();
            }
        });

        //delete button
        Button deleteBtn = viewHolder.deleteButton;

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = "Are you sure you want to delete " + savingGoal.getGoalName() + "?";

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Warning!");
                builder.setMessage(msg);

                //yes button selected
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String result = "";

                        try{
                            result = new DeleteSavingGoalsAsync(context, savingGoal.getGoalID())
                                            .execute()
                                            .get(5000, TimeUnit.MILLISECONDS);
                        }
                        catch(Exception e) {

                        }

                        String[] resultArray = result.split(",");

                        if(resultArray[0].equals("Success")) {

                            savingGoals.remove(entryPosition);

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("DigiBank Alert");
                            builder.setMessage(savingGoal.getGoalName() + " successfully deleted.");

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

        //search button
        Button searchBtn = viewHolder.searchButton;

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("goalName", savingGoal.getGoalName());
                args.putString("itemCost", savingGoal.getItemCost());
                args.putString("currentBalance", savingGoal.getCurrentBalance());
                args.putString("currentValue", savingGoal.getCurrentValue());
                args.putString("goalName", savingGoal.getGoalName());
                args.putString("deadline", savingGoal.getDeadline());
                args.putString("priority", savingGoal.getPriority());

                Fragment viewGoalFrag = new SavingGoalsView();
                viewGoalFrag.setArguments(args);

                ((HomeScreenUser)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, viewGoalFrag)
                        .commit();
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {

        return savingGoals.size();
    }
}
