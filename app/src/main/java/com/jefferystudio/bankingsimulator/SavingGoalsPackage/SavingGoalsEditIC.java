package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

public class SavingGoalsEditIC extends Fragment {

    private Bundle args;
    private String currentUsername;
    private String currentUserID;
    private String currentBalance;
    private String currentGoalID;
    private String currentItemCost;
    private TextView username;
    private TextView balance;
    private TextView itemCost;
    private TextInputLayout newItemCost;
    private Button confirmButton;
    private Button cancelButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals_edit_ic, container, false);

        args = getArguments();

        currentUsername = args.getString("userName");
        currentUserID = args.getString("userID");
        currentGoalID = args.getString("goalID");
        currentBalance = args.getString("currentBalance");
        currentItemCost = args.getString("itemCost");

        username = view.findViewById(R.id.usernameLbl);
        balance = view.findViewById(R.id.balanceLbl);
        itemCost = view.findViewById(R.id.amountLbl);

        username.setText(currentUsername);
        balance.setText(currentBalance);
        itemCost.setText(currentItemCost);

        newItemCost = view.findViewById(R.id.amountTxt);

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

        confirmButton = view.findViewById(R.id.confirmBtn);
        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String toChange = newItemCost.getEditText().getText().toString().trim();

                if (Validation.validateAmount(toChange, newItemCost)) {

                    if (toChange.equals(currentItemCost)) {

                        newItemCost.setError("No changes made to item cost");
                    }
                    else {

                        newItemCost.setError(null);

                        new UpdateSavingGoalsAsync(getActivity(), "EditItemCost", args.getString("userName")).execute(currentUserID, currentGoalID, toChange);
                    }
                }
            }
        });

        return view;
    }
}
