package com.jefferystudio.bankingsimulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DepositAH extends Fragment
{
    private Bundle args;
    private String currentID;
    private String currentBalance;
    private TextView userID;
    private TextView userBalance;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.deposit_ah, container, false);

        args = new Bundle();
        currentID = args.getString("userID");
        currentBalance = args.getString("currentBalance");

        userID = view.findViewById(R.id.usernameLbl);
        userBalance = view.findViewById(R.id.balanceLbl);
        userID.setText(currentID);
        userBalance.setText(currentBalance);

        return view;
    }

}
