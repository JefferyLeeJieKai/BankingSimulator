package com.jefferystudio.bankingsimulator.ImageCropper;

import android.net.Uri;

import java.io.File;

/**
 * @author GT
 */
public class Utils {

    public static Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }
}
