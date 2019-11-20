package com.jefferystudio.bankingsimulator.Quiz.QuizStudentRecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.Quiz.QuizHistoryBankerScoreView;
import com.jefferystudio.bankingsimulator.R;

import java.util.List;

public class QuizStudentRecyclerViewAdaptor extends RecyclerView.Adapter<QuizStudentRecyclerViewAdaptor.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView ahName;
        public Button searchButton;

        public ViewHolder(View itemView) {

            super(itemView);

            ahName = itemView.findViewById(R.id.name);
            searchButton = itemView.findViewById(R.id.viewScores);
        }
    }

    private List<QuizStudentEntry>studentList;
    private Context context;
    private String userID;
    private String userName;

    public QuizStudentRecyclerViewAdaptor(Context context, List<QuizStudentEntry> studentList,
                                          String userID, String userName) {

        this.studentList = studentList;
        this.context = context;
        this.userID = userID;
        this.userName = userName;
    }

    @Override
    public QuizStudentRecyclerViewAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.quiz_student_recycler_row_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(QuizStudentRecyclerViewAdaptor.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final QuizStudentEntry student = studentList.get(position);
        final int entryPosition = position;

        TextView textView1 = viewHolder.ahName;
        textView1.setText(student.getStudentName());

        Button button = viewHolder.searchButton;
        button.setText("Edit");

        Button searchBtn = viewHolder.searchButton;

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("bankerID", userID);
                args.putString("bankerUsername", userName);
                args.putString("studentID", student.getStudentID());
                args.putString("studentName", student.getStudentName());

                Fragment scoreViewFrag = new QuizHistoryBankerScoreView();
                scoreViewFrag.setArguments(args);

                ((HomeScreenBanker) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, scoreViewFrag)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {

        return studentList.size();
    }
}
