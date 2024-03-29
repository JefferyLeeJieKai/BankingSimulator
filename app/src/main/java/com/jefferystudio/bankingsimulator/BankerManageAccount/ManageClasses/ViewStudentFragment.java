package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudentRecyclerView.StudentEntry;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudentRecyclerView.StudentViewRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.Registration.RegistrationFragment;
import com.jefferystudio.bankingsimulator.Validation;

import java.util.concurrent.TimeUnit;

public class ViewStudentFragment extends Fragment {

    private Bundle args;
    private TextInputLayout searchClass;
    private Button searchButton;
    private TextView classLabel;
    private TextView interest;
    private String input;
    private RecyclerView studentDetails;
    private Button addStudentButton;
    private Button backbutton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.student_view, container, false);

        args = getArguments();

        backbutton = view.findViewById(R.id.buttonback);

        backbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Fragment viewClass = new ViewClassFragment();
                viewClass.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, viewClass);
                transaction.commit();
            }
        });

        addStudentButton = view.findViewById(R.id.AddStudent);
        addStudentButton.setOnClickListener(new View.OnClickListener() {

            public void onClick (View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());

                builder1.setTitle("DigiBank Alert");
                builder1.setMessage("Do you want to add: " +
                        "\n1)A previously created account? " +
                        "\n2)A new account?");
                builder1.setPositiveButton("Existing Account", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                        builder2.setTitle("DigiBank Alert");
                        final EditText input = new EditText(getActivity());
                        input.setHint("Please enter account number or username here");
                        builder2.setView(input);

                        builder2.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String data = input.getText().toString().trim();

                                new CheckOwnershipAsync(getActivity()).execute(data, args.getString("userID"),
                                        args.getString("classID"), args.getString("className"));
                            }
                        });

                        AlertDialog inputDialog = builder2.create();
                        inputDialog.show();
                    }
                });

                builder1.setNegativeButton("New Account", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Fragment registrationFrag = new RegistrationFragment();
                        registrationFrag.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, registrationFrag)
                                .commit();
                    }
                });

                AlertDialog quitDialog = builder1.create();
                quitDialog.show();
            }
        });

        studentDetails = view.findViewById(R.id.ahDetailsRv);

        new ClassAsync(getActivity(), "ViewStudentFragment", args.getString("userID"), args.getString("userName"),
                       args.getString("classID"), studentDetails).execute(args.getString("className"));

        return view;
    }

    public void updateAdaptor(int entryPosition) {

        StudentViewRecyclerViewAdaptor adaptor = (StudentViewRecyclerViewAdaptor) studentDetails.getAdapter();
        adaptor.notifyItemRemoved(entryPosition);
        adaptor.notifyItemRangeChanged(entryPosition, adaptor.getItemCount());
    }

    public void updateInterestRate(final StudentEntry studentEntry) {

        String msg = "Are you sure you edit interest rate for " + studentEntry.getUsername() + "' ?";

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());

        builder1.setTitle("Warning!");
        builder1.setMessage(msg);

        //yes button selected
        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {

                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("DigiBank Alert");
                final EditText input = new EditText(getActivity());
                input.setHint("Please enter interest rate here");
                builder2.setView(input);

                builder2.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String data = input.getText().toString().trim();
                        String response = "";
                        float dataFloat = Float.valueOf(data);
                        dataFloat /= 100;
                        dataFloat += 1.0;
                        data = Float.toString(dataFloat);

                        new UpdateInterestRateAsync(getActivity(), studentEntry.getUserID(), data).execute();
                    }
                });

                AlertDialog inputDialog = builder2.create();
                inputDialog.show();
            }
        });
        //no button selected
        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        AlertDialog ad = builder1.create();
        ad.show();
    }

    public void updateInterestRateResult(String response) {

        String[] responseArray = response.split(",");

        AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
        builder3.setTitle("DigiBank Alert");

        if (responseArray[0].equals("Success")) {

            builder3.setMessage("You have successfully updated the interest rate.");
        }

        builder3.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterace, int i) {

                new ClassAsync(getActivity(), "ViewStudentFragment", args.getString("userID"), args.getString("userName"),
                        args.getString("classID"), studentDetails).execute(args.getString("className"));
            }
        });

        AlertDialog quitDialog = builder3.create();
        quitDialog.show();
    }

    public void updateStudentListResult(String response) {

        String[] responseArray = response.split(",");

        AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
        builder3.setTitle("DigiBank Alert");

        if(responseArray[0].equals("Success")) {

            builder3.setMessage("Successfully added student to class.");
        }
        else if(responseArray[0].equals("Fail")) {

            builder3.setMessage("Adding failed. Please make sure you " +
                    "are adding an account you created.");
        }

        builder3.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick (DialogInterface dialogInterace, int i) {

                new ClassAsync(getActivity(), "ViewStudentFragment", args.getString("userID"),
                        args.getString("userName"), args.getString("classID"),
                        studentDetails).execute(args.getString("className"));
            }
        });

        AlertDialog quitDialog = builder3.create();
        quitDialog.show();
    }
}
