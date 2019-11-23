package com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.CommonAsyncPackage.RetrieveProfilePicAsync;
import com.jefferystudio.bankingsimulator.ImageCropper.ImageCropActivity;
import com.jefferystudio.bankingsimulator.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePage extends AppCompatActivity {

    private Bundle args;
    private Context context;
    private TextView profilebtn;
    private static final int PICK_IMAGE = 100;
    private ArrayList<String> errorList = new ArrayList<>();
    private String userID;
    private CircleImageView profilePic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepages);

        args = getIntent().getExtras();
        context = this;
        userID = args.getString("userID");

        Toolbar homeScreenToolbar = (Toolbar) findViewById(R.id.toolbar);
        homeScreenToolbar.setTitle("Profile");
        setSupportActionBar(homeScreenToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profilebtn = (TextView) findViewById(R.id.editbtn);

        profilePic = (CircleImageView) findViewById(R.id.profilephoto);
        SharedPreferences pref = getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
        if (!pref.getString("imageLink", "NotFound").equals("NoImage")) {

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(pref.getString("imageLink", "NotFound"), profilePic);
        }

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openGallery();
            }
        });
    }

    private void openGallery(){

        Intent gallery = new Intent(getApplicationContext(), ImageCropActivity.class);
        gallery.putExtras(args);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("DigiBank Alert");
            builder.setMessage("New profile picture successfully uploaded!");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    SharedPreferences pref = getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
                    if(!pref.getString("imageLink", "NotFound").equals("NoImage")) {

                        ImageLoader imageLoader = ImageLoader.getInstance();
                        imageLoader.displayImage(pref.getString("imageLink", "NotFound"), profilePic);
                    }
                }
            });

            AlertDialog uploadDoneDialog = builder.create();
            uploadDoneDialog.show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
