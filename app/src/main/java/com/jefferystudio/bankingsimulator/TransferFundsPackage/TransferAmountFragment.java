package com.jefferystudio.bankingsimulator.TransferFundsPackage;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateBalanceAsync;
import com.jefferystudio.bankingsimulator.OTP.OTPFragment;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransferAmountFragment extends Fragment{

    private Bundle args;
    private String currentID;
    private String currentUser;
    private String currentPayee;
    private TextView userID;
    private TextView currentLimit;
    private TextInputLayout payee;
    private TextInputLayout amountToTransfer;
    private String input;
    private Button nextButton;
    private CircleImageView profilePic;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.transfer_amount, container, false);

        args = getArguments();
        currentID = args.getString("userID");
        currentUser = args.getString("userName");

        userID = view.findViewById(R.id.payerLbl);
        userID.setText(currentID);

        currentLimit = view.findViewById(R.id.remainingLimitLbl);
        new UpdateBalanceAsync(getActivity(), null, currentLimit).execute(currentID);

        amountToTransfer = view.findViewById(R.id.amountTxt);
        payee = view.findViewById(R.id.payeeLbl);

        profilePic = view.findViewById(R.id.payeeAvatar);
        SharedPreferences pref = getActivity().getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
        if(!pref.getString("imageLink", "NotFound").equals("NoImage")) {

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(pref.getString("imageLink", "NotFound"), profilePic);
        }

        nextButton = view.findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentPayee = payee.getEditText().getText().toString().trim();
                input = amountToTransfer.getEditText().getText().toString().trim();

                new CheckUserExistsAsync(getActivity(), payee).execute(currentPayee);
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

    private boolean validateLimit() {

        boolean result = Validation.validateLimit(input, amountToTransfer, currentLimit.getText().toString());

        //if not empty
        if (result) {

            amountToTransfer.setError(null);
        }

        return result;
    }

    public void performCheck() {

        if(!validatePayee() | !validateAmount() || !validateLimit()) {

            return;
        }
        else {

            args.putString("flag", "Transfer");
            args.putString("payee", currentPayee);
            args.putString("input", input);
            Fragment otpFrag = new OTPFragment();
            otpFrag.setArguments(args);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, otpFrag)
                    .commit();
        }
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
                fragTransc.replace(R.id.outer_frame, new DepositConfirmBankerFragment());
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
