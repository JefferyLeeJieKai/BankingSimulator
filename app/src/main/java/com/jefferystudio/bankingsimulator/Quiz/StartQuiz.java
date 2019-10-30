package com.jefferystudio.bankingsimulator.Quiz;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.R;

public class StartQuiz extends Fragment {

    private Button btnstartquiz;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.start_quiz, container, false);

        btnstartquiz = view.findViewById(R.id.button_start_quiz);

        btnstartquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                QuizActivity QuizActivity = new QuizActivity();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, QuizActivity);
                transaction.commit();
            }
        });

        return view;
    }
}