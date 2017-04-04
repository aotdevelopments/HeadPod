package com.siestasystemheadpod.headpodv10.adicionales;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.fragments.MedicionPlantillaPasosFragment;
import com.siestasystemheadpod.headpodv10.R;

public class DialogSetTimeMedition extends DialogFragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";


    // TODO: Rename and change types of parameters
    private int dias;
    private int horas;
    private int minutos;



    private NumberPicker npDias;
    private NumberPicker npHoras;
    private NumberPicker npMinutos;




    public DialogSetTimeMedition() {
        // Required empty public constructor


    }

    public NumberPicker getNpDias()
    {
        return npDias;
    }

    public NumberPicker getNpHoras()
    {
        return npHoras;
    }

    public  NumberPicker getNpMinutos()
    {
        return npMinutos;
    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicionPlantillaPasosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogSetTimeMedition newInstance(int param1, int param2 , int param3) {
        DialogSetTimeMedition fragment = new DialogSetTimeMedition();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3,param3);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dias = getArguments().getInt(ARG_PARAM1);
            horas = getArguments().getInt(ARG_PARAM2);
            minutos = getArguments().getInt(ARG_PARAM3);

        }
    }





    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view_inflada = inflater.inflate(R.layout.fragment_dialog_set_time_medition, null);

        npDias = (NumberPicker) view_inflada.findViewById(R.id.diasPicker);
        npHoras = (NumberPicker) view_inflada.findViewById(R.id.horasPicker);
        npMinutos = (NumberPicker) view_inflada.findViewById(R.id.minutosPicker);

        MedicionPlantillaPasosFragment medicionPlantillaPasosFragment = (MedicionPlantillaPasosFragment) ((HomeActivity) getActivity()).getSupportFragmentManager().findFragmentByTag("fragment_medicion_sin_headpod");

        if(medicionPlantillaPasosFragment==null)
        {
             medicionPlantillaPasosFragment = (MedicionPlantillaPasosFragment) ((HomeActivity) getActivity()).getSupportFragmentManager().findFragmentByTag("fragment_medicion_con_headpod");

        }

        //MedicionPlantillaPasosFragment medicionPlantillaPasosFragment = new MedicionPlantillaPasosFragment();

        npDias.setMinValue(0);
        npDias.setMaxValue(medicionPlantillaPasosFragment.getNumDiasMax()-1);
        npDias.setValue(dias);

        npHoras.setMinValue(0);
        npHoras.setMaxValue(23);
        npHoras.setValue(horas);


        npMinutos.setMinValue(0);//medicionPlantillaPasosFragment.getIntervarloMin()
        npMinutos.setMaxValue(59);
        npMinutos.setValue(minutos);

        builder.setView(view_inflada)
                // Add action buttons
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {






                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...

                        npDias.setValue(npDias.getValue());
                        npHoras.setValue(npHoras.getValue());
                        npMinutos.setValue(npMinutos.getValue());


                        MedicionPlantillaPasosFragment medicionPlantillaPasosFragment = (MedicionPlantillaPasosFragment) ((HomeActivity) getActivity()).getSupportFragmentManager().findFragmentByTag("fragment_medicion_sin_headpod");
                        if(medicionPlantillaPasosFragment==null)
                        {
                            medicionPlantillaPasosFragment = (MedicionPlantillaPasosFragment) ((HomeActivity) getActivity()).getSupportFragmentManager().findFragmentByTag("fragment_medicion_con_headpod");

                        }

                         if(getNpDias().getValue()>0)
                        {

                        int aux = medicionPlantillaPasosFragment.getNumMinutosMax()+ medicionPlantillaPasosFragment.getNumHorasMax();
                            medicionPlantillaPasosFragment.discreteSeekBar1.setProgress(aux+getNpDias().getValue()-1);
                         }

                        else if(getNpHoras().getValue()>0)
                        {


                            int aux = medicionPlantillaPasosFragment.getNumMinutosMax()+ getNpHoras().getValue();
                            medicionPlantillaPasosFragment.discreteSeekBar1.setProgress(aux);
                        }
                        else if(getNpMinutos().getValue()>0)
                        {

                            int aux = medicionPlantillaPasosFragment.getNumMinutosMax();
                            medicionPlantillaPasosFragment.discreteSeekBar1.setProgress(aux);
                        }

                        String valor;


                        //solo Dias
                        //***********
                        if (getNpDias().getValue()>0 && getNpHoras().getValue()==0 && getNpMinutos().getValue()==0)
                        {
                            valor = getResources().getString(R.string.tiempo_dos_puntos)+" "+npDias.getValue()+" "+ getResources().getQuantityString(R.plurals.dias,npDias.getValue());
                            medicionPlantillaPasosFragment.getTxtTiempoSelect().setText(valor);
                            medicionPlantillaPasosFragment.setDiasSelect(npDias.getValue());
                        }

                        //Dias y horas
                        if (getNpDias().getValue()>0 && getNpHoras().getValue()>0 && getNpMinutos().getValue()==0)
                        {
                            valor = getResources().getString(R.string.tiempo_dos_puntos)+" "+npDias.getValue()+" "+ getResources().getQuantityString(R.plurals.dias,npDias.getValue())
                            +",\n"+" "+npHoras.getValue()+" "+getResources().getQuantityString(R.plurals.horas,npHoras.getValue()) ;
                            medicionPlantillaPasosFragment.getTxtTiempoSelect().setText(valor);
                        }

                        //Dias y minutos
                        if (getNpDias().getValue()>0 && getNpHoras().getValue()==0 && getNpMinutos().getValue()>0)
                        {
                            valor = getResources().getString(R.string.tiempo_dos_puntos)+" "+npDias.getValue()+" "+ getResources().getQuantityString(R.plurals.dias,npDias.getValue())
                                    +",\n"+" "+npMinutos.getValue()+" min" ;
                            medicionPlantillaPasosFragment.getTxtTiempoSelect().setText(valor);
                        }


                        //Dias, horas y minutos
                        if (getNpDias().getValue()>0 && getNpHoras().getValue()>0 && getNpMinutos().getValue()>0)
                        {
                            valor = getResources().getString(R.string.tiempo_dos_puntos)+" "+npDias.getValue()+" "+ getResources().getQuantityString(R.plurals.dias,npDias.getValue())
                                    +",\n"+" "+npHoras.getValue()+" "+getResources().getQuantityString(R.plurals.horas,npHoras.getValue())+", "+npMinutos.getValue()+" min" ;
                            medicionPlantillaPasosFragment.getTxtTiempoSelect().setText(valor);
                        }


                        //Solo horas
                        //******************
                        if (getNpDias().getValue()==0 && getNpHoras().getValue()>0 && getNpMinutos().getValue()==0)
                        {
                            valor = getResources().getString(R.string.tiempo_dos_puntos)+" "+npHoras.getValue()+" "+getResources().getQuantityString(R.plurals.horas,npHoras.getValue());
                            medicionPlantillaPasosFragment.getTxtTiempoSelect().setText(valor);
                        }
                        //Horas y minutos
                        if (getNpDias().getValue()==0 && getNpHoras().getValue()>0 && getNpMinutos().getValue()>0)
                        {
                            valor = getResources().getString(R.string.tiempo_dos_puntos)+" "+npHoras.getValue()+" "+getResources().getQuantityString(R.plurals.horas,npHoras.getValue())+",\n"+
                                    +npMinutos.getValue()+" min" ;

                            medicionPlantillaPasosFragment.getTxtTiempoSelect().setText(valor);
                        }


                        //Solo minutos
                        //**************
                        if (getNpDias().getValue()==0 && getNpHoras().getValue()==0 && getNpMinutos().getValue()>0)
                        {
                            valor = getResources().getString(R.string.tiempo_dos_puntos)+" "+npMinutos.getValue()+"  min" ;
                            medicionPlantillaPasosFragment.getTxtTiempoSelect().setText(valor);
                        }

                        //Paso los valores finales
                        medicionPlantillaPasosFragment.setDiasSelect(npDias.getValue());
                        medicionPlantillaPasosFragment.setHorasSelect(npHoras.getValue());
                        medicionPlantillaPasosFragment.setMinutosSelect(npMinutos.getValue());


                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // LoginDialogFragment.this.getDialog().cancel();

                        getDialog().cancel();
                    }
                });
        return builder.create();
    }



}
