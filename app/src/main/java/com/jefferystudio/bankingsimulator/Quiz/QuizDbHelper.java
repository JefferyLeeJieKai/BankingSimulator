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
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
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
        Question q1 = new Question("What is the term for a person that owns 1,000,000 in currency?", "Millionaire", "Billionaire ", "Trillionaire", 1);
        addQuestion(q1);
        Question q2 = new Question("What do we use to purchase items from a shop?", "Credit card, Coins and Banknotes, Cheque", "Coins and Banknotes, Seashells", "Credit card, Coins and Banknotes, Contactless payment", 3);
        addQuestion(q2);
        Question q3 = new Question("You want to save money in a bank. What does the bank open for you?", "Savings bank account", "Investment account", "A bag of sweets", 1);
        addQuestion(q3);
        Question q4 = new Question("When you exchange money for goods or services, you are _______ it.", "Earning", "Spending", "Budgeting", 2);
        addQuestion(q4);
        Question q5 = new Question("________ is a percentage of your deposit you can earn just for leaving your money in a savings account at the bank.", "Spending", "Management", "Interest", 3);
        addQuestion(q5);
        Question q6 = new Question("Which of the following would lead to a financial problem?", "Investment", "Overspending", "Saving", 2);
        addQuestion(q6);
        Question q7 = new Question("Budgeting helps you to ________", "Spend money", "Earn interest", "Prevent overspending", 3);
        addQuestion(q7);
        Question q8 = new Question("What is a benefit for having savings goal?", "Know if you achieved your target savings", "Helps prevent overspending", "Create a list of items you want to purchase", 1);
        addQuestion(q8);
        Question q9 = new Question("When you put money into an account in the bank, it's called a ________.", "Donation", "Deposit", "Loan", 2);
        addQuestion(q9);
        Question q10 = new Question("Transaction statements allows you to:", "View transactions you are going to make", "View transactions you have made", "Amend transactions that have been made", 2);
        addQuestion(q10);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
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
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}