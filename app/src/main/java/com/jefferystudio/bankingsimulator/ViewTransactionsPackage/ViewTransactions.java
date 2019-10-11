package com.jefferystudio.bankingsimulator.ViewTransactionsPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;

public class ViewTransactions extends Fragment {

    private Bundle args;
    private String currentUserID;
    private String currentUserName;
    private String currentBalance;
    private TextView userName;
    private TextView balance;
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_transactions, container, false);

        args = getArguments();
        currentUserID = args.getString("userID");
        currentUserName = args.getString("userName");
        currentBalance = args.getString("currentBalance");

        userName = view.findViewById(R.id.usernameLbl);
        balance = view.findViewById(R.id.balanceLbl);
        recyclerView = view.findViewById(R.id.transactionsDetailsRv);

        recyclerView = view.findViewById(R.id.transactionsDetailsRv);
        new RetrieveTransactionsAsync(getActivity(), recyclerView).execute(currentUserID, currentUserName, currentBalance);

        userName.setText(currentUserName);
        balance.setText(currentBalance);

        return view;
    }
}
