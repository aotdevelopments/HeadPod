package com.siestasystemheadpod.headpodv10.fragments.pacientes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.SharedPreferSession;
import com.siestasystemheadpod.headpodv10.adicionales.DialogAvisos;
import com.siestasystemheadpod.headpodv10.adicionales.EfectoTxtClick;
import com.siestasystemheadpod.headpodv10.adicionales.MySpinnerAdapter;
import com.siestasystemheadpod.headpodv10.adicionales.extra_mis_pacientes.DialogDatePickerFragment;
import com.siestasystemheadpod.headpodv10.adicionales.foto.TakePicturePaciente;
import com.siestasystemheadpod.headpodv10.adicionales.foto.TakePicturePacienteEdit;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.modelo.Paciente;
import com.siestasystemheadpod.headpodv10.fragments.BotonBelowPestanaFragment;
import com.siestasystemheadpod.headpodv10.fragments.BotonPestanaFragment;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PacienteEditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PacienteEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PacienteEditFragment extends Fragment implements  View.OnClickListener, AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "paciente";



    // TODO: Rename and change types of parameters

    private Paciente mPaciente;








    public FragmentManager fragmentManagerChild;
    public BotonBelowPestanaFragment btn_pestana_izquierda;
    public BotonBelowPestanaFragment  btn_pestana_derecha;



    private DialogDatePickerFragment fechaNacimientoDialog;



    //Recursos del layoutFragment
    //*************************************************************


    public RelativeLayout contenedor_btn_pestana_izquierda;
    public RelativeLayout contenedor_btn_pestana_derecha;


    public TextView txtAnos;
    public TextView txtPeso;
    public RadioButton radioHombre;
    public RadioButton radioMujer;
    public TextView txtSexo;

    private TextView txtDiagnostico;
    private ImageView btnModificar;
    private TextView txtModificar;

    private ImageView btnEliminar;
    private TextView txtEliminar;

    private TextView txtDiagnostico1;
    private TextView txtDiagnostico2;


    private TextView txtAnadirBorrarImg;

    //*******Listas despegables (de momento 2)
    private Spinner spinnerTonoMuscular;
    private  Spinner spinnerControlCefalico;
    private int tonoMuscular_valueSelect;
    private int  controlCefalico_valueSelect;


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
    public TakePicturePacienteEdit takePictruePacienteEdit;


    //*******************************************************************

    private OnFragmentInteractionListener mListener;

    //Para la configuración de la orientación. Con esto respetamos el diseño landScape
    private FrameLayout frameLayout;

    public PacienteEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param paciente Parameter 1.
     * @return A new instance of fragment AnadirPacienteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PacienteEditFragment newInstance(Paciente paciente) {
        PacienteEditFragment fragment = new PacienteEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,paciente );
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPaciente = getArguments().getParcelable(ARG_PARAM1);

        }

        sharedPreferSession = new SharedPreferSession(getContext());

        //Para la base de datos.
        crudDbHeadpod=CrudDbHeadpod.obtenerInstancia(getContext());

    }




    //http://stackoverflow.com/questions/17116602/using-onconfigurationchanged-in-a-fragment
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        ((HomeActivity)getActivity()).cambiarIdioma();
        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_paciente_edit, null);

        setContenidoVista(view);

        frameLayout .addView(view);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            frameLayout = new FrameLayout(getActivity());
            // LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            // Inflate the layout for this fragment

            View view= inflater.inflate(R.layout.fragment_paciente_edit, container, false);

            setContenidoVista(view);


            frameLayout .addView(view);
            //return view;
            return frameLayout;








    }


    public void setContenidoVista(View view)
    {

        obtenerRecursosDeLayout(view);

        darFuenteTexview();

        mListener.asignarTituloToolbar(getResources().getString(R.string.ficha));

        anadirPestanasPaciente();//Aquí redimencionamos tu tamaño también



        listenerPestanasPaciente();//Escuchamos las pestañas del paciente.

        modificarEditsDeNombreApellidoEdadPeso(); //Modificamos editText para el nombre, apellido, edad y peso

        anadir_Poblar_Listener_SpinnersDiagnostico();//Anadimos, poblamos y escuchamos los spinner de diagnostico.


        //Se coloca despues de poblar los spinners.
        darValoresPaciente();


        //colocar imagen si se dispone de ella

        colocarImgPacienteCreateView();

        //escuchadores

        imgPaciente.setOnClickListener(this);
        txtAnadirBorrarImg.setOnClickListener(this);
        btnModificar.setOnClickListener(this);//Escuchamos el boton añadir.
        btnEliminar.setOnClickListener(this);

        comprobarDatosEdit();//Comprueba que todos los campos Edit no esten vacios.


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

        contenedor_btn_pestana_izquierda = (RelativeLayout) view.findViewById(R.id.relativeLayout_btnPestanaIzquierda);
        contenedor_btn_pestana_derecha = (RelativeLayout) view.findViewById(R.id.relativeLayout_btnPestanaDerecha);

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
        btnModificar = (ImageView) view.findViewById(R.id.btnModificarPaciente);
        txtModificar = (TextView) view.findViewById(R.id.txtModificarPaciente);

        btnEliminar = (ImageView) view.findViewById(R.id.btnEliminarPaciente);
        txtEliminar = (TextView) view.findViewById(R.id.txtEliminarPaciente);

    }


    public void darFuenteTexview()
    {
        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);

        txtDiagnostico.setTypeface(myFont);
        txtDiagnostico1.setTypeface(myFont);
        txtDiagnostico2.setTypeface(myFont);
        txtAnos.setTypeface(myFont);
        txtModificar.setTypeface(myFont);
        txtEliminar.setTypeface(myFont);
        txtPeso.setTypeface(myFont);
        txtSexo.setTypeface(myFont);
        radioMujer.setTypeface(myFont);
        radioHombre.setTypeface(myFont);


    }


    public void darValoresPaciente ()
    {

        editNombre.setText(mPaciente.getNombre());
        editApellido.setText(mPaciente.getApellido());

        String anos = getResources().getQuantityString(R.plurals.anos,mPaciente.getEdad());
        editEdad.setText(String.valueOf(mPaciente.getEdad())+" "+anos);
        editPeso.setText(String.valueOf(mPaciente.getPeso()) );

        if(mPaciente.getSexo().equals("h"))
        {
            radioHombre.setChecked(true);
            radioMujer.setChecked(false);
        }
        else
        {
            radioMujer.setChecked(true);
            radioHombre.setChecked(false);
        }




        //Seleccionamos el valor del spinnier para el control cefalico
        //**************************************************************************************************************************
        List<String> listaControlCefalico = Arrays.asList(getResources().getStringArray(R.array.control_cefalico));
        int pos=0;
        for (int i=0; i<listaControlCefalico.size(); i++)
        {
            if (listaControlCefalico.get(i).toUpperCase().equals(mPaciente.getDiagnostico_control_cefalico().toUpperCase()))
            {
                pos=i;
            }
        }
        controlCefalico_valueSelect=pos;
        spinnerControlCefalico.setSelection(pos);
        //**************************************************************************************************************************

        //Seleccionamos el spinner para el tono muscular
        //**************************************************************************************************************************
        List<String> listaTonoMuscular = Arrays.asList(getResources().getStringArray(R.array.tono_muscular));
        for (int i=0; i<listaTonoMuscular.size(); i++)
        {
            if (listaTonoMuscular.get(i).toUpperCase().equals(mPaciente.getDiagnostico_tono_muscular().toUpperCase()))
            {
                pos=i;
            }
        }

        controlCefalico_valueSelect=pos;
        spinnerTonoMuscular.setSelection(pos);
        //**************************************************************************************************************************





    }



    public void anadirPestanasPaciente()
    {

        //Pestanas superiores
        cambiarEstadoPestanaPaciente(contenedor_btn_pestana_izquierda,R.drawable.border_btn_below_pestana_activa,R.drawable.border_btn_below_pestana_activa);
        cambiarEstadoPestanaPaciente(contenedor_btn_pestana_derecha,R.drawable.border_btn_below_pestana_inactiva,R.drawable.border_btn_below_pestana_inactiva);



        //Redimencionamos tamaños de Botones pestañas superiores de asignación
        //****************************************************************************************
       /* ViewGroup.LayoutParams paramBtnIzqdaArriba =contenedor_btn_pestana_izquierda.getLayoutParams();
        paramBtnIzqdaArriba.width =  (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.50);
        */
        //paramBtnIzqdaArriba.height=  (int) (((HomeActivity) getActivity()).getTamanoPantalla().getHeightPixels()*0.08);

        /*
        ViewGroup.LayoutParams paramsBtnDchaArriba =contenedor_btn_pestana_derecha.getLayoutParams();
        paramsBtnDchaArriba.width =  (int) (((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels()*0.50);
        */
       // paramsBtnDchaArriba.height=  (int) (((HomeActivity) getActivity()).getTamanoPantalla().getHeightPixels()*0.08);
        //****************************************************************************************

        //Añadimos los framentos a los elementos de la vista inflada: En este caso las pestañas superiores de asignación
        //*******************************************************************************************************************************************
        //Paso 1: Obtener la instancia del administrador de fragmentos
        fragmentManagerChild = getChildFragmentManager();//Se usa para los fragmentos de fragmentos.
        //getSupportMannager // Para los fragmentos de actividades.

        //Paso 2: Crear una nueva transacción

        btn_pestana_izquierda = BotonBelowPestanaFragment.newInstance(getResources().getString(R.string.ficha),"");
        fragmentManagerChild.beginTransaction().replace(contenedor_btn_pestana_izquierda.getId(),btn_pestana_izquierda,"btn_pestana_izquierda").commit();

        btn_pestana_derecha = BotonBelowPestanaFragment.newInstance(getResources().getString(R.string.mediciones),"");
        fragmentManagerChild.beginTransaction().replace(contenedor_btn_pestana_derecha.getId(),btn_pestana_derecha,"btn_pestana_derecha").commit();



    }




/*
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
/*
    /*
    public void listenerPestanasAsignacion()
    {
        //Escuchamos los contenedores de las pestañas
        //****************************************************************************************************************************
        contenedor_btn_izquierda_asignacion.setOnClickListener(this);
        contenedor_btn_derecha_asignacion.setOnClickListener(this);
    }*/


    public void listenerPestanasPaciente()
    {
        //Escuchamos los contenedores de las pestañas
        //****************************************************************************************************************************
        contenedor_btn_pestana_izquierda.setOnClickListener(this);
        contenedor_btn_pestana_derecha.setOnClickListener(this);
    }



/*
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

    }*/




    public void cambiarEstadoPestanaPaciente(RelativeLayout contenedorPestana, int presionado, int normal)
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



                    if(takePictruePacienteEdit!=null) {
                        takePictruePacienteEdit.borrarImagePaciente(sharedPreferSession.getUserEmail());
                    }
                    else{


                        //Aqui borrar imagen ruta y directorio.
                        //****************************************************************************************************************************
                        //****************************************************************************************************************************
                        //****************************************************************************************************************************
                        //****************************************************************************************************************************     //****************************************************************************************************************************
                        //****************************************************************************************************************************


                        borrarImagePacienteEdit(mPaciente.getDir_imagen());
                        mPaciente.setDir_imagen(null);
                        crudDbHeadpod.updatePacienteById(mPaciente,mPaciente.getId());

                    }


                    txtAnadirBorrarImg.setText(addUnderline(getResources().getString(R.string.anadirImagen)));

                    Bitmap imagenOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.monigote_verde_perfil);


                   // ViewGroup.LayoutParams imgPacienteLayoutParams =imgPaciente.getLayoutParams();

                    //Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal, imgPacienteLayoutParams.width,imgPacienteLayoutParams.height , false);

                    Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal,180,245 , false);



                    imgPaciente.setImageBitmap(imagenFinal);


                    // imgPerfil.setImageResource(R.drawable.monigote_verde_perfil);
                    //Acutalizamos el header del navigationView.
                    //((HomeActivity) getActivity()).actualizarHeaderNavigationView();



                }
                else {
                    Log.d("FotoText","anadir_imagen");

                    takePictruePacienteEdit = new TakePicturePacienteEdit(this);//Fragment
                    takePictruePacienteEdit.dispatchTakePictureIntentPaciente();
                }


                break;

            case R.id.relativeLayout_btnPestanaIzquierda:
                Log.d("click","soy pestaña izquierda superior");


                cambiarEstadoPestanaPaciente(contenedor_btn_pestana_izquierda,R.drawable.border_btn_below_pestana_activa,R.drawable.border_btn_below_pestana_activa);
                cambiarEstadoPestanaPaciente(contenedor_btn_pestana_derecha,R.drawable.border_btn_below_pestana_inactiva,R.drawable.border_btn_below_pestana_inactiva);

                mListener.asignarTituloToolbar(getResources().getString(R.string.ficha));


                new EfectoTxtClick(getContext(),btn_pestana_izquierda.txtNamePestana,R.animator.txt_color_efecto2,80);

                break;

            case R.id.relativeLayout_btnPestanaDerecha:

                Log.d("click","soy pestaña derecha superior");

                cambiarEstadoPestanaPaciente(contenedor_btn_pestana_izquierda,R.drawable.border_btn_below_pestana_inactiva,R.drawable.border_btn_below_pestana_inactiva);
                cambiarEstadoPestanaPaciente(contenedor_btn_pestana_derecha,R.drawable.border_btn_below_pestana_activa,R.drawable.border_btn_below_pestana_activa);

                mListener.asignarTituloToolbar(getResources().getString(R.string.mediciones));

                new EfectoTxtClick(getContext(),btn_pestana_derecha.txtNamePestana,R.animator.txt_color_efecto2,80);



                //mListener.onFragmentInteraction("fragment_mis_mediciones");

                mListener.onMispacientesFragmentAdapter(mPaciente,"fragment_mis_mediciones");



                break;

            case R.id.btnModificarPaciente:
                new EfectoTxtClick(getContext(),txtModificar,R.animator.txt_color_efecto,80);

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


                        mPaciente.setNombre(editNombre.getText().toString());
                        mPaciente.setApellido(editApellido.getText().toString());
                        mPaciente.setPeso(Float.valueOf(editPeso.getText().toString()));
                        mPaciente.setDia(dia_nac);
                        mPaciente.setMes(mes_nac);
                        mPaciente.setAno(ano_nac);
                        mPaciente.setSexo(sexo);
                        mPaciente.setDiagnostico_control_cefalico( spinnerControlCefalico.getItemAtPosition(controlCefalico_valueSelect).toString());
                        mPaciente.setDiagnostico_tono_muscular(spinnerTonoMuscular.getItemAtPosition(tonoMuscular_valueSelect).toString());



                        //añadimos la ruta al paciente.
                        if(takePictruePacienteEdit!=null && takePictruePacienteEdit.getmCurrentPhotoPath()!=null)
                        {
                            mPaciente.setDir_imagen(takePictruePacienteEdit.getmCurrentPhotoPath());
                        }


                        //actualizamos el paciente.
                        crudDbHeadpod.updatePacienteById(mPaciente,mPaciente.getId());

                        //creamos el paciente y lo relacionamos.


                            Toast.makeText(getContext(),getString(R.string.paciente_modify),Toast.LENGTH_LONG).show();
                            //Eliminamos el ultimo fragmento de la pila de transiciones y la pila auxiliar y volver al anterior fragmento.
                            //************************************************************************************************************
                            //Eliminamos el fragmento de la pila.
                            //A este fragmento no lo estoy metiendo en la pila de transición
                            //((HomeActivity) getActivity()).eliminarFragmentoDePilaTransicion("fragment_edit_paciente");
                            //Elimino las dos posiciones altas de la pila y vuelvo a cargar la penultima.
                        /* int popPilaAux = ((HomeActivity) getActivity()).pilaAuxFragments.size() - 1;

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

                            //************************************************************************************************************

                        */
                    }
                    else
                    {


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

                        mPaciente.setNombre(editNombre.getText().toString());
                        mPaciente.setApellido(editApellido.getText().toString());
                        mPaciente.setSexo(sexo);
                        mPaciente.setDiagnostico_control_cefalico( spinnerControlCefalico.getItemAtPosition(controlCefalico_valueSelect).toString());
                        mPaciente.setDiagnostico_tono_muscular(spinnerTonoMuscular.getItemAtPosition(tonoMuscular_valueSelect).toString());

                        mPaciente.setPeso(Float.valueOf(editPeso.getText().toString()));


                        //añadimos la ruta al paciente.
                        if(takePictruePacienteEdit!=null && takePictruePacienteEdit.getmCurrentPhotoPath()!=null)
                        {
                            mPaciente.setDir_imagen(takePictruePacienteEdit.getmCurrentPhotoPath());
                        }


                        //actualizamos el paciente.
                        crudDbHeadpod.updatePacienteById(mPaciente,mPaciente.getId());

                        //creamos el paciente y lo relacionamos.


                        Toast.makeText(getContext(),getString(R.string.paciente_modify),Toast.LENGTH_LONG).show();







                    }



                }
                else
                {
                    Toast.makeText(getContext(),getString(R.string.paciente_fail),Toast.LENGTH_LONG).show();
                }


                break;


            case R.id.btnEliminarPaciente:
                new EfectoTxtClick(getContext(),txtEliminar,R.animator.txt_color_efecto,80);

                DialogAvisos dialogAvisos = DialogAvisos.newInstance(R.string.aviso_eliminar_paciente,mPaciente);
                dialogAvisos.show(getActivity().getSupportFragmentManager(),"aviso");


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
        Bitmap bitmap = BitmapFactory.decodeFile(takePictruePacienteEdit.getmCurrentPhotoPath());

        //String hhh= takePicture.decodeFile(takePicture.getmCurrentPhotoPath(),256,512);

        //reduce la calidad de la imagen
        //takePictruePacienteEdit.decodeFile(takePictruePacienteEdit.getmCurrentPhotoPath(),256,512);


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


        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Actualizamos la base de datos
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        mPaciente.setDir_imagen(takePictruePacienteEdit.getmCurrentPhotoPath());
        crudDbHeadpod.updatePacienteById(mPaciente,mPaciente.getId());
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


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



    public void colocarImgPacienteCreateView()
    {

        if(mPaciente.getDir_imagen()!=null){

            Uri path = Uri.parse(mPaciente.getDir_imagen());

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap imagenFinal = BitmapFactory.decodeFile(path.toString(),bmOptions);

            imagenFinal = Bitmap.createScaledBitmap(imagenFinal, 180,245 , false);

            //creamos el drawable redondeado
            // RoundedBitmapDrawable roundedDrawable =
            //        RoundedBitmapDrawableFactory.create(getResources(), imagenFinal);
            //asignamos el CornerRadius
            // roundedDrawable.setCornerRadius(imagenFinal.getHeight());

            //imgPerfil.setImageDrawable(roundedDrawable);

            imgPaciente.setImageBitmap(imagenFinal);



            //imgPerfil.setImageURI(path);
            txtAnadirBorrarImg.setText(addUnderline(getResources().getString(R.string.borrarImagen)));

        }
        else
        {
            Bitmap imagenOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.monigote_verde_perfil);
            Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal, 180,245 , false);

            // ViewGroup.LayoutParams imgPerfilLayoutParams =imgPerfil.getLayoutParams();

            //imgPerfilLayoutParams.height=heightImg;
            //imgPerfilLayoutParams.width=widthImg;


            imgPaciente.setImageBitmap(imagenFinal);
            txtAnadirBorrarImg.setText(addUnderline(getResources().getString(R.string.anadirImagen)));
        }

        // TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, getResources().getDisplayMetrics());


    }




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
    public void onResume() {
        //Log.e("DEBUG", "onResume of LoginFragment");
        new EfectoTxtClick(getContext(),btn_pestana_izquierda.txtNamePestana,R.animator.txt_color_efecto2,120);
        super.onResume();
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
        void onMispacientesFragmentAdapter(Paciente paciente, String tagFragment);

    }
}



