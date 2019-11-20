package com.jefferystudio.bankingsimulator.Quiz.QuizStudentRecyclerView;

public class QuizStudentEntry {

    private String studentID;
    private String studentName;

    public QuizStudentEntry(String studentID, String studentName) {

        this.studentID = studentID;
        this.studentName = studentName;
    }

    public String getStudentID() {

        return studentID;
    }

    public String getStudentName() {

        return studentName;
    }
}
