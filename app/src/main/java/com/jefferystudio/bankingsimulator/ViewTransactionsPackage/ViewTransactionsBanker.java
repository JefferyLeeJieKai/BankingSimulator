package com.jefferystudio.bankingsimulator.ViewTransactionsPackage;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

public class ViewTransactionsBanker extends Fragment {

    private TextInputLayout username;
    private Button searchButton;
    String input;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewtransactions_banker, container, false);

        username = view.findViewById(R.id.usernameTxt);

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

        return view;
    }
}
