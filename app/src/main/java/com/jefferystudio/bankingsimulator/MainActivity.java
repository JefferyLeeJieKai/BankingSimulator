package com.jefferystudio.bankingsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton buttonlogin;
    private ImageButton buttonsignup;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
         setContentView(R.layout.loginadvance);

         buttonlogin = (ImageButton) findViewById(R.id.loginposb);
         buttonlogin.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){
                 LoginScreen();
             }
         });
         buttonsignup =(ImageButton) findViewById(R.id.signup);
         buttonsignup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 registrationpage1();
             }
         });
    }

    public void LoginScreen(){
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        finish();
    }

    public void registrationpage1(){
        Intent intent = new Intent(getApplicationContext(), registrationpage1.class);
        startActivity(intent);
        finish();
    }
}
