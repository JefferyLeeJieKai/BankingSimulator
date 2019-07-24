package com.jefferystudio.bankingsimulator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PerformTransactionFragment extends Fragment {

    private int amountToTransact;
    private String accountNo;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private String[] userAccounts;
    private String[] payeeList;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.perform_transaction_fragment, container, false);

        userAccounts = new String[1];
        userAccounts[0] = "Jeffery 20827562";

        payeeList = new String[2];
        payeeList[0] = "John 20129512";
        payeeList[1] = "Alice 20429210";

        fromSpinner = view.findViewById(R.id.fromSpinner);
        ArrayAdapter<String> fromSpinnerArrayAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_text, userAccounts);
        fromSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
        fromSpinner.setAdapter(fromSpinnerArrayAdapter);

        toSpinner = view.findViewById(R.id.toSpinner);
        ArrayAdapter<String> toSpinnerArrayAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_text, payeeList);
        toSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
        toSpinner.setAdapter(toSpinnerArrayAdapter);

        Bundle args = getArguments();

        if(args.getInt("Status") > 0) {

            toSpinner.setEnabled(false);
            toSpinner.setAlpha(0.1f);
            TextView text1 = view.findViewById(R.id.from);
            TextView text2 = view.findViewById(R.id.to);
            EditText text3 = view.findViewById(R.id.amount);
            text2.setAlpha(0.1f);

            if(args.getInt("Status") == 2) {

                text1.setText("Withdraw from");
                text2.setHint("Amount to withdraw");
            }
            else if(args.getInt("Status") == 1) {

                text1.setText("Desposit To");
                text2.setHint("Amount to deposit");
            }
        }

        return view;
    }
}
