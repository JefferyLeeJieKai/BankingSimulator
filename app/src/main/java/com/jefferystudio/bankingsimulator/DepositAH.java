package com.jefferystudio.bankingsimulator;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DepositAH extends Fragment
{
    private Bundle args;
    private String currentID;
    private String currentBalance;
    private TextView userID;
    private TextView userBalance;
    private TextInputLayout amountToDeposit;
    private String input;
    private Button nextButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.deposit_ah, container, false);

        args = new Bundle();
        currentID = args.getString("userID");
        currentBalance = args.getString("currentBalance");

        userID = view.findViewById(R.id.usernameLbl);
        userBalance = view.findViewById(R.id.balanceLbl);
        userID.setText(currentID);
        userBalance.setText(currentBalance);

        amountToDeposit = view.findViewById(R.id.amountTxt);
        nextButton = view.findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                input = amountToDeposit.getEditText().getText().toString().trim();

                if(!validateAmount(input)) {

                }
                else{


                }
            }
        });

        return view;
    }

    public boolean validateAmount(String input) {

        if(input.isEmpty()) {

            amountToDeposit.setError("Amount cannot be empty");
            return false;
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
                amountToDeposit.setError("Please enter the correct format");
            }
        }
        catch(NumberFormatException e) {

            amountToDeposit.setError("Please enter the correct format");
            return false;
        }
        catch(Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }

        return true;
    }

}
