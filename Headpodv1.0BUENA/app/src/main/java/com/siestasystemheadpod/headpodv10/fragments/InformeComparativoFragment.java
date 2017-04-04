package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.Util;
import com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor.TaskSensorBle;

import java.text.DecimalFormat;
import java.util.ArrayList;

//import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InformeComparativoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InformeComparativoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformeComparativoFragment extends Fragment implements OnChartValueSelectedListener {
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


    public RelativeLayout contenedor_barra_temporal;
    public RelativeLayout contenedor_cara_3D;
    public RelativeLayout contenedor_cara_flexion;
    public RelativeLayout contenedor_cara_rotacion;
    public RelativeLayout contenedor_cara_inclinacion;
    public RelativeLayout contenedor_botones_avatar;


    public Cara3DFragment cara_fragment_3D;



    private LineChart mChart;

    protected Typeface mTfLight;


    //Para la configuración de la orientación. Con esto respetamos el diseño landScape
    private FrameLayout frameLayout;



    //Fecha, hora y duración del informe
    //*********************************************************************************************

    private int diaMedicion;
    private int mesMedicion;
    private int anoMedicion;

    private int horaMedicion;
    private int minMedicion;

    private int horasDuracion;
    private int minutosDuracion;

   /* private TextView txtXXDia;
    private TextView txtXXMes;
    private TextView txtXXAno;

    private TextView txtXXHora;

    private TextView txtDuracion;
    private TextView txtXXDuracion;*/

    //Porcentaje de tiempo en cada rango
    //*********************************************************************************************
    private TextView  txtTituloPorcentajesRangos;
    private LinearLayout barraPorcentajeRangoGoood ;
    private LinearLayout barraPorcentajeRangoFair ;
    private LinearLayout barraPorcentajeRangoBad ;
    private LinearLayout barraPorcentajeRangoVeryBad ;

    private TextView  txtPorcentajeRangoGood ;
    private TextView  txtPorcentajeRangoFair ;
    private TextView  txtPorcentajeRangoBad ;
    private TextView  txtPorcentajeRangoVeryBad ;

    private LinearLayout corchete_inferior_porcentaje_good;
    private LinearLayout corchete_inferior_porcentaje_bad;

    private LinearLayout contenedor_result_corchete_good;
    private LinearLayout contenedor_result_corchete_bad;
    private TextView txt_corchetes_porcentaje_good;
    private TextView txt_corchetes_porcentaje_bad;
    //*********************************************************************************************

    //Resultados inclinacion
    //***********************************************************************************************
   /* private ImageView imgBarraNeg90Incl ;
    private ImageView imgBarraNeg70Incl ;
    private ImageView imgBarraNeg55Incl ;
    private ImageView imgBarraNeg40Incl ;
    private ImageView imgBarraNeg25Incl ;
    private ImageView imgBarraNeutro10Incl ;
    private ImageView imgBarraPos25Incl;
    private ImageView imgBarraPos40Incl ;
    private ImageView imgBarraPos55Incl;
    private ImageView imgBarraPos70Incl ;
    private ImageView imgBarraPos90Incl ;


    private LinearLayout barraInclinacionGood ;
    private LinearLayout barraInclinacionFair ;
    private LinearLayout barraInclinacionBad ;
    private LinearLayout barraInclinacionVeryBad ;


    private TextView txtBarraInclinacionGood ;
    private TextView txtBarraInclinacionFair ;
    private TextView txtBarraInclinacionBad ;
    private TextView txtBarraInclinacionVeryBad ;


    private LinearLayout corcheteInclinationGood ;
    private LinearLayout corcheteInclinationBad ;

    private LinearLayout contenedorTxtCorcheteInclinationGood;
    private LinearLayout contenedorTxtCorcheteInclinationBad;

    private TextView txt_porcentaje_good_inclination ;
    private TextView txt_porcentaje_bad_inclination ;*/


    float porcentajeInclinatioGood;
    float porcentajeInclinationFair;
    float porcentajeInclinationBad;
    float porcentajeInclinationVeryBad;


    //************************************************************************************************



    //Resultados Flexion
    //***********************************************************************************************
   /* private ImageView imgBarraNeg90Flex ;
    private ImageView imgBarraNeg70Flex ;
    private ImageView imgBarraNeg55Flex ;
    private ImageView imgBarraNeg40Flex ;
    private ImageView imgBarraNeg25Flex ;
    private ImageView imgBarraNeutro10Flex ;
    private ImageView imgBarraPos25Flex;
    private ImageView imgBarraPos40Flex ;
    private ImageView imgBarraPos55Flex;
    private ImageView imgBarraPos70Flex ;
    private ImageView imgBarraPos90Flex ;


    private LinearLayout barraFlexionGood ;
    private LinearLayout barraFlexionFair ;
    private LinearLayout barraFlexionBad ;
    private LinearLayout barraFlexionVeryBad ;


    private TextView txtBarraFlexionGood ;
    private TextView txtBarraFlexionFair ;
    private TextView txtBarraFlexionBad ;
    private TextView txtBarraFlexionVeryBad ;


    private LinearLayout corcheteFlexionGood ;
    private LinearLayout corcheteFlexionBad ;

    private LinearLayout contenedorTxtCorcheteFlexionGood;
    private LinearLayout contenedorTxtCorcheteFlexionBad;

    private TextView txt_porcentaje_good_flexion ;
    private TextView txt_porcentaje_bad_flexion ;*/


    float porcentajeFlexionGood;
    float porcentajeFlexionFair;
    float porcentajeFlexionBad;
    float porcentajeFlexionVeryBad;
    //************************************************************************************************



    //Resultados Rotacion
    //***********************************************************************************************
    float porcentajeRotationGood;
    float porcentajeRotationFair;
    float porcentajeRotationBad;
    float porcentajeRotationVeryBad;
    //************************************************************************************************


    public InformeComparativoFragment() {
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
    public static InformeComparativoFragment newInstance(String param1, String param2) {
        InformeComparativoFragment fragment = new InformeComparativoFragment();
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



        /*
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/fast99.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/


        //Estraer de la BD la fecha, hora y duracion

        diaMedicion=1;
        mesMedicion=3;
        anoMedicion=2017;

        horaMedicion=10;
        minMedicion=25;

        horasDuracion=1;
        minutosDuracion=8;


    }


    //http://stackoverflow.com/questions/17116602/using-onconfigurationchanged-in-a-fragment
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        ((HomeActivity)getActivity()).cambiarIdioma();

        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_informe_comparativo, null);
       // setContenidoVista(view);

        frameLayout.addView(view);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

       // getActivity().	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);




        frameLayout = new FrameLayout(getActivity());
        // LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_informe_comparativo, container, false);

        //setContenidoVista(view);

        frameLayout .addView(view);
       // return view;
        return frameLayout;
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
        titulo.setText(getString(R.string.inf_report));

        //Fecha y hora del informe
        //******************************************************************
        TextView txtXXDia = (TextView) view.findViewById(R.id.txtXXDia);
        TextView txtXXMes = (TextView) view.findViewById(R.id.txtXXMes);
        TextView txtXXAno = (TextView) view.findViewById(R.id.txtXXAno);

        TextView txtHora = (TextView) view.findViewById(R.id.txtHora);
        TextView txtXXHora = (TextView) view.findViewById(R.id.txtXXHora);

        TextView txtDuracion = (TextView) view.findViewById(R.id.txtDuracion);
        TextView txtXXDuracion = (TextView) view.findViewById(R.id.txtXXDuracion);


        setFecha(txtXXDia,txtXXMes,txtXXAno,txtXXHora,txtXXDuracion);
        //**************************************************************************

      /*
        *
        *
        */

        //*********************************************************************************************
        //*********************************************************************************************
        //Control de movimientos
        //*********************************************************************************************
        //*********************************************************************************************
        //Titulo
        TextView txtTitluloControlMov = (TextView) view.findViewById(R.id.txtControlMov);

        //pobre
        LinearLayout contenedorMovSelectPoor = (LinearLayout) view.findViewById(R.id.contenedor_select_poor);
        LinearLayout contenedorMovColorPoor = (LinearLayout) view.findViewById(R.id.contenedor_mov_poor);
        TextView txtMovMalo = (TextView) view.findViewById(R.id.txt_mov_malo);

        //normal
        LinearLayout contenedorMovSelectNormal = (LinearLayout) view.findViewById(R.id.contenedor_select_normal);
        LinearLayout contenedorMovColorNormal = (LinearLayout) view.findViewById(R.id.contenedor_mov_normal);
        TextView txtMovNormal = (TextView) view.findViewById(R.id.txt_mov_normal);

        //bueno
        LinearLayout contenedorMovSelectGood = (LinearLayout) view.findViewById(R.id.contenedor_select_good);
        LinearLayout contenedorMovColorGood = (LinearLayout) view.findViewById(R.id.contenedor_mov_good);
        TextView txtMovGood = (TextView) view.findViewById(R.id.txt_mov_good);


        //Manipulación del control de movimientos
        //******************************************************************************************
        //0=pobre, 1=nomal, 2 = bueno
        int resultado = 1;
        seleccionarResultadoControlMovimientos(contenedorMovSelectPoor,contenedorMovColorPoor,contenedorMovSelectNormal
        ,contenedorMovColorNormal,contenedorMovSelectGood,contenedorMovColorGood,resultado);
        //*********************************************************************************************
        //*********************************************************************************************
      /*
        *
        *
        */

        //*********************************************************************************************
        //*********************************************************************************************
        //Porcentaje de tiempo en cada rango
        //*********************************************************************************************
        //*********************************************************************************************

        txtTituloPorcentajesRangos = (TextView) view.findViewById(R.id. txtTituloPorcentajesRangos);
        barraPorcentajeRangoGoood = (LinearLayout) view.findViewById(R.id.barraPorcentajeRangoGoood);
        barraPorcentajeRangoFair = (LinearLayout) view.findViewById(R.id.barraPorcentajeRangoFair);
        barraPorcentajeRangoBad = (LinearLayout) view.findViewById(R.id.barraPorcentajeRangoBad);
        barraPorcentajeRangoVeryBad = (LinearLayout) view.findViewById(R.id.barraPorcentajeRangoVerBad);

        //barra
        txtPorcentajeRangoGood = (TextView) view.findViewById(R.id.txtPorcentajeRangoGood);
        txtPorcentajeRangoFair = (TextView) view.findViewById(R.id.txtPorcentajeRangoFair);
        txtPorcentajeRangoBad = (TextView) view.findViewById(R.id.txtPorcentajeRangoBad);
        txtPorcentajeRangoVeryBad = (TextView) view.findViewById(R.id.txtPorcentajeRangoVeryBad);

        corchete_inferior_porcentaje_good = (LinearLayout) view.findViewById(R.id.corchete_inferior_porcentaje_good);
        corchete_inferior_porcentaje_bad = (LinearLayout) view.findViewById(R.id.corchete_inferior_porcentaje_bad);

        contenedor_result_corchete_good = (LinearLayout) view.findViewById(R.id.contenedor_result_corchete_good);
        contenedor_result_corchete_bad = (LinearLayout) view.findViewById(R.id.contenedor_result_corchete_bad);
        txt_corchetes_porcentaje_good = (TextView) view.findViewById(R.id.txt_corchetes_porcentaje_good);
        txt_corchetes_porcentaje_bad = (TextView) view.findViewById(R.id.txt_corchetes_porcentaje_bad);

        //leyenda
        TextView txtTituloLeyendaRangos = (TextView) view.findViewById(R.id.txtTituloLeyendaRangos);

        TextView leyenda_good = (TextView) view.findViewById(R.id.leyenda_good);
        TextView txt_leyenda_good = (TextView) view.findViewById(R.id.txt_leyenda_good);

        TextView leyenda_fair = (TextView) view.findViewById(R.id.leyenda_fair);
        TextView txt_leyenda_fair = (TextView) view.findViewById(R.id.txt_leyenda_fair);

        TextView leyenda_bad = (TextView) view.findViewById(R.id.leyenda_bad);
        TextView txt_leyenda_bad = (TextView) view.findViewById(R.id.txt_leyenda_bad);

        TextView leyenda_very_bad = (TextView) view.findViewById(R.id.leyenda_ver_bad);
        TextView txt_leyenda_very_bad = (TextView) view.findViewById(R.id.txt_leyenda_very_bad);


        float porcentajeGood,porcentajeFair, porcentajeBad, porcentajeVeryBad;
        //Establecer porcentajes de la barra de rango de porcentajes
        //***************************************************************************************
        porcentajeGood=0.22f;
        porcentajeFair=0.22f;
        porcentajeBad=0.35f;
        porcentajeVeryBad=0.21f;
        setViewPorcentajesRangos(porcentajeGood,porcentajeFair,porcentajeBad,porcentajeVeryBad);


        //Set Leyenda Rangos
        //***************************************************************************************
        setLeyendaRango(txt_leyenda_good,porcentajeGood);
        setLeyendaRango(txt_leyenda_fair,porcentajeFair);
        setLeyendaRango(txt_leyenda_bad,porcentajeBad);
        setLeyendaRango(txt_leyenda_very_bad,porcentajeVeryBad);

        //*********************************************************************************************
        //*********************************************************************************************

        /*
        *
        *
        */

        //*********************************************************************************************
        //*********************************************************************************************
        //Resultados inclinación
        //*********************************************************************************************
        //*********************************************************************************************
        ImageView imgBarraNeg90Incl = (ImageView) view.findViewById(R.id.imgNeg90Inclination);
        ImageView imgBarraNeg70Incl = (ImageView) view.findViewById(R.id.imgNeg70Inclination);
        ImageView imgBarraNeg55Incl = (ImageView) view.findViewById(R.id.imgNeg55Inclination);
        ImageView imgBarraNeg40Incl = (ImageView) view.findViewById(R.id.imgNeg40Inclination);
        ImageView imgBarraNeg25Incl = (ImageView) view.findViewById(R.id.imgNeg25Inclination);
        ImageView imgBarraNeutro10Incl = (ImageView) view.findViewById(R.id.imgNeutro10Inclination);
        ImageView imgBarraPos25Incl = (ImageView) view.findViewById(R.id.imgPos25Inclination);
        ImageView imgBarraPos40Incl = (ImageView) view.findViewById(R.id.imgPos40Inclination);
        ImageView imgBarraPos55Incl = (ImageView) view.findViewById(R.id.imgPos55Inclination);
        ImageView imgBarraPos70Incl = (ImageView) view.findViewById(R.id.imgPos70Inclination);
        ImageView imgBarraPos90Incl = (ImageView) view.findViewById(R.id.imgPos90Inclination);



        setTrapecioCircular(imgBarraNeg90Incl,3.0f,"g1");
        setTrapecioCircular(imgBarraNeg70Incl,5f,"g2");
        setTrapecioCircular(imgBarraNeg55Incl,0f,"g3");
        setTrapecioCircular(imgBarraNeg40Incl,5f,"g4");
        setTrapecioCircular(imgBarraNeg25Incl,10f,"g5");
        setTrapecioCircular(imgBarraNeutro10Incl,15.0f,"g6");
        setTrapecioCircular(imgBarraPos25Incl,25.0f,"g7");
        setTrapecioCircular(imgBarraPos40Incl,20.0f,"g8");
        setTrapecioCircular(imgBarraPos55Incl,5.0f,"g9");
        setTrapecioCircular(imgBarraPos70Incl,10.0f,"g10");
        setTrapecioCircular(imgBarraPos90Incl,2.0f,"g11");



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



        porcentajeInclinatioGood=0.15f;
        porcentajeInclinationFair=0.35f;
        porcentajeInclinationBad=0.25f;
        porcentajeInclinationVeryBad=0.25f;

        setViewBarraPosition(barraInclinacionGood,barraInclinacionFair,barraInclinacionBad,barraInclinacionVeryBad,
                txtBarraInclinacionGood,txtBarraInclinacionFair,txtBarraInclinacionBad,txtBarraInclinacionVeryBad,
                corcheteInclinationGood,corcheteInclinationBad,contenedorTxtCorcheteInclinationGood,contenedorTxtCorcheteInclinationBad,
                txt_porcentaje_good_inclination,txt_porcentaje_bad_inclination,porcentajeInclinatioGood,porcentajeInclinationFair,porcentajeInclinationBad,porcentajeInclinationVeryBad);



        //*********************************************************************************************
        //*********************************************************************************************
      /*
        *
        *
        */
        //*********************************************************************************************
        //*********************************************************************************************
        //Resultados flexion
        //*********************************************************************************************
        //*********************************************************************************************

        ImageView imgBarraNeg90Flex = (ImageView) view.findViewById(R.id.imgNeg90Flexion);
        ImageView imgBarraNeg70Flex = (ImageView) view.findViewById(R.id.imgNeg70Flexion);
        ImageView imgBarraNeg55Flex = (ImageView) view.findViewById(R.id.imgNeg55Flexion);
        ImageView imgBarraNeg40Flex = (ImageView) view.findViewById(R.id.imgNeg40Flexion);
        ImageView imgBarraNeg25Flex = (ImageView) view.findViewById(R.id.imgNeg25Flexion);
        ImageView imgBarraNeutro10Flex = (ImageView) view.findViewById(R.id.imgNeutro10Flexion);
        ImageView imgBarraPos25Flex = (ImageView) view.findViewById(R.id.imgPos25Flexion);
        ImageView imgBarraPos40Flex = (ImageView) view.findViewById(R.id.imgPos40Flexion);
        ImageView imgBarraPos55Flex = (ImageView) view.findViewById(R.id.imgPos55Flexion);
        ImageView imgBarraPos70Flex = (ImageView) view.findViewById(R.id.imgPos70Flexion);
        ImageView imgBarraPos90Flex = (ImageView) view.findViewById(R.id.imgPos90Flexion);



        setTrapecioCircular(imgBarraNeg90Flex,3.0f,"g1");
        setTrapecioCircular(imgBarraNeg70Flex,5f,"g2");
        setTrapecioCircular(imgBarraNeg55Flex,0f,"g3");
        setTrapecioCircular(imgBarraNeg40Flex,5f,"g4");
        setTrapecioCircular(imgBarraNeg25Flex,10f,"g5");
        setTrapecioCircular(imgBarraNeutro10Flex,15.0f,"g6");
        setTrapecioCircular(imgBarraPos25Flex,25.0f,"g7");
        setTrapecioCircular(imgBarraPos40Flex,20.0f,"g8");
        setTrapecioCircular(imgBarraPos55Flex,5.0f,"g9");
        setTrapecioCircular(imgBarraPos70Flex,10.0f,"g10");
        setTrapecioCircular(imgBarraPos90Flex,2.0f,"g11");



        LinearLayout barraFlexionGood = (LinearLayout) view.findViewById(R.id.barraFlexionGood);
        LinearLayout barraFlexionFair = (LinearLayout) view.findViewById(R.id.barraFlexionFair);
        LinearLayout barraFlexionBad = (LinearLayout) view.findViewById(R.id.barraFlexionBad);
        LinearLayout barraFlexionVeryBad = (LinearLayout) view.findViewById(R.id.barraFlexionVeryBad);


        TextView txtBarraFlexionGood = (TextView) view.findViewById(R.id.txtBarraFlexionGood);
        TextView txtBarraFlexionFair = (TextView) view.findViewById(R.id.txtBarraFlexionFair);
        TextView txtBarraFlexionBad = (TextView) view.findViewById(R.id.txtBarraFlexionBad);
        TextView txtBarraFlexionVeryBad = (TextView) view.findViewById(R.id.txtBarraFlexionVeryBad);


        LinearLayout corcheteFlexionGood = (LinearLayout) view.findViewById(R.id.corcheteFlexionGood);
        LinearLayout corcheteFlexionBad = (LinearLayout) view.findViewById(R.id.corcheteFlexionBad);


        LinearLayout contenedorTxtCorcheteFlexionGood = (LinearLayout) view.findViewById(R.id.contenedorTxtCorcheteFlexionGood);
        LinearLayout contenedorTxtCorcheteFlexionBad = (LinearLayout) view.findViewById(R.id.contenedorTxtCorcheteFlexionBad);

        TextView txt_porcentaje_good_flexion = (TextView) view.findViewById(R.id.txt_porcentaje_good_flexion);
        TextView txt_porcentaje_bad_flexion = (TextView) view.findViewById(R.id.txt_porcentaje_bad_flexion);



        porcentajeFlexionGood=0.15f;
        porcentajeFlexionFair=0.35f;
        porcentajeFlexionBad=0.25f;
        porcentajeFlexionVeryBad=0.25f;

        setViewBarraPosition(barraFlexionGood,barraFlexionFair,barraFlexionBad,barraFlexionVeryBad,
                txtBarraFlexionGood,txtBarraFlexionFair,txtBarraFlexionBad,txtBarraFlexionVeryBad,
                corcheteFlexionGood,corcheteFlexionBad,contenedorTxtCorcheteFlexionGood,contenedorTxtCorcheteFlexionBad,
                txt_porcentaje_good_flexion,txt_porcentaje_bad_flexion,porcentajeFlexionGood,porcentajeFlexionFair,porcentajeFlexionBad,porcentajeFlexionVeryBad);



        //*********************************************************************************************
        //*********************************************************************************************
        /*
        *
        *
        */




        //*********************************************************************************************
        //*********************************************************************************************
        //Resultados rotación
        //*********************************************************************************************
        //*********************************************************************************************

        ImageView imgBarraNeg90Rotation = (ImageView) view.findViewById(R.id.imgNeg90Rotation);
        ImageView imgBarraNeg70Rotation = (ImageView) view.findViewById(R.id.imgNeg70Rotation);
        ImageView imgBarraNeg55Rotation = (ImageView) view.findViewById(R.id.imgNeg55Rotation);
        ImageView imgBarraNeg40Rotation = (ImageView) view.findViewById(R.id.imgNeg40Rotation);
        ImageView imgBarraNeg25Rotation = (ImageView) view.findViewById(R.id.imgNeg25Rotation);
        ImageView imgBarraNeutro10Rotation = (ImageView) view.findViewById(R.id.imgNeutro10Rotation);
        ImageView imgBarraPos25Rotation = (ImageView) view.findViewById(R.id.imgPos25Rotation);
        ImageView imgBarraPos40Rotation = (ImageView) view.findViewById(R.id.imgPos40Rotation);
        ImageView imgBarraPos55Rotation = (ImageView) view.findViewById(R.id.imgPos55Rotation);
        ImageView imgBarraPos70Rotation = (ImageView) view.findViewById(R.id.imgPos70Rotation);
        ImageView imgBarraPos90Rotation = (ImageView) view.findViewById(R.id.imgPos90Rotation);



        setTrapecioCircularRotation(imgBarraNeg90Rotation,90.0f,"g1");
        setTrapecioCircularRotation(imgBarraNeg70Rotation,5f,"g2");
        setTrapecioCircularRotation(imgBarraNeg55Rotation,0f,"g3");
        setTrapecioCircularRotation(imgBarraNeg40Rotation,5f,"g4");
        setTrapecioCircularRotation(imgBarraNeg25Rotation,10f,"g5");
        setTrapecioCircularRotation(imgBarraNeutro10Rotation,15.0f,"g6");
        setTrapecioCircularRotation(imgBarraPos25Rotation,25.0f,"g7");
        setTrapecioCircularRotation(imgBarraPos40Rotation,20.0f,"g8");
        setTrapecioCircularRotation(imgBarraPos55Rotation,5.0f,"g9");
        setTrapecioCircularRotation(imgBarraPos70Rotation,10.0f,"g10");
        setTrapecioCircularRotation(imgBarraPos90Rotation,2.0f,"g11");






        //*********************************************************************************************
        //*********************************************************************************************
        /*
        *
        *
        */





        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);

        titulo.setTypeface(myFont);

        txtXXDia.setTypeface(myFont);
        txtXXMes.setTypeface(myFont);
        txtXXAno.setTypeface(myFont);
        txtHora.setTypeface(myFont);
        txtXXHora.setTypeface(myFont);
        txtDuracion.setTypeface(myFont);
        txtXXDuracion.setTypeface(myFont);

        txtTitluloControlMov.setTypeface(myFont);
        txtMovMalo.setTypeface(myFont);
        txtMovNormal.setTypeface(myFont);
        txtMovGood.setTypeface(myFont);

        txtTituloPorcentajesRangos.setTypeface(myFont);
        txtPorcentajeRangoGood.setTypeface(myFont);
        txtPorcentajeRangoFair.setTypeface(myFont);
        txtPorcentajeRangoBad.setTypeface(myFont);
        txtPorcentajeRangoVeryBad.setTypeface(myFont);
        txt_corchetes_porcentaje_good.setTypeface(myFont);
        txt_corchetes_porcentaje_bad.setTypeface(myFont);


        txtTituloLeyendaRangos.setTypeface(myFont);
        leyenda_good.setTypeface(myFont);
        leyenda_fair.setTypeface(myFont);
        leyenda_bad.setTypeface(myFont);
        leyenda_very_bad.setTypeface(myFont);
        txt_leyenda_good.setTypeface(myFont);
        txt_leyenda_fair.setTypeface(myFont);
        txt_leyenda_bad.setTypeface(myFont);
        txt_leyenda_very_bad.setTypeface(myFont);



        mTfLight=myFont;

        mChart = (LineChart) view.findViewById(R.id.chart1);


        mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);


        // mChart.setVisibleYRangeMinimum(15,AxisDependency.LEFT);
        // mChart.setVisibleYRangeMaximum(60, AxisDependency.LEFT);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        //mChart.setBackgroundColor(Color.LTGRAY);
        mChart.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.data_background));

        // add data
        setData(10, 30);

        mChart.animateX(2500);



        //Deshabilitamos la leyenda
        mChart.getLegend().setEnabled(false);


        /*
        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(mTfLight);
        l.setTextSize(11f);
        //l.setTextColor(Color.WHITE);
        l.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        //l.setYOffset(11f);
        */

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        xAxis.setTextSize(15f);



        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//Para situar los valores del eje abajo.

        xAxis.setAxisMinimum(0f);
        //xAxis.setAxisMaximum(100f);


        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        // leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        leftAxis.setAxisMaximum(60f);
        leftAxis.setTextSize(15f);

        leftAxis.setAxisMinimum(-60f);
        //leftAxis.setMinWidth(15);
        //leftAxis.setSpaceMin(15);


        //Quiere decir que acepta 20 valores en su eje Y (creo). Por eso se ajusta el intervarlo a mostrar.
        leftAxis.setLabelCount(20);//55




        // mChart.setVisibleYRangeMaximum(60, AxisDependency.LEFT);

        leftAxis.setAxisLineWidth(4f);
        leftAxis.setAxisLineColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        mChart.getAxisRight().setEnabled(false);





        /*
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

            BotonesAvatarFragment btn_avatar_fragment = BotonesAvatarFragment.newInstance("botones_avatar_sin_medicion","");
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


*/
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





    public void setFecha(TextView XXDia,TextView XXMes, TextView XXAno,TextView XXHora,TextView XXDuracion)
    {

        DecimalFormat decimalFormat = new DecimalFormat("00");
        XXDia.setText(String.valueOf(decimalFormat.format(diaMedicion)));
        String auxMes = "/"+String.valueOf(decimalFormat.format(mesMedicion))+"/";
        XXMes.setText(auxMes);
        XXAno.setText(String.valueOf(decimalFormat.format(anoMedicion)));


        String auxHora=String.valueOf(decimalFormat.format(horaMedicion))+":"+String.valueOf(decimalFormat.format(minMedicion));
        XXHora.setText(auxHora);

        String auxDuracion=String.valueOf(decimalFormat.format(horasDuracion))+":"+String.valueOf(decimalFormat.format(minutosDuracion));
        XXDuracion.setText(auxDuracion);


    }








    public void setLeyendaRango(TextView txt_rango, float porcentaje)
    {

        DecimalFormat decimalFormat = new DecimalFormat("0");
        String aux_txt_leyenda_good =String.valueOf(decimalFormat.format(porcentaje*100))+"%";
        txt_rango.setText(aux_txt_leyenda_good);


    }


    public void  setViewPorcentajesRangos(float porcentajeGood,float porcentajeFair,float porcentajeBad,float porcentajeVeryBad)

    {


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






    public void seleccionarResultadoControlMovimientos(
            LinearLayout contenedorMovSelectPoor,LinearLayout contenedorMovColorPoor
        ,LinearLayout contenedorMovSelectNormal,LinearLayout contenedorMovColorNormal
        ,LinearLayout contenedorMovSelectGood, LinearLayout contenedorMovColorGood
        ,int result
    )
    {

        //DisplayMetrics metrics = getResources().getDisplayMetrics();
        //Log.d("density_dpi",String.valueOf(metrics.densityDpi));
        //Log.d("density",String.valueOf(metrics.density));
        //Log.d("scaled_density",String.valueOf(metrics.scaledDensity));

        ViewGroup.LayoutParams higthMovSelectPoor;
        ViewGroup.LayoutParams higthMovSelectNormal;
        ViewGroup.LayoutParams higthMovSelectGood;
        int px;


        switch (result)
        {
            case 0:
                contenedorMovSelectPoor.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_seleccion_result));
                px = (int )getResources().getDimension(R.dimen.inf_size_padding_select_result);//al pillarlo de dimens lo convierte solo a px. No hace falta convertirlo-
                //px = (int) getResources().getDimension(R.dimen.inf_size_padding_select_result) * (metrics.densityDpi / 160);
                contenedorMovSelectPoor.setPadding(px, px, px, px);
                //asignamos el tamaño del contenedor
                higthMovSelectPoor = contenedorMovSelectPoor.getLayoutParams();
                higthMovSelectPoor.height = (int) getResources().getDimension(R.dimen.inf_size_select_result);
                contenedorMovColorPoor.setAlpha(1f);

                contenedorMovSelectNormal.setBackground(null);
                contenedorMovSelectNormal.setPadding(0,0, 0, 0);
                //asignamos el tamaño del contenedor
                higthMovSelectNormal = contenedorMovSelectNormal.getLayoutParams();
                higthMovSelectNormal.height = (int) getResources().getDimension(R.dimen.inf_size_select_no_result);
                contenedorMovColorNormal.setAlpha(0.35f);

                contenedorMovSelectGood.setBackground(null);
                contenedorMovSelectGood.setPadding(0, 0, 0, 0);
                //asignamos el tamaño del contenedor
                higthMovSelectGood = contenedorMovSelectGood.getLayoutParams();
                higthMovSelectGood.height = (int) getResources().getDimension(R.dimen.inf_size_select_no_result);
                contenedorMovColorGood.setAlpha(0.35f);



                break;
            case 1:


                contenedorMovSelectPoor.setBackground(null);
                contenedorMovSelectPoor.setPadding(0, 0, 0, 0);
                //asignamos el tamaño del contenedor
                higthMovSelectPoor = contenedorMovSelectPoor.getLayoutParams();
                higthMovSelectPoor.height = (int) getResources().getDimension(R.dimen.inf_size_select_no_result);
                contenedorMovColorPoor.setAlpha(0.30f);

                contenedorMovSelectNormal.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_seleccion_result));
                px = (int )getResources().getDimension(R.dimen.inf_size_padding_select_result);//al pillarlo de dimens lo convierte solo a px. No hace falta convertirlo-
                //px = (int) getResources().getDimension(R.dimen.inf_size_padding_select_result) * (metrics.densityDpi / 160);
                contenedorMovSelectNormal.setPadding(px, px, px, px);
                //asignamos el tamaño del contenedor
                higthMovSelectNormal = contenedorMovSelectNormal.getLayoutParams();
                higthMovSelectNormal.height = (int) getResources().getDimension(R.dimen.inf_size_select_result);
                contenedorMovColorNormal.setAlpha(1f);

                contenedorMovSelectGood.setBackground(null);
                contenedorMovSelectGood.setPadding(0, 0, 0, 0);
                //asignamos el tamaño del contenedor
                higthMovSelectGood = contenedorMovSelectGood.getLayoutParams();
                higthMovSelectGood.height = (int) getResources().getDimension(R.dimen.inf_size_select_no_result);
                contenedorMovColorGood.setAlpha(0.30f);

                break;
            case 2:

                contenedorMovSelectPoor.setBackground(null);
                contenedorMovSelectPoor.setPadding(0, 0, 0, 0);
                //asignamos el tamaño del contenedor
                higthMovSelectPoor = contenedorMovSelectPoor.getLayoutParams();
                higthMovSelectPoor.height = (int) getResources().getDimension(R.dimen.inf_size_select_no_result);
                contenedorMovColorPoor.setAlpha(0.35f);

                contenedorMovSelectNormal.setBackground(null);
                contenedorMovSelectNormal.setPadding(0, 0, 0, 0);
                //asignamos el tamaño del contenedor
                higthMovSelectNormal = contenedorMovSelectNormal.getLayoutParams();
                higthMovSelectNormal.height = (int) getResources().getDimension(R.dimen.inf_size_select_no_result);
                contenedorMovColorNormal.setAlpha(0.35f);

                contenedorMovSelectGood.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_seleccion_result));
                px = (int )getResources().getDimension(R.dimen.inf_size_padding_select_result);//al pillarlo de dimens lo convierte solo a px. No hace falta convertirlo-
                //px = (int) getResources().getDimension(R.dimen.inf_size_padding_select_result) * (metrics.densityDpi / 160);
                contenedorMovSelectGood.setPadding(px, px, px, px);
                //asignamos el tamaño del contenedor
                higthMovSelectGood = contenedorMovSelectGood.getLayoutParams();
                higthMovSelectGood.height = (int) getResources().getDimension(R.dimen.inf_size_select_result);
                contenedorMovColorGood.setAlpha(1f);

                break;
        }


    }






    public void setTrapecioCircularRotation(ImageView trapecio, float porcentaje, String prefijoNameResourse)
    {
        // id del Drawable
        int drawableResourceId;

        //Obtain drawable
        Drawable drawable;

        //Convert drawable in to bitmap
        Bitmap obtainBitmap ;

        //Pass the bitmap and color code to change the icon color dynamically.
        Bitmap changeColor;


        //if(porcentaje==0)
        //{

            //drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_01", "drawable", this.getContext().getPackageName());
            //trapecio.setImageResource(drawableResourceId);
        //}
        if(porcentaje>0 && porcentaje<5)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_02", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }

        if(porcentaje>=5 && porcentaje<10 )
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_03", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }

        if(porcentaje>=10 && porcentaje<15)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_04", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }

        if(porcentaje>=15 && porcentaje<20)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_05", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=20 && porcentaje<25)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_06", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=25 && porcentaje<30)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_07", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=30 && porcentaje<35)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_08", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=35 && porcentaje<40)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_09", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=40 && porcentaje<45)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_10", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=45 && porcentaje<50)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_11", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=50 && porcentaje<55)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_12", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=55 && porcentaje<60)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_13", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=60 && porcentaje<65)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_14", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=65 && porcentaje<70)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_15", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=70 && porcentaje<75)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_16", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=75 && porcentaje<80)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_17", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=80 && porcentaje<85)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_18", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=85 && porcentaje<90)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_19", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }
        if(porcentaje>=90)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_20", "drawable", this.getContext().getPackageName());
            drawable = ContextCompat.getDrawable(getContext(), drawableResourceId);
            obtainBitmap= Util.convertDrawableToBitmap(drawable);
            changeColor=Util.changeImageColor(obtainBitmap, ContextCompat.getColor(getContext(),R.color.data_rotation_color25));

            trapecio.setImageBitmap(changeColor);
        }









    }

    public  void setTrapecioCircular(ImageView trapecio, float porcentaje,String prefijoNameResourse)
    {

        int drawableResourceId;

        if(porcentaje==0)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_01", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>0 && porcentaje<5)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_02", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }

        if(porcentaje>=5 && porcentaje<10 )
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_03", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }

        if(porcentaje>=10 && porcentaje<15)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_04", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }

        if(porcentaje>=15 && porcentaje<20)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_05", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=20 && porcentaje<25)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_06", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=25 && porcentaje<30)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_07", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=30 && porcentaje<35)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_08", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=35 && porcentaje<40)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_09", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=40 && porcentaje<45)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_10", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=45 && porcentaje<50)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_11", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=50 && porcentaje<55)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_12", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=55 && porcentaje<60)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_13", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=60 && porcentaje<65)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_14", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=65 && porcentaje<70)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_15", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=70 && porcentaje<75)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_16", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=75 && porcentaje<80)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_17", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=80 && porcentaje<85)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_18", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=85 && porcentaje<90)
        {

            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_19", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }
        if(porcentaje>=90)
        {
            drawableResourceId = this.getResources().getIdentifier(prefijoNameResourse+"_20", "drawable", this.getContext().getPackageName());
            trapecio.setImageResource(drawableResourceId);
        }



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







        if(((HomeActivity) getActivity()).getEstadoSensor()==1) {
            TaskSensorBle tareasSensor = new TaskSensorBle(getActivity());
            tareasSensor.desHabilitarAcelerometro();
            tareasSensor.desHabilitarNotificacionesAccel();

            //cara_fragment_3D.getFace().animationEnabled(false);
            //cara_fragment_3D.moverCara3D(false);
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
        super.onResume();

       // getActivity().	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }











    //char Line

    //**************************************************************************************************************
    //**************************************************************************************************************


    private void setData(int count, float range) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult) + 15;
            yVals1.add(new Entry(i, val));
        }

        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i = 0; i < count-1; i++) {
            float mult = range;
            //float val = (float) (Math.random() * mult) + 450;
            float val = (float) (Math.random() * mult) -50;
            yVals2.add(new Entry(i, val));
//            if(i == 10) {
//                yVals2.add(new Entry(i, val + 50));
//            }
        }

        ArrayList<Entry> yVals3 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range;
            //float val = (float) (Math.random() * mult) + 500;
            float val = (float) (Math.random() * mult) -20 ;
            yVals3.add(new Entry(i, val));
        }

        LineDataSet set1, set2, set3;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);


            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "inclinación");



            // set1.setValueTextColor(ContextCompat.getColor(this.getBaseContext(),R.color.data_inclination));

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            //set1.setColor(ColorTemplate.getHoloBlue());
            set1.setColor(ContextCompat.getColor(this.getContext(),R.color.data_inclination));
            //set1.setCircleColor(Color.WHITE);
            set1.setCircleColor(ContextCompat.getColor(getContext(),R.color.data_inclination));
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            //set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setFillColor(ContextCompat.getColor(this.getContext(),R.color.data_inclination));
            //set1.setFillColor(ContextCompat.getColor(this.getBaseContext(),R.color.data_inclination));
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type




            set2 = new LineDataSet(yVals2, "Flexión");


            //set2.setHighLightColor(ContextCompat.getColor(getBaseContext(),R.color.data_flexion));

            // set2.setValueTextColor(ContextCompat.getColor(getBaseContext(),R.color.data_flexion));
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            //set2.setColor(Color.RED);
            set2.setColor(ContextCompat.getColor(getContext(),R.color.data_flexion));
            //set2.setCircleColor(Color.WHITE);
            set2.setCircleColor(ContextCompat.getColor(getContext(),R.color.data_flexion));
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            //set2.setFillColor(ContextCompat.getColor(getBaseContext(),R.color.data_flexion));
            set2.setDrawCircleHole(false);
            //set2.setHighLightColor(Color.rgb(244, 117, 0));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(yVals3, "Rotación");
            // set3.setValueTextColor(ContextCompat.getColor(getBaseContext(),R.color.data_rotacion));
            set3.setAxisDependency(YAxis.AxisDependency.LEFT);
            // set3.setColor(Color.YELLOW);
            set3.setColor(ContextCompat.getColor(getContext(),R.color.data_rotacion));
            //set3.setCircleColor(Color.WHITE);
            set3.setCircleColor(ContextCompat.getColor(getContext(),R.color.data_rotacion));
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            // set3.setFillColor(ContextCompat.getColor(getBaseContext(),R.color.data_rotacion));
            set3.setDrawCircleHole(false);
            set3.setHighLightColor(Color.rgb(244, 117, 117));

            // create a data object with the datasets
            LineData data = new LineData(set1, set2, set3);
            //data.setValueTextColor(ContextCompat.getColor(getBaseContext(),R.color.colorPrimary));
            // data.setValueTextColor(Color.WHITE);
            //data.setValueTextSize(9f);
            data.setDrawValues(false);



            // set data
            mChart.setData(data);
        }
    }



    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());

        mChart.centerViewToAnimated(e.getX(), e.getY(), mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
        //mChart.zoomAndCenterAnimated(2.5f, 2.5f, e.getX(), e.getY(), mChart.getData().getDataSetByIndex(dataSetIndex)
        // .getAxisDependency(), 1000);
        //mChart.zoomAndCenterAnimated(1.8f, 1.8f, e.getX(), e.getY(), mChart.getData().getDataSetByIndex(dataSetIndex)
        // .getAxisDependency(), 1000);
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }



    //**************************************************************************************************************
    //**************************************************************************************************************









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


 LinearLayout barraRotationGood = (LinearLayout) view.findViewById(R.id.barraRotationGood);
        LinearLayout barraRotationFair = (LinearLayout) view.findViewById(R.id.barraRotationFair);
        LinearLayout barraRotationBad = (LinearLayout) view.findViewById(R.id.barraRotationBad);
        LinearLayout barraRotationVeryBad = (LinearLayout) view.findViewById(R.id.barraRotationVeryBad);


        TextView txtBarraRotationGood = (TextView) view.findViewById(R.id.txtBarraRotationGood);
        TextView txtBarraRotationFair = (TextView) view.findViewById(R.id.txtBarraRotationFair);
        TextView txtBarraRotationBad = (TextView) view.findViewById(R.id.txtBarraRotationBad);
        TextView txtBarraRotationVeryBad = (TextView) view.findViewById(R.id.txtBarraRotationVeryBad);


        LinearLayout corcheteRotationGood = (LinearLayout) view.findViewById(R.id.corcheteRotationGood);
        LinearLayout corcheteRotationBad = (LinearLayout) view.findViewById(R.id.corcheteRotationBad);


        LinearLayout contenedorTxtCorcheteRotationGood = (LinearLayout) view.findViewById(R.id.contenedorTxtCorcheteRotationGood);
        LinearLayout contenedorTxtCorcheteRotationBad = (LinearLayout) view.findViewById(R.id.contenedorTxtCorcheteRotationBad);

        TextView txt_porcentaje_good_rotation = (TextView) view.findViewById(R.id.txt_porcentaje_good_rotation);
        TextView txt_porcentaje_bad_rotation = (TextView) view.findViewById(R.id.txt_porcentaje_bad_rotation);



        porcentajeRotationGood=0.15f;
        porcentajeRotationFair=0.35f;
        porcentajeRotationBad=0.25f;
        porcentajeRotationVeryBad=0.25f;

        setViewBarraPosition(barraRotationGood,barraRotationFair,barraRotationBad,barraRotationVeryBad,
                txtBarraRotationGood,txtBarraRotationFair,txtBarraRotationBad,txtBarraRotationVeryBad,
                corcheteRotationGood,corcheteRotationBad,contenedorTxtCorcheteRotationGood,contenedorTxtCorcheteRotationBad,
                txt_porcentaje_good_rotation,txt_porcentaje_bad_rotation,porcentajeRotationGood,porcentajeRotationFair,porcentajeRotationBad,porcentajeRotationVeryBad);



 */