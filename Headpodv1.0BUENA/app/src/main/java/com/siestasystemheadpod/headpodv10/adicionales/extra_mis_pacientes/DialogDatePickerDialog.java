package com.siestasystemheadpod.headpodv10.adicionales.extra_mis_pacientes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;
import com.siestasystemheadpod.headpodv10.R;
import java.util.Calendar;

/**
 * Created by Michael on 16/11/2016.
 * Esta clase la creamos para poder usar un datePiclerDialog, el cual tiene un cuadro de texto
 * con un titulo, y debajo la fecha a cambiar.
 * El objetivo de usar esta clae es conseguir que el titulo del cuadro de texto, no cambie y se
 * mantenga constante, de lo contrario al cambiar de fecha se cambia el titulo por dicha fecha.
 */


public class DialogDatePickerDialog extends DatePickerDialog {

     //implements DatePickerDialog.OnDateSetListener{
        private Resources resources;
        private CharSequence title;

        private int edad=0;
        private int dia;
        private int mes;
        private int ano;


        //Atributos para obtener la fecha actual
        //******************************************
        private final Calendar c = Calendar.getInstance();
        private int ano_actual;
        private int mes_actual;
        private int dia_actual;
        //********************************************

        private Toast aviso;//Fecha de nacimiento erroenea

        public DialogDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth, Resources resources) {
            super(context, callBack, year, monthOfYear, dayOfMonth);
            this.resources=resources;
            //Inicializamos los datos
            inicializarDatos(dayOfMonth,monthOfYear,year);
           // onDateSet(,year,monthOfYear,dayOfMonth);
        }

        public void setPermanentTitle(CharSequence title) {
            this.title = title;
            setTitle(title);
        }

        @Override
        public void onDateChanged(DatePicker view, int year, int month, int day) {
            super.onDateChanged(view, year, month, day);
            setTitle(title);
            //Actualizamos los datos
            onDateSet2(year,month,day);
        }

        /*@Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            Log.d("onDateSet","dfasfasdfsddf");
            //inicializarDatos(year,month,day);
        }*/




    //@Override
    //public void onDateSet(DatePicker view, int year, int month, int day) {

    //Método para actualizar los datos cuando se detecta un cambio en la fecha.
    public void onDateSet2(int year, int month, int day) {

        obtenerFechaActual();
        //Actualizamos la fecha seleccionada.
        dia=day;
        mes=month;
        ano=year;
        //Calculamos la edad.
        edad = ano_actual - year;

        if (mes_actual < month || (mes_actual == month && dia_actual < day)) {
            edad--;
        }
        Log.d("edad", String.valueOf(edad));

        if(ano_actual==ano && mes_actual==mes && dia_actual<dia)
        {
           aviso.show();
           // Log.d("caso1","dia_actual<dia");

            //this.cancel();
            this.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);

        }
         else if(ano_actual==ano && mes_actual<mes)
        {
            aviso.show();
            //Log.d("caso2","mes actual<mes");
            this.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);

        }
        else if(ano_actual<ano)
        {
            aviso.show();
           // Log.d("caso3","año_actual<año");
            this.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);


        }
        else {
            aviso.cancel();
            this.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);




        }
    }

    public void obtenerFechaActual()
    {
         ano_actual = c.get(Calendar.YEAR);
         mes_actual = c.get(Calendar.MONTH);
         dia_actual = c.get(Calendar.DAY_OF_MONTH);
    }


    public void inicializarDatos(int dayOfMonth,int monthOfYear,int year)
    {
        //Inicializamos los avisos.
        aviso= Toast.makeText(getContext(),resources.getString(R.string.edad_fail),Toast.LENGTH_LONG);
        //Obtenemos la fecha actual
        obtenerFechaActual();
        //Actualizamos la fecha seleccionada.
        dia=dayOfMonth;
        mes=monthOfYear;
        ano=year;
        //Calculamos la edad.
        edad = ano_actual - year;
        if (mes_actual < mes|| (mes_actual == mes && dia_actual < dia)) {
            edad--;
        }

    }



    public int getDia()
    {
        return dia;
    }

    public int getMes()
    {
        return mes;
    }

    public int getAno()
    {
        return ano;
    }

    public int getEdad()
    {
        return edad;
    }



}