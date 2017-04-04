package com.siestasystemheadpod.headpodv10.data.modelo;

/**
 * Created by Michael on 09/03/2017.
 */

public class Coordenadas {



    private long _id;

    private int x; //Representa el tiempo
    private int y; //Representa el angulo
    private long fk_id_movimiento;//Clave foranea


    public Coordenadas(int x, int y, long fk_id_movimiento)
    {
        this.x=x;
        this.y=y;
        this.fk_id_movimiento=fk_id_movimiento;
    }


    public int getX()
    {
        return x;
    }

    public int getY()
    {

        return y;
    }

    public long getFk_id_movimiento()
    {
        return fk_id_movimiento;
    }







}
