package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClassRecyclerView.ClassViewRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateBalanceAsync;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView.SavingGoalsRecyclerViewAdaptor;

import java.io.File;
import java.io.FileInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class SavingGoalsAllFragment extends Fragment {

    private Bundle args;
    private String currentUserID;
    private String currentUserName;
    private String currentBalance;
    private TextView userName;
    private TextView balance;
    private RecyclerView recyclerView;
    private Button backButton;
    private CircleImageView profilePic;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstance) {

        View view = inflater.inflate(R.layout.saving_goals_all, container, false);

        args = getArguments();

        currentUserID = args.getString("userID");
        currentUserName = args.getString("userName");
        currentBalance = args.getString("currentBalance");

        userName = view.findViewById(R.id.usernameLbl);
        balance = view.findViewById(R.id.balanceLbl);
        new UpdateBalanceAsync(getActivity(), balance, null).execute(currentUserID);

        profilePic = view.findViewById(R.id.profilephoto);
        try {
            ContextWrapper cw = new ContextWrapper(getActivity());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File profilePicFile = new File(directory, "ProfilePicture.jpg");
            Bitmap picture = BitmapFactory.decodeStream(new FileInputStream(profilePicFile));
            profilePic.setImageBitmap(picture);
        }
        catch(Exception e) {

        }

        recyclerView = view.findViewById(R.id.goalDetailsRv);

        recyclerView = view.findViewById(R.id.goalDetailsRv);
        new RetrieveSavingGoalsAsync(getActivity(), recyclerView, "AccountHolder").execute(currentUserID, currentUserName, currentBalance);

        userName.setText(currentUserName);
        balance.setText(currentBalance);

        profilePic = view.findViewById(R.id.profilephoto);
        try {
            ContextWrapper cw = new ContextWrapper(getActivity());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File profilePicFile = new File(directory, "ProfilePicture.png");
            Bitmap picture = BitmapFactory.decodeStream(new FileInputStream(profilePicFile));
            profilePic.setImageBitmap(picture);
        }
        catch(Exception e) {

        }

        backButton = view.findViewById(R.id.backbtn);
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment homeFrag = new HomeFragmentUser();
                Bundle homeBundle = new Bundle();
                homeBundle.putString("userID", currentUserID);
                homeBundle.putString("userName", currentUserName);
                homeFrag.setArguments(homeBundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, homeFrag)
                        .commit();
            }
        });

        return view;
    }

    public void updateAdaptor(int entryPosition) {

        SavingGoalsRecyclerViewAdaptor savingGoalsAdapter = (SavingGoalsRecyclerViewAdaptor)recyclerView.getAdapter();
        savingGoalsAdapter.notifyItemRemoved(entryPosition);
        savingGoalsAdapter.notifyItemRangeChanged(entryPosition, savingGoalsAdapter.getItemCount());
    }
}
