package com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.siestasystemheadpod.headpodv10.HomeActivity;

/**
 * Created by Michael on 27/12/2016.
 */
public class CambiarPeriodoAccel implements Runnable {

    public FragmentActivity fragmentActivity;

    private int posPeriodo;
/*
   private byte [] periodo1 = {0x01}; //0x01 = 100 ms
    private byte [] periodo2 = {0x02}; //0x02 = 200 ms
    private byte [] periodo3 = {0x03}; //0x03 = 500 ms
    private byte [] periodo4 = {0x04}; //0x04 = 1000 ms
    private final byte [] ACCEL_PERIOD [] = {periodo1,periodo2,periodo3,periodo4};

 */


    public CambiarPeriodoAccel(FragmentActivity fragmentActivity,int posPeriodo)
    {
        this.fragmentActivity=fragmentActivity;
       this.posPeriodo=posPeriodo;

    }

    public void run()
    {
        if(  ((HomeActivity) fragmentActivity).controladorServiceBLE!=null)
            ((HomeActivity) fragmentActivity).controladorServiceBLE.cambiarPeriodoAcelerometro(posPeriodo);

    }
}
