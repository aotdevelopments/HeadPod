package com.siestasystemheadpod.headpodv10.adicionales.informe;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.Util;

/**
 * Subproceso para cargar los resultados de la imagen del movimiento de inclinación ,flexión y rotación
 * Se crear un único bitmap combinando los 11bitmaps de cara rango.
 * Por último lo colocamos en un canvas
 */
public class LoadViewResultMov extends AsyncTask<Void, Void, Long> {

    private FragmentActivity fragmentActivity;
    private Context context;
    private String  defPackge;//ruta de donde obtenemos los recursos
    private ImageView imgResultado;//Donde colocaremos el resultado final



    //Porcentajes de los arcos de la semicircunfenrencia  //NOTAAAA:    //Ojo con la rotación ya que los valores serÁn al reves (giramos la imagen)
    //***************************************************************
    private float porcent_negativo90;
    private float porcent_negativo70;
    private float porcent_negativo55;
    private float porcent_negativo40;
    private float porcent_negativo25;
    private float porcent_neutro10;
    private float porcent_positivo25;
    private float porcent_positivo40;
    private float porcent_positivo55;
    private float porcent_positivo70;
    private float porcent_positivo90;
    //**************************************************************************

    //Bitmap para combinar imagenes de fondo (trapecio circular) con la imagen del movimiento de inclinación, flexion o rotacion
    private Bitmap combinedBitmap;

    //Nombre del recurso de la imagen a cargar
    private String nameResourcePrincipal;

    private boolean changeColorGreen;


    public LoadViewResultMov(FragmentActivity fragmentActivity, Context context, String nameResourcePrincipal,boolean changeColorGreen)
    {
        this.fragmentActivity=fragmentActivity;
        this.context=context;
        defPackge=context.getPackageName();
        this.nameResourcePrincipal=nameResourcePrincipal;
        this.changeColorGreen=changeColorGreen;
    }

    public void setImgMovimientoResult(ImageView img)
    {
        imgResultado=img;
    }

    public void setPorcent_negativo90(float x)
    {
        porcent_negativo90=x;
    }


    public void setPorcent_negativo70(float x)
    {
        porcent_negativo70=x;
    }


    public void setPorcent_negativo55(float x)
    {
        porcent_negativo55=x;
    }


    public void setPorcent_negativo40(float x)
    {
        porcent_negativo40=x;
    }

    public void setPorcent_negativo25(float x)
    {
        porcent_negativo25=x;
    }

    public void setPorcent_neutro10(float x)
    {
        porcent_neutro10=x;
    }

    public void setPorcent_positivo25(float x)
    {
        porcent_positivo25=x;
    }

    public void setPorcent_positivo40(float x)
    {
        porcent_positivo40=x;
    }

    public void setPorcent_positivo55(float x)
    {
        porcent_positivo55=x;
    }

    public void setPorcent_positivo70(float x)
    {
        porcent_positivo70=x;
    }

    public void setPorcent_positivo90(float x)
    {
        porcent_positivo90=x;
    }



    //onPreExecute(). Se ejecutará antes del código principal de nuestra tarea. Se suele utilizar para preparar la ejecución de la tarea, inicializar la interfaz, etc.
    @Override
    protected void onPreExecute() {

        //Toast.makeText(context, fragmentActivity.getString(R.string.wait_please), Toast.LENGTH_SHORT).show();


    }

    //doInBackground(). Contendrá el código principal de nuestra tarea.
    @Override
    protected Long doInBackground(Void... fragment) {

        long result =0;
        try {

           int drawableResourceIdPrincipal = getResources().getIdentifier(nameResourcePrincipal,"drawable",defPackge);//Recurso de la imagen principal (La cara con la semicircunferencia)
            //Aquí guardaremos cada uno de los recursos necesarios para obtener el trapecio circular (Arco resultante), que quedara encima de la semicircunferencia.
            //******************************************************
           int drawableResourceId1 = getIdResource(porcent_negativo90,"g1");//3.0f
           int drawableResourceId2 = getIdResource(porcent_negativo70,"g2");//5f
           int drawableResourceId3 = getIdResource(porcent_negativo55,"g3");//0f
           int drawableResourceId4 = getIdResource(porcent_negativo40,"g4");//5f
           int drawableResourceId5 = getIdResource(porcent_negativo25,"g5");//10f
           int drawableResourceId6= getIdResource(porcent_neutro10,"g6");//15f
           int drawableResourceId7 = getIdResource(porcent_positivo25,"g7");//25f
           int drawableResourceId8 = getIdResource(porcent_positivo40,"g8");//20.0f
           int drawableResourceId9 = getIdResource(porcent_positivo55,"g9");//5.0f
           int drawableResourceId10 = getIdResource(porcent_positivo70,"g10");//10.0f
           int drawableResourceId11 = getIdResource(porcent_positivo90,"g11");//2.0f
            //*************************************************************************
           Bitmap bitInlcination = BitmapFactory.decodeResource(getResources(), drawableResourceIdPrincipal);

           Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), drawableResourceId1);
           Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),drawableResourceId2);
           Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),drawableResourceId3);
           Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(),drawableResourceId4);
           Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(),drawableResourceId5);
           Bitmap bitmap6 = BitmapFactory.decodeResource(getResources(),drawableResourceId6);
           Bitmap bitmap7 = BitmapFactory.decodeResource(getResources(),drawableResourceId7);
           Bitmap bitmap8 = BitmapFactory.decodeResource(getResources(),drawableResourceId8);
           Bitmap bitmap9 = BitmapFactory.decodeResource(getResources(),drawableResourceId9);
           Bitmap bitmap10 = BitmapFactory.decodeResource(getResources(),drawableResourceId10);
           Bitmap bitmap11 = BitmapFactory.decodeResource(getResources(),drawableResourceId11);

           if(changeColorGreen)
           {
               combinedBitmap = getCombinedBitmapChangeColor(bitInlcination, bitmap1, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, bitmap7, bitmap8, bitmap9, bitmap10, bitmap11);
           }
            else {
               combinedBitmap = getCombinedBitmap(bitInlcination, bitmap1, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, bitmap7, bitmap8, bitmap9, bitmap10, bitmap11);
           }

           //publishProgress();


        } catch(Exception e) {
            Log.e("Error","LoadViewResultMov: "+e.toString());
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
    protected void onPostExecute(Long  resourceId) {

        //Toast.makeText(context, "carga finalizada", Toast.LENGTH_SHORT).show();
        imgResultado.setImageBitmap(combinedBitmap);
       // scaleImage(imgResultado);


    }


    public Resources getResources()
    {
        return  fragmentActivity.getResources();
    }


    private Bitmap getCombinedBitmap(Bitmap principal, Bitmap b1, Bitmap b2, Bitmap b3, Bitmap b4, Bitmap b5, Bitmap b6, Bitmap b7, Bitmap b8, Bitmap b9 , Bitmap b10, Bitmap b11) {
        Bitmap drawnBitmap = null;

        try {

            drawnBitmap = Bitmap.createBitmap(principal.getWidth(),principal.getHeight() , Bitmap.Config.ARGB_8888);//800-700

            Canvas canvas = new Canvas(drawnBitmap);
            // JUST CHANGE TO DIFFERENT Bitmaps and coordinates .

            canvas.drawBitmap(b1, canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(b2,canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(b3, canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(b4, canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(b5, canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(b6,canvas.getWidth()/ 2 - b1.getWidth() / 2,0, null);
            canvas.drawBitmap(b7, canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(b8,canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(b9,canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(b10, canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(b11, canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(principal, canvas.getWidth()/ 2 - principal.getWidth() / 2, 0, null);
            //for more images :
            // canvas.drawBitmap(b3, 0, 0, null);
            // canvas.drawBitmap(b4, 0, 0, null);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return drawnBitmap;
    }



    private Bitmap getCombinedBitmapChangeColor(Bitmap principal, Bitmap b1, Bitmap b2, Bitmap b3, Bitmap b4, Bitmap b5, Bitmap b6, Bitmap b7, Bitmap b8, Bitmap b9 , Bitmap b10, Bitmap b11) {
        Bitmap drawnBitmap = null;

        try {

            drawnBitmap = Bitmap.createBitmap(principal.getWidth(),principal.getHeight() , Bitmap.Config.ARGB_8888);//800-700

            Canvas canvas = new Canvas(drawnBitmap);
            // JUST CHANGE TO DIFFERENT Bitmaps and coordinates .

            canvas.drawBitmap(changeColorBitmap(b1), canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(changeColorBitmap(b2),canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(changeColorBitmap(b3), canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(changeColorBitmap(b4), canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(changeColorBitmap(b5), canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(changeColorBitmap(b6),canvas.getWidth()/ 2 - b1.getWidth() / 2,0, null);
            canvas.drawBitmap(changeColorBitmap(b7), canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(changeColorBitmap(b8),canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(changeColorBitmap(b9),canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(changeColorBitmap(b10), canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(changeColorBitmap(b11), canvas.getWidth()/ 2 - b1.getWidth() / 2, 0, null);
            canvas.drawBitmap(principal, canvas.getWidth()/ 2 - principal.getWidth() / 2, 0, null);
            //for more images :
            // canvas.drawBitmap(b3, 0, 0, null);
            // canvas.drawBitmap(b4, 0, 0, null);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return drawnBitmap;
    }



    public  int getIdResource(float porcentaje,String prefijoNameResourse)
    {


        int drawableResourceId;

        if(porcentaje==0)
        {
            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_01", "drawable", defPackge);

        }
        else if(porcentaje>0.01 && porcentaje<0.05)
        {
            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_02", "drawable", defPackge);

        }

        else if(porcentaje>=0.05 && porcentaje<0.10 )
        {
            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_03", "drawable", defPackge);

        }

        else if(porcentaje>=0.10 && porcentaje<0.15)
        {
            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_04", "drawable", defPackge);

        }

        else if(porcentaje>=0.15 && porcentaje<0.20)
        {
            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_05", "drawable", defPackge);

        }
        else if(porcentaje>=0.20 && porcentaje<0.25)
        {
            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_06", "drawable", defPackge);

        }
        else if(porcentaje>=0.25 && porcentaje<0.30)
        {
            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_07", "drawable", defPackge);

        }
        else if(porcentaje>=0.30 && porcentaje<0.35)
        {
            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_08", "drawable", defPackge);

        }
        else if(porcentaje>=0.35 && porcentaje<0.40)
        {

            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_09", "drawable", defPackge);

        }
        else if(porcentaje>=0.40 && porcentaje<0.45)
        {
            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_10", "drawable", defPackge);

        }
        else if(porcentaje>=0.45 && porcentaje<0.50)
        {

            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_11", "drawable", defPackge);

        }
        else if(porcentaje>=0.50 && porcentaje<0.55)
        {

            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_12", "drawable", defPackge);

        }
        else if(porcentaje>=0.55 && porcentaje<0.60)
        {

            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_13", "drawable", defPackge);

        }
        else if(porcentaje>=0.60 && porcentaje<0.65)
        {

            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_14", "drawable", defPackge);

        }
        else if(porcentaje>=0.65 && porcentaje<0.70)
        {

            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_15", "drawable", defPackge);

        }
        else if(porcentaje>=0.70 && porcentaje<0.75)
        {
            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_16", "drawable",defPackge);

        }
        else if(porcentaje>=0.75 && porcentaje<0.80)
        {
            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_17", "drawable", defPackge);


        }
        else if(porcentaje>=0.80 && porcentaje<0.85)
        {

            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_18", "drawable", defPackge);

        }
        else if(porcentaje>=0.85 && porcentaje<0.90)
        {

            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_19", "drawable", defPackge);

        }
        else //if(porcentaje>=90)
        {
            drawableResourceId = fragmentActivity.getResources().getIdentifier(prefijoNameResourse+"_20", "drawable", defPackge);

        }

        return drawableResourceId;
    }




    public Bitmap changeColorBitmap(Bitmap bitmaporiginal)
    {
        return  Util.changeImageColor(bitmaporiginal, ContextCompat.getColor(context,R.color.data_rotation_color25));
    }




    private void scaleImage(ImageView logo)
    {

        int widthFinal=200; //=(int)(widthPixels*0.25);
        //Colocaer Logo redimensionado tamaño manteniendo la proporcion


        /*if (getTamanoPantalla().getWidthPixels() > 320 && getTamanoPantalla().getWidthPixels() < 720) {
            widthFinal = 140;//110
        } else if (getTamanoPantalla().getWidthPixels() <= 320) {
            widthFinal = 105;//75
        } else {
            widthFinal = 205;//175
        }*/

        Bitmap imagenOriginal = combinedBitmap;//BitmapFactory.decodeResource(getResources(), R.drawable.logo_headpod_sin_leyenda);

        // Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal,887,250,false);
        // Dividimos el ancho final por el ancho de la imagen original
        //***********************************************************************************************************************
        float proporcion = widthFinal / (float) imagenOriginal.getWidth();

        int heightFinal = (int) (imagenOriginal.getHeight() * proporcion);
        Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal, widthFinal, heightFinal, true);


        logo.setImageBitmap(imagenFinal);

    }




}


           /* DisplayMetrics metrics = new DisplayMetrics();
            fragmentActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int height = metrics.heightPixels;
            int width = metrics.widthPixels;

            Log.d("h=",String.valueOf(height));

            Log.d("w=",String.valueOf(width));
            */