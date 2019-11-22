package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.BankNote.RedeemBanknoteFragment;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateBalanceAsync;
import com.jefferystudio.bankingsimulator.DepositPackage.DepositAHFragment;
import com.jefferystudio.bankingsimulator.Education.eduhomepage;
import com.jefferystudio.bankingsimulator.Quiz.StartQuiz;
import com.jefferystudio.bankingsimulator.Quiz.quizhome;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.TransferFundsPackage.TransferAmountFragment;
import com.jefferystudio.bankingsimulator.ViewTransactionsPackage.ViewTransactionsFragment;
import com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage.UserSettings;
import com.jefferystudio.bankingsimulator.WithdrawalPackage.WithdrawalAHFragment;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.GoalsPage;

import java.io.File;
import java.io.FileInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragmentUser extends Fragment {

    private TextView greetingsMsg;
    private CircleImageView profilePic;
    private TextView currentAccountNo;
    private TextView currentUserBalance;
    private Bundle args;
    private String currentID;
    private ImageButton btnsavings;
    private ImageButton btntransfer;
    private ImageButton btndeposit;
    private ImageButton btnquiz;
    private ImageButton btnviewtransaction;
    private ImageButton btnwithdraw;
    private ImageButton btnusersettings;
    private ImageButton btnvideo;
    private ImageButton btnredeem;



    public HomeFragmentUser(){

    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment_user, container, false);

        args = getArguments();

        profilePic = view.findViewById(R.id.profilephotomain);
        try {

            ContextWrapper cw = new ContextWrapper(getActivity());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File profilePicFile = new File(directory, "ProfilePicture.jpg");
            Bitmap picture = BitmapFactory.decodeStream(new FileInputStream(profilePicFile));
            profilePic.setImageBitmap(picture);
        }
        catch(Exception e) {

        }

        greetingsMsg = view.findViewById(R.id.textView4);
        greetingsMsg.setText("Welcome to KidzSmart,\n" + args.getString("userName"));

        currentAccountNo = view.findViewById(R.id.actualAccountNo);
        currentUserBalance = view.findViewById(R.id.actualCurrentBalance);

        currentID = args.getString("userID");
        currentAccountNo.setText(currentID);
        new UpdateBalanceAsync(getActivity(), currentUserBalance, null).execute(currentID);

        btnsavings = view.findViewById(R.id.savings);
        btntransfer = view.findViewById(R.id.transferfunds);
        btndeposit = view.findViewById(R.id.depositfunds);
        btnquiz = view.findViewById(R.id.quiz);
        btnviewtransaction = view.findViewById(R.id.viewtransaction);
        btnwithdraw = view.findViewById(R.id.withdraw);
        btnusersettings = view.findViewById(R.id.settingsuser);
        btnvideo = view.findViewById(R.id.btnvideo);
        btnredeem = view.findViewById(R.id.btnredeemBanknote);


        btnsavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Fragment goalsPageFragment = new GoalsPage();
                goalsPageFragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                             .replace(R.id.frame_layout, goalsPageFragment)
                             .commit();
            }
        });

        btnvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity().getApplicationContext(), eduhomepage.class);
                intent.putExtras(args);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnredeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Fragment redeemnotes = new RedeemBanknoteFragment();
                redeemnotes.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, redeemnotes);
                transaction.commit();

            }
        });

        btnusersettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent = new Intent(getActivity().getApplicationContext(), UserSettings.class);
                intent.putExtras(args);
                startActivity(intent);
            }
        });

        btntransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment transferpayees = new TransferAmountFragment();
                transferpayees.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, transferpayees);
                transaction.commit();
            }
        });

        btndeposit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment depositFrag = new DepositAHFragment();
                depositFrag.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, depositFrag);
                transaction.commit();
            }
        });

        btnwithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment Withdrawalah = new WithdrawalAHFragment();
                Withdrawalah.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, Withdrawalah);
                transaction.commit();
            }
        });

        btnquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity().getApplicationContext(), StartQuiz.class);
                intent.putExtras(args);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnviewtransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment viewTransactions = new ViewTransactionsFragment();
                viewTransactions.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, viewTransactions);
                transaction.commit();
            }
        });

        return view;
    }
}

