package com.jefferystudio.bankingsimulator.Quiz.QuizScoreRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;

import java.util.List;

public class QuizScoreRecyclerViewAdapter extends RecyclerView.Adapter<QuizScoreRecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTaken;
        public TextView name;
        public TextView score;

        public ViewHolder(View itemView) {

            super(itemView);

            dateTaken = itemView.findViewById(R.id.dateTaken);
            name = itemView.findViewById(R.id.name);
            score = itemView.findViewById(R.id.score);
        }
    }

    private List<QuizEntry> quizList;
    private Context context;

    public QuizScoreRecyclerViewAdapter(Context context, List<QuizEntry> quizList) {

        this.quizList = quizList;
        this.context = context;
    }

    @Override
    public QuizScoreRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.quiz_score_recycler_row_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(QuizScoreRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final QuizEntry quiz = quizList.get(position);
        final int entryPosition = position;

        TextView textView1 = viewHolder.dateTaken;
        textView1.setText(quiz.getTimeTaken());

        TextView textView2 = viewHolder.name;
        textView2.setText(quiz.getStudentName());

        TextView textView3 = viewHolder.score;
        textView3.setText(quiz.getScore());
    }

    @Override
    public int getItemCount() {

        return quizList.size();
    }
}

