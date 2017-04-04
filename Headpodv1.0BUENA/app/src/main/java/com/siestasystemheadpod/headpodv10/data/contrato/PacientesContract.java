package com.siestasystemheadpod.headpodv10.data.contrato;

/**
 * Created by Michael on 01/09/2016.
 Esquema de la base de datos para Terapeutas
 */

import android.provider.BaseColumns;

public class PacientesContract {

    public static abstract class PacienteEntry implements BaseColumns{//clase interna para guardar la columna de la tabla
        public static final String TABLE_NAME ="paciente";

        public static final String _ID = "_id";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDO  = "apellido";

        //Ruta de la imagen
        public static final String DIR_IMAGEN = "dir_imagen";

        //Fecha de nacimiento
        public static final String DIA = "dia";
        public static final String MES = "mes";
        public static final String ANO = "ano";
        // public static final String FECHA_NACIMIENTO = "fecha_nacimiento";

        public static final String PESO = "peso";

        public static final String SEXO = "sexo";

        public static final String DIAGNOSTICO_CONTROL_CEFALICO = "diagnostico_control_cefalico";
        public static final String DIAGNOSTICO_TONO_MUSCULAR = "diagnostico_tono_muscular";

        public static final String SELECCIONADO = "seleccionado";

        /*
        public static final String CONT_INTERVARLO1 = "cont_intervarlo1";
        public static final String CONT_INTERVARLO2 = "cont_intervarlo2";
        public static final String CONT_INTERVARLO3 = "cont_intervarlo3";
        public static final String CONT_INTERVARLO4 = "cont_intervarlo4";

        public static final String ULTIMA_MAC = "ultima_mac";


        public static final String TIEMPO_MEDICION = "tiempo_medicion";

        public static final String CONT_CONVULSIONES = "cont_convulsiones";
        public static final String VELOCIDAD_MEDIA= "velocidad_media";

        */
    }
}
