package com.siestasystemheadpod.headpodv10.data.bd;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.siestasystemheadpod.headpodv10.data.contrato.MedicionContract;
import com.siestasystemheadpod.headpodv10.data.contrato.MovimientoContract;
import com.siestasystemheadpod.headpodv10.data.modelo.Coordenadas;
import com.siestasystemheadpod.headpodv10.data.modelo.Medicion;
import com.siestasystemheadpod.headpodv10.data.modelo.Movimiento;
import com.siestasystemheadpod.headpodv10.data.modelo.Terapeuta;

import static com.siestasystemheadpod.headpodv10.data.contrato.TerapeutasContract.TerapeutaEntry;
import static com.siestasystemheadpod.headpodv10.data.contrato.PacientesContract.PacienteEntry;
import static  com.siestasystemheadpod.headpodv10.data.contrato.TerapeutasTienePacienteContract.TerapeutaTienePacienteEntry;


import static com.siestasystemheadpod.headpodv10.data.contrato.MedicionContract.MedicionEntry;
import static com.siestasystemheadpod.headpodv10.data.contrato.MovimientoContract.MovimientoEntry;
import static com.siestasystemheadpod.headpodv10.data.contrato.CoordenadasContract.CoordenadasEntry;

/**
 * Created by Michael on 01/09/2016.
 *
 * BBDD LOCAL BASADA EN SQLITE.
 */
public class DbHeadpod extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Headpod.db";
    private final Context contexto;

    interface Tablas {
        String TERAPEUTA = TerapeutaEntry.TABLE_NAME;
        String PACIENTE = PacienteEntry.TABLE_NAME;
        String TERAPEUTA_TIENE_PACIENTE = TerapeutaTienePacienteEntry.TABLE_NAME;
        String MEDICION = MedicionEntry.TABLE_NAME;
        String MOVIMIENTO= MovimientoContract.MovimientoEntry.TABLE_NAME;
        String COORDENADAS = CoordenadasEntry.TABLE_NAME;
    }



    public DbHeadpod(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.contexto=context;
    }



    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }



    interface Referencias {

        String EMAIL_TERAPEUTA = String.format("REFERENCES %s(%s) ON DELETE CASCADE",
                Tablas.TERAPEUTA, TerapeutaEntry.EMAIL);

        String ID_PACIENTE = String.format("REFERENCES %s (%s",Tablas.PACIENTE, PacienteEntry._ID);


        String FK_TERAPEUTAPACIENTE_TERAPEUTA = String.format(" FOREIGN KEY(%s) REFERENCES %s(%s) ON DELETE CASCADE",
                TerapeutaTienePacienteEntry.FK_EMAIL,Tablas.TERAPEUTA,TerapeutaEntry.EMAIL);

        String FK_TERAPEUTAPACIENTE_PACIENTE = String.format(" FOREIGN KEY(%s) REFERENCES %s(%s) ON DELETE CASCADE",
                TerapeutaTienePacienteEntry.FK_ID_PACIENTE, Tablas.PACIENTE, PacienteEntry._ID);


        //************************************************
        //Relacion 1 a N, Paciente tiene medicion
        //************************************************
        String FK_MEDICION_PACIENTE = String.format(" FOREIGN KEY(%s) REFERENCES %s(%s) ON DELETE CASCADE",MedicionEntry.FK_ID_PACIENTE,
                Tablas.PACIENTE,PacienteEntry._ID);
        //************************************************
        //Relación 1 a N, Medición tiene Movimiento
        //************************************************
        String FK_MOVIMIENTO_MEDICION = String.format(" FOREIGN KEY(%s) REFERENCES %s(%s) ON DELETE CASCADE",MovimientoEntry.FK_ID_MEDICION,
                Tablas.MEDICION,MedicionEntry._ID);

        //************************************************
        //Relacion 1 a N, Movimiento tiene coordenadas
        //**************************************************
        String FK_COORDENAS_MOVIMIENTO = String.format(" FOREIGN KEY(%s) REFERENCES %s(%s) ON DELETE CASCADE",CoordenadasEntry.FK_ID_MOVIMIENTO,
                Tablas.MOVIMIENTO,MovimientoEntry._ID);


    }


    //Para crear la base de datos.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Modificando el método onCreate() podemos ejecutar sentencias SQL


        //para crear las tablas de nuestra base de datos.
        //*********************************************************************************************************//TerapeutaEntry.TABLE_NAME
        db.execSQL("CREATE TABLE " + Tablas.TERAPEUTA + " ("
                + TerapeutaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TerapeutaEntry.EMAIL + " TEXT NOT NULL,"
                + TerapeutaEntry.ALIAS + " TEXT NOT NULL,"
                + TerapeutaEntry.PASSWORD + " TEXT NOT NULL,"
                + TerapeutaEntry.RUTA_IMAGEN + " TEXT,"
                + TerapeutaEntry.TIEMPO_SESION + " REAL,"
                + TerapeutaEntry.VALIDADO + " INTEGER,"
                + "UNIQUE (" + TerapeutaEntry.EMAIL + "))");

        //Otra forma de crear la tabla
        db.execSQL(String.format("CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT NOT NULL," +
                "%s TEXT NOT NULL," +
                "%s TEXT," +
                "%s INTEGER NOT NULL,"+
                "%s INTEGER NOT NULL,"+
                "%s INTEGER NOT NULL,"+
                "%s REAL NOT NULL," +
                "%s TEXT NOT NULL,"+
                "%s TEXT NOT NULL," +
                "%s TEXT NOT NULL," +
                "%s INTEGER NOT NULL)", Tablas.PACIENTE,
                PacienteEntry._ID,
                PacienteEntry.NOMBRE,
                PacienteEntry.APELLIDO,
                PacienteEntry.DIR_IMAGEN,

                PacienteEntry.DIA,
                PacienteEntry.MES,
                PacienteEntry.ANO,
                //PacienteEntry.FECHA_NACIMIENTO,

                PacienteEntry.PESO,
                PacienteEntry.SEXO,
                PacienteEntry.DIAGNOSTICO_CONTROL_CEFALICO,
                PacienteEntry.DIAGNOSTICO_TONO_MUSCULAR,

                PacienteEntry.SELECCIONADO
        ));


        db.execSQL(String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +

                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL," +

                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL," +

                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL," +

                        "%s INTEGER NOT NULL," +
                        "%s TEXT NOT NULL,"+

                        "%s INTEGER,"+
                        "%s)",Tablas.MEDICION,

                MedicionEntry._ID,

                MedicionEntry.DIA,
                MedicionEntry.MES,
                MedicionEntry.ANO,

                MedicionEntry.HORA_MEDICION,
                MedicionEntry.MIN_MEDICION,

                MedicionEntry.HORA_DURACION,
                MedicionEntry.MIN_DURACION,

                MedicionEntry.TIPO,
                MedicionEntry.CONTROL,

                MedicionEntry.FK_ID_PACIENTE,

                Referencias.FK_MEDICION_PACIENTE
        ));



        db.execSQL(String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +

                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL," +

                        "%s INTEGER NOT NULL," +

                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL," +

                        "%s INTEGER NOT NULL,"+
                        "%s INTEGER,"+

                        "%s)",Tablas.MOVIMIENTO,

                MovimientoEntry._ID,

                MovimientoEntry.T_I_MM3_NEG,
                MovimientoEntry.T_I_MM2_NEG,
                MovimientoEntry.T_I_MM1_NEG,
                MovimientoEntry.T_I_M_NEG,
                MovimientoEntry.T_I_B_NEG,

                MovimientoEntry.T_I_MB,

                MovimientoEntry.T_I_B_POS,
                MovimientoEntry.T_I_M_POS,
                MovimientoEntry.T_I_MM1_POS,
                MovimientoEntry.T_I_MM2_POS,
                MovimientoEntry.T_I_MM3_POS,

                MovimientoEntry.TIPO,
                MovimientoEntry.FK_ID_MEDICION,

                Referencias.FK_MOVIMIENTO_MEDICION
        ));




        //Tabla coordenadas
        db.execSQL(String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s INTEGER NOT NULL," +
                        "%s INTEGER NOT NULL,"+
                        "%s INTEGER,"+
                        "%s)",Tablas.COORDENADAS,

                CoordenadasEntry._ID,
                CoordenadasEntry.X,
                CoordenadasEntry.Y,
                CoordenadasEntry.FK_ID_MOVIMIENTO,
                Referencias.FK_COORDENAS_MOVIMIENTO
        ));

        //Creo las relaciones
        //***************************************************************************************************************************************
        //Terapeuta_tiene_paciente
        db.execSQL(String.format("CREATE TABLE %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL," +
                        "%s INTEGER NOT NULL," +
                        "%s," +
                        "%s)",Tablas.TERAPEUTA_TIENE_PACIENTE,
               TerapeutaTienePacienteEntry._ID,
               TerapeutaTienePacienteEntry.FK_EMAIL,
               TerapeutaTienePacienteEntry.FK_ID_PACIENTE,
               Referencias.FK_TERAPEUTAPACIENTE_TERAPEUTA,
               Referencias.FK_TERAPEUTAPACIENTE_PACIENTE
        ));


        //Medición
        //*********************************************************************************************************
        // Insertar datos ficticios para prueba inicia
        //mockData(db);

    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TERAPEUTA);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.PACIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.TERAPEUTA_TIENE_PACIENTE);
        onCreate(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        //mockTerapeuta(sqLiteDatabase, new Terapeuta("correo@prueba.com", "123456","1", "25"));

    }


    public long mockTerapeuta(SQLiteDatabase db, Terapeuta terapeuta) {

        return db.insert(
                TerapeutaEntry.TABLE_NAME,
                null,
                terapeuta.toContentValues());
    }






}
