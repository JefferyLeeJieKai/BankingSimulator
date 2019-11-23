package com.jefferystudio.bankingsimulator.DepositPackage;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateBalanceAsync;
import com.jefferystudio.bankingsimulator.Validation;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DepositBankerFragment extends Fragment
{
    private Bundle args;
    private String currentID;
    private TextView userID;
    private TextView userBalance;
    private ArrayList<String> list;
    private TextView accountNos;
    private Spinner accounts;
    private TextInputLayout amountToDeposit;
    private String input;
    private Button nextButton;
    private Button backbtn;
    private CircleImageView profilePic;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.depositfunds_banker, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        args = getArguments();
        currentID = args.getString("userID");
        list = args.getStringArrayList("BankerList");

        accountNos = view.findViewById(R.id.accountDDLText);
        accounts = view.findViewById(R.id.accountDDL);

        userID = view.findViewById(R.id.usernameLbl);
        userBalance = view.findViewById(R.id.balanceLbl);
        userID.setText("Acc No. : " + currentID);
        new UpdateBalanceAsync(getActivity(), userBalance, null).execute(currentID);

        accounts = view.findViewById(R.id.accountDDL);
        ArrayAdapter<String> accountsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        accountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accounts.setAdapter(accountsAdapter);

        backbtn = view.findViewById(R.id.backBtn);

        backbtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment homeFrag = new HomeFragmentBanker();
                homeFrag.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, homeFrag)
                        .commit();
            }
        });

        profilePic = view.findViewById(R.id.profilephoto);
        SharedPreferences pref = getActivity().getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
        if(!pref.getString("imageLink", "NotFound").equals("NoImage")) {

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(pref.getString("imageLink", "NotFound"), profilePic);
        }

        amountToDeposit = view.findViewById(R.id.amountTxt);
        nextButton = view.findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                input = amountToDeposit.getEditText().getText().toString().trim();

                if(Validation.validateAmount(input, amountToDeposit))
                {
                    Fragment depositConfirmFrag = new DepositConfirmBankerFragment();
                    args.putString("amount", input);
                    String preSplit = String.valueOf(accounts.getSelectedItem());
                    String[] splitArray = preSplit.split(":");
                    String[] targetNameArray = splitArray[0].split(" ");
                    args.putString("targetName", targetNameArray[0]);
                    String targetID = splitArray[1].substring(1);
                    args.putString("targetID", targetID);
                    depositConfirmFrag.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, depositConfirmFrag)
                            .commit();

                    //new TransactionAsync(getActivity(),"DepositUser").execute(currentID, input);
                    //new UpdateTransAsync(getActivity(), "DepositFunds").execute(currentID, input);
                }
            }
        });

        return view;
    }

    /*
    protected void validateAmount(String input) {

        if(input.isEmpty()) {

            amountToDeposit.setError("Amount cannot be empty");
        }

        try{

            float amount = Float.valueOf(input);
            int befDec = 0;
            int currentCount = 0;

            for(int i = 0; i < input.length(); i++) {

                if(input.charAt(i) == '.') {

                    befDec = currentCount;
                }

                ++currentCount;
            }

            if(input.length() - (befDec + 1) != 2) {
                amountToDeposit.setError("Please enter the correct format");
            }
            else{

                Fragment depositConfirmFrag = new DepositConfirmBankerFragment();
                args.putString("amount", input);
                String preSplit = String.valueOf(accounts.getSelectedItem());
                String[] splitArray = preSplit.split(":");
                String[] targetNameArray = splitArray[0].split(" ");
                args.putString("targetName", targetNameArray[0]);
                String targetID = splitArray[1].substring(1);
                args.putString("targetID", targetID);
                depositConfirmFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, depositConfirmFrag)
                        .commit();

                //new TransactionAsync(getActivity(),"DepositUser").execute(currentID, input);
                //new UpdateTransAsync(getActivity(), "DepositFunds").execute(currentID, input);
            }
        }
        catch(NumberFormatException e) {

            amountToDeposit.setError("Please enter the correct format");
        }
        catch(Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    */
}

