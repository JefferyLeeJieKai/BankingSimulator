package com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jefferystudio.bankingsimulator.R;

public class DialogEmail extends AppCompatDialogFragment {

    private EditText editTextEmail;
    private ExampleDialogListener listener;

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanaceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialogemail, null);

        builder.setView(view)
                .setTitle("Edit Email")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email = editTextEmail.getText().toString();
                        listener.applyTextsEmail(email);
                    }
                });

        editTextEmail = view.findViewById(R.id.edit_email);

        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener{
        void applyTextsEmail(String email);
    }
}
