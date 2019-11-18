package com.jefferystudio.bankingsimulator.Education;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jefferystudio.bankingsimulator.R;

import java.util.Vector;

public class eduhomepage extends AppCompatActivity {

    RecyclerView recyclerView;
    Vector<YoutubeVideo> youtubeVideos = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_video);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" src=\"https://www.youtube.com/embed/aJJoV0xSDqA\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" src=\"https://www.youtube.com/embed/o_R4M2He_yc\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" src=\"https://www.youtube.com/embed/fzahg9uhT0U\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" src=\"https://www.youtube.com/embed/TCfY2WuYiho\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" src=\"https://www.youtube.com/embed/58EuubFG9-c\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" src=\"https://www.youtube.com/embed/RTZMfBBr7xs\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" src=\"https://www.youtube.com/embed/srQhIOKpR1A\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YoutubeVideo("<iframe width=\"100%\" src=\"https://www.youtube.com/embed/8vlJw0ary5Y\" frameborder=\"0\" allowfullscreen></iframe>") );

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);
    }
}
