package com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView;

public class SavingGoal {

    private String userID;
    private String username;
    private String currentBalance;
    private String goalID;
    private String goalName;
    private String itemCost;
    private String currentValue;

    public SavingGoal(String userID, String username, String currentBalance, String goalID,
                      String goalName, String itemCost, String currentValue) {

        this.userID = userID;
        this.username = username;
        this.currentBalance = currentBalance;
        this.goalID = goalID;
        this.goalName = goalName;
        this.itemCost = itemCost;
        this.currentValue = currentValue;
    }

    public String getUserID() {

        return userID;
    }

    public String getUsername() {

        return username;
    }

    public String getCurrentBalance() {

        return currentBalance;
    }

    public String getGoalID() {

        return goalID;
    }

    public String getGoalName() {

        return goalName;
    }

    public String getItemCost() {

        return itemCost;
    }

    public String getCurrentValue() {

        return currentValue;
    }
}
