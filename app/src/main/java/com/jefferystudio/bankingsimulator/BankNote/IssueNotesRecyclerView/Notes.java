package com.jefferystudio.bankingsimulator.BankNote.IssueNotesRecyclerView;

public class Notes {

    private String issueID;
    private String bankerID;
    private String accountholderID;
    private String accountholderUsername;
    private String issuedDate;
    private String totalBalance;

    public Notes (String issueID, String bankerID, String accountholderID, String accountholderUsername,
                  String issuedDate, String totalBalance) {

        this.issueID = issueID;
        this.bankerID = bankerID;
        this.accountholderID = accountholderID;
        this.accountholderUsername = accountholderUsername;
        this.totalBalance = totalBalance;
    }

    public String getIssueID() {

        return issueID;
    }

    public String getbankerID() {

        return bankerID;
    }

    public String getAccountholderID() {

        return accountholderID;
    }

    public String getAccountholderUsername() {

        return accountholderUsername;
    }

    public String getIssuedDate() {

        return issuedDate;
    }

    public String getTotalBalance() {

        return totalBalance;
    }
}
