package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Registration.Registration;
import com.q42.android.scrollingimageview.ScrollingImageView;

public class PreLogin extends AppCompatActivity {

    private ImageButton buttonlogin;
    private ImageButton buttonsignup;
    private ScrollingImageView scrollimage;
    //private ImageButton buttontransfer;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
         setContentView(R.layout.loginadvance);

         buttonsignup =(ImageButton) findViewById(R.id.signup);
         buttonlogin = (ImageButton) findViewById(R.id.loginposb);
         scrollimage = (ScrollingImageView) findViewById(R.id.scrollingImageView);
         //buttontransfer = (ImageButton) findViewById(R.id.transferposb) ;

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


         /*
         buttontransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewtransfer) {
                LoginScreen();
            }
        });

          */
    }


    public void LoginScreen(){
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
        finish();
    }

    public void registration1(){
        Intent intent = new Intent(getApplicationContext(), Registration.class);
        startActivity(intent);
        finish();
    }
}
