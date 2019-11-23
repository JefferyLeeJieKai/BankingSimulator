package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.CommonAsyncPackage.TransactionAsync;
import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateBalanceAsync;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class SavingGoalsAddAmountFragment extends Fragment {

    private Bundle args;
    private ProgressBar progress;
    private TextView progressLabel;
    private TextView amountSavedCurrency;
    private TextView amountSaved;
    private TextView amountRemainCurrency;
    private TextView amountRemain;
    private TextView goalName;
    private TextInputLayout amount;
    private Button confirmButton;
    private Button cancelButton;
    private CircleImageView profilePic;
    private TextView currentIDTextbox;
    private TextView currentLimit;
    private TextView currentBalance;
    private String currentID;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals_add_amount, container, false);

        args = getArguments();
        currentID = args.getString("userID");

        currentIDTextbox = view.findViewById(R.id.usernameLbl);
        currentBalance = view.findViewById(R.id.balanceLbl);
        currentLimit = view.findViewById(R.id.currentLimit);
        currentIDTextbox.setText("Acc No. : " + currentID);
        new UpdateBalanceAsync(getActivity(), currentBalance, currentLimit).execute(currentID);

        progress = view.findViewById(R.id.amountPB);

        final float savingAmount = Float.valueOf(args.getString("itemCost"));
        final float existAmount = Float.valueOf(args.getString("currentValue"));
        float remainAmount = savingAmount - existAmount;
        int percent = calculation(savingAmount, existAmount);

        progressLabel = view.findViewById(R.id.progressLbl);
        progressLabel.setText(String.valueOf(percent));

        progress.setProgress(percent);

        //amount saved
        amountSavedCurrency = view.findViewById(R.id.asCurrencyLbl);

        amountSaved = view.findViewById(R.id.asLbl);
        amountSaved.setText(args.getString("currentValue"));

        //amount remaining
        amountRemainCurrency = view.findViewById(R.id.arCurrencyLbl);

        amountRemain = view.findViewById(R.id.arLbl);
        amountRemain.setText(Float.toString(remainAmount));

        //goal name
        goalName = view.findViewById(R.id.goalNameDDL);
        goalName.setText(args.getString("goalName"));


        //add amount
        amount = view.findViewById(R.id.amountTxt);

        profilePic = view.findViewById(R.id.profilephoto);
        SharedPreferences pref = getActivity().getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
        if(!pref.getString("imageLink", "NotFound").equals("NoImage")) {

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(pref.getString("imageLink", "NotFound"), profilePic);
        }

        //confirm button
        confirmButton = view.findViewById(R.id.confirmBtn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = amount.getEditText().getText().toString().trim();

                //if invalid amount found
                if (validateAmount(input)) {

                    String dollarCurrentLimit = currentLimit.getText().toString().substring(16);
                    float currentLimitFloat = Float.valueOf(dollarCurrentLimit);
                    float amountToSave = Float.valueOf(input);
                    float amountToUpdate = amountToSave + existAmount;

                    if(amountToUpdate > savingAmount || amountToSave > currentLimitFloat) {

                        AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                        builder.setTitle("DigiBank Alert");

                        if(amountToUpdate > savingAmount) {

                            builder.setMessage("Amount to save exceeds current goal.");
                        }
                        else if (amountToSave > currentLimitFloat) {

                            builder.setMessage("Amount to save exceeds current limit. Increase your daily limit to commit savings.");
                        }

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                amount.getEditText().getText().clear();
                            }
                        });

                        AlertDialog exceedDialog = builder.create();
                        exceedDialog.show();
                    }
                    else {

                        new TransactionAsync(getActivity(), "WithdrawalUserNoShow", args.getString("userName"))
                                .execute(args.getString("userID"), input, Float.toString(amountToUpdate),
                                        args.getString("goalID"));
                    }
                }
            }
        });

        //cancel button
        cancelButton = view.findViewById(R.id.cancelBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment viewAllSavingGoalsFrag = new SavingGoalsAllFragment();
                args.putString("userID", args.getString("userID"));
                args.putString("userName", args.getString("userName"));
                args.putString("currentBalance", args.getString("currentBalance"));
                viewAllSavingGoalsFrag.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, viewAllSavingGoalsFrag)
                        .commit();
            }
        });
        return view;
    }

    //calculation
    private int calculation(double savingAmount, double existAmount)
    {
        double result = (existAmount / savingAmount) * 100;
        
        int percent = (int)result;

        return percent;
    }

    //validations
    private boolean validateAmount(String input) {

        boolean result = Validation.validateAmount(input, amount);

        if (result) {
            amount.setError(null);
        }

        return result;
    }

    public void updateResult(String result) {

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("True")) {

            AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setTitle("DigiBank Alert");
            builder.setMessage("Amount saved to goal successfully.\nDo you want to save more?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    amount.getEditText().getText().clear();
                    new UpdateBalanceAsync(getActivity(), currentBalance, currentLimit).execute(currentID);
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment viewAllSavingGoalsFrag = new SavingGoalsAllFragment();
                    args.putString("userID", args.getString("userID"));
                    args.putString("userName", args.getString("userName"));
                    args.putString("accountType", args.getString("accountType"));
                    viewAllSavingGoalsFrag.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, viewAllSavingGoalsFrag)
                            .commit();
                }
            });

            android.app.AlertDialog quitDialog = builder.create();
            quitDialog.show();
        }
        else if(resultArray[0].equals("False")) {

            amount.setError("Amount to save exceeds your bank balance.");
        }
    }
}
