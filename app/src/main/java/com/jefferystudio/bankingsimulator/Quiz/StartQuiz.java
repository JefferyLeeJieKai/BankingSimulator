package com.jefferystudio.bankingsimulator.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.R;

public class StartQuiz extends AppCompatActivity {

    private Button btnstartquiz;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_quiz);

        btnstartquiz = findViewById(R.id.button_start_quiz);

        btnstartquiz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startquiz();
            }
        });

    }

    private void startquiz() {
        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
        startActivity(intent);
    }
}