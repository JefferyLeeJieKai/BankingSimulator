package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.R;

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

                usernameField.setError((null));
                emailField.setError(null);

                new CredentialsResetAsync(context, "ForgetPassword", null, usernameField,
                        emailField, null).execute();
            }
        });
    }
}
