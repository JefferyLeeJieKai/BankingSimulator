package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.BankNote.IssueBanknoteFragment;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.CreateClassFragment;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.DeleteClassFragment;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.EditClassFragment;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClassFragment;
import com.jefferystudio.bankingsimulator.BankerManageAccount.account_create_ah1;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.RetriveBankerListAsync;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateBalanceAsync;
import com.jefferystudio.bankingsimulator.DepositPackage.DepositBankerFragment;
import com.jefferystudio.bankingsimulator.Quiz.QuizHistoryBanker;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.ViewTransactionsPackage.ViewTransactionsBankerFragment;
import com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage.BankerSettings;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
    //private ImageButton btncreateacc;
    private ImageButton btnissuenotes;
    private CircleImageView profilePic;
    private ArrayList<String> bankerList;

    public HomeFragmentBanker(){

    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment_banker, container, false);

        args = getArguments();
        args.putString("accountType", "Banker");

        bankerList = new ArrayList<>();
        new RetriveBankerListAsync(getActivity()).execute(args.getString("userID"));
        args.putStringArrayList("BankerList", bankerList);

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
        //btncreateacc = view.findViewById(R.id.btncreateacc);
        btnissuenotes = view.findViewById(R.id.btnissuenotes);

        profilePic = view.findViewById(R.id.profilephoto);
        SharedPreferences pref = getActivity().getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
        if(!pref.getString("imageLink", "NotFound").equals("NoImage")) {

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(pref.getString("imageLink", "NotFound"), profilePic);
        }

        btnsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity().getApplicationContext(), BankerSettings.class);
                intent.putExtras(args);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnquizresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Fragment quizHistoryFrag = new QuizHistoryBanker();
                quizHistoryFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, quizHistoryFrag)
                        .commit();
            }
        });


        btnissuenotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Fragment issuenotes = new IssueBanknoteFragment();
                issuenotes.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, issuenotes);
                transaction.commit();

            }
        });

        btnViewTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Fragment viewtransactions = new ViewTransactionsBankerFragment();
                viewtransactions.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, viewtransactions);
                transaction.commit();


            }
        });

        /*
        btncreateacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment acccreate = new account_create_ah1();
                acccreate.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, acccreate);
                transaction.commit();

            }
        });

         */

        btncreateclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment createclass = new CreateClassFragment();
                createclass.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, createclass);
                transaction.commit();

            }
        });

        btneditclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment viewClassFrag = new ViewClassFragment();
                viewClassFrag.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, viewClassFrag);
                transaction.commit();

            }
        });


        btnviewclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment viewclass = new ViewClassFragment();
                viewclass.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, viewclass);
                transaction.commit();

            }
        });

        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Fragment dBanker = new DepositBankerFragment();
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
        new UpdateBalanceAsync(getActivity(), currentUserBalance, null).execute(currentID);


        return view;
    }

    public void updateBankerList(String result) {

        try {
            String[] resultArray = result.split(",");

            for (int i = 0; i < resultArray.length; i += 2) {

                String entry = resultArray[i + 1] + "     AccountNo: " + resultArray[i];
                bankerList.add(entry);
            }
        }
        catch(Exception e) {

            //Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
        }
    }
}
