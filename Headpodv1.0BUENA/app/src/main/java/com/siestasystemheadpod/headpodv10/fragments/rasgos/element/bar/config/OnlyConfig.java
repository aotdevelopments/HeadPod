package com.siestasystemheadpod.headpodv10.fragments.rasgos.element.bar.config;

import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.bar.IndicatorBar;

/**
 * Created by plaftware
 */

public class OnlyConfig implements IndicatorBar.Config {

    @Override
    public int getBottomPercentaje() {
        return 40;
    }

    @Override
    public float getStrokePercentaje() {
        return 3f;
    }

    @Override
    public float getTextCenterPercentaje() {
        return 5.521f;
    }

    @Override
    public float getLineGBPercentaje() {
        return 17.0525f;
    }

    @Override
    public float getTextGBPercentaje() {
        return 19.663f;
    }
}
