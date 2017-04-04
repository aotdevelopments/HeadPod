package com.siestasystemheadpod.headpodv10.adicionales;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.siestasystemheadpod.headpodv10.R;



/**
 * Created by Michael on 17/10/2016.
 * Esta será la gráfica que se encargara de tomar los datos del sensor en tiempo real
 * En el eje y se representaran los angulos del sensor
 *
 */
public class GrafinaLinealEnTiempoReal implements OnChartValueSelectedListener {


    //Gráfica lineal
    private LineChart mChart;
    private Typeface mTfLight;
    private Context context;
    private boolean startData=false;

    private ILineDataSet set1;
    private ILineDataSet set2;


    public GrafinaLinealEnTiempoReal(LineChart mChart, Typeface fuente, Context context)
    {
        this.mChart=mChart;
        mTfLight=fuente;
        this.context=context;
    }

    public void setStartData(boolean peticion)
    {
        startData=peticion;
    }


    public void setGraficaLineal(LineChart mChart)
    {
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
        //Para el zoom
        mChart.setPinchZoom(true);
        mChart.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.data_background));
        //mChart.animateX(2500);

        //Deshabilitamos la leyenda
        mChart.getLegend().setEnabled(false);

        //Des habilitamos el eje derecho
       // mChart.getAxisRight().setEnabled(false);





        //Valores correspondientes al eje X.
        //*****************************************************************************************
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        xAxis.setTextSize(15f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//Para situar los valores del eje abajo.
        xAxis.setAxisMinimum(0f);
        //xAxis.setAxisMaximum(100f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setAvoidFirstLastClipping(true);
        //******************************************************************************************
        xAxis.setEnabled(false);




        //Valores correspondientes al eje Y.
        //******************************************************************************************
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        // leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        leftAxis.setAxisMaximum(60f);
       // leftAxis.setAxisMaximum(30f);
        leftAxis.setTextSize(15f);
        leftAxis.setAxisMinimum(-60f);
        //leftAxis.setAxisMinimum(18f);
        //leftAxis.setMinWidth(15);
        //leftAxis.setSpaceMin(15);
        //Quiere decir que acepta 20 valores en su eje Y (creo). Por eso se ajusta el intervarlo a mostrar.
        leftAxis.setLabelCount(5);//55//20
        // mChart.setVisibleYRangeMaximum(60, AxisDependency.LEFT);
        leftAxis.setAxisLineWidth(4f);
        leftAxis.setAxisLineColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        //Deshabilitamos el eje derecho del y
        mChart.getAxisRight().setEnabled(false);
        //Dejo comentado este código ya que puede ser usado en un futuro para representar la gravedad y su rotación.
        //
        /*
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTypeface(mTfLight);
        // leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        rightAxis.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        rightAxis.setAxisMaximum(1f);
        // leftAxis.setAxisMaximum(30f);
        //rightAxis.setTextSize(15f);
        rightAxis.setAxisMinimum(-1f);
        //leftAxis.setAxisMinimum(18f);
        //leftAxis.setMinWidth(15);
        //leftAxis.setSpaceMin(15);
        //Quiere decir que acepta 20 valores en su eje Y (creo). Por eso se ajusta el intervarlo a mostrar.
       // rightAxis.setLabelCount(5);//55//20
        // mChart.setVisibleYRangeMaximum(60, AxisDependency.LEFT);

        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);

        rightAxis.setAxisLineWidth(4f);
        rightAxis.setAxisLineColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
       // rightAxis.setDrawGridLines(true);
       // rightAxis.setGranularityEnabled(true);
        */

        //Inicializamos las tres lineas que tendra la grafíca.
        //**************************************************************************************
        // create a data object with the datasets
        LineData data = new LineData(setDataLineX(), setDataLineY(), setDataLineZ());
        data.setDrawValues(false);
        // set data
        mChart.setData(data);
        //***************************************************************************************

    }

    private Context getContext()
    {
        return context;
    }


    //Método para añadir los datos dinamicamente proporcionados por el sensor.
    public void addEntryY(float angI, float angF, float angR) {

        if(startData) {
            LineData data = mChart.getData();

            if (data != null && data.getDataSetCount() > 0) {

                 set1 = data.getDataSetByIndex(0);//Flexión
                 set2 = data.getDataSetByIndex(1);//Inclinación
                // ILineDataSet set3 = data.getDataSetByIndex(2);//Rotación
                // set.addEntry(...); // can be called as well

                if (set1 == null) {
                    set1 = setDataLineX();
                    data.addDataSet(set1);
                }
                if (set2 == null) {
                    set2 = setDataLineY();
                    data.addDataSet(set2);
                }

           /* if( set3==null)
            {
                set3 = setDataLineZ();
                data.addDataSet(set3);
            }*/


                //(float) (Math.random() * 40) + 30f), 0
                data.addEntry(new Entry(set1.getEntryCount(), angI), 0);//Intercambiamos para que se correspondan los colores.
                data.addEntry(new Entry(set2.getEntryCount(), angF), 1);
                // data.addEntry(new Entry(set3.getEntryCount(),angR),2);//Rotación

                data.notifyDataChanged();

                // let the chart know it's data has changed
                mChart.notifyDataSetChanged();

                // limit the number of visible entries
                mChart.setVisibleXRangeMaximum(30);
                // mChart.setVisibleYRange(30, AxisDependency.LEFT);

                // move to the latest entry
                mChart.moveViewToX(data.getEntryCount());

                // this automatically refreshes the chart (calls invalidate())
                // mChart.moveViewTo(data.getXValCount()-7, 55f,
                // AxisDependency.LEFT);
            }
        }

    }


    //método para generar la linea del eje x (Inclinación)
    private LineDataSet setDataLineX()
    {
        LineDataSet set = new LineDataSet(null, "Inclinación");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ContextCompat.getColor(this.getContext(),R.color.data_inclination));
        set.setCircleColor(ContextCompat.getColor(getContext(),R.color.data_inclination));
        set.setLineWidth(2f);
        set.setCircleRadius(3f);
        set.setFillAlpha(65);
        set.setFillColor(ContextCompat.getColor(this.getContext(),R.color.data_inclination));
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setDrawCircleHole(false);
        set.setDrawValues(false);
        return set;
    }

    //método para generar la linea del eje y (Flexión)
    private LineDataSet setDataLineY()
    {
        LineDataSet set = new LineDataSet(null, "Flexion");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ContextCompat.getColor(getContext(),R.color.data_flexion));
        set.setCircleColor(ContextCompat.getColor(getContext(),R.color.data_flexion));
        set.setLineWidth(2f);
        set.setCircleRadius(3f);
        set.setFillAlpha(65);
        set.setFillColor(Color.RED);
        set.setDrawCircleHole(false);
        set.setDrawValues(false);
        return set;

    }


    //método para generar la linea del eje z (Rotación)
    private LineDataSet setDataLineZ()
    {

        LineDataSet set = new LineDataSet(null, "Rotacion");
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);//Para el eje derecho en caso de representar la rotación con G's.
        set.setColor(ContextCompat.getColor(getContext(),R.color.data_rotacion));
        set.setCircleColor(ContextCompat.getColor(getContext(),R.color.data_rotacion));
        set.setLineWidth(2f);
        set.setCircleRadius(3f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
        set.setDrawCircleHole(false);
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setDrawValues(false);

        return set;

    }


    public void clearLineChart()
    {



        mChart.getData().removeDataSet(set1);
        mChart.getData().removeDataSet(set2);
        mChart.clearAllViewportJobs();
        mChart.getData().clearValues();
        //mChart.destroyDrawingCache();
        mChart.clear();






    }



    //Sobrescrituras de la interfaz
    //**************************************************************************************************************
    //**************************************************************************************************************
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



}