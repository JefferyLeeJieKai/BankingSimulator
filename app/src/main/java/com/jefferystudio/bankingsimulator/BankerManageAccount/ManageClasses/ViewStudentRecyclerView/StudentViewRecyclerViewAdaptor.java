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

import com.jefferystudio.bankingsimulator.R;

import java.util.ArrayList;
import java.util.List;

public class StudentViewRecyclerViewAdaptor extends RecyclerView.Adapter<StudentViewRecyclerViewAdaptor.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView userName;
        private TextView userID;
        private Button deleteButton;

        public ViewHolder(View view) {

            super(view);

            userName = view.findViewById(R.id.username);
            userID = view.findViewById(R.id.userid);
            deleteButton = view.findViewById(R.id.deleteBtn);
        }
    }

    private Context context;
    private ArrayList<StudentEntry> studentList;

    public StudentViewRecyclerViewAdaptor(Context context, ArrayList<StudentEntry> studentList) {

        this.context = context;
        this.studentList = studentList;
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int postion) {

        final StudentEntry studentEntry = studentList.get(postion);

        TextView textView1 = viewHolder.userID;
        textView1.setText(studentEntry.getUserID());


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

                        //delete saving goal from database



                        //testing
                        Toast.makeText(context,
                                studentEntry.getUsername() + " deleted",
                                Toast.LENGTH_LONG).show();
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
