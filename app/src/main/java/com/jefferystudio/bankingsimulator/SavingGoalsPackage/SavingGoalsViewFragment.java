package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.CheckSavingGoalsFragment;
import com.jefferystudio.bankingsimulator.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class SavingGoalsViewFragment extends Fragment {

    private Bundle args;
    private Bundle newArgs;
    private TextView userName;
    private TextView balance;
    private ProgressBar savingsBar;
    private TextView amountSaved;
    private TextView amountLeft;
    private TextView goalName;
    private TextView cost;
    private TextView deadline;
    private TextView priority;
    private TextView progressLabel;
    private String flag;
    private Button backbtn;
    private CircleImageView profilePic;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals_view, container, false);

        args = getArguments();
        newArgs = new Bundle();

        userName = view.findViewById(R.id.usernameLbl);
        balance = view.findViewById(R.id.balanceLbl);
        savingsBar = view.findViewById(R.id.amountPB);
        amountSaved = view.findViewById(R.id.asLbl);
        amountLeft = view.findViewById(R.id.arLbl);
        goalName = view.findViewById(R.id.goalNameLbl);
        cost = view.findViewById(R.id.amountLbl);
        deadline = view.findViewById(R.id.goalDateLbl);
        priority = view.findViewById(R.id.priorityLbl);
        backbtn = view.findViewById(R.id.buttonback);

        userName.setText(args.getString("userName"));
        balance.setText(args.getString("currentBalance"));
        amountSaved.setText(args.getString("currentValue"));
        goalName.setText(args.getString("goalName"));
        cost.setText(args.getString("itemCost"));
        deadline.setText(args.getString("deadLine"));
        priority.setText(args.getString("priority"));
        flag = args.getString("flag");

        float itemAmount = Float.valueOf(args.getString("itemCost"));
        float savedAmount = Float.valueOf(args.getString("currentValue"));
        float remainingAmount = itemAmount - savedAmount;

        amountLeft.setText(Float.toString(remainingAmount));

        int percentage = calculation(itemAmount, savedAmount);

        progressLabel = view.findViewById(R.id.progressLbl);
        progressLabel.setText(String.valueOf(percentage));

        savingsBar.setProgress(percentage);

        profilePic = view.findViewById(R.id.profilephoto);
        SharedPreferences pref = getActivity().getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
        if(!pref.getString("imageLink", "NotFound").equals("NoImage")) {

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(pref.getString("imageLink", "NotFound"), profilePic);
        }

        backbtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(flag.equals("AccountHolder")) {

                    Fragment viewAllSavingGoalsFrag = new SavingGoalsAllFragment();
                    newArgs.putString("userID", args.getString("userID"));
                    newArgs.putString("userName", args.getString("userName"));
                    newArgs.putString("currentBalance", args.getString("currentBalance"));
                    newArgs.putString("accountType", "AccountHolder");
                    viewAllSavingGoalsFrag.setArguments(newArgs);

                    getActivity().getSupportFragmentManager().beginTransaction()
                                 .replace(R.id.frame_layout, viewAllSavingGoalsFrag)
                                 .commit();
                }
                else if(flag.equals("Banker")) {

                    Fragment viewStudSavingGoalsFrag = new CheckSavingGoalsFragment();
                    newArgs.putString("studentID", args.getString("userID"));
                    newArgs.putString("userID", args.getString("bankerID"));
                    newArgs.putString("userName", args.getString("bankerUsername"));
                    newArgs.putString("classID", args.getString("classID"));
                    newArgs.putString("className", args.getString("className"));
                    newArgs.putString("accountType", "Banker");
                    viewStudSavingGoalsFrag.setArguments(newArgs);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, viewStudSavingGoalsFrag)
                            .commit();
                }
            }
        });

        return view;
    }

    private int calculation(float savingAmount, float existAmount)
    {
        float result = (existAmount / savingAmount) * 100;

        int percent = (int)result;

        return percent;
    }


}
