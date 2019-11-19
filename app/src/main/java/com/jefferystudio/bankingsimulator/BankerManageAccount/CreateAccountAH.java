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
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

import org.w3c.dom.Text;

import java.util.Calendar;

public class CreateAccountAH extends Fragment {

    private TextInputLayout name;
    private TextView dob;
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
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });

        phone = view.findViewById(R.id.phoneTxt);
        username = view.findViewById(R.id.usernameTxt);
        password = view.findViewById(R.id.passTxt);

        //gender



        //buttons
        createButton = view.findViewById(R.id.createBtn);

        /*
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if invalid class or interest rate found
                if (!validateName() | !validateDOB() | !validateUsername() | !validatePass()) {

                    return;
                }
            }
        });
        */

        cancelButton = view.findViewById(R.id.cancelBtn);

        return view;
    }

    //validations

    private boolean validateName() {

        String input = name.getEditText().getText().toString().trim();

        boolean result = Validation.validateEmpty(input, name);

        if (result) {
            name.setError(null);
        }

        return result;
    }

    private boolean validateDOB() {

        boolean result = true;

        String input = dob.getText().toString().trim();

        if (input.isEmpty()) {
            result = false;
        }
        else {
            dob.setError(null);
        }

        return result;
    }

    private boolean validateUsername() {

        String input = username.getEditText().getText().toString().trim();

        boolean result = Validation.validateEmpty(input, username);

        if (result) {
            username.setError(null);
        }

        return result;
    }

    private boolean validatePass() {

        String input = password.getEditText().getText().toString().trim();

        boolean result = Validation.validateEmpty(input, password);

        if (result) {
            password.setError(null);
        }

        return result;
    }
}


