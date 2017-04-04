package com.siestasystemheadpod.headpodv10.adicionales;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.fragments.Cara3DFragment;
import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;


import java.text.DecimalFormat;
import java.util.TimerTask;

/**
 * Created by Michael on 13/10/2016.
 *
 * Esta clase se encargar de dar el movimiento a las caras en 2D, cuando el avatar en 3D se mueve.
 *
 */
public class MyTimerTaskFaceWithoutTime extends TimerTask {

    public ImageView cara_flexion  ;
    public TextView grados_flexion ;

    public ImageView cara_inclinacion ;
    public TextView grados_inclinacion ;

    public ImageView cara_rotacion ;
    public TextView  grados_rotacion ;

    public Cara3DFragment cara3DFragment ;

    public HomeActivity actividad;

    public DesplazamientoAngular desplazamiento_angular_flexion;

    public float vam_flexion;

    public TextView txt_vam_total;

    public TextView txt_convulsiones_t;

    public float incremento_t_pos_angular;// incremento del tiempo de la variacion del dezplazamiento de la posicion aungular en dicho tiempo.
    public int frecuencia_de_ejecucion_run;//tiempo que se ejecuta el metodo run
    public int cont;//contador para ayudar a calcular el tiempo en segundos.


/*
    public int v_i_flexion=0;
    public int v_i_rotacion=0;
    public int v_i_inclinacion=0;

*/

    public int convulsiones=0;

    //Limites de los angulos
    float limite_inicial=0;
    float limite_verde=10;
    float limite_amarrillo=20;
    float limite_naranja=36;
    float limite_rojo=45;

    //Rotación
    public Bitmap cabeza_rotacion_blanca;
    public Bitmap cabeza_rotacion_verde;
    public Bitmap cabeza_rotacion_amarilla;
    public Bitmap cabeza_rotacion_naranja;
    public Bitmap cabeza_rotacion_roja;


    //inclinación
    public Bitmap cabeza_inclinacion_blanca;
    public Bitmap cabeza_inclinacion_verde;
    public Bitmap cabeza_inclinacion_amarilla;
    public Bitmap cabeza_inclinacion_naranja;
    public Bitmap cabeza_inclinacion_roja;


    //flexión
    public Bitmap cabeza_flexion_blanca;
    public Bitmap cabeza_flexion_verde;
    public Bitmap cabeza_flexion_amarilla;
    public Bitmap cabeza_flexion_naranja;
    public Bitmap cabeza_flexion_roja;


    //Aceleraciones

    public TextView aceleracion_x;
    public TextView aceleracion_y;
    public TextView aceleracion_z;

    public TextView aceleracion_x_2;
    public TextView aceleracion_y_2;
    public TextView aceleracion_z_2;


    //Pass the bitmap and color code to change the icon color dynamically.

    //Constructor para inicializar cada objeto del xml y preparo los distitnos tipos de cara según
    //el rango de intervarlos
    public MyTimerTaskFaceWithoutTime(Cara3DFragment cara3DFragment,
                                      ImageView cara_flexion, TextView grados_flexion,
                                      ImageView cara_inclinacion, TextView grados_inclinacion,
                                      ImageView cara_rotacion, TextView grados_rotacion,
                                      HomeActivity actividad, DesplazamientoAngular desplazamiento_angular_flexion,
                                      int cont,int periodo_de_ejecucion,float incremento_t_pos_angular,TextView va_m, TextView convulsiones
                                    ){

        this.cara3DFragment=cara3DFragment;

        this.cara_flexion  = cara_flexion;
        this.grados_flexion = grados_flexion;

        this.cara_inclinacion = cara_inclinacion;
        this.grados_inclinacion = grados_inclinacion;

        this.cara_rotacion = cara_rotacion;
        this.grados_rotacion = grados_rotacion;

        this.actividad=actividad;
        this.desplazamiento_angular_flexion=desplazamiento_angular_flexion;
        this.cont=cont;
        this.frecuencia_de_ejecucion_run=periodo_de_ejecucion;
        this.incremento_t_pos_angular=incremento_t_pos_angular;
        this.txt_vam_total=va_m;
        this.txt_convulsiones_t=convulsiones;



        //Inicializamos estos atributos para poder pintar las caras obteniendola del recurso Drawable
        //Get the image to be changed from the drawable, drawable-xhdpi, drawable-hdpi,etc folder.
        Drawable cabeza_rotacion = ContextCompat.getDrawable(actividad.getBaseContext(), R.drawable.cabeza_rotacion);

        Drawable cabeza_flexion = ContextCompat.getDrawable(actividad.getBaseContext(), R.drawable.cabeza_flexion);

        Drawable cabeza_inclinacion= ContextCompat.getDrawable(actividad.getBaseContext(), R.drawable.cabeza_inclinacion_sin_cuello);

        //Convert drawable in to bitmap
        Bitmap cabeza_rotacion_Bitmap = Util.convertDrawableToBitmap(cabeza_rotacion);
        Bitmap cabeza_flexion_Bitmap = Util.convertDrawableToBitmap(cabeza_flexion);
        Bitmap cabeza_inclinacion_Bitmap = Util.convertDrawableToBitmap(cabeza_inclinacion);


        //Pass the bitmap and color code to change the icon color dynamically.
        cabeza_rotacion_blanca = Util.changeImageColor(cabeza_rotacion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.blanco));
        cabeza_rotacion_verde = Util.changeImageColor(cabeza_rotacion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.verde));
        cabeza_rotacion_amarilla = Util.changeImageColor(cabeza_rotacion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.amarrillo));
        cabeza_rotacion_naranja = Util.changeImageColor(cabeza_rotacion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.naranja));
        cabeza_rotacion_roja = Util.changeImageColor(cabeza_rotacion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.rojo));

        cabeza_inclinacion_blanca = Util.changeImageColor(cabeza_inclinacion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.blanco));
        cabeza_inclinacion_verde = Util.changeImageColor(cabeza_inclinacion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.verde));
        cabeza_inclinacion_amarilla = Util.changeImageColor(cabeza_inclinacion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.amarrillo));
        cabeza_inclinacion_naranja = Util.changeImageColor(cabeza_inclinacion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.naranja));
        cabeza_inclinacion_roja = Util.changeImageColor(cabeza_inclinacion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.rojo));


        cabeza_flexion_blanca = Util.changeImageColor(cabeza_flexion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.blanco));
        cabeza_flexion_verde = Util.changeImageColor(cabeza_flexion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.verde));
        cabeza_flexion_amarilla = Util.changeImageColor(cabeza_flexion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.amarrillo));
        cabeza_flexion_naranja = Util.changeImageColor(cabeza_flexion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.naranja));
        cabeza_flexion_roja = Util.changeImageColor(cabeza_flexion_Bitmap, ContextCompat.getColor(actividad.getBaseContext(),R.color.rojo));

        /*
        //Get the image to be changed from the drawable, drawable-xhdpi, drawable-hdpi,etc folder.
        Drawable sourceDrawable = ContextCompat.getDrawable(getBaseContext(),R.drawable.cabeza_flexion);
        //Convert drawable in to bitmap
        Bitmap sourceBitmap = Util.convertDrawableToBitmap(sourceDrawable);
        //Pass the bitmap and color code to change the icon color dynamically.
        Bitmap mFinalBitmap = Util.changeImageColor(sourceBitmap, ContextCompat.getColor(getBaseContext(),R.color.rojo));
        cara_flexion.setImageBitmap(mFinalBitmap);
        */

    }

    public void run()
    {
        //runOnUiTread, necesario para actualizar la interfaz grafica

        //Log.d("valor_3","fdfasfasf");
        actividad.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //stuff that updates ui
                try {



                    //Comprobamos en rango de angulos
                    pintarCaraSegunAngulo(cara3DFragment.getobjModel().rotation().x,cara_flexion,"flexion");
                    pintarCaraSegunAngulo(cara3DFragment.getobjModel().rotation().z,cara_inclinacion,"inclinacion");
                    pintarCaraSegunAngulo(cara3DFragment.getobjModel().rotation().y,cara_rotacion,"rotacion");

                    //x-flexion
                    //z-inclinacion
                    //y-rotacion

                    cara_flexion.setRotation(cara3DFragment.getobjModel().rotation().x);
                    cara_inclinacion.setRotation(cara3DFragment.getobjModel().rotation().z*-1);//para que coincida el mov con la cara en 3D
                    cara_rotacion.setRotation(cara3DFragment.getobjModel().rotation().y*-1);//para que coincida el mov con la cara en 3D

                    ++cont;
                   //Log.d("velocidad_angular","contador= "+Integer.toString(++cont));

                    //****************************************************************************************************************************
                    //NOTA IMPORTANTE:
                    //*************************************************************************************************************************
                    //****************************************************************************************************************************
                    //yo se que recorrer el bucle 430 veces tarda aproximadamente 1 segundo, a traves de probarlo con calendar.getInstance.
                    //No usar Modulo en "cont" para que no se produzca ningun tipo de desbordamiento.

                    //OJO CON EL TIMER.SCHEDULEEN  de botonesAvatarFragment que hace que se ejecute esto cada 2ms. Si el tiempo varia aquí, el contador
                    //de simular los segundos variara en proporcion al periodo del timer.
                    //****************************************************************************************************************************
                    //****************************************************************************************************************************

                    if(cont>=(215*frecuencia_de_ejecucion_run)*incremento_t_pos_angular)
                    //%860==0)//Hay que ajustar el cont (430) con el tiempo de la frecuencia del bucle, para simular un segundo.//En frecuencia de 2ms tarda 1 segudo en hacer el run 430 veces.
                    {



                        desplazamiento_angular_flexion.setAnguloFinal(cara3DFragment.getobjModel().rotation().x);
                       //// desplazamiento_angular_flexion.setCalendarFinal(Calendar.getInstance());//obtenemos una instancia del tiempo
                        //Log.d("velocidad_angular", "pos_f=" + Float.toString(desplazamiento_angular_flexion.getAnguloFinal()) + " pos_i=" + Float.toString(desplazamiento_angular_flexion.getAnguloInicial()) + " incremento=" + desplazamiento_angular_flexion.incrementoDesplazamientoAngular());

                        //Log.d("velocidad_angular", "incremento=" + desplazamiento_angular_flexion.incrementoDesplazamientoAngular());

                        // Log.d("velocidad_angular", "iteraciones= " + Integer.toString(desplazamiento_angular_flexion.getContIncrementos()));
                       // Log.d("velocidad_angular", "velocidad_angular_media_aritmetica= " + Float.toString(desplazamiento_angular_flexion.getVelocidadAngularMediaAritmetica(incremento_t_pos_angular))+" rad/s");


                        vam_flexion=desplazamiento_angular_flexion.getVelocidadAngularMediaAritmetica(incremento_t_pos_angular);

                       // vam_flexion=desplazamiento_angular_flexion.getVelocidadAngularMediaEnIncrementoTiempoX(incremento_t_pos_angular);


                        //Formato
                        DecimalFormat df = new DecimalFormat("##.##");
                        String aux= df.format((double)vam_flexion);
                        String v_flexion= aux+" rad/s";
                        txt_vam_total.setText(v_flexion);

                        desplazamiento_angular_flexion.setAnguloInicial(cara3DFragment.getobjModel().rotation().x);
                        cont=0;
                        desplazamiento_angular_flexion.cont_incrementos_mass_mass();//para ayudar a calcular la media arimética
                    }

                    //El siguiente código da animación por si es necesario
                    /*
                      ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(cara_flexion ,
                      "rotation", 0f, angulo);
                        imageViewObjectAnimator.setDuration(200); // miliseconds
                    imageViewObjectAnimator.start();
                     */

                     /* RotateAnimation rotate = new RotateAnimation(0, angulo, Animation.RELATIVE_TO_SELF,
                    0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
                        rotate.setDuration(500);
                        cara_inclinacion.startAnimation(rotate);*/

                    //Formato
                    DecimalFormat df = new DecimalFormat("##.#");
                    String grados_x = String.valueOf(df.format(cara3DFragment.getobjModel().rotation().x*-1))+"º";//Para que el eje positivo sea arriba y negativo abajo
                    String grados_z = String.valueOf(df.format(cara3DFragment.getobjModel().rotation().z))+"º";
                    String grados_y = String.valueOf(df.format(cara3DFragment.getobjModel().rotation().y))+"º";

                    grados_flexion.setText(grados_x);
                    grados_inclinacion.setText(grados_z);
                    grados_rotacion.setText(grados_y);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                }
            }

        });
    }


    public float devolver_incremento_desplazamiento_angular_flexion ()
    {

        return cara3DFragment.getobjModel().rotation().x;
    }







    /*
    Este método se encarga de mostrar el color de la cara según el intervarlo de angulo definido
     */
    public void pintarCaraSegunAngulo(float angulo, ImageView cara,String orientacion)
    {
        if((angulo>limite_inicial && angulo <=limite_verde) || (angulo>limite_verde*-1 && angulo<=limite_inicial))
        {
            //cara_flexion.setColorFilter(ContextCompat.getColor(getBaseContext(),R.color.rojo_ocuro));//esto no pinta contorno
            switch (orientacion)
            {
                case "flexion":
                    cara.setImageBitmap(cabeza_flexion_blanca);
                    break;

                case "inclinacion":
                    cara.setImageBitmap(cabeza_inclinacion_blanca);
                    break;
                case "rotacion":
                    cara.setImageBitmap(cabeza_rotacion_blanca);
                    break;
            }

        }

        else if ((angulo>limite_verde && angulo <=limite_amarrillo) || (angulo>limite_amarrillo*-1 && angulo<=-limite_verde))
        {

            switch (orientacion)
            {
                case "flexion":
                    cara.setImageBitmap(cabeza_flexion_verde);
                    break;

                case "inclinacion":
                    cara.setImageBitmap(cabeza_inclinacion_verde);
                    break;
                case "rotacion":
                    cara.setImageBitmap(cabeza_rotacion_verde);
                    break;
            }
        }

        else if ((angulo>limite_amarrillo && angulo <=limite_naranja) || (angulo>limite_naranja*-1 && angulo<=-limite_amarrillo))
        {

            switch (orientacion)
            {
                case "flexion":
                    cara.setImageBitmap(cabeza_flexion_amarilla);
                    break;

                case "inclinacion":
                    cara.setImageBitmap(cabeza_inclinacion_amarilla);
                    break;
                case "rotacion":
                    cara.setImageBitmap(cabeza_rotacion_amarilla);
                    break;
            }
        }

        else if ((angulo>limite_naranja && angulo <=limite_rojo) || (angulo>limite_rojo*-1 && angulo<=-limite_naranja))
        {

            switch (orientacion)
            {
                case "flexion":
                    cara.setImageBitmap(cabeza_flexion_naranja);
                    break;

                case "inclinacion":
                    cara.setImageBitmap(cabeza_inclinacion_naranja);
                    break;
                case "rotacion":
                    cara.setImageBitmap(cabeza_rotacion_naranja);
                    break;
            }

        }
        else
        {
            switch (orientacion)
            {
                case "flexion":
                    cara.setImageBitmap(cabeza_flexion_roja);
                    break;

                case "inclinacion":
                    cara.setImageBitmap(cabeza_inclinacion_roja);
                    break;
                case "rotacion":
                    cara.setImageBitmap(cabeza_rotacion_roja);
                    break;
            }

        }

    }
}
