package com.jefferystudio.bankingsimulator.ViewTransactionsPackage.ViewTransactionsRecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreen;
import com.jefferystudio.bankingsimulator.R;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsEdit;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView.SavingGoal;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView.SavingGoalsRecyclerViewAdaptor;

import java.util.List;

public class TransactionsRecyclerViewAdaptor extends RecyclerView.Adapter<TransactionsRecyclerViewAdaptor.ViewHolder> {

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView transaction;
    public TextView amount;

    public ViewHolder(View itemView) {

        super(itemView);

        transaction = itemView.findViewById(R.id.transactionItem);
        amount = itemView.findViewById(R.id.amountInvolved);
    }
}

    private List<Transaction> transactions;
    private Context context;

    public TransactionsRecyclerViewAdaptor(Context context, List<Transaction> transactions) {

        this.transactions = transactions;
        this.context = context;
    }

    @Override
    public TransactionsRecyclerViewAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.transactions_recycler_row_layout, parent, false);

        // Return a new holder instance
        TransactionsRecyclerViewAdaptor.ViewHolder viewHolder = new TransactionsRecyclerViewAdaptor.ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TransactionsRecyclerViewAdaptor.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Transaction transaction = transactions.get(position);

        // Set item views based on your views and data model
        TextView transactionDetails = viewHolder.transaction;
        TextView amountDetails = viewHolder.amount;

        if(transaction.getTypeTrans().equals("DepositFunds")) {

            transactionDetails.setText("Deposit");
            amountDetails.setTextColor(Color.parseColor("#33cc33"));
            amountDetails.setText("+" + transaction.getTransactedAmt());
        }
        else if(transaction.getTypeTrans().equals("WithdrawFunds")) {

            transactionDetails.setText("Withdrawal");
            amountDetails.setTextColor(Color.parseColor("#ff0000"));
            amountDetails.setText("-" + transaction.getTransactedAmt());
        }
        else if(transaction.getTypeTrans().equals("TransferFunds")) {

            if(transaction.getUserID().equals(transaction.getPayeeID())) {

                transactionDetails.setText("Transfered from " + transaction.getTransactionUsername());
                amountDetails.setTextColor(Color.parseColor("#33cc33"));
                amountDetails.setText("+" + transaction.getTransactedAmt());
            }
            else if(transaction.getUserID().equals(transaction.getTransactionUserID())){

                transactionDetails.setText("Transfered to " + transaction.getPayeeName());
                amountDetails.setTextColor(Color.parseColor("#ff0000"));
                amountDetails.setText("-" + transaction.getTransactedAmt());
            }
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {

        return transactions.size();
    }
}
