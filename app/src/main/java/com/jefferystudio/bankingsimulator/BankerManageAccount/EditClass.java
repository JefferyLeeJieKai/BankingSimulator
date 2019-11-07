package com.jefferystudio.bankingsimulator.BankerManageAccount;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;

public class EditClass extends Fragment {

    TextInputLayout searchClass;
    Button searchButton;
    TextView classLabel;
    Button editClassButton;
    TextView interest;
    Button editIntButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.class_edit, container, false);

        //default
        classLabel = view.findViewById(R.id.classLbl);
        classLabel.setText("NIL");

        interest = view.findViewById(R.id.intRateLbl);
        interest.setText("NIL");

        searchClass = view.findViewById(R.id.classTxt);

        //search button
        searchButton = view.findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //draw out information from database
                //testing purpose
                classLabel.setText("1");
                interest.setText("0.2");

            }
        });

        /*
        //edit class button
        editClassButton = view.findViewById(R.id.editClassBtn);
        editClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                //do something

            }
        });

        //edit interest button
        editIntButton = view.findViewById(R.id.editIntBtn);
        editIntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {

                //do something

            }
        });
        */

        return view;
    }
}