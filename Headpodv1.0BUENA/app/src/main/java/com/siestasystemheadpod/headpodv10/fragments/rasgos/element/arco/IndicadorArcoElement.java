package com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco;

import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;

import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.RasgosDesigner;

/**
 * Created by plaftware
 */

class IndicadorArcoElement extends AbstractItemArcoElement {

    private final int startAngle0;
    private final int endAngle0;

    private final double startAngle;
    private final double endAngle;
    private final double angleWidthRadians;
    private final float angleWidth;

    private String startLabel;
    private String endLabel;

    private boolean startBar;
    private boolean endBar;

    private int MAX_LEVEL;

    private final int COLOR_INDICADOR;

    // Definimos la altura de los separadores
    private int HEIGHT_SEPARATOR;

    IndicadorArcoElement(ArcoElement arcoElement, int startAngle, int endAngle, int colorIndicador) {
        super(arcoElement);

        this.startAngle0 = startAngle;
        this.endAngle0 = endAngle;

        this.startAngle = rasgosDesigner.getAngle(startAngle);
        this.endAngle = rasgosDesigner.getAngle(endAngle);

        this.angleWidth = startAngle - endAngle;
        this.angleWidthRadians = Math.toRadians(angleWidth);
        this.COLOR_INDICADOR = colorIndicador;
    }

    @Override
    public void draw() {
        MAX_LEVEL = HEIGHT_SEPARATOR = (int) arcoElement.getBarHeigth();

        int centerArcoX = arcoElement.getCenterArcoX();
        int centerArcoY = arcoElement.getCenterArcoY();
        float radius = arcoElement.getRadius();

        float startX0 = (float) (centerArcoX + radius * Math.sin(startAngle));
        float startY0 = (float) (centerArcoY + radius * Math.cos(startAngle));

        float endX0 = (float) (centerArcoX + radius * Math.sin(endAngle));
        float endY0 = (float) (centerArcoY + radius * Math.cos(endAngle));

        this.drawSeparators(startX0, startY0, endX0, endY0);
        this.drawIndicador();
    }

    IndicadorArcoElement setStartLabel(String startLabel){
        this.startLabel = startLabel;
        this.startBar = !TextUtils.isEmpty(startLabel);
        return this;
    }

    IndicadorArcoElement setEndLabel(String endLabel){
        this.endLabel = endLabel;
        this.endBar = !TextUtils.isEmpty(endLabel);
        return this;
    }

    private void drawIndicador(){
      if(porcentaje > 0){
          setColor(COLOR_INDICADOR);
          setStyle(Paint.Style.STROKE);

          int strokeLevel = 5;
          int level = (MAX_LEVEL * porcentaje / 100) * strokeLevel;

          int centerArcoX = arcoElement.getCenterArcoX();
          int centerArcoY = arcoElement.getCenterArcoY();
          float radius = arcoElement.getRadius() + 2.5f;

          float startBX = (float) (centerArcoX + radius * Math.sin(startAngle));
          float startBY = (float) (centerArcoY + radius * Math.cos(startAngle));

          float endBX = (float) (centerArcoX + radius * Math.sin(endAngle));
          float endBY = (float) (centerArcoY + radius * Math.cos(endAngle));

          //this.drawCurve(startBX, startBY, endBX, endBY);
          Paint paint = new Paint();
          paint.setStyle(Paint.Style.STROKE);
          paint.setColor(COLOR_INDICADOR);
          paint.setStrokeWidth(2);

          double newStartAngle = startAngle0 - arcoElement.getBarWidth();
          double newEndAngle = endAngle0 + arcoElement.getBarWidth();

          double newStartAngleRadians = rasgosDesigner.getAngle(newStartAngle);
          double newEndAngleRadians = rasgosDesigner.getAngle(newEndAngle);

          float newAngleWidth = (float) (newStartAngle - newEndAngle);
          double newAngleWidthRadians = Math.toRadians(newAngleWidth);

          int flag = (HEIGHT_SEPARATOR * 90 / 100);

          for (int level0 = 1; level0 <= level; level0++) {
              float startBX0 =  startBX;
              float startBY0 =  startBY;
              float endBX0 =  endBX;
              float endBY0 =  endBY;

              float angleWidth = this.angleWidth;
              double angleWidthRadians = this.angleWidthRadians;

              if(level0 < flag){
                  startBX0 = (float) (centerArcoX + radius * Math.sin(newStartAngleRadians));
                  startBY0 = (float) (centerArcoY + radius * Math.cos(newStartAngleRadians));

                  endBX0 = (float) (centerArcoX + radius * Math.sin(newEndAngleRadians));
                  endBY0 = (float) (centerArcoY + radius * Math.cos(newEndAngleRadians));

                  angleWidth = newAngleWidth;
                  angleWidthRadians = newAngleWidthRadians;
              }

              float startTX = (float) (startBX0 + level0 * Math.sin(startAngle));
              float startTY = (float) (startBY0 + level0 * Math.cos(startAngle));

              float endTX = (float) (endBX0 + level0 * Math.sin(endAngle));
              float endTY = (float) (endBY0 + level0 * Math.cos(endAngle));

              this.drawCurve(paint, startTX, startTY, endTX, endTY, angleWidthRadians, angleWidth);
          }
      }
    }

    private void drawCurve(Paint paint, float e1x, float e1y, float e2x, float e2y, double angleRadians, float angleWidth){
        double dx = e2x - e1x, dy = e2y - e1y;
        double l = Math.sqrt((dx * dx) + (dy * dy));
        double l1 = l / 2.0;

        double h = l1 / (Math.tan(angleRadians / 2.0));
        double r = l1 / (Math.sin(angleRadians / 2.0));

        // a2 is the angleWidthRadians at which L intersects the x axis
        double a2 = Math.atan2(dy, dx);

        // a3 is the angleWidthRadians at which H intersects the x axis
        double a3 = (Math.PI / 2.0) - a2;

        // m is the midpoint of the line from e1 to e2
        double mX = (e1x + e2x) / 2.0;
        double mY = (e1y + e2y) / 2.0;

        // c is the the center of the circle
        double cY = mY + (h * Math.sin(a3));
        double cX = mX - (h * Math.cos(a3));

        RectF oval = new RectF((float) (cX - r), (float) (cY - r), (float) (cX + r), (float) (cY + r));

        // a4 is the starting sweep angleWidthRadians
        double rawA4 = Math.atan2(e1y - cY, e1x - cX);
        float a4 = (float) Math.toDegrees(rawA4);


        rasgosDesigner.getCanvas().drawArc(oval, a4, angleWidth, false, paint);
    }


    private void drawSeparators(float startX0, float startY0, float endX0, float endY0){
        setColor(arcoElement.getArcoColor());
        setStyle(Style.FILL);
        setStrokeWidth(arcoElement.getRadius() * 2.4f / 100f);

        rasgosDesigner.getCanvas().save();

        if(startBar){
            float startX = (float) (startX0 + HEIGHT_SEPARATOR * Math.sin(startAngle));
            float startY = (float) (startY0 + HEIGHT_SEPARATOR * Math.cos(startAngle));
            rasgosDesigner.getCanvas().drawLine(startX0, startY0, startX, startY, this);

            if(!TextUtils.isEmpty(startLabel)){
                double textStartAngle = rasgosDesigner.getAngle(startAngle0);

                startX = (float) (startX0 - (HEIGHT_SEPARATOR - (HEIGHT_SEPARATOR / 2)) * Math.sin(textStartAngle));
                startY = (float) (startY0 - (HEIGHT_SEPARATOR + (HEIGHT_SEPARATOR / 2)) * Math.cos(textStartAngle));

                RasgosDesigner.setTextSizeForWidth(this, arcoElement.getIndicadorFontSize(), startLabel);

                //rasgosDesigner.getCanvas().rotate((startAngle0 / 2) * -1, startX, startY);
                rasgosDesigner.getCanvas().drawText(startLabel, startX, startY, this);
                //rasgosDesigner.getCanvas().restore();
            }
        }


        if(endBar){
            float endX = (float) (endX0 + HEIGHT_SEPARATOR * Math.sin(endAngle));
            float endY = (float) (endY0 + HEIGHT_SEPARATOR * Math.cos(endAngle));
            rasgosDesigner.getCanvas().drawLine(endX0, endY0, endX, endY, this);

            if(!TextUtils.isEmpty(endLabel)){
                double textEndAngle = rasgosDesigner.getAngle(endAngle0);

                endX = (float) (endX0 - (HEIGHT_SEPARATOR * 2) * Math.sin(textEndAngle));
                endY = (float) (endY0 - (HEIGHT_SEPARATOR + (HEIGHT_SEPARATOR / 2)) * Math.cos(textEndAngle));

                RasgosDesigner.setTextSizeForWidth(this, arcoElement.getIndicadorFontSize(), endLabel);
                rasgosDesigner.getCanvas().drawText(endLabel, endX, endY, this);
            }
        }
    }

    IndicadorArcoElement setStartBar(boolean startBar) {
        this.startBar = startBar;
        return this;
    }

    IndicadorArcoElement setEndBar(boolean endBar) {
        this.endBar = endBar;
        return this;
    }
}
