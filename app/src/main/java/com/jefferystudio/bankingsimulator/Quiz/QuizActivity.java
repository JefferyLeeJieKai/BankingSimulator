package com.jefferystudio.bankingsimulator.Quiz;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;

import java.util.List;

public class QuizActivity extends Fragment {

    private TextView textViewQuestion;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button buttonConfirmNext;

    private List<Question> questionList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_quiz, container, false);

        textViewQuestion = view.findViewById(R.id.text_view_question);
        textViewQuestionCount = view.findViewById(R.id.text_view_question_count);
        textViewCountDown = view.findViewById(R.id.text_view_countdown);
        rbGroup = view.findViewById(R.id.radio_group);
        rb1 = view.findViewById(R.id.radio_button1);
        rb2 = view.findViewById(R.id.radio_button2);
        rb3 = view.findViewById(R.id.radio_button3);
        buttonConfirmNext = view.findViewById(R.id.button_confirm_next);

        QuizDbHelper dbHelper = new QuizDbHelper((Context)getActivity());
        questionList = dbHelper.getAllQuestions();

        return view;
    }
}
