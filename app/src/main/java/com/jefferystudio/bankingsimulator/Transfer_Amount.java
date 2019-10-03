package com.jefferystudio.bankingsimulator;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Transfer_Amount extends Fragment{

    private Bundle args;
    private String currentID;
    private String currentPayee;
    private TextView userID;
    private TextInputLayout payee;
    private TextInputLayout amountToTransfer;
    private String input;
    private Button nextButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.transfer_amount, container, false);

        args = getArguments();
        currentID = args.getString("userID");

        userID = view.findViewById(R.id.payerLbl);
        userID.setText(currentID);


        Spinner purpose = (Spinner) view.findViewById(R.id.purposeDDL);
        ArrayAdapter<String> purposeAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.transfer_purpose));
        purposeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purpose.setAdapter(purposeAdapter);


        amountToTransfer = view.findViewById(R.id.amountTxt);
        payee = view.findViewById(R.id.payeeLbl);
        nextButton = view.findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentPayee = payee.getEditText().getText().toString().trim();
                input = amountToTransfer.getEditText().getText().toString().trim();

                validateAmount(input);
            }
        });

        return view;
    }

    protected void validateAmount(String input) {

        if(input.isEmpty()) {

            amountToTransfer.setError("Amount cannot be empty");
        }

        try{

            float amount = Float.valueOf(input);
            int befDec = 0;
            int currentCount = 0;

            for(int i = 0; i < input.length(); i++) {

                if(input.charAt(i) == '.') {

                    befDec = currentCount;
                }

                ++currentCount;
            }

            if(input.length() - (befDec + 1) > 2) {
                amountToTransfer.setError("Please enter the correct format");
            }
            else{

                /*FragmentTransaction fragTransc = getChildFragmentManager().beginTransaction();
                fragTransc.replace(R.id.outer_frame, new DepositConfirm());
                fragTransc.addToBackStack(null);
                fragTransc.commit();*/

                new TransactionAsync(getActivity(),"TransferFundsUser").execute(currentID, currentPayee, input);
                new UpdateTransAsync(getActivity(),"TransferFunds").execute(currentID, input, currentPayee, "Testing");
            }
        }
        catch(NumberFormatException e) {

            amountToTransfer.setError("Please enter the correct format");
        }
        catch(Exception e) {

            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
