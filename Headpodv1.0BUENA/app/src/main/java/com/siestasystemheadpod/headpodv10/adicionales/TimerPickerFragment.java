package com.siestasystemheadpod.headpodv10.adicionales;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import com.siestasystemheadpod.headpodv10.R;

import java.util.Calendar;

/**
 * Created by Michael on 02/11/2016.
 */
public  class TimerPickerFragment extends DialogFragment
         {

    private int horas;
    private int minutos;
    public int chivato=0;
/*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
       // final Calendar c = Calendar.getInstance();
        //int hour = c.get(Calendar.HOUR_OF_DAY);
        //int minute = c.get(Calendar.MINUTE);






        // Create a new instance of TimePickerDialog and return it
        Dialog dialog= new TimePickerDialog(getActivity(), this, 0, 15,
                DateFormat.is24HourFormat(getActivity()));


        dialog.setTitle(getResources().getString(R.string.establecer_tiempo));
        return dialog;
    }
*/



}