package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.DepositPackage.DepositAH;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
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
    private TextView progressLabel;
    private Button backbtn;

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
        backbtn = view.findViewById(R.id.buttonback);

        userName.setText(args.getString("userName"));
        balance.setText(args.getString("currentBalance"));
        amountSaved.setText(args.getString("currentValue"));
        goalName.setText(args.getString("goalName"));
        cost.setText(args.getString("itemCost"));
        deadline.setText(args.getString("dateline"));
        priority.setText(args.getString("priority"));

        float itemAmount = Float.valueOf(args.getString("itemCost"));
        float savedAmount = Float.valueOf(args.getString("currentValue"));
        float remainingAmount = itemAmount - savedAmount;

        amountLeft.setText(Float.toString(remainingAmount));

        int percentage = calculation(itemAmount, savedAmount);

        progressLabel = view.findViewById(R.id.progressLbl);
        progressLabel.setText(String.valueOf(percentage));

        savingsBar.setProgress(percentage);

        backbtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });

        return view;
    }

    private int calculation(float savingAmount, float existAmount)
    {
        float result = (existAmount / savingAmount) * 100;

        int percent = (int)result;

        return percent;
    }


}
