package com.jefferystudio.bankingsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsAll;

public class goalspage extends AppCompatActivity {

    private ImageButton btnViewGoals;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goalspage);

        Toolbar homeScreenToolbar = (Toolbar) findViewById(R.id.toolbar);
        homeScreenToolbar.setTitle("Goal");
        setSupportActionBar(homeScreenToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnViewGoals = (ImageButton) findViewById(R.id.viewSavingGoals);
        btnViewGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavingGoalsAll();
            }
        });
    }

    public void SavingGoalsAll(){
        Intent intent = new Intent(getApplicationContext(), SavingGoalsAll.class);
        startActivity(intent);
    }



    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
