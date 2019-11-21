package com.jefferystudio.bankingsimulator.DepositPackage;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.TransactionAsync;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateTransAsync;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class DepositConfirmUserFragment extends Fragment {

    private String userName;
    private String currentID;
    private String input;
    private TextView username;
    private TextView accNo;
    private TextView amount;
    private Bundle args;
    private Button confirmButton;
    private Button cancelButton;
    private TextView TextViewdate;
    private String date;
    private CircleImageView profilePic;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.deposit_confirm_user, container, false);

        args = getArguments();
        userName = args.getString("userName");
        currentID = args.getString("userID");
        input = args.getString("amount");

        username = view.findViewById(R.id.usernameLbl);
        username.setText("Username: " + userName);
        accNo = view.findViewById(R.id.accountLbl);
        accNo.setText("Acc No. : " + currentID);
        amount = view.findViewById(R.id.amountLbl);
        amount.setText(input);

        confirmButton = view.findViewById(R.id.confirmBtn);
        cancelButton = view.findViewById(R.id.cancelBtn);
        TextViewdate = view.findViewById(R.id.dateLbl);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        TextViewdate.setText(date);

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

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TransactionAsync(getActivity(),"DepositUser", args.getString("userName")).execute(currentID, input);
                new UpdateTransAsync(getActivity(), "DepositFunds").execute(currentID, input);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment homeFrag = new HomeFragmentUser();
                homeFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, homeFrag)
                        .commit();
            }
        });

        return view;
    }

    public void recall() {

        Fragment recallFrag = new DepositAHFragment();
        recallFrag.setArguments(args);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, recallFrag)
                .commit();
    }
}

