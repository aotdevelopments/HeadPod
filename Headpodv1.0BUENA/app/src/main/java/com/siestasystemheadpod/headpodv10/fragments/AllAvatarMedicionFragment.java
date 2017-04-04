package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.EfectoTxtClick;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllAvatarMedicionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllAvatarMedicionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllAvatarMedicionFragment extends Fragment implements View.OnClickListener{

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

    public RelativeLayout contenedor_btn_izquierda_asignacion;
    public RelativeLayout contenedor_btn_derecha_asignacion;
    public RelativeLayout contenedor_pestana_izquierda;
    public RelativeLayout contenedor_pestana_derecha;
    public RelativeLayout contenedor_barra_temporal;
    public RelativeLayout contenedor_cara_3D;
    public RelativeLayout contenedor_cara_flexion;
    public RelativeLayout contenedor_cara_rotacion;
    public RelativeLayout contenedor_cara_inclinacion;
    public RelativeLayout contenedor_botones_avatar;



    public BotonPestanaFragment btn_izquierda_asignacion;

    public BotonPestanaFragment  btn_derecha_asignacion;

    public BotonBelowPestanaFragment btn_pestana_izquierda;

    public  BotonBelowPestanaFragment btn_pestana_derecha;


    public Cara3DFragment cara_fragment_3D;



    public AllAvatarMedicionFragment() {
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
    public static AllAvatarMedicionFragment newInstance(String param1, String param2) {
        AllAvatarMedicionFragment fragment = new AllAvatarMedicionFragment();
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

        cara_fragment_3D = new Cara3DFragment();


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_avatar_medicion, container, false);

        //Para el titulo del menú
        //((HomeActivity) getActivity()).getSupportActionBar().setTitle("");
        TextView titulo= (TextView)((HomeActivity) getActivity()).findViewById(R.id.toolbar_title);

        switch (mParam1)
        {
            case "medicion_sin_hp":
                titulo.setText(R.string.medicion_sin_hp);
                break;

            case "medicion_con_hp":

                titulo.setText(R.string.medicion_con_hp);
                break;

        }

        TextView txt_convulsiones = (TextView) view.findViewById(R.id.txtConvulsiones);
        TextView txt_va_m= (TextView) view.findViewById(R.id.txtVelocidadMedia);
        convulsiones = ( TextView) view.findViewById(R.id.convulsiones);
        va_media= (TextView) view.findViewById(R.id.velocidadMedia);


        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);
        txt_convulsiones.setTypeface(myFont);
        txt_va_m.setTypeface(myFont);
        convulsiones.setTypeface(myFont);
        va_media.setTypeface(myFont);
        titulo.setTypeface(myFont);


        //Obtenemos las vistas que van a tener los fragmentos
        contenedor_btn_izquierda_asignacion = (RelativeLayout) view.findViewById(R.id.layout_btnIzquierdaAsignancion);
        contenedor_btn_derecha_asignacion = (RelativeLayout) view.findViewById(R.id.layout_btnDerechaAsignacion);
        contenedor_pestana_izquierda = (RelativeLayout) view.findViewById(R.id.layout_btnPestanaIzquierda);
        contenedor_pestana_derecha = (RelativeLayout) view.findViewById(R.id.layout_btnPestanaDerecha);
        contenedor_barra_temporal = (RelativeLayout) view.findViewById(R.id.layout_fragmento_barra_temporal);

        contenedor_cara_3D = (RelativeLayout) view.findViewById(R.id.layout_cara_3D);
        contenedor_cara_flexion = (RelativeLayout) view.findViewById(R.id.layout_cara_flexion);
        contenedor_cara_rotacion = (RelativeLayout) view.findViewById(R.id.layout_cara_rotacion);
        contenedor_cara_inclinacion = (RelativeLayout) view.findViewById(R.id.layout_cara_inclinacion);
        contenedor_botones_avatar = (RelativeLayout) view.findViewById(R.id.fragmento_botones_avatar);


        //Colocamos el borde a los contenedores de las pestañas, inicializandolas a un estado inicial
        //*********************************************************************************************
        //Pestanas superiores
       // cambiarEstadoPestana(contenedor_btn_izquierda_asignacion,R.drawable.border_btn_pestana_izqda_presionada,R.drawable.border_btn_pestana_izqda);
       // cambiarEstadoPestana(contenedor_btn_derecha_asignacion,R.drawable.border_btn_pestana_dcha_presionada,R.drawable.border_btn_pestana_dcha);

        //Pestanañas inferiores (Pestañabelow)
        //cambiarEstadoPestana(contenedor_pestana_izquierda,R.drawable.border_btn_below_pestana_presionado,R.drawable.border_btn_below_pestana_activa);
        //cambiarEstadoPestana(contenedor_pestana_derecha,R.drawable.border_btn_below_pestana_presionado,R.drawable.border_btn_below_pestana_inactiva);

        //Escuchamos los contenedores de las pestañas
        //****************************************************************************************************************************
        contenedor_pestana_izquierda.setOnClickListener(this);
        contenedor_pestana_derecha.setOnClickListener(this);

        contenedor_btn_izquierda_asignacion.setOnClickListener(this);
        contenedor_btn_derecha_asignacion.setOnClickListener(this);

        //Redimensionamos el tamaño de cada fragmento
        //****************************************************************************************************************************
        //Botones pestañas superiores de asignación
        ViewGroup.LayoutParams paramBtnIzqdaArriba =contenedor_btn_izquierda_asignacion.getLayoutParams();
        paramBtnIzqdaArriba.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.50);
        paramBtnIzqdaArriba.height=  (int) (((HomeActivity) getActivity()).getHeightPixels()*0.07);

        ViewGroup.LayoutParams paramsBtnDchaArriba =contenedor_btn_derecha_asignacion.getLayoutParams();
        paramsBtnDchaArriba.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.50);
        paramsBtnDchaArriba.height=  (int) (((HomeActivity) getActivity()).getHeightPixels()*0.07);

        //Redimensionamos el tamaño en el propio fragmento. El objetivo de esto es conseguir asignar un borde
        //dinamico en función de los click

        //Botones pestañas inferiores below
        ViewGroup.LayoutParams paramBtnPestanaIzqdaArriba =contenedor_pestana_izquierda.getLayoutParams();
        paramBtnPestanaIzqdaArriba.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.50);

        ViewGroup.LayoutParams paramsBtnPestanaDchaArriba =contenedor_pestana_derecha.getLayoutParams();
        paramsBtnPestanaDchaArriba.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.50);

        //Framento con la cara en 3D:
        ViewGroup.LayoutParams params =contenedor_cara_3D.getLayoutParams();
        params.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.70);
        params.height=  (int) (((HomeActivity) getActivity()).getHeightPixels()*0.41);//41-42 para tablet nexus 10 pulgadas

        //Fragmento cara_flexion
        ViewGroup.LayoutParams params2 =contenedor_cara_flexion.getLayoutParams();
        params2.width = (int) (((HomeActivity) getActivity()).getWidthPixels()*0.305);//doy mas ancho por el margen de los laterales

        //Fragmento cara inclinacion
        ViewGroup.LayoutParams params3 =contenedor_cara_inclinacion.getLayoutParams();
        params3.width = (int) (((HomeActivity) getActivity()).getWidthPixels()*0.305);

        //Fragmento cara rotacion
        ViewGroup.LayoutParams params4 =contenedor_cara_rotacion.getLayoutParams();
        params4.width = (int) (((HomeActivity) getActivity()).getWidthPixels()*0.305);







        //Añadimos los framentos a los elementos de la vista inflada:
        //*******************************************************************************************************************************************
        //Paso 1: Obtener la instancia del administrador de fragmentos
        fragmentManagerChild = getChildFragmentManager();//Se usa para los fragmentos de fragmentos.
        //getSupportMannager // Para los fragmentos de actividades.

        //Paso 2: Crear una nueva transacción
        fragmentTransaction = fragmentManagerChild.beginTransaction();

        //Paso 3: Crear un nuevo fragmento y añadirlo


        // eliminarCara3D();

         if(!existFragment("cara_3D") && !existFragment("cara_flexion") && !existFragment("cara_inclinacion") && !existFragment("cara_rotacion") &&
                  !existFragment("botones_avatar")) {

        Log.d("fragmento_avatar", "No existo");
        //Aquí tendría que coger el terapeuta que haya iniciado sesión


         if(!existFragment("btn_izquierda_asignacion"))
         {
        btn_izquierda_asignacion = BotonPestanaFragment.newInstance(getResources().getString(R.string.terapeuta_sin_asignar),"");
        fragmentTransaction.replace(contenedor_btn_izquierda_asignacion.getId(),btn_izquierda_asignacion);

          }


         if(!existFragment("btn_derecha_asignacion"))
          {
        btn_derecha_asignacion = BotonPestanaFragment.newInstance(getResources().getString(R.string.paciente_sin_asignar),"");
        fragmentTransaction.replace(contenedor_btn_derecha_asignacion.getId(),btn_derecha_asignacion);

         }

         if(!existFragment("pestana_izquierda"))
        {


        btn_pestana_izquierda = BotonBelowPestanaFragment.newInstance(getResources().getString(R.string.pestana_en_tiempo_real), "");//datos en tiempo real
        fragmentTransaction.replace(contenedor_pestana_izquierda.getId(),btn_pestana_izquierda);

         }

        if(!existFragment("pestana_derecha"))
         {

        btn_pestana_derecha = BotonBelowPestanaFragment.newInstance(getResources().getString(R.string.estadisticas), "");
        fragmentTransaction.replace(contenedor_pestana_derecha.getId(),btn_pestana_derecha);

        }


         if(!existFragment("barra_progreso_temporal"))
         {
        Log.d("fragmento_avatar", "barra_medicion");
        BarraProgresoTemporalFragment progressBarFragment =  new BarraProgresoTemporalFragment();
        fragmentTransaction.replace(contenedor_barra_temporal.getId(), progressBarFragment);
          }



        fragmentTransaction.replace(contenedor_cara_3D.getId(), cara_fragment_3D);

        CaraFlexionFragment caraFlexionFragment = new CaraFlexionFragment();
        fragmentTransaction.replace(contenedor_cara_flexion.getId(), caraFlexionFragment );

        CaraInclinacionFragment caraInclinacionFragment = new CaraInclinacionFragment();
        fragmentTransaction.replace(contenedor_cara_inclinacion.getId(), caraInclinacionFragment );

        CaraRotacionFragment caraRotacionFragment = new CaraRotacionFragment();
        fragmentTransaction.replace(contenedor_cara_rotacion.getId(), caraRotacionFragment);

        BotonesAvatarFragment btn_avatar_fragment = BotonesAvatarFragment.newInstance("botones_avatar_con_medicion","");
        fragmentTransaction.replace(contenedor_botones_avatar.getId(), btn_avatar_fragment);

        fragmentTransaction.commit();

         }
        else
        {
            Log.d("fragmento_avatar", "Existo");

            //Aquí tendría que coger el terapeuta que haya iniciado sesión
            if(existFragment("btn_izquierda_asignacion"))
            {

            //OJO: AL CAMBIAR DE IDIOMA ES NECESARIO VOLVER A CREAR LA INSTANCIA PARA QUE PILLE EL IDIOMA CORRECTO.

                btn_izquierda_asignacion = (BotonPestanaFragment) fragmentManagerChild.findFragmentByTag("btn_izquierda_asignacion");//OJO AL CAMBIAR EL IDIOMA ASÍ NO LO CAMBIARIA.
               // btn_izquierda_asignacion = BotonPestanaFragment.newInstance(getResources().getString(R.string.terapeuta_sin_asignar),"");
                fragmentTransaction.replace(contenedor_btn_izquierda_asignacion.getId(),btn_izquierda_asignacion);
            }

            if(existFragment("btn_derecha_asignacion"))
            {

                btn_derecha_asignacion = (BotonPestanaFragment) fragmentManagerChild.findFragmentByTag("btn_derecha_asignacion");

               // btn_derecha_asignacion = BotonPestanaFragment.newInstance(getResources().getString(R.string.paciente_sin_asignar),"");
                fragmentTransaction.replace(contenedor_btn_derecha_asignacion.getId(),btn_derecha_asignacion);

            }
            if(existFragment("pestana_izquierda"))
            {

                BotonBelowPestanaFragment btn_pestana_izquierda = (BotonBelowPestanaFragment) fragmentManagerChild.findFragmentByTag("pestana_izquierda");
            //btn_pestana_izquierda = BotonBelowPestanaFragment.newInstance(getResources().getString(R.string.pestana_en_tiempo_real), "");//datos en tiempo real

                fragmentTransaction.replace(contenedor_pestana_izquierda.getId(),btn_pestana_izquierda);

            }

            if(existFragment("pestana_derecha"))
            {
                BotonBelowPestanaFragment btn_pestana_derecha = (BotonBelowPestanaFragment) fragmentManagerChild.findFragmentByTag("pestana_derecha");
                 //btn_pestana_derecha = BotonBelowPestanaFragment.newInstance(getResources().getString(R.string.estadisticas), "");
                fragmentTransaction.replace(contenedor_pestana_derecha.getId(),btn_pestana_derecha);
            }

            if(existFragment("barra_progreso_temporal"))
            {

                Log.d("fragmento_avatar", "barra_medicion");
                BarraProgresoTemporalFragment progressBarFragment =  (BarraProgresoTemporalFragment) fragmentManagerChild.findFragmentByTag("barra_progreso_temporal");
                fragmentTransaction.replace(contenedor_barra_temporal.getId(), progressBarFragment);

            }

            Cara3DFragment cara_fragment_3D = (Cara3DFragment) fragmentManagerChild.findFragmentByTag("cara_3D");
            fragmentTransaction.replace(contenedor_cara_3D.getId(), cara_fragment_3D);

            CaraFlexionFragment caraFlexionFragment = (CaraFlexionFragment) fragmentManagerChild.findFragmentByTag("cara_flexion");
            fragmentTransaction.replace(contenedor_cara_flexion.getId(), caraFlexionFragment);

            CaraInclinacionFragment caraInclinacionFragment = (CaraInclinacionFragment) fragmentManagerChild.findFragmentByTag("cara_inclinacion");
            fragmentTransaction.replace(contenedor_cara_inclinacion.getId(), caraInclinacionFragment);

            CaraRotacionFragment caraRotacionFragment = (CaraRotacionFragment) fragmentManagerChild.findFragmentByTag("cara_rotacion");
            fragmentTransaction.replace(contenedor_cara_rotacion.getId(), caraRotacionFragment);

            BotonesAvatarFragment btn_avatar_fragment = (BotonesAvatarFragment) fragmentManagerChild.findFragmentByTag("botones_avatar");
            fragmentTransaction.replace(contenedor_botones_avatar.getId(), btn_avatar_fragment, "botones_avatar");

            fragmentTransaction.commit();

        }

        return view;

    }






    public void  eliminarCara3D()
    {
        if(fragmentManagerChild!=null) {
            if (fragmentManagerChild.getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry first = fragmentManagerChild.getBackStackEntryAt(0);
                if (first.getName().equals("cara_3D")) {
                    fragmentManagerChild.popBackStackImmediate(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    fragmentManagerChild.popBackStack();
                }
            }
        }

    }






    public boolean existFragment(String tagFrament)
    {
        boolean exito= false;

        Fragment fragment = fragmentManagerChild.findFragmentByTag(tagFrament);

        if(fragment!=null){

           // getChildFragmentManager().beginTransaction().detach(fragment).commit();
            //getChildFragmentManager().beginTransaction().attach(fragment).commit();
            //getChildFragmentManager().beginTransaction().remove(fragment).commit();


            exito = true;
        }

        return  exito;

    }


public void onClick(View v)
{

    switch (v.getId())
    {
        case R.id.layout_btnPestanaIzquierda :

            Log.d("click","soy pestaña izquierda");
            //PestañaBelowIzquierda
            cambiarEstadoPestana(contenedor_pestana_izquierda,R.drawable.border_btn_below_pestana_presionado,R.drawable.border_btn_below_pestana_activa);

            //PestañaBelowIzquierda
            cambiarEstadoPestana(contenedor_pestana_derecha,R.drawable.border_btn_below_pestana_presionado,R.drawable.border_btn_below_pestana_inactiva);

            break;

        case R.id.layout_btnPestanaDerecha:

            Log.d("click","soy pestaña derecha");

            //PestañaBelowDerecha
            cambiarEstadoPestana(contenedor_pestana_derecha,R.drawable.border_btn_below_pestana_presionado,R.drawable.border_btn_below_pestana_activa);

            //PestañaBelowIzquierda
            cambiarEstadoPestana(contenedor_pestana_izquierda,R.drawable.border_btn_below_pestana_presionado,R.drawable.border_btn_below_pestana_inactiva);
            break;




        case R.id.layout_btnIzquierdaAsignancion:
            Log.d("click","soy pestaña izquierda superior");

           // btn_izquierda_asignacion.txtBtnName.setTextColor(ContextCompat.getColor(getContext(),R.color.rojo_ocuro));



            new EfectoTxtClick(getContext(),btn_izquierda_asignacion.txtBtnName,R.animator.txt_color_efecto,80);

                break;

        case R.id.layout_btnDerechaAsignacion:

            Log.d("click","soy pestaña derecha superior");

            new EfectoTxtClick(getContext(),btn_derecha_asignacion.txtBtnName,R.animator.txt_color_efecto,80);


            break;
    }



}

    public void cambiarEstadoPestana(RelativeLayout contenedorPestana, int presionado, int normal)
    {

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed}, //presionado
                ContextCompat.getDrawable(getContext(),presionado));

        states.addState(new int[] { },//normal
                ContextCompat.getDrawable(getContext(),normal));
        //  states2.addState(new int[] {android.R.attr.state_focused},//focused
        //ContextCompat.getDrawable(getContext(),R.drawable.border_btn_below_pestana_activa));
        contenedorPestana.setBackground(states);

    }




/*
    @Override
    public void onPause() {

        super.onPause();
       // fragmentTransaction.hide(caraFlexionFragment);

        getChildFragmentManager().findFragmentByTag("cara_flexion")
                .setRetainInstance(true);
    }

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





/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_avatar_medicion, container, false);

        //Para el titulo del menú
        //((HomeActivity) getActivity()).getSupportActionBar().setTitle("");
        TextView titulo= (TextView)((HomeActivity) getActivity()).findViewById(R.id.toolbar_title);

        switch (mParam1)
        {
            case "medicion_sin_hp":
                titulo.setText(R.string.medicion_sin_hp);
                break;

            case "medicion_con_hp":

                titulo.setText(R.string.medicion_con_hp);
                break;

        }

        TextView txt_convulsiones = (TextView) view.findViewById(R.id.txtConvulsiones);
        TextView txt_va_m= (TextView) view.findViewById(R.id.txtVelocidadMedia);
        convulsiones = ( TextView) view.findViewById(R.id.convulsiones);
        va_media= (TextView) view.findViewById(R.id.velocidadMedia);


        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);
        txt_convulsiones.setTypeface(myFont);
        txt_va_m.setTypeface(myFont);
        convulsiones.setTypeface(myFont);
        va_media.setTypeface(myFont);
        titulo.setTypeface(myFont);


        //Obtenemos las vistas que van a tener los fragmentos
        contenedor_btn_izquierda_asignacion = (RelativeLayout) view.findViewById(R.id.layout_btnIzquierdaAsignancion);
        contenedor_btn_derecha_asignacion = (RelativeLayout) view.findViewById(R.id.layout_btnDerechaAsignacion);
        contenedor_pestana_izquierda = (RelativeLayout) view.findViewById(R.id.layout_btnPestanaIzquierda);
        contenedor_pestana_derecha = (RelativeLayout) view.findViewById(R.id.layout_btnPestanaDerecha);
        contenedor_barra_temporal = (RelativeLayout) view.findViewById(R.id.layout_fragmento_barra_temporal);

        contenedor_cara_3D = (RelativeLayout) view.findViewById(R.id.layout_cara_3D);
        contenedor_cara_flexion = (RelativeLayout) view.findViewById(R.id.layout_cara_flexion);
        contenedor_cara_rotacion = (RelativeLayout) view.findViewById(R.id.layout_cara_rotacion);
        contenedor_cara_inclinacion = (RelativeLayout) view.findViewById(R.id.layout_cara_inclinacion);
        contenedor_botones_avatar = (RelativeLayout) view.findViewById(R.id.fragmento_botones_avatar);


        //Colocamos el borde a los contenedores de las pestañas, inicializandolas a un estado inicial
        //*********************************************************************************************
        //Pestanas superiores
        cambiarEstadoPestana(contenedor_btn_izquierda_asignacion,R.drawable.border_btn_pestana_izqda_presionada,R.drawable.border_btn_pestana_izqda);
        cambiarEstadoPestana(contenedor_btn_derecha_asignacion,R.drawable.border_btn_pestana_dcha_presionada,R.drawable.border_btn_pestana_dcha);

        //Pestanañas inferiores (Pestañabelow)
        cambiarEstadoPestana(contenedor_pestana_izquierda,R.drawable.border_btn_below_pestana_presionado,R.drawable.border_btn_below_pestana_activa);
        cambiarEstadoPestana(contenedor_pestana_derecha,R.drawable.border_btn_below_pestana_presionado,R.drawable.border_btn_below_pestana_inactiva);

        //Escuchamos los contenedores de las pestañas
        //****************************************************************************************************************************
        contenedor_pestana_izquierda.setOnClickListener(this);
        contenedor_pestana_derecha.setOnClickListener(this);

        contenedor_btn_izquierda_asignacion.setOnClickListener(this);
        contenedor_btn_derecha_asignacion.setOnClickListener(this);

        //Redimensionamos el tamaño de cada fragmento
        //****************************************************************************************************************************
        //Botones pestañas superiores de asignación
        ViewGroup.LayoutParams paramBtnIzqdaArriba =contenedor_btn_izquierda_asignacion.getLayoutParams();
        paramBtnIzqdaArriba.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.50);
        paramBtnIzqdaArriba.height=  (int) (((HomeActivity) getActivity()).getHeightPixels()*0.07);

        ViewGroup.LayoutParams paramsBtnDchaArriba =contenedor_btn_derecha_asignacion.getLayoutParams();
        paramsBtnDchaArriba.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.50);
        paramsBtnDchaArriba.height=  (int) (((HomeActivity) getActivity()).getHeightPixels()*0.07);

        //Redimensionamos el tamaño en el propio fragmento. El objetivo de esto es conseguir asignar un borde
        //dinamico en función de los click

        //Botones pestañas inferiores below
        ViewGroup.LayoutParams paramBtnPestanaIzqdaArriba =contenedor_pestana_izquierda.getLayoutParams();
        paramBtnPestanaIzqdaArriba.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.50);

        ViewGroup.LayoutParams paramsBtnPestanaDchaArriba =contenedor_pestana_derecha.getLayoutParams();
        paramsBtnPestanaDchaArriba.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.50);

        //Framento con la cara en 3D:
        ViewGroup.LayoutParams params =contenedor_cara_3D.getLayoutParams();
        params.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.70);
        params.height=  (int) (((HomeActivity) getActivity()).getHeightPixels()*0.41);//41-42 para tablet nexus 10 pulgadas

        //Fragmento cara_flexion
        ViewGroup.LayoutParams params2 =contenedor_cara_flexion.getLayoutParams();
        params2.width = (int) (((HomeActivity) getActivity()).getWidthPixels()*0.305);//doy mas ancho por el margen de los laterales

        //Fragmento cara inclinacion
        ViewGroup.LayoutParams params3 =contenedor_cara_inclinacion.getLayoutParams();
        params3.width = (int) (((HomeActivity) getActivity()).getWidthPixels()*0.305);

        //Fragmento cara rotacion
        ViewGroup.LayoutParams params4 =contenedor_cara_rotacion.getLayoutParams();
        params4.width = (int) (((HomeActivity) getActivity()).getWidthPixels()*0.305);







        //Añadimos los framentos a los elementos de la vista inflada:
        //*******************************************************************************************************************************************
        //Paso 1: Obtener la instancia del administrador de fragmentos
        fragmentManagerChild = getChildFragmentManager();//Se usa para los fragmentos de fragmentos.
        //getSupportMannager // Para los fragmentos de actividades.

        //Paso 2: Crear una nueva transacción
        fragmentTransaction = fragmentManagerChild.beginTransaction();

        //Paso 3: Crear un nuevo fragmento y añadirlo


        // eliminarCara3D();

        // if(!existFragment("cara_3D") && !existFragment("cara_flexion") && !existFragment("cara_inclinacion") && !existFragment("cara_rotacion") &&
        //          !existFragment("botones_avatar")) {

        Log.d("fragmento_avatar", "No existo");
        //Aquí tendría que coger el terapeuta que haya iniciado sesión


        // if(!existFragment("btn_izquierda_asignacion"))
        // {
        btn_izquierda_asignacion = BotonPestanaFragment.newInstance(getResources().getString(R.string.terapeuta_sin_asignar),"");
        fragmentTransaction.replace(contenedor_btn_izquierda_asignacion.getId(),btn_izquierda_asignacion);

        //  }


        // if(!existFragment("btn_derecha_asignacion"))
        //  {
        btn_derecha_asignacion = BotonPestanaFragment.newInstance(getResources().getString(R.string.paciente_sin_asignar),"");
        fragmentTransaction.replace(contenedor_btn_derecha_asignacion.getId(),btn_derecha_asignacion);

        // }

        // if(!existFragment("pestana_izquierda"))
        //{


        btn_pestana_izquierda = BotonBelowPestanaFragment.newInstance(getResources().getString(R.string.pestana_en_tiempo_real), "");//datos en tiempo real
        fragmentTransaction.replace(contenedor_pestana_izquierda.getId(),btn_pestana_izquierda);

        // }

        //if(!existFragment("pestana_derecha"))
        // {

        btn_pestana_derecha = BotonBelowPestanaFragment.newInstance(getResources().getString(R.string.estadisticas), "");
        fragmentTransaction.replace(contenedor_pestana_derecha.getId(),btn_pestana_derecha);

        //}


        // if(!existFragment("barra_progreso_temporal"))
        // {
        Log.d("fragmento_avatar", "barra_medicion");
        BarraProgresoTemporalFragment progressBarFragment =  new BarraProgresoTemporalFragment();
        fragmentTransaction.replace(contenedor_barra_temporal.getId(), progressBarFragment);
        //  }



        fragmentTransaction.replace(contenedor_cara_3D.getId(), cara_fragment_3D);

        CaraFlexionFragment caraFlexionFragment = new CaraFlexionFragment();
        fragmentTransaction.replace(contenedor_cara_flexion.getId(), caraFlexionFragment );

        CaraInclinacionFragment caraInclinacionFragment = new CaraInclinacionFragment();
        fragmentTransaction.replace(contenedor_cara_inclinacion.getId(), caraInclinacionFragment );

        CaraRotacionFragment caraRotacionFragment = new CaraRotacionFragment();
        fragmentTransaction.replace(contenedor_cara_rotacion.getId(), caraRotacionFragment);

        BotonesAvatarFragment btn_avatar_fragment = BotonesAvatarFragment.newInstance("botones_avatar_con_medicion","");
        fragmentTransaction.replace(contenedor_botones_avatar.getId(), btn_avatar_fragment);

        fragmentTransaction.commit();

        // }
      /*  else
        {
            Log.d("fragmento_avatar", "Existo");

            //Aquí tendría que coger el terapeuta que haya iniciado sesión
           // if(existFragment("btn_izquierda_asignacion"))
           // {

            //OJO: AL CAMBIAR DE IDIOMA ES NECESARIO VOLVER A CREAR LA INSTANCIA PARA QUE PILLE EL IDIOMA CORRECTO.

               // btn_izquierda_asignacion = (BotonPestanaFragment) fragmentManagerChild.findFragmentByTag("btn_izquierda_asignacion");//OJO AL CAMBIAR EL IDIOMA ASÍ NO LO CAMBIARIA.
                btn_izquierda_asignacion = BotonPestanaFragment.newInstance(getResources().getString(R.string.terapeuta_sin_asignar),"");
                fragmentTransaction.replace(contenedor_btn_izquierda_asignacion.getId(),btn_izquierda_asignacion);
            //}

           // if(existFragment("btn_derecha_asignacion"))
           // {

               // btn_derecha_asignacion = (BotonPestanaFragment) fragmentManagerChild.findFragmentByTag("btn_derecha_asignacion");

                btn_derecha_asignacion = BotonPestanaFragment.newInstance(getResources().getString(R.string.paciente_sin_asignar),"");
                fragmentTransaction.replace(contenedor_btn_derecha_asignacion.getId(),btn_derecha_asignacion);

            //}
           // if(existFragment("pestana_izquierda"))
           // {

              //  BotonBelowPestanaFragment btn_pestana_izquierda = (BotonBelowPestanaFragment) fragmentManagerChild.findFragmentByTag("pestana_izquierda");
            btn_pestana_izquierda = BotonBelowPestanaFragment.newInstance(getResources().getString(R.string.pestana_en_tiempo_real), "");//datos en tiempo real

                fragmentTransaction.replace(contenedor_pestana_izquierda.getId(),btn_pestana_izquierda);

            //}

            //if(existFragment("pestana_derecha"))
            //{
               // BotonBelowPestanaFragment btn_pestana_derecha = (BotonBelowPestanaFragment) fragmentManagerChild.findFragmentByTag("pestana_derecha");
                 btn_pestana_derecha = BotonBelowPestanaFragment.newInstance(getResources().getString(R.string.estadisticas), "");
                fragmentTransaction.replace(contenedor_pestana_derecha.getId(),btn_pestana_derecha);
            //}

            if(existFragment("barra_progreso_temporal"))
            {

                Log.d("fragmento_avatar", "barra_medicion");
                BarraProgresoTemporalFragment progressBarFragment =  (BarraProgresoTemporalFragment) fragmentManagerChild.findFragmentByTag("barra_progreso_temporal");
                fragmentTransaction.replace(contenedor_barra_temporal.getId(), progressBarFragment);

            }

            Cara3DFragment cara_fragment_3D = (Cara3DFragment) fragmentManagerChild.findFragmentByTag("cara_3D");
            fragmentTransaction.replace(contenedor_cara_3D.getId(), cara_fragment_3D);

            CaraFlexionFragment caraFlexionFragment = (CaraFlexionFragment) fragmentManagerChild.findFragmentByTag("cara_flexion");
            fragmentTransaction.replace(contenedor_cara_flexion.getId(), caraFlexionFragment);

            CaraInclinacionFragment caraInclinacionFragment = (CaraInclinacionFragment) fragmentManagerChild.findFragmentByTag("cara_inclinacion");
            fragmentTransaction.replace(contenedor_cara_inclinacion.getId(), caraInclinacionFragment);

            CaraRotacionFragment caraRotacionFragment = (CaraRotacionFragment) fragmentManagerChild.findFragmentByTag("cara_rotacion");
            fragmentTransaction.replace(contenedor_cara_rotacion.getId(), caraRotacionFragment);

            BotonesAvatarFragment btn_avatar_fragment = (BotonesAvatarFragment) fragmentManagerChild.findFragmentByTag("botones_avatar");
            fragmentTransaction.replace(contenedor_botones_avatar.getId(), btn_avatar_fragment, "botones_avatar");

            fragmentTransaction.commit();

        }
*/
//        return view;

//    }





/*



















    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_avatar, container, false);

        //Para el titulo del menú
        //((HomeActivity) getActivity()).getSupportActionBar().setTitle("");
        TextView titulo= (TextView)((HomeActivity) getActivity()).findViewById(R.id.toolbar_title);
        titulo.setText(R.string.titulo_datos_en_tiempo_real);



        TextView txt_convulsiones = (TextView) view.findViewById(R.id.txtConvulsiones);
        TextView txt_va_m= (TextView) view.findViewById(R.id.txtVelocidadMedia);
        convulsiones = ( TextView) view.findViewById(R.id.convulsiones);
        va_media= (TextView) view.findViewById(R.id.velocidadMedia);


        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);
        txt_convulsiones.setTypeface(myFont);
        txt_va_m.setTypeface(myFont);
        convulsiones.setTypeface(myFont);
        va_media.setTypeface(myFont);
        titulo.setTypeface(myFont);


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

            BotonesAvatarFragment btn_avatar_fragment = BotonesAvatarFragment.newInstance("botones_avatar_con_medicion","");
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

            BotonesAvatarFragment btn_avatar_fragment = (BotonesAvatarFragment) fragmentManagerChild.findFragmentByTag("botones_avatar");
            //fragmentTransaction.remove(btn_avatar_fragment);
            fragmentTransaction.replace(contenedor_botones_avatar.getId(), btn_avatar_fragment, "botones_avatar");


            fragmentTransaction.commit();


        }





        return view;
    }










 */