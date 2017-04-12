package com.siestasystemheadpod.headpodv10.fragments.informe;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.IndicatorBarView;
import com.siestasystemheadpod.headpodv10.fragments.rasgos.RasgosView;

/**
 * Created by plaftware
 */

public class ResultFlexionFragment extends Fragment {

    private FrameLayout frameLayout;
    private RasgosView rasgosView;
    private IndicatorBarView indicatorBar;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((HomeActivity)getActivity()).cambiarIdioma();
        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_result_flexion, null);
        setContenidoVista(view);
        frameLayout .addView(view);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        frameLayout = new FrameLayout(getActivity());
        // LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ((HomeActivity) getActivity()).setFragmentActual(this);
        //((HomeActivity) getActivity()).fragmentActual=this;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_flexion, container, false);
        setContenidoVista(view);

        frameLayout .addView(view);
        //return view;
        return frameLayout;
    }

    public void setContenidoVista(View view){
        rasgosView = (RasgosView) view.findViewById(R.id.rasgos);
        indicatorBar = (IndicatorBarView) view.findViewById(R.id.indicadorBar);
        rasgosView.setRasgosListener(indicatorBar);

        rasgosView.indicador(1, 100)
                .indicador(6, 100)
                .indicador(5, 30)
                .indicador(4, 40)
                .indicador(3, 10)
                .indicador(11, 100)
                .render();
    }
}


