package com.jefferystudio.bankingsimulator.BankNote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.BankNote.RedeemNotesRecyclerView.RedeemNotesRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateBalanceAsync;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.R;

public class RedeemBanknoteFragment extends Fragment {

    private Bundle args;
    private String userID;
    private TextView userName;
    private TextView balance;
    private RecyclerView notRedeemedNotes;
    private Button btnback;

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.redeem_banknote, container, false);

        args = getArguments();
        userID = args.getString("userID");

        userName = view.findViewById(R.id.usernameLbl);
        userName.setText(userID);

        balance = view.findViewById(R.id.balanceLbl);
        new UpdateBalanceAsync(getActivity(), balance, null).execute(userID);

        notRedeemedNotes = view.findViewById(R.id.detailsRv);

        new RetrieveNotesAsync(getActivity(), userID, "viewAccountHolder", notRedeemedNotes).execute();

        btnback = view.findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment homeFrag = new HomeFragmentUser();
                Bundle homeBundle = new Bundle();
                homeBundle.putString("userID", userID);
                homeFrag.setArguments(homeBundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, homeFrag)
                        .commit();
            }
        });

        return view;
    }

    public void updateAdaptor (int entryPosition) {

        RedeemNotesRecyclerViewAdaptor adaptor = (RedeemNotesRecyclerViewAdaptor)notRedeemedNotes.getAdapter();
        adaptor.notifyItemRemoved(entryPosition);
    }

    public void updateBalance() {

        new UpdateBalanceAsync(getActivity(), balance, null).execute(userID);
    }
}
