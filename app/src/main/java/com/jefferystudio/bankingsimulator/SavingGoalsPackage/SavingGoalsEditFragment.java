package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SavingGoalsEditFragment extends Fragment {

    private Bundle args;
    private String currentUserID;
    private String currentUsername;
    private String currentBalance;
    private String currentGoalName;
    private String currentCost;
    private String currentDeadline;
    private String currentPriority;
    private TextView username;
    private TextView balance;
    private TextView goalName;
    private TextView cost;
    private TextView deadline;
    private TextView priority;
    private EditText editGoalName;
    private EditText editCost;
    private EditText editDeadline;
    private Spinner editPriority;
    private Button saveForGoal;
    private Button editGoalNameButton;
    private Button editItemCostButton;
    private Button editDeadlineButton;
    private Button editPriorityButton;
    private Button cancelButton;
    private ArrayList<String> priorityList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals_edit, container, false);

        args = getArguments();

        currentUserID = args.getString("userID");
        currentUsername = args.getString("userName");
        currentBalance = args.getString("currentBalance");
        currentGoalName = args.getString("goalName");
        currentCost = args.getString("itemCost");
        currentDeadline = args.getString("deadLine");
        currentPriority = args.getString("priority");

        goalName = view.findViewById(R.id.goal);
        cost = view.findViewById(R.id.itemvalue);
        deadline = view.findViewById(R.id.deadline);
        priority = view.findViewById(R.id.priority);
        editGoalName = view.findViewById(R.id.goalinput);
        editCost = view.findViewById(R.id.itemvalueinput);

        editPriority = view.findViewById(R.id.priorityinput);
        priorityList = new ArrayList<>();
        for(int i = 5; i >= 1; i--) {

            if(i == 5) {

                String entry = i + " (Highest priority)";
                priorityList.add(entry);
            }
            else if(i == 1) {

                String entry = i + " (Lowest priority)";
                priorityList.add(entry);
            }
            else {

                priorityList.add(String.valueOf(i));
            }
        }
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, priorityList);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editPriority.setAdapter(priorityAdapter);

        editDeadline = view.findViewById(R.id.deadlineinput);
        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        editDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String strDate = year + "-" + month + "-" + day;
                        editDeadline.setText(strDate);

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        goalName.setText("Current goal name is: " + currentGoalName);
        cost.setText("Current saving goal is: $" + currentCost);
        deadline.setText("Current deadline is: " + currentDeadline);
        priority.setText("Current priority is: " + currentPriority);

        saveForGoal = view.findViewById(R.id.savemoney);
        saveForGoal.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment saveMoneyFrag = new SavingGoalsAddAmountFragment();
                saveMoneyFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, saveMoneyFrag)
                        .commit();
            }
        });

        editGoalNameButton = view.findViewById(R.id.editGoalNameBtn);
        editGoalNameButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

               String input = editGoalName.getText().toString().trim();

               if(!Validation.validateEmptyNonTextInputLayout(input)) {

                   displayNullDialog();
               }
               else {

                   displayConfirmationDialog(input, "UpdateGoalName");
               }
            }
        });

        editItemCostButton = view.findViewById(R.id.editAmountBtn);
        editItemCostButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String input = editCost.getText().toString().trim();

                if(!Validation.validateEmptyNonTextInputLayout(input)) {

                    displayNullDialog();
                }
                else {

                    if(!Validation.validateAmountNonTextInputLayout(input)) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Digibank Alert");
                        builder.setMessage("Please ensure that the amount entered is in two decimal places and only digits");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        AlertDialog formatErrorDialog = builder.create();
                        formatErrorDialog.show();
                    }
                    else {

                        displayConfirmationDialog(input, "UpdateAmount");
                    }
                }
            }
        });

        editDeadlineButton = view.findViewById(R.id.editDeadlineBtn);
        editDeadlineButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String input = editDeadline.getText().toString().trim();

                if(!Validation.validateEmptyNonTextInputLayout(input)) {

                    displayNullDialog();
                }
                else {

                    displayConfirmationDialog(input, "UpdateDeadline");
                }
            }
        });

        editPriorityButton = view.findViewById(R.id.editPriorityBtn);
        editPriorityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String input = String.valueOf(editPriority.getSelectedItem());
                    displayConfirmationDialog(input.substring(0, 1), "UpdatePriority");
            }
        });

        cancelButton = view.findViewById(R.id.cancelBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment savingGoalFrag = new SavingGoalsAllFragment();
                savingGoalFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                             .replace(R.id.frame_layout, savingGoalFrag)
                             .commit();
            }
        });

        return view;
    }

    public void displayNullDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Digibank Alert");
        builder.setMessage("Please ensure that the field is filled");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog nullDialog = builder.create();
        nullDialog.show();
    }

    public void displayConfirmationDialog(final String input, final String flag) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Digibank Alert");

        if(flag.equals("UpdateGoalName")) {

            builder.setMessage("Do you want to update goal name to: " + input);
        }
        else if(flag.equals("UpdateAmount")) {

            builder.setMessage("Do you want to update goal amount to: " + input);
        }
        else if(flag.equals("UpdateDeadline")) {

            builder.setMessage("Do you want to update deadline to: " + input);
        }
        else if(flag.equals("UpdatePriority")) {

            builder.setMessage("Do you want to update priority to: " + input);
        }

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String result = "";

                try {
                result = new UpdateSavingGoalsAsync(getActivity(), flag, currentUsername)
                            .execute(currentUserID, args.getString("goalID"), input)
                            .get(5000, TimeUnit.MILLISECONDS);
                }
                catch (Exception e) {


                }

                String[] resultArray = result.split(",");

                if(resultArray[0].equals("True")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("DigiBank Alert");
                    builder.setMessage("Update success!");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if(flag.equals("UpdateGoalName")) {

                                goalName.setText("Current goal name is: " + input);
                                editGoalName.getText().clear();
                            }
                            else if (flag.equals("UpdateAmount")) {

                                cost.setText("Current saving goal is: $" + input);
                                editCost.getText().clear();
                            }
                            else if(flag.equals("UpdateDeadline")) {

                                deadline.setText("Current deadline is: " + input);
                                editDeadline.getText().clear();
                            }
                            else if(flag.equals("UpdatePriority")) {

                                priority.setText("Current priority is: " + input);
                            }
                        }
                    });

                    AlertDialog informDialog = builder.create();
                    informDialog.show();
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        AlertDialog confirmDialog = builder.create();
        confirmDialog.show();
    }
}
