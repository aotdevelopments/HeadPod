package com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco;

import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.AbstractRasgosElement;

/**
 * Created by plaftware
 */

abstract class AbstractItemArcoElement extends AbstractRasgosElement implements ArcoItemElement {

    final ArcoElement arcoElement;
    int porcentaje;

    AbstractItemArcoElement(ArcoElement arcoElement) {
        super(arcoElement.getRasgosDesigner());
        this.arcoElement = arcoElement;
        arcoElement.add(this);
    }

    /*public ArcoElement getArcoElement() {
        return arcoElement;
    }*/

    @Override
    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }
}
