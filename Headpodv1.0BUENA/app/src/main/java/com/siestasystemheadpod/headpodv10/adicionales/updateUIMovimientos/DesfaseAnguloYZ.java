package com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos;

/**
 * Created by Michael on 27/03/2017.
 *
 * Esta clase devolverá el angulo de la INCLINACIÓN
 * El ángulo de la flexión debido al rango estimado de +-16g, no será preciso
 * así que devolveremos solo su valor entero.
 *
 */

public class DesfaseAnguloYZ {


    private int desfaseY;
    private int desfaseZ;



    public DesfaseAnguloYZ()
    {
        desfaseY=0;
        desfaseZ=0;
    }


    public void setDesfaseY(int desfaseY)
    {
        this.desfaseY=desfaseY;
    }

    public void setDesfaseZ(int desfaseZ)
    {
        this.desfaseZ=desfaseZ;
    }

    public int getDesfaseY()
    {
        return desfaseY;
    }

    public int getDesfaseZ()
    {
        return desfaseZ;
    }

    public int valorAbsolucto(int anguloDesfase)
    {
        if(anguloDesfase<0)
        {
            anguloDesfase=anguloDesfase*-1;
        }
        return anguloDesfase;


    }



/*
    private int valorAbsoluctoY(int angulo)
    {
        if(angulo<0)
        {
            angulo=angulo*-1;
        }
        return angulo;
    }



    private int valorAbsoluctoZ(int angulo)
    {
        if(angulo<0)
        {
            angulo=angulo*-1;
        }
        return angulo;
    }*/



}
