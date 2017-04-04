package com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor;

import android.support.v4.app.FragmentActivity;

import com.siestasystemheadpod.headpodv10.HomeActivity;

/**
 * Created by Michael on 27/12/2016.
 */
public class HabilitarAcelerometro implements Runnable {

    public FragmentActivity fragmentActivity;

    public HabilitarAcelerometro(FragmentActivity fragmentActivity)
    {
        this.fragmentActivity=fragmentActivity;
    }

    public void run()
    {
        if(  ((HomeActivity) fragmentActivity).controladorServiceBLE!=null)
            ((HomeActivity) fragmentActivity).controladorServiceBLE.habilitarAcelerometro();
    }
}
