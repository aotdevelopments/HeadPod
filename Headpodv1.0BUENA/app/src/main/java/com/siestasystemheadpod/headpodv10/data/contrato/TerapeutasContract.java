package com.siestasystemheadpod.headpodv10.data.contrato;

/**
 * Created by Michael on 01/09/2016.
 Esquema de la base de datos para Terapeutas
 */

import android.provider.BaseColumns;
public class TerapeutasContract {

    public static abstract class TerapeutaEntry implements BaseColumns{//clase interna para guardar la columna de la tabla
        public static final String TABLE_NAME ="terapeuta";

        //public static final String ID = "id";
        public static final String EMAIL = "email";
        public static final String ALIAS  = "alias";
        public static final String PASSWORD = "password";
        public static final String TIEMPO_SESION ="tiempo_session";
        public static final String RUTA_IMAGEN ="ruta_imagen";
        public static final String VALIDADO="validado";


    }








}
