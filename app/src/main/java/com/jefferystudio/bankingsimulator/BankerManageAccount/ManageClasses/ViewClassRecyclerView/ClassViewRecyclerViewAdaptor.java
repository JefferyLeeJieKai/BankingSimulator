package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClassRecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.EditClassFragment;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.EditClassesAsync;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClassFragment;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudentFragment;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class ClassViewRecyclerViewAdaptor extends RecyclerView.Adapter<ClassViewRecyclerViewAdaptor.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView className;
        private TextView noOfStudents;
        private Button viewStudents;
        private Button editClass;
        private Button deleteClass;

        public ViewHolder (View view) {

            super(view);

            className = view.findViewById(R.id.classname);
            noOfStudents = view.findViewById(R.id.numberOfStudents);
            viewStudents = view.findViewById(R.id.viewStudents);
            editClass = view.findViewById(R.id.editClass);
            deleteClass = view.findViewById(R.id.deleteClass);
        }
    }

    private Context context;
    private ArrayList<ClassEntry> classList;

    public ClassViewRecyclerViewAdaptor(Context context, ArrayList<ClassEntry> classArrayList) {

        this.context = context;
        this.classList = classArrayList;
    }

    @NonNull
    @Override
    public ClassViewRecyclerViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View rowView = inflater.inflate(R.layout.class_view_recycler_row_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClassViewRecyclerViewAdaptor.ViewHolder viewHolder, int position) {

        final ClassEntry classEntry = classList.get(position);
        final int entryPosition = position;

        // Set item views based on your views and data model
        TextView textView1 = viewHolder.className;
        textView1.setText(classEntry.getClassName());

        TextView textView2 = viewHolder.noOfStudents;
        String noOfStudents = "No. of students: " + classEntry.getNoOfStudents();
        textView2.setText(noOfStudents);

        Button editStudentsButton = viewHolder.viewStudents;
        editStudentsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("userID", classEntry.getUserID());
                args.putString("userName", classEntry.getUsername());
                args.putString("classID", classEntry.getClassID());
                args.putString("className", classEntry.getClassName());

                Fragment editStudents = new ViewStudentFragment();
                editStudents.setArguments(args);

                ((HomeScreenBanker)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, editStudents)
                        .commit();
            }
        });

        Button editButton = viewHolder.editClass;
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("userID", classEntry.getUserID());
                args.putString("userName", classEntry.getUsername());
                args.putString("classID", classEntry.getClassID());
                args.putString("className", classEntry.getClassName());

                Fragment editClass = new EditClassFragment();
                editClass.setArguments(args);

                ((HomeScreenBanker)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, editClass)
                        .commit();
            }
        });

        //delete button
        Button deleteBtn = viewHolder.deleteClass;

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = "Are you sure you want to delete " + classEntry.getClassName() + "?";

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Warning!");
                builder.setMessage(msg);

                //yes button selected
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                         String returnMessage = "";
                         try {

                             returnMessage = new EditClassesAsync(context, "DeleteClassFragment")
                                             .execute(classEntry.getClassID())
                                             .get(5000, TimeUnit.MILLISECONDS);

                             String[] returnMessageArray = returnMessage.split(",");

                             if(returnMessageArray[0].equals("Success")) {

                                 new EditClassesAsync(context, "DeleteAllStudents")
                                         .execute(classEntry.getClassID())
                                         .get(5000, TimeUnit.MILLISECONDS);
                             }
                         }
                         catch(Exception e) {


                         }

                         String[] returnArray = returnMessage.split(",");

                         if(returnArray[0].equals("Success")) {

                             classList.remove(entryPosition);

                             AlertDialog.Builder builder = new AlertDialog.Builder(context);
                             builder.setTitle("DigiBank Alert");
                             builder.setMessage(classEntry.getClassName() + " successfully deleted.");

                             builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                 public void onClick(DialogInterface dialogInterface, int i) {


                                     ViewClassFragment currentFrag = (ViewClassFragment) ((HomeScreenBanker) context).getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                                     currentFrag.updateAdaptor(entryPosition);
                                 }
                             });

                             AlertDialog confirmDialog = builder.create();
                             confirmDialog.show();
                         }
                         else if(returnArray[0].equals("Fail")){

                             Toast.makeText(context,
                                     "Deleting of " + classEntry.getClassName() + " failed.",
                                     Toast.LENGTH_LONG).show();
                         }
                    }
                });

                //no button selected
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return classList.size();
    }
}
