package com.jefferystudio.bankingsimulator.Registration;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.UpdateStudentListAsync;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudentFragment;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationFragment extends Fragment {

    private Bundle args;
    private Button buttonsubmit;
    private EditText nameBox;
    private EditText emailBox;
    private EditText usernameBox;
    private EditText passwordBox;
    private EditText cfmpasswordBox;
    private RadioGroup genderGroup;
    private RadioButton male;
    private RadioButton female;
    private String gender;
    private EditText dobBox;
    private Spinner roleBox;
    private String verifyResult;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.registration_fragment, container, false);

        args = getArguments();

        buttonsubmit = view.findViewById(R.id.btnsubmit);
        nameBox = view.findViewById(R.id.nameinput);
        emailBox = view.findViewById(R.id.emailinput);
        usernameBox = view.findViewById(R.id.usernameinput);
        passwordBox = view.findViewById(R.id.passwordinput);
        cfmpasswordBox = view.findViewById(R.id.cfmpasswordinput);
        genderGroup = view.findViewById(R.id.radioGroupGender);

        dobBox = view.findViewById(R.id.birthdayinput);
        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        dobBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String strDate = year + "-" + month + "-" + day;
                        dobBox.setText(strDate);

                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        male = view.findViewById(R.id.radioMale);
        female = view.findViewById(R.id.radioFemale);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int id) {

                if(male.isChecked()) {

                    gender = male.getText().toString().trim();
                }
                else if(female.isChecked()) {

                    gender = female.getText().toString().trim();
                }
            }
        });

        roleBox = view.findViewById(R.id.identitytype);

        buttonsubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                checkAllInput();
            }
        });

        return view;
    }

    public void checkAllInput() {

        String email = emailBox.getText().toString().trim();
        String username = usernameBox.getText().toString().trim();
        username = username.toLowerCase();

        ArrayList<String> errorList = new ArrayList<>();

        if (!email.matches("") && !username.matches("")) {

            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                try {
                    verifyResult = new verifyRegistrationAsync(getActivity()).execute(username, email).get(5000, TimeUnit.MILLISECONDS);
                }
                catch (Exception e) {

                }
            }
            else {

                errorList.add("Please enter a valid email address.");
            }
        }

        String name = nameBox.getText().toString().trim();
        String password = passwordBox.getText().toString().trim();
        String cfmpassword = cfmpasswordBox.getText().toString().trim();
        String dob = dobBox.getText().toString().trim();
        String role = String.valueOf(roleBox.getSelectedItem());

        if (!name.matches("") && !email.matches("") && !username.matches("") && !password.matches("") && !gender.matches("")
                && !dob.matches("") && !role.matches("")) {

            Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            Matcher hasSpecial = special.matcher(password);

            if (password.length() <= 7 || !password.matches(".*\\d.*") || !hasSpecial.find()) {

                errorList.add("Please enter a password with at least 8 characters, a digit and a special character.");
            }
            //Toast.makeText(this, verifyResult, Toast.LENGTH_LONG).show();
            if(verifyResult != null) {
                String[] verifyResultArray = verifyResult.split(",");

                if (verifyResultArray[0].equals("True") && verifyResultArray[1].equals("True")) {

                    errorList.add("Username already exists.");
                    errorList.add("Email is already used.");
                } else if (verifyResultArray[1].equals("True")) {

                    errorList.add("Email is already used.");
                } else if (verifyResultArray[0].equals("True")) {

                    errorList.add("Username already exists.");
                }
            }

            if(!cfmpassword.equals(password))
            {
                errorList.add("Password not the same!");
            }
        }
        else {

            errorList.add("Please make sure all field are filled");
        }

        if(errorList.size() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            String completeMsg = "";

            int i = 1;

            for(String errorMsg : errorList) {

                completeMsg += i + ") " + errorMsg + "\n\n";
                ++i;
            }

            builder.setTitle("DigiBank Alert");
            builder.setMessage(completeMsg);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });

            AlertDialog errorDialog = builder.create();
            errorDialog.show();
        }
        else if(errorList.size() == 0) {

            String result1 = "";
            String result2= "";

            try {

                result1 = new RegistrationAsync(getActivity())
                        .execute(name, email, username, password, gender, dob, role)
                        .get(5000, TimeUnit.MILLISECONDS);
                //Toast.makeText(getActivity(), role, Toast.LENGTH_LONG).show();
                String[] resultArray1 = result1.split(",");

                result2 = new UpdateStudentListAsync(getActivity(), args.getString("userID"), username,
                                                     args.getString("classID"), args.getString("className"))
                              .execute()
                              .get(5000, TimeUnit.MILLISECONDS);

                String[] resultArray2 = result2.split(",");

                if(resultArray1[0].equals("Success") && resultArray2[0].equals("Success")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("DigiBank Alert");
                    builder.setMessage("Account created successfully." +
                                       "\nA confirmation email will be send to the registered email shortly." +
                                       "\nDo you want to add another student?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Fragment viewStudentFrag = new ViewStudentFragment();
                            viewStudentFrag.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_layout, viewStudentFrag)
                                    .commit();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Fragment homeFrag = new HomeFragmentBanker();
                            Bundle newArgs = new Bundle();
                            newArgs.putString("userID", args.getString("userID"));
                            newArgs.putString("userName", args.getString("userName"));
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
            catch(Exception e) {

            }
        }
    }

    public void returnVerification(String result) {

        verifyResult = result;
    }
}
