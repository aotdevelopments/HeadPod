package com.siestasystemheadpod.headpodv10.adicionales;

import java.util.Calendar;

/**
 * Created by Michael on 17/10/2016.
 */
public class DesplazamientoAngular {


    public final double PI =Math.PI;

    private float angulo_inicial;
    private float angulo_final;



    private Calendar c1,c2;//Para obtener los segundos a través de una instancia del calendario

    //Segundos iniciales del intervarlo de tiempo de un incremento de la pos angular
    private float intervarlo_i ;
    //segundos finales del intervarlo de tiempo de un incremento de la pos angular
    private float intervarlo_f;

    private float velocidad_angular_media;

    private int cont_incrementos;

    private float aux_incremento;


    public DesplazamientoAngular(float a_i, float a_f)
    {
        angulo_inicial=a_i;
        angulo_final=a_f;
        velocidad_angular_media=0;
        cont_incrementos=1;
        aux_incremento=0;
    }

    public void cont_incrementos_mass_mass()
    {
        cont_incrementos++;
    }

    public int getContIncrementos()
    {
        return cont_incrementos;
    }



    public void setVelocidadAngularMedia(float v)
    {
        velocidad_angular_media=v;
    }


    public String obtenerMovimiento()
    {
        if(angulo_inicial>angulo_final)
        {
            return "movimiento_horario";
        }
        else if (angulo_inicial<angulo_final)
        {
            return "movimiento_antihorario";
        }
        else
        {
            return "sin_movimiento";
        }

    }


    public float incrementoDesplazamientoAngular()
    {
        //Convertimos en radianes



        float angulo_inicial_rad=  (angulo_inicial* (float)PI)/180;
        float angulo_final_rad=  (angulo_final* (float)PI)/180;


        return angulo_final_rad-angulo_inicial_rad;

        //return angulo_final-angulo_inicial;

    }

    public float getSumatorioVelocidadAngularMedia()
    {

        float va_m=velocidad_angular_media=velocidad_angular_media+(incrementoDesplazamientoAngular()/incrementoTiempo());
        return va_m;//rad/s
    }

    public float getVelocidadAngularMediaAritmetica(float intervarlo_tiempo)
    {
        //En principio creo que interesan los valores absolutos de la velocidad angular.
        float vam =(incrementoDesplazamientoAngular()/intervarlo_tiempo);
        //Log.d("velocidad_angular", "velocidad_angu_ant= " + Float.toString(velocidad_angular_media)+" rad/s");
       // Log.d("velocidad_angular", "velocidad_angular_media_en_1s= " + Float.toString(vam)+" rad/s");
        if (vam<0)
        {
            vam=vam*-1;
        }

        aux_incremento=aux_incremento+vam;

        //media aritmetica
        velocidad_angular_media=(velocidad_angular_media+aux_incremento)/cont_incrementos;

        return velocidad_angular_media;//rad/s
    }

    public float getVelocidadAngularMediaEnIncrementoTiempoX(float intervarlo_tiempo)
    {

        float vam =(incrementoDesplazamientoAngular()/intervarlo_tiempo);
        //Log.d("velocidad_angular", "velocidad_angu_ant= " + Float.toString(velocidad_angular_media)+" rad/s");
        // Log.d("velocidad_angular", "velocidad_angular_media_en_1s= " + Float.toString(vam)+" rad/s");
        if (vam<0)
        {
            vam=vam*-1;
        }

        return vam;

    }


    public float incrementoTiempo()
    {
        //medido en segundos

       // if()

        return intervarlo_f-intervarlo_i;
    }

    public boolean incrementoTiempoNulo()
    {

        if(intervarlo_f-intervarlo_i==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean t_f_mayor_ti()
    {
        if(intervarlo_f>intervarlo_i)
        {
            return true;
        }
        else
        {
            return  false;
        }

    }


    public Calendar getCalendarInicial()
    {
        return c1;
    }

    public void setCalendarInicial(Calendar c)
    {
        c1=c;
         int h_i =c1.get(Calendar.HOUR_OF_DAY);//24 horas
         int m_i =c1.get(Calendar.MINUTE);
         int s_i= c1.get(Calendar.SECOND);
        float ms_i= (float) c1.get(Calendar.MILLISECOND);

        intervarlo_i= (float) (h_i*3600) + (m_i*60) + s_i + (ms_i/1000);

    }

    public Calendar getCalendarFinal()
    {
        return c2;
    }

    public void setCalendarFinal(Calendar c)
    {
        c2=c;
        int h_i =c2.get(Calendar.HOUR_OF_DAY);//24 horas
        int m_i =c2.get(Calendar.MINUTE);
        int s_i= c2.get(Calendar.SECOND);
        float ms_i= (float) c1.get(Calendar.MILLISECOND);

        intervarlo_f= (float) (h_i*3600) + (m_i*60) + s_i + (ms_i/1000);
    }




    public void setIntervarloInicial(float i_i)
    {
        intervarlo_i=i_i;
    }

    public float getIntervarloInicial()
    {
        return intervarlo_i;
    }



    public void setIntervarloFinal(float i_f)
    {
        intervarlo_f=i_f;
    }

    public float getIntervarloFinal()
    {
        return intervarlo_f;
    }


    public float getAnguloInicial()
    {
        return angulo_inicial;
    }

    public float getAnguloFinal()
    {
        return angulo_final;
    }

    public void setAnguloInicial(float a_i)
    {

        angulo_inicial=a_i;
    }

    public void setAnguloFinal(float a_f)
    {
        angulo_final=a_f;
    }



}
