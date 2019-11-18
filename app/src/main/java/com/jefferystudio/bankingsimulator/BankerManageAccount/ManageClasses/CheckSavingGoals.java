package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.RetrieveSavingGoalsAsync;

public class CheckSavingGoals extends Fragment {

    private Bundle args;
    private String currentUserID;
    private Button backButton;
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstance) {

        View view = inflater.inflate(R.layout.check_saving_goals, container, false);

        args = getArguments();

        currentUserID = args.getString("studentID");

        recyclerView = view.findViewById(R.id.goalDetailsRv);
        new RetrieveSavingGoalsAsync(getActivity(), recyclerView, "Banker").execute(currentUserID, null, null);

        backButton = view.findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment studentViewFrag = new ViewStudent();
                studentViewFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                             .replace(R.id.frame_layout, studentViewFrag)
                             .commit();
            }
        });

        return view;
    }
}
