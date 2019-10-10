package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;

public class SavingGoalsEdit extends Fragment {

    private Bundle args;
    private String currentUserID;
    private String currentUsername;
    private String currentBalance;
    private String currentGoalName;
    private String currentCost;
    private TextView username;
    private TextView balance;
    private TextView goalName;
    private TextView cost;
    private Button editGoalNameButton;
    private Button editItemCostButton;
    private Button cancelButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals_edit, container, false);

        args = getArguments();

        currentUserID = args.getString("userID");
        currentUsername = args.getString("userName");
        currentBalance = args.getString("currentBalance");
        currentGoalName = args.getString("goalName");
        currentCost = args.getString("itemCost");

        username = view.findViewById(R.id.usernameLbl);
        balance = view.findViewById(R.id.balanceLbl);
        goalName = view.findViewById(R.id.goalNameLbl);
        cost = view.findViewById(R.id.amountLbl);

        username.setText(currentUsername);
        balance.setText(currentBalance);
        goalName.setText(currentGoalName);
        cost.setText(currentCost);

        editGoalNameButton = view.findViewById(R.id.editGoalNameBtn);
        editItemCostButton = view.findViewById(R.id.editAmountBtn);
        cancelButton = view.findViewById(R.id.cancelBtn);

        editGoalNameButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment editGoalNameFrag = new SavingGoalsEditGN();
                editGoalNameFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, editGoalNameFrag)
                        .commit();
            }
        });

        editItemCostButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment editItemCountFrag = new SavingGoalsEditIC();
                editItemCountFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, editItemCountFrag)
                        .commit();
            }
        });

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
