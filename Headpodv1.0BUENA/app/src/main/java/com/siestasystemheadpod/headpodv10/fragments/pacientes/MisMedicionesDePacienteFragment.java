package com.siestasystemheadpod.headpodv10.fragments.pacientes;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.SharedPreferSession;
import com.siestasystemheadpod.headpodv10.adicionales.EfectoTxtClick;
import com.siestasystemheadpod.headpodv10.adicionales.extra_mis_pacientes.AdapterMisPacientes;
import com.siestasystemheadpod.headpodv10.adicionales.extra_mis_pacientes.ItemMisPacientes;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.modelo.Paciente;
import com.siestasystemheadpod.headpodv10.fragments.BotonBelowPestanaFragment;
import com.siestasystemheadpod.headpodv10.fragments.BotonPestanaFragment;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MisMedicionesDePacienteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MisMedicionesDePacienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 *
 * Esta clase se encargara de las mediciones de cada paciente.
 *
 */
public class MisMedicionesDePacienteFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "paciente";

    // TODO: Rename and change types of parameters
    private Paciente mPaciente;



    //Recursos del layout
    //******************************************************************************************************
    //******************************************************************************************************


    public RelativeLayout contenedor_btn_pestana_izquierda;
    public RelativeLayout contenedor_btn_pestana_derecha;

    public BotonBelowPestanaFragment btn_pestana_izquierda;
    public BotonBelowPestanaFragment  btn_pestana_derecha;



    //1fila
    //******************************************************************************************
    private TextView txtFiltrar ;
    private TextView txtDesde ;
    private TextView txtFechaDesdeXX;
    private TextView txtHasta ;
    private TextView txtFechaHastaXX ;
    //******************************************************************************************

    //2fila (Aviso de que debe seleccionar un paciente)
    //******************************************************************************************
    private TextView txtSelectAvisoMedicion;
    //******************************************************************************************

    //3fila
    //*******************************************************************************************
    private RelativeLayout contenedorVer;
    private RelativeLayout contenedorFecha;
    private RelativeLayout contenedorTipo;
    private RelativeLayout contenedorDuracion;
    private RelativeLayout contenedorEliminar;
    private RelativeLayout contenedorComparar;

    private TextView txtVer;
    private TextView txtFecha;
    private TextView txtTipo ;
    private TextView txtDuracion;
    private TextView txtComparar;
    //*******************************************************************************************

    //4fila y demas (listview)
    //*******************************************************************************************
    private ListView listView;
    private TextView txtMedicionesEmpty;
    //******************************************************************************************************
    //******************************************************************************************************




    public FragmentManager fragmentManagerChild;

    public RelativeLayout contenedor_btn_izquierda_asignacion;
    public RelativeLayout contenedor_btn_derecha_asignacion;


    public BotonPestanaFragment btn_izquierda_asignacion;
    public BotonPestanaFragment  btn_derecha_asignacion;


    private  ImageView logo_paciente;
    public   TextView txt_nuevo_paciente;

    private OnFragmentInteractionListener mListener;


    private SharedPreferSession sharedPreferSession;


    //Adapter para los pacientes.
    private  AdapterMisPacientes adapterMisPacientes;


    //

    private int chivatoBusquedad;







    //Lista de los pacientes actualizada (al seleccionar o deseleccionar)
    private  ArrayList<ItemMisPacientes> arrayListItemsPacientesActualizado;



    //Base de datos
    //instaciamos la base de datos
    private CrudDbHeadpod crudDbHeadpod;




    //Para la configuración de la orientación. Con esto respetamos el diseño landScape
    private FrameLayout frameLayout;

    //Lista de los pacientes
   // private ListView listViewPacientes;

    public MisMedicionesDePacienteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param paciente Parameter 1.
     * @return A new instance of fragment MisPacientesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MisMedicionesDePacienteFragment newInstance(Paciente paciente) {
        MisMedicionesDePacienteFragment fragment = new MisMedicionesDePacienteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, paciente);
        fragment.setArguments(args);
        return fragment;
    }





    //http://stackoverflow.com/questions/17116602/using-onconfigurationchanged-in-a-fragment
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        ((HomeActivity)getActivity()).cambiarIdioma();
        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_mis_mediciones, null);

        setContenidoVista(view);

        frameLayout .addView(view);
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPaciente = getArguments().getParcelable(ARG_PARAM1);


        }

        sharedPreferSession= new SharedPreferSession(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        frameLayout = new FrameLayout(getActivity());
        // LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_mediciones, container, false);


        setContenidoVista(view);


        frameLayout .addView(view);
        //return view;
        return frameLayout;
    }




    public void setContenidoVista(View view)
    {


        mListener.asignarTituloToolbar(getResources().getString(R.string.mediciones));

        obtenerRecursosDeLayout(view);

        darFuenteTexview();

        anadirPestanasPaciente();

        listenerPestanasPaciente();//Escuchamos las pestañas del paciente.


        montarCabeceraListaMediciones();//Aqui se dan unos porcentajes a cada columna.


        txtVer.setOnClickListener(this);

        // titulo.setText("datos en tiempo real");

        //contenedor_btn_izquierda_asignacion = (RelativeLayout) view.findViewById(R.id.layout_btnIzquierdaAsignancion);
        //contenedor_btn_derecha_asignacion = (RelativeLayout) view.findViewById(R.id.layout_btnDerechaAsignacion);









        //*******************************************************************************************






        // ViewGroup.MarginLayoutParams margin_final = (ViewGroup.MarginLayoutParams) contenedorConHp.getLayoutParams();

        //margin_final.setMarginEnd((int) (widthPantalla*0.01));

        //Método para escoger los tamaños de la cabezera de la lista dinámica
        //******scaleHeadListPacientes(imgCheckBox,imgPerfil,imgCabezaShp,imgCabezaChp);


/*
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cabeza_con_hp.getLayoutParams();
        //params.width = 200; params.leftMargin = 100; params.topMargin = 200;
        params.setMarginEnd(16);
*/


        //instaciamos la base de datos
        crudDbHeadpod = CrudDbHeadpod.obtenerInstancia(getContext());

        //Buscamos los pacientes correspondientes al terapeuta que ha iniciado sesión

 /*       //****************************************************************************************************************************
//***
        ArrayList<Long> listaIdPacientes = crudDbHeadpod.getID_AllPacientesOfTerapeuta(sharedPreferSession.getUserEmail());

        if(listaIdPacientes!=null && !listaIdPacientes.isEmpty())
        {
            txtPacientesEmpty.setVisibility(View.INVISIBLE);

            //Usaremos un adapter para la listView
            final ArrayList<ItemMisPacientes>  arrayListItemMisPacientes =new ArrayList<ItemMisPacientes>();



            for (int i=0 ; i< listaIdPacientes.size();i++)
            {

                Paciente paciente = crudDbHeadpod.getPacientebyID(listaIdPacientes.get(i));

                if(paciente!=null)
                {
                    Log.d("id_pacienteClass",String.valueOf(paciente.getId()));

                    ItemMisPacientes itemMisPacientes = new ItemMisPacientes();
                    itemMisPacientes.setPaciente(paciente);
                    arrayListItemMisPacientes.add(itemMisPacientes);



                }
            }
            adapterMisPacientes = new AdapterMisPacientes(getActivity(),arrayListItemMisPacientes, crudDbHeadpod);

            listViewPacientes.setAdapter(adapterMisPacientes);





            //Esuchamos el editText de buscar paciente
            //*************************************************************
            //*******ListenerEditeTextBuscarPaciente(edit_buscar_paciente,listViewPacientes);
            //**************************************************************


            //Nota importante:
            //**********************************************************
            //En el contenedor raiz del listview, importante añadir:
            //android:descendantFocusability="blocksDescendants"
            //De lo contrario no funciona.
            //Escuchamos el listview
            //******************************************************************************************
           //************** listenerListviewPacientes(listViewPacientes);
            //******************************************************************************************************


        }
        else
        {

            txtPacientesEmpty.setVisibility(View.VISIBLE);
        }
*/
        //
        //****************************************************************************************************************************
        //Colocamos el borde a los contenedores de las pestañas, inicializandolas a un estado inicial
        //*********************************************************************************************
        //Pestanas superiores
        //cambiarEstadoPestana(contenedor_btn_izquierda_asignacion,R.drawable.border_btn_pestana_izqda_presionada,R.drawable.border_btn_pestana_izqda);
        // cambiarEstadoPestana(contenedor_btn_derecha_asignacion,R.drawable.border_btn_pestana_dcha_presionada,R.drawable.border_btn_pestana_dcha);


        //Escuchamos los contenedores de las pestañas
        //****************************************************************************************************************************
        // contenedor_btn_izquierda_asignacion.setOnClickListener(this);
        //  contenedor_btn_derecha_asignacion.setOnClickListener(this);



        //esuchamos
        //***************************************************************
        //******txt_nuevo_paciente.setOnClickListener(this);
        //********logo_paciente.setOnClickListener(this);
        //***************************************************************








        //Botones pestañas superiores de asignación
        /*ViewGroup.LayoutParams paramBtnIzqdaArriba =contenedor_btn_izquierda_asignacion.getLayoutParams();
        paramBtnIzqdaArriba.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.50);
        paramBtnIzqdaArriba.height=  (int) (((HomeActivity) getActivity()).getHeightPixels()*0.07);

        ViewGroup.LayoutParams paramsBtnDchaArriba =contenedor_btn_derecha_asignacion.getLayoutParams();
        paramsBtnDchaArriba.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.50);
        paramsBtnDchaArriba.height=  (int) (((HomeActivity) getActivity()).getHeightPixels()*0.07);
        */

        //Redimensionamos logo paciente
//************************************************************************************************
        //************************************************************************************************
//************************************************************************************************
//************************************************************************************************
//************************************************************************************************
//************************************************************************************************
//************************************************************************************************
//************************************************************************************************
//************************************************************************************************

        //ViewGroup.LayoutParams logoPacienteParams = logo_paciente.getLayoutParams();
        //logoPacienteParams.width = (int) (((HomeActivity) getActivity()).getWidthPixels()*0.35);//Habría que darle una mejor resolucion a la imagen.

        //Redimencionamos triangulo

        /*
        ViewGroup.LayoutParams trianguloParams = triangulo.getLayoutParams();
        trianguloParams.width = (int) (((HomeActivity) getActivity()).getWidthPixels()*0.06);


        //Redimencionamos el tamaño de las cabezas
        ViewGroup.LayoutParams cabeza_sin_hpLayoutParams =cabeza_sin_hp.getLayoutParams();
        cabeza_sin_hpLayoutParams.width = (int) (((HomeActivity) getActivity()).getWidthPixels()*0.20);

        ViewGroup.LayoutParams cabeza_con_hpLayoutParams =cabeza_con_hp.getLayoutParams();
        cabeza_con_hpLayoutParams.width = (int) (((HomeActivity) getActivity()).getWidthPixels()*0.23);


*/
        //Añadimos los framentos a los elementos de la vista inflada:
        //*******************************************************************************************************************************************
        //Paso 1: Obtener la instancia del administrador de fragmentos
       /* fragmentManagerChild = getChildFragmentManager();//Se usa para los fragmentos de fragmentos.
        //getSupportMannager // Para los fragmentos de actividades.

        //Paso 2: Crear una nueva transacción


        btn_izquierda_asignacion = BotonPestanaFragment.newInstance(getResources().getString(R.string.terapeuta_sin_asignar),"");
        fragmentManagerChild.beginTransaction().replace(contenedor_btn_izquierda_asignacion.getId(),btn_izquierda_asignacion,"btn_izquierda_asignacion").commit();

        btn_derecha_asignacion = BotonPestanaFragment.newInstance(getResources().getString(R.string.paciente_sin_asignar),"");
        fragmentManagerChild.beginTransaction().replace(contenedor_btn_derecha_asignacion.getId(),btn_derecha_asignacion,"btn_derecha_asignacion").commit();

*/
        //Añadir pacientes en caso de haberlos



    }



    public void obtenerRecursosDeLayout(View view)
    {


        contenedor_btn_pestana_izquierda = (RelativeLayout) view.findViewById(R.id.relativeLayout_btnPestanaIzquierda);
        contenedor_btn_pestana_derecha = (RelativeLayout) view.findViewById(R.id.relativeLayout_btnPestanaDerecha);

        //1fila
        //******************************************************************************************
        txtFiltrar = (TextView) view.findViewById(R.id.txtFiltrar);
        txtDesde = (TextView) view.findViewById(R.id.txtDesde);
        txtFechaDesdeXX= (TextView) view.findViewById(R.id.txtDesdeXX);
        txtHasta = (TextView) view.findViewById(R.id.txtHasta);
        txtFechaHastaXX = (TextView) view.findViewById(R.id.txtHastaXX);
        //******************************************************************************************

        //2fila (Aviso de que debe seleccionar un paciente)
        //******************************************************************************************
        txtSelectAvisoMedicion = (TextView) view.findViewById(R.id.txtAvisoSeleccion);
        //******************************************************************************************

        //3fila
        //*******************************************************************************************

        contenedorVer = (RelativeLayout) view.findViewById(R.id.relativeLayoutVer);
        contenedorFecha = (RelativeLayout) view.findViewById(R.id.relativeLayoutFecha);
        contenedorTipo = (RelativeLayout) view.findViewById(R.id.relativeLayoutTipo);
        contenedorDuracion = (RelativeLayout) view.findViewById(R.id.relativeLayoutDuracion);

        contenedorEliminar = (RelativeLayout) view.findViewById(R.id.relativeLayoutEliminar);
        contenedorComparar = (RelativeLayout) view.findViewById(R.id.relativeLayoutComparar);

        txtVer = (TextView) view.findViewById(R.id.txtVer);
        txtFecha = (TextView) view.findViewById(R.id.txtFecha);
        txtTipo = (TextView) view.findViewById(R.id.txtTipo);
        txtDuracion = (TextView) view.findViewById(R.id.txtDuracion);
        txtComparar = (TextView) view.findViewById(R.id.txtComparar);
        //*******************************************************************************************

        //4fila y demas (listview)
        //*******************************************************************************************
        listView = (ListView) view.findViewById(R.id.listView);
        txtMedicionesEmpty = (TextView) view.findViewById(R.id.txtMedicionesEmpty);




    }


    public void darFuenteTexview()
    {

        //Aplicacmos la fuente
        //*******************************************************************************************
        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);

        txtFiltrar.setTypeface(myFont);
        txtDesde.setTypeface(myFont);
        txtFechaDesdeXX.setTypeface(myFont);
        txtHasta.setTypeface(myFont);
        txtFechaHastaXX.setTypeface(myFont);
        txtSelectAvisoMedicion.setTypeface(myFont);
        txtVer.setTypeface(myFont);
        txtFecha.setTypeface(myFont);
        txtTipo.setTypeface(myFont);
        txtDuracion.setTypeface(myFont);
        txtComparar.setTypeface(myFont);
        txtMedicionesEmpty.setTypeface(myFont);
        //*******************************************************************************************


    }

    public void listenerPestanasPaciente()
    {
        //Escuchamos los contenedores de las pestañas
        //****************************************************************************************************************************
        contenedor_btn_pestana_izquierda.setOnClickListener(this);
        contenedor_btn_pestana_derecha.setOnClickListener(this);
    }




    public void anadirPestanasPaciente()
    {

        //Pestanas superiores
        cambiarEstadoPestanaPaciente(contenedor_btn_pestana_izquierda,R.drawable.border_btn_below_pestana_inactiva,R.drawable.border_btn_below_pestana_inactiva);
        cambiarEstadoPestanaPaciente(contenedor_btn_pestana_derecha,R.drawable.border_btn_below_pestana_activa,R.drawable.border_btn_below_pestana_activa);





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




    private void montarCabeceraListaMediciones()
    {


        //obtenemos el tamaño de pantalla
        int widthPantalla = ((HomeActivity) getActivity()).getTamanoPantalla().getWidthPixels();



        //20 *5 = 100



        //hay que dejar algo de margen reduzco el 1%
        //(14 *3) + 19 + 34 = 95 % del ancho


        //14+14+38+15+15=96
        //1 de marginstat
        //1 de marginEnd
        //96+1+1=98

        //dejamos dos libres por paddings, margin, etc


        contenedorVer.getLayoutParams().width= (int) (widthPantalla*0.10);
        ViewGroup.MarginLayoutParams margin_start = (ViewGroup.MarginLayoutParams) contenedorVer.getLayoutParams();
        margin_start.setMarginStart((int) (widthPantalla*0.02));


        contenedorFecha.getLayoutParams().width = (int) (widthPantalla*0.21);

        contenedorTipo.getLayoutParams().width = (int) (widthPantalla*0.15);

        contenedorDuracion.getLayoutParams().width = (int) (widthPantalla*0.20);

        contenedorEliminar.getLayoutParams().width = (int) (widthPantalla*0.08);//esta cara es mas grande sino se redimenciona

        contenedorComparar.getLayoutParams().width = ( int ) (widthPantalla*0.21);

    }




    private void ListenerEditeTextBuscarPaciente(EditText edit_buscar_paciente, final ListView listViewPacientes)
    {

        edit_buscar_paciente.addTextChangedListener(new TextWatcher() {
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
                Log.d("valor",s.toString());
                //auxValorTxtTimeSelect=s;





                //Extraemos los datos de la BD por si se ha producido algún cambio
                //********************************************************************************************************************************************************
                //********************************************************************************************************************************************************
                ArrayList<Long> listaIdPacientes = crudDbHeadpod.getID_AllPacientesOfTerapeuta(sharedPreferSession.getUserEmail());


                //Usaremos un adapter para la listView
                final ArrayList<ItemMisPacientes> arrayListItemMisPacientes = new ArrayList<ItemMisPacientes>();
                if(listaIdPacientes!=null && !listaIdPacientes.isEmpty()) {

                    for (int i = 0; i < listaIdPacientes.size(); i++) {

                        Paciente paciente = crudDbHeadpod.getPacientebyID(listaIdPacientes.get(i));

                        if (paciente != null) {
                            Log.d("id_pacienteClass", String.valueOf(paciente.getId()));

                            ItemMisPacientes itemMisPacientes = new ItemMisPacientes();
                            itemMisPacientes.setPaciente(paciente);
                            arrayListItemMisPacientes.add(itemMisPacientes);


                        }
                    }
                }
                 //*************************************************************************************************************************************
                //********************************************************************************************************************************************************


                chivatoBusquedad=0;

                if(s.length()>0)
                {
                    ArrayList<ItemMisPacientes> arrayListAux = new ArrayList<ItemMisPacientes>();



                    for(int i=0; i<=arrayListItemMisPacientes.size()-1;i++)
                    {
                        String apellido =arrayListItemMisPacientes.get(i).getPaciente().getApellido();
                        String nombre =arrayListItemMisPacientes.get(i).getPaciente().getNombre();
                        String sexo =arrayListItemMisPacientes.get(i).getPaciente().getSexo();//hombre, mujer
                        int edad =arrayListItemMisPacientes.get(i).getPaciente().getEdad();
                        float peso = arrayListItemMisPacientes.get(i).getPaciente().getPeso();




                        if(nombre.toUpperCase().contains(s.toString().toUpperCase()) || apellido.toUpperCase().contains(s.toString().toUpperCase())   )
                        {

                            arrayListAux.add(arrayListItemMisPacientes.get(i));
                            chivatoBusquedad=1;
                        }


                        if(s.toString().toUpperCase().equals(getResources().getString(R.string.hombre).toUpperCase()))
                        {
                            if(sexo.equals("h"))
                            {
                                arrayListAux.add(arrayListItemMisPacientes.get(i));
                                chivatoBusquedad=1;
                            }
                        }



                        if(s.toString().toUpperCase().equals(getResources().getString(R.string.mujer).toUpperCase()))
                        {
                            if(sexo.equals("m"))
                            {
                                arrayListAux.add(arrayListItemMisPacientes.get(i));
                                chivatoBusquedad=1;
                            }
                        }



                    }//fin for


                    if(chivatoBusquedad==1)
                    {


                        AdapterMisPacientes adapterMisPacientes = new AdapterMisPacientes(getActivity(),arrayListAux,crudDbHeadpod);


                        adapterMisPacientes.setItemsAux(arrayListItemMisPacientes);


                       //ItemMisPacientes itemMisPacientes = (ItemMisPacientes) listViewPacientes.getAdapter().getItem(0);


                        //Log.d("fasdfs",itemMisPacientes.getPaciente().getApellido());


                        listViewPacientes.setAdapter(adapterMisPacientes);
                    }
                    else
                    {

                        String[] mensaje = {
                           getResources().getString(R.string.find_pacientes_fail)
                        };

                        //android.R.layout.simple_list_item_1
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.textview_adapter_find_pacientes_fail,mensaje);

                        listViewPacientes.setAdapter(adapter);

                    }

                }
                else {
                    //Extraemos los datos de la BD por si se ha producido algún cambio
                    //********************************************************************************************************************************************************
                    //********************************************************************************************************************************************************

                    ArrayList<Long> listaIdPacientes2 = crudDbHeadpod.getID_AllPacientesOfTerapeuta(sharedPreferSession.getUserEmail());

                    if(listaIdPacientes2!=null && !listaIdPacientes2.isEmpty()) {
                        //txtPacientesEmpty.setVisibility(View.INVISIBLE);

                        //Usaremos un adapter para la listView
                        final ArrayList<ItemMisPacientes> arrayListItemMisPacientes2 = new ArrayList<ItemMisPacientes>();


                        for (int i = 0; i < listaIdPacientes2.size(); i++) {

                            Paciente paciente = crudDbHeadpod.getPacientebyID(listaIdPacientes2.get(i));

                            if (paciente != null) {
                                Log.d("id_pacienteClass", String.valueOf(paciente.getId()));

                                ItemMisPacientes itemMisPacientes = new ItemMisPacientes();
                                itemMisPacientes.setPaciente(paciente);
                                arrayListItemMisPacientes2.add(itemMisPacientes);


                            }
                        }

                        //********************************************************************************************************************************************************
                        //********************************************************************************************************************************************************

                        AdapterMisPacientes adapterMisPacientes = new AdapterMisPacientes(getActivity(), arrayListItemMisPacientes2, crudDbHeadpod);

                        adapterMisPacientes.setItems(arrayListItemMisPacientes2);
                       // adapterMisPacientes.setItemsAux(null);


                        listViewPacientes.setAdapter(adapterMisPacientes);


                    }

                }

            }


        });

    }


    //Método para detectar el click de cada item de la lista de pacientes
    private void listenerListviewPacientes(final ListView listViewPacientes)
    {

        listViewPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                ImageView imgPaciente = (ImageView) view.findViewById(R.id.imgPaciente);
                TextView txtNombre = (TextView) view.findViewById(R.id.txtNombre);
                TextView txtApellido = (TextView) view.findViewById(R.id.txtApellido);


                AdapterMisPacientes adapter= (AdapterMisPacientes) parent.getAdapter();
                ItemMisPacientes itemMisPacientes = (ItemMisPacientes) adapter.getItem(position);
                Paciente paciente = itemMisPacientes.getPaciente();


                Log.d("paciente_id",Long.toString(paciente.getId()));
                Log.d("nombre", paciente.getNombre());
                Log.d("apellido", paciente.getApellido());
                Log.d("select",String.valueOf(paciente.getSeleccionado()));

                Log.d("diagnostico1",paciente.getDiagnostico_control_cefalico());

                Log.d("diagnostico2", paciente.getDiagnostico_tono_muscular());


                //mListener.onFragmentInteraction("fragment_edit_paciente");


                if(paciente.getDir_imagen()!=null)
                Log.d("dir_imagen",paciente.getDir_imagen());



                mListener.onMispacientesFragmentAdapter(paciente,"fragment_edit_paciente");

            }
        });





    }


    //Méotod para escalar el tamaño de la cabezera de la lista dinmaica de mis pacientes.
    public void scaleHeadListPacientes( ImageView imgCheckBox, ImageView imgPerfil, ImageView imgCaraShp,ImageView imgCaraChp)
    {

        double pulgadas =((HomeActivity)getActivity()).getTamanoPantalla().getScreenInchesPulgadas();

        //Obtengo los valores en pixeles:
        //***********************************************************************************************
        int widthPixelImgCheckBox = imgCheckBox.getDrawable().getIntrinsicWidth();
        int heightPixelImgCheckBox = imgCheckBox.getDrawable().getIntrinsicHeight();


        int widthPixelImgPerfil = imgPerfil.getDrawable().getIntrinsicWidth();
        int heightPixelImgPerfil = imgPerfil.getDrawable().getIntrinsicHeight();


        int widthPixelImgCaraShp = imgCaraShp.getDrawable().getIntrinsicWidth();
        int heightPixelImgCaraShp = imgCaraShp.getDrawable().getIntrinsicHeight();

        int widthPixelImgCaraChp = imgCaraChp.getDrawable().getIntrinsicWidth();
        int heightPixelImgCaraChp = imgCaraShp.getDrawable().getIntrinsicHeight();




        if(pulgadas<4.5)
        {


            imgCheckBox.getLayoutParams().width=(int)(widthPixelImgCheckBox*0.65);
            imgPerfil.getLayoutParams().width=(int)(widthPixelImgPerfil*0.65);
            imgCaraShp.getLayoutParams().width=(int)(widthPixelImgCaraShp*0.65);
            imgCaraChp.getLayoutParams().width=(int)(widthPixelImgCaraChp*0.65);




        }

        if(pulgadas>=4.5 && pulgadas<7)
        {

            imgCheckBox.getLayoutParams().width=(int)(widthPixelImgCheckBox*0.80);
            imgPerfil.getLayoutParams().width=(int)(widthPixelImgPerfil*0.80);
            imgCaraShp.getLayoutParams().width=(int)(widthPixelImgCaraShp*0.80);
            imgCaraChp.getLayoutParams().width=(int)(widthPixelImgCaraChp*0.80);



        }


    }







    public void onClick(View v)
    {

        switch (v.getId())
        {

            case R.id.txtNuevoPaciente:

                new EfectoTxtClick(getContext(),txt_nuevo_paciente,R.animator.txt_color_efecto2,80);
                mListener.onFragmentInteraction("fragment_anadir_paciente");

                break;

            case R.id.logo_paciente:

                ColorStateList csl = AppCompatResources.getColorStateList(getContext(), R.color.ColorSecundadrio);
                Drawable drawable = DrawableCompat.wrap(logo_paciente.getDrawable());
                DrawableCompat.setTintList(drawable, csl);
                logo_paciente.setImageDrawable(drawable);
                mListener.onFragmentInteraction("fragment_anadir_paciente");



                break;



            case R.id.txtVer:


                Log.d("clic","ver");



                mListener.onFragmentInteraction("fragment_prueba_graficos");

                break;












            case R.id.relativeLayout_btnPestanaIzquierda:
                Log.d("click","soy pestaña izquierda superior");


                cambiarEstadoPestanaPaciente(contenedor_btn_pestana_izquierda,R.drawable.border_btn_below_pestana_activa,R.drawable.border_btn_below_pestana_activa);
                cambiarEstadoPestanaPaciente(contenedor_btn_pestana_derecha,R.drawable.border_btn_below_pestana_inactiva,R.drawable.border_btn_below_pestana_inactiva);

                mListener.asignarTituloToolbar(getResources().getString(R.string.ficha));


                new EfectoTxtClick(getContext(),btn_pestana_izquierda.txtNamePestana,R.animator.txt_color_efecto2,80);


                getActivity().onBackPressed();



               // mListener.onMispacientesFragmentAdapter(mPaciente,"fragment_edit_paciente");

                break;

            case R.id.relativeLayout_btnPestanaDerecha:

                Log.d("click","soy pestaña derecha superior");

                cambiarEstadoPestanaPaciente(contenedor_btn_pestana_izquierda,R.drawable.border_btn_below_pestana_inactiva,R.drawable.border_btn_below_pestana_inactiva);
                cambiarEstadoPestanaPaciente(contenedor_btn_pestana_derecha,R.drawable.border_btn_below_pestana_activa,R.drawable.border_btn_below_pestana_activa);

                mListener.asignarTituloToolbar(getResources().getString(R.string.mediciones));

                new EfectoTxtClick(getContext(),btn_pestana_derecha.txtNamePestana,R.animator.txt_color_efecto2,80);








                break;





           /* case R.id.layout_btnIzquierdaAsignancion:
                Log.d("click","soy pestaña izquierda superior");

                new EfectoTxtClick(getContext(),btn_izquierda_asignacion.txtBtnName,R.animator.txt_color_efecto,80);

                break;

            case R.id.layout_btnDerechaAsignacion:

                Log.d("click","soy pestaña derecha superior");

                new EfectoTxtClick(getContext(),btn_derecha_asignacion.txtBtnName,R.animator.txt_color_efecto,80);

                break;
                */
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    //En este método lo utilizamos para delegar el funcionamiento de pulsar el botón atrás.
    //De tal forma que al pulsar atras consigamos eliminar el ultimo fragmento de la pila de transiciones
    //y hacerlo volver una posición atrás.

    @Override
    public void onResume() {

        super.onResume();

        View view = getView();
        if (view != null) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                        // handle back button

                        //Log.d("click","pulsadoo");

                       // getActivity().onBackPressed();

                        //getActivity().onBackPressed();

                        ((HomeActivity) getActivity()).eliminarFragmentoDePilaTransicion("fragment_mis_mediciones");

                        getActivity().onBackPressed();

                        return true;

                    }
                    else
                    {     //Log.d("click","no pulsado");
                        return false;
                    }




                }
            });
        }




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

        void onMispacientesFragmentAdapter(Paciente paciente, String tagFragment);

        void asignarTituloToolbar(String titulo);
    }
}
