package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.jefferystudio.bankingsimulator.R;

import java.security.acl.LastOwnerException;

public class ChangePassword extends AppCompatActivity {

    private Bundle args;
    private TextInputLayout oldPasswordField;
    private TextInputLayout newPasswordField;
    private TextInputLayout confirmPasswordField;
    private Button submitButton;
    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        args = getIntent().getExtras();
        context = this;

        oldPasswordField = (TextInputLayout)findViewById(R.id.oldPassword);
        newPasswordField = (TextInputLayout)findViewById(R.id.newPassword);
        confirmPasswordField = (TextInputLayout)findViewById(R.id.confirmPassword);

        /*
        oldPasswordField = view.findViewById(R.id.oldPassword);
        newPasswordField = view.findViewById(R.id.newPassword);
        confirmPasswordField = view.findViewById(R.id.confirmPassword);
        */

        submitButton = (Button)findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                oldPasswordField.setError(null);
                newPasswordField.setError(null);
                confirmPasswordField.setError(null);

                new CredentialsResetAsync(context, "ChangePassword", args.getString("accountType"), oldPasswordField,
                                          newPasswordField, confirmPasswordField).execute(args.getString("userID"));

                Bundle newArgs = new Bundle();
                newArgs.putString("userID", args.getString("userID"));
                newArgs.putString("userName", args.getString("userName"));
                Intent intent = new Intent(getApplicationContext(), HomeScreenUser.class);
                intent.putExtras(newArgs);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {

        finish();
    }
}
