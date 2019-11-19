package com.jefferystudio.bankingsimulator.DepositPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.TransactionAsync;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateTransAsync;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DepositConfirmBanker extends Fragment {

    private String targetName;
    private String targetID;
    private String input;
    private TextView username;
    private TextView accNo;
    private TextView amount;
    private Bundle args;
    private Button confirmButton;
    private Button cancelButton;
    private TextView DisplayDateTime;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private TextView TextViewdate;
    private String date;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.deposit_confirm, container, false);

        args = getArguments();
        targetName = args.getString("targetName");
        targetID = args.getString("targetID");
        input = args.getString("amount");

        username = view.findViewById(R.id.usernameLbl);
        username.setText("Username: " + targetName);
        accNo = view.findViewById(R.id.accountLbl);
        accNo.setText("Acc No. : " + targetID);
        amount = view.findViewById(R.id.amountLbl);
        amount.setText(input);

        confirmButton = view.findViewById(R.id.confirmBtn);
        cancelButton = view.findViewById(R.id.cancelBtn);


        TextViewdate = view.findViewById(R.id.dateLbl);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        TextViewdate.setText(date);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TransactionAsync(getActivity(),"DepositBanker", args.getString("userName")).execute(targetID, input, args.getString("userID"));
                new UpdateTransAsync(getActivity(), "BankerDeposit").execute(args.getString("userID"), input, targetID, "NotApplicable");
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment homeFrag = new HomeFragmentBanker();
                homeFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, homeFrag)
                        .commit();
            }
        });


        return view;
    }

    public void recall() {

        Fragment recallFrag = new DepositBanker();
        recallFrag.setArguments(args);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, recallFrag)
                .commit();
    }
}
