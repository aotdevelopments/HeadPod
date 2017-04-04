package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.DialogImagenMuestra;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MedicionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MedicionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicionFragment extends Fragment implements  View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Para la configuración de la orientación. Con esto respetamos el diseño landScape
    private FrameLayout frameLayout;


    public MedicionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicionFragment newInstance(String param1, String param2) {
        MedicionFragment fragment = new MedicionFragment();
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
        setRetainInstance(true);
    }



    //http://stackoverflow.com/questions/17116602/using-onconfigurationchanged-in-a-fragment

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        ((HomeActivity)getActivity()).cambiarIdioma();
        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_medicion, null);


        setContenidoVista(view);


        frameLayout .addView(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        frameLayout = new FrameLayout(getActivity());
        // LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medicion, container, false);



        setContenidoVista(view);


        frameLayout .addView(view);
        //return view;
        return frameLayout;
    }


    public void setContenidoVista(View view )
    {


        TextView txtMedicionSinHp= (TextView) view.findViewById(R.id.txtMedicionSinHp);
        TextView txtMedicionSinHpContenido = (TextView) view.findViewById(R.id.txtMedicionSinHpContenido);

        TextView txtMedicionConHp = (TextView) view.findViewById(R.id.txtMedicionConHp);
        TextView txtMedicionConHpContenido = (TextView) view.findViewById(R.id.txtMedicionConHpContenido);

        //RelativeLayout contendor_texto_sin_hp = (RelativeLayout) view.findViewById(R.id.relativeLayout1);
        ImageView cara_sin_hp = (ImageView) view.findViewById(R.id.cara_sin_hp);
        ImageView cara_con_hp = (ImageView) view.findViewById(R.id.cara_con_hp);

        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);

        txtMedicionSinHp.setTypeface(myFont);
        txtMedicionSinHpContenido.setTypeface(myFont);
        txtMedicionConHp.setTypeface(myFont);
        txtMedicionConHpContenido.setTypeface(myFont);


        TextView titulo= (TextView)((HomeActivity) getActivity()).findViewById(R.id.toolbar_title);
        titulo.setText(R.string.inicar_medicion);

        ImageView btnMedicionSinHP = (ImageView) view.findViewById(R.id.btnMedicionSinHP);
        ImageView btnMedicionConHp = (ImageView) view.findViewById(R.id.btnMedicion);

        //Redimensionamos para los tamaños para que quede como el diseño de Alfonso
        //Imagen sin Hp
        ViewGroup.LayoutParams imagen_sin_hp =cara_sin_hp.getLayoutParams();
        imagen_sin_hp.width =  (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.25);//25% del ancho de la pantalla

        //Imagen con Hp
        ViewGroup.LayoutParams imagen_con_hp = cara_con_hp.getLayoutParams();
        imagen_con_hp.width = (int) ((  (HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.25);//25% del ancho de la pantalla


        btnMedicionSinHP.setOnClickListener(this);
        btnMedicionConHp.setOnClickListener(this);
        txtMedicionConHp.setOnClickListener(this);
        txtMedicionSinHp.setOnClickListener(this);
        txtMedicionConHpContenido.setOnClickListener(this);
        txtMedicionSinHpContenido.setOnClickListener(this);



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

            case R.id.btnMedicionSinHP :
                mListener.onFragmentInteraction("fragment_medicion_sin_headpod");
                break;
            case R.id.txtMedicionSinHp:
                mListener.onFragmentInteraction("fragment_medicion_sin_headpod");
                break;

            case R.id.btnMedicion :
                mListener.onFragmentInteraction("fragment_medicion_con_headpod");
                break;

            case R.id.txtMedicionConHp:
                mListener.onFragmentInteraction("fragment_medicion_con_headpod");
                break;
            case R.id.txtMedicionSinHpContenido:

                //*****************************AQUÍ HABRA QUE COLOCAR LA IMAGEN PARA EL DIALOG"***************************

                //El parametro será la ruta de la imagen
                //String ruta="android.resource://com.siestasystemheadpod.headpodv10/" + mRecursoImagen;
                String ruta="android.resource://"+getActivity().getPackageName()+"/" + R.drawable.monigote_verde_64;
                DialogImagenMuestra imagenMuestra = DialogImagenMuestra.newInstance(ruta);
                imagenMuestra.show(getActivity().getSupportFragmentManager(),"showDialogImagen");
                break;


            case R.id.txtMedicionConHpContenido:

                //*****************************AQUÍ HABRA QUE COLOCAR LA IMAGEN PARA EL DIALOG"***************************
                //El parametro será la ruta de la imagen
                ruta="android.resource://"+getActivity().getPackageName()+"/" + R.drawable.buscando_sensor_mio;
                DialogImagenMuestra imagenMuestra2 = DialogImagenMuestra.newInstance(ruta);
                imagenMuestra2.show(getActivity().getSupportFragmentManager(),"showDialogImagen");

                break;




        }
    }
}
