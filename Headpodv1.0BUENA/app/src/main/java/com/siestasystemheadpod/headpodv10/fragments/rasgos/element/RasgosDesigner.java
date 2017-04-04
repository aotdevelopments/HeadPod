package com.siestasystemheadpod.headpodv10.fragments.rasgos.element;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * Clase que se encarga de todas las caracteristicas del diseño
 *
 * Created by plaftware
 */

public class RasgosDesigner {

    private final Context context;
    private Canvas canvas;
    private float width;
    private float height;
    private float centerX;
    private float centerY;

    private List<RasgosElement> entities;

    public RasgosDesigner(Context context) {
        this.context = context;
        entities = new LinkedList<>();
    }

    public void target(Canvas canvas){
        if(canvas != null){
            this.canvas = canvas;
            this.canvas.drawColor(0, PorterDuff.Mode.CLEAR);

            width = (float) canvas.getWidth();
            height = (float) canvas.getHeight();

            centerX = width / 2;
            centerY = height / 2;

            // dibujamos elementos
            for (RasgosElement rasgosElement : entities) {
                rasgosElement.draw();
            }
        }
    }

    RasgosDesigner add(RasgosElement rasgosElement){
        entities.add(rasgosElement);
        return this;
    }

    public float getWidth(float percentage){
        return width * percentage / 100;
    }

    public float getHeight(float percentage){
        return height * percentage / 100;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public Context getContext() {
        return context;
    }

    public double getAngle(double angle){
        return Math.toRadians(90 + angle);
    }

    public static void setTextSizeForWidth(Paint paint, float desiredWidth,
                                            String text) {
        final float testTextSize = 48f;
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();
        paint.setTextSize(desiredTextSize);
    }
}
