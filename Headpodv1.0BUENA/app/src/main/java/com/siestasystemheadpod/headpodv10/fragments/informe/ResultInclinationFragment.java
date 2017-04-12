package com.siestasystemheadpod.headpodv10.fragments.informe;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.IndicatorBarView;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.RasgosView;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.bar.IndicatorBar;





/*

// get fragment manager
FragmentManager fm = getFragmentManager();

// add
FragmentTransaction ft = fm.beginTransaction();
ft.add(R.id.your_placehodler, new YourFragment());
// alternatively add it with a tag
// trx.add(R.id.your_placehodler, new YourFragment(), "detail");
ft.commit();

// replace
FragmentTransaction ft = fm.beginTransaction();
ft.replace(R.id.your_placehodler, new YourFragment());
ft.commit();

// remove
Fragment fragment = fm.findFragmentById(R.id.your_placehodler);
FragmentTransaction ft = fm.beginTransaction();
ft.remove(fragment);
ft.commit();

http://www.vogella.com/tutorials/AndroidFragments/article.html



 */


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultInclinationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ResultInclinationFragment extends Fragment implements View.OnClickListener,SeekBar.OnSeekBarChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String CALIBRI_BOLD="fonts/Calibri Bold.ttf";



    private OnFragmentInteractionListener mListener;


    //Para la configuración de la orientación. Con esto respetamos el diseño landScape
    private FrameLayout frameLayout;


    RasgosView rasgosView;

    private SparseIntArray indexSeekBar;

    public ResultInclinationFragment() {
        // Required empty public constructor
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
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        ((HomeActivity)getActivity()).cambiarIdioma();
        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_result_inclination, null);


        setContenidoVista(view);


        frameLayout .addView(view);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        frameLayout = new FrameLayout(getActivity());
        // LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ((HomeActivity) getActivity()).setFragmentActual(this);
        //((HomeActivity) getActivity()).fragmentActual=this;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_inclination, container, false);
        setContenidoVista(view);

        frameLayout .addView(view);
        //return view;
        return frameLayout;
    }


    public void setContenidoVista(View view)
    {

        //Para el titulo del menú
        //((HomeActivity) getActivity()).getSupportActionBar().setTitle("");
        TextView titulo= (TextView)((HomeActivity) getActivity()).findViewById(R.id.toolbar_title);

        titulo.setText(R.string.titulo_home);

        rasgosView = (RasgosView) view.findViewById(R.id.rasgos);

        /*indexSeekBar = new SparseIntArray();
        indexSeekBar.put(R.id.ind1, 1);
        indexSeekBar.put(R.id.ind2, 2);
        indexSeekBar.put(R.id.ind3, 3);
        indexSeekBar.put(R.id.ind4, 4);
        indexSeekBar.put(R.id.ind5, 5);
        indexSeekBar.put(R.id.ind6, 6);
        indexSeekBar.put(R.id.ind7, 7);
        indexSeekBar.put(R.id.ind8, 8);
        indexSeekBar.put(R.id.ind9, 9);
        indexSeekBar.put(R.id.ind10, 10);
        indexSeekBar.put(R.id.ind11, 11);

       ((SeekBar)view.findViewById(R.id.ind1)).setOnSeekBarChangeListener(this);
        ((SeekBar)view.findViewById(R.id.ind2)).setOnSeekBarChangeListener(this);
        ((SeekBar)view.findViewById(R.id.ind3)).setOnSeekBarChangeListener(this);
        ((SeekBar)view.findViewById(R.id.ind4)).setOnSeekBarChangeListener(this);
        ((SeekBar)view.findViewById(R.id.ind5)).setOnSeekBarChangeListener(this);
        ((SeekBar)view.findViewById(R.id.ind6)).setOnSeekBarChangeListener(this);
        ((SeekBar)view.findViewById(R.id.ind7)).setOnSeekBarChangeListener(this);
        ((SeekBar)view.findViewById(R.id.ind8)).setOnSeekBarChangeListener(this);
        ((SeekBar)view.findViewById(R.id.ind9)).setOnSeekBarChangeListener(this);
        ((SeekBar)view.findViewById(R.id.ind10)).setOnSeekBarChangeListener(this);
        ((SeekBar)view.findViewById(R.id.ind11)).setOnSeekBarChangeListener(this);*/

        // Este es un ejemplo de como pasar los datos al componente
        rasgosView.indicador(1, 100)
                .indicador(6, 100)
                .indicador(5, 30)
                .indicador(4, 40)
                .indicador(3, 10)
                .indicador(11, 100)
                .render();

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

/*
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/


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


    @Override
    public void onClick(View v) {

    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        rasgosView.indicador(indexSeekBar.get(seekBar.getId()), progress).render();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }










}
