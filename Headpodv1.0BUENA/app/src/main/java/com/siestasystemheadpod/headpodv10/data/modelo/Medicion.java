package com.siestasystemheadpod.headpodv10.data.modelo;

/**
 * Created by Michael on 09/03/2017.
 */

public class Medicion {



    //ID
    private long _id;
    //***************************************************

    //Fecha de medición
    //***************************************************
    private int dia,mes,ano;
    //*****************************************************

    //hora de medición
    //***************************************************
    private int horaMedicion,minMedicion;
    //*****************************************************

    //duracion de medición
    //***************************************************
    private int horaDuracion,minDuracion;
    //*****************************************************

    //Tipo de medición
    //*****************************************************
    int tipo; // 0=Sin heapod y 1= Con headpod
    //*****************************************************

    //CONTROL DE MOVIMIENTO
    //************************************************
    String control; //Malo, Normal, bueno;

    //*****************************************************

    //Clave foranea
    private long fk_id_paciente;


    public Medicion(int diaM, int mesM, int anoM, int horaM, int minM, int horaD, int minD, int tipo, String control, long fk_id_paciente)
    {
        this.tipo=tipo;
        this.control=control;
        dia=diaM;
        mes=mesM;
        ano=anoM;
        horaMedicion=horaM;
        minMedicion = minM;
        horaDuracion = horaD;
        minDuracion= minD;
        this.fk_id_paciente=fk_id_paciente;
    }








    public void setTipo(int tipo)
    {
        this.tipo=tipo;
    }

    public int getTipo()
    {
        return tipo;
    }

    public void setControl(String control)
    {
        this.control=control;
    }

    public String getControl()
    {
        return control;
    }


    public int getDia()
    {
        return dia;
    }

    public int getMes()
    {
        return mes;
    }

    public int getAno()
    {
        return ano;
    }

    public int getHoraMedicion(){

        return horaMedicion;
    }

    public int getMinMedicion()
    {
        return minMedicion;
    }

    public int getHoraDuracion()
    {
        return minMedicion;
    }

    public int getMinDuracion()
    {
        return minDuracion;
    }


    public long getFk_id_paciente()
    {
        return fk_id_paciente;
    }






}
