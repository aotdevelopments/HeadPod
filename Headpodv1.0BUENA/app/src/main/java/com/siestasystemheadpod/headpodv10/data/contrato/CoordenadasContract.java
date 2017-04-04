package com.siestasystemheadpod.headpodv10.data.contrato;

/**
 * Created by Michael on 01/09/2016.
 Esquema de la base de datos para Terapeutas
 */

import android.provider.BaseColumns;

public class CoordenadasContract {

    public static abstract class CoordenadasEntry implements BaseColumns{//clase interna para guardar la columna de la tabla
        public static final String TABLE_NAME ="coordenadas";

        //ID
        public static final String _ID = "_id";
        public static final String X ="x";
        public static final String Y = "y";

        //CLAVE FORANEA
        public static final String FK_ID_MOVIMIENTO = "fk_id_movimiento";

    }
}
