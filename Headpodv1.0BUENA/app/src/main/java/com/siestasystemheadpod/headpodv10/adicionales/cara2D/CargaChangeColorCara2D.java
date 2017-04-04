package com.siestasystemheadpod.headpodv10.adicionales.cara2D;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.Util;
import com.siestasystemheadpod.headpodv10.fragments.AllAvatarFragment;
import com.siestasystemheadpod.headpodv10.fragments.BotonesAvatarFragment;

import static com.siestasystemheadpod.headpodv10.adicionales.cara2D.Cara2D.COLOR_AMARILLO;
import static com.siestasystemheadpod.headpodv10.adicionales.cara2D.Cara2D.COLOR_BLANCO;
import static com.siestasystemheadpod.headpodv10.adicionales.cara2D.Cara2D.COLOR_NARANJA;
import static com.siestasystemheadpod.headpodv10.adicionales.cara2D.Cara2D.COLOR_ROJO;
import static com.siestasystemheadpod.headpodv10.adicionales.cara2D.Cara2D.COLOR_VERDE;

/**
 * Created by Michael on 17/03/2017
 * Convierte el drawable de las cabezas en sus correspondientes bitmaps.
 * Clase para obtener los recursos para cambiar la cara de color en segundo plano.
 *La finalidad es dejar preparadas todas las caras en sus distintos colores.
 */
public class CargaChangeColorCara2D extends AsyncTask<Void, Void, Void> {

    private Drawable cabezaDrawable;
    private Context context;
    private Bitmap cabezaBlanca=null;
    private Bitmap cabezaVerde=null;
    private Bitmap cabezaAmarrilla=null;
    private Bitmap cabezaNaranja=null;
    private Bitmap cabezaRoja=null;


    public CargaChangeColorCara2D(Drawable cabezaDrawable, Context context)
    {
        this.cabezaDrawable=cabezaDrawable;
        this.context=context;
    }

    //onPreExecute(). Se ejecutará antes del código principal de nuestra tarea. Se suele utilizar para preparar la ejecución de la tarea, inicializar la interfaz, etc.
    @Override
    protected void onPreExecute() {
    }

    //doInBackground(). Contendrá el código principal de nuestra tarea.
    @Override
    protected Void doInBackground(Void... fragment) {

        Bitmap cabeza_Bitmap = Util.convertDrawableToBitmap(cabezaDrawable);
        cabezaBlanca = Util.changeImageColor(cabeza_Bitmap, ContextCompat.getColor(getContext(),COLOR_BLANCO));
        cabezaVerde = Util.changeImageColor(cabeza_Bitmap, ContextCompat.getColor(getContext(),COLOR_VERDE));
        cabezaAmarrilla = Util.changeImageColor(cabeza_Bitmap, ContextCompat.getColor(getContext(),COLOR_AMARILLO));
        cabezaNaranja = Util.changeImageColor(cabeza_Bitmap, ContextCompat.getColor(getContext(),COLOR_NARANJA));
        cabezaRoja = Util.changeImageColor(cabeza_Bitmap, ContextCompat.getColor(getContext(),COLOR_ROJO));
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
    protected void onPostExecute(Void avoid) {

    }

    private Context getContext()
    {
        return context;
    }


    public Bitmap getCabezaBlancaBitmap()
    {
            return cabezaBlanca;
    }

    public Bitmap getCabezaVerdeBitmap()
    {
        return cabezaVerde;
    }

    public Bitmap getCabezaAmarrillaBitmap()
    {
        return cabezaAmarrilla;
    }

    public Bitmap getCabezaNaranjaBitmap()
    {
        return cabezaNaranja;
    }

    public Bitmap getCabezaRojaBitmap()
    {
        return cabezaRoja;
    }

}
