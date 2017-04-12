package com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco.config;

import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco.ArcoElement;

/**
 * Created by plaftware
 */

public class InclinationConfig implements ArcoElement.Config {

    private final int[][] INDEX_INDICATOR_GRADO;


    public InclinationConfig(){
        INDEX_INDICATOR_GRADO = new int[11][2];

        // 1 Indicador
        INDEX_INDICATOR_GRADO[0][0] = 180;
        INDEX_INDICATOR_GRADO[0][1] = 160;

        // 2 Indicador
        INDEX_INDICATOR_GRADO[1][0] = 160;
        INDEX_INDICATOR_GRADO[1][1] = 145;

        // 3 Indicador
        INDEX_INDICATOR_GRADO[2][0] = 145;
        INDEX_INDICATOR_GRADO[2][1] = 130;

        // 4 Indicador
        INDEX_INDICATOR_GRADO[3][0] = 130;
        INDEX_INDICATOR_GRADO[3][1] = 115;

        // 5 Indicador
        INDEX_INDICATOR_GRADO[4][0] = 115;
        INDEX_INDICATOR_GRADO[4][1] = 100;

        // 6 Indicador
        INDEX_INDICATOR_GRADO[5][0] = 100;
        INDEX_INDICATOR_GRADO[5][1] = 80;

        // 7 Indicador
        INDEX_INDICATOR_GRADO[6][0] = 80;
        INDEX_INDICATOR_GRADO[6][1] = 65;

        // 8 Indicador
        INDEX_INDICATOR_GRADO[7][0] = 65;
        INDEX_INDICATOR_GRADO[7][1] = 50;

        // 9 Indicador
        INDEX_INDICATOR_GRADO[8][0] = 50;
        INDEX_INDICATOR_GRADO[8][1] = 35;

        // 9 Indicador
        INDEX_INDICATOR_GRADO[9][0] = 35;
        INDEX_INDICATOR_GRADO[9][1] = 20;

        // 9 Indicador
        INDEX_INDICATOR_GRADO[10][0] = 20;
        INDEX_INDICATOR_GRADO[10][1] = 0;
    }

    @Override
    public int[] getIndicadorGrade(int index) {
        return INDEX_INDICATOR_GRADO[index];
    }

    @Override
    public int[] getArcGrade() {
        return new int[]{0, -180};
    }
}
