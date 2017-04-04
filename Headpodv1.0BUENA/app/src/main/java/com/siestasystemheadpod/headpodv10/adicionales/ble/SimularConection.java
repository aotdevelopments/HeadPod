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
import com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor.TaskSensorBle;

/**
 * Created by Michael on 14/12/2016.
 */
public class SimularConection extends AsyncTask<Void, Void, Long>{




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
            Thread.sleep(2000);//simulamos aproximadamente dos segundos.







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


    }

}
