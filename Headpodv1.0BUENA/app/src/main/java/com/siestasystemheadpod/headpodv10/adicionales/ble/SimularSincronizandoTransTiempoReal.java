package com.siestasystemheadpod.headpodv10.adicionales.ble;

import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.cargando.CargandoTaskDatosTReal;

/**
 * Created by Michael on 14/12/2016.
 */
public class SimularSincronizandoTransTiempoReal extends AsyncTask<Void, Void, Long> {


    public ImageView img  ;
    public TextView txtWaitPlease ;
    public TextView txtSincronizando;
    public AnimationDrawable frameAnimationSensor;
    public FragmentActivity fragmentActivity;
    public String aviso;
    public int posframe;






        /*
        Este método será el encargado de realizar la tarea en segundo plano.
        Como vemos, recibe un número cualquiera de parámetros del tipo Params,
        así que debemos tratar a terapeutas como un array.

         */
/*
onCancelled(). Se ejecutará cuando se cancele la ejecución de la tarea antes de su finalización normal.
*/


    public SimularSincronizandoTransTiempoReal(TextView txt, ImageView img, AnimationDrawable frameAnimationSensor, FragmentActivity fragmentActivity, String aviso, int posframe, TextView txt2  )
    {

        this.txtWaitPlease=txt;
        this.txtSincronizando=txt2;
        this.img=img;
        this.frameAnimationSensor=frameAnimationSensor;
        this.fragmentActivity=fragmentActivity;
        this.aviso = aviso;
        this.posframe = posframe;
    }

    //onPreExecute(). Se ejecutará antes del código principal de nuestra tarea. Se suele utilizar para preparar la ejecución de la tarea, inicializar la interfaz, etc.
    @Override
    protected void onPreExecute() {

        //Toast.makeText(RegisterActivity.this, getString(R.string.wait_please), Toast.LENGTH_SHORT).show();



    }

    //doInBackground(). Contendrá el código principal de nuestra tarea.
    @Override
    protected Long doInBackground(Void... fragment) {


        long result =0;

        try {
            Thread.sleep(2000);
            publishProgress();
            result=1;
        } catch(InterruptedException e) {
            Log.e("Error_simularSincro",e.toString());
        }

        return result;
    }


    //onProgressUpdate(). Se ejecutará cada vez que llamemos al método publishProgress() desde el método doInBackground().
    @Override
    protected void onProgressUpdate (Void... result) {



    }


    /*
    Aquí puedes publicar todos los resultados retornados por doInBackground()
    hacia el hilo principal.
     */

    //onPostExecute(). Se ejecutará cuando finalice nuestra tarea, o dicho de otra forma, tras la finalización del método doInBackground().
    @Override
    protected void onPostExecute(Long x) {
        txtWaitPlease.setVisibility(View.INVISIBLE);
        txtSincronizando.setText(aviso);
        img.setImageDrawable(frameAnimationSensor.getFrame(posframe));
        frameAnimationSensor.stop();

        if(fragmentActivity!=null) {
            //Cambiamos el nombre del menu de vincular sensor a desvincular sensor.
            ((HomeActivity) fragmentActivity).getMenuNavigation().getMenu().getItem(1).getSubMenu().getItem(0).setTitle(R.string.desvincular_sensor);
            Log.d("conection","transTiempoReal");

            if (fragmentActivity.getSupportFragmentManager().getBackStackEntryCount() >= 2) {
                Log.d("control", ">2 fragmentos");

                //DeviceControlFragment fragment = (DeviceControlFragment) fragmentActivity.getSupportFragmentManager().findFragmentByTag("fragment_sincronizando_sensor");

                ((HomeActivity) fragmentActivity).controladorServiceBLE = (DeviceControlFragment) fragmentActivity.getSupportFragmentManager().findFragmentByTag("fragment_sincronizando_sensor");

                fragmentActivity.getSupportFragmentManager().popBackStack();
                fragmentActivity.getSupportFragmentManager().popBackStack();




                Log.d("tans","Avataraaaa");



                new CargandoTaskDatosTReal(fragmentActivity).execute();




            }

        }

       // new SimularConection().execute();
    }

}
