package com.jefferystudio.bankingsimulator;

import android.support.design.widget.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//all validations place in this class
public class Validation {

    public static boolean validateAmount(String input, TextInputLayout inputBox) {

        boolean result = true;

        //if empty
        if (input.isEmpty()) {
            inputBox.setError("Amount cannot be empty");
            result = false;
        }
        //if not empty
        else {
            String regExp = "^-?[0-9]+([.][0-9]{1,2})?";

            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(input);

            //if non-numeric values found
            if (!m.matches()) {
                inputBox.setError("Please enter the correct format");
                result = false;
            }
            //if numeric values only
            else {
                //convert to float
                float amount = Float.valueOf(input);

                //if amount is negative or more than 10000
                if (amount <= 0 || amount > 10000) {
                    inputBox.setError("Please enter a value between 0 to 10000");
                    result = false;
                }
            }
        }

        return result;
    }
}
