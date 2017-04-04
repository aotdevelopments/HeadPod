package com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos;

import android.util.Log;

/**
 * Created by Michael on 27/03/2017.
 *
 * Esta clase devolverá el angulo de la INCLINACIÓN
 * El ángulo de la flexión debido al rango estimado de +-16g, no será preciso
 * así que devolveremos solo su valor entero.
 *
 */

public class AnguloZ {


    private int angulo;




    public AnguloZ()
    {
    }


    //Obtención del angulo Y  con respecto a la gravedad.
    //Debido a la falta de presición del sensor necesitamos que el angulo sea entero.
    public int getAnguloZ(int accelLsbZ)
    {

        //obtiene el valor del eje x y z
        int dataXY[]=getDataXY(accelLsbZ);

        //calculo del ángulo z (Inclinación)

        angulo = (int)((float) Math.atan(accelLsbZ / Math.sqrt(Math.pow(dataXY[0], 2) + Math.pow(dataXY[1], 2))) * (float) (180.0 / Math.PI));
        return angulo;

    }



    //Método que asigna los ejes X y Z en función del ejeY
    private int[] getDataXY(int accelLsbZ)
    {

        int[] dataXY = new int[2];
        //dataXY[0]=>accelLsbX
        //dataXY[1]=>accelLsbY

        //Trabajamos con el modulo para evitar el cambio de signo
        if(accelLsbZ<0)
        {
            accelLsbZ=accelLsbZ*-1;
        }


        switch (accelLsbZ)
        {
            case 0:
                dataXY[0]=31;
                dataXY[1]=1;
                break;
            case 1:
                dataXY[0]=31;
                dataXY[1]=2;
                break;
            case 2:
                dataXY[0]=31;
                dataXY[1]=2;
                break;
            case 3:
                dataXY[0]=31;
                dataXY[1]=2;
                break;
            case 4:
                dataXY[0]=31;
                dataXY[1]=2;
                break;
            case 5:
                dataXY[0]=31;
                dataXY[1]=2;
                break;
            case 6:
                dataXY[0]=30;
                dataXY[1]=3;
                break;
            case 7:
                dataXY[0]=29;
                dataXY[1]=4;
                break;
            case 8:
                dataXY[0]=29;
                dataXY[1]=3;
                break;
            case 9:
                dataXY[0]=29;
                dataXY[1]=2;
                break;
            case 10:
                dataXY[0]=28;
                dataXY[1]=3;
                break;
            case 11:
                dataXY[0]=27;
                dataXY[1]=3;
                break;
            case 12:
                dataXY[0]=28;
                dataXY[1]=3;
                break;
            case 13:
                dataXY[0]=27;
                dataXY[1]=3;
                break;
            case 14:
                dataXY[0]=27;
                dataXY[1]=3;
                break;
            case 15://29º
                dataXY[0]=26;
                dataXY[1]=3;
                break;
            case 16://31º
                dataXY[0]=26;
                dataXY[1]=4;
                break;
            case 17://33º
                dataXY[0]=25;
                dataXY[1]=4;
                break;
            case 18://35º
                dataXY[0]=25;
                dataXY[1]=4;
                break;
            case 19://37
                dataXY[0]=24;
                dataXY[1]=4;
                break;
            case 20://39
                dataXY[0]=24;
                dataXY[1]=5;
                break;
            case 21://41
                dataXY[0]=23;
                dataXY[1]=5;
                break;
            case 22://43º
                dataXY[0]=23;
                dataXY[1]=5;
                break;
            case 23://45º
                dataXY[0]=22;
                dataXY[1]=5;
                break;
            case 24://50º
                dataXY[0]=19;
                dataXY[1]=5;
                break;
            case 25://52º
                dataXY[0]=19;
                dataXY[1]=4;
                break;
            case 26://54º
                dataXY[0]=18;
                dataXY[1]=4;
                break;
            case 27://58º
                dataXY[0]=16;
                dataXY[1]=3;
                break;
            case 28://62º
                dataXY[0]=14;
                dataXY[1]=3;
                break;
            case 29://65º
                dataXY[0]=13;
                dataXY[1]=3;
                break;
            case 30://67º
                dataXY[0]=12;
                dataXY[1]=3;
                break;
            case 31://74º
                dataXY[0]=8;
                dataXY[1]=3;
                break;
            case 32://78º
                dataXY[0]=6;
                dataXY[1]=3;
                break;

        }


        return dataXY;


    }










}
