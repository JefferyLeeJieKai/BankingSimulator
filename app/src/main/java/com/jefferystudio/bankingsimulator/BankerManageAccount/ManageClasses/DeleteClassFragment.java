package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

public class DeleteClassFragment extends Fragment {

    private TextInputLayout searchClass;
    private Button searchButton;
    private TextView classLabel;
    private TextView interest;
    private Button deleteButton;
    private Button cancelButton;
    private String input;
    private Bundle args;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.class_delete, container, false);

        args = getArguments();

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
                    classLabel.setText(input);
                    interest.setText("0.2");
                }
            }
        });

        //delete button
        deleteButton = view.findViewById(R.id.deleteBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strClass = classLabel.getText().toString();

                //if user did not select a class
                if (strClass.equals("NIL")) {
                    Toast.makeText(getActivity(),
                            "Please search for a class first",
                            Toast.LENGTH_SHORT).show();
                }
                //if user selected a class
                else {
                    String msg = "Are you sure you want to delete '" + strClass + "' ?";


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Warning!");
                    builder.setMessage(msg);

                    //yes button selected
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //delete class from database


                            //testing
                            Toast.makeText(getActivity(),
                                    "DELETED",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    //no button selected
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog ad = builder.create();
                    ad.show();
                }
            }
        });


        //cancel button
        cancelButton = view.findViewById(R.id.cancelBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
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