package com.jefferystudio.bankingsimulator.ViewTransactionsPackage;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;
import com.jefferystudio.bankingsimulator.ViewTransactionsPackage.ViewTransactionsRecyclerView.TransactionsRecyclerViewAdaptor;

import java.util.ArrayList;

public class ViewTransactionsBankerFragment extends Fragment {

    private Button btnback;
    private Bundle args;
    private String currentID;
    private RecyclerView transactionDetails;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewtransactions_banker, container, false);

        args = getArguments();
        currentID = args.getString("userID");

        btnback = view.findViewById(R.id.backBtn);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment homeFrag = new HomeFragmentBanker();
                homeFrag.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, homeFrag)
                        .commit();
            }
        });

        transactionDetails = view.findViewById(R.id.transactionsDetailsRv);
        new RetrieveTransactionsAsync(getActivity(), transactionDetails, "viewBanker").execute(currentID, args.getString("userName"), args.getString("currentBalance"));
        return view;
    }
}
