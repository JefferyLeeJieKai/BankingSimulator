package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

public class ViewStudent extends Fragment {

    private Bundle args;
    private TextInputLayout searchClass;
    private Button searchButton;
    private TextView classLabel;
    private TextView interest;
    private String input;
    private RecyclerView studentDetails;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.student_view, container, false);

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

        studentDetails = view.findViewById(R.id.ahDetailsRv);

        new ClassAsync(getActivity(), "ViewStudent", args.getString("userID"), args.getString("userName"), studentDetails).execute();

        return view;
    }
}
