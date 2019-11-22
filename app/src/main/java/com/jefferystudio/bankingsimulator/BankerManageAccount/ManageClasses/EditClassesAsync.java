package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses.ViewClassRecyclerView.ClassViewRecyclerViewAdaptor;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenBanker;
import com.jefferystudio.bankingsimulator.LoginAndHomepagePackage.HomeScreenUser;
import com.jefferystudio.bankingsimulator.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class EditClassesAsync extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog progDialog;
    private String flag;
    private String link;
    private String data;
    private String classID;

    public EditClassesAsync(Context context, String flag) {

        this.context = context;
        this.flag = flag;
    }

    @Override
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);

        if(flag.equals("DeleteClass")) {

            progDialog.setMessage("Deleting class...");
        }
        else if(flag.equals("EditClassName")) {

            progDialog.setMessage("Editing class name...");
        }
        else if(flag.equals("DeleteAllStudents")) {

            progDialog.setMessage("Deleting all students...");
        }
        else if(flag.equals("DeleteStudent")) {

            progDialog.setMessage("Deleting student...");
        }

        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        if(flag.equals("DeleteClass")) {

            classID = args[0];

            try {

                link = "https://www.kidzsmartapp.com/databaseAccess/editClasses.php";
                data = URLEncoder.encode("classid", "UTF-8") + "=" +
                        URLEncoder.encode(classID, "UTF-8");
                data += "&" + URLEncoder.encode("flag", "UTF-8") + "=" +
                        URLEncoder.encode(flag, "UTF-8");
            }
            catch(Exception e) {


            }
        }
        else if(flag.equals("EditClassName")) {

            classID = args[0];
            String toChange = args[1];

            try {

                link = "https://www.kidzsmartapp.com/databaseAccess/editClasses.php";
                data = URLEncoder.encode("classid", "UTF-8") + "=" +
                        URLEncoder.encode(classID, "UTF-8");
                data += "&" + URLEncoder.encode("tochange", "UTF-8") + "=" +
                        URLEncoder.encode(toChange, "UTF-8");
                data += "&" + URLEncoder.encode("flag", "UTF-8") + "=" +
                        URLEncoder.encode(flag, "UTF-8");
            }
            catch(Exception e) {


            }
        }
        else if(flag.equals("DeleteAllStudents")) {

            try {

                classID = args[0];

                link = "https://www.kidzsmartapp.com/databaseAccess/editClasses.php";
                data = URLEncoder.encode("classid", "UTF-8") + "=" +
                        URLEncoder.encode(classID, "UTF-8");
                data += "&" + URLEncoder.encode("flag", "UTF-8") + "=" +
                        URLEncoder.encode(flag, "UTF-8");
            }
            catch(Exception e) {


            }
        }
        else if(flag.equals("DeleteStudent")) {

            try {

                classID = args[0];
                String accountholder_ID = args[1];

                link = "https://www.kidzsmartapp.com/databaseAccess/editClasses.php";
                data = URLEncoder.encode("classid", "UTF-8") + "=" +
                        URLEncoder.encode(classID, "UTF-8");
                data += "&" + URLEncoder.encode("accountholder_id", "UTF-8") + "=" +
                        URLEncoder.encode(accountholder_ID, "UTF-8");
                data += "&" + URLEncoder.encode("flag", "UTF-8") + "=" +
                        URLEncoder.encode(flag, "UTF-8");
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

        progDialog.dismiss();
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        String[] resultArray = result.split(",");

        if(flag.equals("DeleteClass")) {

            if(resultArray[0].equals("Success")) {

                new EditClassesAsync(context, "DeleteAllStudents").execute(classID);
            }
        }
        else if(flag.equals("DeleteAllStudents")) {

            if(resultArray[0].equals("Success")) {

                ViewClassFragment fragment = (ViewClassFragment)((HomeScreenBanker)context)
                                             .getSupportFragmentManager()
                                             .findFragmentById(R.id.frame_layout);

                RecyclerView recyclerView = fragment.getView().findViewById(R.id.classDetailsRv);
                ClassViewRecyclerViewAdaptor adapter = (ClassViewRecyclerViewAdaptor)recyclerView.getAdapter();
                adapter.updateDeleteClass();
            }
        }
        else if(flag.equals("EditClassName")) {

            EditClassFragment fragment = (EditClassFragment)((HomeScreenBanker)context)
                                          .getSupportFragmentManager().findFragmentById(R.id.frame_layout);

            if(resultArray[0].equals("Success")) {

                fragment.updateResultSuccess();
            }
            else if(resultArray[0].equals("Fail")) {

                fragment.updateResultFail();
            }
        }
    }
}
