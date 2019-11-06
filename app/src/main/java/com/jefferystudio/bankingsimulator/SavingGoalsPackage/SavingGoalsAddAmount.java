package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;

import java.text.DecimalFormat;

public class SavingGoalsAddAmount extends Fragment {

    ProgressBar progress;
    TextView progressLabel;
    TextView amountSavedCurrency;
    TextView amountSaved;
    TextView amountRemainCurrency;
    TextView amountRemain;

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

        return view;
    }

    public int calculation(double savingAmount, double existAmount)
    {
        int percent = (int) Math.round((existAmount / savingAmount) * 100);

        return percent;
    }

}
