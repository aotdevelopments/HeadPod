package com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.fragments.Cara3DFragment;

/**
 * Created by Michael on 27/12/2016.
 */
public class LeerBateria implements Runnable {

    public FragmentActivity fragmentActivity;
    public Context context;
    public int idioma;



    public LeerBateria(FragmentActivity fragmentActivity, Context context, int idioma)
    {
        this.fragmentActivity=fragmentActivity;
        this.context=context;
        this.idioma=idioma;

    }

    public void run()
    {
        if(  ((HomeActivity) fragmentActivity).controladorServiceBLE!=null)
            ((HomeActivity) fragmentActivity).controladorServiceBLE.getValorBateria(context,idioma);

    }
}
