package com.jefferystudio.bankingsimulator.Registration;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.LoginScreen;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.PreLogin;
import com.jefferystudio.bankingsimulator.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

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

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration2);
        final Context context = this;

        buttonsubmit =(Button) findViewById(R.id.btnsubmit);
        nameBox = findViewById(R.id.nameinput);
        emailBox = findViewById(R.id.emailinput);
        usernameBox = findViewById(R.id.usernameinput);
        passwordBox = findViewById(R.id.passwordinput);
        cfmpasswordBox = findViewById(R.id.cfmpasswordinput);
        genderGroup = findViewById(R.id.radioGroupGender);

        dobBox = findViewById(R.id.birthdayinput);
        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        dobBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context, new DatePickerDialog.OnDateSetListener() {
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

        male = findViewById(R.id.radioMale);
        female = findViewById(R.id.radioFemale);
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

        roleBox = findViewById(R.id.identitytype);

        buttonsubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                checkAllInput();
            }
        });
    }

    public void checkAllInput() {

        String email = emailBox.getText().toString().trim();
        String username = usernameBox.getText().toString().trim();

        ArrayList<String> errorList = new ArrayList<>();

        if (!email.matches("") && !username.matches("")) {

            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                try {
                    verifyResult = new verifyRegistrationAsync(this).execute(username, email).get(5000, TimeUnit.MILLISECONDS);
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
        String prelimRole = String.valueOf(roleBox.getSelectedItem());
        String role = "";

        if(prelimRole.equals("Account Holder")) {

            role = "AccountHolder";
        }

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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

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

            new RegistrationAsync(this).execute(name, email, username, password, gender, dob, role);
        }
    }

    public void returnVerification(String result) {

        verifyResult = result;
    }

    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), PreLogin.class);
        startActivity(intent);
    }
}
