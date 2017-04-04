package com.siestasystemheadpod.headpodv10.adicionales;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;

/**
 * Created by Michael on 07/11/2016.
 *
 *
 */
public class DialogImagenMuestra extends DialogFragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2="param2";
    //private static final String ARG_PARAM3 = "param3";



    // TODO: Rename and change types of parameters

    private String mCurrentImagePath;//Ruta de la imagen.


    //private int recursoImagen;

    //private int typeImg;

    //private String mCurrenPathImg;


    public DialogImagenMuestra() {
        // Required empty public constructor


    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param rutaImagen Parameter 1.
     * @return A new instance of fragment MedicionPlantillaPasosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogImagenMuestra newInstance(String rutaImagen) {
        DialogImagenMuestra fragment = new DialogImagenMuestra();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, rutaImagen);
        //args.putInt(ARG_PARAM2,tipoImg);
       // args.putString(ARG_PARAM3,mcurrenPathImg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentImagePath = getArguments().getString(ARG_PARAM1);
            //typeImg = getArguments().getInt(ARG_PARAM2);
           // mCurrenPathImg = getArguments().getString(ARG_PARAM3);
        }
    }








    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view_inflada = inflater.inflate(R.layout.fragment_dialog_show_image, null);
        ImageView imagenDialog = (ImageView) view_inflada.findViewById(R.id.imageDialog);


        ViewGroup.LayoutParams imagen_redimensionada =imagenDialog.getLayoutParams();
        imagen_redimensionada.width =  (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels());//ancho de la pantalla


       // String mCurrentPhotoPath = "/storage/emulated/0/Android/data/com.siestasystemheadpod.headpodv10/files/Pictures/usuario2/JPEG_19-12017_9624_1378840796.jpg";
        //android.resource://com.siestasystemheadpod.headpodv10/R.drawable.nombre
        //Uri path = Uri.parse("android.resource://com.siestasystemheadpod.headpodv10/" + recursoImagen);


        Uri path = Uri.parse(mCurrentImagePath);
        imagenDialog.setImageURI(path);



        /*
        switch (typeImg)
        {
            case 0://Drawable
                Drawable imagen =  ContextCompat.getDrawable(getContext(),recursoImagen);
                imagenDialog.setImageDrawable(imagen);
                break;
            case 1: //Bitmap
                Bitmap bitmap = BitmapFactory.decodeFile(mCurrenPathImg);
                //Bitmap imagenFinal = Bitmap.createScaledBitmap(bitmap, 98,133 , false);
                imagenDialog.setImageBitmap(bitmap);

                break;
        }
        */


        builder.setView(view_inflada)
                // Add action buttons
                .setCancelable(false)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...




                    }
                })
        ;


        return builder.create();
    }


}