package com.jefferystudio.bankingsimulator.Quiz;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.BankNote.IssueBanknoteFragment;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.Quiz.QuizStudentRecyclerView.QuizStudentEntry;
import com.jefferystudio.bankingsimulator.Quiz.QuizStudentRecyclerView.QuizStudentRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.R;

import java.util.ArrayList;

public class QuizHistoryBanker extends Fragment {

    private Button backbtn;
    private Bundle args;
    private RecyclerView students;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.quizresult_banker, container, false);

        args = getArguments();

        backbtn = view.findViewById(R.id.backBtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle newArgs = new Bundle();
                newArgs.putString("userID", args.getString("userID"));
                newArgs.putString("userName", args.getString("userName"));
                Intent intent = new Intent(getActivity(), HomeScreenBanker.class);
                intent.putExtras(newArgs);
                startActivity(intent);
            }
        });

        students = view.findViewById(R.id.quizResultDetailsRv);
        new GetQuizDetailsAsync(getActivity(), "GetStudents", students)
                .execute( args.getString("userID"), args.getString("userName"));

        return view;
    }


}
