package com.siestasystemheadpod.headpodv10.fragments.rasgos.element;

import android.graphics.Paint;

/**
 * Created by plaftware
 */

public abstract class AbstractRasgosElement extends Paint implements RasgosElement {

    protected final RasgosDesigner rasgosDesigner;

    public AbstractRasgosElement(RasgosDesigner rasgosDesigner) {
        super(Paint.ANTI_ALIAS_FLAG);
        this.rasgosDesigner = rasgosDesigner;
        rasgosDesigner.add(this);
    }

    public RasgosDesigner getRasgosDesigner() {
        return rasgosDesigner;
    }
}
