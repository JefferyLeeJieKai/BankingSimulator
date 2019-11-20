package com.jefferystudio.bankingsimulator.CommonAsyncPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class RetrieveProfilePicAsync extends AsyncTask<String, String, String> {

    private Context context;
    private File file;
    private String link;
    private Bitmap bitmap;
    private ArrayList<String> errorList = new ArrayList<String>();

    public RetrieveProfilePicAsync(Context context, File file, ArrayList<String> errorList) {

        this.context = context;
        this.file = file;
        this.errorList = errorList;
    }

    @Override
    protected String doInBackground(String[] args) {

        link = args[0];


        try {

            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);

            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();


        }
        catch (IOException e) {

            errorList.add("Unable to communicate with server.");
        }

        return "Success";
    }
}
