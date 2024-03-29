package com.jefferystudio.bankingsimulator.OTP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.BankNote.IssueBanknoteFragment;
import com.jefferystudio.bankingsimulator.BankNote.IssueNotesAsync;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
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

                        Intent intent = new Intent();
                        args.putString("accountType", "AccountHolder");
                        intent.putExtras(args);
                        getActivity().setResult(Activity.RESULT_OK,intent);
                        getActivity().finish();
                    }
                    else if(args.getString("flag").equals("IssueNotes")) {

                        new IssueNotesAsync(getActivity(), args.getString("userID"),
                                args.getString("accountholderCreds"), args.getString("totalAmount"))
                                .execute(args.getString("userName"));
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
                else if(args.getString("flag").equals("ChangeLimit")) {

                    Intent intent = new Intent();
                    args.putString("accountType", "AccountHolder");
                    intent.putExtras(args);
                    getActivity().setResult(Activity.RESULT_CANCELED,intent);
                    getActivity().finish();
                }
                else if(args.getString("flag").equals("IssueNotes")) {

                    Fragment issueNotesFrag = new IssueBanknoteFragment();
                    issueNotesFrag.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, issueNotesFrag)
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
