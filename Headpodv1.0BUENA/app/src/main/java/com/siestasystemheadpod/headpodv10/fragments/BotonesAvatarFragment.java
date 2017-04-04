package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.Cronometro;
import com.siestasystemheadpod.headpodv10.adicionales.DesplazamientoAngular;
import com.siestasystemheadpod.headpodv10.adicionales.EfectoTxtClick;
import com.siestasystemheadpod.headpodv10.adicionales.MyTimerTaskFaceWithoutTime;
import com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor.TaskSensorBle;


import java.text.DecimalFormat;
import java.util.Timer;

/**
 *
 ******************************************************************************************
 * ESTE FRAGMENTO YA NO SIRVE PERO LO DEJO PORQUE PUEDE SERME UTIL PARA REUTILIZAR CÓDIGO.*
 ******************************************************************************************
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BotonesAvatarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BotonesAvatarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BotonesAvatarFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView txtBtnAvatarIzqda;
    private TextView txtBtnAvatarDcha;

    private ImageView btnAvatarIzqda ;//para bloquearlo y desbloquear


    private  Timer timer=null;

    private MyTimerTaskFaceWithoutTime task=null;

    public final int FRECUENCIA_DE_EJECUCION= 2; //2ms //lO QUE TARDA EN EJECUTAR EL TASK DEL TIMER (el run de MyTimerTaskfaceWithoutTime)
    public final float INCREMENTO_DE_TIEMPO_POS_ANGULAR= (float) 1.0; //aproximadamente 1s


    private DesplazamientoAngular desplazamiento_angular_flexion;


    private DesplazamientoAngular desplazamiento_angular_inclinacion;

    private DesplazamientoAngular desplazamiento_angular_rotacion;


    //Los fragmentos contenedor de fragmentos con los que trabajaremos
    public AllAvatarFragment fragment;
    public AllAvatarMedicionFragment fragment_medicion;



    public Cara3DFragment cara3DFragment;
    public CaraFlexionFragment caraFlexionFragment;
    public CaraInclinacionFragment caraInclinacionFragment;
    public CaraRotacionFragment caraRotacionFragment;
    public ImageView cara_flexion;
    public TextView grados_flexion;
    public ImageView  cara_inclinacion;
    public TextView grados_inclinacion;
    public ImageView cara_rotacion;
    public TextView grados_rotacion;
    public BarraProgresoTemporalFragment barra_temporal;


    public Cronometro crono_tiempo_avanzado;

    private int chivato=0;//activar movimiento


    private  TaskSensorBle tareasSensor;



    public Timer getTimer()
    {

        return timer;
    }


    public BotonesAvatarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BotonesAvatarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BotonesAvatarFragment newInstance(String param1, String param2) {
        BotonesAvatarFragment fragment = new BotonesAvatarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ImageView getBtnAvatarIzqda()
    {
        return  btnAvatarIzqda;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        tareasSensor = new TaskSensorBle(getActivity());
        tareasSensor.habilitarAcelerometro();

        //Inicializamos el desplazamiento
         desplazamiento_angular_flexion = new DesplazamientoAngular(0,0);


        //desplazamiento_angular_inclinacion = new DesplazamientoAngular(0,0);

        //desplazamiento_angular_rotacion = new DesplazamientoAngular(0,0);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_botones_avatar, container, false);

         txtBtnAvatarIzqda = (TextView) view.findViewById(R.id.txtBtnAvatarIzqda);
         txtBtnAvatarDcha = (TextView) view.findViewById(R.id.txtbtnAvatarDcha);
         btnAvatarIzqda = (ImageView) view.findViewById(R.id.btnAvatarIzqda);
        ImageView btnAvatarDcha = (ImageView) view.findViewById(R.id.btnAvatarDcha);

        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);

        txtBtnAvatarDcha.setTypeface(myFont);
        txtBtnAvatarIzqda.setTypeface(myFont);

        btnAvatarIzqda.setOnClickListener(this);
        btnAvatarDcha.setOnClickListener(this);


        //De esta forma cada vez que cargue la vista  deshabilite acel y notificaicones
        //Deshabilitamos el acelerometro y sus notificaciones
      //  tareasSensor = new TaskSensorBle(getActivity());
      //  tareasSensor.desHabilitarAcelerometro();
       // tareasSensor.desHabilitarNotificacionesAccel();



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }







    public void onClick(View v)
    {
       // comprobacionPrevia();


        FragmentManager fragmentManager;
        fragmentManager = ((HomeActivity) getActivity()).getSupportFragmentManager();


        int cont;
        if(mParam1.equals("botones_avatar_sin_medicion"))
        {
            fragment = (AllAvatarFragment) fragmentManager.findFragmentByTag("fragment_all_avatar");//fragment_all_avatar
            //Tengo que acceder al getChildFragmentManager();
            cara3DFragment = (Cara3DFragment) fragment.fragmentManagerChild.findFragmentByTag("cara_3D");
            caraFlexionFragment = (CaraFlexionFragment) fragment.fragmentManagerChild.findFragmentByTag("cara_flexion");
            caraInclinacionFragment = (CaraInclinacionFragment) fragment.fragmentManagerChild.findFragmentByTag("cara_inclinacion");
            caraRotacionFragment = (CaraRotacionFragment) fragment.fragmentManagerChild.findFragmentByTag("cara_rotacion");

            //Obtenemos la imagen y el textview de cada cara en 2D
            cara_flexion = (ImageView) caraFlexionFragment.getActivity().findViewById(R.id.cara_flexion);
            grados_flexion = (TextView) caraFlexionFragment.getActivity().findViewById(R.id.grados_flexion);

            cara_inclinacion = (ImageView) caraInclinacionFragment.getActivity().findViewById(R.id.cara_inclinacion);
            grados_inclinacion = (TextView) caraInclinacionFragment.getActivity().findViewById(R.id.grados_inclinacion);

            cara_rotacion = (ImageView) caraRotacionFragment.getActivity().findViewById(R.id.cara_rotacion);
            grados_rotacion = (TextView) caraRotacionFragment.getActivity().findViewById(R.id.grados_rotacion);

            cont=0;//para ayudar a calcular los segundos transcurridos de una posicion agular a otra.
            //  float angulo_inicial= cara3DFragment.tres_ejes.x;
            task = new MyTimerTaskFaceWithoutTime(cara3DFragment, cara_flexion, grados_flexion, cara_inclinacion, grados_inclinacion, cara_rotacion, grados_rotacion,
                    ((HomeActivity) getActivity()),desplazamiento_angular_flexion,cont,FRECUENCIA_DE_EJECUCION,INCREMENTO_DE_TIEMPO_POS_ANGULAR,
                    fragment.va_media, fragment.convulsiones);

            /*((HomeActivity) getActivity()).controladorServiceBLE.inicializarTextViewAccel(fragment.aceleracion_x,fragment.aceleracion_x_2,
                    fragment.aceleracion_y,fragment.aceleracion_y_2,fragment.aceleracion_z,fragment.aceleracion_z_2,fragment.grados_x,fragment.grados_x_2,
                    fragment.grados_y,fragment.grados_y_2,fragment.grados_z,fragment.grados_z_2);*/


        }
        else
        {

            fragment_medicion = (AllAvatarMedicionFragment) fragmentManager.findFragmentByTag("fragment_medicion_sin_headpod_iniciar");//fragment_all_avatar_medicion//fragment_medicion_sin_headpod_iniciar



            //Tengo que acceder al getChildFragmentManager();
            cara3DFragment = (Cara3DFragment) fragment_medicion.fragmentManagerChild.findFragmentByTag("cara_3D");
            caraFlexionFragment = (CaraFlexionFragment) fragment_medicion.fragmentManagerChild.findFragmentByTag("cara_flexion");
            caraInclinacionFragment = (CaraInclinacionFragment) fragment_medicion.fragmentManagerChild.findFragmentByTag("cara_inclinacion");
            caraRotacionFragment = (CaraRotacionFragment) fragment_medicion.fragmentManagerChild.findFragmentByTag("cara_rotacion");

            //Obtenemos la imagen y el textview de cada cara en 2D
            cara_flexion = (ImageView) caraFlexionFragment.getActivity().findViewById(R.id.cara_flexion);
            grados_flexion = (TextView) caraFlexionFragment.getActivity().findViewById(R.id.grados_flexion);

            cara_inclinacion = (ImageView) caraInclinacionFragment.getActivity().findViewById(R.id.cara_inclinacion);
            grados_inclinacion = (TextView) caraInclinacionFragment.getActivity().findViewById(R.id.grados_inclinacion);

            cara_rotacion = (ImageView) caraRotacionFragment.getActivity().findViewById(R.id.cara_rotacion);
            grados_rotacion = (TextView) caraRotacionFragment.getActivity().findViewById(R.id.grados_rotacion);

            barra_temporal = (BarraProgresoTemporalFragment) fragment_medicion.fragmentManagerChild.findFragmentByTag("barra_progreso_temporal");


            cont=0;//para ayudar a calcular los segundos transcurridos de una posicion agular a otra.
            //  float angulo_inicial= cara3DFragment.tres_ejes.x;
            task = new MyTimerTaskFaceWithoutTime(cara3DFragment, cara_flexion, grados_flexion, cara_inclinacion, grados_inclinacion, cara_rotacion, grados_rotacion,
                    ((HomeActivity) getActivity()),desplazamiento_angular_flexion,cont,FRECUENCIA_DE_EJECUCION,INCREMENTO_DE_TIEMPO_POS_ANGULAR,
                    fragment_medicion.va_media, fragment_medicion.convulsiones);


        }



        switch (v.getId()) {
            case R.id.btnAvatarIzqda:

                //activarLogicaBotonIzquierda();

                if(  ((HomeActivity) getActivity()).getEstadoSensor()==1) {


                    if(  ((HomeActivity) getActivity()).controladorServiceBLE!=null) {


                        //((HomeActivity) getActivity()).controladorServiceBLE.habilitarAcelerometro();


                        //TaskSensorBle tareasSensor = new TaskSensorBle(getActivity());


                        ((HomeActivity) getActivity()).controladorServiceBLE.inicializarCara3D(cara3DFragment);


                        tareasSensor.habilitarNotificacionesAccel();

                       // tareasSensor.leerDatosAcelerometroCara3D(cara3DFragment);


                       /* HabilitarAcelerometro habilitarAcelerometro = new HabilitarAcelerometro(getActivity());
                        HabilitarNotificacionesAccel habilitarNotificacionesAccel = new HabilitarNotificacionesAccel(getActivity());
                        LeerDatosAcelerometroCara3D obtainDataAccel3Dcara = new LeerDatosAcelerometroCara3D(getActivity(),cara3DFragment);

                        Thread hiloHabilitarAccel =new Thread(habilitarAcelerometro);
                        Thread hiloHabilitarNotiAccel = new Thread(habilitarNotificacionesAccel);
                        Thread hiloObtainDatosAcelerometro = new Thread(obtainDataAccel3Dcara);


                        hiloHabilitarAccel.start();

                        while (hiloHabilitarAccel.isAlive())
                        {

                            Log.d("hilos","esperando habilitarAccel ");
                            //((HomeActivity) getActivity()).timeWaitCharacteristic(100);
                           // ((HomeActivity)getActivity()).timeWaitCharacteristic(5);

                        }

                        hiloHabilitarNotiAccel.start();

                        while(hiloHabilitarNotiAccel.isAlive())
                        {
                            Log.d("hilos","esperando habNoti");
                        }




                        hiloObtainDatosAcelerometro.start();


                        while (hiloObtainDatosAcelerometro.isAlive())
                        {

                            Log.d("hilos","esperando recibir datos");
                            //((HomeActivity)getActivity()).timeWaitCharacteristic(200);
                        }


*/
                        // ((HomeActivity) getActivity()).controladorServiceBLE.habilitarNotificacionesAcelerometro();
                        //Damos margen a que termine algo previo si lo hay
                        //((HomeActivity) getActivity()).timeWaitCharacteristic(200);


                        //((HomeActivity) getActivity()).controladorServiceBLE.leerDatosAcelerometro(cara3DFragment);
                        //Damos margen de tiempo a que se active leer datos.
                        //((HomeActivity)getActivity()).timeWaitCharacteristic(200);

                    }

                }

                Log.d("click_izqda","chivato="+Integer.toString(chivato));

                if(!mParam1.equals("botones_avatar_sin_medicion") && chivato==0)
                {
                    String t="00:00:00";
                    barra_temporal.txt_tiempo_avanzado.setText(t);

                    MedicionPlantillaPasosFragment msc_hp = (MedicionPlantillaPasosFragment) fragmentManager.findFragmentByTag("fragment_medicion_sin_headpod");

                    if (msc_hp==null)
                    {
                        msc_hp=(MedicionPlantillaPasosFragment) fragmentManager.findFragmentByTag("fragment_medicion_con_headpod");
                    }

                    int segundosTotales= ((msc_hp.getDiasSelect()*24+msc_hp.getHorasSelect()*60+msc_hp.getMinutosSelect())*60);

                    crono_tiempo_avanzado= new Cronometro(barra_temporal.txt_tiempo_avanzado,barra_temporal.barra_proreso_temporal,segundosTotales);
                    //new Thread (crono_tiempo_avanzado).start();
                  Thread hilo=  new Thread (crono_tiempo_avanzado);

                    hilo.start();
                    hilo.interrupt();



                    chivato=1;
                }
                if (chivato==1)
                {
                    crono_tiempo_avanzado.reanudar();
                }

                txtBtnAvatarDcha.setText(getResources().getString(R.string.btn_avatar_derecha_parar));//Parar

                btnAvatarIzqda.setEnabled(false);//Bloqueamos para asegurar que no falle la app

                //efecto boton
                new EfectoTxtClick(getContext(), txtBtnAvatarIzqda);


                //Creamos un Timer que se ejecuta cada 1milisegundo para el movimiento de las cabezas en 2D
                timer = new Timer();

                if(cara3DFragment!=null)
                cara3DFragment.setDetener(false);
                // desplazamiento_angular_flexion.setCalendarInicial(Calendar.getInstance());//obtenemos una instancia del tiempo
                //Log.d("velocidad_angular","incremento_i= "+Float.toString(desplazamiento_angular_flexion.getIntervarloInicial()));

                //Creamos un Timer en botonesAvatarFragment que se ejecuta cada 1milisegundo para el movimiento de las cabezas en 2D
                //timer = new Timer();
                // task = new MyTimerTaskFaceWithoutTime(cara3DFragment, cara_flexion, grados_flexion, cara_inclinacion, grados_inclinacion, cara_rotacion, grados_rotacion,((HomeActivity) getActivity()) );
                timer.schedule(task, 0, FRECUENCIA_DE_EJECUCION);//2ms //tiempo que quieres que tarde en ejecutarse (antes de ejecucion) y t ejecucion cada 2 milisegundo (periodo de ejecucion)



                //Toast.makeText(getContext(), "Soy boton_izquierda", Toast.LENGTH_SHORT).show();


                break;

            case R.id.btnAvatarDcha:


                if(  ((HomeActivity) getActivity()).getEstadoSensor()==1) {


                    if(  ((HomeActivity) getActivity()).controladorServiceBLE!=null) {

                        tareasSensor.desHabilitarNotificacionesAccel();
                       // tareasSensor.desHabilitarAcelerometro();

                    }

                }




                btnAvatarIzqda.setEnabled(true);
                cara3DFragment.setDetener(true);
                task.cara_flexion.setRotation(0);

                if (timer!=null) {
                    timer.cancel();
                }


                //Detenemos/cancelamos el timer.
                if(timer!=null )
                {
                    txtBtnAvatarIzqda.setText(getResources().getString(R.string.btn_avatar_izquierda_reanudar));//Reanudar
                }

                //Pausamos el crono
                if(!mParam1.equals("botones_avatar_sin_medicion"))
                {
                    crono_tiempo_avanzado.pause();

                }


                int chiv=0;

                if(txtBtnAvatarDcha.getText().toString().equals(getResources().getString(R.string.btn_avatar_derecha_parar)))//Parar
                {




                    txtBtnAvatarDcha.setText(R.string.btn_avatar_derecha_reset);//Reset
                    chiv=1;
                    //txtBtnAvatarIzqda.setText("inicio");
                }

                if(txtBtnAvatarDcha.getText().toString().equals(getResources().getString(R.string.btn_avatar_derecha_reset)))//Reset
                {

                    if(chiv==0)
                    {
                        //Formato
                        DecimalFormat df = new DecimalFormat("##.#");

                        cara_flexion.setImageBitmap(task.cabeza_flexion_blanca);
                        cara_inclinacion.setImageBitmap(task.cabeza_inclinacion_blanca);
                        cara_rotacion.setImageBitmap(task.cabeza_rotacion_blanca);

                        cara_flexion.setRotation(0);
                        cara_inclinacion.setRotation(0);
                        cara_rotacion.setRotation(0);

                        //desplazamiento_angular_flexion.setAnguloInicial(0);
                        //desplazamiento_angular_flexion.setIntervarloFinal(0);
                        desplazamiento_angular_flexion= new DesplazamientoAngular(0,0);


                        String v_txt= "0 rad/s";
                        if(mParam1.equals("botones_avatar_sin_medicion"))
                        {
                            fragment.va_media.setText(v_txt);
                        }
                        else
                        {
                            fragment_medicion.va_media.setText(v_txt);
                        }

                        desplazamiento_angular_flexion.setVelocidadAngularMedia(0);



                        String grados_x = String.valueOf(df.format(0)) + "º";//Para que el eje positivo sea arriba y negativo abajo
                        String grados_z = String.valueOf(df.format(0)) + "º";
                        String grados_y = String.valueOf(df.format(0)) + "º";

                        grados_flexion.setText(grados_x);
                        grados_inclinacion.setText(grados_z);
                        grados_rotacion.setText(grados_y);

                        cara3DFragment.getobjModel().rotation().x=0;
                        cara3DFragment.getobjModel().rotation().y=0;
                        cara3DFragment.getobjModel().rotation().z=0;
                        txtBtnAvatarIzqda.setText(getResources().getString(R.string.btn_avatar_izquierda_inicio));//Inicio


                        if(!mParam1.equals("botones_avatar_sin_medicion")) {
                            crono_tiempo_avanzado.reiniciar();

                        }

                    }

                }

                new EfectoTxtClick(getContext(),txtBtnAvatarDcha);
                //mListener.detenerStreamingSinTiempo("fragment_all_avatar",timer,chiv);
                // Toast.makeText(getContext(), "soy boton derecha", Toast.LENGTH_SHORT).show();


                //activarLogicaBotonDerecha();
                break;
        }
    }



    private void comprobacionPrevia()
    {


        FragmentManager fragmentManager;
        fragmentManager = ((HomeActivity) getActivity()).getSupportFragmentManager();


        int cont;
        if(mParam1.equals("botones_avatar_sin_medicion"))
        {
            fragment = (AllAvatarFragment) fragmentManager.findFragmentByTag("fragment_all_avatar");//fragment_all_avatar
            //Tengo que acceder al getChildFragmentManager();
            cara3DFragment = (Cara3DFragment) fragment.fragmentManagerChild.findFragmentByTag("cara_3D");
            caraFlexionFragment = (CaraFlexionFragment) fragment.fragmentManagerChild.findFragmentByTag("cara_flexion");
            caraInclinacionFragment = (CaraInclinacionFragment) fragment.fragmentManagerChild.findFragmentByTag("cara_inclinacion");
            caraRotacionFragment = (CaraRotacionFragment) fragment.fragmentManagerChild.findFragmentByTag("cara_rotacion");

            //Obtenemos la imagen y el textview de cada cara en 2D
            cara_flexion = (ImageView) caraFlexionFragment.getActivity().findViewById(R.id.cara_flexion);
            grados_flexion = (TextView) caraFlexionFragment.getActivity().findViewById(R.id.grados_flexion);

            cara_inclinacion = (ImageView) caraInclinacionFragment.getActivity().findViewById(R.id.cara_inclinacion);
            grados_inclinacion = (TextView) caraInclinacionFragment.getActivity().findViewById(R.id.grados_inclinacion);

            cara_rotacion = (ImageView) caraRotacionFragment.getActivity().findViewById(R.id.cara_rotacion);
            grados_rotacion = (TextView) caraRotacionFragment.getActivity().findViewById(R.id.grados_rotacion);

            cont=0;//para ayudar a calcular los segundos transcurridos de una posicion agular a otra.
            //  float angulo_inicial= cara3DFragment.tres_ejes.x;
            task = new MyTimerTaskFaceWithoutTime(cara3DFragment, cara_flexion, grados_flexion, cara_inclinacion, grados_inclinacion, cara_rotacion, grados_rotacion,((HomeActivity) getActivity()),desplazamiento_angular_flexion,cont,FRECUENCIA_DE_EJECUCION,INCREMENTO_DE_TIEMPO_POS_ANGULAR, fragment.va_media, fragment.convulsiones);


        }
        else
        {

            fragment_medicion = (AllAvatarMedicionFragment) fragmentManager.findFragmentByTag("fragment_medicion_sin_headpod_iniciar");//fragment_all_avatar_medicion



            //Tengo que acceder al getChildFragmentManager();
            cara3DFragment = (Cara3DFragment) fragment_medicion.fragmentManagerChild.findFragmentByTag("cara_3D");
            caraFlexionFragment = (CaraFlexionFragment) fragment_medicion.fragmentManagerChild.findFragmentByTag("cara_flexion");
            caraInclinacionFragment = (CaraInclinacionFragment) fragment_medicion.fragmentManagerChild.findFragmentByTag("cara_inclinacion");
            caraRotacionFragment = (CaraRotacionFragment) fragment_medicion.fragmentManagerChild.findFragmentByTag("cara_rotacion");

            //Obtenemos la imagen y el textview de cada cara en 2D
            cara_flexion = (ImageView) caraFlexionFragment.getActivity().findViewById(R.id.cara_flexion);
            grados_flexion = (TextView) caraFlexionFragment.getActivity().findViewById(R.id.grados_flexion);

            cara_inclinacion = (ImageView) caraInclinacionFragment.getActivity().findViewById(R.id.cara_inclinacion);
            grados_inclinacion = (TextView) caraInclinacionFragment.getActivity().findViewById(R.id.grados_inclinacion);

            cara_rotacion = (ImageView) caraRotacionFragment.getActivity().findViewById(R.id.cara_rotacion);
            grados_rotacion = (TextView) caraRotacionFragment.getActivity().findViewById(R.id.grados_rotacion);

            barra_temporal = (BarraProgresoTemporalFragment) fragment_medicion.fragmentManagerChild.findFragmentByTag("barra_progreso_temporal");



            cont=0;//para ayudar a calcular los segundos transcurridos de una posicion agular a otra.
            //  float angulo_inicial= cara3DFragment.tres_ejes.x;
            task = new MyTimerTaskFaceWithoutTime(cara3DFragment, cara_flexion, grados_flexion, cara_inclinacion, grados_inclinacion, cara_rotacion, grados_rotacion,((HomeActivity) getActivity()),desplazamiento_angular_flexion,cont,FRECUENCIA_DE_EJECUCION,INCREMENTO_DE_TIEMPO_POS_ANGULAR, fragment_medicion.va_media, fragment_medicion.convulsiones);




        }


    }


   /* @Override
    public void onResume() {
        super.onResume();





        //activarLogicaBotonIzquierda();

    }*/



    private void activarLogicaBotonIzquierda()
    {
        comprobacionPrevia();

        FragmentManager fragmentManager;
        fragmentManager = ((HomeActivity) getActivity()).getSupportFragmentManager();
        if(  ((HomeActivity) getActivity()).getEstadoSensor()==1) {


            if(  ((HomeActivity) getActivity()).controladorServiceBLE!=null) {
                //((HomeActivity) getActivity()).controladorServiceBLE.leerDatosAcelerometro(cara3DFragment);
                //Damos margen de tiempo a que se active leer datos.
               // ((HomeActivity)getActivity()).timeWaitCharacteristic(400);

            }

        }

        Log.d("click_izqda","chivato="+Integer.toString(chivato));

        if(!mParam1.equals("botones_avatar_sin_medicion") && chivato==0)
        {
            String t="00:00:00";
            barra_temporal.txt_tiempo_avanzado.setText(t);

            MedicionPlantillaPasosFragment msc_hp = (MedicionPlantillaPasosFragment) fragmentManager.findFragmentByTag("fragment_medicion_sin_headpod");

            if (msc_hp==null)
            {
                msc_hp=(MedicionPlantillaPasosFragment) fragmentManager.findFragmentByTag("fragment_medicion_con_headpod");
            }

            int segundosTotales= ((msc_hp.getDiasSelect()*24+msc_hp.getHorasSelect()*60+msc_hp.getMinutosSelect())*60);

            crono_tiempo_avanzado= new Cronometro(barra_temporal.txt_tiempo_avanzado,barra_temporal.barra_proreso_temporal,segundosTotales);
            new Thread (crono_tiempo_avanzado).start();

            chivato=1;
        }
        if (chivato==1)
        {
            crono_tiempo_avanzado.reanudar();
        }

        txtBtnAvatarDcha.setText(getResources().getString(R.string.btn_avatar_derecha_parar));//Parar

        btnAvatarIzqda.setEnabled(false);//Bloqueamos para asegurar que no falle la app

        //efecto boton
        new EfectoTxtClick(getContext(), txtBtnAvatarIzqda);
        //Creamos un Timer que se ejecuta cada 1milisegundo para el movimiento de las cabezas en 2D
        timer = new Timer();

        cara3DFragment.setDetener(false);
        // desplazamiento_angular_flexion.setCalendarInicial(Calendar.getInstance());//obtenemos una instancia del tiempo
        //Log.d("velocidad_angular","incremento_i= "+Float.toString(desplazamiento_angular_flexion.getIntervarloInicial()));

        //Creamos un Timer en botonesAvatarFragment que se ejecuta cada 1milisegundo para el movimiento de las cabezas en 2D
        //timer = new Timer();
        // task = new MyTimerTaskFaceWithoutTime(cara3DFragment, cara_flexion, grados_flexion, cara_inclinacion, grados_inclinacion, cara_rotacion, grados_rotacion,((HomeActivity) getActivity()) );
        timer.schedule(task, 0, FRECUENCIA_DE_EJECUCION);//2ms //tiempo que quieres que tarde en ejecutarse (antes de ejecucion) y t ejecucion cada 2 milisegundo (periodo de ejecucion)

        //Toast.makeText(getContext(), "Soy boton_izquierda", Toast.LENGTH_SHORT).show();

    }

    private void activarLogicaBotonDerecha()
    {
        comprobacionPrevia();
        //
        btnAvatarIzqda.setEnabled(true);
        cara3DFragment.setDetener(true);
        task.cara_flexion.setRotation(0);

        if (timer!=null) {
            timer.cancel();
        }


        //Detenemos/cancelamos el timer.
        if(timer!=null )
        {
            txtBtnAvatarIzqda.setText(getResources().getString(R.string.btn_avatar_izquierda_reanudar));//Reanudar
        }

        //Pausamos el crono
        if(!mParam1.equals("botones_avatar_sin_medicion"))
        {
            crono_tiempo_avanzado.pause();

        }


        int chiv=0;

        if(txtBtnAvatarDcha.getText().toString().equals(getResources().getString(R.string.btn_avatar_derecha_parar)))//Parar
        {



            txtBtnAvatarDcha.setText(R.string.btn_avatar_derecha_reset);//Reset
            chiv=1;
            //txtBtnAvatarIzqda.setText("inicio");
        }

        if(txtBtnAvatarDcha.getText().toString().equals(getResources().getString(R.string.btn_avatar_derecha_reset)))//Reset
        {

            if(chiv==0)
            {
                //Formato
                DecimalFormat df = new DecimalFormat("##.#");

                cara_flexion.setImageBitmap(task.cabeza_flexion_blanca);
                cara_inclinacion.setImageBitmap(task.cabeza_inclinacion_blanca);
                cara_rotacion.setImageBitmap(task.cabeza_rotacion_blanca);

                cara_flexion.setRotation(0);
                cara_inclinacion.setRotation(0);
                cara_rotacion.setRotation(0);

                //desplazamiento_angular_flexion.setAnguloInicial(0);
                //desplazamiento_angular_flexion.setIntervarloFinal(0);
                desplazamiento_angular_flexion= new DesplazamientoAngular(0,0);


                String v_txt= "0 rad/s";
                if(mParam1.equals("botones_avatar_sin_medicion"))
                {
                    fragment.va_media.setText(v_txt);
                }
                else
                {
                    fragment_medicion.va_media.setText(v_txt);
                }

                desplazamiento_angular_flexion.setVelocidadAngularMedia(0);



                String grados_x = String.valueOf(df.format(0)) + "º";//Para que el eje positivo sea arriba y negativo abajo
                String grados_z = String.valueOf(df.format(0)) + "º";
                String grados_y = String.valueOf(df.format(0)) + "º";

                grados_flexion.setText(grados_x);
                grados_inclinacion.setText(grados_z);
                grados_rotacion.setText(grados_y);

                cara3DFragment.getobjModel().rotation().x=0;
                cara3DFragment.getobjModel().rotation().y=0;
                cara3DFragment.getobjModel().rotation().z=0;
                txtBtnAvatarIzqda.setText(getResources().getString(R.string.btn_avatar_izquierda_inicio));//Inicio


                if(!mParam1.equals("botones_avatar_sin_medicion")) {
                    crono_tiempo_avanzado.reiniciar();

                }

            }

        }

        new EfectoTxtClick(getContext(),txtBtnAvatarDcha);
        //mListener.detenerStreamingSinTiempo("fragment_all_avatar",timer,chiv);
        // Toast.makeText(getContext(), "soy boton derecha", Toast.LENGTH_SHORT).show();



    }






    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);

    }
}
