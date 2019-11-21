package com.jefferystudio.bankingsimulator.ImageCropper.PhotoView;

import android.graphics.Rect;

/**
 * Used by the PhotoView for calculating the crop overlay bounds.
 */
public interface IGetImageBounds {

    /**
     * Returns the current image cropping bounds.
     *
     * @return A Rect with the current image bounds
     */
    Rect getImageBounds();
}