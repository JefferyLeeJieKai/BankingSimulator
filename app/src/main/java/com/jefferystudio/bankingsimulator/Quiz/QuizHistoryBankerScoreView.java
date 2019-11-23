package com.jefferystudio.bankingsimulator.Quiz;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;

import java.io.File;
import java.io.FileInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuizHistoryBankerScoreView extends Fragment {

    private Button backbtn;
    private Bundle args;
    private RecyclerView scores;
    private CircleImageView profilePic;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.quizresult_banker, container, false);

        args = getArguments();

        backbtn = view.findViewById(R.id.backBtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment quizHistoryStudFrag = new QuizHistoryBanker();
                Bundle newArgs = new Bundle();
                newArgs.putString("userID", args.getString("bankerID"));
                newArgs.putString("userName", args.getString("bankerUsername"));
                quizHistoryStudFrag.setArguments(newArgs);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, quizHistoryStudFrag)
                        .commit();
            }
        });

        profilePic = view.findViewById(R.id.profilephotomain);
        try {
            ContextWrapper cw = new ContextWrapper(getActivity());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File profilePicFile = new File(directory, "ProfilePicture.png");
            Bitmap picture = BitmapFactory.decodeStream(new FileInputStream(profilePicFile));
            profilePic.setImageBitmap(picture);
        }
        catch(Exception e) {

        }

        scores = view.findViewById(R.id.quizResultDetailsRv);
        new GetQuizDetailsAsync(getActivity(), "GetScores", scores)
                .execute(args.getString("studentID"), args.getString("studentName"));

        return view;
    }
}
