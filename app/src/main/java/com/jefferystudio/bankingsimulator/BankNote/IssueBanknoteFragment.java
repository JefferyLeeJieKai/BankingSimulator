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

                    String result = "";

                    try {

                        result = new IssueNotesAsync(getActivity(), userID, accountholderCreds,
                                                     Float.toString(totalAmount))
                                 .execute()
                                .get(5000, TimeUnit.MILLISECONDS);
                    }
                    catch(Exception e) {

                    }

                    String[] resultArray = result.split(",");

                    if(resultArray[0].equals("Success")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("DigiBank Alert");
                        builder.setMessage("Notes are issued successfully.\n" +
                                           "Do you want to issue another set?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                accountholderCredsInput.getText().clear();
                                twoDollarsInput.getText().clear();
                                fiveDollarsInput.getText().clear();
                                tenDollarsInput.getText().clear();
                                fiftyDollarsInput.getText().clear();

                                new RetrieveNotesAsync(getActivity(), userID, "viewBanker", issuedNotesView).execute();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialogInterface, int i) {

                                Bundle newArgs = new Bundle();
                                newArgs.putString("userID", args.getString("userID"));
                                newArgs.putString("userName", args.getString("userName"));

                                Fragment homeFrag = new HomeFragmentBanker();
                                homeFrag.setArguments(newArgs);
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout, homeFrag)
                                        .commit();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else if(resultArray[0].equals("Fail")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("DigiBank Alert");
                        builder.setMessage(resultArray[1]);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                accountholderCredsInput.getText().clear();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
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

        return view;
    }

    public void updateAdaptor(int currentEntry) {

        IssueNotesRecyclerViewAdaptor adaptor = (IssueNotesRecyclerViewAdaptor) issuedNotesView.getAdapter();
        adaptor.notifyItemRemoved(currentEntry);
    }
}
