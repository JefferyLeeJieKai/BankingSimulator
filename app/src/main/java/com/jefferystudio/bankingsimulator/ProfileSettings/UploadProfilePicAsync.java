package com.jefferystudio.bankingsimulator.ProfileSettings;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class UploadProfilePicAsync extends AsyncTask<Bitmap,String,String> {

    private Context context;
    private String userID;

    public UploadProfilePicAsync(Context context, String userID) {

        this.context = context;
        this.userID = userID;
    }

    @Override
    protected String doInBackground(Bitmap... bitmaps) {

        StringBuffer sb = new StringBuffer("");
        Bitmap bitmap = bitmaps[0];
        String uploadImage = getStringImage(bitmap);

        try {
            String link = "http://www.kidzsmart.tk/databaseAccess/uploadProfilePic.php";
            String data = URLEncoder.encode("image", "UTF-8") + "=" +
                    URLEncoder.encode(uploadImage, "UTF-8");
            data += "&" + URLEncoder.encode("userid", "UTF-8") + "=" +
                    URLEncoder.encode(userID, "UTF-8");

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
        catch(Exception e) {


        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

    public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
