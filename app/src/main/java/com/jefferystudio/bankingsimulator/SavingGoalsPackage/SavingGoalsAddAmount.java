package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

import java.text.DecimalFormat;

public class SavingGoalsAddAmount extends Fragment {

    private ProgressBar progress;
    private TextView progressLabel;
    private TextView amountSavedCurrency;
    private TextView amountSaved;
    private TextView amountRemainCurrency;
    private TextView amountRemain;
    private Spinner goalNames;
    private TextInputLayout amount;
    private Button confirmButton;
    private Button cancelButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals_add_amount, container, false);

        progress = view.findViewById(R.id.amountPB);

        //set to 2 decimal place
        DecimalFormat df = new DecimalFormat("0.00");


        //testing purpose
        double savingAmount = 60;
        double existAmount = 25.5;


        //formula to find the remaining amount
        double remainAmount = savingAmount - existAmount;


        //amount saved
        amountSavedCurrency = view.findViewById(R.id.asCurrencyLbl);

        amountSaved = view.findViewById(R.id.asLbl);
        amountSaved.setText(df.format(existAmount));

        //amount remaining
        amountRemainCurrency = view.findViewById(R.id.arCurrencyLbl);

        amountRemain = view.findViewById(R.id.arLbl);
        amountRemain.setText(df.format(remainAmount));


        //call calculation() method
        int percent = calculation(savingAmount, existAmount);

        //set progress bar
        progressLabel = view.findViewById(R.id.progressLbl);
        progressLabel.setText(String.valueOf(percent));

        progress.setProgress(percent);


        //goal name
        goalNames = view.findViewById(R.id.goalNameDDL);


        //add amount
        amount = view.findViewById(R.id.amountTxt);


        //confirm button
        confirmButton = view.findViewById(R.id.confirmBtn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if invalid amount found
                if (!validateAmount()) {

                    return;
                }
            }
        });

        //cancel button
        cancelButton = view.findViewById(R.id.cancelBtn);

        return view;
    }

    //calculation
    private int calculation(double savingAmount, double existAmount)
    {
        double result = (existAmount / savingAmount) * 100;
        
        int percent = (int)result;

        return percent;
    }

    //validations
    private boolean validateAmount() {

        String input = amount.getEditText().getText().toString().trim();

        boolean result = Validation.validateAmount(input, amount);

        if (result) {
            amount.setError(null);
        }

        return result;
    }

}
