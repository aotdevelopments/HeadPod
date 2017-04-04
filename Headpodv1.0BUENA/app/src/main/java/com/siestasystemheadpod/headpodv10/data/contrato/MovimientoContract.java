package com.siestasystemheadpod.headpodv10.data.contrato;

/**
 * Created by Michael on 01/09/2016.
 Esquema de la base de datos para Terapeutas
 */

import android.provider.BaseColumns;

public class MovimientoContract {

    public static abstract class MovimientoEntry implements BaseColumns{//clase interna para guardar la columna de la tabla
        public static final String TABLE_NAME ="movimiento";


        //ID
        public static final String _ID = "_id";

        //Intervalos de tiempo
        public static final String T_I_MM3_NEG = "t_i_mm3_neg";
        public static final String T_I_MM2_NEG = "t_i_mm2_neg";
        public static final String T_I_MM1_NEG = "t_i_mm1_neg";
        public static final String T_I_M_NEG = "t_i_m_neg";
        public static final String T_I_B_NEG = "t_i_b_neg";
        public static final String T_I_MB = "t_i_mb";
        public static final String T_I_B_POS = "t_i_b_pos";
        public static final String T_I_M_POS = "t_i_m_pos";
        public static final String T_I_MM1_POS = "t_i_mm1_pos";
        public static final String T_I_MM2_POS = "t_i_mm2_pos";
        public static final String T_I_MM3_POS = "t_i_mm3_pos";

        public static final String TIPO = "tipo";

        //CLAVE FORANEA
        public static final String FK_ID_MEDICION = "fk_id_medicion";

    }
}
