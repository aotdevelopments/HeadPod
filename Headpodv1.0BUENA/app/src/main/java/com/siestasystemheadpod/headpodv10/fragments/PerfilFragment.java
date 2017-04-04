package com.siestasystemheadpod.headpodv10.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.IntroActivity;
import com.siestasystemheadpod.headpodv10.MainActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.SharedPreferSession;
import com.siestasystemheadpod.headpodv10.adicionales.DialogAvisos;
import com.siestasystemheadpod.headpodv10.adicionales.EfectoTxtClick;
import com.siestasystemheadpod.headpodv10.adicionales.perfil.DialogChangePassword;
import com.siestasystemheadpod.headpodv10.adicionales.DialogImagenMuestra;
import com.siestasystemheadpod.headpodv10.adicionales.MySpinnerAdapter;
import com.siestasystemheadpod.headpodv10.adicionales.foto.TakePicture;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.modelo.Terapeuta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.id;
import static android.app.Activity.RESULT_OK;
import static com.siestasystemheadpod.headpodv10.R.id.contenedor;
import static com.siestasystemheadpod.headpodv10.R.id.timeSession;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PerfilFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    public EditText editAlias;
    public TextView txtPreferencias;

    public int pos;

    public TextView txtSesiones;
    public ImageView btnGuardar;


    private TextView txtCambiarPassword;
    private TextView txtEliminarCuenta;



    public ImageView imgPerfil;
    public TextView anadir_borrarImagen;

    //para la camara
    //static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 2;
    private TakePicture takePicture;


    //Clase para acceder a la base de datos
    private CrudDbHeadpod crudDbHeadpod;
    //Clase para acceder a las credenciales de sesion.
    private SharedPreferSession sharedPreferSession;
    //atributo que nos chica si los cambios al guardar han tenido exito.
    private boolean exitoGuardar;


    private int tiempoSeleccionado;


    private FragmentActivity fragmentActivity;

    private static  Context context;



    private int heightImg;
    private int widthImg;

    private Terapeuta terapeuta;


    //Para la configuración de la orientación. Con esto respetamos el diseño landScape
    private FrameLayout frameLayout;



    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


        //String storageDir =(Environment.DIRECTORY_PICTURES+"/usuario2");//Pictures/usuario2
        //String storeFile=getActivity().getFilesDir().getAbsolutePath();///data/data/com.siestasystemheadpod.headpodv10/files
        //Log.d("ruta1",storageDir);
        //Log.d("ruta2",storeFile);
        crudDbHeadpod = CrudDbHeadpod.obtenerInstancia(getContext());
        sharedPreferSession= new SharedPreferSession(getContext());

        fragmentActivity=getActivity();


    }




    //http://stackoverflow.com/questions/17116602/using-onconfigurationchanged-in-a-fragment
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        ((HomeActivity)getActivity()).cambiarIdioma();

        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_perfil, null);

        setContenidoVista(view);

        frameLayout .addView(view);
    }



    /*public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Guardar Cambios")
                .setMessage("Los cambios se han guardado correctamente")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                .onPossitiveButtonClick();
                            }
                        });

        return builder.create();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        frameLayout = new FrameLayout(getActivity());
       // LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // Inflate the layout for this fragment
        //Para evitar que el fragmento muestre el teclado en el inpuit nada mas cargar
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ((HomeActivity) getActivity()).setFragmentActual(this);
        //((HomeActivity) getActivity()).fragmentActual=this;

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);


        setContenidoVista(view);
        Button guardar = (Button) view.findViewById(R.id.botonGuardar);



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                //Para cambiar de actividad mediante la pulsacion del boton
                //Intent volver = new Intent(getActivity(), PerfilFragment.class);
                //getActivity().startActivity(volver);
                dialog.setTitle("¡Actualizado!");
                dialog.setMessage("Los cambios se han guardado correctamente");
                dialog.show();


                /*Spinner spinner = (Spinner) view.findViewById(R.id.timeSession);
                pos = spinner.getSelectedItemPosition();
                Intent i = new Intent (getActivity(), DatosEnTRealFragment.class);
                if (pos == 0){ //15mins
                    i.putExtra("tiempo", 900);
                }else if (pos == 1){ //30mins
                    i.putExtra("tiempo", 1800);
                }else if (pos == 2){  //45mins
                    i.putExtra("tiempo", 2700);
                }else if (pos == 3){  //60mins
                    i.putExtra("tiempo", 3600);
                }else if (pos == 4){  //120mins
                    i.putExtra("tiempo", 7200);
                }*/

            }

        });

        frameLayout .addView(view);
        //return view;
        return frameLayout;

    }

    /*public int getTiempo(){
        return pos;
    }*/

    public void setContenidoVista(View view)

    {

        //((HomeActivity) getActivity()).getSupportActionBar().setTitle(R.string.titulo_perfil_terapeuta);
        TextView titulo = (TextView) getActivity().findViewById(R.id.toolbar_title);


        imgPerfil = (ImageView) view.findViewById(R.id.imgPerfil);
        anadir_borrarImagen = (TextView) view.findViewById(R.id.txtAnadirBorrarImg);
        txtCambiarPassword = (TextView) view.findViewById(R.id.txtCambiarPassword);
        txtEliminarCuenta = (TextView) view.findViewById(R.id.txtEliminarCuenta);
        editAlias = (EditText) view.findViewById(R.id.editAlias);
        txtPreferencias = (TextView) view.findViewById(R.id.txtPreferencias);
        txtSesiones = (TextView) view.findViewById(R.id.txtSesiones);
        Spinner spinner = (Spinner) view.findViewById(R.id.timeSession);


        terapeuta = crudDbHeadpod.getTerapeutaByEmail(sharedPreferSession.getUserEmail());
        ///storage/emulated/0/Android/data/com.siestasystemheadpod.headpodv10/files/Pictures/comercial@kinesiopod.com/JPEG_PERFIL.jpg


        // obtenerTamanoImgPerfil();
        colocarImgPerfil();




        imgPerfil.setOnClickListener(this);
        anadir_borrarImagen.setOnClickListener(this);

        txtCambiarPassword.setOnClickListener(this);
        txtEliminarCuenta.setOnClickListener(this);




        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);



        //View view3 = inflater.inflate(R.layout.simple_spinner_dropdown, container, false);

        // CheckedTextView spinnerSelect = (CheckedTextView) view3.findViewById(R.id.spinner_select);

        titulo.setTypeface(myFont);
        editAlias.setTypeface(myFont);
        anadir_borrarImagen.setTypeface(myFont);
        txtCambiarPassword.setTypeface(myFont);
        txtEliminarCuenta.setTypeface(myFont);

        txtPreferencias.setTypeface(myFont);
        txtSesiones.setTypeface(myFont);

        //obtenemos el alias
        obtenerAlias();


        //Creo una clase que hereda de arrayAdapter
        MySpinnerAdapter adapter = new MySpinnerAdapter(
                getContext(),
                R.layout.spinner_text,
                Arrays.asList(getResources().getStringArray(R.array.time_session))
        );
        // spinner.setAdapter(adapter);


        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);//simple_spinner_dropdown_item
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//simple_spinner_dropdown_item

        // Apply the adapter to the spinner

        spinner.setAdapter(adapter);
        //Otra forma de hacerlo:
        //String[] years = {"1996","1997","1998","1998"};
        //ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.spinner_text, years  );
        //langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        //spinner.setAdapter(langAdapter);

        //set the default according to value
        //spinner.setSelection(3);//,true

        spinnerSetSelectionbyBD(terapeuta,spinner);

        spinner.setOnItemSelectedListener(this);


        titulo.setText(R.string.titulo_perfil_terapeuta);




        editAlias.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here
                guardarAlias();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });




    }


    public void guardarAlias()
    {
        Terapeuta t =crudDbHeadpod.getTerapeutaByEmail(sharedPreferSession.getUserEmail());;
        if( editAlias.length()==0)
        {
            //Toast.makeText(getContext(),"guardar info alias"+alias,Toast.LENGTH_SHORT).show();
            if(t!=null)
            {
                t.setAlias(getResources().getString(R.string.sin_alias));
                //t.setTiempoSession((float) tiempoSeleccionado);
                crudDbHeadpod.updateTerapeuta(t,sharedPreferSession.getUserEmail());
                exitoGuardar=true;
            }
        }
        else
        {
            if(t!=null)
            {
                t.setAlias(editAlias.getText().toString());
                //t.setTiempoSession((float) tiempoSeleccionado);
                crudDbHeadpod.updateTerapeuta(t,sharedPreferSession.getUserEmail());
                exitoGuardar=true;
            }


        }
        if(exitoGuardar)
        {
            //Actualizamos header
            ((HomeActivity) getActivity()).actualizarHeaderNavigationView();

            //Toast.makeText(getContext(),getResources().getString(R.string.exito_guardar),Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Toast.makeText(getContext(),getResources().getString(R.string.fail_guardar),Toast.LENGTH_SHORT).show();
        }
    }


    public void guardarTiempoSession()
    {


        Terapeuta t =crudDbHeadpod.getTerapeutaByEmail(sharedPreferSession.getUserEmail());;
        if( editAlias.length()==0)
        {
            //Toast.makeText(getContext(),"guardar info alias"+alias,Toast.LENGTH_SHORT).show();
            if(t!=null)
            {
                //t.setAlias(getResources().getString(R.string.sin_alias));
                t.setTiempoSession((float) tiempoSeleccionado);
                crudDbHeadpod.updateTerapeuta(t,sharedPreferSession.getUserEmail());
                exitoGuardar=true;
            }
        }
        else
        {
            if(t!=null)
            {
                //t.setAlias(editAlias.getText().toString());
                t.setTiempoSession((float) tiempoSeleccionado);
                crudDbHeadpod.updateTerapeuta(t,sharedPreferSession.getUserEmail());
                exitoGuardar=true;
            }


        }
        if(exitoGuardar)
        {
            //Actualizamos header
            ((HomeActivity) getActivity()).actualizarHeaderNavigationView();

            //Toast.makeText(getContext(),getResources().getString(R.string.exito_guardar),Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Toast.makeText(getContext(),getResources().getString(R.string.fail_guardar),Toast.LENGTH_SHORT).show();
        }


    }




    public void colocarImgPerfil()
    {



        if(terapeuta!=null && terapeuta.getRuta_imagen()!=null)
        {
            Uri path = Uri.parse(terapeuta.getRuta_imagen());

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap imagenFinal = BitmapFactory.decodeFile(path.toString(),bmOptions);

           // imagenFinal = Bitmap.createScaledBitmap(imagenFinal, widthImg,heightImg , false);

            //creamos el drawable redondeado
           // RoundedBitmapDrawable roundedDrawable =
            //        RoundedBitmapDrawableFactory.create(getResources(), imagenFinal);
            //asignamos el CornerRadius
           // roundedDrawable.setCornerRadius(imagenFinal.getHeight());

            //imgPerfil.setImageDrawable(roundedDrawable);

            imgPerfil.setImageBitmap(imagenFinal);



            //imgPerfil.setImageURI(path);
            anadir_borrarImagen.setText(addUnderline(getResources().getString(R.string.borrarImagen)));

        }
        else
        {
            Bitmap imagenOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.monigote_verde_perfil);
           // Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal, widthImg,heightImg , false);

           // ViewGroup.LayoutParams imgPerfilLayoutParams =imgPerfil.getLayoutParams();

            //imgPerfilLayoutParams.height=heightImg;
            //imgPerfilLayoutParams.width=widthImg;


            imgPerfil.setImageBitmap(imagenOriginal);
            anadir_borrarImagen.setText(addUnderline(getResources().getString(R.string.anadirImagen)));
        }

       // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, getResources().getDisplayMetrics());


    }

    public void obtenerTamanoImgPerfil()
    {

        double pulgadas =((HomeActivity)getActivity()).getTamanoPantalla().getScreenInchesPulgadas();


        //94
        //127


        if(pulgadas<4.5)
        {
            widthImg=141;
            heightImg=191;
        }

        if(pulgadas>=4.5 && pulgadas<7)
        {
            widthImg=187;
            heightImg=254;
        }


        if(pulgadas>=7 && pulgadas <=8)
        {
            widthImg=235;
            heightImg=318;
        }

        if(pulgadas>8)
        {
            widthImg=282;
            widthImg=381;
        }



    }





    //Metodo para seleccionar el valor del spinner desde la base de datos.
    public void spinnerSetSelectionbyBD(Terapeuta terapeuta, Spinner spinner)
    {

        if(terapeuta!=null) {

            List<String> lista = Arrays.asList(getResources().getStringArray(R.array.time_session));
            //lista.get(0);
            int pos=0;
            for (String item: lista )
            {
                if(item.contains(String.valueOf((int) terapeuta.getTiempoSession())))
                {
                    spinner.setSelection(pos);
                }
                pos++;

            }

        }

    }

    public void setTiempoSession(int tSession)
    {
        Terapeuta terapeuta = crudDbHeadpod.getTerapeutaByEmail(sharedPreferSession.getUserEmail());

        if(terapeuta!=null)
        {
            terapeuta.setTiempoSession((float) tSession);
            crudDbHeadpod.updateTerapeuta(terapeuta,sharedPreferSession.getUserEmail());
        }

    }

    public void obtenerAlias()
    {
        Cursor consulta = crudDbHeadpod.getTerapeuta(sharedPreferSession.getUserEmail());
        if(consulta.getCount()>0 ) {

            consulta.moveToFirst();
            //while(!consulta.isAfterLast()) {
            String alias = consulta.getString(consulta.getColumnIndex("alias"));
            if(!alias.equals(getResources().getString(R.string.sin_alias)))
                editAlias.setText(alias);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        //view.setBackgroundColor(getResources().getColor(R.color.rojo_ocuro));
        ///parent.setBackgroundColor(getResources().getColor(R.color.rojo_ocuro));
        //String select = (String) parent.getItemAtPosition(pos);

        //asi obtengo el valor seleccionado:
        //Toast.makeText(getContext(),"Selecionado: "+  parent.getItemAtPosition(pos),Toast.LENGTH_LONG).show();
        String valor = parent.getItemAtPosition(pos).toString();
        String result[] = valor.split(" ");
        //Log.d("corte",result[0]);//x
        //Log.d("corte2",result[1]);//minutos
        tiempoSeleccionado= Integer.valueOf(result[0]);
        guardarTiempoSession();


    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onClick(View v) {//btn guardar


        switch (v.getId())
        {
            case R.id.imgPerfil:

                Terapeuta terapeuta = crudDbHeadpod.getTerapeutaByEmail(sharedPreferSession.getUserEmail());

                if(terapeuta!=null && terapeuta.getRuta_imagen()!=null)
                {
                    DialogImagenMuestra imagenMuestra = DialogImagenMuestra.newInstance(terapeuta.getRuta_imagen());
                    imagenMuestra.show(getActivity().getSupportFragmentManager(),"showDialogImagen");
                }
                else
                {
                    String ruta="android.resource://com.siestasystemheadpod.headpodv10/" + R.drawable.monigote_verde_perfil;
                    DialogImagenMuestra imagenMuestra = DialogImagenMuestra.newInstance(ruta);
                    imagenMuestra.show(getActivity().getSupportFragmentManager(),"showDialogImagen");
                }

                break;
            case R.id.txtAnadirBorrarImg:
                new EfectoTxtClick(getContext(), anadir_borrarImagen,R.animator.txt_color_efecto2,80);//efecto 2

                Log.d("FotoText","click");

                if(anadir_borrarImagen.getText().toString().equals(getResources().getString(R.string.borrarImagen)))
                {
                    Log.d("FotoText","borrar_imagen");
                    //borrar la imagen

                        takePicture = new TakePicture(this);
                        terapeuta = crudDbHeadpod.getTerapeutaByEmail(sharedPreferSession.getUserEmail());
                        if(terapeuta!=null) {

                            takePicture.borrarImagePerfilTerapeuta(terapeuta.getRuta_imagen());
                            anadir_borrarImagen.setText(addUnderline(getResources().getString(R.string.anadirImagen)));

                            terapeuta.setRuta_imagen(null);
                            crudDbHeadpod.updateTerapeuta(terapeuta,sharedPreferSession.getUserEmail());
                        }


                    Bitmap imagenOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.monigote_verde_perfil);
                   // Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal, widthImg,heightImg , false);

                    // ViewGroup.LayoutParams imgPerfilLayoutParams =imgPerfil.getLayoutParams();

                    //imgPerfilLayoutParams.height=heightImg;
                    //imgPerfilLayoutParams.width=widthImg;


                    imgPerfil.setImageBitmap(imagenOriginal);


                       // imgPerfil.setImageResource(R.drawable.monigote_verde_perfil);
                    //Acutalizamos el header del navigationView.
                    ((HomeActivity) getActivity()).actualizarHeaderNavigationView();


                }
                else {
                    Log.d("FotoText","anadir_imagen");

                    takePicture = new TakePicture(this);//Fragment
                    takePicture.dispatchTakePictureIntentPerfilTerapeuta();
                }
                break;

            case R.id.txtCambiarPassword:
                new EfectoTxtClick(getContext(), txtCambiarPassword,R.animator.txt_color_efecto2,80);
                //DialogChangePassword dialogChangePassword = DialogChangePassword.newInstance(sharedPreferSession.getUserEmail());
                DialogChangePassword dialogChangePassword = new DialogChangePassword();
                dialogChangePassword.show(getActivity().getSupportFragmentManager(),"changePassword");

                break;

            case R.id.txtEliminarCuenta:

                new EfectoTxtClick(getContext(), txtEliminarCuenta,R.animator.txt_color_efecto2,80);

                DialogAvisos dialogAvisos = DialogAvisos.newInstance(R.string.aviso_eliminar_terapeuta);
                dialogAvisos.show(getActivity().getSupportFragmentManager(),"aviso");

                break;
        }
    }


    //Esto es lo que me devuelve StartActiviyForResult de la clase TakePicture
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

       // Log.d("onActivityResult","requestttttt PErfil Fragment");
        //resultCode != RESULT_CANCELED
        //if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        //    Bundle extras = data.getExtras();
        //    Bitmap imageBitmap = (Bitmap) extras.get("data");
        //    imgPerfil.setImageBitmap(imageBitmap);
        //}
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic();
        }

    }//fin onActivityResult.

    private void setPic() {

        //Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        Bitmap bitmap = BitmapFactory.decodeFile(takePicture.getmCurrentPhotoPath());

        //String hhh= takePicture.decodeFile(takePicture.getmCurrentPhotoPath(),256,512);

        //reduce la calidad de la imagen
        //********************************************************************************************************************
        //********************************************************************************************************************
        //takePicture.decodeFile(takePicture.getmCurrentPhotoPath(),256,512,"JPEG_PERFIL.jpg");


        //alamacenamos la ruta de la imagen, actualizando la BD
        Terapeuta terapeuta = crudDbHeadpod.getTerapeutaByEmail(sharedPreferSession.getUserEmail());
        if(terapeuta!=null) {

            terapeuta.setRuta_imagen(takePicture.getmCurrentPhotoPath());

            Log.d("foto","cambio ruta "+terapeuta.getRuta_imagen());

            crudDbHeadpod.updateTerapeuta(terapeuta, sharedPreferSession.getUserEmail());

        }


        //Bitmap imagenFinal = Bitmap.createScaledBitmap(bitmap, widthImg,heightImg , false);
        imgPerfil.setImageBitmap(bitmap);


        //Acutalizamos el header del navigationView.
        ((HomeActivity) getActivity()).actualizarHeaderNavigationView();


        //Cambiamos el texto y ponemos el subrayado
        anadir_borrarImagen.setText(addUnderline(getResources().getString(R.string.borrarImagen)));


    }


    //Método para añadir subrayado
    private SpannableString addUnderline(String texto)
    {
        //Añadimos el texto el cual tendra subryado
        SpannableString content = new SpannableString(texto);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;

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
}
