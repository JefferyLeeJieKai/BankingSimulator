package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

public class EditClass extends Fragment {

    private Bundle args;
    private String currentUserID;
    private String currentUsername;
    private String currentClassID;
    private String currentClassName;
    private TextView classLabel;
    private TextInputLayout newClassName;
    private Button cancelButton;
    private Button confirmButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.class_edit, container, false);

        args = getArguments();

        currentUserID = args.getString("userID");
        currentUsername = args.getString("userName");
        currentClassID = args.getString("classID");
        currentClassName = args.getString("className");

        classLabel = view.findViewById(R.id.classLbl);
        classLabel.setText(currentClassName);

        newClassName = view.findViewById(R.id.classTxt);

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

        confirmButton = view.findViewById(R.id.confirmBtn);
        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String toChange = newClassName.getEditText().getText().toString().trim();

                if (Validation.validateEmpty(toChange, newClassName)) {

                    if (toChange.equalsIgnoreCase(currentClassName)) {

                        newClassName.setError("No changes made to class name");
                    }
                    else {

                        newClassName.setError(null);

                        //update here

                    }
                }
            }
        });

        return view;
    }
}