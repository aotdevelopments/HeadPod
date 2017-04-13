package com.siestasystemheadpod.headpodv10.fragments.rasgos.element.bar.config;

import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.bar.IndicatorBar;

/**
 * Created by plaftware
 */

public class IncludeConfig implements IndicatorBar.Config{

    @Override
    public int getBottomPercentaje() {
        return 7;
    }

    @Override
    public float getStrokePercentaje() {
        return 0.5f;
    }

    @Override
    public float getTextCenterPercentaje() {
        return 1.121f;
    }

    @Override
    public float getLineGBPercentaje() {
        return 3.0525f;
    }

    @Override
    public float getTextGBPercentaje() {
        return 3.663f;
    }
}
