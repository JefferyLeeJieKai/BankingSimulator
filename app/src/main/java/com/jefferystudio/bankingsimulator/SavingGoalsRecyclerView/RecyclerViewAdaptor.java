package com.jefferystudio.bankingsimulator.SavingGoalsRecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreen;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsEdit;

import java.util.List;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView savingGoal;
        public Button editButton;

        public ViewHolder(View itemView) {

            super(itemView);

            savingGoal = itemView.findViewById(R.id.savingGoalItem);
            editButton = itemView.findViewById(R.id.editSavingGoal);
        }
    }

    private List<SavingGoal> savingGoals;
    private Context context;

    public RecyclerViewAdaptor(Context context, List<SavingGoal> savingGoals) {

        this.savingGoals = savingGoals;
        this.context = context;
    }

    @Override
    public RecyclerViewAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycler_row_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecyclerViewAdaptor.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final SavingGoal savingGoal = savingGoals.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.savingGoal;
        textView.setText(savingGoal.getGoalName());
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
                args.putString("goalName", savingGoal.getGoalName());
                args.putString("itemCost", savingGoal.getItemCost());

                Fragment editGoalFrag = new SavingGoalsEdit();
                editGoalFrag.setArguments(args);

                ((HomeScreen)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, editGoalFrag)
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
