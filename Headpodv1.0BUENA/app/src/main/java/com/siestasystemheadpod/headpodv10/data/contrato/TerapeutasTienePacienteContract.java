package com.siestasystemheadpod.headpodv10.data.contrato;

/**
 * Created by Michael on 01/09/2016.
 Esquema de la base de datos para Terapeutas
 */

import android.provider.BaseColumns;

public class TerapeutasTienePacienteContract {

    public static abstract class TerapeutaTienePacienteEntry implements BaseColumns{//clase interna para guardar la columna de la tabla
        public static final String TABLE_NAME ="terapeuta_tiene_paciente";

        //public static final String ID = "id";
        public static final String FK_EMAIL = "fk_email";
        public static final String FK_ID_PACIENTE  = "fk_id_paciente";

    }
}
