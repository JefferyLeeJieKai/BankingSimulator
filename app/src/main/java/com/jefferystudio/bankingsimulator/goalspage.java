package com.jefferystudio.bankingsimulator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class goalspage extends Fragment {

    private ImageButton viewAllGoalsBtn;
    private ImageButton setNewGoalsBtn;
    private ImageButton addAmountBtn;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.goalspage, container, false);

        viewAllGoalsBtn = view.findViewById(R.id.viewallgoals);
        setNewGoalsBtn = view.findViewById(R.id.setnewgoals);
        addAmountBtn = view.findViewById(R.id.addamount);

        viewAllGoalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Goals_ViewAll viewAllGoals = new Goals_ViewAll();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, viewAllGoals);
                transaction.commit();
            }
        });

        setNewGoalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Goals_New setNewGoals = new Goals_New();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, setNewGoals);
                transaction.commit();
            }
        });

        addAmountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Goals_AddAmount addAmount = new Goals_AddAmount();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, addAmount);
                transaction.commit();
            }
        });

        return view;
    }
}