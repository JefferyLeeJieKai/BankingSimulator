package com.jefferystudio.bankingsimulator.WithdrawalPackage;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateBalanceAsync;

public class WithdrawalAH extends Fragment
{
    private Bundle args;
    private String currentID;
    private TextView userID;
    private TextView userBalance;
    private TextInputLayout amountToWithdraw;
    private String input;
    private Button nextButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.withdraw_ah, container, false);

        args = getArguments();
        currentID = args.getString("userID");

        userID = view.findViewById(R.id.usernameLbl);
        userBalance = view.findViewById(R.id.balanceLbl);
        userID.setText(currentID);
        new UpdateBalanceAsync(getActivity(), userBalance).execute(currentID);

        amountToWithdraw = view.findViewById(R.id.amountTxt);
        nextButton = view.findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                input = amountToWithdraw.getEditText().getText().toString().trim();

                validateAmount(input);
            }
        });

        return view;
    }

    protected void validateAmount(String input) {

        if(input.isEmpty()) {

            amountToWithdraw.setError("Amount cannot be empty");
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
                amountToWithdraw.setError("Please enter the correct format");
            }
            else{

                Fragment withdrawalConfirmFrag = new WithdrawalConfirm();
                args.putString("amount", input);
                withdrawalConfirmFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, withdrawalConfirmFrag)
                        .commit();

                //new TransactionAsync(getActivity(),"WithdrawalUser").execute(currentID, input);
                //new UpdateTransAsync(getActivity(), "WithdrawFunds").execute(currentID, input);
            }
        }
        catch(NumberFormatException e) {

            amountToWithdraw.setError("Please enter the correct format");
        }
        catch(Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}