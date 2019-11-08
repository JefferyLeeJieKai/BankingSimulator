package com.jefferystudio.bankingsimulator.BankerManageAccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Transfer_Amount;
import com.jefferystudio.bankingsimulator.goalspage;

public class CreateClass extends Fragment {

    TextInputLayout getClass;
    TextInputLayout interest;
    private Button CancelBtn;
    private Bundle args;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.class_create, container, false);

        getClass = view.findViewById(R.id.classTxt);
        interest = view.findViewById(R.id.intRateTxt);
        CancelBtn = view.findViewById(R.id.cancelBtn);


        return view;
    }
}