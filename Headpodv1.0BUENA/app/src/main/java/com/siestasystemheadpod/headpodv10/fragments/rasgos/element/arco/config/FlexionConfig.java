package com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco.config;

import com.siestasystemheadpod.headpodv10.fragments.rasgos.element.arco.ArcoElement;

/**
 * Created by plaftware
 */

public class FlexionConfig implements ArcoElement.Config{

    private final int[][] INDEX_INDICATOR_GRADO;

    public FlexionConfig(){
        INDEX_INDICATOR_GRADO = new int[11][2];

        // 1 Indicador
        INDEX_INDICATOR_GRADO[0][0] = 90;
        INDEX_INDICATOR_GRADO[0][1] = 70;

        // 2 Indicador
        INDEX_INDICATOR_GRADO[1][0] = 70;
        INDEX_INDICATOR_GRADO[1][1] = 55;

        // 3 Indicador
        INDEX_INDICATOR_GRADO[2][0] = 55;
        INDEX_INDICATOR_GRADO[2][1] = 40;

        // 4 Indicador
        INDEX_INDICATOR_GRADO[3][0] = 40;
        INDEX_INDICATOR_GRADO[3][1] = 25;

        // 5 Indicador
        INDEX_INDICATOR_GRADO[4][0] = 25;
        INDEX_INDICATOR_GRADO[4][1] = 10;

        // 6 Indicador
        INDEX_INDICATOR_GRADO[5][0] = 10;
        INDEX_INDICATOR_GRADO[5][1] = -10;

        // 7 Indicador
        INDEX_INDICATOR_GRADO[6][0] = -10;
        INDEX_INDICATOR_GRADO[6][1] = -25;

        // 8 Indicador
        INDEX_INDICATOR_GRADO[7][0] = -25;
        INDEX_INDICATOR_GRADO[7][1] = -40;

        // 9 Indicador
        INDEX_INDICATOR_GRADO[8][0] = -40;
        INDEX_INDICATOR_GRADO[8][1] = -55;

        // 9 Indicador
        INDEX_INDICATOR_GRADO[9][0] = -55;
        INDEX_INDICATOR_GRADO[9][1] = -70;

        // 9 Indicador
        INDEX_INDICATOR_GRADO[10][0] = -70;
        INDEX_INDICATOR_GRADO[10][1] = -90;
    }

    @Override
    public int[] getIndicadorGrade(int index) {
        return INDEX_INDICATOR_GRADO[index];
    }

    @Override
    public int[] getArcGrade() {
        return new int[]{90, -180};
    }
}
