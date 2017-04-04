package com.siestasystemheadpod.headpodv10.adicionales.perfil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.SharedPreferSession;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.modelo.Terapeuta;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.siestasystemheadpod.headpodv10.HomeActivity.CALIBRI_BOLD;

/**
 * Created by Michael on 07/11/2016.
 *
 *
 */
public class DialogChangePassword extends DialogFragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String correoSession;//usuario de la sesion

    private SharedPreferSession sharedPreferSession;


    private EditText editPasswordActual;
    private EditText editPassword1;
    private EditText editPassword2;




    public DialogChangePassword() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param correoSession Parameter 1.
     * @return A new instance of fragment MedicionPlantillaPasosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogChangePassword newInstance(String correoSession) {
        DialogChangePassword fragment = new DialogChangePassword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, correoSession);
        //args.putInt(ARG_PARAM2,tipoImg);
        // args.putString(ARG_PARAM3,mcurrenPathImg);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           correoSession = getArguments().getString(ARG_PARAM1);

        }
        sharedPreferSession = new SharedPreferSession(getContext());
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        //obtenemos el correo de la session iniciada.
        correoSession = sharedPreferSession.getUserEmail();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view_inflada = inflater.inflate(R.layout.fragment_dialog_change_password, null);

        TextView titulo = (TextView) view_inflada.findViewById(R.id.titulo);
        titulo.setText(getResources().getString(R.string.cambiar_password));//para quitar el subrayado.

        editPasswordActual = (EditText) view_inflada.findViewById(R.id.password_actual);
        editPassword1 = (EditText) view_inflada.findViewById(R.id.password);
        editPassword2 = (EditText) view_inflada.findViewById(R.id.password2);

        //Aplicamos fuente
        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), CALIBRI_BOLD);
        editPassword1.setTypeface(myFont);
        editPassword2.setTypeface(myFont);
        editPasswordActual.setTypeface(myFont);
        titulo.setTypeface(myFont);



        builder.setView(view_inflada)
                // Add action buttons
                .setCancelable(false)

                .setPositiveButton(R.string.cambiar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        //Log.d("click","positive");
                        //La función onClick se la delego a setOnShowListener.
                        //de esto se encargar setOnShowListener el cual se encuentra mas abajo
                    }//fin onclick
                })

                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // LoginDialogFragment.this.getDialog().cancel();
                getDialog().cancel();

            }
        });


        //Este código es para manter el dialog pulsando el boton positvo.
         final AlertDialog dialogView = builder.create();
        dialogView.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button b = dialogView.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        if(passwordActualOk() && comprobarPasswordsNuevas(editPassword1.getText().toString(),editPassword2.getText().toString())==1)
                        {
                            //all ok
                            Terapeuta terapeuta =obtenerTerapeuta();
                            terapeuta.setPassword(md5(editPassword1.getText().toString()));
                            actualizarTerapeuta(terapeuta);
                            //Actualizamos la contraseña de la sesion actual.
                            sharedPreferSession.setUserPassword(md5(editPassword1.getText().toString()));
                            Toast.makeText(getContext(),getResources().getString(R.string.success_changed_password_perfil),Toast.LENGTH_SHORT).show();
                            dialogView.dismiss();
                        }
                        else
                        {
                            dialogView.show();
                        }

                    }
                });
            }
        });

        //return builder.create();
        return dialogView;
    }


    public boolean passwordActualOk()
    {
        boolean exito = false;
        Terapeuta terapeuta = obtenerTerapeuta();
        if(terapeuta!=null)
        {
           exito= matchPassword(md5(editPasswordActual.getText().toString()),terapeuta.getPassword());
            if(!exito) {
                editPasswordActual.setError(getResources().getString(R.string.fail_password_actual));
                editPasswordActual.requestFocus();
            }
        }
        return exito;
    }


    public Terapeuta obtenerTerapeuta()
    {
        if(correoSession!=null) {
            CrudDbHeadpod crudDbHeadpod = CrudDbHeadpod.obtenerInstancia(getContext());
            return crudDbHeadpod.getTerapeutaByEmail(correoSession);
        }
        else
        {
            return null;
        }

    }

    public void actualizarTerapeuta(Terapeuta terapeuta)
    {
        if(correoSession!=null) {
            CrudDbHeadpod crudDbHeadpod = CrudDbHeadpod.obtenerInstancia(getContext());
            crudDbHeadpod.updateTerapeuta(terapeuta, correoSession);

        }
    }

    public int comprobarPasswordsNuevas(String password, String password2)
    {


        int okpass=0;
        editPassword1.setError(null);
        editPassword2.setError(null);


        if( password.length()>0  )
        {
            if(password2.length()>0)
            {
                if(password.length()==password2.length())
                {
                    if(!matchPassword(password,password2))
                    {
                        // txtAvisoPassword2.setVisibility(View.VISIBLE);
                        editPassword2.setError(getResources().getString(R.string.password_distintas));
                        editPassword1.setError(getResources().getString(R.string.password_distintas));
                        // txtAvisoPassword2.setText(getResources().getString(R.string.password_distintas));
                        okpass=0;
                    }
                    else
                    {
                        okpass=1;
                    }
                }
                else
                {
                    editPassword2.setError(getResources().getString(R.string.password_distintas));
                    okpass=0;
                }

            }
            else
            {
                editPassword2.setError(getResources().getString(R.string.repeat_new_empty));
                editPassword2.requestFocus();

            }
        }
        else
        {
            editPassword1.setError(getResources().getString(R.string.password1_vacia));
            editPassword1.requestFocus();
            Log.d("vacia","fadsfasfasfasdfasdfas");
        }




        if(okpass==1)
        {
            editPassword1.setError(null);
            editPassword2.setError(null);
            return 1;

        }
        else
        {
            return 0;

        }

    }

    public boolean matchPassword(String p1, String p2)
    {
        return p1.equals(p2);
    }







    //codificar password
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}