package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.RegisterActivity;
import com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor.TaskSensorBle;

import java.util.Locale;





/*

// get fragment manager
FragmentManager fm = getFragmentManager();

// add
FragmentTransaction ft = fm.beginTransaction();
ft.add(R.id.your_placehodler, new YourFragment());
// alternatively add it with a tag
// trx.add(R.id.your_placehodler, new YourFragment(), "detail");
ft.commit();

// replace
FragmentTransaction ft = fm.beginTransaction();
ft.replace(R.id.your_placehodler, new YourFragment());
ft.commit();

// remove
Fragment fragment = fm.findFragmentById(R.id.your_placehodler);
FragmentTransaction ft = fm.beginTransaction();
ft.remove(fragment);
ft.commit();

http://www.vogella.com/tutorials/AndroidFragments/article.html



 */


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String CALIBRI_BOLD="fonts/Calibri Bold.ttf";

    private TextView txtTituloMenuHome;
    private TextView txtOpcionAvatar;
    private TextView txtOpcionMedición;
    private TextView txtOpcionMisPacientes;
    private TextView txtOpcionCargarPaciente;

    private Context contexto;

    ImageView btnAvatar ;
    ImageView btnMedicion;
    ImageView btnMisPacientes  ;
    ImageView btnCargarPaciente ;

    private OnFragmentInteractionListener mListener;


    //Para la configuración de la orientación. Con esto respetamos el diseño landScape
    private FrameLayout frameLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        ((HomeActivity)getActivity()).cambiarIdioma();
        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_home, null);


        setContenidoVista(view);


        frameLayout .addView(view);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        frameLayout = new FrameLayout(getActivity());
        // LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ((HomeActivity) getActivity()).setFragmentActual(this);
        //((HomeActivity) getActivity()).fragmentActual=this;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setContenidoVista(view);

        frameLayout .addView(view);
        //return view;
        return frameLayout;
    }


    public void setContenidoVista(View view)
    {

        //Para el titulo del menú
        //((HomeActivity) getActivity()).getSupportActionBar().setTitle("");
        TextView titulo= (TextView)((HomeActivity) getActivity()).findViewById(R.id.toolbar_title);

        titulo.setText(R.string.titulo_home);

        txtTituloMenuHome = (TextView) view.findViewById(R.id.tituloMenuHome);
        txtOpcionAvatar = (TextView)view.findViewById(R.id.txtAvatar);
        txtOpcionMedición =(TextView) view.findViewById(R.id.txtMedicion);
        txtOpcionMisPacientes = (TextView) view.findViewById(R.id.txtMisPacientes);


        //botones auxiliares y texview para quitar despues

        TextView txtInforme = (TextView) view.findViewById(R.id.txtInforme);
        ImageView btnInforme = (ImageView) view.findViewById(R.id.btnInforme);

        TextView txtInformeComparativo = (TextView) view.findViewById(R.id.txtInformeCoparativo);
        ImageView btnInformeComparativo = (ImageView) view.findViewById(R.id.btnInformeCoparativo);



        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);
        txtTituloMenuHome.setTypeface(myFont);
        txtOpcionAvatar.setTypeface(myFont);
        txtOpcionMedición.setTypeface(myFont);
        txtOpcionMisPacientes.setTypeface(myFont);

        titulo.setTypeface(myFont);

        btnAvatar = (ImageView) view.findViewById(R.id.btnAvatar);
        btnMedicion= (ImageView) view.findViewById(R.id.btnMedicion);
        btnMisPacientes = (ImageView) view.findViewById(R.id.btnMisPacientes);



        btnAvatar.setOnClickListener(this);
        btnMedicion.setOnClickListener(this);
        btnMisPacientes.setOnClickListener(this);

        txtOpcionAvatar.setOnClickListener(this);
        txtOpcionMedición.setOnClickListener(this);
        txtOpcionMisPacientes.setOnClickListener(this);


        //Escuchadroes auxiliares, borrar después.

        txtInforme.setOnClickListener(this);
        txtInformeComparativo.setOnClickListener(this);
        btnInforme.setOnClickListener(this);
        btnInformeComparativo.setOnClickListener(this);






        //  View view = inflater.inflate(R.layout.fragment_home, container, false);
        //getActivity().setContentView(R.layout.activity_home);

        /*
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        int densityDpi = metrics.densityDpi;


        int widthFinal;
        //Colocaer Logo redimensionado tamaño manteniendo la proporcion

        if(widthPixels>320 && widthPixels<720)
        {
            widthFinal=110;
        }
        else if(widthPixels<=320)
        {
            widthFinal=75;
        }
        else
        {
            widthFinal=175;
        }

        Bitmap imagenOriginal = BitmapFactory.decodeResource(getResources(),R.drawable.logo_headpod_sin_leyenda);

        // Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal,887,250,false);
        // Dividimos el ancho final por el ancho de la imagen original
        //***********************************************************************************************************************
        float proporcion = widthFinal / (float) imagenOriginal.getWidth();

        int heightFinal = (int) (imagenOriginal.getHeight() * proporcion);
        Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal,widthFinal,heightFinal,false);


        ImageView logo = (ImageView) view.findViewById(R.id.logo_cabecera);
        logo.setImageBitmap(imagenFinal);

        //***********************************************************************************************************************************
        txtAliasMenu = (TextView) view.findViewById(R.id.txt_alias_menu);
        txtEmailMenu = (TextView) view.findViewById(R.id.txt_email_menu);
        //**************************************************************

       // return inflater.inflate(R.layout.fragment_home, container, false);
       */

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

/*
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/


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


    @Override
    public void onClick(View v) {

        
        switch (v.getId())
        {
            case R.id.btnAvatar: //Toast.makeText(getContext(),"Datos en tiempo real",Toast.LENGTH_SHORT).show();

                if(mListener!=null)
                {


                    ((HomeActivity) getActivity()).cargarDatosEnTReal();
                    //Aqui es donde tendria que venir el proceso de calibrar
                    //Metodo para habilitar acelerómetro y notificaciones
                   // mListener.onFragmentInteraction("fragment_all_avatar");//avatar3d
                }

                break;


            case R.id.txtAvatar:

                if(mListener!=null)
                {


                    ((HomeActivity) getActivity()).cargarDatosEnTReal();

                    //mListener.onFragmentInteraction("fragment_avatar");

                    //Aqui es donde tendria que venir el proceso de calibrar
                    //Metodo para habilitar acelerómetro y notificaciones
                   // habilitandoSensor();

                   // mListener.onFragmentInteraction("fragment_all_avatar");//avatar3d
                }
                break;


            case R.id.btnMedicion:  //Toast.makeText(getContext(),"inicar Medición",Toast.LENGTH_SHORT).show();

                if(mListener!=null)
                {
                    mListener.onFragmentInteraction("fragment_medicion");

                }
                break;


            case R.id.txtMedicion:

                if(mListener!=null)
                {

                    mListener.onFragmentInteraction("fragment_medicion");

                }
                break;


            case R.id.btnMisPacientes:

                //Toast.makeText(getContext(),"cargar terapeuta",Toast.LENGTH_SHORT).show();
               // mListener.onFragmentInteraction("fragment_soplar");//avatar3d
               // Log.d("nivel_bat",((HomeActivity)getActivity()).controladorServiceBLE.getNivelBat2());
                //obtenerIdiomaUsado();
              //  int id = getResources().getIdentifier("com.siestasystemheadpod.headpodv10:drawable/" + "bateria_mini_con_sensor_2", null, null);
                //((HomeActivity)getActivity()).imgSensorSincronizando.setImageResource(id);


                //TaskSensorBle taskSensorBle = new TaskSensorBle(getActivity());

              //  taskSensorBle.habilitarNotificacionesAccel();


                if(mListener!=null)
                {

                    mListener.onFragmentInteraction("fragment_mis_pacientes");

                }
                break;

            case R.id.txtMisPacientes:  //Toast.makeText(getContext(),"Cargar paciente",Toast.LENGTH_SHORT).show();

                if(mListener!=null)
                {

                    mListener.onFragmentInteraction("fragment_mis_pacientes");

                }
                //leerBateria();
                break;

            case R.id.txtInforme:
                Log.d("hola","fsdfasdfas");

               //mListener.onFragmentInteraction("fragment_informe");


                if(mListener!=null)
                {

                    mListener.onFragmentInteraction("fragment_informe");

                }


                break;

            case R.id.btnInforme:

                Log.d("hola","fasdfsadfsa");
               // mListener.onFragmentInteraction("fragment_informe");

                break;

            case R.id.txtInformeCoparativo:


                if(mListener!=null)
                {

                    mListener.onFragmentInteraction("fragment_informe_comparativo");

                }

                break;

            case R.id.btnInformeCoparativo:


                Intent intent = new Intent(getActivity(), RegisterActivity.class);

                startActivity(intent);


                /*if(mListener!=null)
                {

                    mListener.onFragmentInteraction("fragment_informe_comparativo");

                }*/

                break;




        }


    }




    public int obtenerIdiomaUsado()
    {
        //0=es=español
        //1=en=ingles.

        if(((HomeActivity)getActivity()).getLocaleActivity()!=null) {
            Log.d("idioma", ((HomeActivity) getActivity()).getLocaleActivity().getLanguage());
            if (((HomeActivity) getActivity()).getLocaleActivity().getLanguage().equals("es")) {
                return 0;
            } else {
                return 1;
            }

        }
        else{
            Log.d("idioma_default", Locale.getDefault().getLanguage());
            if(Locale.getDefault().getLanguage().equals("es"))
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
    }






    private void leerBateria()
    {

        if(  ((HomeActivity) getActivity()).getEstadoSensor()==1) {


            TaskSensorBle taskSensorBle1 = new TaskSensorBle(getActivity());

            taskSensorBle1.leerBateria(getContext());

            //Cara3DFragment cara3DFragment = new Cara3DFragment();



           // taskSensorBle.habilitarNotificacionesAccel();


            //taskSensorBle.leerBateria(getContext());
            //taskSensorBle.desHabilitarNotificacionesAccel();


          //  Log.d("ESPACIO","ESPACIOOOOOOOO");
            //((HomeActivity) getActivity()).timeWaitCharacteristic(200);
            //taskSensorBle.leerBateria(getContext());




           // taskSensorBle.habilitarNotificacionesAccel();


            // taskSensorBle.leerDatosAcelerometroCara3D(cara3DFragment);

        }


        /*

        if(  ((HomeActivity) getActivity()).getEstadoSensor()==1)
        {
            TaskSensorBle taskSensorBle = new TaskSensorBle(getActivity());

            taskSensorBle.desHabilitarNotificacionesAccel();

            //taskSensorBle.habilitarNotificacionesAccel();

            Log.d("espera",String.valueOf(taskSensorBle.espera));
            while(taskSensorBle.espera)
            {
                Log.d("ESPERA_TAREA","ESPERA TAREAAAAAAAAA");
            }

            Log.d("espera_fin",String.valueOf(taskSensorBle.espera));


            taskSensorBle.leerBateria(getContext());

        //
          //  if(  ((HomeActivity) getActivity()).controladorServiceBLE!=null)
            //{
              //  ((HomeActivity) getActivity()).controladorServiceBLE.getValorBateria(getContext());
            //}

        }
            */
    }



/*
    public void vincularSensor() {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        if (!((HomeActivity) getActivity()).existFragment("vincular_desvincular_sensor")) {
            //Paso 1: Obtener la instancia del administrador de fragmentos
            fragmentManager = getActivity().getSupportFragmentManager();
            //Paso 2: Crear una nueva transacción
            fragmentTransaction = fragmentManager.beginTransaction();

            //Paso 3: Crear un nuevo fragmento y añadirlo
            //Creamos el fragmento contenedor de todos los fragmentos de la vista avatar.
            DeviceScanFragment deviceScanFragment = new DeviceScanFragment();
            fragmentTransaction.replace(R.id.contenedorHome, deviceScanFragment, "vincular_desvincular_sensor");//R.id.contenedorHome

            //NO INTERESA QUE EL USUARIO PUEDA VOLVER A LA PARTE DE CONEXION
            //*************************************************************************
            fragmentTransaction.addToBackStack("vincular_desvincular_sensor");
            //((HomeActivity)getActivity()).pilaAuxFragments.add("vincular_desvincular_sensor");
            //fragmentTransaction.commitAllowingStateLoss();
            fragmentTransaction.commit();
        }
    }
*/


}
