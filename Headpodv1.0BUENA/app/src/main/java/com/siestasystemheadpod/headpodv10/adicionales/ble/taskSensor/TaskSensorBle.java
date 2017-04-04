package com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;

import com.siestasystemheadpod.headpodv10.fragments.Cara3DFragment;

/**
 * Created by Michael on 27/12/2016.
 */
public class TaskSensorBle {

    public FragmentActivity fragmentActivity;
    public int idioma;


    public boolean espera;

    public TaskSensorBle(FragmentActivity fragmentActivity)
    {
        this.fragmentActivity=fragmentActivity;
        espera=true;
    }



    public TaskSensorBle(FragmentActivity fragmentActivity, int idioma)
    {
        this.fragmentActivity=fragmentActivity;
        espera=true;
        this.idioma=idioma;
    }


    //Método encaargdo de habilitar el acelerómetro.
    public void habilitarAcelerometro()
    {
        HabilitarAcelerometro habilitarAcelerometro = new HabilitarAcelerometro(fragmentActivity);
        Thread hiloHabilitarAccel =new Thread(habilitarAcelerometro);

        hiloHabilitarAccel.start();
        esperando(hiloHabilitarAccel,"habilitando_accel");
    }

    //Método encaragado de desHabilitar el acelerómetro
    public void desHabilitarAcelerometro()
    {
        DesHabilitarAcelerometro desHabilitarAcelerometro = new DesHabilitarAcelerometro(fragmentActivity);
        Thread hiloDesHabilitarAccel = new Thread(desHabilitarAcelerometro);
        hiloDesHabilitarAccel.start();
        esperando(hiloDesHabilitarAccel,"deshabilitando_accel");

    }


    //Metodo encargado de habilitar las notificaciones del acelerometro
    public void habilitarNotificacionesAccel()
    {
        HabilitarNotificacionesAccel habilitarNotificacionesAccel = new HabilitarNotificacionesAccel(fragmentActivity);
        Thread hiloHabilitarNotiAccel = new Thread(habilitarNotificacionesAccel);

        hiloHabilitarNotiAccel.start();
        esperando(hiloHabilitarNotiAccel,"HabilitandoNotificaciones_accel");

    }

    //Método encargado de desHabilitar las notificaciones
    public void desHabilitarNotificacionesAccel()
    {

        DesHabilitarNotificacionesAccel desHabilitarNotificacionesAccel = new DesHabilitarNotificacionesAccel(fragmentActivity);
        Thread hiloDeshabilitarNotifiAccel = new Thread(desHabilitarNotificacionesAccel);

        hiloDeshabilitarNotifiAccel.start();
        esperando(hiloDeshabilitarNotifiAccel,"deshabilitandoNotificaciones_accel");

    }

    //Metodo encargado de leer los datos del accelerometro
    public void leerDatosAcelerometroCara3D(Cara3DFragment cara3DFragment)
    {
        LeerDatosAcelerometroCara3D leerDatosAcelerometroCara3D= new LeerDatosAcelerometroCara3D(fragmentActivity,cara3DFragment);
        Thread hiloLeerDatosAccelCara3D = new Thread(leerDatosAcelerometroCara3D);

        hiloLeerDatosAccelCara3D.start();
        esperando(hiloLeerDatosAccelCara3D,"leyendo_datos_accel");


    }


    public void cambiarPeriodoAccel( int posPeriodo)
    {

        CambiarPeriodoAccel cambiarPeriodoAccel = new CambiarPeriodoAccel(fragmentActivity,posPeriodo);
        Thread hiloCambiarPeriodoAccel = new Thread(cambiarPeriodoAccel);

        hiloCambiarPeriodoAccel.start();
        esperando(hiloCambiarPeriodoAccel,"cambiando_periodo_accel");



    }

    public void habilitarNearField()
    {
        HabilitarNearField habilitarNearField = new HabilitarNearField(fragmentActivity);
        Thread hiloHabilitarNearField = new Thread(habilitarNearField);

        hiloHabilitarNearField.start();
        esperando(hiloHabilitarNearField,"leyendo near field");
    }


    public void desHabilitarNearField()
    {
       DesHabilitarNearField desHabilitarNearField= new DesHabilitarNearField(fragmentActivity);
        Thread hiloDesHabilitarNearField = new Thread(desHabilitarNearField);

        hiloDesHabilitarNearField.start();
        esperando(hiloDesHabilitarNearField,"leyendo near field");
    }



    public void leerBateria(Context context)
    {
        LeerBateria leerBateria = new LeerBateria(fragmentActivity,context,idioma);
        Thread hiloLeerBateria = new Thread(leerBateria);

        hiloLeerBateria.start();
        esperando(hiloLeerBateria,"leyendo bateria");
    }


    //Metodo que se encarga de esperar a que la tarea termine.
    public void esperando(Thread hiloTarea, String tarea)
    {

        while (hiloTarea.isAlive())
        {
            Log.d("SENSOR","esperando "+ tarea);

            //esperando=true;
          espera=true;
        }

            espera=false;



    }

    //Metodo que se encarga de esperar a que la tarea termine.




}
