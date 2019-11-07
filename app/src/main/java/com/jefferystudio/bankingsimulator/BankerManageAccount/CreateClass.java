package com.jefferystudio.bankingsimulator.BankerManageAccount;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.R;

public class CreateClass extends Fragment {

    TextInputLayout getClass;
    TextInputLayout interest;
    Button createButton;
    Button cancelButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.class_create, container, false);

        getClass = view.findViewById(R.id.classTxt);
        interest = view.findViewById(R.id.intRateTxt);

        //create button
        createButton = view.findViewById(R.id.createBtn);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //do something

            }
        });

        //cancel button
        cancelButton = view.findViewById(R.id.cancelBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //do something

            }
        });

        return view;
    }
}