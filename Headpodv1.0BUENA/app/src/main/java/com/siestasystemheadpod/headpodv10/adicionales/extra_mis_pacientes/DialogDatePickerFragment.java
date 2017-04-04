package com.siestasystemheadpod.headpodv10.adicionales.extra_mis_pacientes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.fragments.pacientes.AnadirPacienteFragment;
import com.siestasystemheadpod.headpodv10.fragments.pacientes.PacienteEditFragment;

/**
 * Created by Michael on 16/11/2016.
 * Esta clase se usa para escoger la fecha de nacimiento de un paciente (añadir paciente nuevo),
 * mediante una ventana emergente Dialog
 * De forma que se calculara la edad
 */


public class DialogDatePickerFragment extends DialogFragment {
             //implements DatePickerDialog.OnDateSetListener{

   // public int edad=0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";


    // TODO: Rename and change types of parameters
    private int dia;
    private int mes;
    private int ano;
    public boolean datos_ok= false;

    private DialogDatePickerDialog dialog2;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicionPlantillaPasosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogDatePickerFragment newInstance(int param1, int param2 , int param3) {
        DialogDatePickerFragment fragment = new DialogDatePickerFragment();
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
            dia = getArguments().getInt(ARG_PARAM1);
            mes = getArguments().getInt(ARG_PARAM2);
            ano = getArguments().getInt(ARG_PARAM3);

        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Establecemos la fecha por defecto.
        //Por defecto seleccionamos el 01/01/2000 pasado en AnadirPacienteFragment

        // Create a new instance of DatePickerDialog and return it
        // OJO!!: Los parametros de la fecha en este orden: AÑO, MES, DÍA
        // dialog = new DatePickerDialog(getActivity(), this,ano,mes,dia);//el dia y mes empiezan de cero, (0=1)
        dialog2 = new DialogDatePickerDialog(getActivity(),null,ano,mes,dia,getActivity().getResources());

        dialog2.setPermanentTitle(getResources().getString(R.string.selecciona_ano_nacimiento));

        dialog2.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.aceptar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        datos_ok=true;

                        AnadirPacienteFragment anadirPacienteFragment = (AnadirPacienteFragment) ((HomeActivity) getActivity()).getSupportFragmentManager().findFragmentByTag("fragment_anadir_paciente");
                        if (anadirPacienteFragment == null) {
                            //Aquí sera el framente de preferencias
                            //medicionPlantillaPasosFragment = (MedicionPlantillaPasosFragment) ((HomeActivity) getActivity()).getSupportFragmentManager().findFragmentByTag("fragment_medicion_con_headpod");


                        }


                        if (anadirPacienteFragment != null) {
                            anadirPacienteFragment.editEdad.setText(String.valueOf(dialog2.getEdad()));

                            anadirPacienteFragment.editEdad.setFocusable(true);
                            anadirPacienteFragment.txtAnos.setText(getResources().getQuantityText(R.plurals.anos, dialog2.getEdad()));
                        }

                        PacienteEditFragment pacienteEditFragment = (PacienteEditFragment) (getActivity()).getSupportFragmentManager().findFragmentByTag("fragment_edit_paciente");

                        if(pacienteEditFragment!=null)
                        {
                            pacienteEditFragment.editEdad.setText(String.valueOf(dialog2.getEdad()));

                            pacienteEditFragment.editEdad.setFocusable(true);
                            pacienteEditFragment.txtAnos.setText(getResources().getQuantityText(R.plurals.anos, dialog2.getEdad()));

                        }




                        if(dialog2.getEdad()<0)
                        {
                            //gracias a descativar el boton a este caso no llegamos nunca pero lo dejo por si las moscas
                            Log.d("edad_final",String.valueOf(dialog2.getEdad()));
                            Toast.makeText(getContext(),getResources().getString(R.string.edad_fail),Toast.LENGTH_LONG).show();
                        }

                        else
                        {

                            //edad=getEdad();
                            dia=dialog2.getDia();
                            mes=dialog2.getMes();
                            ano=dialog2.getAno();
                            Log.d("edad_final",String.valueOf(dialog2.getEdad()));
                            Log.d("dia_escogido",String.valueOf(dia));
                            Log.d("mes_select",String.valueOf(mes));
                            Log.d("año_select",String.valueOf(ano));

                        }

                    }
                });
        return dialog2;
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

    public DialogDatePickerDialog getDialog2()
    {
        return dialog2;
    }



}