package com.jefferystudio.bankingsimulator.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.jefferystudio.bankingsimulator.R;

public class quizhomepage extends AppCompatActivity {

    private ImageButton btnstartquiz;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizhomepage);

        btnstartquiz = (ImageButton) findViewById(R.id.startquiz);

        btnstartquiz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startquiz();
            }
        });

    }

    private void startquiz() {
        Intent intent = new Intent(getApplicationContext(), StartQuiz.class);
        startActivity(intent);
    }
}

