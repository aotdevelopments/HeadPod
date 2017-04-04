package com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.AbstractRasgosElement;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.IndicatorColors;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.RasgosDesigner;

/**
 * Created by plaftware
 */

public class ArcoElement extends AbstractRasgosElement {

    private final RectF oval = new RectF();
    private float radius;
    private int centerArcoX;
    private int centerArcoY;
    private float indicadorFontSize;

    private float barHeigth;
    private float barWidth;

    // colores items


    private List<ArcoItemElement> arcoItemElements;
    private final int arcoColor = Color.parseColor("#55c5d0");

    public ArcoElement(RasgosDesigner rasgosDesigner) {
        super(rasgosDesigner);

        setStyle(Paint.Style.STROKE);
        setStrokeWidth(5);
        setColor(arcoColor);

        arcoItemElements = new ArrayList<>();
        this.initIndicadores();
    }

    private void initIndicadores(){
        // izquierdo
        new IndicadorArcoElement(this, 180, 160, IndicatorColors.COLOR_VIOLETA)
                .setStartBar(true);

        new IndicadorArcoElement(this, 160, 145,  IndicatorColors.COLOR_MORADO)
                .setStartLabel("70°");

        new IndicadorArcoElement(this, 145, 130,  IndicatorColors.COLOR_ROJO)
                .setStartLabel("55°");

        new IndicadorArcoElement(this, 130, 115,  IndicatorColors.COLOR_NARANJA)
                .setStartLabel("40°");

        new IndicadorArcoElement(this, 115, 100,  IndicatorColors.COLOR_AMARILLO)
                .setStartLabel("25°");

        // centro
        new IndicadorArcoElement(this, 100, 80,  IndicatorColors.COLOR_VERDE)
                .setStartLabel("10°")
                .setEndLabel("10°");
        // centro

        // derecho
        new IndicadorArcoElement(this, 80, 65,  IndicatorColors.COLOR_AMARILLO)
                .setEndLabel("25°");

        new IndicadorArcoElement(this, 65, 50,  IndicatorColors.COLOR_NARANJA)
                .setEndLabel("40°");

        new IndicadorArcoElement(this, 50, 35,  IndicatorColors.COLOR_ROJO)
                .setEndLabel("55°");

        new IndicadorArcoElement(this, 35, 20,  IndicatorColors.COLOR_MORADO)
                .setEndLabel("70°");

        new IndicadorArcoElement(this, 20, 0,  IndicatorColors.COLOR_VIOLETA)
                .setEndBar(true);
    }

    ArcoElement add(ArcoItemElement arcoItemElement){
        arcoItemElements.add(arcoItemElement);
        return this;
    }

    public void reset(){
        for (ArcoItemElement arcoItemElement: arcoItemElements) {
            arcoItemElement.setPorcentaje(0);
        }
    }

    public void indicador(int indicador, int porcentaje){
        if(arcoItemElements.size() < indicador)
            throw new IllegalArgumentException("Indicador no valido " + indicador);

        ArcoItemElement arcoItemElement = arcoItemElements.get(indicador - 1);
        arcoItemElement.setPorcentaje(porcentaje);
    }

    @Override
    public void draw() {
        int PERCENT_IN_DESKTOP = 35;
        if (rasgosDesigner.getWidth() > rasgosDesigner.getHeight()){
            radius = rasgosDesigner.getHeight(PERCENT_IN_DESKTOP);
        }else{
            radius = rasgosDesigner.getWidth(PERCENT_IN_DESKTOP);
        }

        //Log.i("Radio: ", radius + "");

        indicadorFontSize = radius * 10f / 100f;
        barHeigth = radius * 8f / 100f;
        barWidth = radius * 0.2f / 100f;

        centerArcoX = (int) rasgosDesigner.getCenterX();
        centerArcoY = (int) (rasgosDesigner.getCenterY() - (rasgosDesigner.getCenterY() * 2 / 100));

        for (ArcoItemElement arcoItemElement: arcoItemElements) {
            arcoItemElement.draw();
        }


        oval.set(centerArcoX - radius,
                centerArcoY - radius,
                centerArcoX + radius,
                centerArcoY + radius);

        rasgosDesigner.getCanvas().drawArc(oval, 0, -180, false, this);
    }

    float getRadius() {
        return radius;
    }

    int getCenterArcoX() {
        return centerArcoX;
    }

    int getCenterArcoY() {
        return centerArcoY;
    }

    int getArcoColor() {
        return arcoColor;
    }

    float getIndicadorFontSize() {
        return indicadorFontSize;
    }

    float getBarHeigth() {
        return barHeigth;
    }

    float getBarWidth() {
        return barWidth;
    }
}
