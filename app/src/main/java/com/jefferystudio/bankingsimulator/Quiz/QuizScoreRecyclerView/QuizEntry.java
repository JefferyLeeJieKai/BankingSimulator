package com.jefferystudio.bankingsimulator.Quiz.QuizScoreRecyclerView;

public class QuizEntry {

    private String studentID;
    private String studentName;
    private String score;
    private String dateTaken;

    public QuizEntry (String studentID, String studentName, String score, String dateTaken) {

        this.studentID = studentID;
        this.studentName = studentName;
        this.score = score;
        this.dateTaken = dateTaken;
    }

    public String getStudentID() {

        return studentID;
    }

    public String getStudentName() {

        return studentName;
    }

    public String getScore() {

        return score;
    }

    public String getTimeTaken() {

        return dateTaken;
    }
}
