package com.jefferystudio.bankingsimulator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class goalspage extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                               @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.goalspage, container, false);


        return view;
    }
}