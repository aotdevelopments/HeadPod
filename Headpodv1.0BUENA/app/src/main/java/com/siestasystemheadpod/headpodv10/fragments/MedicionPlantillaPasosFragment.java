package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.SharedPreferSession;
import com.siestasystemheadpod.headpodv10.adicionales.DialogSetTimeMedition;
import com.siestasystemheadpod.headpodv10.adicionales.EfectoTxtClick;
import com.siestasystemheadpod.headpodv10.adicionales.PartTextViewClicklabe;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.modelo.Paciente;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;

/*
ESTE FRAGMENTO CONTENDRA LA ESTRUTURA DE LA INTERFAZ GRAFICA DE :
-MEDICIÓN SIN HEADPOD
-MEDICIÓN CON HEADPOD
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MedicionPlantillaPasosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MedicionPlantillaPasosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public  class MedicionPlantillaPasosFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String valor; //Valor del texto del tiempo seleccionado

    private  TextView txtMedir;
    private TextView txtTiempoSelect;
    private TextView txtOtroTiempo;

    private OnFragmentInteractionListener mListener;

    public  DiscreteSeekBar discreteSeekBar1;
    private Editable auxValorTxtTimeSelect= null;


    private   CrudDbHeadpod crudDbHeadpod;

    private SharedPreferSession sharedPreferSession;


    private ArrayList<Long> idPacientesDeTerapeuta = new ArrayList<Long>();

    private String pacienteSeleccionado;

    private TextView txt_paso3_1;


    //Para el dialgod del tiempo
    private  DialogSetTimeMedition dialog;

    //Atributos para manipular DiscreteSeekBar
    //*************************************************************

    //OJO: num_horas debe ser 24 e intervarlo_horas debe ser 1,
    //sino ajustar formula de maxprogress.
    private final int NUM_MINUTOS=3;//el número de minutos que tendra la barra (3)-> 15, 30, 45
    private final int INTERVARLO_MIN=15;//el intervarlo de minutos 15, 30 ,45
    private final int NUM_HORAS=24;
    private final int INTERVARLO_HORAS=1;//si es 1 el intervarlo de horas sería 1,2,3,4-eL INTERVARLO DE HORAS SIEMPRE TIENE QUE SER UNO
    private final int NUM_DIAS=4;//NÚMERO DE DIAS
    private final int INTERVARLO_DIAS=1;//

    private final int MINPROGRESS=1;//min valor del progreso
    private final int MAXPROGRESS=NUM_MINUTOS+NUM_HORAS+NUM_DIAS-1;//maximo valor del progreso


    //*******************************************************************
    //Tiempo seleccionado
    private int diasSelect=MAXPROGRESS;//Inicializamos con el máximo del progreso.
    private int horasSelect=0;
    private int minutosSelect=0;

    public MedicionPlantillaPasosFragment() {
        // Required empty public constructor
    }


    public int getMAXPROGRESS()
    {
        return MAXPROGRESS;
    }
    public int getNumDiasMax()
    {
        return  NUM_DIAS;
    }


    public int getNumHorasMax()
    {
        return  NUM_HORAS;
    }

    public int getNumMinutosMax()
    {
        return  NUM_MINUTOS;
    }

    public int getIntervarloMin()
    {
        return  INTERVARLO_MIN;
    }


    public TextView getTxtTiempoSelect()
    {
        return txtTiempoSelect;
    }

    public void setTxtTiempoSelect(String valor)
    {
        txtTiempoSelect.setText(valor);
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
    public static MedicionPlantillaPasosFragment newInstance(String param1, String param2) {
        MedicionPlantillaPasosFragment fragment = new MedicionPlantillaPasosFragment();
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

        crudDbHeadpod= CrudDbHeadpod.obtenerInstancia(getContext());
        sharedPreferSession= new SharedPreferSession(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        //Obtenemos el paciente seleccionado.
        getPacienteSeleccionado();



        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_medicion_plantilla_pasos, container, false);

        TextView uno= (TextView) view.findViewById(R.id.Uno);
        TextView txt_paso1 = (TextView) view.findViewById(R.id.txtUnoMedicion);
        TextView dos = (TextView) view.findViewById(R.id.Dos);
        TextView txt_paso2 = (TextView) view.findViewById(R.id.txtDosMedicion);
        TextView tres = (TextView) view.findViewById(R.id.Tres);
        TextView txt_paso3= (TextView) view.findViewById(R.id.txtTresMedicion);
        txt_paso3_1 = (TextView) view.findViewById(R.id.txtTresMedicionSeleccion);
        TextView txt_paso3_2= (TextView) view.findViewById(R.id.txtTresMedicionPacienteSeleccion);
        TextView txt_paso3_3 = (TextView) view.findViewById(R.id.txtTresMedicionNombrePaciente);

        TextView cuatro = (TextView) view.findViewById(R.id.Cuatro);
        TextView txt_paso4 = (TextView) view.findViewById(R.id.txtCuatroMedicion);


        txtMedir= (TextView) view.findViewById(R.id.txtMedir);
        ImageView btnMedir = (ImageView) view.findViewById(R.id.btnMedir);
        txtTiempoSelect = (TextView) view.findViewById(R.id.tiempoSelect);
        txtOtroTiempo= (TextView) view.findViewById(R.id.otro_tiempo);

        ImageView cara_con_sin_headpod = (ImageView) view.findViewById(R.id.cara_sin_con_hp);

        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);

        uno.setTypeface(myFont);
        dos.setTypeface(myFont);
        tres.setTypeface(myFont);
        cuatro.setTypeface(myFont);
        txt_paso1.setTypeface(myFont);
        txt_paso2.setTypeface(myFont);
        txt_paso3.setTypeface(myFont);
        txt_paso3_1.setTypeface(myFont);
        txt_paso3_2.setTypeface(myFont);
        txt_paso3_3.setTypeface(myFont);
        txt_paso4.setTypeface(myFont);

        txtOtroTiempo.setTypeface(myFont);
        txtMedir.setTypeface(myFont);
        txtTiempoSelect.setTypeface(myFont);

        if(mParam1.equals("fragment_medicion_sin_headpod"))
        {
            TextView titulo= (TextView)((HomeActivity) getActivity()).findViewById(R.id.toolbar_title);
            titulo.setText(R.string.medicion_sin_hp);

            Spannable spannable = new SpannableString(txt_paso1.getText().toString());

            String str = spannable.toString();

            int iStart = str.indexOf(getResources().getString(R.string.fije_sensor));//Fije el sensor
            int iEnd = iStart +14;/*10 characters = Fije el sensor. */

            SpannableString ssText = new SpannableString(spannable);
            //Para dar el color
            ssText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.ColorSecundadrio)), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            iStart = str.indexOf(getResources().getString(R.string.parentisis_ver_foto));//(ver foto
            iEnd = iStart+10;


            int color_presionado = ContextCompat.getColor(getContext(),R.color.dsb_ripple_color_focused);
            int color_normal = ContextCompat.getColor(getContext(),R.color.ColorSecundadrio);
            int color_background = ContextCompat.getColor(getContext(),R.color.color_pestana_presionada);//No lo estoy usando

            //CREAMOS EL DIALOG EMERGENTE PARA LA IMAGEN DE MUESTRA
            PartTextViewClicklabe clickableSpan = new PartTextViewClicklabe(R.drawable.sensor,color_normal,color_presionado,color_background,(HomeActivity)getActivity(),iStart,iEnd) {
                //Aquí podría sobreescribir los métodos de la clase si lo deseo
            };

            ssText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.ColorSecundadrio)), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssText.setSpan(clickableSpan,iStart,iEnd,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);



            iStart = str.indexOf(getResources().getString(R.string.parentisis_ver_foto),70);//A partir de 70.//"(ver foto"
            iEnd = iStart+10;

            //color_presionado = ContextCompat.getColor(getContext(),R.color.dsb_ripple_color_focused);
            //color_normal = ContextCompat.getColor(getContext(),R.color.ColorSecundadrio);
            //color_background = ContextCompat.getColor(getContext(),R.color.color_pestana_presionada);//No lo estoy usando

            //CREAMOS EL DIALOG EMERGENTE PARA LA IMAGEN DE MUESTRA
            clickableSpan = new PartTextViewClicklabe(R.drawable.nino_senal_sensor_v3,color_normal,color_presionado,color_background,(HomeActivity)getActivity()) {
                //Aquí podría sobreescribir los métodos de la clase si lo deseo
            };

            //Para dar el color
            //ssText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.ColorSecundadrio)), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssText.setSpan(clickableSpan,iStart,iEnd,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);


            txt_paso1.setText(ssText);


            //Necesario para hacer clikleable las partes del texto deseadas ("ver foto")
            txt_paso1.setMovementMethod(LinkMovementMethod.getInstance());
            txt_paso1.setHighlightColor(Color.TRANSPARENT);


            spannable = new SpannableString(txt_paso4.getText().toString());
            str = spannable.toString();
            iStart = str.indexOf(getResources().getString(R.string.cabeza));//cabeza
            iEnd = iStart +6;
            ssText = new SpannableString(spannable);

            ssText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.ColorSecundadrio)), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            iStart = str.indexOf(getResources().getString(R.string.neutral));//neutral
            iEnd = iStart+7;

            ssText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.ColorSecundadrio)), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            txt_paso4.setText(ssText);

        }
        else
        {

            TextView titulo= (TextView)((HomeActivity) getActivity()).findViewById(R.id.toolbar_title);
            titulo.setText(R.string.medicion_con_hp);

            txt_paso1.setText(getResources().getString(R.string.uno_con_headpod));//Cambiamos el paso1.
            cara_con_sin_headpod.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.calibracion_con_headpod));//Cambiamos la imagen.

            Spannable spannable = new SpannableString(txt_paso4.getText().toString());
            String str = spannable.toString();
            int iStart = str.indexOf("cabeza");
            int iEnd = iStart +6;
            SpannableString ssText = new SpannableString(spannable);

            ssText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.ColorSecundadrio)), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            iStart = str.indexOf("neutral");
            iEnd = iStart+7;

            ssText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.ColorSecundadrio)), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            txt_paso4.setText(ssText);

        }


        //Colocamos el paciente seleccionado
        txt_paso3_3.setText(pacienteSeleccionado);



        //escuchamos el click de seleccionar paciente
        txt_paso3_1.setOnClickListener(this);

        //Barra para escoger el tiempo:
        discreteSeekBar1 = (DiscreteSeekBar) view.findViewById(R.id.discrete1);
        discreteSeekBar1.setMin(MINPROGRESS);
        discreteSeekBar1.setMax(MAXPROGRESS);
        discreteSeekBar1.setProgress(MAXPROGRESS);

        discreteSeekBar1.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            //int progress=0;
            int progress_valor;
            int valueant;//valor para saber si incrementar o decrementar el progress_valor
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                //progress=value;

                if (seekBar.getProgress() > NUM_MINUTOS) {
                      if (valueant > seekBar.getProgress()) {
                            progress_valor = seekBar.getProgress() - (NUM_MINUTOS-1);//-(3-1)

                          progress_valor--;
                          progress_valor=progress_valor*INTERVARLO_HORAS;
                        } else {

                            progress_valor = seekBar.getProgress() - (NUM_MINUTOS+1);//-(3+1)
                            progress_valor++;
                          progress_valor=progress_valor*INTERVARLO_HORAS;
                        }
                }

            }

            @Override
            public void onStartTrackingTouch(final DiscreteSeekBar seekBar) {
                valueant=seekBar.getProgress();

                if(seekBar.getProgress()==MAXPROGRESS)
                {

                    progress_valor=(MAXPROGRESS-NUM_MINUTOS)*INTERVARLO_HORAS;

                }
                else if(seekBar.getProgress()<=NUM_MINUTOS)
                {
                    progress_valor=0;
                }

                seekBar.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {

                    @Override
                    public int transform(int value) {

                        return value;
                    }

                    @Override
                    public String transformToString(int value) {
                        String valor;

                        if(value*INTERVARLO_MIN>59)
                        {
                                if (progress_valor <NUM_HORAS) {

                                    valor = String.valueOf(progress_valor) + " " + getResources().getQuantityString(R.plurals.horas,progress_valor);//hora o horas
                                } else {

                                    if(seekBar.getProgress()==MAXPROGRESS)
                                    {
                                     valor = "       "+getResources().getString(R.string.indefinido)+ "       ";
                                    }
                                    else {
                                        valor = "     "+String.valueOf((progress_valor - (NUM_HORAS - 1)) * INTERVARLO_DIAS) + " " + getResources().getQuantityString(R.plurals.dias, (progress_valor - (NUM_HORAS - 1)) * INTERVARLO_DIAS) + "    ";//día ó días
                                    }

                                }
                        }
                        else
                        {

                            valor= String.valueOf(value*INTERVARLO_MIN)+" "+getResources().getString(R.string.min);//min
                        }

                        return valor;
                    }



                    @Override
                    public boolean useStringTransform() {

                        return true;
                    }


                });


            }
            @Override
            public void onStopTrackingTouch(final DiscreteSeekBar seekBar) {

                if(seekBar.getProgress()*INTERVARLO_MIN>59)
                {
                    if (progress_valor <NUM_HORAS) {

                        valor =getResources().getString(R.string.tiempo_dos_puntos)+" "+ String.valueOf(progress_valor) + " " + getResources().getQuantityString(R.plurals.horas,progress_valor);//hora o horas
                        horasSelect=progress_valor;
                        minutosSelect=0;
                        diasSelect=0;
                    } else {

                        if(seekBar.getProgress()==MAXPROGRESS)
                        {
                            valor =getResources().getString(R.string.tiempo_max);
                            diasSelect=MAXPROGRESS;
                            horasSelect=0;
                            minutosSelect=0;
                        }
                        else {
                            valor = getResources().getString(R.string.tiempo_dos_puntos)+" "+String.valueOf((progress_valor - (NUM_HORAS - 1)) * INTERVARLO_DIAS) + " " + getResources().getQuantityString(R.plurals.dias, (progress_valor - (NUM_HORAS - 1)) * INTERVARLO_DIAS) + "    ";//día ó días
                            diasSelect=(progress_valor - (NUM_HORAS - 1)) * INTERVARLO_DIAS;
                            horasSelect=0;
                            minutosSelect=0;
                        }

                    }
                }
                else
                {
                    valor=getResources().getString(R.string.tiempo_dos_puntos)+" "+  String.valueOf(seekBar.getProgress()*INTERVARLO_MIN)+" "+getResources().getString(R.string.min);//min
                    minutosSelect=seekBar.getProgress()*INTERVARLO_MIN;
                    horasSelect=0;
                    diasSelect=0;
                }

                txtTiempoSelect.setText(valor);
            }//fin stop

        });
        //listener boton_medir



        //Esto lo hacemos para no perder el valor del tiempo seleccionado del textView al
        // presionar el botoón de volver atrás.
        //****************************************************************************************
        txtTiempoSelect.addTextChangedListener(new TextWatcher() {
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
                auxValorTxtTimeSelect=s;

            }


        });

        if(auxValorTxtTimeSelect!=null)
        {
            txtTiempoSelect.setText(auxValorTxtTimeSelect.toString());
        }
        //*************************************************************************************************

        btnMedir.setOnClickListener(this);
        txtOtroTiempo.setOnClickListener(this);

        return view;
    }



    //Metodo para obtener el paciente seleccionado.
    private void getPacienteSeleccionado()
    {

        idPacientesDeTerapeuta = crudDbHeadpod.getID_AllPacientesOfTerapeuta(sharedPreferSession.getUserEmail());

        if(idPacientesDeTerapeuta!=null) {
            int chiv = 0;
            for (int i = 0; i < idPacientesDeTerapeuta.size(); i++) {
                Paciente paciente = crudDbHeadpod.getPacientebyID(idPacientesDeTerapeuta.get(i));

                if (paciente.getSeleccionado() == 1) {

                    pacienteSeleccionado = paciente.getNombre() + " " + paciente.getApellido();
                    chiv = 1;
                    break;
                }
            }

            if (chiv == 0) {
                pacienteSeleccionado = getString(R.string.ninguno);
            }
        }
        else
        {
            pacienteSeleccionado =getString(R.string.ninguno);
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
    }


    public void onClick(View v)
    {


        switch (v.getId())
        {
            case R.id.txtUnoMedicion:



                Log.d("valor","clic");


                break;

            case R.id.otro_tiempo:
                new EfectoTxtClick(getContext(), txtOtroTiempo,R.animator.txt_color_efecto2,80);

                if (dialog==null)
                {
                    dialog =DialogSetTimeMedition.newInstance(0,0,INTERVARLO_MIN);
                    dialog.show(getActivity().getSupportFragmentManager(),"setTimePiker");
                }
                else
                {

                    dialog=DialogSetTimeMedition.newInstance(dialog.getNpDias().getValue(),dialog.getNpHoras().getValue(),dialog.getNpMinutos().getValue());
                    dialog.show(getActivity().getSupportFragmentManager(),"setTimePiker");
                }

                break;

            case R.id.btnMedir:
                //efecto boton en este caso falla, averiguarlo en otro momento.
                new EfectoTxtClick(getContext(), txtMedir);


                if(mParam1.equals("fragment_medicion_sin_headpod")) {

                    mListener.onFragmentInteraction("fragment_medicion_sin_headpod_iniciar");//fragmento fictisio
                }
                else
                {
                    mListener.onFragmentInteraction("fragment_medicion_con_headpod_iniciar");//fragmento fictisio
                }


                Log.d("valores: ","dias: "+String.valueOf(diasSelect)+" horas: "+String.valueOf(horasSelect)+" min: "+String.valueOf(minutosSelect));
                break;


            case R.id.txtTresMedicionSeleccion:

                new EfectoTxtClick(getContext(),txt_paso3_1,R.animator.txt_color_efecto2,80);

                mListener.onFragmentInteraction("fragment_mis_pacientes");


        }

        /*
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        //Paso 1: Obtener la instancia del administrador de fragmentos
        fragmentManager = ((HomeActivity) getActivity()).getSupportFragmentManager();
        AllAvatarFragment fragment = (AllAvatarFragment) fragmentManager.findFragmentByTag("fragment_all_avatar");//fragment_all_avatar


        //Paso 2: Crear una nueva transacción
        fragmentTransaction = fragmentManager.beginTransaction();

        //Paso 3: Crear un nuevo fragmento y añadirlo
        //Creamos el fragmento contenedor de todos los fragmentos de la vista avatar.
        AllAvatarFragment all_fragments_avatar = AllAvatarFragment.newInstance("fragment_all_avatar","con_barra_de_medicion");//new AllAvatarFragment();
        fragmentTransaction.replace(R.id.contenedorHome, all_fragments_avatar ,"fragment_all_avatar");//R.id.contenedorHome
        fragmentTransaction.addToBackStack(null);
        //fragmentTransaction.commitAllowingStateLoss();
        fragmentTransaction.commit();
    */


    }




    public int getDiasSelect()
    {
    return diasSelect;
    }
    public int getHorasSelect()
    {
        return horasSelect;
    }

    public int getMinutosSelect()
    {
        return minutosSelect;
    }

    public void setDiasSelect( int dias)
    {
        diasSelect=dias;
    }
    public void setHorasSelect(int horasSelect)
    {
        this.horasSelect=horasSelect;

    }
    public void setMinutosSelect(int minutosSelect)
    {
        this.minutosSelect=minutosSelect;
    }


}//fin class



/*
      case R.id.otro_tiempo:
                new EfectoTxtClick(getContext(), txtOtroTiempo,R.animator.txt_color_efecto2,80);

               // TimerPickerFragment newFragment = new TimerPickerFragment();

               // newFragment.show(getActivity().getSupportFragmentManager(),"timePicker");


*/
               /*
                Dialog dialog= new TimePickerDialog(getActivity(), this, horas, minutos,
                        DateFormat.is24HourFormat(getActivity()));

                dialog.setTitle(getResources().getString(R.string.establecer_tiempo));
                dialog.show();
                */





/*

      @Override
                    public String transformToString(int value) {
                        String valor;

                        if(value*INTERVARLO_MIN>59)
                        {
                            if(value==NUM_MINUTOS+1)
                            {

                                if(INTERVARLO_HORAS==1) {
                                    valor = String.valueOf(progress_valor) + " " + getResources().getString(R.string.hora);//hora
                                }

                            }
                            else {

                                if (progress_valor <NUM_HORAS) {

                                    valor = String.valueOf(progress_valor) + " " + getResources().getString(R.string.horas);//horas
                                } else {

                                    if(progress_valor==NUM_HORAS)
                                    {
                                        if(INTERVARLO_DIAS==1)
                                        {
                                            valor = String.valueOf((progress_valor-(NUM_HORAS-1))*INTERVARLO_DIAS) + " " + getResources().getString(R.string.dia)+"    ";//horas
                                        }

                                    }
                                    else
                                    {
                                        valor = String.valueOf((progress_valor-(NUM_HORAS-1))*INTERVARLO_DIAS) + " " + getResources().getString(R.string.dias)+"    ";//horas


                                    }


                                }
                            }

                        }
                        else
                        {

                            valor= String.valueOf(value*INTERVARLO_MIN)+" "+getResources().getString(R.string.min);//min
                        }

                        return valor;
                    }


 */


// Apply the adapter to the spinner
// spinner.setAdapter(adapter);//***************************

//Otra forma de hacerlo:
//String[] years = {"1996","1997","1998","1998"};

//set the default according to value
//spinner.setSelection(3);//,true//*******************************





//  seekBar = (SeekBar) view.findViewById(R.id.seekBar);
// ediTxtTime = (EditText) view.findViewById(R.id.editTextTime);



//textView.setText("Covered: " + seekBar.getProgress() + "/" + seekBar.getMax());

//TimePickerDialog.OnTimeSetListener

/*        seekBar.setProgress(100);
        if(100==seekBar.getProgress())
        {
            ediTxtTime.setText(getResources().getString(R.string.indefinido));
        }
        else
        {
            String valor = Integer.toString(seekBar.getProgress());
            ediTxtTime.setText(valor);
        }




        ViewGroup.LayoutParams paramBtnIzqdaArriba =seekBar.getLayoutParams();
        paramBtnIzqdaArriba.width =  (int) (((HomeActivity) getActivity()).getWidthPixels()*0.54);





        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
               // Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
               // textView.setText("Covered: " + progress + "/" + seekBar.getMax());
                if(progress==100)
                {
                    ediTxtTime.setText(getResources().getString(R.string.indefinido));
                }

                else if(0==progress)
                {
                    String valor = Integer.toString(progress+1);
                    ediTxtTime.setText(valor);
                }
                else
                {
                    String valor = Integer.toString(progress);
                    ediTxtTime.setText(valor);
                }
               // Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }

        });



        ediTxtTime.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                // you can call or do what you want with your EditText here
                if (s.length() > 0) {
                    //ediTxtTime.setText(c);
                    //tiempoTecleado=ediTxtTime.getText().toString();
                    String valor = ediTxtTime.getText().toString();

                    if(valor.equals("100") || valor.equals(getResources().getString(R.string.indefinido)) )
                    {
                            seekBar.setProgress(100);
                           // ediTxtTime.setText(getResources().getString(R.string.indefinido));
                    }
                    else
                    {
                        seekBar.setProgress(Integer.valueOf(valor));
                    }
                }
                else
                {
                    seekBar.setProgress(0);
                }



            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //valorInicial=s.toString();

              //  Log.d("valor-1: ",valorInicial);

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("valor-2",ediTxtTime.getText().toString());

            }
        });


*/
