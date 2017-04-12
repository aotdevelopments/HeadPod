package com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

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

    private int [] gradeArc;

    // colores items
    public interface Config{

        int[] getIndicadorGrade(int index);

        int[] getArcGrade();
    }


    private List<ArcoItemElement> arcoItemElements;
    private final int arcoColor = Color.parseColor("#55c5d0");

    public ArcoElement(@NonNull RasgosDesigner rasgosDesigner, @NonNull Config config) {
        super(rasgosDesigner);
        this.gradeArc = config.getArcGrade();

        setStyle(Paint.Style.STROKE);
        setStrokeWidth(5);
        setColor(arcoColor);

        arcoItemElements = new ArrayList<>();
        this.initIndicadores(config);
    }

    private void initIndicadores(Config config){
        // izquierdo
        int[] grade = config.getIndicadorGrade(0);
        new IndicadorArcoElement(this, grade[0], grade[1], IndicatorColors.COLOR_VIOLETA)
                .setStartBar(true);

        grade = config.getIndicadorGrade(1);
        new IndicadorArcoElement(this, grade[0], grade[1],  IndicatorColors.COLOR_MORADO)
                .setStartLabel("70°");

        grade = config.getIndicadorGrade(2);
        new IndicadorArcoElement(this, grade[0], grade[1],  IndicatorColors.COLOR_ROJO)
                .setStartLabel("55°");

        grade = config.getIndicadorGrade(3);
        new IndicadorArcoElement(this, grade[0], grade[1],  IndicatorColors.COLOR_NARANJA)
                .setStartLabel("40°");

        grade = config.getIndicadorGrade(4);
        new IndicadorArcoElement(this, grade[0], grade[1],  IndicatorColors.COLOR_AMARILLO)
                .setStartLabel("25°");

        // centro
        grade = config.getIndicadorGrade(5);
        new IndicadorArcoElement(this, grade[0], grade[1],  IndicatorColors.COLOR_VERDE)
                .setStartLabel("10°")
                .setEndLabel("10°");
        // centro

        // derecho
        grade = config.getIndicadorGrade(6);
        new IndicadorArcoElement(this, grade[0], grade[1],  IndicatorColors.COLOR_AMARILLO)
                .setEndLabel("25°");

        grade = config.getIndicadorGrade(7);
        new IndicadorArcoElement(this, grade[0], grade[1],  IndicatorColors.COLOR_NARANJA)
                .setEndLabel("40°");

        grade = config.getIndicadorGrade(8);
        new IndicadorArcoElement(this, grade[0], grade[1],  IndicatorColors.COLOR_ROJO)
                .setEndLabel("55°");

        grade = config.getIndicadorGrade(9);
        new IndicadorArcoElement(this, grade[0], grade[1],  IndicatorColors.COLOR_MORADO)
                .setEndLabel("70°");

        grade = config.getIndicadorGrade(10);
        new IndicadorArcoElement(this, grade[0], grade[1],  IndicatorColors.COLOR_VIOLETA)
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

        rasgosDesigner.getCanvas().drawArc(oval, gradeArc[0], gradeArc[1], false, this);
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
