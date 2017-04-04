package com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos;

import android.util.Log;

/**
 * Created by Michael on 27/03/2017.
 *
 * Esta clase devolverá el angulo de la FLEXIÓN.
 * El ángulo de la flexión debido al rango estimado de +-16g, no será preciso
 * así que devolveremos solo su valor entero.
 *
 */

public class AnguloY {


    private int angulo;


    public AnguloY()
    {
    }


    //Obtención del angulo Y  con respecto a la gravedad.
    //Debido a la falta de presición del sensor necesitamos que el angulo sea entero.
    public int getAnguloY(int accelLsbY)
    {
        //obtiene el valor del eje x y z
        int dataXZ[]=getDataXZ(accelLsbY);
        //Actualizamos el angulo
        angulo = (int)((float) Math.atan(accelLsbY / Math.sqrt(Math.pow(dataXZ[0], 2) + Math.pow(dataXZ[1], 2))) * (float) (180.0 / Math.PI));
        return angulo;
    }



    //Método que asigna los ejes X y Z en función del ejeY
    private int[] getDataXZ(int accelLsbY)
    {

        int[] dataXZ = new int[2];
        //dataXZ[0]=x
        //dataXZ[1]=z


        //Trabajamos con el modulo
        if(accelLsbY<0)
        {
            accelLsbY=accelLsbY*-1;
        }


        switch (accelLsbY)
        {
            case 0:
                dataXZ[0]=31;
                dataXZ[1]=1;
                break;
            case 1:
                dataXZ[0]=31;
                dataXZ[1]=1;
                break;
            case 2:
                dataXZ[0]=31;
                dataXZ[1]=1;
                break;
            case 3:
                dataXZ[0]=31;
                dataXZ[1]=1;
                break;
            case 4:
                dataXZ[0]=31;
                dataXZ[1]=1;
                break;
            case 5:
                dataXZ[0]=31;
                dataXZ[1]=1;
                break;
            case 6:
                dataXZ[0]=30;
                dataXZ[1]=1;
                break;
            case 7:
                dataXZ[0]=29;
                dataXZ[1]=2;
                break;
            case 8:
                dataXZ[0]=29;
                dataXZ[1]=2;
                break;
            case 9:
                dataXZ[0]=29;
                dataXZ[1]=2;
                break;
            case 10:
                dataXZ[0]=29;
                dataXZ[1]=1;
                break;
            case 11://21º
                dataXZ[0]=28;
                dataXZ[1]=1;
                break;
            case 12://23º
                dataXZ[0]=28;
                dataXZ[1]=1;
                break;
            case 13://25º
                dataXZ[0]=27;
                dataXZ[1]=1;
                break;
            case 14://27º
                dataXZ[0]=27;
                dataXZ[1]=1;
                break;
            case 15://29º
                dataXZ[0]=26;
                dataXZ[1]=1;
                break;
            case 16://31º
                dataXZ[0]=26;
                dataXZ[1]=2;
                break;
            case 17://33º
                dataXZ[0]=26;
                dataXZ[1]=2;
                break;
            case 18://35º
                dataXZ[0]=25;
                dataXZ[1]=2;
                break;
            case 19://37º
                dataXZ[0]=25;
                dataXZ[1]=1;
                break;
            case 20://39º
                dataXZ[0]=24;
                dataXZ[1]=2;
                break;
            case 21://42º
                dataXZ[0]=23;
                dataXZ[1]=2;
                break;
            case 22://44º
                dataXZ[0]=22;
                dataXZ[1]=2;
                break;
            case 23://47º
                dataXZ[0]=21;
                dataXZ[1]=2;
                break;
            case 24://51º
                dataXZ[0]=19;
                dataXZ[1]=1;
                break;
            case 25://54º
                dataXZ[0]=18;
                dataXZ[1]=1;
                break;
            case 26://58º
                dataXZ[0]=16;
                dataXZ[1]=1;
                break;
            case 27://60º
                dataXZ[0]=15;
                dataXZ[1]=1;
                break;
            case 28://65º
                dataXZ[0]=13;
                dataXZ[1]=1;
                break;
            case 29://70º
                dataXZ[0]=10;
                dataXZ[1]=1;
                break;
            case 30://78º
                dataXZ[0]=6;
                dataXZ[1]=0;
                break;
            case 31://80º
                dataXZ[0]=5;
                dataXZ[1]=2;
                break;
            case 32://82º
                dataXZ[0]=4;
                dataXZ[1]=2;
                break;
        }
        return dataXZ;

    }







}
