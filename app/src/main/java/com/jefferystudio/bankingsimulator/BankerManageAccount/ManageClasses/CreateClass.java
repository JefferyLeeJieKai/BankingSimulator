package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

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

public class CreateClass extends Fragment {

    private Bundle args;
    private TextInputLayout searchClass;
    //private TextInputLayout interest;
    private Button createButton;
    private Button cancelButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.class_create, container, false);

        args = getArguments();

        searchClass = view.findViewById(R.id.classTxt);
        //interest = view.findViewById(R.id.intRateTxt);

        //create button
        createButton = view.findViewById(R.id.createBtn);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if invalid class or interest rate found
                if (!validateClass()) {

                    return;
                }
                else {

                    String className = searchClass.getEditText().getText().toString().trim();
                    String userID = args.getString("userID");
                    String username = args.getString("userName");

                    new ClassAsync(getActivity(), "AddClass", userID, username, null).execute(className);
                }
            }
        });

        //cancel button
        cancelButton = view.findViewById(R.id.cancelBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {

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

    //validations
    private boolean validateClass() {

        String input = searchClass.getEditText().getText().toString().trim();

        boolean result = Validation.validateEmpty(input, searchClass);

        if (result) {
            searchClass.setError(null);
        }

        return result;
    }

    /*private boolean validateInt() {

        String input = interest.getEditText().getText().toString().trim();

        boolean result = Validation.validateAmount(input, interest);

        if (result) {
            interest.setError(null);
        }

        return result;
    }*/
}