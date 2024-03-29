package com.jefferystudio.bankingsimulator.BankNote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jefferystudio.bankingsimulator.BankNote.IssueNotesRecyclerView.IssueNotesRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.OTP.OTPFragment;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

import java.util.concurrent.TimeUnit;

public class IssueBanknoteFragment extends Fragment {

    private Bundle args;
    private String userID;
    private EditText accountholderCredsInput;
    private EditText twoDollarsInput;
    private EditText fiveDollarsInput;
    private EditText tenDollarsInput;
    private EditText fiftyDollarsInput;
    private Button issueBankNote;
    private Button backButton;
    private RecyclerView issuedNotesView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.issue_banknote, container, false);

        args = getArguments();
        userID = args.getString("userID");
        issuedNotesView = view.findViewById(R.id.detailsRv);

        new RetrieveNotesAsync(getActivity(), userID, "viewBanker", issuedNotesView).execute();

        accountholderCredsInput = view.findViewById(R.id.ahIDInput);
        twoDollarsInput = view.findViewById(R.id.twodollarinput);
        fiveDollarsInput = view.findViewById(R.id.fivedollarinput);
        tenDollarsInput = view.findViewById(R.id.tendollarinput);
        fiftyDollarsInput = view.findViewById(R.id.fiftydollarinput);

        issueBankNote = view.findViewById(R.id.issue);
        issueBankNote.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String accountholderCreds = accountholderCredsInput.getText().toString().trim();

                if(Validation.validateEmptyNonTextInputLayout(accountholderCreds)) {

                    String twodollars = twoDollarsInput.getText().toString().trim();
                    if (twodollars.length() == 0) {

                        twodollars = "0";
                    }

                    String fivedollars = fiveDollarsInput.getText().toString().trim();
                    if (fivedollars.length() == 0) {

                        fivedollars = "0";
                    }

                    String tendollars = tenDollarsInput.getText().toString().trim();
                    if (tendollars.length() == 0) {

                        tendollars = "0";
                    }

                    String fiftydollars = fiftyDollarsInput.getText().toString().trim();
                    if (fiftydollars.length() == 0) {

                        fiftydollars = "0";
                    }

                    float totalAmount = (2 * Float.valueOf(twodollars)) + (5 * Float.valueOf(fivedollars)) +
                            (10 * Float.valueOf(tendollars)) + (50 * Float.valueOf(fiftydollars));

                    args.putString("accountholderCreds", accountholderCreds);
                    args.putString("totalAmount", Float.toString(totalAmount));
                    args.putString("flag", "IssueNotes");

                    Fragment otpFrag = new OTPFragment();
                    otpFrag.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, otpFrag)
                            .commit();
                }
                else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("DigiBank Alert");
                    builder.setMessage("Please ensure that accountholder field is filled");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        backButton = view.findViewById(R.id.buttonback);
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment homeFrag = new HomeFragmentBanker();
                homeFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, homeFrag)
                        .commit();
            }
        });

        return view;
    }

    public void updateAdaptor(int currentEntry) {

        IssueNotesRecyclerViewAdaptor adaptor = (IssueNotesRecyclerViewAdaptor) issuedNotesView.getAdapter();
        adaptor.notifyItemRemoved(currentEntry);
    }
}
