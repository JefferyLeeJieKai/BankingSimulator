package com.jefferystudio.bankingsimulator.OTP;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.DepositPackage.DepositConfirmUserFragment;
import com.jefferystudio.bankingsimulator.R;

public class OTPFragment extends Fragment {

    private Bundle args;
    private TextInputLayout verificationCodeLayout;
    private String actualVerificationCode;
    private String verificationCode;
    private Button confirmButton;
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

                    Fragment depositConfirmFrag = new DepositConfirmUserFragment();
                    depositConfirmFrag.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, depositConfirmFrag)
                            .commit();
                }
            }
        });
        //error = view.findViewById(R.id.errorCode);
        new OTPAsync(getActivity(), args.getString("accountType")).execute(args.getString("userID"));
        return view;
    }

    public void getActualVerifcationCode(String receivedCode) {

        actualVerificationCode = receivedCode.substring(0,6);
    }
}
