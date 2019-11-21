package com.jefferystudio.bankingsimulator.Quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;

public class StartQuiz extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private Bundle args;
    private TextView textViewHighscore;
    private TextView textViewCurrScore;
    private int highscore;
    private Button btnstartquiz;
    private Button backButton;
    private String userID;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_quiz);

        args = getIntent().getExtras();
        userID = args.getString("userID");

        textViewHighscore = findViewById(R.id.HighScore);
        textViewCurrScore = findViewById(R.id.CurrScore);
        loadHighscore();

        btnstartquiz = findViewById(R.id.button_start_quiz);

        btnstartquiz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startquiz();
            }
        });

        backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), HomeScreenUser.class);
                intent.putExtras(args);
                startActivity(intent);
                finish();
            }
        });
    }

    private void startquiz() {
        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                showScore(score);
                if (score > highscore) {
                    updateHighscore(score);
                }

                new UpdateScoreAsync(this).execute(userID, Integer.toString(score));
            }
        }
    }

    private void showScore(int currscore) {
        textViewCurrScore.setText("Your score: " + currscore);
    }

    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore: " + highscore);
    }

    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighscore.setText("Highscore: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
}