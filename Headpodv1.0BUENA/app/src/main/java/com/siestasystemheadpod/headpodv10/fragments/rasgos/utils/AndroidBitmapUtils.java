package com.siestasystemheadpod.headpodv10.fragments.rasgos.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by plaftware
 */

public class AndroidBitmapUtils {

    static {
        if(!AppCompatDelegate.isCompatVectorFromResourcesEnabled()){
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }
    }

    /*public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId, int width, int height){
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap bitmap = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }*/

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId, int width, int height){
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap bitmap = Bitmap.createBitmap(getDim(width),
                getDim(height), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private static int getDim(int dim){
        return dim == 0 ? 100 : dim;
    }





}
