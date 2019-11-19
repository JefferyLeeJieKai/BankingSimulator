package com.jefferystudio.bankingsimulator.TransferFundsPackage;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.CommonAsyncPackage.TransactionAsync;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateTransAsync;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

import java.util.concurrent.TimeUnit;

public class Transfer_Amount extends Fragment{

    private Bundle args;
    private String currentID;
    private String currentPayee;
    private TextView userID;
    private TextInputLayout payee;
    private TextInputLayout amountToTransfer;
    private String input;
    private Spinner purpose;
    private Button nextButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.transfer_amount, container, false);

        args = getArguments();
        currentID = args.getString("userID");

        userID = view.findViewById(R.id.payerLbl);
        userID.setText(currentID);

        amountToTransfer = view.findViewById(R.id.amountTxt);
        payee = view.findViewById(R.id.payeeLbl);
        nextButton = view.findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentPayee = payee.getEditText().getText().toString().trim();
                input = amountToTransfer.getEditText().getText().toString().trim();

                if(!validatePayee() | !validateAmount()) {

                    return;
                }

                String result = "";

                try {

                    result = new TransactionAsync(getActivity(), "TransferFundsUser", args.getString("userName"))
                                        .execute(currentID, currentPayee, input)
                                        .get(5000, TimeUnit.MILLISECONDS);
                }
                catch(Exception e) {

                }

                String[] resultArray = result.split(",");

                if(resultArray[0].equals("True")) {

                    new UpdateTransAsync(getActivity(), "TransferFunds").execute(currentID, input, currentPayee);
                }
            }
        });

        return view;
    }

    //validations
    private boolean validatePayee() {

        boolean result = Validation.validateEmpty(currentPayee, payee);

        //if not empty
        if (result) {

            payee.setError((null));
        }

        return result;
    }

    private boolean validateAmount() {

        boolean result = Validation.validateAmount(input, amountToTransfer);

        //if not empty
        if (result) {

            amountToTransfer.setError(null);
        }

        return result;
    }

    /*
    protected void validateAmount(String input) {

        if(input.isEmpty()) {

            amountToTransfer.setError("Amount cannot be empty");
        }

        try{

            float amount = Float.valueOf(input);
            int befDec = 0;
            int currentCount = 0;

            for(int i = 0; i < input.length(); i++) {

                if(input.charAt(i) == '.') {

                    befDec = currentCount;
                }

                ++currentCount;
            }

            if(input.length() - (befDec + 1) > 2) {
                amountToTransfer.setError("Please enter the correct format");
            }
            else{

                /*FragmentTransaction fragTransc = getChildFragmentManager().beginTransaction();
                fragTransc.replace(R.id.outer_frame, new DepositConfirmBanker());
                fragTransc.addToBackStack(null);
                fragTransc.commit();*/
        /*
                new TransactionAsync(getActivity(),"TransferFundsUser").execute(currentID, currentPayee, input);
                new UpdateTransAsync(getActivity(),"TransferFunds").execute(currentID, input, currentPayee, String.valueOf(purpose.getSelectedItem()));
            }
        }
        catch(NumberFormatException e) {

            amountToTransfer.setError("Please enter the correct format");
        }
        catch(Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    */
}
