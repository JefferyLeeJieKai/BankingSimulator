package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClassRecyclerView.ClassEntry;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClassRecyclerView.ClassViewRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClassRecyclerView.GetNoStudentsAsync;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudentRecyclerView.StudentEntry;
import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewStudentRecyclerView.StudentViewRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeFragmentBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ClassAsync extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String flag;
    private String link;
    private String data;
    private String userID;
    private String username;
    private String bankerName;
    private String classID;
    private String className;
    private boolean showDialog;
    private RecyclerView recyclerView;

    public ClassAsync(Context context, String flag, String userID, String username, String classID,
                      RecyclerView recyclerView) {

        this.context = context;
        this.flag = flag;
        this.userID = userID;
        this.username = username;
        this.classID = classID;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Retriving class details...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        if(flag.equals("AddClass")) {

            String className = args[0];
            showDialog = true;

            try {
                link = "https://www.kidzsmartapp.com/databaseAccess/addClass.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("classname", "UTF-8") + "=" +
                        URLEncoder.encode(className, "UTF-8");
            }
            catch(Exception e ) {

            }
        }
        else if(flag.equals("ViewClassFragment")) {

            try {
                link = "https://www.kidzsmartapp.com/databaseAccess/getClass.php";
                data = URLEncoder.encode("userid", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8");
            }
            catch(Exception e ) {

            }
        }
        else if(flag.equals("ViewStudentFragment")) {

            className = args[0];

            try {

                link = "https://www.kidzsmartapp.com/databaseAccess/getStudent.php";
                data = URLEncoder.encode("classid", "UTF-8") + "=" +
                        URLEncoder.encode(classID, "UTF-8");
            }
            catch(Exception e) {


            }
        }

        try {

            URL url = new URL(link);
            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
        }
        catch(Exception e) {


        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        String[] resultArray = result.split(",");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if(flag.equals("AddClass")) {

            progDialog.dismiss();

            builder.setMessage("Class added successfully.\nDo you want to stay on the page?");
        }
        else if(flag.equals("ViewClassFragment")) {

            ArrayList<ClassEntry> classList = new ArrayList<>();

            int entryCount = resultArray.length / 4;

            for(int i = 0; i < entryCount; i++) {

                ClassEntry classEntry = new ClassEntry(resultArray[(i * 4)], resultArray[(i * 4) + 1],
                                                       resultArray[(i * 4) + 2], resultArray[(i * 4) + 3]);
                classList.add(classEntry);
            }

            for(ClassEntry classEntry : classList) {

                String asyncResult = "";

                try{

                    asyncResult = new GetNoStudentsAsync(context).execute(classEntry.getClassID()).get(5000, TimeUnit.MILLISECONDS);
                }
                catch(Exception e){

                }

                String[] asyncResultArray = asyncResult.split(",");

                classEntry.setNoOfStudents(asyncResultArray[0]);
            }
            ClassViewRecyclerViewAdaptor adaptor = new ClassViewRecyclerViewAdaptor(context, classList);
            recyclerView.setAdapter(adaptor);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            progDialog.dismiss();
        }
        else if(flag.equals("ViewStudentFragment")) {

            ArrayList<StudentEntry> studentList = new ArrayList<>();

            int entryCount = resultArray.length / 3;

            for(int i = 0; i < entryCount; i++) {

                StudentEntry studentEntry = new StudentEntry(resultArray[(i * 3)], resultArray[(i * 3) + 1],
                                                             resultArray[(i * 3) + 2]);
                studentList.add(studentEntry);
            }

            StudentViewRecyclerViewAdaptor adaptor = new StudentViewRecyclerViewAdaptor(context, studentList, classID,
                                                                                        className, userID, username);
            recyclerView.setAdapter(adaptor);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            progDialog.dismiss();
        }

        if(resultArray[0].equals("Success") && showDialog) {

            progDialog.dismiss();

            builder.setTitle("DigiBank Alert");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Fragment homeFrag = new HomeFragmentBanker();
                    Bundle args = new Bundle();
                    args.putString("userID", userID);
                    args.putString("userName", username);
                    homeFrag.setArguments(args);
                    ((HomeScreenBanker)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, homeFrag)
                            .commit();
                }
            });

            AlertDialog quitDialog = builder.create();
            quitDialog.show();
        }
    }
}
