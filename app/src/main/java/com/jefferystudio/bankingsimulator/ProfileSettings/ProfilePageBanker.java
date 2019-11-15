package com.jefferystudio.bankingsimulator.ProfileSettings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.jefferystudio.bankingsimulator.R;


public class ProfilePageBanker extends AppCompatActivity {

    private Bundle args;
    private Context context;
    private ProgressBar progressBar;
    private ImageView profilepic;
    private TextView profilebtn;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private Bitmap bitmap;
    private Button backButton;
    private Button confirmButton;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.profilepage_banker);
            args = getIntent().getExtras();
            context = this;

            Toolbar homeScreenToolbar = (Toolbar) findViewById(R.id.toolbar);
            homeScreenToolbar.setTitle("Profile");
            setSupportActionBar(homeScreenToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.amountPB);
        progressBar.setProgress(0);

        profilebtn = (TextView)findViewById(R.id.editbtn);
        profilepic = (ImageView)findViewById(R.id.profilephoto);

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

    }



    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE){

            imageUri = data.getData();

            String result = "";

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                Toast.makeText(context, "StartingAsync", Toast.LENGTH_LONG).show();
                new UploadPicProgressBarAsync(context, args.getString("userID"), profilepic, imageUri).execute(bitmap);
            }
            catch(Exception e) {

                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }


        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
