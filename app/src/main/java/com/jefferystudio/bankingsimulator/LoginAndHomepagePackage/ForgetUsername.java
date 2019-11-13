package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Validation;

public class ForgetUsername extends AppCompatActivity {

    private TextInputLayout emailField;
    private Button submitButton;
    private String input;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_username);
        final Context context = this;

        emailField = findViewById(R.id.email);

        submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                input = emailField.getEditText().getText().toString().trim();

                if (Validation.validateEmpty(input, emailField)) {

                    emailField.setError(null);

                    new CredentialsResetAsync(context, "ForgetUsername", null, emailField,
                            null, null).execute();
                }
            }
        });
    }
}
