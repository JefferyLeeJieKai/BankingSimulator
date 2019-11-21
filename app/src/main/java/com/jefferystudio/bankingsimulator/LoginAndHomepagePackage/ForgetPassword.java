package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

public class ForgetPassword extends AppCompatActivity {

    private TextInputLayout usernameField;
    private TextInputLayout emailField;
    private Button submitButton;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        final Context context = this;

        usernameField = findViewById(R.id.username);
        emailField = findViewById(R.id.email);

        submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateUsername() | !validateEmail()) {

                    return;
                }

                new CredentialsResetAsync(context, "ForgetPassword", null, usernameField,
                        emailField, null).execute();
            }
        });
    }

    //validations
    private boolean validateUsername() {

        String username = usernameField.getEditText().getText().toString().trim();

        boolean result = Validation.validateEmpty(username, usernameField);

        //if not empty
        if (result) {

            usernameField.setError((null));
        }

        return result;
    }

    private boolean validateEmail() {

        String email = emailField.getEditText().getText().toString().trim();

        boolean result = Validation.validateEmpty(email, emailField);

        //if not empty
        if (result) {

            emailField.setError(null);
        }

        return result;
    }

    public void onBackPressed() {

        finish();
    }
}
