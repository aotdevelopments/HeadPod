package com.siestasystemheadpod.headpodv10.adicionales.cara2D;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.Util;

/**
 * Created by Michael on 17/03/2017.
 * Interfaz que contendra los atributos y métodos para la manipulación de las caras en 2D
 * Aquí se estableceran los limietes de los intervalos de los angulos
 * Así como los coleres que tendra la cara,
 * y en angulo de rotación
 */

public interface Cara2D {

    //Limites de los intervalos de los angulos
    float LIMITE_INICIAL=0;
    float LIMITE_VERDE=10;
    float LIMITE_AMARAILLO=20;
    float LIMITE_NARANJA=36;
    float LIMITE_ROJO=45;

    //Colores de las caras
    int COLOR_BLANCO = R.color.blanco;//Color.parseColor("#BD202E");
    int COLOR_VERDE = R.color.verde;
    int COLOR_AMARILLO= R.color.amarrillo;
    int COLOR_NARANJA = R.color.naranja;
    int COLOR_ROJO = R.color.rojo;


    void pintarCaraSegunAngulo(float angulo);
    void rotarCara(float angulo);

}
