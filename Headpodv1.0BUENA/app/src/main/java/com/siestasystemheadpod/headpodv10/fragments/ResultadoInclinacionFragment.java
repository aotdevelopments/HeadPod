package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.informe.LoadViewResultMov;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultadoInclinacionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultadoInclinacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadoInclinacionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PORCENTAJE_NEG_90 = "param1";
    private static final String PORCENTAJE_NEG_70 = "param2";
    private static final String PORCENTAJE_NEG_55 = "param3";
    private static final String PORCENTAJE_NEG_40 = "param4";
    private static final String PORCENTAJE_NEG_25 = "param5";
    private static final String PORCENTAJE_NEUTRO_10 = "param6";
    private static final String PORCENTAJE_POS_25 = "param7";
    private static final String PORCENTAJE_POS_40 = "param8";
    private static final String PORCENTAJE_POS_55 = "param9";
    private static final String PORCENTAJE_POS_70 = "param10";
    private static final String PORCENTAJE_POS_90 = "param11";


    // TODO: Rename and change types of parameters
    private float mPorcentaje_neg_90;
    private float mPorcentaje_neg_70;
    private float mPorcentaje_neg_55;
    private float mPorcentaje_neg_40;
    private float mPorcentaje_neg_25;
    private float mPorcentaje_neutro_10;
    private float mPorcentaje_pos_25;
    private float mPorcentaje_pos_40;
    private float mPorcentaje_pos_55;
    private float mPorcentaje_pos_70;
    private float mPorcentaje_pos_90;


    //Contexto para la carga de imagenes del trapecio circular
    private String defPackge;

    private OnFragmentInteractionListener mListener;

    public ResultadoInclinacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param porcentaje_neg90 Parameter 1.
     * @param porcentaje_neg70 Parameter 2.
     * @param porcentaje_neg55 Parameter 3.
     * @param porcentaje_neg40 Parameter 4.
     * @param porcentaje_neg25 Parameter 5.
     * @param porcentaje_neutro10 Parameter 6.
     * @param porcentaje_pos25 Parameter 7.
     * @param porcentaje_pos40 Parameter 8.
     * @param porcentaje_pos55 Parameter 9.
     * @param porcentaje_pos70 Parameter 10.
     * @param porcentaje_pos90 Parameter 11.
     * @return A new instance of fragment CaraInclinacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultadoInclinacionFragment newInstance(float porcentaje_neg90,
                                                           float porcentaje_neg70, float porcentaje_neg55,
                                                           float porcentaje_neg40, float porcentaje_neg25,
                                                           float porcentaje_neutro10,
                                                           float porcentaje_pos25, float porcentaje_pos40,
                                                           float porcentaje_pos55, float porcentaje_pos70,
                                                           float porcentaje_pos90) {
        ResultadoInclinacionFragment fragment = new ResultadoInclinacionFragment();
        Bundle args = new Bundle();

        args.putFloat(PORCENTAJE_NEG_90,porcentaje_neg90);
        args.putFloat(PORCENTAJE_NEG_70,porcentaje_neg70);
        args.putFloat(PORCENTAJE_NEG_55,porcentaje_neg55);
        args.putFloat(PORCENTAJE_NEG_40,porcentaje_neg40);
        args.putFloat(PORCENTAJE_NEG_25,porcentaje_neg25);
        args.putFloat(PORCENTAJE_NEUTRO_10,porcentaje_neutro10);
        args.putFloat(PORCENTAJE_POS_25,porcentaje_pos25);
        args.putFloat(PORCENTAJE_POS_40,porcentaje_pos40);
        args.putFloat(PORCENTAJE_POS_55,porcentaje_pos55);
        args.putFloat(PORCENTAJE_POS_70,porcentaje_pos70);
        args.putFloat(PORCENTAJE_POS_90,porcentaje_pos90);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mPorcentaje_neg_90=getArguments().getFloat(PORCENTAJE_NEG_90);
            mPorcentaje_neg_70=getArguments().getFloat(PORCENTAJE_NEG_70);
            mPorcentaje_neg_55=getArguments().getFloat(PORCENTAJE_NEG_55);
            mPorcentaje_neg_40=getArguments().getFloat(PORCENTAJE_NEG_40);
            mPorcentaje_neg_25=getArguments().getFloat(PORCENTAJE_NEG_25);
            mPorcentaje_neutro_10 = getArguments().getFloat(PORCENTAJE_NEUTRO_10);
            mPorcentaje_pos_25 = getArguments().getFloat(PORCENTAJE_POS_25);
            mPorcentaje_pos_40 = getArguments().getFloat(PORCENTAJE_POS_40);
            mPorcentaje_pos_55= getArguments().getFloat(PORCENTAJE_POS_55);
            mPorcentaje_pos_70 = getArguments().getFloat(PORCENTAJE_POS_70);
            mPorcentaje_pos_90 = getArguments().getFloat(PORCENTAJE_POS_90);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resultado_inclinacion, container, false);

       // MySurfaceView mySurfaceView = new MySurfaceView(getContext());

        //((RelativeLayout) rootView.findViewById(R.id.gridmapLayout)).addView(maze);
        //((LinearLayout)view.findViewById(R.id.prueba)).addView(mySurfaceView);

       // return view;
        //setContentView(mySurfaceView);


        //Aquí pondremos el resultado de la imagen
        //*****************************************************************************
        ImageView imgInclinacion = (ImageView) view.findViewById(R.id.imgInclinacion);
        //*****************************************************************************


        //Obtenemos los recursos para crear la barra con sus porcentajes
        //******************************************************************
        LinearLayout barraInclinacionGood = (LinearLayout) view.findViewById(R.id.barraInclinacionGood);
        LinearLayout barraInclinacionFair = (LinearLayout) view.findViewById(R.id.barraInclinacionFair);
        LinearLayout barraInclinacionBad = (LinearLayout) view.findViewById(R.id.barraInclinacionBad);
        LinearLayout barraInclinacionVeryBad = (LinearLayout) view.findViewById(R.id.barraInclinacionVeryBad);


        TextView txtBarraInclinacionGood = (TextView) view.findViewById(R.id.txtBarraInclinacionGood);
        TextView txtBarraInclinacionFair = (TextView) view.findViewById(R.id.txtBarraInclinacionFair);
        TextView txtBarraInclinacionBad = (TextView) view.findViewById(R.id.txtBarraInclinacionBad);
        TextView txtBarraInclinacionVeryBad = (TextView) view.findViewById(R.id.txtBarraInclinacionVeryBad);


        LinearLayout corcheteInclinationGood = (LinearLayout) view.findViewById(R.id.corcheteInclinationGood);
        LinearLayout corcheteInclinationBad = (LinearLayout) view.findViewById(R.id.corcheteInclinationBad);


        LinearLayout contenedorTxtCorcheteInclinationGood = (LinearLayout) view.findViewById(R.id.contenedorTxtCorcheteGood);
        LinearLayout contenedorTxtCorcheteInclinationBad = (LinearLayout) view.findViewById(R.id.contenedorTxtCorcheteBad);

        TextView txt_porcentaje_good_inclination = (TextView) view.findViewById(R.id.txt_porcentaje_good_inclinacion);
        TextView txt_porcentaje_bad_inclination = (TextView) view.findViewById(R.id.txt_porcentaje_bad_inclinacion);
        //********************************************************************


        //Hilo encargado de cargar los recursos de las 11 imagenes para los procentajes de la semicircunferencia.
        LoadViewResultMov loadViewResultMov = new LoadViewResultMov(getActivity(),getContext(),"rangos_inclinacion",false);

        //PASAMOS LA IMAGEVIEW donde cargaremos el resultado final
        loadViewResultMov.setImgMovimientoResult(imgInclinacion);

        //PASAMOS LOS PORCENTAJES DE CADA RANGO DE LA SEMICIRCUNFERENCIA ("TRAPECIOS CIRCURLARES").



        Log.d("neg90",String.valueOf(mPorcentaje_neg_90));

        Log.d("neg70",String.valueOf(mPorcentaje_neg_70));

        Log.d("neg55",String.valueOf(mPorcentaje_neg_55));

        Log.d("neg40",String.valueOf(mPorcentaje_neg_40));

        Log.d("neg25",String.valueOf(mPorcentaje_neg_25));


        Log.d("neutro10",String.valueOf(mPorcentaje_neutro_10));


        Log.d("pos25",String.valueOf(mPorcentaje_pos_25));

        Log.d("pos40",String.valueOf(mPorcentaje_pos_40));


        Log.d("pos55",String.valueOf(mPorcentaje_pos_55));


        Log.d("pos70",String.valueOf(mPorcentaje_pos_70));


        Log.d("pos90",String.valueOf(mPorcentaje_pos_90));





        loadViewResultMov.setPorcent_negativo90(mPorcentaje_neg_90);
        loadViewResultMov.setPorcent_negativo70(mPorcentaje_neg_70);
        loadViewResultMov.setPorcent_negativo55(mPorcentaje_neg_55);
        loadViewResultMov.setPorcent_negativo40(mPorcentaje_neg_40);
        loadViewResultMov.setPorcent_negativo25(mPorcentaje_neg_25);
        loadViewResultMov.setPorcent_neutro10(mPorcentaje_neutro_10);
        loadViewResultMov.setPorcent_positivo25(mPorcentaje_pos_25);
        loadViewResultMov.setPorcent_positivo40(mPorcentaje_pos_40);
        loadViewResultMov.setPorcent_positivo55(mPorcentaje_pos_55);
        loadViewResultMov.setPorcent_positivo70(mPorcentaje_pos_70);
        loadViewResultMov.setPorcent_positivo90(mPorcentaje_pos_90);

        //Ejecutamos el hilo encargado de cargar la imagen resultado del movimiento (cara, semicircunferencias y trapecios).
        loadViewResultMov.execute();



        float porcentajeInclinatioGood=mPorcentaje_neutro_10;//0.15f
        float porcentajeInclinationFair=mPorcentaje_neg_25+mPorcentaje_pos_25;//0.35f
        float porcentajeInclinationBad=mPorcentaje_neg_40+mPorcentaje_pos_40;//0.25f
        float porcentajeInclinationVeryBad=mPorcentaje_neg_55+mPorcentaje_neg_70+mPorcentaje_neg_90+mPorcentaje_pos_55+mPorcentaje_pos_70+mPorcentaje_pos_90;//0.25f

        //Obtenemos el resultado de la barra.
        setViewBarraPosition(barraInclinacionGood,barraInclinacionFair,barraInclinacionBad,barraInclinacionVeryBad,
                txtBarraInclinacionGood,txtBarraInclinacionFair,txtBarraInclinacionBad,txtBarraInclinacionVeryBad,
                corcheteInclinationGood,corcheteInclinationBad,contenedorTxtCorcheteInclinationGood,contenedorTxtCorcheteInclinationBad,
                txt_porcentaje_good_inclination,txt_porcentaje_bad_inclination,porcentajeInclinatioGood,porcentajeInclinationFair,porcentajeInclinationBad,porcentajeInclinationVeryBad);

        return view;

    }



    public void  setViewBarraPosition(LinearLayout barraPorcentajeRangoGoood,
                                      LinearLayout barraPorcentajeRangoFair,
                                      LinearLayout barraPorcentajeRangoBad,
                                      LinearLayout barraPorcentajeRangoVeryBad,

                                      TextView  txtPorcentajeRangoGood,
                                      TextView  txtPorcentajeRangoFair,
                                      TextView  txtPorcentajeRangoBad,
                                      TextView  txtPorcentajeRangoVeryBad,

                                      LinearLayout corchete_inferior_porcentaje_good,
                                      LinearLayout corchete_inferior_porcentaje_bad,

                                      LinearLayout contenedor_result_corchete_good,
                                      LinearLayout contenedor_result_corchete_bad,

                                      TextView  txt_corchetes_porcentaje_good,
                                      TextView txt_corchetes_porcentaje_bad,

                                      float porcentajeGood,float porcentajeFair,float porcentajeBad,float porcentajeVeryBad)

    {


        Log.d("porcentaje_good",String.valueOf(porcentajeGood));

        Log.d("porcentaje_fair",String.valueOf(porcentajeFair));

        Log.d("porcentaje_bad",String.valueOf(porcentajeBad));

        Log.d("porcentaje_verybad",String.valueOf(porcentajeVeryBad));

        DecimalFormat decimales = new DecimalFormat("0");
        setPorcentajeRango(barraPorcentajeRangoGoood,porcentajeGood);
        setPorcentajeRango(barraPorcentajeRangoFair,porcentajeFair);
        setPorcentajeRango(barraPorcentajeRangoBad,porcentajeBad);
        setPorcentajeRango(barraPorcentajeRangoVeryBad,porcentajeVeryBad);


        String porcentaje_rango_good=String.valueOf(decimales.format(porcentajeGood*100))+"%";
        txtPorcentajeRangoGood.setText(porcentaje_rango_good);

        String porcentaje_rango_fair=String.valueOf(decimales.format(porcentajeFair*100))+"%";
        txtPorcentajeRangoFair.setText(porcentaje_rango_fair);

        String porcentaje_rango_bad=String.valueOf(decimales.format(porcentajeBad*100))+"%";
        txtPorcentajeRangoBad.setText(porcentaje_rango_bad);

        String porcentaje_rango_very_bad=String.valueOf(decimales.format(porcentajeVeryBad*100))+"%";
        txtPorcentajeRangoVeryBad.setText(porcentaje_rango_very_bad);


        setPorcentajeRango(corchete_inferior_porcentaje_good,porcentajeGood+porcentajeFair);
        setPorcentajeRango(corchete_inferior_porcentaje_bad,porcentajeBad+porcentajeVeryBad);
        setPorcentajeRango(contenedor_result_corchete_good,porcentajeGood+porcentajeFair);
        setPorcentajeRango(contenedor_result_corchete_bad,porcentajeBad+porcentajeVeryBad);

        String porcentaje_corchete_rango_good=String.valueOf(decimales.format(porcentajeGood*100+porcentajeFair*100))+"%";
        txt_corchetes_porcentaje_good.setText(porcentaje_corchete_rango_good);

        String porcentaje_corchete_rango_bad=String.valueOf(decimales.format(porcentajeBad*100+porcentajeVeryBad*100))+"%";
        txt_corchetes_porcentaje_bad.setText(porcentaje_corchete_rango_bad);

    }


    public void setPorcentajeRango(LinearLayout barraPorcentajeRango,float porcentaje)
    {

        // Get params:
        LinearLayout.LayoutParams loparams = (LinearLayout.LayoutParams) barraPorcentajeRango.getLayoutParams();
        // Set only target params:
        //loparams.height = 0;
        loparams.weight=porcentaje;
        barraPorcentajeRango.setLayoutParams(loparams);
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }
}
