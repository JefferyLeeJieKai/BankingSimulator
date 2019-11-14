package com.jefferystudio.bankingsimulator.Education;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.R;

public class edupage extends Fragment {

    private Button click_btn1;
    private Button click_btn2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.education, container, false);

        click_btn1 = view.findViewById(R.id.video1);
        click_btn1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=aJJoV0xSDqA"));
                startActivity(browserIntent);
            }
        });

        click_btn2 = view.findViewById(R.id.video2);
        click_btn2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=o_R4M2He_yc"));
                startActivity(browserIntent);
            }
        });

        return view;
    }
}