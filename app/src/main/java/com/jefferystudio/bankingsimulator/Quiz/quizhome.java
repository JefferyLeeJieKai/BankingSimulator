package com.jefferystudio.bankingsimulator.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;

public class quizhome extends AppCompatActivity {

    private ImageButton btnstartq;
    private ImageButton btnviewhist;
    private Button btnback;
    private Bundle args;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizhomepage);

        args = getIntent().getExtras();
        btnstartq = (ImageButton) findViewById(R.id.startquiz);
        btnviewhist = (ImageButton) findViewById(R.id.viewquiz);
        btnback = (Button) findViewById(R.id.backbtn);

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

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle newArgs = new Bundle();
                newArgs.putString("userID", args.getString("userID"));
                newArgs.putString("userName", args.getString("userName"));
                Intent intent = new Intent(getApplicationContext(), HomeScreenUser.class);
                intent.putExtras(newArgs);
                startActivity(intent);
                finish();
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
