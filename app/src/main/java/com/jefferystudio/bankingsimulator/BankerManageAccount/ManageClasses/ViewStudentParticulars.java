package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;

import java.util.concurrent.TimeUnit;

public class ViewStudentParticulars extends Fragment {

    private Bundle args;
    private TextView currentID;
    private TextView currentName;
    private TextView currentUsername;
    private TextView currentEmail;
    private TextView dateCreated;
    private TextView currentBalance;
    private String currentUserID;
    private Button backButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_student_particulars,container, false);

        args = getArguments();

        currentID = view.findViewById(R.id.idLbl);
        currentID.setText(args.getString("studentID"));
        currentName = view.findViewById(R.id.nameLbl);
        currentUsername = view.findViewById(R.id.usernameLbl);
        currentEmail = view.findViewById(R.id.emailLbl);
        dateCreated = view.findViewById(R.id.dateLbl);
        currentBalance = view.findViewById(R.id.balanceLbl);

        String result = "";

        try {

            result = new getPersonalParticularsAsync(getActivity())
                         .execute(args.getString("studentID"))
                         .get(5000, TimeUnit.MILLISECONDS);
        }
        catch(Exception e) {


        }

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("Success")) {

            currentName.setText(resultArray[1]); ;
            currentUsername.setText(resultArray[2]);
            currentEmail.setText(resultArray[3]);
            dateCreated.setText(resultArray[4]); ;
            currentBalance.setText(resultArray[5]); ;
        }

        backButton = view.findViewById(R.id.buttonback);
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment viewStudentFrag = new ViewStudent();
                viewStudentFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                             .replace(R.id.frame_layout, viewStudentFrag)
                             .commit();
            }
        });




        return view;
    }
}
