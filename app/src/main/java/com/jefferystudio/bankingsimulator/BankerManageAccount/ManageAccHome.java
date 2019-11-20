package com.jefferystudio.bankingsimulator.BankerManageAccount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.CreateClassFragment;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.DeleteClassFragment;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.EditClassFragment;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudentFragment;
import com.jefferystudio.bankingsimulator.R;

public class ManageAccHome extends Fragment{

    private ImageButton btnCreate;
    private ImageButton btnDelete;
    private ImageButton btnEdit;
    private ImageButton btnView;

    private Bundle args;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.manageaccount, container, false);

        btnCreate = view.findViewById(R.id.createclassbtn);
        btnDelete = view.findViewById(R.id.deleteclassbtn);
        btnEdit = view.findViewById(R.id.editclassbtn);
        btnView = view.findViewById(R.id.viewclassbtn);


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment CreateClass1 = new CreateClassFragment();
                CreateClass1.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, CreateClass1);
                transaction.commit();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment DeleteClass1 = new DeleteClassFragment();
                DeleteClass1.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, DeleteClass1);
                transaction.commit();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment EditClass1 = new EditClassFragment();
                EditClass1.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, EditClass1);
                transaction.commit();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment ViewClass1 = new ViewStudentFragment();
                ViewClass1.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, ViewClass1);
                transaction.commit();
            }
        });

        return view;
    }
}
