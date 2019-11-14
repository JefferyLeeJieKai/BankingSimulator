package com.jefferystudio.bankingsimulator.ProfileSettings;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ProfilePage extends AppCompatActivity {

    private Bundle args;
    private Context context;
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

                String result = "";
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                    result = new UploadProfilePicAsync(context, args.getString("userID")).execute(bitmap).get(8000, TimeUnit.MILLISECONDS);

                    Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                    String[] resultArray = result.split(",");

                    if (resultArray[0].equals("Successfully Uploaded")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("DigiBank Alert");
                        builder.setMessage("New profile picture successfully uploaded!");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                profilepic.setImageURI(imageUri);
                            }
                        });

                        AlertDialog uploadDoneDialog = builder.create();
                        uploadDoneDialog.show();
                    }
                }
                catch(Exception e) {

                    //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

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
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
