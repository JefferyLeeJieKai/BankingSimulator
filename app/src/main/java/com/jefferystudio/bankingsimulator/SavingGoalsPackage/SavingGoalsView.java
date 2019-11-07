package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;

public class SavingGoalsView extends Fragment {

    private Bundle args;
    private String currentGoalName;
    private String currentCost;
    private String currentDateline;
    private String currentPriority;
    private TextView goalName;
    private TextView cost;
    private TextView deadline;
    private TextView priority;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals_view, container, false);

        args = getArguments();

        currentGoalName = args.getString("goalName");
        currentCost = args.getString("itemCost");
        currentDateline = args.getString("dateline");
        currentPriority = args.getString("priority");

        goalName = view.findViewById(R.id.goalNameLbl);
        cost = view.findViewById(R.id.amountLbl);
        deadline = view.findViewById(R.id.goalDateLbl);
        priority = view.findViewById(R.id.priorityLbl);

        goalName.setText(currentGoalName);
        cost.setText(currentCost);
        deadline.setText(currentDateline);
        priority.setText(currentPriority);

        return view;
    }
}
