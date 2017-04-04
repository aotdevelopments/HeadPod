package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BarraProgresoTemporalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarraProgresoTemporalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarraProgresoTemporalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TextView txt_tiempo_avanzado;
    public TextView txt_tiempo_maximo;
    public ProgressBar barra_proreso_temporal;



    public BarraProgresoTemporalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarraProgresoTemporalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BarraProgresoTemporalFragment newInstance(String param1, String param2) {
        BarraProgresoTemporalFragment fragment = new BarraProgresoTemporalFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_barra_progreso_temporal, container, false);

        barra_proreso_temporal = (ProgressBar) view.findViewById(R.id.progressBarTime);
        txt_tiempo_avanzado = (TextView) view.findViewById(R.id.tiempo_avanzado);
        txt_tiempo_maximo  =  (TextView) view.findViewById(R.id.tiempo_maximo);

        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);
        txt_tiempo_avanzado.setTypeface(myFont);
        txt_tiempo_maximo.setTypeface(myFont);


       /* MedicionPlantillaPasosFragment msc_hp = (MedicionPlantillaPasosFragment) ((HomeActivity) getActivity()).getSupportFragmentManager().findFragmentByTag("fragment_medicion_sin_headpod");

        if(msc_hp==null)
        {
            msc_hp = (MedicionPlantillaPasosFragment) ((HomeActivity) getActivity()).getSupportFragmentManager().findFragmentByTag("fragment_medicion_con_headpod");

        }

        //Los condicionales simplemente los uso para que cumpla el formato 00:00:00 (Horas:min:segundos)
        //segundos siempre 00.
        if(msc_hp.getDiasSelect()==msc_hp.getMAXPROGRESS())
        {//Asignar tiempo máximo del dispositivo
            String valormax= String.valueOf(24*msc_hp.getNumDiasMax())+":00"+":00";
            txt_tiempo_maximo.setText(valormax);
        }
        else
        {
            int horas = msc_hp.getDiasSelect()*24+msc_hp.getHorasSelect();
            int minutos = msc_hp.getMinutosSelect();

            if(minutos==0 && horas<10)
            {
                String valormax= "0"+String.valueOf(horas)+":00"+":00";
                txt_tiempo_maximo.setText(valormax);
            }
            if(minutos==0 && horas>9)
            {
                String valormax= String.valueOf(horas)+":00"+":00";
                txt_tiempo_maximo.setText(valormax);
            }

            if (minutos<10 && horas>9)
            {
                String valormax= String.valueOf(horas)+":0"+String.valueOf(minutos)+":00";
                txt_tiempo_maximo.setText(valormax);
            }
            if(horas==0 && minutos<10)
            {
                String valormax= "00:0"+String.valueOf(minutos)+":00";
                txt_tiempo_maximo.setText(valormax);

            }

            if(horas==0 && minutos>9)
            {
                    String valormax = "00:" + String.valueOf(minutos) + ":00";
                    txt_tiempo_maximo.setText(valormax);
            }
            if(horas<10 && minutos>9)
            {
                    String valormax = "0"+String.valueOf(horas) + ":" + String.valueOf(minutos) + ":00";
                    txt_tiempo_maximo.setText(valormax);
            }
            if(horas > 9 && minutos>9) {
                    String valormax = String.valueOf(horas) + ":" + String.valueOf(minutos) + ":00";
                    txt_tiempo_maximo.setText(valormax);
            }

        }

        */

        return view;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }
}
