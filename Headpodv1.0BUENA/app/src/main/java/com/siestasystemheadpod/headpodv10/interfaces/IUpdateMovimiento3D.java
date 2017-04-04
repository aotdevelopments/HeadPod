package com.siestasystemheadpod.headpodv10.interfaces;

import com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos.Aceleracion;
import com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos.AnguloY;
import com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos.AnguloZ;
import com.siestasystemheadpod.headpodv10.fragments.Cara3DFragment;

/**
 * Created by Michael on 29/03/2017.
 * Interfaz para declarar los métodos para el movimiento de la cabeza en 3D con el sensor.
 */

public interface IUpdateMovimiento3D {

    //Se encargara de actualizar el movimiento relacionado con el ángulo de la flexión
    public void updateFlexion3D();

    //Se encargara de actualizar el movimiento relacionado con el ángulo de la inclinación
    public void updateInclinacion3D();


}
