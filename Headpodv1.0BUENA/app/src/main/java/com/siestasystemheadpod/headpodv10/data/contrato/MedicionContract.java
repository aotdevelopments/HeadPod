package com.siestasystemheadpod.headpodv10.data.contrato;

/**
 * Created by Michael on 01/09/2016.
 Esquema de la base de datos para Terapeutas
 */

import android.provider.BaseColumns;

public class MedicionContract {

    public static abstract class MedicionEntry implements BaseColumns{//clase interna para guardar la columna de la tabla
        public static final String TABLE_NAME ="medicion";


        //ID
        public static final String _ID = "_id";

        //Fecha de Medición
        public static final String DIA = "dia";
        public static final String MES = "mes";
        public static final String ANO = "ano";


        //HORA MEDICIÓN
        public static  final String HORA_MEDICION = "horaMedicion";
        public static  final String MIN_MEDICION = "minMedicion";

        //DURACIÓN DE MEDICIÓN
        public static  final String HORA_DURACION = "horaDuracion";
        public static  final String MIN_DURACION = "minDuracion";

        //TIPO DE MEDICIÓN
        public static final String TIPO = "tipo";

        //CONTROL DE MOVIMIENTO
        public static  final String CONTROL = "control";

        //Clave foranea
        public static final String FK_ID_PACIENTE ="fk_id_paciente";



    }
}
