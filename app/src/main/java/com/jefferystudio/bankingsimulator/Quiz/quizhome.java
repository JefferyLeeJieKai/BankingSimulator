package com.jefferystudio.bankingsimulator.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsAll;

public class quizhome extends AppCompatActivity {

    private ImageButton btnstartq;
    private ImageButton btnviewhist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizhomepage);


        btnstartq = (ImageButton) findViewById(R.id.startquiz);
        btnviewhist = (ImageButton) findViewById(R.id.viewquiz);

        btnstartq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startquiz();
            }
        });

        btnviewhist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewhist();
            }
        });
    }

    public void startquiz(){
        Intent intent = new Intent(getApplicationContext(), StartQuiz.class);
        startActivity(intent);
    }

    public void viewhist(){
        Intent intent = new Intent(getApplicationContext(), QuizHistory.class);
        startActivity(intent);
    }
}
