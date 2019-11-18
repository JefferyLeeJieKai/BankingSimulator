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

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageAccHome;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.CreateClass;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.DeleteClass;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.EditClass;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClass;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateBalanceAsync;
import com.jefferystudio.bankingsimulator.DepositPackage.DepositBanker;
import com.jefferystudio.bankingsimulator.Quiz.QuizHistoryBanker;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Settings;
import com.jefferystudio.bankingsimulator.ViewTransactionsPackage.ViewTransactionsBanker;
import com.jefferystudio.bankingsimulator.WithdrawalPackage.BankerSettings;

public class HomeFragmentBanker extends Fragment {

    private TextView greetingsMsg;
    private TextView currentAccountNo;
    private TextView currentUserBalance;
    private Bundle args;
    private String currentID;
    private ImageButton btnsettings;
    private ImageButton btnquizresult;
    private ImageButton btnViewTransactions;
    private ImageButton btnDeposit;
    private ImageButton btncreateclass;
    private ImageButton btneditclass;
    private ImageButton btndeleteclass;
    private ImageButton btnviewclass;

    public HomeFragmentBanker(){

    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment_banker, container, false);

        args = getArguments();
        greetingsMsg = view.findViewById(R.id.textView4);
        greetingsMsg.setText("Welcome to KidzSmart, " + args.getString("userName") + " (Banker)");

        btnsettings = view.findViewById(R.id.settings);
        btnquizresult = view.findViewById(R.id.viewquizresult);
        btnViewTransactions = view.findViewById(R.id.bankertransactions);
        btnDeposit = view.findViewById(R.id.bankerdepositfunds);
        btncreateclass = view.findViewById(R.id.createclassbtn);
        btneditclass = view.findViewById(R.id.editclassbtn);
        btndeleteclass = view.findViewById(R.id.deleteclassbtn);
        btnviewclass = view.findViewById(R.id.viewclassbtn);



        btnsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity().getApplicationContext(), BankerSettings.class);
                intent.putExtras(args);
                startActivity(intent);

            }
        });

        btnquizresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity().getApplicationContext(), QuizHistoryBanker.class);
                startActivity(intent);

            }
        });


        btnViewTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment viewTransBanker = new ViewTransactionsBanker();
                viewTransBanker.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, viewTransBanker);
                transaction.commit();

            }
        });

        btncreateclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment createclass = new CreateClass();
                createclass.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, createclass);
                transaction.commit();

            }
        });

        btneditclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment editclass = new EditClass();
                editclass.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, editclass);
                transaction.commit();

            }
        });

        btndeleteclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment deleteclass = new DeleteClass();
                deleteclass.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, deleteclass);
                transaction.commit();

            }
        });

        btnviewclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment viewclass = new ViewClass();
                viewclass.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, viewclass);
                transaction.commit();

            }
        });

        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment dBanker = new DepositBanker();
                dBanker.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, dBanker);
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
