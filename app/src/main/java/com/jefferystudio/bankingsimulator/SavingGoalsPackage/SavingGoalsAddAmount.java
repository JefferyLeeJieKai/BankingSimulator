package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jefferystudio.bankingsimulator.R;

public class SavingGoalsAddAmount extends Fragment {

    ProgressBar progress;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals_add_amount, container, false);

        progress = view.findViewById(R.id.amountPB);

        double savingAmount = 60;
        double existAmount = 25.5;

        int percent = calculation(savingAmount, existAmount);

        progress.setProgress(percent);

        return view;
    }

    public int calculation(double savingAmount, double existAmount)
    {
        int percent = (int) Math.round((existAmount / savingAmount) * 100);

        return percent;
    }

}
