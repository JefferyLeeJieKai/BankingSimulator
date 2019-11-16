package com.jefferystudio.bankingsimulator.BankNote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jefferystudio.bankingsimulator.R;

public class IssueBanknoteFragment extends Fragment {

    private Bundle args;
    private String userID;
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

        twoDollarsInput = view.findViewById(R.id.twodollarinput);
        fiveDollarsInput = view.findViewById(R.id.fivedollarinput);
        tenDollarsInput = view.findViewById(R.id.tendollarinput);
        fiftyDollarsInput = view.findViewById(R.id.fiftydollarinput);
        issuedNotesView = view.findViewById(R.id.detailsRv);

        issueBankNote = view.findViewById(R.id.issue);
        issueBankNote.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String twodollars = twoDollarsInput.getText().toString().trim();
                String fivedollars = fiveDollarsInput.getText().toString().trim();
                String tendollars = tenDollarsInput.getText().toString().trim();
                String fiftydollars = fiftyDollarsInput.getText().toString().trim();

                float totalAmount = (2 * Float.valueOf(twodollars)) + (5 * Float.valueOf(fivedollars)) +
                                    (10 * Float.valueOf(tendollars)) + (50 * Float.valueOf(fiftydollars));



            }
        });

        new RetrieveNotesAsync(getActivity(), userID, "viewBanker", issuedNotesView).execute();
        return view;
    }
}
