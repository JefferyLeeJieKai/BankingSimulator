package com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfilePageBanker extends AppCompatActivity implements Dialog.ExampleDialogListener {

    private Bundle args;
    private Context context;
    private ImageView profilepic;
    private TextView profilebtn;
    private static final int PICK_IMAGE = 100;
    private TextView getlblname;
    private TextView getlblemail;
    private ImageButton editNamebtn;
    private ImageButton editEmailbtn;
    private ArrayList<String> errorList = new ArrayList<>();
    private String userID;
    private CircleImageView profilePic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepage_banker);
        args = getIntent().getExtras();
        context = this;
        userID = args.getString("userID");

        Toolbar homeScreenToolbar = (Toolbar) findViewById(R.id.toolbar);
        homeScreenToolbar.setTitle("Profile");
        setSupportActionBar(homeScreenToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profilebtn = findViewById(R.id.editbtn);
        profilepic = findViewById(R.id.profilephoto);

        getlblname = findViewById(R.id.lblname);
        getlblemail = findViewById(R.id.lblemail);
        editNamebtn = findViewById(R.id.modeEditName);
        editEmailbtn = findViewById(R.id.modeEditEmail);

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

            editNamebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    openDialogName();
                }
            });

            editEmailbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    openDialogEmail();
                }
            });

        profilePic = (CircleImageView)findViewById(R.id.profilephoto);
        try {

            ContextWrapper cw = new ContextWrapper(getApplication());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File profilePicFile = new File(directory, "ProfilePicture.jpg");
            Bitmap picture = BitmapFactory.decodeStream(new FileInputStream(profilePicFile));
            profilePic.setImageBitmap(picture);
        }
        catch(Exception e) {

        }


    }

    public void openDialogName(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    public void openDialogEmail(){
        DialogEmail dialog = new DialogEmail();
        dialog.show(getSupportFragmentManager(), "dialogEmail");
    }

    @Override
    public void applyTextsName(String name) {

        getlblname.setTextColor(Color.parseColor("#000000"));
        getlblname.setText(name);
    }

    public void applyTextsEmail(String email) {

        getlblemail.setTextColor(Color.parseColor("#000000"));
        getlblemail.setText(email);
    }


    private void openGallery(){

        Intent gallery = new Intent(getApplicationContext(), ImageCropActivity.class);
        gallery.putExtras(args);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("DigiBank Alert");
            builder.setMessage("New profile picture successfully uploaded!");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    ContextWrapper cw = new ContextWrapper(context);
                    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                    File file = new File(directory, "ProfilePicture.jpg");

                    String result = "";
                    try {

                        result = new RetrieveProfilePicAsync(context, file, errorList)
                                .execute("https://www.kidzsmartapp.com/ProfilePicsUpload/" + userID + ".jpg")
                                .get(5000, TimeUnit.MILLISECONDS);
                    }
                    catch(Exception e) {

                    }

                    String[] resultArray = result.split(",");

                    if(resultArray[0].equals("Success")) {

                        try {

                            Bitmap picture = BitmapFactory.decodeStream(new FileInputStream(file));
                            profilepic.setImageBitmap(picture);
                        }
                        catch(Exception e) {

                        }
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
