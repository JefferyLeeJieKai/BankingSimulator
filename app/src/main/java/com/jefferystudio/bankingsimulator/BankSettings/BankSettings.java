package com.jefferystudio.bankingsimulator.BankSettings;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.R;

public class BankSettings extends Fragment {

    private TextView currency;
    private TextView limit;
    private Button editLimitButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bank_settings, container, false);

        currency = view.findViewById(R.id.currencyLbl);

        limit = view.findViewById(R.id.limitLbl);
        //by default
        //if there is any changes made, draw changed amount from database
        limit.setText("2000");

        editLimitButton = view.findViewById(R.id.editLimitBtn);

        editLimitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDialogAction("New Daily Limit: ", "Daily Limit");

            }
        });

        return view;
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
