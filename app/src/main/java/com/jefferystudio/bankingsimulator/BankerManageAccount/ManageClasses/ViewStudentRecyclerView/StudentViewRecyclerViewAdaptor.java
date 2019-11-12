package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudentRecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.EditClassesAsync;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudent;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StudentViewRecyclerViewAdaptor extends RecyclerView.Adapter<StudentViewRecyclerViewAdaptor.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView userName;
        private TextView interestRate;
        private Button deleteButton;

        public ViewHolder(View view) {

            super(view);

            userName = view.findViewById(R.id.username);
            interestRate = view.findViewById(R.id.interestrate);
            deleteButton = view.findViewById(R.id.deleteBtn);
        }
    }

    private Context context;
    private ArrayList<StudentEntry> studentList;
    private String classID;

    public StudentViewRecyclerViewAdaptor(Context context, ArrayList<StudentEntry> studentList, String classID) {

        this.context = context;
        this.studentList = studentList;
        this.classID = classID;
    }

    @NonNull
    @Override
    public StudentViewRecyclerViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.student_view_recycler_view_layout, parent);
        ViewHolder viewHolder = new ViewHolder(rowView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final StudentEntry studentEntry = studentList.get(position);
        final int entryPosition = position;

        TextView textView1 = viewHolder.interestRate;
        textView1.setText(studentEntry.getInterestRate());


        TextView textView2 = viewHolder.userName;
        textView2.setText(studentEntry.getUsername());

        Button button = viewHolder.deleteButton;
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String msg = "Are you sure you want to delete '" + studentEntry.getUsername() + "' ?";

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Warning!");
                builder.setMessage(msg);

                //yes button selected
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String returnMessage = "";
                        try {

                            returnMessage = new EditClassesAsync(context, "DeleteStudent")
                                            .execute(classID, studentEntry.getUserID())
                                            .get(5000, TimeUnit.MILLISECONDS);
                        }
                        catch(Exception e) {

                        }

                        String[] resultArray = returnMessage.split(",");

                        if(resultArray[0].equals("Success")) {

                            studentList.remove(entryPosition);
                            ViewStudent viewStudentFrag = (ViewStudent) ((HomeScreenBanker) context)
                                    .getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                            viewStudentFrag.updateAdaptor(entryPosition);

                            Toast.makeText(context,
                                    studentEntry.getUsername() + " deleted",
                                    Toast.LENGTH_LONG).show();
                        }
                        else if(resultArray[0].equals("Fail")){

                            Toast.makeText(context,
                                    "Deleting of " + studentEntry.getUsername() + " failed.",
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

    public int getItemCount() {

        return studentList.size();
    }
}
