package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jefferystudio.bankingsimulator.R;

import java.util.ArrayList;

public class ViewClass extends Fragment {

    private Bundle args;
    private RecyclerView classDetails;
    private ArrayList<Class> classArrayList;

    public View onCreateView (LayoutInflater layout, ViewGroup container, Bundle savedInstanceState) {

        View view = layout.inflate(R.layout.class_view, container, false);

        args = getArguments();

        classDetails = view.findViewById(R.id.classDetailsRv);
        new ClassAsync(getActivity(), "ViewClass", args.getString("userID"), null, classDetails).execute(args.getString("userID"));

        return view;
    }

    public void getArrayListFromAsync(ArrayList<Class> classArrayList) {

        this.classArrayList = classArrayList;
    }
}
