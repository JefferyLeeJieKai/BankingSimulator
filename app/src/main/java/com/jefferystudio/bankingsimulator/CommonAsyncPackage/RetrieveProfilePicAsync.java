package com.jefferystudio.bankingsimulator.CommonAsyncPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
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
    private ProgressDialog progDialog;
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
    protected void onPreExecute() {

        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Retriving profile picture");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {

        link = args[0];


        try {

            //URL url = new URL(link);
            //URLConnection connection = url.openConnection();

            InputStream inputStream = new java.net.URL(link).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            FileOutputStream fos = new FileOutputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();

            fos.write(bitmapdata);
            fos.flush();
            fos.close();


        }
        catch (IOException e) {

            errorList.add("Unable to communicate with server.");
            return "Fail,";
        }

        return "Success,";
    }

    @Override
    protected void onPostExecute(String s) {

        progDialog.dismiss();
    }
}
