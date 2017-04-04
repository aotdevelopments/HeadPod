package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
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
import com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor.TaskSensorBle;


import java.util.ArrayList;
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
 * {@link PruebaGraficosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PruebaGraficosFragment extends Fragment implements View.OnClickListener, OnChartValueSelectedListener {
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

    private LineChart mChart;

    protected Typeface mTfLight;

    ImageView btnAvatar ;
    ImageView btnMedicion;
    ImageView btnMisPacientes  ;
    ImageView btnCargarPaciente ;

    private OnFragmentInteractionListener mListener;

    public PruebaGraficosFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        ((HomeActivity) getActivity()).setFragmentActual(this);
        //((HomeActivity) getActivity()).fragmentActual=this;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prueba_graficos, container, false);

        //Para el titulo del menú
        //((HomeActivity) getActivity()).getSupportActionBar().setTitle("");
        TextView titulo= (TextView)((HomeActivity) getActivity()).findViewById(R.id.toolbar_title);

        titulo.setText("pruebaaaaa");




        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);

        titulo.setTypeface(myFont);


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
//        l.setYOffset(11f);

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













        //LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);


       // linearLayout.setRotation(90);















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
