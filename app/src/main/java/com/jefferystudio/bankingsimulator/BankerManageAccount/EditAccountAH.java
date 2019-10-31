package com.jefferystudio.bankingsimulator.BankerManageAccount;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.R;

import java.util.Calendar;

public class EditAccountAH extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account_edit_ah, container, false);

        //name button
        Button nameDialog = view.findViewById(R.id.editNameBtn);

        nameDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialogAction("New Name: ", "Name");
            }
        });

        //date of birth button
        Button dobDialog = view.findViewById(R.id.editDOBBtn);

        dobDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDOBDialogAction("New Date Of Birth: ", "Select Date");
            }
        });

        //phone button
        Button phoneDialog = view.findViewById(R.id.editPhoneBtn);

        phoneDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialogAction("New Phone: ", "Phone Number");
            }
        });

        //username button
        Button usernameDialog = view.findViewById(R.id.editUsernameBtn);

        usernameDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialogAction("New Username: ", "Username");
            }
        });

        //password button
        Button passDialog = view.findViewById(R.id.editPassBtn);

        passDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialogAction("New Password: ", "Password");
            }
        });

        //gender button






        return view;
    }

    public void getDOBDialogAction(String label, String hint)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getLayoutInflater().inflate(R.layout.edit_dialog, null);

        //set label and hint accordingly
        final TextView inputLabel = v.findViewById(R.id.inputLbl);
        inputLabel.setText(label);

        final EditText etInput = v.findViewById(R.id.input);
        etInput.setHint(hint);

        //get date
        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        etInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String strDate = day + "/" + month + "/" + year;
                        etInput.setText(strDate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        //confirm button
        Button confirm = v.findViewById(R.id.confirmBtn);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!etInput.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),
                            etInput.getText().toString(),
                            Toast.LENGTH_SHORT).show();

                    //do something
                }
                else {
                    Toast.makeText(getActivity(),
                            "Please fill in the empty field",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //cancel button
        Button cancel = v.findViewById(R.id.cancelBtn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //do something
            }
        });

        builder.setView(v);
        AlertDialog ad = builder.create();
        ad.show();
    }

    public void getDialogAction(String label, String hint)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getLayoutInflater().inflate(R.layout.edit_dialog, null);

        //set label and hint accordingly
        final TextView inputLabel = v.findViewById(R.id.inputLbl);
        inputLabel.setText(label);

        final EditText etInput = v.findViewById(R.id.input);
        etInput.setHint(hint);

        //confirm button
        Button confirm = v.findViewById(R.id.confirmBtn);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!etInput.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),
                            etInput.getText().toString(),
                            Toast.LENGTH_SHORT).show();

                    //do something
                }
                else {
                    Toast.makeText(getActivity(),
                            "Please fill in the empty field",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //cancel button
        Button cancel = v.findViewById(R.id.cancelBtn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //do something
            }
        });

        builder.setView(v);
        AlertDialog ad = builder.create();
        ad.show();
    }
}
