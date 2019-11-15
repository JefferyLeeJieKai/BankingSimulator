package com.jefferystudio.bankingsimulator.BankNote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;

public class RedeemBanknoteFragment extends Fragment {

    private TextView userName;
    private TextView balance;
    private RecyclerView notRedeemedNotes;

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.redeem_banknote, container, false);

        userName = view.findViewById(R.id.usernameLbl);
        balance = view.findViewById(R.id.balanceLbl);
        notRedeemedNotes = view.findViewById(R.id.detailsRv);


        return view;
    }
}
