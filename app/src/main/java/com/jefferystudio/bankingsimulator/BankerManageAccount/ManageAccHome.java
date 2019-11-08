package com.jefferystudio.bankingsimulator.BankerManageAccount;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jefferystudio.bankingsimulator.R;

public class ManageAccHome extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageaccount);

        Toolbar homeScreenToolbar = (Toolbar) findViewById(R.id.toolbar);
        homeScreenToolbar.setTitle("Manage Account");
        setSupportActionBar(homeScreenToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
