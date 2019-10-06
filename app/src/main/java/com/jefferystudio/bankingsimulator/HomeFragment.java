package com.jefferystudio.bankingsimulator;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    private TextView currentAccountNo;
    private TextView currentUserBalance;
    private Bundle args;
    private String currentID;
    private ImageButton btnsavings;
    private ImageButton btntransfer;


    public HomeFragment(){

    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        btnsavings = view.findViewById(R.id.savings);
        btntransfer = view.findViewById(R.id.transferfunds);

        btnsavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                goalspage goalspages = new goalspage();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, goalspages);
                transaction.commit();
            }
        });

        btntransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                transferpayee transferpayees = new transferpayee();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, transferpayees);
                transaction.commit();
            }
        });

        currentAccountNo = view.findViewById(R.id.actualAccountNo);
        currentUserBalance = view.findViewById(R.id.actualCurrentBalance);

        args = getArguments();
        currentID = args.getString("userID");
        currentAccountNo.setText(currentID);
        new UpdateBalanceAsync(getActivity(), currentUserBalance).execute(currentID);



        return view;


    }

}

