package com.siestasystemheadpod.headpodv10.adicionales;

import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Michael on 27/10/2016.
 */
public class Cronometro implements Runnable{

    // Atributos privados de la clase
    private TextView tiempo; // Etiqueta para mostrar la información
    private String nombrecronometro; // Nombre del cronómetro
    private int segundos, minutos, horas, segsTotales; // Segundos, minutos y horas que lleva activo el cronómetro
    private Handler escribirenUI; // Necesario para modificar la UI
    private Boolean pausado; // Para pausar el cronómetro
    private String salida; // Salida formateada de los datos del cronómetro

    private int tiempo_total; //segundos

    private ProgressBar barra_progreso;

    public Boolean continuarHilo=true;



    /**
     * Constructor de la clase
     * @param tiempo Etiqueta para mostrar información
     */
    public Cronometro(TextView tiempo, ProgressBar barra_progreso,int segundosTotales)
    {
        this.tiempo = tiempo;
        this.barra_progreso = barra_progreso;
        tiempo_total=segundosTotales;


        salida = "";
        segundos = 0;
        minutos = 0;
        horas = 0;


        segsTotales=0;

        escribirenUI = new Handler();
        pausado = Boolean.FALSE;
    }


    public void reiniciar()
    {
        segundos = 0;
        minutos = 0;
        horas = 0;
        pausado = Boolean.FALSE;
        segsTotales=0;
        barra_progreso.setProgress(0);
        pausado=Boolean.TRUE;
        String t_i="00:00:00";
        tiempo.setText(t_i);
    }

    public void pause()
    {
        pausado = !pausado;
    }

    public void reanudar()
    {
        pausado = Boolean.FALSE;
    }

    @Override
    /**
     * Acción del cronómetro, contar tiempo en segundo plano
     */
    public void run()
    {
        try
        {
            // segundosTotales<tiempo_total

            while(segsTotales<tiempo_total && continuarHilo)
            {
                Thread.sleep(1000);//simulamos un segundo (1000 ms)
                salida = "";
                if( !pausado )
                {
                    segundos++;
                    segsTotales++;
                    if(segundos == 60)
                    {
                        segundos = 0;
                        minutos++;
                    }
                    if(minutos == 60)
                    {
                        minutos = 0;
                        horas++;
                    }
                    // Formateo la salida
                    salida += "0";
                    salida += horas;
                    salida += ":";
                    if( minutos <= 9 )
                    {
                        salida += "0";
                    }
                    salida += minutos;
                    salida += ":";
                    if( segundos <= 9 )
                    {
                        salida += "0";
                    }
                    salida += segundos;
                    // Modifico la UI
                    try
                    {
                        escribirenUI.post(new Runnable()
                        {
                            @Override
                            public void run()
                            {

                                barra_progreso.setProgress((segsTotales*100)/tiempo_total);
                                tiempo.setText(salida);
                            }
                        });
                    }
                    catch (Exception e)
                    {
                        Log.i("Cronometro", "Error en el cronometro " + nombrecronometro + " al escribir en la UI: " + e.toString());
                    }
                }
            }
        }
        catch (InterruptedException e)
        {
            Log.i("Cronometro", "Error en el cronometro " + nombrecronometro + ": " + e.toString());
        }
    }
}
