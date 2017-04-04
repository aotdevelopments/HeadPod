package com.siestasystemheadpod.headpodv10.fragments.pacientes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.SharedPreferSession;
import com.siestasystemheadpod.headpodv10.adicionales.EfectoTxtClick;
import com.siestasystemheadpod.headpodv10.adicionales.extra_mis_pacientes.DialogDatePickerFragment;
import com.siestasystemheadpod.headpodv10.adicionales.MySpinnerAdapter;
import com.siestasystemheadpod.headpodv10.adicionales.foto.TakePicture;
import com.siestasystemheadpod.headpodv10.adicionales.foto.TakePicturePaciente;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.modelo.Paciente;
import com.siestasystemheadpod.headpodv10.data.modelo.Terapeuta;
import com.siestasystemheadpod.headpodv10.fragments.BotonPestanaFragment;

import java.util.Arrays;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnadirPacienteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnadirPacienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnadirPacienteFragment extends Fragment implements  View.OnClickListener, AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentManager fragmentManagerChild;
    public BotonPestanaFragment btn_izquierda_asignacion;
    public BotonPestanaFragment  btn_derecha_asignacion;



    private DialogDatePickerFragment fechaNacimientoDialog;



    //Recursos del layoutFragment
    //*************************************************************
    public RelativeLayout contenedor_btn_izquierda_asignacion;
    public RelativeLayout contenedor_btn_derecha_asignacion;
    public TextView txtAnos;
    public TextView txtPeso;
    public RadioButton radioHombre;
    public RadioButton radioMujer;
    public TextView txtSexo;

    private TextView txtDiagnostico;
    private ImageView btnAnadir;
    private TextView txtAnadir;
    private TextView txtDiagnostico1;
    private TextView txtDiagnostico2;


    private TextView txtAnadirBorrarImg;

    //*******Listas despegables (de momento 2)
    private Spinner spinnerTonoMuscular;
    private  Spinner spinnerControlCefalico;
    private int tonoMuscular_valueSelect =0;
    private int  controlCefalico_valueSelect =3;


    //Datos de paciente:

    public ImageView imgPaciente;
    public  EditText editNombre;
    public  EditText editApellido;
    public RadioGroup opcionSexo;

    //********Fecha de nacimiento
    public int dia_nac;
    public int mes_nac;
    public int ano_nac;
    public EditText editEdad;
    public  EditText editPeso;

    public String sexo;

    //Base de datos
    public CrudDbHeadpod crudDbHeadpod;

    //Para coger al usuario terapeuta que ha iniciado sesion
    public SharedPreferSession  sharedPreferSession;

    //para la camara
    //static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 2;
    public TakePicturePaciente takePicturePaciente;


    //*******************************************************************

    private OnFragmentInteractionListener mListener;

    public AnadirPacienteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnadirPacienteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnadirPacienteFragment newInstance(String param1, String param2) {
        AnadirPacienteFragment fragment = new AnadirPacienteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        sharedPreferSession = new SharedPreferSession(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_anadir_paciente, container, false);

        obtenerRecursosDeLayout(view);

        darFuenteTexview();

        mListener.asignarTituloToolbar(getResources().getString(R.string.nuevo_paciente));

        //anadirPestanasAsignacion();//Aquí redimencionamos tu tamaño también

       // listenerPestanasAsignacion();//Escuchamos las pestañas de asignacion

        modificarEditsDeNombreApellidoEdadPeso(); //Modificamos editText para el nombre, apellido, edad y peso

        anadir_Poblar_Listener_SpinnersDiagnostico();//Anadimos, poblamos y escuchamos los spinner de diagnostico.


        //escuchadores

        imgPaciente.setOnClickListener(this);
        txtAnadirBorrarImg.setOnClickListener(this);
        btnAnadir.setOnClickListener(this);//Escuchamos el boton añadir.

        comprobarDatosEdit();//Comprueba que todos los campos Edit no esten vacios.



        return view;
    }



    //Método que comprueba que todos los campos no esten vacios
    public void comprobarDatosEdit()
    {
        editNombre.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                showEdit(editNombre,getResources().getString(R.string.nombre_vacio));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        editApellido.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                showEdit(editApellido,getResources().getString(R.string.apellido_vacio));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        editEdad.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                showEdit(editEdad,getResources().getString(R.string.edad_vacia));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        editPeso.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                showEdit(editPeso,getResources().getString(R.string.peso_vacio));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }

    public void obtenerRecursosDeLayout(View view)
    {

        contenedor_btn_izquierda_asignacion = (RelativeLayout) view.findViewById(R.id.layout_btnIzquierdaAsignancion);
        contenedor_btn_derecha_asignacion = (RelativeLayout) view.findViewById(R.id.layout_btnDerechaAsignacion);



        imgPaciente = (ImageView) view.findViewById(R.id.imgPaciente);
        txtAnadirBorrarImg = (TextView) view.findViewById(R.id.txtAnadirBorrarImg);

        editNombre= (EditText) view.findViewById(R.id.editNombrePaciente);
        editApellido = ( EditText) view.findViewById(R.id.editApellidoPaciente);
        editEdad = (EditText) view.findViewById(R.id.editEdadPaciente);
        editPeso = (EditText) view.findViewById(R.id.editPesoPaciente);

        txtAnos = (TextView) view.findViewById(R.id.txtAnos);
        txtPeso = (TextView) view.findViewById(R.id.txtPeso);

        txtSexo = (TextView) view.findViewById(R.id.txtSexo);
        opcionSexo = (RadioGroup) view.findViewById(R.id.opciones_sexo);
        radioHombre = (RadioButton) view.findViewById(R.id.radio_masculino);
        radioMujer = (RadioButton)view.findViewById(R.id.radio_femenino);

        txtDiagnostico = (TextView) view.findViewById(R.id.txtDiagnostico);
        txtDiagnostico1 = (TextView) view.findViewById(R.id.txtControlCefalico);
        txtDiagnostico2 = (TextView) view.findViewById(R.id.txtTonoMuscular);


        //Listas despegables, de momento solo hay dos
        //***************
        spinnerControlCefalico = (Spinner) view.findViewById(R.id.spinnerControlCefalico);
        spinnerTonoMuscular = (Spinner) view.findViewById(R.id.spinnerTonoMuscular);
        //**************
        btnAnadir = (ImageView) view.findViewById(R.id.btnAnadirPaciente);
        txtAnadir = (TextView) view.findViewById(R.id.txtAnadirPaciente);

    }


    public void darFuenteTexview()
    {
        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);

        txtDiagnostico.setTypeface(myFont);
        txtDiagnostico1.setTypeface(myFont);
        txtDiagnostico2.setTypeface(myFont);
        txtAnos.setTypeface(myFont);
        txtAnadir.setTypeface(myFont);
        txtPeso.setTypeface(myFont);
        txtSexo.setTypeface(myFont);
        radioMujer.setTypeface(myFont);
        radioHombre.setTypeface(myFont);


    }
    public void anadirPestanasAsignacion()
    {

        //Colocamos el borde a los contenedores de las pestañas, inicializandolas a un estado inicial
        //*********************************************************************************************
        //Pestanas superiores
        cambiarEstadoPestana(contenedor_btn_izquierda_asignacion,R.drawable.border_btn_pestana_izqda_presionada,R.drawable.border_btn_pestana_izqda);
        cambiarEstadoPestana(contenedor_btn_derecha_asignacion,R.drawable.border_btn_pestana_dcha_presionada,R.drawable.border_btn_pestana_dcha);

        //Redimencionamos tamaños de Botones pestañas superiores de asignación
        //****************************************************************************************
        ViewGroup.LayoutParams paramBtnIzqdaArriba =contenedor_btn_izquierda_asignacion.getLayoutParams();
        paramBtnIzqdaArriba.width =  (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.50);
        paramBtnIzqdaArriba.height=  (int) (((HomeActivity) getActivity()).getTamanoPantalla().getHeightPixels()*0.07);

        ViewGroup.LayoutParams paramsBtnDchaArriba =contenedor_btn_derecha_asignacion.getLayoutParams();
        paramsBtnDchaArriba.width =  (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.50);
        paramsBtnDchaArriba.height=  (int) (((HomeActivity) getActivity()).getTamanoPantalla().getHeightPixels()*0.07);
        //****************************************************************************************

        //Añadimos los framentos a los elementos de la vista inflada: En este caso las pestañas superiores de asignación
        //*******************************************************************************************************************************************
        //Paso 1: Obtener la instancia del administrador de fragmentos
        fragmentManagerChild = getChildFragmentManager();//Se usa para los fragmentos de fragmentos.
        //getSupportMannager // Para los fragmentos de actividades.

        //Paso 2: Crear una nueva transacción

        btn_izquierda_asignacion = BotonPestanaFragment.newInstance(getResources().getString(R.string.terapeuta_sin_asignar),"");
        fragmentManagerChild.beginTransaction().replace(contenedor_btn_izquierda_asignacion.getId(),btn_izquierda_asignacion,"btn_izquierda_asignacion").commit();

        btn_derecha_asignacion = BotonPestanaFragment.newInstance(getResources().getString(R.string.paciente_sin_asignar),"");
        fragmentManagerChild.beginTransaction().replace(contenedor_btn_derecha_asignacion.getId(),btn_derecha_asignacion,"btn_derecha_asignacion").commit();
        //*******************************************************************************************************************************************


    }

    public void listenerPestanasAsignacion()
    {
        //Escuchamos los contenedores de las pestañas
        //****************************************************************************************************************************
        contenedor_btn_izquierda_asignacion.setOnClickListener(this);
        contenedor_btn_derecha_asignacion.setOnClickListener(this);
    }


    public void cambiarEstadoPestana(RelativeLayout contenedorPestana, int presionado, int normal)
    {

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[] {android.R.attr.state_pressed}, //presionado
                ContextCompat.getDrawable(getContext(),presionado));

        states.addState(new int[] { },//normal
                ContextCompat.getDrawable(getContext(),normal));
        //  states2.addState(new int[] {android.R.attr.state_focused},//focused
        //ContextCompat.getDrawable(getContext(),R.drawable.border_btn_below_pestana_activa));
        contenedorPestana.setBackground(states);

    }


    public void modificarEditsDeNombreApellidoEdadPeso()
    {
        //Modificamos el ancho de los editText para el nombre, apellido, edad y peso
        //****************************************************************************************
        ViewGroup.LayoutParams editNombreParams = editNombre.getLayoutParams();
        editNombreParams.width = (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.40);

        ViewGroup.LayoutParams editApellidoParams = editApellido.getLayoutParams();
        editApellidoParams.width = (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.40);

        ViewGroup.LayoutParams editEdadParams = editEdad.getLayoutParams();
        editEdadParams.width = (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.30);

        ViewGroup.LayoutParams editPesoParams = editPeso.getLayoutParams();
        editPesoParams.width = (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.33);
        //**********************************************************************************************

        //Para hacer a edit clicleable, es necesario: android:focusable="false",android:enabled="true
        editEdad.setOnClickListener(this);
    }


    public void anadir_Poblar_Listener_SpinnersDiagnostico()
    {
        //Poblamos los spinners disponibles:
        //*******************************************************************************************************************************************
        //Creo una clase que hereda de arrayAdapter
        MySpinnerAdapter adapter = new MySpinnerAdapter(
                getContext(),
                R.layout.spinner_text,
                Arrays.asList(getResources().getStringArray(R.array.control_cefalico))
        );
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);//simple_spinner_dropdown_item

        // Apply the adapter to the spinner
        spinnerControlCefalico.setAdapter(adapter);

        //set the default according to value
        spinnerControlCefalico.setSelection(controlCefalico_valueSelect);//,true//*****************************

        //Escuchamos el spinner

        spinnerControlCefalico.setOnItemSelectedListener(this);

       /* ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),
                R.layout.spinner_text_doble_linea,  Arrays.asList(getResources().getStringArray(R.array.tono_muscular)));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/

        adapter=new MySpinnerAdapter(
                getContext(),
                R.layout.spinner_text,
                Arrays.asList(getResources().getStringArray(R.array.tono_muscular))
        );

        // Specify the layout to use when the list of choices appears
        // adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_2);//simple_spinner_dropdown_item
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);//simple_spinner_dropdown_item

        //Aplicamos el adaptador
        spinnerTonoMuscular.setAdapter(adapter);

        //Seleccionamos valor por defecto
         spinnerTonoMuscular.setSelection(tonoMuscular_valueSelect);

        //Escuchamos el spinner
        spinnerTonoMuscular.setOnItemSelectedListener(this);
        //*******************************************************************************************************************************************
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {

            case R.id.editEdadPaciente:

                if(fechaNacimientoDialog==null)
                {


                    fechaNacimientoDialog = DialogDatePickerFragment.newInstance(1,0,1998);//el dia y mes empiezan de cero, (0=1)//OJO con escoger mes y dia 0 0  y año 2000 para inicializar. da algun problema.
                    fechaNacimientoDialog.show(getActivity().getSupportFragmentManager(), "datePicker");

                }
                else
                {
                    fechaNacimientoDialog = DialogDatePickerFragment.newInstance(fechaNacimientoDialog.getDia(), fechaNacimientoDialog.getMes(), fechaNacimientoDialog.getAno());
                    fechaNacimientoDialog.show(getActivity().getSupportFragmentManager(), "datePicker");

                }
                break;


            case R.id.imgPaciente:

                break;

            case R.id.txtAnadirBorrarImg:

                new EfectoTxtClick(getContext(),txtAnadirBorrarImg,R.animator.txt_color_efecto2,80);//efecto 2

                Log.d("FotoText","click");

                if(txtAnadirBorrarImg.getText().toString().equals(getResources().getString(R.string.borrarImagen)))
                {
                    Log.d("FotoText","borrar_imagen");
                    //borrar la imagen


                    takePicturePaciente.borrarImagePaciente(sharedPreferSession.getUserEmail());
                    txtAnadirBorrarImg.setText(addUnderline(getResources().getString(R.string.anadirImagen)));

                    Bitmap imagenOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.monigote_verde_perfil);


                    ViewGroup.LayoutParams imgPacienteLayoutParams =imgPaciente.getLayoutParams();

                    Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal, imgPacienteLayoutParams.width,imgPacienteLayoutParams.height , false);


                    imgPaciente.setImageBitmap(imagenFinal);


                    // imgPerfil.setImageResource(R.drawable.monigote_verde_perfil);
                    //Acutalizamos el header del navigationView.
                    //((HomeActivity) getActivity()).actualizarHeaderNavigationView();



                }
                else {
                    Log.d("FotoText","anadir_imagen");

                    takePicturePaciente = new TakePicturePaciente(this);//Fragment
                    takePicturePaciente.dispatchTakePictureIntentPaciente();
                }


                break;

            case R.id.layout_btnIzquierdaAsignancion:
                Log.d("click","soy pestaña izquierda superior");

                new EfectoTxtClick(getContext(),btn_izquierda_asignacion.txtBtnName,R.animator.txt_color_efecto,80);

                break;

            case R.id.layout_btnDerechaAsignacion:

                Log.d("click","soy pestaña derecha superior");

                new EfectoTxtClick(getContext(),btn_derecha_asignacion.txtBtnName,R.animator.txt_color_efecto,80);


                break;

            case R.id.btnAnadirPaciente:

                new EfectoTxtClick(getContext(),txtAnadir,R.animator.txt_color_efecto,80);

                if (comprobarDatos()==1)
                {

                    if(fechaNacimientoDialog!=null && fechaNacimientoDialog.datos_ok)
                    {
                        //Log.d("edad_final_final",String.valueOf(fechaNacimientoDialog.getDialog2().getEdad()));
                        //Log.d("dia_final_final",String.valueOf(fechaNacimientoDialog.getDialog2().getDia()));
                        //Log.d("mes_final_final",String.valueOf(fechaNacimientoDialog.getDialog2().getMes()));
                        //Log.d("ano_final_final",String.valueOf(fechaNacimientoDialog.getDialog2().getAno()));

                        mes_nac=fechaNacimientoDialog.getDialog2().getMes()+1;//Sumamos 1 porque empieza desde cero.
                        dia_nac=fechaNacimientoDialog.getDialog2().getDia();
                        ano_nac=fechaNacimientoDialog.getDialog2().getAno();

                        if (radioHombre.isChecked()){
                            //Log.d("sexo", "soy hombre");
                            sexo="h";

                        }

                        if (radioMujer.isChecked()){
                           // Log.d("sexo", "soy mujer");
                            sexo="m";
                        }
                        //Log.d("spinner1",spinnerControlCefalico.getItemAtPosition(controlCefalico_valueSelect).toString());
                        //Log.d("spinner2",spinnerTonoMuscular.getItemAtPosition(tonoMuscular_valueSelect).toString());

                        Paciente paciente = new Paciente(editNombre.getText().toString(),
                                editApellido.getText().toString(),
                                dia_nac,
                                mes_nac,
                                ano_nac,
                                Float.valueOf(editPeso.getText().toString()),
                                sexo,
                                spinnerControlCefalico.getItemAtPosition(controlCefalico_valueSelect).toString(),
                                spinnerTonoMuscular.getItemAtPosition(tonoMuscular_valueSelect).toString(),0);


                        //añadimos la ruta al paciente.
                        if(takePicturePaciente!=null && takePicturePaciente.getmCurrentPhotoPath()!=null)
                        {
                            paciente.setDir_imagen(takePicturePaciente.getmCurrentPhotoPath());
                        }

                        crudDbHeadpod=CrudDbHeadpod.obtenerInstancia(getContext());

                        //creamos el paciente y lo relacionamos.
                        if(crudDbHeadpod.relacionPacienteConTerapeuta(paciente,sharedPreferSession.getUserEmail())>0)
                        {

                            Toast.makeText(getContext(),getString(R.string.paciente_success),Toast.LENGTH_LONG).show();

                            //Eliminamos el ultimo fragmento de la pila de transiciones y la pila auxiliar y volver al anterior fragmento.
                            //************************************************************************************************************
                            //Eliminamos el fragmento de la pila.
                            ((HomeActivity) getActivity()).eliminarFragmentoDePilaTransicion("fragment_anadir_paciente");

                            //Elimino las dos posiciones altas de la pila y vuelvo a cargar la penultima.
                            int popPilaAux = ((HomeActivity) getActivity()).pilaAuxFragments.size() - 1;

                            String fragmentName;
                            if(popPilaAux>=1)
                            {
                                fragmentName = ((HomeActivity) getActivity()).pilaAuxFragments.get(popPilaAux - 1);//fragment_mis_pacientes

                                ((HomeActivity) getActivity()).pilaAuxFragments.remove(popPilaAux);//Eliminamos el ultimo
                                ((HomeActivity) getActivity()).pilaAuxFragments.remove(popPilaAux - 1);//Eliminamos el penultimo
                                mListener.onFragmentInteraction(fragmentName);//volvemos a cargar penultimo.

                            }

                            else
                            {
                                fragmentName = "fragment_home";
                                ((HomeActivity) getActivity()).pilaAuxFragments.remove(popPilaAux);//Eliminamos el ultimo
                                mListener.onFragmentInteraction(fragmentName);//volvemos a cargar penultimo.
                            }

                        }

                    }
                }
                else
                {
                    Toast.makeText(getContext(),getString(R.string.paciente_fail),Toast.LENGTH_LONG).show();
                }

                break;
        }


    }//fin onClick

    public int comprobarDatos()
    {
        int valido=0;

        int ok1=0;
        int ok2=0;
        int ok3=0;
        int ok4=0;


        if(isEditEmpty(editEdad))
        {
            //Toast.makeText(getContext(),getResources().getString(R.string.nombre_vacio),Toast.LENGTH_LONG).show();
            editEdad.setError(getResources().getString(R.string.edad_vacia));
            editEdad.requestFocus();
        }
        else
        {
        ok1=1;

        }
        if(isEditEmpty(editPeso))
        {
            //Toast.makeText(getContext(),getResources().getString(R.string.nombre_vacio),Toast.LENGTH_LONG).show();
            editPeso.setError(getResources().getString(R.string.peso_vacio));
            editPeso.requestFocus();
        }
        else
        {
            ok2=1;
        }


        if(isEditEmpty(editApellido))
        {
            //Toast.makeText(getContext(),getResources().getString(R.string.nombre_vacio),Toast.LENGTH_LONG).show();
            editApellido.setError(getResources().getString(R.string.apellido_vacio));
            editApellido.requestFocus();
        }
        else
        {
            ok3=1;
        }


        if(isEditEmpty(editNombre))
        {
            //Toast.makeText(getContext(),getResources().getString(R.string.nombre_vacio),Toast.LENGTH_LONG).show();
            editNombre.setError(getResources().getString(R.string.nombre_vacio));
            editNombre.requestFocus();
        }
        else
        {
            ok4=1;
        }


        if(ok1+ok2+ok3+ok4==4)
        {
            valido=1;
        }

        Log.d("comprobar datos",String.valueOf(valido));
        return valido;
    }


    public boolean isEditEmpty(EditText edit)
    {
            return  edit.length()==0;
    }


    public void showEdit(EditText edit ,String mensaje)
    {
        if(isEditEmpty(edit))
        {
            //Toast.makeText(getContext(),getResources().getString(R.string.nombre_vacio),Toast.LENGTH_LONG).show();
            edit.setError(mensaje);
            edit.requestFocus();
        }
        else
        {
            edit.setError(null);
            edit.requestFocus();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using



        //view.setBackgroundColor(getResources().getColor(R.color.rojo_ocuro));
        ///parent.setBackgroundColor(getResources().getColor(R.color.rojo_ocuro));
        //String select = (String) parent.getItemAtPosition(pos);


        switch (parent.getId())
        {
            case R.id.spinnerControlCefalico:
                controlCefalico_valueSelect=pos;
                Log.d("valor: ",parent.getItemAtPosition(pos).toString());
                break;
            case R.id.spinnerTonoMuscular:
                tonoMuscular_valueSelect=pos;
                Log.d("valor: ",parent.getItemAtPosition(pos).toString());
                break;
        }


        //asi obtengo el valor seleccionado:
        // Toast.makeText(getContext(),"Selecionado: "+  parent.getItemAtPosition(pos),Toast.LENGTH_LONG).show();
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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
        Bitmap bitmap = BitmapFactory.decodeFile(takePicturePaciente.getmCurrentPhotoPath());

        //String hhh= takePicture.decodeFile(takePicture.getmCurrentPhotoPath(),256,512);

        //reduce la calidad de la imagen
        //takePicturePaciente.decodeFile(takePicturePaciente.getmCurrentPhotoPath(),256,512);


        //alamacenamos la ruta de la imagen, actualizando la BD
       /* Terapeuta terapeuta = crudDbHeadpod.getTerapeutaByEmail(sharedPreferSession.getUserEmail());
        if(terapeuta!=null) {

            terapeuta.setRuta_imagen(takePicture.getmCurrentPhotoPath());

            Log.d("foto","cambio ruta "+terapeuta.getRuta_imagen());

            crudDbHeadpod.updateTerapeuta(terapeuta, sharedPreferSession.getUserEmail());

        }*/


        ViewGroup.LayoutParams paramImgPaciente =imgPaciente.getLayoutParams();

        Bitmap imagenFinal = Bitmap.createScaledBitmap(bitmap, paramImgPaciente.width,paramImgPaciente.height , false);
        imgPaciente.setImageBitmap(imagenFinal);

        //actualizamos txt borrar imagen.
        txtAnadirBorrarImg.setText(addUnderline(getResources().getString(R.string.borrarImagen)));

        //Acutalizamos el header del navigationView. //Actualizar la lista de pacientes
       // ((HomeActivity) getActivity()).actualizarHeaderNavigationView();


        //Cambiamos el texto y ponemos el subrayado
        //anadir_borrarImagen.setText(addUnderline(getResources().getString(R.string.borrarImagen)));


    }


    //Método para añadir subrayado
    private SpannableString addUnderline(String texto)
    {
        //Añadimos el texto el cual tendra subryado
        SpannableString content = new SpannableString(texto);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;

    }



    public void colocarImgPaciente()
    {


/*
        if(terapeuta!=null && terapeuta.getRuta_imagen()!=null)
        {
            Uri path = Uri.parse(terapeuta.getRuta_imagen());

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap imagenFinal = BitmapFactory.decodeFile(path.toString(),bmOptions);

            imagenFinal = Bitmap.createScaledBitmap(imagenFinal, widthImg,heightImg , false);

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
            Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal, widthImg,heightImg , false);

            // ViewGroup.LayoutParams imgPerfilLayoutParams =imgPerfil.getLayoutParams();

            //imgPerfilLayoutParams.height=heightImg;
            //imgPerfilLayoutParams.width=widthImg;


            imgPerfil.setImageBitmap(imagenFinal);
            anadir_borrarImagen.setText(addUnderline(getResources().getString(R.string.anadirImagen)));
        }

        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, getResources().getDisplayMetrics());
*/

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
        void asignarTituloToolbar(String titulo);
    }
}



/*


        discreteSeekBarPeso.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(final DiscreteSeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(final DiscreteSeekBar seekBar) {

                editPeso.setText(String.valueOf(seekBar.getProgress()));
            }//fin stop

        });



        editPeso.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub


            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Log.d("valores", "letras= "+String.valueOf(s.length()));

                if (s.length() > 0 && s.length()<3) {

                    String valor = editPeso.getText().toString();
                    discreteSeekBarPeso.setProgress(Integer.valueOf(valor));
                    // ediTxtTime.setText(getResources().getString(R.string.indefinido));
                }
                if (s.length()>3)
                {
                    Toast.makeText(getContext(),"Nadie pesa tanto",Toast.LENGTH_SHORT).show();
                }


            }


        });


 */