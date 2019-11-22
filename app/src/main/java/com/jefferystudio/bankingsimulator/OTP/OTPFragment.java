package com.jefferystudio.bankingsimulator.OTP;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage.UserSettings;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.TransferFundsPackage.TransferAmountFragment;
import com.jefferystudio.bankingsimulator.TransferFundsPackage.TransferConfirmFragment;
import com.jefferystudio.bankingsimulator.WithdrawalPackage.WithdrawalAHFragment;
import com.jefferystudio.bankingsimulator.WithdrawalPackage.WithdrawalConfirmFragment;

public class OTPFragment extends Fragment {

    private Bundle args;
    private TextInputLayout verificationCodeLayout;
    private String actualVerificationCode;
    private String verificationCode;
    private Button confirmButton;
    private Button cancelButton;
    //private TextView error;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.otp_page ,container, false);

        args = getArguments();

        verificationCodeLayout = view.findViewById(R.id.verificationCode);

        confirmButton = view.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                verificationCode = verificationCodeLayout.getEditText().getText().toString().trim();

                if(verificationCode.equals(actualVerificationCode)) {

                    verificationCodeLayout.setError(null);

                    if(args.getString("flag").equals("Withdraw")) {

                        Fragment withdrawalConfirmFrag = new WithdrawalConfirmFragment();
                        withdrawalConfirmFrag.setArguments(args);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, withdrawalConfirmFrag)
                                .commit();
                    }
                    else if(args.getString("flag").equals("Transfer")) {

                        Fragment transferFrag = new TransferConfirmFragment();
                        transferFrag.setArguments(args);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, transferFrag)
                                .commit();
                    }
                    else if(args.getString("flag").equals("ChangeLimit")) {

                        Intent intent = new Intent(getActivity(), UserSettings.class);
                        intent.putExtras(args);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                    }
                }
                else {

                    verificationCodeLayout.setError("Please enter the correct code.");
                }
            }
        });
        //error = view.findViewById(R.id.errorCode);

        cancelButton = view.findViewById(R.id.cancelBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(args.getString("flag").equals("Withdraw")) {

                    Fragment withdrawalConfirmFrag = new WithdrawalAHFragment();
                    withdrawalConfirmFrag.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, withdrawalConfirmFrag)
                            .commit();
                }
                else if(args.getString("flag").equals("Transfer")) {

                    Fragment transferFrag = new TransferAmountFragment();
                    transferFrag.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, transferFrag)
                            .commit();
                }
            }
        });

        new OTPAsync(getActivity(), args.getString("accountType")).execute(args.getString("userID"));
        return view;
    }

    public void getActualVerifcationCode(String receivedCode) {

        actualVerificationCode = receivedCode.substring(0,6);
    }
}
