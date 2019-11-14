package com.jefferystudio.bankingsimulator.ProfileSettings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.PreLogin;
import com.jefferystudio.bankingsimulator.Quiz.quizhome;
import com.jefferystudio.bankingsimulator.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProfilePageBanker extends AppCompatActivity {

    private Bundle args;
    private ImageView profilepic;
    private TextView profilebtn;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private Button backButton;
    private Button confirmButton;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.profilepage_banker);

            Toolbar homeScreenToolbar = (Toolbar) findViewById(R.id.toolbar);
            homeScreenToolbar.setTitle("Profile");
            setSupportActionBar(homeScreenToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        profilebtn = (TextView)findViewById(R.id.editbtn);
        profilepic = (ImageView)findViewById(R.id.profilephoto);

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery1();
            }
        });

    }



    private void openGallery1(){
        Intent gallery1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery1, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            profilepic.setImageURI(imageUri);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
