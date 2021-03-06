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
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.siestasystemheadpod.headpodv10.R;

import java.util.ArrayList;

/**
 * Created by Michael on 17/10/2016.
 *
 * Clase encargada de representar los datos de la grafica lineal en tiempo real.
 */
public class GrafinaLinealv2 implements OnChartValueSelectedListener {


    //Gráfica lineal
    private LineChart mChart;
    private Typeface mTfLight;
    private Context context;



    public GrafinaLinealv2(LineChart mChart, Typeface fuente, Context context)
    {
        this.mChart=mChart;
        mTfLight=fuente;
        this.context=context;

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


        // mChart.setVisibleYRangeMinimum(15,AxisDependency.LEFT);
        // mChart.setVisibleYRangeMaximum(60, AxisDependency.LEFT);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        //mChart.setBackgroundColor(Color.LTGRAY);
        mChart.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.data_background));

        // add data
        setData(10, 30);
        //setDataTReal(30,15,45,5);

        mChart.animateX(2500);

        //Deshabilitamos el eje derecho
        mChart.getAxisRight().setEnabled(false);
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

    }







    private Context getContext()
    {
        return context;
    }




    private void setData(int count, float range) {

        ArrayList<Entry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult) + 15;
            yVals1.add(new Entry(i, val));
        }

        ArrayList<Entry> yVals2 = new ArrayList<>();

        for (int i = 0; i < count-1; i++) {
            //float mult = range;
            //float val = (float) (Math.random() * mult) + 450;
            float val = (float) (Math.random() * range) -50;
            yVals2.add(new Entry(i, val));
//            if(i == 10) {
//                yVals2.add(new Entry(i, val + 50));
//            }
        }

        ArrayList<Entry> yVals3 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            //float mult = range;
            //float val = (float) (Math.random() * mult) + 500;
            float val = (float) (Math.random() * range) -20 ;
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


/*
    private void feedMultiple() {

        if (thread != null)
            thread.interrupt();

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                addEntry();
            }
        };

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {

                    // Don't generate garbage runnables inside the loop.
                    fragmentActivity.runOnUiThread(runnable);

                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

 */