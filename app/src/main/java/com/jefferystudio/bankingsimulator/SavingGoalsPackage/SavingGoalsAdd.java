package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.R;

import java.util.Calendar;

public class SavingGoalsAdd extends Fragment {

    private Bundle args;
    private String currentID;
    private String userName;
    private String inputName;
    private String inputAmount;
    private TextView username;
    private TextView userBalance;
    private TextInputLayout savingGoalName;
    private TextInputLayout savingGoalAmount;
    private Button createButton;
    private Button cancelButton;
    private EditText etDate;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals, container, false);

        args = getArguments();
        userName = args.getString("userName");
        currentID = args.getString("userID");

        username = view.findViewById(R.id.usernameLbl);
        userBalance = view.findViewById(R.id.amountLbl);
        username.setText(userName);

        savingGoalName = view.findViewById(R.id.goalNameTxt);
        savingGoalAmount = view.findViewById(R.id.amountTxt);

        //date
        etDate = view.findViewById(R.id.date);

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String strDate = day + "/" + month + "/" + year;
                        etDate.setText(strDate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        createButton = view.findViewById(R.id.createBtn);
        cancelButton = view.findViewById(R.id.cancelBtn);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputName = savingGoalName.getEditText().getText().toString().trim();
                inputAmount = savingGoalAmount.getEditText().getText().toString().trim();

                if(validateAmount(inputAmount)) {

                    new SettingsGoalsAsync(getActivity(), "NewSavingGoal").execute(currentID, inputName, inputAmount);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment homeFrag = new HomeFragmentUser();
                homeFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, homeFrag)
                        .commit();
            }
        });

        return view;
    }

    protected boolean validateAmount(String input) {

        boolean result = true;

        if(input.isEmpty()) {

            savingGoalAmount.setError("Amount cannot be empty");
            result = false;
        }

        float amount = Float.valueOf(input);
        int befDec = 0;
        int currentCount = 0;

        for(int i = 0; i < input.length(); i++) {

            if(input.charAt(i) == '.') {

                befDec = currentCount;
            }

            ++currentCount;
        }

        if(input.length() - (befDec + 1) != 2) {

            savingGoalAmount.setError("Please enter the correct format");
            result = false;
        }

        return result;
    }
}
