package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.Cronometro;
import com.siestasystemheadpod.headpodv10.adicionales.DesplazamientoAngular;
import com.siestasystemheadpod.headpodv10.adicionales.DialogAvisos;
import com.siestasystemheadpod.headpodv10.adicionales.EfectoTxtClick;
import com.siestasystemheadpod.headpodv10.adicionales.GrafinaLinealEnTiempoReal;
import com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor.TaskSensorBle;
import com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos.DesfaseAnguloYZ;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Handler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatosEnTRealFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatosEnTRealFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 *
 * Esta clase sera la encargada de llevar las operaciones para la interacción de los movimientos de la cabeza.
 *
 *
 //fragmentManagerChild = getChildFragmentManager();//Se usa para los fragmentos de fragmentos.
 //getSupportMannager // Para los fragmentos de actividades.
 *
 */
public class DatosEnTRealFragment extends PerfilFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public int SEGUNDOSTOTALES = 1800; //getTiempo() --> Para cojer el valor del spinner de PerfilFragment(Seria lo ideal); 3600s = 1h .Valor por defecto para Datos a tiempo Real.



    public FragmentManager fragmentManagerChild;
    public FragmentTransaction fragmentTransaction;

    public TextView va_media;
    public TextView convulsiones;

    public TextView aceleracion_x;
    public TextView aceleracion_y;
    public TextView aceleracion_z;


    public TextView grados_x;
    public TextView grados_y;
    public TextView grados_z;


    public TextView aceleracion_x_2;
    public TextView aceleracion_y_2;
    public TextView aceleracion_z_2;

    public TextView grados_x_2;
    public TextView grados_y_2;
    public TextView grados_z_2;


    public RelativeLayout contenedor_barra_temporal;
    public RelativeLayout contenedor_cara_3D;
    public LinearLayout contenedor_cara_flexion;
    public LinearLayout contenedor_cara_inclinacion;
   // public LinearLayout contenedor_cara_rotacion;
    public RelativeLayout contenedor_botones_avatar;


    public Cara3DFragment cara3DFragment;

    public CaraFlexionFragment caraFlexionFragment;
    public CaraInclinacionFragment caraInclinacionFragment;
    public CaraRotacionFragment caraRotacionFragment;




    private BarraProgresoTemporalFragment barraProgresoTemporalFragment;
    private  Timer timer=null;

    private TaskSensorBle tareasSensor;
    private DesplazamientoAngular desplazamiento_angular_flexion;

    private int chivato=0;//activar movimiento
    public Cronometro cronometro;

    private Thread hiloCronoetro;




    private BotonesAvatarFragment  btn_avatar_fragment;

    private static View view;




    public Button buttonM1;
    public Button buttonM2;
    public Button buttonC1;


    //Botones de acción
    //************************************
    private TextView txtPausarReanudar;
    private TextView txtReset;
    private TextView txtMedir;
    private ImageView imgBtnReset;
    private ImageView imgBtnPausarReanudar ;
    private ImageView imgBtnMedir;
    //***************************************




    //Gráfica lineal
   // private LineChart mChart;
   // protected Typeface mTfLight;
    private GrafinaLinealEnTiempoReal grafinaLinealEnTiempoReal;
    private LineChart mChart;


    private LinearLayout contenedorBtnReset;
    private LinearLayout contenedorBtnPausarReanudar;
    private LinearLayout contenedorBtnMedir;










    //Para la configuración de la orientación. Con esto respetamos el diseño landScape
    private FrameLayout frameLayout;



    public ImageView getButtonInicio()
    {
        return imgBtnPausarReanudar;
    }


    public DatosEnTRealFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllAvatarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatosEnTRealFragment newInstance(String param1, String param2) {
        DatosEnTRealFragment fragment = new DatosEnTRealFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        tareasSensor = new TaskSensorBle(getActivity());

        /*Intent i = getActivity().getIntent();
        SEGUNDOSTOTALES = Integer.parseInt(i.getStringExtra("tiempo"));*/


        //Inicializamos el desplazamiento
       // desplazamiento_angular_flexion = new DesplazamientoAngular(0,0);

    }









    // getActivity().	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

    //http://stackoverflow.com/questions/17116602/using-onconfigurationchanged-in-a-fragment
/*    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        ((HomeActivity)getActivity()).cambiarIdioma();

        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_datos_t_real, null);

        setContenidoVista(view);

        //obtenemos las cabezas en 2D del xml del Layout.
        caraFlexionFragment = (CaraFlexionFragment) getChildFragmentManager().findFragmentById(R.id.cara_flexion);
        caraInclinacionFragment = (CaraInclinacionFragment) getChildFragmentManager().findFragmentById(R.id.cara_inclinacion);
        caraRotacionFragment = (CaraRotacionFragment) getChildFragmentManager().findFragmentById(R.id.cara_rotacion);
        //Obtenemos la barraTemporal
        barraProgresoTemporalFragment = (BarraProgresoTemporalFragment) getChildFragmentManager().findFragmentById(R.id.fragmento_barra_temporal);

        frameLayout .addView(view);
    }
*/



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

       // getActivity().	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        //frameLayout = new FrameLayout(getActivity());
       //  LayoutInflater inflater2 = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate the layout for this fragment
       // View view = inflater2.inflate(R.layout.fragment_datos_t_real, container, false);


        View view = inflater.inflate(R.layout.fragment_datos_t_real, container, false);

        setContenidoVista(view);

        //obtenemos las cabezas en 2D del xml del Layout.
        caraFlexionFragment = (CaraFlexionFragment) getChildFragmentManager().findFragmentById(R.id.cara_flexion);
        caraInclinacionFragment = (CaraInclinacionFragment) getChildFragmentManager().findFragmentById(R.id.cara_inclinacion);
        //caraRotacionFragment = (CaraRotacionFragment) getChildFragmentManager().findFragmentById(R.id.cara_rotacion);
        //Obtenemos la barraTemporal
        barraProgresoTemporalFragment = (BarraProgresoTemporalFragment) getChildFragmentManager().findFragmentById(R.id.fragmento_barra_temporal);

        //frameLayout.addView(view);
       return view;
        //return frameLayout;




/*
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_datos_t_real, container, false);
            setContenidoVista(view);
            //obtenemos las cabezas en 2D del xml del Layout.
            caraFlexionFragment = (CaraFlexionFragment) getChildFragmentManager().findFragmentById(R.id.cara_flexion);
            caraInclinacionFragment = (CaraInclinacionFragment) getChildFragmentManager().findFragmentById(R.id.cara_inclinacion);
            caraRotacionFragment = (CaraRotacionFragment) getChildFragmentManager().findFragmentById(R.id.cara_rotacion);
            //Obtenemos la barraTemporal
            barraProgresoTemporalFragment = (BarraProgresoTemporalFragment) getChildFragmentManager().findFragmentById(R.id.fragmento_barra_temporal);


        } catch (InflateException e) {

        }
        return view;
*/


    }








    /*
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {

            if(getActivity() != null) getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }*/



    public void setContenidoVista(View view)
    {


        //Para el titulo del menú
        //((HomeActivity) getActivity()).getSupportActionBar().setTitle("");
        TextView titulo= (TextView)((HomeActivity) getActivity()).findViewById(R.id.toolbar_title);
        titulo.setText(R.string.titulo_datos_en_tiempo_real);


      //  TextView txt_convulsiones = (TextView) view.findViewById(R.id.txtConvulsiones);
       // TextView txt_va_m= (TextView) view.findViewById(R.id.txtVelocidadMedia);
        //convulsiones = ( TextView) view.findViewById(R.id.convulsiones);
        //va_media= (TextView) view.findViewById(R.id.velocidadMedia);

       txtPausarReanudar= (TextView) view.findViewById(R.id.txtPausarReanudar);
       txtMedir = (TextView) view.findViewById(R.id.txtMedir);
       txtReset = (TextView) view.findViewById(R.id.txtReset);


        imgBtnPausarReanudar = (ImageView) view.findViewById(R.id.imgBtnPausarReanudar);
        imgBtnReset = (ImageView) view.findViewById(R.id.imgBtnReset);
        imgBtnMedir = (ImageView) view.findViewById(R.id.imgBtnMedir);


        contenedorBtnPausarReanudar = (LinearLayout) view.findViewById(R.id.contenedorBtnPausarReanudar);
        contenedorBtnReset = (LinearLayout) view.findViewById(R.id.contenedorBtnReset);
        contenedorBtnMedir = (LinearLayout) view.findViewById(R.id.contenedorBtnMedir);


        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);
       // txt_convulsiones.setTypeface(myFont);
      //  txt_va_m.setTypeface(myFont);
      //  convulsiones.setTypeface(myFont);
      //  va_media.setTypeface(myFont);
        titulo.setTypeface(myFont);
       txtReset.setTypeface(myFont);
       txtPausarReanudar.setTypeface(myFont);


        //Colocar Cara en 3D
        contenedor_cara_3D = (RelativeLayout) view.findViewById(R.id.layout_cara_3D);
        colocarCara3D();

        //Colocar cara2D flexión
        //contenedor_cara_flexion= (LinearLayout) view.findViewById(R.id.contenedor_cara_flexion);
        //colocarCaraFlexion2D();

       //contenedor_cara_inclinacion = (LinearLayout) view.findViewById(R.id.contenedor_cara_inclinacion);
       // colocarCaraInclinacion2D();




        //Colocar cara2D Inclinación

        //Para la grafíca lineal
        mChart = (LineChart) view.findViewById(R.id.chart1);
        grafinaLinealEnTiempoReal = new GrafinaLinealEnTiempoReal(mChart,myFont,getContext());
        //construimos la grafica lineal
        grafinaLinealEnTiempoReal.setGraficaLineal(mChart);


        sincronizarGrafLinealConControladorBLE(grafinaLinealEnTiempoReal);



        //grafinaLinealEnTiempoReal.setDataTReal(0,30,50,0);
        //Escucha de botones
        imgBtnPausarReanudar.setOnClickListener(this);
        imgBtnReset.setOnClickListener(this);
        imgBtnMedir.setOnClickListener(this);
    }





    public boolean existFragment(String tagFrament)
    {
        boolean exito= false;

        Fragment fragment = fragmentManagerChild.findFragmentByTag(tagFrament);

        if(fragment!=null){

            exito = true;
        }

        return  exito;

    }





    @Override
    public void onPause() {

        //Toast.makeText(getContext(),"estoy en pause",Toast.LENGTH_LONG).show();

        //fragmentManagerChild.findFragmentByTag("cara_inclinacion").setRetainInstance(true);

        //De esta forma cada vez que cargue la vista  deshabilite acel y notificaicones
        //Deshabilitamos el acelerometro y sus notificaciones

        //*************************************************************************************
       // ((HomeActivity) getActivity()).controladorServiceBLE.desHabilitarNotificacionesAcelerometro();
            //getActivity().unbindService(((HomeActivity) getActivity()).controladorServiceBLE.getmServiceConnection());

        //****************************************************************************************
        super.onPause();

    }

    /*
    @Override
    public void onResume() {

        super.onResume();

        getChildFragmentManager().findFragmentByTag("cara_flexion")
                .getRetainInstance();

    }*/

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

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        /*
        if (caraFlexionFragment != null )
            getFragmentManager().beginTransaction().remove(caraFlexionFragment).commit();
        if(caraInclinacionFragment!=null)
            getFragmentManager().beginTransaction().remove(caraInclinacionFragment).commit();
        if(caraRotacionFragment!=null)
            getFragmentManager().beginTransaction().remove(caraRotacionFragment).commit();
        if(barraProgresoTemporalFragment!=null)
            getFragmentManager().beginTransaction().remove(barraProgresoTemporalFragment).commit();*/

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



    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.imgBtnMedir://En este "botón" vamos a calibrar la cabeza con el sensor fijandonos en el desfase de angulos.

                new EfectoTxtClick(getContext(),txtMedir);//Aplicamos el efecto clik en el texto de medir

                if(isOkSensorOrientation())
                {
                    calibrarIniciarMedicion();
                    iniciarCronometro();
                }
                else
                {
                    DialogAvisos dialogAvisos = DialogAvisos.newInstance(R.string.aviso_sensor_mal_colocado);
                    dialogAvisos.setCancelable(false);
                    dialogAvisos.show(getActivity().getSupportFragmentManager(),"aviso");
                }

                break;
            case R.id.imgBtnPausarReanudar:
                //activarLogicaBotonIzquierda();


                if(buttonIsPause())
                {
                    if(isConnectSensorAndExistControlador())
                    {
                        // ((HomeActivity) getActivity()).controladorServiceBLE.inicializarCara3D(cara3DFragment);
                        //((HomeActivity) getActivity()).controladorServiceBLE.inicializarCaras2D(caraFlexionFragment,caraInclinacionFragment,caraRotacionFragment);
                        //cara3DFragment.setDetener(false);

                        //Al deshabilitar las notificaciones del acelerometro, no hace falta hacer estos dos pasos.
                        //grafinaLinealEnTiempoReal.setStartData(false);
                        //sincronizarGrafLinealConControladorBLE(grafinaLinealEnTiempoReal);//Volvemos a "sincronizar".

                        cronometro.pause();//pausamos cornometro.
                        tareasSensor.desHabilitarNotificacionesAccel();
                        txtPausarReanudar.setText(getString(R.string.reanudar));


                    }

                }
                else//Reanudar
                {
                    if(isConnectSensorAndExistControlador())
                    {
                        grafinaLinealEnTiempoReal.setStartData(true);//habilitamos la recogida de datos
                        sincronizarGrafLinealConControladorBLE(grafinaLinealEnTiempoReal);//Volvemos a "sincronizar".
                        tareasSensor.habilitarNotificacionesAccel();

                        cronometro.reanudar();//reanudaos cronometro.
                        txtPausarReanudar.setText(getString(R.string.pausar));


                    }
                }





                Log.d("click_izqda","chivato="+Integer.toString(chivato));


                break;

            case R.id.imgBtnReset:

                DialogAvisos dialogAvisos = DialogAvisos.newInstance(R.string.aviso_reset_medicion);
                dialogAvisos.setCancelable(false);
                dialogAvisos.show(getActivity().getSupportFragmentManager(),"aviso");







/*

                if (timer!=null) {
                    timer.cancel();
                }


                //Detenemos/cancelamos el timer.
                if(timer!=null )
                {
                   txtPausarReanudar.setText(getResources().getString(R.string.btn_avatar_izquierda_reanudar));//Reanudar
                }

                //Pausamos el crono
              // if(!mParam1.equals("botones_avatar_sin_medicion"))
               // {
               //     crono_tiempo_avanzado.pause();

               // }


                int chiv=0;

                if(txtPausarReanudar.getText().toString().equals(getResources().getString(R.string.btn_avatar_derecha_parar)))//Parar
                {




                   txtReset.setText(R.string.btn_avatar_derecha_reset);//Reset
                    chiv=1;
                    //txtBtnAvatarIzqda.setText("inicio");
                }

                if(txtReset.getText().toString().equals(getResources().getString(R.string.btn_avatar_derecha_reset)))//Reset
                {

                    if(chiv==0)
                    {
                        //Formato
                        DecimalFormat df = new DecimalFormat("##.#");



                        caraFlexionFragment.pintarCaraSegunAngulo(0);
                        caraInclinacionFragment.pintarCaraSegunAngulo(0);
                        caraRotacionFragment.pintarCaraSegunAngulo(0);
                        caraFlexionFragment.rotarCara(45);
                        caraInclinacionFragment.rotarCara(45);
                        caraRotacionFragment.rotarCara(45);


                        //desplazamiento_angular_flexion.setAnguloInicial(0);
                        //desplazamiento_angular_flexion.setIntervarloFinal(0);
                        desplazamiento_angular_flexion= new DesplazamientoAngular(0,0);


                        String v_txt= "0 rad/s";
                       // if(mParam1.equals("botones_avatar_sin_medicion"))
                       // {
                            //fragment.va_media.setText(v_txt);
                       // }
                       // else
                       // {
                          //  fragment_medicion.va_media.setText(v_txt);
                       // }

                        //desplazamiento_angular_flexion.setVelocidadAngularMedia(0);



                       // String grados_x = String.valueOf(df.format(0)) + "º";//Para que el eje positivo sea arriba y negativo abajo
                       // String grados_z = String.valueOf(df.format(0)) + "º";
                       // String grados_y = String.valueOf(df.format(0)) + "º";

                       // caraFlexionFragment.setTxtAnguloCabeza(grados_x);
                       // caraFlexionFragment.setTxtAnguloSensorY(grados_x);

                       // caraFlexionFragment.setTxtA_y("0");
                       // caraFlexionFragment.setTxtG_y("0");





                        //cara3DFragment.getobjModel().rotation().x=0;
                       // cara3DFragment.getobjModel().rotation().y=0;
                       // cara3DFragment.getobjModel().rotation().z=0;


                       //txtPausarReanudar.setText(getResources().getString(R.string.btn_avatar_izquierda_inicio));//Inicio


                       // if(!mParam1.equals("botones_avatar_sin_medicion")) {
                       //     crono_tiempo_avanzado.reiniciar();

                       // }

                    }

                }

                new EfectoTxtClick(getContext(),txtReset);
                //mListener.detenerStreamingSinTiempo("fragment_all_avatar",timer,chiv);
                // Toast.makeText(getContext(), "soy boton derecha", Toast.LENGTH_SHORT).show();


                //activarLogicaBotonDerecha();


                */
                break;
        }




    }



    public Timer getTimer()
    {

        return timer;
    }



    public void colocarCara3D()
    {

        //Colocamos las cara en 3D
        //************************************
        //Añadimos los framentos a los elementos de la vista inflada:
        //Paso 1: Obtener la instancia del administrador de fragmentos
        fragmentManagerChild = getChildFragmentManager();//Se usa para los fragmentos de fragmentos.
        //getSupportMannager // Para los fragmentos de actividades.


        //Paso 2: Crear una nueva transacción
        fragmentTransaction = fragmentManagerChild.beginTransaction();

        //Paso 3: Crear un nuevo fragmento y añadirlo

        if(!existFragment("cara_3D") ) {
            Log.d("fragmento_avatar", "No existo");

            cara3DFragment = new Cara3DFragment();
            fragmentTransaction.replace(contenedor_cara_3D.getId(), cara3DFragment, "cara_3D");
            fragmentTransaction.commit();
        }
        else
        {

            Log.d("fragmento_avatar", "Existo");
            cara3DFragment = (Cara3DFragment) fragmentManagerChild.findFragmentByTag("cara_3D");
            fragmentTransaction.replace(contenedor_cara_3D.getId(), cara3DFragment);
            fragmentTransaction.commit();


        }
        //((HomeActivity) getActivity()).controladorServiceBLE.inicializarCara3D(cara3DFragment);
    }







    private boolean buttonIsPause()
    {
        return  txtPausarReanudar.getText().toString().equals(getString(R.string.pausar));
    }

    private boolean isReanudar()
    {
        return  txtPausarReanudar.getText().toString().equals(getString(R.string.reanudar));
    }

    private boolean isConnectSensorAndExistControlador()
    {
        boolean result=false;
        if(  ((HomeActivity) getActivity()).getEstadoSensor()==1) {
            if(  ((HomeActivity) getActivity()).controladorServiceBLE!=null) {

                result=true;

                }
            }

        return  result;
    }

    private void sincronizarGrafLinealConControladorBLE(GrafinaLinealEnTiempoReal grafinaLinealEnTiempoReal)
    {
        if(isConnectSensorAndExistControlador())
        {
            ((HomeActivity) getActivity()).controladorServiceBLE.incializarGraficaLineal(grafinaLinealEnTiempoReal);
        }

    }

    private boolean isOkSensorOrientation()
    {
        boolean result=true;
        if(isConnectSensorAndExistControlador())
        {
            int accelLsbx= ((HomeActivity) getActivity()).controladorServiceBLE.aceleracion.getAccelLsbX();

            if(accelLsbx<0)
            {
                result=false;
            }
        }

        return  result;

    }

    private void calibrarIniciarMedicion()
    {

        if(isConnectSensorAndExistControlador())
        {
            grafinaLinealEnTiempoReal.setStartData(true);//habilitamos la recogida de datos
            sincronizarGrafLinealConControladorBLE(grafinaLinealEnTiempoReal);//Volvemos a "sincronizar".

            if(((HomeActivity) getActivity()).controladorServiceBLE.updateMovCabeza!=null)
            {
                if(((HomeActivity) getActivity()).controladorServiceBLE.desfaseAnguloYZ!=null){


                    resetCalibrado();

                    int desfaseY=((HomeActivity) getActivity()).controladorServiceBLE.updateMovCabeza.getAnguloFlexion();
                    int desfaseZ= ((HomeActivity) getActivity()).controladorServiceBLE.updateMovCabeza.getAnguloInclinacion();


                    ((HomeActivity) getActivity()).controladorServiceBLE.calibrarMovFlexion(desfaseY);
                    ((HomeActivity) getActivity()).controladorServiceBLE.calibrarMovInclinacion(desfaseZ);



                    contenedorBtnReset.setVisibility(View.VISIBLE);
                    contenedorBtnPausarReanudar.setVisibility(View.VISIBLE);
                    contenedorBtnMedir.setVisibility(View.INVISIBLE);

                }
            }
        }

    }

    public void resetMedicion()//Respuesta del dialog
    {

        resetCalibrado();

        txtPausarReanudar.setText(getString(R.string.pausar));

        //Limpiamos la gráfica.
        grafinaLinealEnTiempoReal.clearLineChart();
        //construimos la grafica lineal de nuevo.
        grafinaLinealEnTiempoReal.setGraficaLineal(mChart);
        grafinaLinealEnTiempoReal.setStartData(false);
        //la pasamos la instancia modificada al controlador.
        sincronizarGrafLinealConControladorBLE(grafinaLinealEnTiempoReal);//Volvemos a "sincronizar".
        //Dejamos visible solo el botón de medir.
        contenedorBtnReset.setVisibility(View.INVISIBLE);
        contenedorBtnPausarReanudar.setVisibility(View.INVISIBLE);
        contenedorBtnMedir.setVisibility(View.VISIBLE);
        if(isConnectSensorAndExistControlador())
        {
            tareasSensor.habilitarNotificacionesAccel();
        }

    }


    public void resetCalibrado()
    {

        if(isConnectSensorAndExistControlador()) {
            grafinaLinealEnTiempoReal.setStartData(true);//habilitamos la recogida de datos
            sincronizarGrafLinealConControladorBLE(grafinaLinealEnTiempoReal);//Volvemos a "sincronizar".

            if (((HomeActivity) getActivity()).controladorServiceBLE.updateMovCabeza != null) {
                if (((HomeActivity) getActivity()).controladorServiceBLE.desfaseAnguloYZ != null) {

                    DesfaseAnguloYZ desfaseAnguloYZ = ((HomeActivity) getActivity()).controladorServiceBLE.desfaseAnguloYZ;
                    desfaseAnguloYZ.setDesfaseZ(0);
                    desfaseAnguloYZ.setDesfaseY(0);
                    ((HomeActivity) getActivity()).controladorServiceBLE.calibrarMovFlexion(0);
                    ((HomeActivity) getActivity()).controladorServiceBLE.calibrarMovInclinacion(0);
                }
            }
        }


    }




    private void iniciarCronometro()
    {
        String t="00:00:00";
        barraProgresoTemporalFragment.txt_tiempo_avanzado.setText(t);
        cronometro= new Cronometro(barraProgresoTemporalFragment.txt_tiempo_avanzado,barraProgresoTemporalFragment.barra_proreso_temporal,SEGUNDOSTOTALES);
        new Thread(cronometro).start();

    }

    public void detenerCronometro()//usado al aceptar el dialog de reiniciar.
    {
        if(cronometro!=null)
        {
            cronometro.continuarHilo=false;
        }
    }






}
