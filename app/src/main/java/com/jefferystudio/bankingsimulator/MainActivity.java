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
    private ImageButton buttontransfer;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
         setContentView(R.layout.loginadvance);

         buttonsignup =(ImageButton) findViewById(R.id.signup);
         buttonlogin = (ImageButton) findViewById(R.id.loginposb);
         buttontransfer = (ImageButton) findViewById(R.id.transferposb) ;

         buttonlogin.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){
                 LoginScreen();
             }
         });

         buttonsignup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 registration1();
             }
         });

        buttontransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewtransfer) {
                LoginScreen();
            }
        });
    }

    public void LoginScreen(){
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        finish();
    }

    public void registration1(){
        Intent intent = new Intent(getApplicationContext(), registration1.class);
        startActivity(intent);
        finish();
    }
}
