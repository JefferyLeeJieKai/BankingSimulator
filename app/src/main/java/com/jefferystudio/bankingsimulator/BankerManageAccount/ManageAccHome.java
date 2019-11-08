package com.jefferystudio.bankingsimulator.BankerManageAccount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jefferystudio.bankingsimulator.R;

public class ManageAccHome extends Fragment{

    private ImageButton btnCreate;
    private Bundle args;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.manageaccount, container, false);

        btnCreate = view.findViewById(R.id.createclassbtn);


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment CreateClass1 = new CreateClass();
                CreateClass1.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, CreateClass1);
                transaction.commit();
            }
        });

        return view;
    }
}
