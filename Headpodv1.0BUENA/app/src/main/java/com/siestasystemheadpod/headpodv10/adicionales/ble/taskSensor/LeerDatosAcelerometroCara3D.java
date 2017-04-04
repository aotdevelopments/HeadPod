package com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor;

import android.support.v4.app.FragmentActivity;

import com.siestasystemheadpod.headpodv10.fragments.Cara3DFragment;
import com.siestasystemheadpod.headpodv10.HomeActivity;

/**
 * Created by Michael on 27/12/2016.
 */
public class LeerDatosAcelerometroCara3D implements Runnable {

    public FragmentActivity fragmentActivity;
    public Cara3DFragment cara3DFragment;

    public LeerDatosAcelerometroCara3D(FragmentActivity fragmentActivity, Cara3DFragment cara3DFragment)
    {
        this.fragmentActivity=fragmentActivity;
        this.cara3DFragment = cara3DFragment;
    }

    public void run()
    {
        if(  ((HomeActivity) fragmentActivity).controladorServiceBLE!=null)
            ((HomeActivity) fragmentActivity).controladorServiceBLE.leerDatosAcelerometro(cara3DFragment);
    }
}
