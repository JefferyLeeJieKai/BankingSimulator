package com.jefferystudio.bankingsimulator.ViewTransactionsPackage.ViewTransactionsRecyclerView;

public class Transaction {

    private String userID;
    private String username;
    private String currentBalance;
    private String transactionUserID;
    private String transactionUsername;
    private String payeeID;
    private String payeeName;
    private String transactedAmt;
    private String purpose;
    private String typeTrans;

    public Transaction(String userID, String username, String currentBalance,
                       String transactionUserID, String transactionUsername, String payeeID,
                       String payeeName, String transactedAmt, String purpose, String typeTrans) {

        this.userID = userID;
        this.username = username;
        this.currentBalance = currentBalance;
        this.transactionUserID = transactionUserID;
        this.transactionUsername = transactionUsername;
        this.payeeID = payeeID;
        this.payeeName = payeeName;
        this.transactedAmt = transactedAmt;
        this.purpose = purpose;
        this.typeTrans = typeTrans;
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

    public String getTransactionUserID() {

        return transactionUserID;
    }

    public String getTransactionUsername() {

        return transactionUsername;
    }

    public String getPayeeID() {

        return payeeID;
    }

    public String getPayeeName() {

        return payeeName;
    }

    public String getTransactedAmt() {

        return transactedAmt;
    }

    public String getPurpose() {

        return purpose;
    }

    public String getTypeTrans() {

        return typeTrans;
    }
}
