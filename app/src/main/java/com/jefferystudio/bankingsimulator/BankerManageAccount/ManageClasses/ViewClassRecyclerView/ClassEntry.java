package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClassRecyclerView;

public class ClassEntry {

    private String userID;
    private String username;
    private String classID;
    private String className;

    public ClassEntry(String userID, String username, String classID, String className) {

        this.userID = userID;
        this.username = username;
        this.classID = classID;
        this.className = className;
    }

    public String getUserID() {

        return userID;
    }

    public String getUsername() {

        return username;
    }

    public String getClassID() {

        return classID;
    }

    public String getClassName() {

        return className;
    }
}
