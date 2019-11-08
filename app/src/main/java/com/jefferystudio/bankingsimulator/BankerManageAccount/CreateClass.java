package com.jefferystudio.bankingsimulator.BankerManageAccount;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

public class CreateClass extends Fragment {

    private TextInputLayout searchClass;
    private TextInputLayout interest;
    private Button createButton;
    private Button cancelButton;
    private Bundle args;
    private String inputClass, inputInt;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.class_create, container, false);

        searchClass = view.findViewById(R.id.classTxt);
        interest = view.findViewById(R.id.intRateTxt);

        //create button
        createButton = view.findViewById(R.id.createBtn);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if invalid class or interest rate found
                if (!validateClass() | !validateInt()) {

                    return;
                }
            }
        });

        //cancel button
        cancelButton = view.findViewById(R.id.cancelBtn);


        return view;
    }

    private boolean validateClass() {

        inputClass = searchClass.getEditText().getText().toString().trim();

        boolean result = Validation.validateEmpty(inputClass, searchClass);

        if (result) {
            searchClass.setError(null);
        }

        return result;
    }

    private boolean validateInt() {

        inputInt = interest.getEditText().getText().toString().trim();

        boolean result = Validation.validateAmount(inputInt, interest);

        if (result) {
            interest.setError(null);
        }

        return result;
    }
}