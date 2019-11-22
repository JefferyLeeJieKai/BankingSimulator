package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClassRecyclerView.ClassViewRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.R;

import java.util.ArrayList;

public class ViewClassFragment extends Fragment {

    private Bundle args;
    private RecyclerView classDetails;
    private Button buttonback;

    private ArrayList<Class> classList;

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.class_view, container, false);

        args = getArguments();

        classDetails = view.findViewById(R.id.classDetailsRv);
        new ClassAsync(getActivity(), "ViewClassFragment", args.getString("userID"), null, null, classDetails).execute(args.getString("userID"));

        buttonback = view.findViewById(R.id.buttonback);

        buttonback.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment homeFragmentBanker = new HomeFragmentBanker();
                homeFragmentBanker.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, homeFragmentBanker);
                transaction.commit();
            }
        });


        return view;
    }

    public void updateAdaptor(int entryPosition) {

        ClassViewRecyclerViewAdaptor classListAdapter = (ClassViewRecyclerViewAdaptor)classDetails.getAdapter();
        classListAdapter.notifyItemRemoved(entryPosition);
        classListAdapter.notifyItemRangeChanged(entryPosition, classListAdapter.getItemCount());
    }
}
