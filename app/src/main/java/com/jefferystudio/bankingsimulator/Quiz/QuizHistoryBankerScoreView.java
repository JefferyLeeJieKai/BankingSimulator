package com.jefferystudio.bankingsimulator.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;

public class QuizHistoryBankerScoreView extends Fragment {

    private Button backbtn;
    private Bundle args;
    private RecyclerView scores;

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
                quizHistoryStudFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, quizHistoryStudFrag)
                        .commit();
            }
        });

        scores = view.findViewById(R.id.quizResultDetailsRv);
        new GetQuizDetailsAsync(getActivity(), "GetScores", scores)
                .execute(args.getString("studentID"), args.getString("studentName"));

        return view;
    }
}
