package com.jefferystudio.bankingsimulator;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
    private TextView userID;
    private TextView userBalance;
    private TextInputLayout amountToDeposit;
    private String input;
    private Button nextButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.deposit_ah, container, false);

        args = getArguments();
        currentID = args.getString("userID");

        userID = view.findViewById(R.id.usernameLbl);
        userBalance = view.findViewById(R.id.balanceLbl);
        userID.setText(currentID);
        new UpdateBalanceAsync(getActivity(), userBalance).execute(currentID);

        amountToDeposit = view.findViewById(R.id.amountTxt);
        nextButton = view.findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                input = amountToDeposit.getEditText().getText().toString().trim();

                validateAmount(input);
            }
        });

        return view;
    }

    protected void validateAmount(String input) {

        if(input.isEmpty()) {

            amountToDeposit.setError("Amount cannot be empty");
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

            if(input.length() - (befDec + 1) != 2) {
                amountToDeposit.setError("Please enter the correct format");
            }
            else{

                Fragment depositConfirmFrag = new DepositConfirm();
                args.putString("amount", input);
                depositConfirmFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, depositConfirmFrag)
                        .commit();

                //new TransactionAsync(getActivity(),"DepositUser").execute(currentID, input);
                //new UpdateTransAsync(getActivity(), "DepositFunds").execute(currentID, input);
            }
        }
        catch(NumberFormatException e) {

            amountToDeposit.setError("Please enter the correct format");
        }
        catch(Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
