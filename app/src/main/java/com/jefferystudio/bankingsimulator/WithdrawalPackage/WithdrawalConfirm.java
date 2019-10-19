package com.jefferystudio.bankingsimulator.WithdrawalPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.TransactionAsync;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateTransAsync;

public class WithdrawalConfirm extends Fragment {

    private String userName;
    private String currentID;
    private String input;
    private TextView username;
    private TextView accNo;
    private TextView amount;
    private Bundle args;
    private Button confirmButton;
    private Button cancelButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.withdraw_confirm, container, false);

        args = getArguments();
        userName = args.getString("userName");
        currentID = args.getString("userID");
        input = args.getString("amount");

        username = view.findViewById(R.id.usernameLbl);
        username.setText(userName);
        accNo = view.findViewById(R.id.accountLbl);
        accNo.setText(currentID);
        amount = view.findViewById(R.id.amountLbl);
        amount.setText(input);

        confirmButton = view.findViewById(R.id.confirmBtn);
        cancelButton = view.findViewById(R.id.cancelBtn);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TransactionAsync(getActivity(),"WithdrawalUser").execute(currentID, input);
                new UpdateTransAsync(getActivity(), "WithdrawFunds").execute(currentID, input);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment homeFrag = new HomeFragmentUser();
                homeFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, homeFrag)
                        .commit();
            }
        });

        return view;
    }

    public void recall() {

        Fragment recallFrag = new WithdrawalAH();
        recallFrag.setArguments(args);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, recallFrag)
                .commit();
    }
}
