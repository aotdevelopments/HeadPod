package com.siestasystemheadpod.headpodv10.adicionales;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;


/**
 * Created by Michael on 03/02/2017.
 * Esta clase servira para obtener las dimensiones de la pantalla del dispositivo
 * El objetivo es poder adaptar algunos tamaños en tiempo de ejecución para que encajen correctamente
 * en las dimensiones de la pantalla.
 */

public class TamanoPantalla {

    //Tamanño de la pantalla del dispositivo
    private int heightPixels;
    private int widthPixels;
    private int densityDpi;
    private float density;
    //Pulgadas de la pantalla
    private double screenInchesPulgadas;





    public TamanoPantalla(Activity activity)
    {
        //DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        //activity.getApplicationContext().getResources().getDisplayMetrics().density;
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        heightPixels = dm.heightPixels;
        widthPixels = dm.widthPixels;
        densityDpi = dm.densityDpi;
        density = dm.density;


        Log.d("Tamano","Ancho de la Pantalla hpix: "+Double.toString(heightPixels));

        Log.d("Tamano","Alto de la Pantalla wpix: "+Double.toString(widthPixels));

        Log.d("Tamano","desnsidad de la Pantalla (dpi) dpix: "+Double.toString(densityDpi));

        Log.d("Tamano", "Densidad a secas: "+ Double.toString(density));


        density= density * 160;
        double x = Math.pow(widthPixels / density, 2);
        double y = Math.pow(heightPixels / density, 2);

        screenInchesPulgadas = Math.sqrt(x + y);
        // screenInchesPulgadas=  (double)Math.round(screenInchesPulgadas * 10) / 10;

        Log.d("Tamano","Pulgadas: "+Double.toString(screenInchesPulgadas));


        detectarDpi();
    }


    public double getScreenInchesPulgadas()
    {
        return screenInchesPulgadas;
    }

    public int getHeightPixels()
    {
        return heightPixels;
    }

    public int getWidthPixels()
    {
        return widthPixels;
    }

    public int getDensityDpi()
    {
        return densityDpi;
    }

    public float getDensity()
    {
        return density;
    }


    public void detectarDpi()
    {

        float scale = (float) density;

        // buscando los pixeles a partir de dips con la densidad
        int dips = 200;
        float pixelBoton = 0;
        float scaleDensity = 0;


        switch(densityDpi)
        {


            case DisplayMetrics.DENSITY_XXXHIGH:
                Log.d("Tamano","Alta xxxhdpi");

                scaleDensity = scale * 640;
                pixelBoton = dips * (scaleDensity / 640);

                break;

            case DisplayMetrics.DENSITY_XXHIGH:

                Log.d("Tamano","Alta xxhdpi");

                scaleDensity = scale * 480;
                pixelBoton = dips * (scaleDensity / 480);

                break;

            case DisplayMetrics.DENSITY_XHIGH:

                Log.d("Tamano","Alta xhdpi");

                scaleDensity = scale * 320;
                pixelBoton = dips * (scaleDensity / 320);

                break;

            case DisplayMetrics.DENSITY_HIGH: //HDPI
                //text5.setText("Alta Densidad");

                Log.d("Tamano","Alta densdiad");
                scaleDensity = scale * 240;
                pixelBoton = dips * (scaleDensity / 240);
                break;
            case DisplayMetrics.DENSITY_MEDIUM: //MDPI

                Log.d("Tamano","mediana densidad");
                //text5.setText("mediana Densidad");
                scaleDensity = scale * 160;
                pixelBoton = dips * (scaleDensity / 160);
                break;

            case DisplayMetrics.DENSITY_LOW:  //LDPI
                //text5.setText("baja Densidad");

                Log.d("Tamano","baja densdiad");
                scaleDensity = scale * 120;
                pixelBoton = dips * (scaleDensity / 120);
                break;
        }
        Log.d(getClass().getSimpleName(), "pixels:"+Float.toString(pixelBoton));

    }



}