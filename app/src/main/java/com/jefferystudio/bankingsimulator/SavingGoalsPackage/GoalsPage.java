package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsAddFragment;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsAllFragment;

public class GoalsPage extends Fragment {

    private Context homeScreenActivity;
    private ImageButton btnViewGoals;
    private Button btnback;
    private ImageButton btnviewallgoals;
    private ImageButton btnsetnewgoals;
    private Bundle args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.goalspage, container, false);
        args = getArguments();

        btnViewGoals = view.findViewById(R.id.viewallgoals);
        btnback = view.findViewById(R.id.buttonback);
        btnsetnewgoals = view.findViewById(R.id.setnewgoals);

        btnViewGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment savingGoalsAll = new SavingGoalsAllFragment();
                savingGoalsAll.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                             .replace(R.id.frame_layout, savingGoalsAll)
                             .commit();
            }
        });

        btnsetnewgoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment savingGoalsAdd = new SavingGoalsAddFragment();
                savingGoalsAdd.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, savingGoalsAdd)
                        .commit();
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment homeFrag = new HomeFragmentUser();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, homeFrag)
                        .commit();
            }
        });

        return view;
    }
}
