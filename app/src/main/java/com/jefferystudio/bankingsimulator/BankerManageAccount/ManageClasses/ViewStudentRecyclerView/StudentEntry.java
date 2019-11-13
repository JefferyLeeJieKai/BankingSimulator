package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudentRecyclerView;

public class StudentEntry {

    private String userID;
    private String username;
    private String interestRate;

    public StudentEntry(String userID, String username, String interestRate) {

        this.userID = userID;
        this.username = username;
        String subStrIR = interestRate.substring(interestRate.length() - 3);
        Float displayRate = Float.valueOf(subStrIR);
        this.interestRate = String.valueOf(displayRate / 100);
    }

    public String getUserID() {

        return userID;
    }

    public String getUsername() {

        return username;
    }

    public String getInterestRate() {

        return interestRate;
    }
}
