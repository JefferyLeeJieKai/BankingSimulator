package com.jefferystudio.bankingsimulator;

import android.support.design.widget.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//all validations place in this class
public class Validation {

    //check for empty field
    public static boolean validateEmpty(String input, TextInputLayout inputBox)
    {
        boolean result = true;

        //if empty
        if (input.isEmpty()) {
            inputBox.setError("Field cannot be empty");
            result = false;
        }

        return result;
    }

    //validate amount
    public static boolean validateAmount(String input, TextInputLayout inputBox) {

        boolean result = validateEmpty(input, inputBox);

        //if not empty
        if (result) {
            String regExp = "^-?[0-9]+([.][0-9]{1,2})?";

            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(input);

            //if non-numeric values found
            if (!m.matches()) {
                inputBox.setError("Please enter digits in 2 decimal place");
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

    public static boolean validateLimit(String input, TextInputLayout inputBox, String currentLimit) {

        boolean result = true;

        String dollarCurrentLimit = currentLimit.substring(16);

        Float withdrawAmount = Float.valueOf(input);
        Float currentLimitAmount = Float.valueOf(dollarCurrentLimit);

        if(withdrawAmount > currentLimitAmount) {

            inputBox.setError("Withdrawal exceeds the current limit. Increase your daily limit to perform transaction");
            result = false;
        }

        return result;
    }

    public static boolean validateEmptyNonTextInputLayout(String input)
    {
        boolean result = true;

        //if empty
        if (input.isEmpty()) {

            result = false;
        }

        return result;
    }

    public static boolean validateAmountNonTextInputLayout(String input) {

        boolean result = true;

        String regExp = "^-?[0-9]+([.][0-9]{1,2})?";

        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(input);

        //if non-numeric values found
        if (!m.matches()) {
            result = false;
        }
        //if numeric values only
        else {
            //convert to float
            float amount = Float.valueOf(input);

            //if amount is negative or more than 10000
            if (amount <= 0 || amount > 10000) {
                result = false;
            }
        }

        return result;
    }
}
