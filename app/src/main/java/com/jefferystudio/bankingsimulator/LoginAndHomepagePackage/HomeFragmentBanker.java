package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateBalanceAsync;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Settings;
import com.jefferystudio.bankingsimulator.goalspage;
import com.jefferystudio.bankingsimulator.transferpayee;

public class HomeFragmentBanker extends Fragment {

    private TextView greetingsMsg;
    private TextView currentAccountNo;
    private TextView currentUserBalance;
    private Bundle args;
    private String currentID;
    private ImageButton btnsavings;
    private ImageButton btntransfer;
    private ImageButton btnsettings;

    public HomeFragmentBanker(){

    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment_banker, container, false);

        args = getArguments();
        greetingsMsg = view.findViewById(R.id.textView4);
        greetingsMsg.setText("Welcome to KidzSmart, " + args.get("userName") + " (Banker)");

        btnsavings = view.findViewById(R.id.savings);
        btntransfer = view.findViewById(R.id.transferfunds);
        btnsettings = view.findViewById(R.id.settings);


        btnsavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity().getApplicationContext(), goalspage.class);
                startActivity(intent);
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

        btnsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity().getApplicationContext(), Settings.class);
                startActivity(intent);

            }
        });

        currentAccountNo = view.findViewById(R.id.actualAccountNo);
        currentUserBalance = view.findViewById(R.id.actualCurrentBalance);

        currentID = args.getString("userID");
        currentAccountNo.setText(currentID);
        new UpdateBalanceAsync(getActivity(), currentUserBalance).execute(currentID);


        return view;
    }
}
