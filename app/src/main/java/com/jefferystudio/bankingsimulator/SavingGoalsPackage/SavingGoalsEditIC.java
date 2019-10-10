package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;

public class SavingGoalsEditIC extends Fragment {

    private Bundle args;
    private String currentUsername;
    private String currentBalance;
    private String currentItemCost;
    private TextView username;
    private TextView balance;
    private TextView itemCost;
    private Button cancelButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals_edit_ic, container, false);

        args = getArguments();

        currentUsername = args.getString("userName");
        currentBalance = args.getString("currentBalance");
        currentItemCost = args.getString("itemCost");

        username = view.findViewById(R.id.usernameLbl);
        balance = view.findViewById(R.id.balanceLbl);
        itemCost = view.findViewById(R.id.amountLbl);

        username.setText(currentUsername);
        balance.setText(currentBalance);
        itemCost.setText(currentItemCost);

        cancelButton = view.findViewById(R.id.cancelBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment allSavingGoalsFrag = new SavingGoalsAll();
                allSavingGoalsFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, allSavingGoalsFrag)
                        .commit();
            }
        });

        return view;
    }
}
