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

public class SavingGoalsEditGN extends Fragment {

    private Bundle args;
    private String currentUsername;
    private String currentUserID;
    private String currentBalance;
    private String currentGoalID;
    private String currentGoalname;
    private TextView username;
    private TextView balance;
    private TextView goalName;
    private TextInputLayout newGoalName;
    private Button cancelButton;
    private Button confirmButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals_edit_gn, container, false);

        args = getArguments();

        currentUsername = args.getString("userName");
        currentUserID = args.getString("userID");
        currentBalance = args.getString("currentBalance");
        currentGoalname = args.getString("goalName");
        currentGoalID = args.getString("goalID");

        username = view.findViewById(R.id.usernameLbl);
        balance = view.findViewById(R.id.balanceLbl);
        goalName = view.findViewById(R.id.goalNameLbl);

        username.setText(currentUsername);
        balance.setText(currentBalance);
        goalName.setText(currentGoalname);

        newGoalName = view.findViewById(R.id.goalNameTxt);

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

                String toChange = newGoalName.getEditText().getText().toString().trim();

                if (toChange.equalsIgnoreCase(currentGoalname))
                    newGoalName.setError("No changes made to goal name");
                else
                    new UpdateSavingGoalsAsync(getActivity(), "EditGoalName").execute(currentUserID, currentGoalID, toChange);
            }
        });

        return view;
    }
}
