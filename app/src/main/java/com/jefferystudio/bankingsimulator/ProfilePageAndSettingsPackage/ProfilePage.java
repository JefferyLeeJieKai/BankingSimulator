package com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.R;

public class ProfilePage extends AppCompatActivity implements Dialog.ExampleDialogListener {

    private Bundle args;
    private Context context;
    private ImageView profilepic;
    private TextView profilebtn;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private Bitmap bitmap;
    private TextView getlblname;
    private TextView getlblemail;
    private ImageButton editNamebtn;
    private ImageButton editEmailbtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepages);

        args = getIntent().getExtras();
        context = this;

        Toolbar homeScreenToolbar = (Toolbar) findViewById(R.id.toolbar);
        homeScreenToolbar.setTitle("Profile");
        setSupportActionBar(homeScreenToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profilebtn = (TextView)findViewById(R.id.editbtn);
        profilepic = (ImageView)findViewById(R.id.profilephoto);

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openGallery();
            }
        });

        getlblname = (TextView)findViewById(R.id.lblname);
        getlblemail = (TextView)findViewById(R.id.lblemail);
        editNamebtn = (ImageButton) findViewById(R.id.modeEditName);
        editEmailbtn = (ImageButton) findViewById(R.id.modeEditEmail);


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

    }

    public void openDialogName(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    public void openDialogEmail(){
        DialogEmail dialogemail = new DialogEmail();
        dialogemail.show(getSupportFragmentManager(), "dialogEmail");
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
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){

            imageUri = data.getData();

            String result = "";
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                new UploadPicProgressBarAsync(context, args.getString("userID"), profilepic, imageUri).execute(bitmap);

                //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }
            catch(Exception e) {

                //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }
}