package com.jefferystudio.bankingsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsAll;

public class goalspage extends AppCompatActivity {

    private ImageButton btnViewGoals;
    private Button btnback;
    private Bundle args;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goalspage);

        btnViewGoals = (ImageButton) findViewById(R.id.viewallgoals);
        btnback = (Button) findViewById(R.id.buttonback);

        args = getIntent().getExtras();

        btnViewGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavingGoalsAll();
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
            }
        });
    }

    public void SavingGoalsAll(){
        Intent intent = new Intent(getApplicationContext(), SavingGoalsAll.class);
        startActivity(intent);
    }

}
