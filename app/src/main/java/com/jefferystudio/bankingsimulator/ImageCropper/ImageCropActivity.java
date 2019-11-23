package com.jefferystudio.bankingsimulator.ImageCropper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.jefferystudio.bankingsimulator.ImageCropper.CropOverlay.CropOverlayView;
import com.jefferystudio.bankingsimulator.ImageCropper.CropOverlay.edge.Edge;
import com.jefferystudio.bankingsimulator.ProfilePageAndSettingsPackage.UploadPicProgressBarAsync;
import com.jefferystudio.bankingsimulator.R;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import com.jefferystudio.bankingsimulator.ImageCropper.PhotoView.IGetImageBounds;
import com.jefferystudio.bankingsimulator.ImageCropper.PhotoView.PhotoView;
import com.jefferystudio.bankingsimulator.ImageCropper.PhotoView.RotationSeekBar;

/**
 * @author GT
 */
public class ImageCropActivity extends AppCompatActivity {

    public static final String TAG = "ImageCropActivity";
    private static final int ANCHOR_CENTER_DELTA = 10;

    PhotoView mImageView;
    CropOverlayView mCropOverlayView;
    Button btnFromGallery;
    Button btnDone;
    Button mBtnReset;
    View mMoveResizeText;
    RotationSeekBar mRotationBar;
    Button mBtnUndoRotation;

    private Bundle args;
    private String userID;
    private ContentResolver mContentResolver;

    private final int IMAGE_MAX_SIZE = 1024;
    private final Bitmap.CompressFormat mOutputFormat = Bitmap.CompressFormat.JPEG;

    //Temp file to save cropped image
    private String mImagePath;
    private Uri mSaveUri = null;
    private Uri mImageUri = null;


    //File for capturing camera images
    private File mFileTemp;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public static final int REQUEST_CODE_PICK_GALLERY = 200;
    public static final String ERROR_MSG = "error_msg";
    public static final String ERROR = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        args = getIntent().getExtras();
        userID = args.getString("userID");
        setContentView(R.layout.activity_image_crop);
        mContentResolver = getContentResolver();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(null);
        mImageView = (PhotoView) findViewById(R.id.iv_photo);
        mCropOverlayView = (CropOverlayView) findViewById(R.id.crop_overlay);
        btnFromGallery = (Button) findViewById(R.id.btnFromGallery);
        btnDone = (Button) findViewById(R.id.btn_done);
        mBtnReset = (Button) findViewById(R.id.btn_reset);
        mMoveResizeText = findViewById(R.id.tv_move_resize_txt);
        mRotationBar = (RotationSeekBar) findViewById(R.id.bar_rotation);
        mBtnUndoRotation = (Button) findViewById(R.id.btn_undo);

        btnFromGallery.setOnClickListener(btnFromGalleryListener);
        btnDone.setOnClickListener(btnDoneListerner);
        mBtnReset.setOnClickListener(btnResetListerner);
        mBtnUndoRotation.setOnClickListener(btnUndoRotationListener);

        mImageView.setImageBoundsListener(new IGetImageBounds() {
            @Override
            public Rect getImageBounds() {
                return mCropOverlayView.getImageBounds();
            }
        });

        // initialize rotation seek bar
        mRotationBar.setOnSeekBarChangeListener(new RotationSeekBar.OnRotationSeekBarChangeListener(mRotationBar) {

            private float mLastAngle;

            @Override
            public void onRotationProgressChanged(@NonNull RotationSeekBar seekBar, float angle, float delta, boolean fromUser) {
                mLastAngle = angle;
                if (fromUser) {
                    mImageView.setRotationBy(delta, false);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                if (Math.abs(mLastAngle) < ANCHOR_CENTER_DELTA) {
                    mRotationBar.reset();
                    mImageView.setRotationBy(0, true);
                }
            }
        });

        createTempFile();
        if (savedInstanceState == null || !savedInstanceState.getBoolean("restoreState")) {
            String action = getIntent().getStringExtra("ACTION");
            if (null != action) {
                switch (action) {
                    case com.jefferystudio.bankingsimulator.ImageCropper.GOTOConstants.IntentExtras.ACTION_GALLERY:
                        getIntent().removeExtra("ACTION");
                        pickImage();
                        return;
                }
            }
        }
        mImagePath = mFileTemp.getPath();
        mSaveUri = com.jefferystudio.bankingsimulator.ImageCropper.Utils.getImageUri(mImagePath);
        mImageUri = com.jefferystudio.bankingsimulator.ImageCropper.Utils.getImageUri(mImagePath);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {

        Bitmap bitmap = getBitmap(mImageUri);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);

        float minScale = mImageView.setMinimumScaleToFit(drawable);
        mImageView.setMaximumScale(minScale * 3);
        mImageView.setMediumScale(minScale * 2);
        mImageView.setScale(minScale);
        mImageView.setImageDrawable(drawable);

        //Initialize the MoveResize text
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mMoveResizeText.getLayoutParams();
        lp.setMargins(0, Math.round(Edge.BOTTOM.getCoordinate()) + 20, 0, 0);
        mMoveResizeText.setLayoutParams(lp);
    }

    private View.OnClickListener btnDoneListerner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            saveUploadCroppedImage();
        }
    };

    private void saveUploadCroppedImage() {

        Bitmap saved = saveOutput();

        String result = "";

        try {

            result = new UploadPicProgressBarAsync(this, userID).execute(saved).get(5000, TimeUnit.MILLISECONDS);
        }
        catch(Exception e) {


        }

        String[] resultArray = result.split(",");

        if(resultArray[0].equals("Successfully Uploaded")) {

            Intent intent = new Intent();
            //intent.putExtra(com.jefferystudio.bankingsimulator.ImageZoomCrop.GOTOConstants.IntentExtras.IMAGE_PATH, mImagePath);
            setResult(RESULT_OK, intent);
            finish();
        }
        else {

            Toast.makeText(this, "Unable to upload profile picture to the server.", Toast.LENGTH_LONG).show();
        }
    }

    private View.OnClickListener btnResetListerner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mRotationBar.reset();
            // init();
            mImageView.reset();
        }
    };

    private View.OnClickListener btnUndoRotationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mImageView.setRotationBy(0, true);
            mRotationBar.reset();
        }
    };

    private View.OnClickListener btnFromGalleryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (null == mFileTemp) {
                createTempFile();
            }

            pickImage();
        }
    };


    private void createTempFile() {
        /*String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }*/
        ContextWrapper cw = new ContextWrapper(this);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        mFileTemp = new File(directory, "temp_photo.jpg");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("restoreState", true);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        try {

            startActivityForResult(intent, REQUEST_CODE_PICK_GALLERY);
        }
        catch (ActivityNotFoundException e) {

            Toast.makeText(this, "No image source available", Toast.LENGTH_SHORT).show();
        }
    }

    private static void copyStream(InputStream input, OutputStream output) throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = input.read(buffer)) != -1) {

            output.write(buffer, 0, bytesRead);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        super.onActivityResult(requestCode, resultCode, result);

        createTempFile();

        if (requestCode == REQUEST_CODE_PICK_GALLERY) {

            if (resultCode == RESULT_CANCELED) {

                userCancelled();
                return;
            }
            else if (resultCode == RESULT_OK) {

                try {

                    InputStream inputStream = getContentResolver().openInputStream(result.getData()); // Got the bitmap .. Copy it to the temp file for cropping
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    mImagePath = mFileTemp.getPath();
                    mSaveUri = com.jefferystudio.bankingsimulator.ImageCropper.Utils.getImageUri(mImagePath);
                    mImageUri = com.jefferystudio.bankingsimulator.ImageCropper.Utils.getImageUri(mImagePath);
                    init();
                }
                catch (Exception e) {

                    Toast.makeText(this, "Error while opening the image file. Please try again.", Toast.LENGTH_LONG).show();
                    com.jefferystudio.bankingsimulator.ImageCropper.L.e(e);
                    return;
                }
            }
            else {

                Toast.makeText(this, "Error while opening the image file. Please try again.", Toast.LENGTH_LONG).show();
                return;
            }

        }
    }


    private Bitmap getBitmap(Uri uri) {

        InputStream in = null;
        Bitmap returnedBitmap = null;

        try {

            in = mContentResolver.openInputStream(uri);

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);

            in.close();
            int scale = 1;

            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = mContentResolver.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            //First check
            ExifInterface ei = new ExifInterface(uri.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:

                    returnedBitmap = rotateImage(bitmap, 90);
                    //Free up the memory
                    bitmap.recycle();
                    bitmap = null;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:

                    returnedBitmap = rotateImage(bitmap, 180);
                    //Free up the memory
                    bitmap.recycle();
                    bitmap = null;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:

                    returnedBitmap = rotateImage(bitmap, 270);
                    //Free up the memory
                    bitmap.recycle();
                    bitmap = null;
                    break;

                default:

                    returnedBitmap = bitmap;
            }

            return returnedBitmap;
        }
        catch (FileNotFoundException e) {

            com.jefferystudio.bankingsimulator.ImageCropper.L.e(e);
        }
        catch (IOException e) {

            com.jefferystudio.bankingsimulator.ImageCropper.L.e(e);
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                super.onBackPressed();
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }


    private Bitmap saveOutput() {

        Bitmap croppedImage = mImageView.getCroppedImage();
        /*if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = mContentResolver.openOutputStream(mSaveUri);
                if (outputStream != null) {
                    croppedImage.compress(mOutputFormat, 90, outputStream);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            } finally {
                closeSilently(outputStream);
            }
        } else {
            Log.e(TAG, "not defined image url");
            return false;
        }
        croppedImage.recycle();*/
        return croppedImage;
    }


    public void closeSilently(Closeable c) {

        if (c == null) return;

        try {

            c.close();
        }
        catch (Throwable t) {

            // do nothing
        }
    }


    private Bitmap rotateImage(Bitmap source, float angle) {

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public void userCancelled() {

        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);

        finish();
    }

    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
