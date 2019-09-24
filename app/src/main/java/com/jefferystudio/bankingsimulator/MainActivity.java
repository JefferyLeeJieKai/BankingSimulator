package com.jefferystudio.bankingsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton buttonlogin;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
         setContentView(R.layout.loginadvance);

         buttonlogin = (ImageButton) findViewById(R.id.loginposb);
         buttonlogin.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){
                 new LoginScreen();
             }
         });
    }

    public void LoginScreen(){
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        finish();
    }
}
