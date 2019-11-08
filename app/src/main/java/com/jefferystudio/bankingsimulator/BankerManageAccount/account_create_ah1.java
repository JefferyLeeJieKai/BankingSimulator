package com.jefferystudio.bankingsimulator.BankerManageAccount;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

public class account_create_ah1 extends Fragment {

    private TextInputLayout searchClass;
    private Button searchButton;
    private TextView classLabel;
    private TextView interest;
    private Button nextButton;
    private String input;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account_create_ah1, container, false);

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

                input = searchClass.getEditText().getText().toString().trim();

                //if not empty
                if(Validation.validateEmpty(input, searchClass)) {
                    searchClass.setError(null);

                    //draw out information from database
                    //testing purpose
                    classLabel.setText(searchClass.getEditText().getText().toString());
                    interest.setText("0.2");
                }
            }
        });

        //next button
        nextButton = view.findViewById(R.id.nextBtn);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                CreateAccountAH CreateAccountah = new CreateAccountAH();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, CreateAccountah);
                transaction.commit();
            }
        });

        return view;
    }
}
