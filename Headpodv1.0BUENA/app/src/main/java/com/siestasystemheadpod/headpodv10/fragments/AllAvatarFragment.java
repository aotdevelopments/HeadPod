package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.ble.DeviceControlFragment;
import com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor.TaskSensorBle;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllAvatarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllAvatarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllAvatarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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
    public RelativeLayout contenedor_cara_flexion;
    public RelativeLayout contenedor_cara_rotacion;
    public RelativeLayout contenedor_cara_inclinacion;
    public RelativeLayout contenedor_botones_avatar;


    public Cara3DFragment cara_fragment_3D;
    private BotonesAvatarFragment  btn_avatar_fragment;


    //Para la configuración de la orientación. Con esto respetamos el diseño landScape
    private FrameLayout frameLayout;

    public AllAvatarFragment() {
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
    public static AllAvatarFragment newInstance(String param1, String param2) {
        AllAvatarFragment fragment = new AllAvatarFragment();
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

       // getActivity().	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

    }

/*
    //http://stackoverflow.com/questions/17116602/using-onconfigurationchanged-in-a-fragment
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        ((HomeActivity)getActivity()).cambiarIdioma();

        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_all_avatar, null);

        setContenidoVista(view);

        frameLayout .addView(view);
    }

*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

       // getActivity().	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);



       // frameLayout = new FrameLayout(getActivity());
        // LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_avatar, container, false);



        setContenidoVista(view);


        //frameLayout .addView(view);
        return view;
       // return frameLayout;
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



        TextView txt_convulsiones = (TextView) view.findViewById(R.id.txtConvulsiones);
        TextView txt_va_m= (TextView) view.findViewById(R.id.txtVelocidadMedia);
        convulsiones = ( TextView) view.findViewById(R.id.convulsiones);
        va_media= (TextView) view.findViewById(R.id.velocidadMedia);

        aceleracion_x = (TextView) view.findViewById(R.id.aceleracion_x);
        aceleracion_y = (TextView) view.findViewById(R.id.aceleracion_y);
        aceleracion_z = (TextView) view.findViewById(R.id.aceleracion_z);

        grados_x = (TextView) view.findViewById(R.id.grados_x);
        grados_y = (TextView) view.findViewById(R.id.grados_y);
        grados_z = (TextView) view.findViewById(R.id.grados_z);

        aceleracion_x_2 = (TextView) view.findViewById(R.id.aceleracion_x_2);
        aceleracion_y_2 = (TextView) view.findViewById(R.id.aceleracion_y_2);
        aceleracion_z_2 = (TextView) view.findViewById(R.id.aceleracion_z_2);

        grados_x_2 = (TextView) view.findViewById(R.id.grados_x_2);
        grados_y_2 = (TextView) view.findViewById(R.id.grados_y_2);
        grados_z_2 = (TextView) view.findViewById(R.id.grados_z_2);




        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);
        txt_convulsiones.setTypeface(myFont);
        txt_va_m.setTypeface(myFont);
        convulsiones.setTypeface(myFont);
        va_media.setTypeface(myFont);
        titulo.setTypeface(myFont);



        aceleracion_x.setTypeface(myFont);
        aceleracion_y.setTypeface(myFont);
        aceleracion_z.setTypeface(myFont);
        aceleracion_x_2.setTypeface(myFont);
        aceleracion_y_2.setTypeface(myFont);
        aceleracion_z_2.setTypeface(myFont);




        //Obtenemos las vistas que van a tener los fragmentos

        contenedor_cara_3D = (RelativeLayout) view.findViewById(R.id.layout_cara_3D);
        contenedor_cara_flexion = (RelativeLayout) view.findViewById(R.id.layout_cara_flexion);
        contenedor_cara_rotacion = (RelativeLayout) view.findViewById(R.id.layout_cara_rotacion);
        contenedor_cara_inclinacion = (RelativeLayout) view.findViewById(R.id.layout_cara_inclinacion);
        contenedor_botones_avatar = (RelativeLayout) view.findViewById(R.id.fragmento_botones_avatar);


        //Redimensionamos el tamaño de cada fragmento
        //En este caso tendran un 80% para la cara  3D y 20% para las caras 2D
        //Framento con la cara en 3D:
        ViewGroup.LayoutParams params =contenedor_cara_3D.getLayoutParams();
        params.width =  (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.70);
        params.height=  (int) (((HomeActivity) getActivity()).getTamanoPantalla().getHeightPixels()*0.45);

        //
        ViewGroup.LayoutParams params2 =contenedor_cara_flexion.getLayoutParams();
        params2.width = (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.35);//doy mas ancho por el margen de los laterales

        ViewGroup.LayoutParams params3 =contenedor_cara_inclinacion.getLayoutParams();
        params3.width = (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.35);


        ViewGroup.LayoutParams params4 =contenedor_cara_rotacion.getLayoutParams();
        params4.width = (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.35);

        //Añadimos los framentos a los elementos de la vista inflada:
        //Paso 1: Obtener la instancia del administrador de fragmentos
        fragmentManagerChild = getChildFragmentManager();//Se usa para los fragmentos de fragmentos.
        //getSupportMannager // Para los fragmentos de actividades.


        //Paso 2: Crear una nueva transacción
        fragmentTransaction = fragmentManagerChild.beginTransaction();

        //Paso 3: Crear un nuevo fragmento y añadirlo

        if(!existFragment("cara_3D") && !existFragment("cara_flexion") && !existFragment("cara_inclinacion") && !existFragment("cara_rotacion") && !existFragment("botones_avatar")) {
            Log.d("fragmento_avatar", "No existo");


            cara_fragment_3D = new Cara3DFragment();
            fragmentTransaction.replace(contenedor_cara_3D.getId(), cara_fragment_3D, "cara_3D");




            //((HomeActivity) getActivity()).cara3DFragment= cara_fragment_3D;

            CaraFlexionFragment caraFlexionFragment = new CaraFlexionFragment();
            fragmentTransaction.replace(contenedor_cara_flexion.getId(), caraFlexionFragment, "cara_flexion");

            CaraInclinacionFragment caraInclinacionFragment = new CaraInclinacionFragment();
            fragmentTransaction.replace(contenedor_cara_inclinacion.getId(), caraInclinacionFragment, "cara_inclinacion");

            CaraRotacionFragment caraRotacionFragment = new CaraRotacionFragment();
            fragmentTransaction.replace(contenedor_cara_rotacion.getId(), caraRotacionFragment, "cara_rotacion");

            btn_avatar_fragment = BotonesAvatarFragment.newInstance("botones_avatar_sin_medicion","");
            fragmentTransaction.replace(contenedor_botones_avatar.getId(), btn_avatar_fragment, "botones_avatar");

            fragmentTransaction.commit();
        }
        else
        {


            Log.d("fragmento_avatar", "Existo");


            // cara_fragment_3D = (Cara3DFragment) fragmentManagerChild.findFragmentByTag("cara_3D");

            //cara_fragment_3D.

            //Cara3DFragment cara_fragment_3D_v2 = new Cara3DFragment();
            //fragmentTransaction.replace(contenedor_cara_3D.getId(), cara_fragment_3D_v2,"cara_3D");



            cara_fragment_3D = (Cara3DFragment) fragmentManagerChild.findFragmentByTag("cara_3D");
            fragmentTransaction.replace(contenedor_cara_3D.getId(), cara_fragment_3D);


            CaraFlexionFragment caraFlexionFragment = (CaraFlexionFragment) fragmentManagerChild.findFragmentByTag("cara_flexion");
            //fragmentTransaction.remove(caraFlexionFragment);
            fragmentTransaction.replace(contenedor_cara_flexion.getId(), caraFlexionFragment);

            CaraInclinacionFragment caraInclinacionFragment = (CaraInclinacionFragment) fragmentManagerChild.findFragmentByTag("cara_inclinacion");
            //fragmentTransaction.remove(caraInclinacionFragment);
            fragmentTransaction.replace(contenedor_cara_inclinacion.getId(), caraInclinacionFragment);

            CaraRotacionFragment caraRotacionFragment = (CaraRotacionFragment) fragmentManagerChild.findFragmentByTag("cara_rotacion");
            // fragmentTransaction.remove(caraRotacionFragment);
            fragmentTransaction.replace(contenedor_cara_rotacion.getId(), caraRotacionFragment);

            btn_avatar_fragment = (BotonesAvatarFragment) fragmentManagerChild.findFragmentByTag("botones_avatar");
            //fragmentTransaction.remove(btn_avatar_fragment);
            fragmentTransaction.replace(contenedor_botones_avatar.getId(), btn_avatar_fragment, "botones_avatar");


            fragmentTransaction.commit();


        }


        /*
        if (caraFlexionFragment.isAdded()) { // if the fragment is already in container
            //fragmentTransaction.show(caraFlexionFragment);
            fragmentTransaction.replace(contenedor_cara_flexion.getId(),caraFlexionFragment,"cara_flexion");
        } else { // fragment needs to be added to frame container
            fragmentTransaction.add(contenedor_cara_flexion.getId(), caraFlexionFragment, "cara_flexion");
        }*/




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

        //cara_fragment_3D = (Cara3DFragment) fragmentManagerChild.findFragmentByTag("cara_3D");
        //fragmentTransaction.remove(cara_fragment_3D);

       // if(cara_fragment_3D!=null)
        //cara_fragment_3D.onPause();








        //*************************************************************************************
        /*
        if(((HomeActivity) getActivity()).getEstadoSensor()==1) {
            TaskSensorBle tareasSensor = new TaskSensorBle(getActivity());
            tareasSensor.desHabilitarAcelerometro();
            tareasSensor.desHabilitarNotificacionesAccel();

            //cara_fragment_3D.getFace().animationEnabled(false);
            //cara_fragment_3D.moverCara3D(false);
        }
        */



       // ((HomeActivity) getActivity()).controladorServiceBLE.desHabilitarNotificacionesAcelerometro();

            //getActivity().unbindService(((HomeActivity) getActivity()).controladorServiceBLE.getmServiceConnection());

        //****************************************************************************************


        if(btn_avatar_fragment!=null && btn_avatar_fragment.getTimer()!=null)
        {
            btn_avatar_fragment.getTimer().cancel();
        }


        super.onPause();


       // fragmentTransaction.hide(caraFlexionFragment);



       // getChildFragmentManager().findFragmentByTag("cara_flexion").setRetainInstance(true);
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

       // DeviceControlFragment deviceControlFragment = (DeviceControlFragment) getActivity().getSupportFragmentManager().findFragmentByTag("fragment_device_control");

        //deviceControlFragment.connectServidorBle();


        super.onResume();

       // getActivity().	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
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
