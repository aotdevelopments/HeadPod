package com.siestasystemheadpod.headpodv10.adicionales;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import com.siestasystemheadpod.headpodv10.HomeActivity;

/**
 * Created by Michael on 04/11/2016.
 *
 * Esta clase se encargara de hacer clickeable una parte del texto de un string
 *
 */
public abstract class PartTextViewClicklabe extends ClickableSpan {

    private boolean mIsPressed=false;
    private int mPressedBackgroundColor;
    private int mNormalTextColor;
    private int mPressedTextColor;
    private int mRecursoImagen;
    private int iStart;
    private int iEnd;

    private HomeActivity activity;


    public PartTextViewClicklabe(int recursoImagen, int normalTextColor, int pressedTextColor, int pressedBackgroundColor, HomeActivity activity, int iStart, int iEnd) {

        mRecursoImagen=recursoImagen;
        mNormalTextColor = normalTextColor;
        mPressedTextColor = pressedTextColor;
        mPressedBackgroundColor = pressedBackgroundColor;
        this.activity =activity;
        this.iStart=iStart;
        this.iEnd=iEnd;

        //setPressed(false);
    }

    public PartTextViewClicklabe(int recursoImagen, int normalTextColor, int pressedTextColor, int pressedBackgroundColor, HomeActivity activity) {

        mRecursoImagen=recursoImagen;
        mNormalTextColor = normalTextColor;
        mPressedTextColor = pressedTextColor;
        mPressedBackgroundColor = pressedBackgroundColor;
        this.activity =activity;
        this.iStart=iStart;
        this.iEnd=iEnd;

        //setPressed(false);
    }

    public void setPressed(boolean isSelected) {
        mIsPressed = isSelected;
    }


    @Override
    public void onClick(View textView)
    {
        //setPressed(true);

        String ruta="android.resource://com.siestasystemheadpod.headpodv10/" + mRecursoImagen;
        DialogImagenMuestra imagenMuestra = DialogImagenMuestra.newInstance(ruta);
        imagenMuestra.show(activity.getSupportFragmentManager(),"showDialogImagen");

    }


    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setColor(mNormalTextColor);
        //ds.setColor(mIsPressed ? mPressedTextColor : mNormalTextColor);
       // ds.bgColor = mIsPressed ? mPressedBackgroundColor : 0xffeeeeee;//Para el background
        //ds.setColor(ContextCompat.getColor(context,R.color.txt_efectos));
        ds.setUnderlineText(true);
    }



    public interface IClickSpannableListener{
        void onClickSpannText(String text,int starts,int ends);
    }
}





//En lugar de crear esta clase se podría haber usado esta otra clase predefinida
//Hacemos clicleable al texto "ver foto"
        /*ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // startActivity(new Intent(MyActivity.this, NextActivity.class));

                Log.d("valor","clic_ver_foto2");
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                //super.updateDrawState(ds);//color verdoso
                ds.setUnderlineText(true);
            }
        };*/





        /*
        //Hacemos clicleable al texto "ver foto"
        ClickableSpan clickableSpan = new ClickableSpan() {
            boolean presionado = false;
            @Override
            public void onClick(View textView) {
                // startActivity(new Intent(MyActivity.this, NextActivity.class));

                presionado=true;

                Log.d("valor","clic_ver_foto");
            }


            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds); //Color verdoso.

                int color_presionado = ContextCompat.getColor(getContext(),R.color.rojo_ocuro);
                int color_normal = ContextCompat.getColor(getContext(),R.color.amarrillo);

                ds.setColor(presionado ? color_presionado : color_normal);//pres?? :presionado: normal
               // ds.bgColor = mIsPressed ? mPressedBackgroundColor : 0xffeeeeee;


                ds.setUnderlineText(true);
            }
        };

        */