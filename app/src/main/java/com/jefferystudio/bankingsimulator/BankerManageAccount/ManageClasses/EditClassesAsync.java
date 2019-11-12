package com.jefferystudio.bankingsimulator.BankerManageAccount.ManageClasses;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class EditClassesAsync extends AsyncTask<String, String, String> {

    private Context context;
    private String flag;
    private String link;
    private String data;

    public EditClassesAsync(Context context, String flag) {

        this.context = context;
        this.flag = flag;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String[] args) {

        StringBuffer sb = new StringBuffer("");

        if(flag.equals("DeleteClass")) {

            String classID = args[0];

            try {

                link = "http://www.kidzsmart.tk/databaseAccess/editClasses.php";
                data = URLEncoder.encode("classid", "UTF-8") + "=" +
                        URLEncoder.encode(classID, "UTF-8");
                data += "&" + URLEncoder.encode("flag", "UTF-8") + "=" +
                        URLEncoder.encode(flag, "UTF-8");
            }
            catch(Exception e) {


            }
        }
        else if(flag.equals("DeleteAllStudents")) {

            try {

                String classID = args[0];

                link = "http://www.kidzsmart.tk/databaseAccess/editClasses.php";
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

                String classID = args[0];
                String accountholder_ID = args[1];

                link = "http://www.kidzsmart.tk/databaseAccess/editClasses.php";
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


    }
}
