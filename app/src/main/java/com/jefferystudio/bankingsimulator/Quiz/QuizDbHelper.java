package com.jefferystudio.bankingsimulator.Quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jefferystudio.bankingsimulator.Quiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SmartQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER," +
                QuestionsTable.COLUMN_REASON + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("What is the term for a person that owns 1,000,000 in currency?", "Millionaire", "Billionaire ", "Trillionaire", 1, "Millionaire => 1,000,000 \n Billionaire => 1,000,000,000 \n Trillionaire => 1,000,000,000,000");
        addQuestion(q1);
        Question q2 = new Question("What do we use to purchase items from a shop?", "Credit card, Coins and Banknotes, Cheque", "Coins and Banknotes, Seashells", "Credit card, Coins and Banknotes, Contactless payment", 3, "Cheques are phasing out as a payment type and they are not used as a payment type in a shop. Seashells are not a form of payment.");
        addQuestion(q2);
        Question q3 = new Question("You want to save money in a bank. What does the bank open for you?", "Savings bank account", "Investment account", "A bag of sweets", 1, "A savings account is a basic type of bank account that allows you to deposit money, keep it safe, and withdraw funds, all while earning interest.");
        addQuestion(q3);
        Question q4 = new Question("When you exchange money for goods or services, you are _______ it.", "Earning", "Spending", "Budgeting", 2, "Spending is when an amount of money is paid out in exchange for goods and or services.");
        addQuestion(q4);
        Question q5 = new Question("________ is a percentage of your deposit you can earn just for leaving your money in a savings account at the bank.", "Spending", "Management", "Interest", 3, "Bank interest is what the bank pays to its customers based on the money in their accounts. Banks uses customers deposited money for loans and investments.");
        addQuestion(q5);
        Question q6 = new Question("Which of the following would lead to a financial problem?", "Investment", "Overspending", "Saving", 2, "Overspending is to spend more than you should or expected to. Constant and severe overspending would lead to serious financial issues.");
        addQuestion(q6);
        Question q7 = new Question("Budgeting helps you to ________.", "Spend money", "Earn interest", "Prevent overspending", 3, "Budgeting creates a spending plan which enables you to determine in advance if you are able to make purchases which you want or need.");
        addQuestion(q7);
        Question q8 = new Question("What is a benefit for having savings goal?", "Know if you achieved your target savings", "Helps prevent overspending", "Create a list of items you want to purchase", 1, "Having savings goal is an effective way to track how much you have saved.");
        addQuestion(q8);
        Question q9 = new Question("When you put money into an account in the bank, it's called a ________.", "Donation", "Deposit", "Loan", 2, "Bank deposit is to place money into banking institutions for safe keeping.");
        addQuestion(q9);
        Question q10 = new Question("Transaction statements allows you to:", "View transactions you are going to make", "View transactions you have made", "Amend transactions that have been made", 2, "A bank statement is a record that summarises your financial position at the end of a set period. Statements are usually issued monthly.");
        addQuestion(q10);
        Question q11 = new Question("The card used to deduct funds immediately from your account is called a _______.", "Amazon Gift Card", "Credit Card", "Debit Card", 3, "A purchase made from a debit card draws directly from your account while a credit card borrows money from the bank to be paid back later.");
        addQuestion(q11);
        Question q12 = new Question("Online banking and transactions will be processed ________.", "After 3 working days", "Instantly", "At the end of the month", 2, "Online banking and transactions are known for its speed, convenience and security. Transactions below $200,000 will be done almost instantly.");
        addQuestion(q12);
        Question q13 = new Question("When should you start saving  _______.", "When you start receiving an allowance", "After you start working", "When you want to buy an expensive item", 1, "When you start receiving allowance it is a good practice to start a good habit of saving. 50 - 30 - 20 rule teaches us to allocate 20% of income to savings, 50% to necessities and 30% to your discretion.");
        addQuestion(q13);
        Question q14 = new Question("When a person publicly announces that they are unable to repay their loans it is called a ________.", "Bankruptcy", "Interest", "Loan", 1, "To declare bankruptcy is a legal process for people who are unable to repay debts to creditors may seek relief from some or all of their debts.");
        addQuestion(q14);
        Question q15 = new Question("A credit report is _______.", "Your monthly credit card statement", "Your bank reminder", "A loan and payment history", 3, "Credit report is a credit payment history provided by different credit providers which helps credit providers make lending decisions for you. ");
        addQuestion(q15);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_REASON, question.getReason());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setReason(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_REASON)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}