package com.jefferystudio.bankingsimulator.Quiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudentRecyclerView.StudentEntry;
import com.jefferystudio.bankingsimulator.Quiz.QuizScoreRecyclerView.QuizEntry;
import com.jefferystudio.bankingsimulator.Quiz.QuizScoreRecyclerView.QuizScoreRecyclerViewAdapter;
import com.jefferystudio.bankingsimulator.Quiz.QuizStudentRecyclerView.QuizStudentEntry;
import com.jefferystudio.bankingsimulator.Quiz.QuizStudentRecyclerView.QuizStudentRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView.SavingGoal;
import com.jefferystudio.bankingsimulator.SavingGoalsPackage.SavingGoalsRecyclerView.SavingGoalsRecyclerViewAdaptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class GetQuizDetailsAsync extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String flag;
    private String userID;
    private String userName;
    private RecyclerView recyclerView;

    public GetQuizDetailsAsync(Context context, String flag, RecyclerView recyclerView) {

        this.context = context;
        this.flag = flag;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Retrieving quiz details...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        userID = args[0];
        userName = args[1];

        String link = "https://www.kidzsmartapp.com/databaseAccess/getQuizDetails.php";

        try {

            String data = URLEncoder.encode("userid", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");
            data += "&" + URLEncoder.encode("flag", "UTF-8") + "=" +
                        URLEncoder.encode(flag, "UTF-8");

            URL url = new URL(link);
            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
        }
        catch(IOException e) {


        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        String[] resultArray = result.split(",");

        if(flag.equals("GetStudents")) {

            ArrayList<QuizStudentEntry> studentList = new ArrayList<>();

            int numberOfResults = resultArray.length / 2;

            for (int i = 0; i < numberOfResults; i++) {

                QuizStudentEntry student = new QuizStudentEntry(resultArray[(i * 2)], resultArray[(i * 2) + 1]);

                studentList.add(student);
            }

            QuizStudentRecyclerViewAdaptor adapter = new QuizStudentRecyclerViewAdaptor
                    (context, studentList, userID, userName);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        else if(flag.equals("GetScores")) {

            ArrayList<QuizEntry> quizList = new ArrayList<>();

            int numberOfResults = resultArray.length / 3;

            for (int i = 0; i < numberOfResults; i++) {

                QuizEntry quiz = new QuizEntry(resultArray[(i * 3)], resultArray[(i * 3) + 1],
                                               resultArray[(i * 3) + 2], resultArray[(i * 3) + 3]);

                quizList.add(quiz);
            }

            QuizScoreRecyclerViewAdapter adapter = new QuizScoreRecyclerViewAdapter(context, quizList);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        progDialog.dismiss();
    }
}
