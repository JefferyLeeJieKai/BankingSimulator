package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;

public class SavingGoalsView extends Fragment {

    private Bundle args;

    private TextView userName;
    private TextView balance;
    private ProgressBar savingsBar;
    private TextView amountSaved;
    private TextView amountLeft;
    private TextView goalName;
    private TextView cost;
    private TextView deadline;
    private TextView priority;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals_view, container, false);

        args = getArguments();

        userName = view.findViewById(R.id.usernameLbl);
        balance = view.findViewById(R.id.balanceLbl);
        savingsBar = view.findViewById(R.id.amountPB);
        amountSaved = view.findViewById(R.id.asLbl);
        amountLeft = view.findViewById(R.id.arLbl);
        goalName = view.findViewById(R.id.goalNameLbl);
        cost = view.findViewById(R.id.amountLbl);
        deadline = view.findViewById(R.id.goalDateLbl);
        priority = view.findViewById(R.id.priorityLbl);

        userName.setText(args.getString("userName"));
        balance.setText(args.getString("currentBalance"));
        amountSaved.setText(args.getString("currentValue"));
        goalName.setText(args.getString("goalName"));
        cost.setText(args.getString("itemCost"));
        deadline.setText(args.getString("dateline"));
        priority.setText(args.getString("priority"));

        float savedAmount = Float.valueOf(args.getString("currentValue"));
        float itemAmount = Float.valueOf(args.getString("itemCost"));
        float remainingAmount = itemAmount - savedAmount;

        amountLeft.setText(Float.toString(remainingAmount));

        int percentage = calculation(savedAmount, itemAmount);
        savingsBar.setProgress(percentage);

        return view;
    }

    private int calculation(float savingAmount, float existAmount)
    {
        float result = (existAmount / savingAmount) * 100;

        int percent = (int)result;

        return percent;
    }
}
