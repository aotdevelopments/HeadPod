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
import com.siestasystemheadpod.headpodv10.fragments.BotonPestanaFragment;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MisPacientesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MisPacientesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MisPacientesFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    public MisPacientesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MisPacientesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MisPacientesFragment newInstance(String param1, String param2) {
        MisPacientesFragment fragment = new MisPacientesFragment();
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

        sharedPreferSession= new SharedPreferSession(getContext());

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        ((HomeActivity)getActivity()).cambiarIdioma();
        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_mis_pacientes, null);

        setContenidoVista(view);

        frameLayout .addView(view);
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        frameLayout = new FrameLayout(getActivity());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_pacientes, container, false);

        setContenidoVista(view);

        frameLayout .addView(view);
        //return view;
        return frameLayout;
    }


    public void setContenidoVista(View view )
    {


        TextView titulo= (TextView)((HomeActivity) getActivity()).findViewById(R.id.toolbar_title);
        titulo.setText(getResources().getString(R.string.mis_pacientes));
        // titulo.setText("datos en tiempo real");

        //contenedor_btn_izquierda_asignacion = (RelativeLayout) view.findViewById(R.id.layout_btnIzquierdaAsignancion);
        //contenedor_btn_derecha_asignacion = (RelativeLayout) view.findViewById(R.id.layout_btnDerechaAsignacion);

        //1fila
        //*******************************************************************************************
        EditText edit_buscar_paciente = (EditText) view.findViewById(R.id.editTextBuscarPaciente);
        ImageView ojo_found = (ImageView) view.findViewById(R.id.ojo_buscar);
        //*********************************************************************************************


        //2fila
        //*******************************************************************************************
        logo_paciente = (ImageView) view.findViewById(R.id.logo_paciente);
        txt_nuevo_paciente = (TextView) view.findViewById(R.id.txtNuevoPaciente);
        //*******************************************************************************************

        //Aviso de que debe seleccionar un paciente
        //******************************************************************************************
        TextView txt_select_aviso_paciente = (TextView) view.findViewById(R.id.txtAvisoSeleccion);


        //******************************************************************************************



        //3fila
        //*******************************************************************************************



        ImageView imgCheckBox = (ImageView) view.findViewById(R.id.checkboxImg);
        ImageView imgPerfil = (ImageView) view.findViewById(R.id.imgPerfil);

        ImageView triangulo = (ImageView) view.findViewById(R.id.triangulo);
        final TextView txt_nombre = (TextView) view.findViewById(R.id.txtNombre);
        ImageView imgCabezaShp = (ImageView) view.findViewById(R.id.cara_sin_hp);
        ImageView imgCabezaChp = (ImageView) view.findViewById(R.id.cara_con_hp);
        //*******************************************************************************************



        //4fila y demas (listview)
        //*******************************************************************************************
        ListView listViewPacientes = (ListView) view.findViewById(R.id.listViewPacientes);
        TextView txtPacientesEmpty = (TextView) view.findViewById(R.id.txtPacientesEmpty);

        //*******************************************************************************************

        //Aplicacmos la fuente
        //*******************************************************************************************
        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);

        edit_buscar_paciente.setTypeface(myFont);
        txt_nombre.setTypeface(myFont);
        txt_nuevo_paciente.setTypeface(myFont);
        txt_select_aviso_paciente.setTypeface(myFont);
        txtPacientesEmpty.setTypeface(myFont);

        //*******************************************************************************************


        //Método para escoger los tamaños de la cabezera de la lista dinámica
        scaleHeadListPacientes(imgCheckBox,imgPerfil,imgCabezaShp,imgCabezaChp);



        //instaciamos la base de datos
        crudDbHeadpod = CrudDbHeadpod.obtenerInstancia(getContext());

        //Buscamos los pacientes correspondientes al terapeuta que ha iniciado sesión
        //****************************************************************************************************************************

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
            ListenerEditeTextBuscarPaciente(edit_buscar_paciente,listViewPacientes);
            //**************************************************************


            //Nota importante:
            //**********************************************************
            //En el contenedor raiz del listview, importante añadir:
            //android:descendantFocusability="blocksDescendants"
            //De lo contrario no funciona.
            //Escuchamos el listview
            //******************************************************************************************
            listenerListviewPacientes(listViewPacientes);
            //******************************************************************************************************


        }
        else
        {

            txtPacientesEmpty.setVisibility(View.VISIBLE);
        }

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
        txt_nuevo_paciente.setOnClickListener(this);
        logo_paciente.setOnClickListener(this);
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

        void onMispacientesFragmentAdapter(Paciente paciente , String tagFragment );
    }
}
