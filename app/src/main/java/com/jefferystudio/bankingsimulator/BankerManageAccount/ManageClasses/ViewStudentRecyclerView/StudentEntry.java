package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudentRecyclerView;

public class StudentEntry {

    private String userID;
    private String username;

    public StudentEntry(String userID, String username) {

        this.userID = userID;
        this.username = username;
    }

    public String getUserID() {

        return userID;
    }

    public String getUsername() {

        return username;
    }
}
