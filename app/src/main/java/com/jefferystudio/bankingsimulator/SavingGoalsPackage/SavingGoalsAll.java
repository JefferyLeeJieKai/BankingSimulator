package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClassRecyclerView.ClassViewRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView.SavingGoalsRecyclerViewAdaptor;

public class SavingGoalsAll extends Fragment {

    private Bundle args;
    private String currentUserID;
    private String currentUserName;
    private String currentBalance;
    private TextView userName;
    private TextView balance;
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstance) {

        View view = inflater.inflate(R.layout.saving_goals_all, container, false);

        args = getArguments();
        currentUserID = args.getString("userID");
        currentUserName = args.getString("userName");
        currentBalance = args.getString("currentBalance");

        userName = view.findViewById(R.id.usernameLbl);
        balance = view.findViewById(R.id.balanceLbl);
        recyclerView = view.findViewById(R.id.goalDetailsRv);

        recyclerView = view.findViewById(R.id.goalDetailsRv);
        new RetrieveSavingGoalsAsync(getActivity(), recyclerView).execute(currentUserID, currentUserName, currentBalance);

        userName.setText(currentUserName);
        balance.setText(currentBalance);

        return view;
    }

    public void updateAdaptor(int entryPosition) {

        SavingGoalsRecyclerViewAdaptor savingGoalsAdapter = (SavingGoalsRecyclerViewAdaptor)recyclerView.getAdapter();
        savingGoalsAdapter.notifyItemRemoved(entryPosition);
    }
}
