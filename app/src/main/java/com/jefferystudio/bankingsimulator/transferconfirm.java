package com.jefferystudio.bankingsimulator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;

public class transferconfirm extends Fragment {

    private Button BtnCancel;
    private Button BtnConfirm;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.transfer_confirm, container, false);

        BtnCancel = view.findViewById(R.id.nextBtn);
        BtnConfirm = view.findViewById(R.id.confirmBtn);

        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                HomeFragmentUser homeFragmentsUser = new HomeFragmentUser();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, homeFragmentsUser);
                transaction.commit();
            }
        });

        BtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                HomeFragmentUser homeFragmentsUser = new HomeFragmentUser();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, homeFragmentsUser);
                transaction.commit();
            }
        });

        return view;
    }
}
