package com.siestasystemheadpod.headpodv10.adicionales;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.SharedPreferSession;
import com.siestasystemheadpod.headpodv10.adicionales.ble.DeviceControl;
import com.siestasystemheadpod.headpodv10.adicionales.ble.DeviceControlFragment;
import com.siestasystemheadpod.headpodv10.adicionales.perfil.DialogChangePassword;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.modelo.Paciente;
import com.siestasystemheadpod.headpodv10.data.modelo.Terapeuta;
import com.siestasystemheadpod.headpodv10.fragments.DatosEnTRealFragment;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.siestasystemheadpod.headpodv10.HomeActivity.CALIBRI_BOLD;

/**
 * Created by Michael on 07/11/2016.
 *
 *
 * Aquí estaran todos los avisos de la aplicación.
 */
public class DialogAvisos extends DialogFragment  {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2= "param2";
    private static final String ARG_PARAM3= "activarBtnCancelar";

    private  SharedPreferSession sharedPreferSession;
    private int idResourseString;
    private Paciente mPaciente ;



    //private boolean cancelable=false ;//Para activar o desactivar el boton de cancelar.



    //No usar el metodo vacio, solo el newInstance.
    public DialogAvisos() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idResourseString Parameter 1.
     * @return A new instance of fragment MedicionPlantillaPasosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogAvisos newInstance(int idResourseString) {
        DialogAvisos fragment = new DialogAvisos();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, idResourseString);
        //args.putInt(ARG_PARAM2,tipoImg);
        // args.putString(ARG_PARAM3,mcurrenPathImg);
        fragment.setArguments(args);
        return fragment;
    }

    public static DialogAvisos newInstance(int idResourseString,Paciente paciente) {
        DialogAvisos fragment = new DialogAvisos();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, idResourseString);
        args.putParcelable(ARG_PARAM2,paciente);
        fragment.setArguments(args);
        return fragment;
    }









    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idResourseString = getArguments().getInt(ARG_PARAM1);
            mPaciente = getArguments().getParcelable(ARG_PARAM2);

        }

        sharedPreferSession = new SharedPreferSession(getContext());

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view_inflada = inflater.inflate(R.layout.fragment_dialog_show_aviso, null);

        TextView aviso = (TextView) view_inflada.findViewById(R.id.txtAviso);

        aviso.setText(getResources().getString(idResourseString));

        //Aplicamos fuente
        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), CALIBRI_BOLD);
        aviso.setTypeface(myFont);

        builder.setView(view_inflada)
                // Add action buttons
                .setCancelable(false)

                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                            switch (idResourseString)
                            {
                                case R.string.aviso_eliminar_terapeuta:
                                    avisoEliminarTerapeuta();
                                    break;

                                case R.string.aviso_eliminar_paciente:

                                    avisoEliminarPaciente();
                                    break;

                                case R.string.aviso_sensor_mal_colocado:
                                    //No hacemos nada.
                                    break;

                                case R.string.aviso_reset_medicion:

                                   resetMedicionTreal();
                                    break;

                            }
                    }//fin onclick
                })

                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // LoginDialogFragment.this.getDialog().cancel();
                getDialog().cancel();

            }
        });




        return builder.create();

    }






    public void resetMedicionTreal()
    {
      DatosEnTRealFragment datosEnTRealFragment = (DatosEnTRealFragment) getActivity().getSupportFragmentManager().findFragmentByTag("fragment_datos_ent_real");
        if(datosEnTRealFragment!=null)
        {
            datosEnTRealFragment.detenerCronometro();
            datosEnTRealFragment.cronometro.reiniciar();
            datosEnTRealFragment.resetMedicion();

            DeviceControlFragment deviceControlFragment =((HomeActivity) getActivity()).controladorServiceBLE;
            if(deviceControlFragment!=null)
            {
                deviceControlFragment.gy_flexion_max_neg=0;
                deviceControlFragment.gy_flexion_max_pos=0;
                deviceControlFragment.gz_inclinacion_max_neg=0;
                deviceControlFragment.gz_inclinacion_max_pos=0;
                deviceControlFragment.gx_max_neg=0;
                deviceControlFragment.gx_max_pos=0;




            }

        }

    }




    public void avisoEliminarTerapeuta()
    {
        CrudDbHeadpod crudDbHeadpod = CrudDbHeadpod.obtenerInstancia(getContext());
        crudDbHeadpod.deleteTerapeuta(sharedPreferSession.getUserEmail());
        String mensaje = getResources().getString(R.string.success_delete_terapeuta,sharedPreferSession.getUserEmail());
        sharedPreferSession.limpiarSession();
        ((HomeActivity) getActivity()).cerrarSesion(null);
        Toast.makeText(getContext(),mensaje,Toast.LENGTH_LONG).show();

    }



    public void avisoEliminarPaciente()
    {


        //Hay que eliminar la ruta de la imagen del paciente en caso de tenerla
        if(mPaciente.getDir_imagen()!=null)
        {

            borrarImagePacienteEdit(mPaciente.getDir_imagen());
        }



        CrudDbHeadpod crudDbHeadpod = CrudDbHeadpod.obtenerInstancia(getContext());
        crudDbHeadpod.deletePaciente(mPaciente.getId());




        //Eliminamos el ultimo fragmento de la pila de transiciones y la pila auxiliar y volver al anterior fragmento.
        //************************************************************************************************************
        //Eliminamos el fragmento de la pila.
        //A este fragmento no lo estoy metiendo en la pila de transición
        ((HomeActivity) getActivity()).eliminarFragmentoDePilaTransicion("fragment_edit_paciente");
        //Elimino las dos posiciones altas de la pila y vuelvo a cargar la penultima.
        int popPilaAux = ((HomeActivity) getActivity()).pilaAuxFragments.size() - 1;

        String fragmentName;
        if(popPilaAux>=1)
        {
            fragmentName = ((HomeActivity) getActivity()).pilaAuxFragments.get(popPilaAux - 1);//fragment_mis_pacientes

            ((HomeActivity) getActivity()).pilaAuxFragments.remove(popPilaAux);//Eliminamos el ultimo
            ((HomeActivity) getActivity()).pilaAuxFragments.remove(popPilaAux - 1);//Eliminamos el penultimo
            ((HomeActivity) getActivity()).setFragment(fragmentName);//volvemos a cargar penultimo.



        }

        else
        {
            fragmentName = "fragment_home";
            ((HomeActivity) getActivity()).pilaAuxFragments.remove(popPilaAux);//Eliminamos el ultimo
            ((HomeActivity) getActivity()).setFragment(fragmentName);//volvemos a cargar penultimo.
        }

        //************************************************************************************************************

        String mensaje = getResources().getString(R.string.success_delete_paciente,mPaciente.getNombre()+" "+mPaciente.getApellido()+" "+"(id="+Long.toString(mPaciente.getId())+")");

        Toast.makeText(getContext(),mensaje,Toast.LENGTH_LONG).show();



    }


    //Para borrar la ruta de la imagen del paciente antes de eliminar

    public void borrarImagePacienteEdit(String dir_imagen)
    {
        try {
            //  /storage/emulated/0/Android/data/com.siestasystemheadpod.headpodv10/files/Pictures/a@a.com/Paciente_13-0-2017_9-14-54_TEMP.jpg

            File photoFile = new File(dir_imagen);
            boolean bool = photoFile.delete();
            if (bool) {
                Log.d("Imagen", "borrada");
            } else {
                Log.d("imagen", "no borrada");
            }

        } catch (Exception ex) {
            // Error occurred while creating the File
            Log.e("error","error borrarImagePacienteEditFragement "+ex.toString());
            Toast.makeText(getContext(),"Error borrarImagePacienteEditFragment"+ex.toString(),Toast.LENGTH_SHORT).show();
        }

    }


}