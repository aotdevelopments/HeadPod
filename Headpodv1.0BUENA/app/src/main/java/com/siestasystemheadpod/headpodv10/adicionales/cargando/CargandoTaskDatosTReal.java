package com.siestasystemheadpod.headpodv10.adicionales.cargando;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor.TaskSensorBle;
import com.siestasystemheadpod.headpodv10.fragments.DatosEnTRealFragment;

/**
 * Created by Michael on 14/12/2016.
 *
 * Aquí habilitareos el acelerometro y las notificaciones para que la cara en 3D y 2D, puedan moverse
 * en función del sensor.
 */

public class CargandoTaskDatosTReal extends AsyncTask<Void, Void, Void> {

    private TaskSensorBle tareasSensor;
    private  ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private long fileSize = 0;
    public FragmentActivity fragmentActivity;
    public final int  tiempoEsperaMs=500;// 500 milisegundo por cada porcetaje de carga
    //10,20,30,40,50,60,70,80


/*
onCancelled(). Se ejecutará cuando se cancele la ejecución de la tarea antes de su finalización normal.
*/


    public CargandoTaskDatosTReal(FragmentActivity fragmentActivity )
    {
        this.fragmentActivity=fragmentActivity;


    }

    //onPreExecute(). Se ejecutará antes del código principal de nuestra tarea. Se suele utilizar para preparar la ejecución de la tarea, inicializar la interfaz, etc.
    @Override
    protected void onPreExecute() {

            tareasSensor = new TaskSensorBle(fragmentActivity);
            tareasSensor.habilitarAcelerometro();

            ((HomeActivity) fragmentActivity).setFragment("fragment_datos_ent_real");//coloacmos el fragmento de datos en T real.

            progressBar = new ProgressDialog(fragmentActivity);
            progressBar.setCancelable(false);
            progressBar.setMessage(fragmentActivity.getResources().getString(R.string.cargando));
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();
            progressBarStatus = 0;


    }

    //doInBackground(). Contendrá el código principal de nuestra tarea.
    @Override
    protected Void doInBackground(Void... fragment) {

        fileSize = 0;

        //publishProgress();


        while (progressBarStatus < 100) {
            progressBarStatus = cargandoDatos();

            try {
                Thread.sleep(tiempoEsperaMs);
            }

            catch (InterruptedException e) {
                Log.e("Error",e.toString());
            }

           // progressBarbHandler.post(new Runnable() {
             //   public void run() {
                    progressBar.setProgress(progressBarStatus);
                //}
            //});
        }

        if (progressBarStatus >= 100) {
            try {
                Thread.sleep(2);
            }

            catch (InterruptedException e) {
               // e.printStackTrace();
                Log.e("Error",e.toString());
            }
            progressBar.dismiss();
        }



        return null;

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
    protected void onPostExecute(Void x) {

    }

    private int cargandoDatos() {
        while (fileSize <= 1000000) {
            fileSize++;

            if (fileSize == 100000) {
                return 10;
            }

            else if (fileSize == 200000) {
                return 20;
            }

            else if (fileSize == 300000) {
                return 30;
            }

            else if (fileSize == 400000) {
                return 40;
            }

            else if (fileSize == 500000) {
                return 50;
            }

            else if (fileSize == 700000) {
                return 70;
            }
            else if (fileSize == 800000) {
                return 80;
            }
        }


        DatosEnTRealFragment datosEnTRealFragment = (DatosEnTRealFragment) fragmentActivity.getSupportFragmentManager().findFragmentByTag("fragment_datos_ent_real");

        if(datosEnTRealFragment!=null) {

           if (datosEnTRealFragment.contenedor_cara_3D != null) {
                if (datosEnTRealFragment.cara3DFragment != null) {
                    if (datosEnTRealFragment.cara3DFragment.getFace() != null) {
                        Log.d("cara", "cargado");


                        if(  ((HomeActivity) fragmentActivity).getEstadoSensor()==1) {
                            if (((HomeActivity) fragmentActivity).controladorServiceBLE != null) {
                                ((HomeActivity) fragmentActivity).controladorServiceBLE.inicializarCara3D(datosEnTRealFragment.cara3DFragment);
                                ((HomeActivity) fragmentActivity).controladorServiceBLE.inicializarCaras2D(
                                        datosEnTRealFragment.caraFlexionFragment,datosEnTRealFragment.caraInclinacionFragment,datosEnTRealFragment.caraRotacionFragment);

                                datosEnTRealFragment.cara3DFragment.setDetener(false);//Para que inicie la cara.
                                tareasSensor.habilitarNotificacionesAccel();


                                //datosEnTRealFragment.getButtonInicio().performClick();

                               // datosEnTRealFragment.cara3DFragment.setDetener(false);//Para que inicie la cara.
                               // tareasSensor.habilitarNotificacionesAccel();
                            }
                        }

                        return 100;
                    }
                }
            }
        }
        else
        {
            Log.d("cara","vacia");

        }
        return 100;

    }




}

//Anteriores modificaciones que igual resultan útiles



/*


 @Override
    protected void onPreExecute() {

        //Toast.makeText(RegisterActivity.this, getString(R.string.wait_please), Toast.LENGTH_SHORT).show();


   // Toast.makeText(context,"cargando",Toast.LENGTH_LONG).show();

        //Fragment fragment =(fragmentActivity).getSupportFragmentManager().findFragmentByTag("fragment_all_avatar");


        //if (fragment==null) {
           // ((HomeActivity) fragmentActivity).setFragment("fragment_all_avatar");
        //}

        //((HomeActivity) fragmentActivity).setFragment("fragment_datos_ent_real");


        tareasSensor = new TaskSensorBle(fragmentActivity);

       // tareasSensor.habilitarAcelerometro();


        ((HomeActivity) fragmentActivity).setFragment("fragment_datos_ent_real");


            progressBar = new ProgressDialog(fragmentActivity);
            progressBar.setCancelable(false);
            progressBar.setMessage(fragmentActivity.getResources().getString(R.string.cargando));


        Drawable drawable2 = ContextCompat.getDrawable(fragmentActivity.getBaseContext(),R.drawable.cabeza_con_hp_sensor_51_65);

        progressBar.setProgressDrawable(drawable2);

            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();
            progressBarStatus = 0;


    }


 */





/*

    @Override
    protected void onProgressUpdate (Void... result) {

        // all_fragments_avatar = (AllAvatarFragment) fragmentActivity.getSupportFragmentManager().findFragmentByTag("fragment_all_avatar");

        //Toast.makeText(context,"cargando",Toast.LENGTH_LONG).show();


    }
*/


/*


    protected void onPostExecute(Void x) {


       // fragmentTransaction.show(all_fragments_avatar);
        //fragmentTransaction.commit();


       // fragmentActivity.findViewById(R.id.contenedorHome).setVisibility(View.VISIBLE);

/*
        all_fragments_avatar = (AllAvatarFragment) fragmentActivity.getSupportFragmentManager().findFragmentByTag("fragment_all_avatar");

        //all_fragments_avatar.contenedor_botones_avatar.click
        Log.d("termiando","terminado");


        if(all_fragments_avatar!=null)
        {
            BotonesAvatarFragment btn_avatar_fragment = (BotonesAvatarFragment)all_fragments_avatar.fragmentManagerChild.findFragmentByTag("botones_avatar");

            if(btn_avatar_fragment!=null)
            {
              // // btn_avatar_fragment.getBtnAvatarIzqda().setVisibility(View.INVISIBLE);


                if(all_fragments_avatar.cara_fragment_3D!=null)
                    btn_avatar_fragment.getBtnAvatarIzqda().performClick();



            }
        }
*/
//}

//DatosEnTRealFragment datosEnTRealFragment = (DatosEnTRealFragment) fragmentActivity.getSupportFragmentManager().findFragmentById("");










