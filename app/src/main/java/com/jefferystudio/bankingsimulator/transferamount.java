package com.jefferystudio.bankingsimulator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class transferamount extends Fragment {

    private Button ButtonNext;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.transfer_amount, container, false);

        ButtonNext = view.findViewById(R.id.nextBtn);

        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                transferconfirm transferconfirmed = new transferconfirm();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, transferconfirmed);
                transaction.commit();
            }
        });

        return view;
    }
}
