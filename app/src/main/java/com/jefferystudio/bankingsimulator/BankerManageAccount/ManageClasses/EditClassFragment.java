package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

import java.util.concurrent.TimeUnit;

public class EditClassFragment extends Fragment {

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

        classLabel = view.findViewById(R.id.classHeader);
        classLabel.setText("Current class name: " + currentClassName);

        newClassName = view.findViewById(R.id.classTxt);

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

                        displayConfirmationDialog(toChange, "EditClassName");
                    }
                }
            }
        });

        cancelButton = view.findViewById(R.id.cancelBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment viewClassFrag = new ViewClassFragment();
                viewClassFrag.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, viewClassFrag)
                        .commit();
            }
        });

        return view;
    }

    public void displayConfirmationDialog(final String input, final String flag) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Digibank Alert");

        builder.setMessage("Do you want to update class name to: " + input);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String result = "";

                try {
                    result = new EditClassesAsync(getActivity(), flag)
                            .execute(currentClassID, input)
                            .get(5000, TimeUnit.MILLISECONDS);
                }
                catch (Exception e) {


                }

                String[] resultArray = result.split(",");

                if(resultArray[0].equals("Success")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("DigiBank Alert");
                    builder.setMessage("Update success!");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            classLabel.setText("Current class name is: " + input);
                            newClassName.getEditText().getText().clear();
                        }
                    });

                    AlertDialog informDialog = builder.create();
                    informDialog.show();
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        AlertDialog confirmDialog = builder.create();
        confirmDialog.show();
    }
}