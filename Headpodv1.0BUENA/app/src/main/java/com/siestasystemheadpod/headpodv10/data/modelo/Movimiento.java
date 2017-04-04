package com.siestasystemheadpod.headpodv10.data.modelo;

/**
 * Created by Michael on 09/03/2017.
 */

public class Movimiento {



    private long _id;


    //Tiempos que ha estado en cada intervarlo:
    //**************************************************
    private float t_i_mm3_neg;//-90 a -70 (MUY MALO)
    private float t_i_mm2_neg;//-70 a -55 (MUY MALO)
    private float t_i_mm1_neg;//-55 a -40 (MUY MALO)
    private float t_i_m_neg;//-40 a -25 (MALO)
    private float t_i_b_neg;//-25 a -10 (BUENO)
    private float t_i_mb;//-10 a 10 (MUY BUENO)
    private float t_i_b_pos;//10 a 25 (Bueno)
    private float t_i_m_pos; //25 a 40 (Malo)
    private float t_i_mm1_pos; //40 a 55 (MUY MALO)
    private float t_i_mm2_pos;// 55 a 70 (MUY MALO)
    private float t_i_mm3_pos;//70 a 90 (MUY MALO)
    //***************************************************


    //Tipo //0=Inclinación, 1=Flexión, 2=Rotación
    private int tipo;

    //Velocidad media aritmética del movimiento
   // private float vm;

    //Aceleración media aritmética del movimiento.
    //private float am;

    //clave foranea
    private long fk_id_medicion;

    //*****************************************************

    public Movimiento(float i_5_neg, float i_4_neg, float i_3_neg, float i_2_neg, float i_1_neg, float i_0, float i_1_pos, float i_2_pos, float i_3_pos, float i_4_pos, float i_5_pos, int tipo,long fk_id_medicion)
    {
        t_i_mm3_neg=i_5_neg;
        t_i_mm2_neg =i_4_neg;
        t_i_mm1_neg = i_3_neg;
        t_i_m_neg=i_2_neg;
        t_i_b_neg=i_1_neg;
        t_i_mb = i_0;
        t_i_b_pos = i_1_pos;
        t_i_m_pos = i_2_pos;
        t_i_mm1_pos = i_3_pos;
        t_i_mm2_pos = i_4_pos;
        t_i_mm3_pos = i_5_pos;
        this.tipo=tipo;

        this.fk_id_medicion=fk_id_medicion;
    }



    public float getT_i_mm3_neg()
    {
        return t_i_mm3_neg;
    }


    public float getT_i_mm2_neg()
    {
        return t_i_mm2_neg;
    }


    public float getT_i_mm1_neg()
    {
        return t_i_mm1_neg;
    }


    public float getT_i_m_neg()
    {
        return t_i_m_neg;
    }



    public float getT_i_b_neg()
    {
        return t_i_b_neg;
    }


    public float getT_i_mb()
    {
        return t_i_mb;
    }

    public float getT_i_b_pos()
    {
        return t_i_b_pos;
    }

    public float getT_i_m_pos()
    {
        return t_i_m_pos;
    }

    public float getT_i_mm1_pos()
    {
        return t_i_mm1_pos;
    }

    public float getT_i_mm2_pos()
    {
        return t_i_mm2_pos;
    }

    public float getT_i_mm3_pos()
    {
        return t_i_mm3_pos;
    }


    public int getTipo()
    {
        return tipo;
    }


    public float getFk_id_medicion()
    {
        return fk_id_medicion;
    }
}
