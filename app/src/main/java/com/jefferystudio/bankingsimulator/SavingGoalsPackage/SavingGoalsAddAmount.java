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

import com.jefferystudio.bankingsimulator.CommonAsyncPackage.TransactionAsync;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class SavingGoalsAddAmount extends Fragment {

    private Bundle args;
    private ProgressBar progress;
    private TextView progressLabel;
    private TextView amountSavedCurrency;
    private TextView amountSaved;
    private TextView amountRemainCurrency;
    private TextView amountRemain;
    private TextView goalName;
    private TextInputLayout amount;
    private Button confirmButton;
    private Button cancelButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals_add_amount, container, false);

        args = getArguments();

        progress = view.findViewById(R.id.amountPB);

        float savingAmount = Float.valueOf(args.getString("itemCost"));
        final float existAmount = Float.valueOf(args.getString("currentValue"));
        float remainAmount = savingAmount - existAmount;
        int percent = calculation(savingAmount, existAmount);

        progressLabel = view.findViewById(R.id.progressLbl);
        progressLabel.setText(String.valueOf(percent));

        progress.setProgress(percent);

        //amount saved
        amountSavedCurrency = view.findViewById(R.id.asCurrencyLbl);

        amountSaved = view.findViewById(R.id.asLbl);
        amountSaved.setText(args.getString("currentValue"));

        //amount remaining
        amountRemainCurrency = view.findViewById(R.id.arCurrencyLbl);

        amountRemain = view.findViewById(R.id.arLbl);
        amountRemain.setText(Float.toString(remainAmount));

        //goal name
        goalName = view.findViewById(R.id.goalNameDDL);
        goalName.setText(args.getString("goalName"));


        //add amount
        amount = view.findViewById(R.id.amountTxt);

        //confirm button
        confirmButton = view.findViewById(R.id.confirmBtn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = amount.getEditText().getText().toString().trim();

                //if invalid amount found
                if (validateAmount(input)) {

                    String withdrawalTest;
                    float amountToSave = Float.valueOf(input);

                    try {

                        withdrawalTest = new TransactionAsync(getActivity(), "WithdrawalUser", args.getString("userName"))
                                             .execute(args.getString("userID"), input)
                                             .get(5000, TimeUnit.MILLISECONDS);

                        String[] withdrawalTestArray = withdrawalTest.split(",");

                        if(withdrawalTestArray[0].equals("True")) {

                            float amountToUpdate = amountToSave + existAmount;

                            new UpdateSavingGoalsAsync(getActivity(), "SaveMoney", args.getString("userName"))
                                    .execute(args.getString("userID"), args.getString("goalID"), Float.toString(amountToUpdate));
                        }
                        else if(withdrawalTestArray[0].equals("False")) {

                            amount.setError("Amount to save exceeds your bank balance.");
                        }
                    }
                    catch (Exception e) {


                    }
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
    private boolean validateAmount(String input) {

        boolean result = Validation.validateAmount(input, amount);

        if (result) {
            amount.setError(null);
        }

        return result;
    }

}
