package com.jefferystudio.bankingsimulator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class navigationdrawerheader extends Fragment {

    private ImageButton btnLogo;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.navi_drawer_header, container, false);

        btnLogo = view.findViewById(R.id.profileBtn);

        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                navigationdrawerheader navigationdrawerheaders = new navigationdrawerheader();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, navigationdrawerheaders);
                transaction.commit();
            }
        });


        return view;
    }
}
