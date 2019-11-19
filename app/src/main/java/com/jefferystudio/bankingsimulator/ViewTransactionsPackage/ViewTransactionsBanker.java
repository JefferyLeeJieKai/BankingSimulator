package com.jefferystudio.bankingsimulator.ViewTransactionsPackage;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

import java.util.ArrayList;

public class ViewTransactionsBanker extends Fragment {

    private TextInputLayout username;
    private Button searchButton;
    private String input;
    private Button btnback;
    private Bundle args;
    private ArrayList<String> list;
    private String currentID;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewtransactions_banker, container, false);

        args = getArguments();
        currentID = args.getString("userID");
        list = args.getStringArrayList("BankerList");

        username = view.findViewById(R.id.usernameTxt);
        btnback = view.findViewById(R.id.backBtn);

        //search button
        searchButton = view.findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                input = username.getEditText().getText().toString().trim();

                //if not empty
                if (Validation.validateEmpty(input, username)) {
                    username.setError(null);
                }
            }
        });

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

        return view;
    }
}
