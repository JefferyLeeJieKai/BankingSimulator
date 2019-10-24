package com.jefferystudio.bankingsimulator.BankerManageAccount;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import com.jefferystudio.bankingsimulator.R;

import java.util.Calendar;

public class CreateAccountAH extends Fragment {

    private TextInputLayout name;
    private EditText dob;
    private TextInputLayout phone;
    private TextInputLayout username;
    private TextInputLayout password;

    private Button createButton;
    private Button cancelButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account_create_ah2, container, false);

        name = view.findViewById(R.id.nameTxt);

        //date of birth
        dob = view.findViewById(R.id.DOB);

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String strDate = day + "/" + month + "/" + year;
                        dob.setText(strDate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        phone = view.findViewById(R.id.phoneTxt);
        username = view.findViewById(R.id.usernameTxt);
        password = view.findViewById(R.id.passTxt);

        //gender



        //buttons
        createButton = view.findViewById(R.id.createBtn);
        cancelButton = view.findViewById(R.id.cancelBtn);

        return view;
    }
}


