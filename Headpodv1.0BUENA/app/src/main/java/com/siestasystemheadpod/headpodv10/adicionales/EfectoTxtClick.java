package com.siestasystemheadpod.headpodv10.adicionales;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.R;

/**
 * Created by Michael on 30/09/2016.
 */
public class EfectoTxtClick {//EfectoTxtClickButton

    public static final int TIEMPO_EFECTO_BACKGROUND=80;

    public static final int TIEMPO_EFECTO_COLOR_TXT=80;


    //Para dar animacion al texto
    public EfectoTxtClick(Context context, TextView txtBtn)
    {

        //Para cambiar el background
        TransitionDrawable background = (TransitionDrawable) txtBtn.getBackground();
        background.startTransition(TIEMPO_EFECTO_BACKGROUND);//drawable/txt_bacground_efecto
        background.reverseTransition(TIEMPO_EFECTO_BACKGROUND);

        //Para cambiar el color de texto
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context,
                R.animator.txt_color_efecto);
        set.setDuration(TIEMPO_EFECTO_COLOR_TXT);
        set.setTarget(txtBtn);
        set.start();

    }

    public EfectoTxtClick(Context context, TextView txtBtn, int resourceAnimator, int tiempo)
    {

        //Para cambiar el color de texto
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(context,resourceAnimator);// R.animator.txt_color_efecto
        set.setDuration(tiempo);
        set.setTarget(txtBtn);
        set.start();

    }

    //new EfectoTxtClick(getContext(), anadir_borrarImagen,R.animator.txt_color_efecto2,80);//efecto2 de azul a verde

}
