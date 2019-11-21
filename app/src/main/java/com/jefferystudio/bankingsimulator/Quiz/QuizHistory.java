package com.jefferystudio.bankingsimulator.Quiz;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jefferystudio.bankingsimulator.R;

import java.io.File;
import java.io.FileInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuizHistory extends AppCompatActivity {

    private CircleImageView profilePic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewquizhistory);



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
}
