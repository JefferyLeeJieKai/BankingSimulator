package com.jefferystudio.bankingsimulator.ProfileSettings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class UploadPicProgressBarAsync extends AsyncTask<Bitmap, Integer, String> {


    private Context context;
    private ProgressDialog progDialog;
    private String userID;
    private ImageView profilepic;
    private Uri imageUri;
    private ArrayList<Exception> errorList = new ArrayList<Exception>();

    public UploadPicProgressBarAsync(Context context, String userID, ImageView profilepic, Uri imageUri) {

        this.context = context;
        this.userID = userID;
        this.profilepic = profilepic;
        this.imageUri = imageUri;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        progDialog = new ProgressDialog(context);
        progDialog.setMessage("Uploading image");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progDialog.setCancelable(true);
        progDialog.show();
    }

    @Override
    protected String doInBackground(Bitmap[] bitmaps) {

        StringBuffer sb = new StringBuffer("");

        Bitmap bitmap = bitmaps[0];

        String uploadImage = getStringImage(bitmap);

        publishProgress(10);

        try {
            String link = "http://www.kidzsmart.tk/databaseAccess/uploadProfilePic.php";
            String data = URLEncoder.encode("image", "UTF-8") + "=" +
                    URLEncoder.encode(uploadImage, "UTF-8");
            data += "&" + URLEncoder.encode("userid", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");

            publishProgress(20);

            URL url = new URL(link);
            URLConnection connection = url.openConnection();

            publishProgress(40);

            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

            publishProgress(50);

            wr.write(data);
            wr.flush();
            publishProgress(60);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            publishProgress(80);

            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File file = new File(directory, "ProfilePicture.jpg");
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            publishProgress(100);
        }
        catch(Exception e) {

            errorList.add(e);
        }

        return sb.toString();
    }

    protected void onProgressUpdate(Integer[] args) {

        super.onProgressUpdate(args);
        progDialog.setProgress(args[0]);

        if(args[0] == 10) {

            progDialog.setMessage("Retrieving Image...");
        }
        else if(args[0] == 20) {

            progDialog.setMessage("Packing Data for transfer...");
        }
        else if(args[0] == 40) {

            progDialog.setMessage("Establishing connection...");
        }
        else if(args[0] == 50) {

            progDialog.setMessage("Sending data over...");
        }
        else if(args[0] == 60) {

            progDialog.setMessage("Waiting for a response...");
        }
        else if(args[0] == 80) {

            progDialog.setMessage("Response received...");
        }
        else if(args[0] == 100) {

            progDialog.setMessage("Upload completed!");
        }
    }

    @Override
    protected void onPostExecute(String result) {

        progDialog.dismiss();

        String[] resultArray = result.split(",");

        if (resultArray[0].equals("Successfully Uploaded")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("DigiBank Alert");
            builder.setMessage("New profile picture successfully uploaded!");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    profilepic.setImageURI(imageUri);
                }
            });

            AlertDialog uploadDoneDialog = builder.create();
            uploadDoneDialog.show();
        }
    }

        public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
