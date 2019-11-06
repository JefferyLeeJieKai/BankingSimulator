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
import com.jefferystudio.bankingsimulator.Transfer_Amount;
import com.jefferystudio.bankingsimulator.ViewTransactionsPackage.ViewTransactions;
import com.jefferystudio.bankingsimulator.WithdrawalPackage.WithdrawalAH;
import com.jefferystudio.bankingsimulator.goalspage;
import com.jefferystudio.bankingsimulator.profilepage;
import com.jefferystudio.bankingsimulator.transferamount;
import com.jefferystudio.bankingsimulator.transferpayee;

public class HomeFragmentUser extends Fragment {

    private TextView greetingsMsg;
    private TextView currentAccountNo;
    private TextView currentUserBalance;
    private Bundle args;
    private String currentID;
    private ImageButton btnsavings;
    private ImageButton btntransfer;
    private ImageButton btnsettings;
    private ImageButton btnviewtransaction;
    private ImageButton btnwithdraw;



    public HomeFragmentUser(){

    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment_user, container, false);

        args = getArguments();
        greetingsMsg = view.findViewById(R.id.textView4);
        greetingsMsg.setText("Welcome to KidzSmart, " + args.get("userName"));

        btnsavings = view.findViewById(R.id.savings);
        btntransfer = view.findViewById(R.id.transferfunds);
        btnsettings = view.findViewById(R.id.settings);
        btnviewtransaction = view.findViewById(R.id.viewtransaction);
        btnwithdraw = view.findViewById(R.id.withdraw);


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
                Fragment transferpayees = new Transfer_Amount();
                transferpayees.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, transferpayees);
                transaction.commit();
            }
        });

        btnwithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment Withdrawalah = new WithdrawalAH();
                Withdrawalah.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, Withdrawalah);
                transaction.commit();
            }
        });

        btnsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity().getApplicationContext(), profilepage.class);
                startActivity(intent);

            }
        });

        btnviewtransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment viewTransactions = new ViewTransactions();
                viewTransactions.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, viewTransactions);
                transaction.commit();
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

