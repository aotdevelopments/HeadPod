package com.siestasystemheadpod.headpodv10.fragments.rasgos.element.bar;

import android.graphics.Color;
import android.graphics.Rect;

import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.AbstractRasgosElement;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.IndicatorColors;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.RasgosDesigner;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco.ArcoElement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by plaftware
 */

public class IndicatorBar extends AbstractRasgosElement {

    private AfterBottonOf afterBottonOf;

    private final List<Indicator> indicators;

    private final DecimalFormat formater;
    private Config config;

    public interface Config{

        int getBottomPercentaje();
    }

    public IndicatorBar(RasgosDesigner rasgosDesigner, Config config) {
        super(rasgosDesigner);
        this.config = config;

        formater = new DecimalFormat("#.#'%'");
        indicators = new ArrayList<>();

        initIndicator();
    }

    private void initIndicator() {
        // good
        indicators.add(new Indicator(IndicatorColors.COLOR_VERDE, TypeIndicador.GOOD));
        indicators.add(new Indicator(IndicatorColors.COLOR_AMARILLO, TypeIndicador.GOOD));

        // bad
        indicators.add(new Indicator(IndicatorColors.COLOR_NARANJA, TypeIndicador.BAD));
        indicators.add(new Indicator(IndicatorColors.COLOR_ROJO, TypeIndicador.BAD));
        indicators.add(new Indicator(IndicatorColors.COLOR_MORADO, TypeIndicador.BAD));
        indicators.add(new Indicator(IndicatorColors.COLOR_VIOLETA, TypeIndicador.BAD));
    }

    public interface AfterBottonOf{

        int getBotton();

    }

    private float getPorcentajeTexto(float length, boolean decimal){
        if(length == 100){
            return 11.09756f;
        }else if(length > 9){
            if(decimal)
                return 10.09756f;
            return 7.09756f;
        }
        if(decimal)
            return 6.09756f;
        return 4.09756f;
    }

    @Override
    public void draw() {
        // dibujamos barra
        int sumPorcentaje = getSumPorcentaje();

        if(sumPorcentaje > 0){

            setStrokeWidth(getRasgosDesigner().getHeight(0.5f));
            setColor(Color.parseColor("#55c5d0"));

            int left = 0;
            int top = afterBottonOf.getBotton();
            float rigth = (int) getRasgosDesigner().getWidth();
            int bottom = top + (int)getRasgosDesigner().getHeight(config.getBottomPercentaje());

            // dibujamos borde
            setStyle(Style.STROKE);
            Rect rect = new Rect(left, top, (int) rigth, bottom);
            getRasgosDesigner().getCanvas().drawRect(rect, this);


            // rellenados
            setStyle(Style.FILL);

            left = left + (int) getStrokeWidth();
            top = top + (int) getStrokeWidth();
            bottom = bottom - (int) getStrokeWidth();

            int good = 0, bad = 0;
            float textCenterHeigt = getRasgosDesigner().getHeight(1.121f);

            float widthStroke = getStrokeWidth() * 2;
            float widthBar = rigth - widthStroke;

            for (Indicator indicator : indicators) {
                if(indicator.getPorcentaje() > 0){
                    float porcentaje = indicator.getPorcentaje() * 100f / sumPorcentaje;

                    float width = (widthBar * porcentaje / 100f);
                    int newRigth = Math.round(width) + left;

                    setColor(indicator.color);
                    rect.set(left, top, newRigth, bottom);
                    getRasgosDesigner().getCanvas().drawRect(rect, this);

                    // Agregamos texto
                    String porcentajeFormat = formater.format(porcentaje);
                    int textWidth = (int)(rigth * getPorcentajeTexto(porcentaje, porcentajeFormat.matches(".+(\\.|\\,).+")) / 100f);
                    if(width > textWidth){
                        RasgosDesigner.setTextSizeForWidth(this, textWidth, porcentajeFormat);
                        setColor(Color.BLACK);
                        rasgosDesigner.getCanvas().drawText(porcentajeFormat,
                                (left + width / 2) - (textWidth / 2), // x
                                top + textCenterHeigt + (bottom - top) / 2, // y
                                this);
                    }

                    if(indicator.typeIndicador == TypeIndicador.GOOD) {
                        good += width;
                    }else{
                        bad += width;
                    }

                    left = newRigth;
                }
            }

            // dibujamos indicadores buenos y malos
            int y = bottom + (int)(getRasgosDesigner().getHeight() * 3.0525f / 100f);
            int yText = y + (int)(getRasgosDesigner().getHeight() * 3.663f / 100f);

            int total = good + bad;

            if(good > 0){
                int widthGood = good +  (bad == 0 ? (int)widthStroke : 0);
                float goodPorcentaje = good * 100f / total;
                String goodPorc = formater.format(goodPorcentaje) + " Good";
                setColor(IndicatorColors.COLOR_VERDE);


                // linea
                rasgosDesigner.getCanvas().drawLine(0, y, widthGood, y, this);

                // text
                float indicatorTextWidth = (widthBar * 18.29268f / 100f);
                float indicatorTextWidthCenter = indicatorTextWidth / 2;
                RasgosDesigner.setTextSizeForWidth(this, indicatorTextWidth, goodPorc);
                rasgosDesigner.getCanvas().drawText(goodPorc, indicatorTextWidth < widthGood ? (widthGood / 2) - indicatorTextWidthCenter : 0,
                        yText, this);

                // separators
                rasgosDesigner.getCanvas().drawLine(0, y - 15, 0, y, this);
                rasgosDesigner.getCanvas().drawLine(widthGood, y - 15, widthGood, y, this);
            }

            if(bad > 0){
                int space = good > 0 ? 4 : 0;
                float badPorcentaje = bad * 100f / total;
                int startBad = good + space;
                int widthBad = bad + (int)widthStroke - space;
                int stopX = startBad + widthBad;
                String badPorc = formater.format(badPorcentaje) + " Bad";
                setColor(IndicatorColors.COLOR_ROJO);

                // linea
                rasgosDesigner.getCanvas().drawLine(startBad, y, stopX, y, this);

                // text
                float indicatorTextWidth = (widthBar * 16.29268f / 100f);
                float indicatorTextWidthCenter = indicatorTextWidth / 2;

                RasgosDesigner.setTextSizeForWidth(this, indicatorTextWidth - space, badPorc);

                float startX = indicatorTextWidth < widthBad ? startBad + (bad / 2) - indicatorTextWidthCenter :
                        stopX - indicatorTextWidth;
                //indicatorTextWidth < widthBar ? yText : startBad

                rasgosDesigner.getCanvas().drawText(badPorc, startX,
                        yText, this);

                // separators
                rasgosDesigner.getCanvas().drawLine(startBad, y - 15, startBad, y, this);
                rasgosDesigner.getCanvas().drawLine(stopX, y - 15, stopX, y, this);
            }
        }
    }

    private int getSumPorcentaje(){
        int sumPorcentaje = 0;
        for (Indicator indicator: indicators) {
          sumPorcentaje += indicator.getPorcentaje();
        }
        return sumPorcentaje;
    }

    public void indicador(int indicador, int porcentaje){
       switch (indicador){
           case 6:
               indicators.get(0).setPorcentaje(porcentaje, true);
               break;
           case 5:
           case 7:
               indicators.get(1).setPorcentaje(porcentaje, indicador == 5);
               break;
           case 1:
           case 11:
               indicators.get(5).setPorcentaje(porcentaje, indicador == 1);
               break;
           case 2:
           case 10:
               indicators.get(4).setPorcentaje(porcentaje, indicador == 2);
               break;
           case 3:
           case 9:
               indicators.get(3).setPorcentaje(porcentaje, indicador == 3);
               break;
           case 4:
           case 8:
               indicators.get(2).setPorcentaje(porcentaje, indicador == 4);
               break;
       }
    }

    public void setAfterBottonOf(AfterBottonOf afterBottonOf) {
        this.afterBottonOf = afterBottonOf;
    }


    private enum TypeIndicador{
        GOOD, BAD
    }

    private class Indicator{
        int porcentaje1;
        int porcentaje2;
        int color;
        TypeIndicador typeIndicador;

        Indicator(int color, TypeIndicador typeIndicador) {
            this.color = color;
            this.typeIndicador = typeIndicador;
        }

        void setPorcentaje(int porcentaje, boolean isFirts){
            if(isFirts){
                porcentaje1 = porcentaje;
                return;
            }

            porcentaje2 = porcentaje;
        }

        float getPorcentaje(){
            if(porcentaje2 > 0){
                float p1 = porcentaje1 * 100f / 200f;
                float p2 = porcentaje2 * 100f / 200f;
                return p1 + p2;
            }
            return porcentaje1 + porcentaje2;
        }

    }
}
