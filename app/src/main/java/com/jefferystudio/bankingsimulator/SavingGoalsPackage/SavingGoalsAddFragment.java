package com.jefferystudio.bankingsimulator.SavingGoalsPackage;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.CommonAsyncPackage.UpdateBalanceAsync;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentUser;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class SavingGoalsAddFragment extends Fragment {

    private Bundle args;
    private String currentID;
    private String userName;
    private String inputName;
    private String inputAmount;
    private String inputDate;
    private String inputPriority;
    private TextView username;
    private TextView userBalance;
    private TextInputLayout savingGoalName;
    private TextInputLayout savingGoalAmount;
    private ArrayList<String> list;
    private Spinner priority;
    private Button createButton;
    private Button cancelButton;
    private EditText etDate;
    private CircleImageView profilePic;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.saving_goals, container, false);

        args = getArguments();
        userName = args.getString("userName");
        currentID = args.getString("userID");

        username = view.findViewById(R.id.usernameLbl);
        userBalance = view.findViewById(R.id.balanceLbl);
        username.setText(userName);
        new UpdateBalanceAsync(getActivity(), userBalance, null).execute(currentID);

        profilePic = view.findViewById(R.id.profilephoto);
        SharedPreferences pref = getActivity().getSharedPreferences("userLoginPref", Context.MODE_PRIVATE);
        if(!pref.getString("imageLink", "NotFound").equals("NoImage")) {

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(pref.getString("imageLink", "NotFound"), profilePic);
        }

        savingGoalName = view.findViewById(R.id.goalNameTxt);
        savingGoalAmount = view.findViewById(R.id.amountTxt);
        priority = view.findViewById(R.id.priority);

        etDate = view.findViewById(R.id.date);

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String strDate = year + "-" + month + "-" + day;
                        etDate.setText(strDate);
                    }
                },year,month,day);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        priority = view.findViewById(R.id.priority);
        list = new ArrayList<>();
        for(int i = 5; i >= 1; i--) {

            if(i == 5) {

                String entry = i + " (Highest priority)";
                list.add(entry);
            }
            else if(i == 1) {

                String entry = i + " (Lowest priority)";
                list.add(entry);
            }
            else {

                list.add(String.valueOf(i));
            }
        }
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priority.setAdapter(priorityAdapter);

        createButton = view.findViewById(R.id.createBtn);
        cancelButton = view.findViewById(R.id.cancelBtn);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputName = savingGoalName.getEditText().getText().toString().trim();
                inputAmount = savingGoalAmount.getEditText().getText().toString().trim();
                inputDate = etDate.getText().toString().trim();
                inputPriority = String.valueOf(priority.getSelectedItem());

                if (!validateCost() | !validateDate() | !validateGoal()) {

                    return;
                }

                new SettingsGoalsAsync(getActivity(), "NewSavingGoal", args.getString("userName")).execute(currentID, inputName, inputAmount, inputDate, inputPriority.substring(0, 1));

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment homeFrag = new HomeFragmentUser();
                Bundle homeBundle = new Bundle();
                homeBundle.putString("userID", args.getString("userID"));
                homeBundle.putString("userName", args.getString("userName"));
                homeFrag.setArguments(homeBundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, homeFrag)
                        .commit();
            }
        });

        return view;
    }

    //validations
    private boolean validateGoal()
    {
        boolean result = Validation.validateEmpty(inputName, savingGoalName);

        //if not empty
        if (result) {

            savingGoalName.setError(null);
        }

        return result;
    }

    private boolean validateCost() {

        boolean result = Validation.validateAmount(inputAmount, savingGoalAmount);

        //if not empty
        if (result) {

            savingGoalAmount.setError(null);
        }

        return result;
    }

    private boolean validateDate()
    {
        boolean result = true;

        //if empty
        if (inputDate.length() == 0) {

            etDate.setError("Field cannot be empty");
            result = false;
        }
        //if not empty
        else {

            etDate.setError(null);
        }

        return result;
    }

    public void updateResult(String result) {

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("True")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("DigiBank Alert");
            builder.setMessage(resultArray[1] + " Do you want to key in a new goal?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    savingGoalAmount.getEditText().getText().clear();
                    savingGoalName.getEditText().getText().clear();
                    etDate.getText().clear();
                    priority.setSelection(0);
                }

            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment homeFrag = new HomeFragmentUser();
                    Bundle args = new Bundle();
                    args.putString("userID", currentID);
                    args.putString("userName", userName);
                    homeFrag.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, homeFrag)
                            .commit();
                }
            });

            AlertDialog quitDialog = builder.create();
            quitDialog.show();
        }
        else if(resultArray[0].equals("False")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("DigiBank Alert");
            builder.setMessage(resultArray[1] + " Do you want to retry your action?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }

            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment homeFrag = new HomeFragmentUser();
                    Bundle args = new Bundle();
                    args.putString("userID", currentID);
                    args.putString("userName", userName);
                    homeFrag.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, homeFrag)
                            .commit();
                }
            });

            AlertDialog quitDialog = builder.create();
            quitDialog.show();
        }
    }
}
