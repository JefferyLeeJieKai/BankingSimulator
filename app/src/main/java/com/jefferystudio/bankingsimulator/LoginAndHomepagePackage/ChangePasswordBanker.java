package com.jefferystudio.bankingsimulator.LoginAndHomepagePackage;

import android.content.Context;
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

public class ChangePasswordBanker extends AppCompatActivity {

    private Bundle args;
    private TextInputLayout oldPasswordField;
    private TextInputLayout newPasswordField;
    private TextInputLayout confirmPasswordField;
    private Button submitButton;
    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword_banker);

        args = getIntent().getExtras();
        context = this;

        oldPasswordField = (TextInputLayout)findViewById(R.id.oldPassword);
        newPasswordField = (TextInputLayout)findViewById(R.id.newPassword);
        confirmPasswordField = (TextInputLayout)findViewById(R.id.confirmPassword);

        submitButton = (Button) findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                oldPasswordField.setError(null);
                newPasswordField.setError(null);
                confirmPasswordField.setError(null);

                new CredentialsResetAsync(context, "ChangePassword", args.getString("accountType"), oldPasswordField,
                        newPasswordField, confirmPasswordField).execute(args.getString("userID"));
            }
        });

    }

    public void onBackPressed() {

        finish();
    }
}

/*
public class ChangePasswordBanker extends Fragment {

    private Bundle args;
    private TextInputLayout oldPasswordField;
    private TextInputLayout newPasswordField;
    private TextInputLayout confirmPasswordField;
    private Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.changepassword_banker, container, false);

        args = getArguments();

        oldPasswordField = view.findViewById(R.id.oldPassword);
        newPasswordField = view.findViewById(R.id.newPassword);
        confirmPasswordField = view.findViewById(R.id.confirmPassword);

        submitButton = view.findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                oldPasswordField.setError(null);
                newPasswordField.setError(null);
                confirmPasswordField.setError(null);

                new CredentialsResetAsync(getActivity(), "ChangePassword", args.getString("accountType"), oldPasswordField,
                        newPasswordField, confirmPasswordField).execute(args.getString("userID"));
            }
        });

        return view;
    }
}

 */
