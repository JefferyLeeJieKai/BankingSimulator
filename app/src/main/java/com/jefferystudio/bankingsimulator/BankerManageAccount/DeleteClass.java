package com.jefferystudio.bankingsimulator.BankerManageAccount;

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

import com.jefferystudio.bankingsimulator.R;

public class DeleteClass extends Fragment {

    TextInputLayout searchClass;
    Button searchButton;
    TextView classLabel;
    TextView interest;
    Button deleteButton;
    Button cancelButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.class_delete, container, false);

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

        //delete button
        deleteButton = view.findViewById(R.id.deleteBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = "Are you sure you want to delete '" + classLabel + "' ?";

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Warning!");
                builder.setMessage(msg);

                //yes button selected
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //delete saving goal from database



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