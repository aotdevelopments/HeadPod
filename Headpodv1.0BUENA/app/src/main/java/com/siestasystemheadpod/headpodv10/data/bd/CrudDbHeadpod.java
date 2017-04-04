package com.siestasystemheadpod.headpodv10.data.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.siestasystemheadpod.headpodv10.data.contrato.PacientesContract;
import com.siestasystemheadpod.headpodv10.data.contrato.TerapeutasContract;
import com.siestasystemheadpod.headpodv10.data.contrato.TerapeutasTienePacienteContract;
import com.siestasystemheadpod.headpodv10.data.modelo.Paciente;
import com.siestasystemheadpod.headpodv10.data.modelo.Terapeuta;
import com.siestasystemheadpod.headpodv10.data.modelo.TerapeutaTienePaciente;

import java.util.ArrayList;

/**
 * Created by Michael on 21/11/2016.
 *
 * Patrón singleton. Esto significa, poner su constructor principal como privado,
 * definir un miembro estático de la clase y generar un método estático que permita
 * la obtención del único miembro.
 */
public class CrudDbHeadpod {

    private static DbHeadpod baseDatos;
    private static CrudDbHeadpod instancia = new CrudDbHeadpod();


    private CrudDbHeadpod() {
    }

    public static CrudDbHeadpod obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new DbHeadpod(contexto);//Creamos la instancia de la base de datos
        }
        return instancia;
    }


    public void close(){                //closing the database for writing, avoids error.
        baseDatos.close();
    }


    //OPERACIONES TERAPEUTA
    //**************************************************************************************************
    //**************************************************************************************************
    //Devuelve todos los terapeutas
    public Cursor getAllTerapeutas() {
        return baseDatos.getReadableDatabase()
                .query(
                        TerapeutasContract.TerapeutaEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getTerapeuta(String email)
    {
        String selectionArgs[]= new String[]{email};
        return baseDatos.getReadableDatabase().query(TerapeutasContract.TerapeutaEntry.TABLE_NAME,
                null,
                TerapeutasContract.TerapeutaEntry.EMAIL + " LIKE ?",
                selectionArgs,
                null,
                null,
                null);

    }

    public Long cambiarPassword(String email, String newpassword)
    {

        SQLiteDatabase sqLiteDatabase = baseDatos.getWritableDatabase();

        long result;

        if(sqLiteDatabase!= null)
        {

            ContentValues values = new ContentValues();
            values.put(TerapeutasContract.TerapeutaEntry.PASSWORD, newpassword);

            result= baseDatos.getWritableDatabase().update(
                    TerapeutasContract.TerapeutaEntry.TABLE_NAME,
                    values,
                    TerapeutasContract.TerapeutaEntry.EMAIL + " LIKE ?",
                    new String[]{email}
            );
            sqLiteDatabase.close();

            return result;
        }
        else
        {
            result=-2;
            return result;
            //error con la BBDD
        }



    }



    public boolean existeTerapeuta(Terapeuta terapeuta)
    {
        boolean existe=false;
       /*
        Cursor consulta = getCursorTerapeutaByEmail(terapeuta.getEmail());

        if (consulta!=null && consulta.getCount() > 0) {
            //ya existe el correo
            existe = true;
        }
        */

        if(getTerapeutaByEmail(terapeuta.getEmail())!=null)
        {
            existe=true;
        }


        return  existe;

    }

    public boolean existeTerapeuta(String email)
    {
        boolean existe=false;

        if(getTerapeutaByEmail(email)!=null)
        {
            existe=true;
        }
        return  existe;
    }


    //Guardar
    public long saveTerapeuta(Terapeuta terapeuta) {
        //Abre base de datos en modo escritura.
        SQLiteDatabase sqLiteDatabase = baseDatos.getWritableDatabase();


        long result;

        if(sqLiteDatabase!= null)
        {


            result=sqLiteDatabase.insert(
                    TerapeutasContract.TerapeutaEntry.TABLE_NAME,
                    null,
                    terapeuta.toContentValues());
            sqLiteDatabase.close();

            return result;
        }
        else
        {

            return -2;
            //error con la BBDD
        }
    }


    //Devuelve terapeuta por email
    public Cursor getCursorTerapeutaByEmail(String email) {

        String selectionArgs[]= new String[]{email};
        Cursor c = baseDatos.getReadableDatabase().query(
                TerapeutasContract.TerapeutaEntry.TABLE_NAME,
                null,
                TerapeutasContract.TerapeutaEntry.EMAIL + " LIKE ?",
                selectionArgs,
                null,
                null,
                null);

        return c;
    }


    public Terapeuta getTerapeutaByEmail(String email) {

        String selectionArgs[]= new String[]{email};
        Cursor c = baseDatos.getReadableDatabase().query(
                TerapeutasContract.TerapeutaEntry.TABLE_NAME,
                null,
                TerapeutasContract.TerapeutaEntry.EMAIL + " LIKE ?",
                selectionArgs,
                null,
                null,
                null);



        Terapeuta terapeuta;
        if (c!=null && c.getCount() > 0) {
            //Cogemos el primero
            c.moveToFirst();
            //Creamos un nuevo terapeuta con los datos deseados
            terapeuta = new Terapeuta();

            terapeuta.setAlias(c.getString(c.getColumnIndex(TerapeutasContract.TerapeutaEntry.ALIAS)));

            terapeuta.setEmail(c.getString(c.getColumnIndex(TerapeutasContract.TerapeutaEntry.EMAIL)));

            terapeuta.setPassword(c.getString(c.getColumnIndex(TerapeutasContract.TerapeutaEntry.PASSWORD)));

            terapeuta.setTiempoSession(c.getFloat(c.getColumnIndex(TerapeutasContract.TerapeutaEntry.TIEMPO_SESION)));

            terapeuta.setRuta_imagen(c.getString(c.getColumnIndex(TerapeutasContract.TerapeutaEntry.RUTA_IMAGEN)));

            terapeuta.setValidado(c.getInt(c.getColumnIndex(TerapeutasContract.TerapeutaEntry.VALIDADO)));


            c.close();
        }
        else
        {
            terapeuta=null;
        }

        return terapeuta;
    }





    //eliminar Terapeuta
    public int deleteTerapeuta(String email) {
        return baseDatos.getWritableDatabase().delete(
                TerapeutasContract.TerapeutaEntry.TABLE_NAME,
                TerapeutasContract.TerapeutaEntry.EMAIL + " LIKE ?",
                new String[]{email});
    }

    //actualizar Terapeuta
    public void updateTerapeuta(Terapeuta terapeuta, String email) {
         int result =baseDatos.getWritableDatabase().update(
                TerapeutasContract.TerapeutaEntry.TABLE_NAME,
                terapeuta.toContentValues(),
                TerapeutasContract.TerapeutaEntry.EMAIL + " LIKE ?",
                new String[]{email}
        );
        if(result>=0)
        {
            Log.d("actulizarTerapeuta","ok");
        }
        else
        {
            Log.d("actualizarTerapeuta","fail");
        }

        baseDatos.getWritableDatabase().close();
    }
    //validar terapeuta

    public int actualizarValidarTerapeuta(String email, int validado)
    {

        ContentValues values = new ContentValues();
        values.put(TerapeutasContract.TerapeutaEntry.VALIDADO, validado);

        return baseDatos.getWritableDatabase().update(
                TerapeutasContract.TerapeutaEntry.TABLE_NAME,
                values,
                TerapeutasContract.TerapeutaEntry.EMAIL + " LIKE ?",
                new String[]{email}
        );

    }
    public int actualizarAliasTerapeuta(String email, String alias)
    {
        ContentValues values = new ContentValues();
        values.put(TerapeutasContract.TerapeutaEntry.ALIAS, alias);

        return baseDatos.getWritableDatabase().update(
                TerapeutasContract.TerapeutaEntry.TABLE_NAME,
                values,
                TerapeutasContract.TerapeutaEntry.EMAIL + "LIKE ?",
                new String[]{email}
        );


    }

    //**************************************************************************************************
    //***********************************************************************************************

    //OPERACIONES PACIENTE

    //**************************************************************************************************
    //**************************************************************************************************
    //db.query(item, projection, selection, selectionArgs, sortOrder);

    //INSERTAR:
    //Método para insertar un paciente en la base de datos. Normalmente lo usara el metodo "relacionPacienteConTerapeuta"
    public long insertarPaciente(Paciente paciente) {
        //Abre base de datos en modo escritura.
        SQLiteDatabase sqLiteDatabase = baseDatos.getWritableDatabase();

        long result;

        if(sqLiteDatabase!= null)
        {
            //>=0 da id, <0 error
            result=sqLiteDatabase.insertOrThrow(
                    PacientesContract.PacienteEntry.TABLE_NAME,
                    null,
                   paciente.toContentValues());
            //result=sqLiteDatabase.insertWithOnConflict(PacientesContract.PacienteEntry.TABLE_NAME, null, paciente.toContentValues(), SQLiteDatabase.CONFLICT_REPLACE);
            sqLiteDatabase.close();
            return result;
        }
        else
        {
            return -2;
            //error con la BBDD
        }
    }



    //Método para insertar paciente y crear la relacion entre terapeuta y paciente.
    public long relacionPacienteConTerapeuta(Paciente paciente, String emailTerapeuta) {


            long idPaciente = insertarPaciente(paciente);

            if(idPaciente>=0)
            {

                //Falta actualizar el paciente.
                paciente.setId(idPaciente);

                insertarIdPaciente(paciente,idPaciente);

                Log.d("idPaciente",Long.toString(idPaciente));


                TerapeutaTienePaciente terapeutaTienePaciente = new TerapeutaTienePaciente(emailTerapeuta,idPaciente);

                //Abre base de datos en modo escritura.
                SQLiteDatabase sqLiteDatabase = baseDatos.getWritableDatabase();

                long result;

                if(sqLiteDatabase!= null)
                {
                    result=sqLiteDatabase.insertOrThrow(
                            TerapeutasTienePacienteContract.TerapeutaTienePacienteEntry.TABLE_NAME,
                            null,
                            terapeutaTienePaciente.toContentValues());

                    sqLiteDatabase.close();

                    return result;

                }
                else
                {
                    return -2;
                }

            }

            else
            {
                return -2;
            }

    }

    //obtener una lista de los ids de los pacientes de un terapeuta especifico.
    public ArrayList<Long> getID_AllPacientesOfTerapeuta(String emailTerapeuta) {

        String selectionArgs[]= new String[]{emailTerapeuta};
        Cursor c = baseDatos.getReadableDatabase().query(
                TerapeutasTienePacienteContract.TerapeutaTienePacienteEntry.TABLE_NAME,
                null,
                TerapeutasTienePacienteContract.TerapeutaTienePacienteEntry.FK_EMAIL + " LIKE ?",
                selectionArgs,
                null,
                null,
                null);

        ArrayList<Long> listaIdPacientes = new ArrayList<>();
        if (c!=null && c.getCount() > 0) {
            //Cogemos el primero
            c.moveToFirst();

            //iterate over rows
            for (int i = 0; i < c.getCount(); i++) {
                listaIdPacientes.add(c.getLong(c.getColumnIndex(TerapeutasTienePacienteContract.TerapeutaTienePacienteEntry.FK_ID_PACIENTE)));
                //move to the next row
                c.moveToNext();
            }
            //close the cursor
            c.close();

        }
        else
        {
            listaIdPacientes=null;
        }

        return listaIdPacientes;
    }



    //Devuelve un paciente según su id.
    public Paciente getPacientebyID(Long id) {

        Paciente paciente;
        String selectionArgs[]= new String[]{Long.toString(id)};

        Cursor c = baseDatos.getReadableDatabase().query(
                PacientesContract.PacienteEntry.TABLE_NAME,
                null,
                PacientesContract.PacienteEntry._ID + " = ?",
                selectionArgs,
                null,
                null,
                null);

        if (c!=null && c.getCount() > 0) {
            //Cogemos el primero
            c.moveToFirst();
            //c.moveToPosition(0);

            //Creamos un nuevo terapeuta con los datos deseados
            paciente = new Paciente();

            paciente.setId(c.getLong(c.getColumnIndex(PacientesContract.PacienteEntry._ID)));

            paciente.setNombre(c.getString(c.getColumnIndex(PacientesContract.PacienteEntry.NOMBRE)));

            paciente.setApellido(c.getString(c.getColumnIndex(PacientesContract.PacienteEntry.APELLIDO)));


            paciente.setDir_imagen(c.getString(c.getColumnIndex(PacientesContract.PacienteEntry.DIR_IMAGEN)));


            paciente.setDia(c.getInt(c.getColumnIndex(PacientesContract.PacienteEntry.DIA)));
            paciente.setMes(c.getInt(c.getColumnIndex(PacientesContract.PacienteEntry.MES)));
            paciente.setAno(c.getInt(c.getColumnIndex(PacientesContract.PacienteEntry.ANO)));

            paciente.setPeso(c.getFloat(c.getColumnIndex(PacientesContract.PacienteEntry.PESO)));
            paciente.setSexo(c.getString(c.getColumnIndex(PacientesContract.PacienteEntry.SEXO)));

            paciente.setDiagnostico_control_cefalico(c.getString(c.getColumnIndex(PacientesContract.PacienteEntry.DIAGNOSTICO_CONTROL_CEFALICO)));
            paciente.setDiagnostico_tono_muscular(c.getString(c.getColumnIndex(PacientesContract.PacienteEntry.DIAGNOSTICO_TONO_MUSCULAR)));

            paciente.setSeleccionado(c.getInt(c.getColumnIndex(PacientesContract.PacienteEntry.SELECCIONADO)));


            c.close();
        }
        else
        {
            paciente=null;
        }

        return paciente;


    }


    public void updatePacienteById(Paciente paciente, Long id)
    {


        int result =baseDatos.getWritableDatabase().update(
                PacientesContract.PacienteEntry.TABLE_NAME,
                paciente.toContentValues(),
                PacientesContract.PacienteEntry._ID+ " = ?",
                new String[]{Long.toString(id)}
        );
        if(result>=0)
        {
            Log.d("actulizarPaciente","ok");
        }
        else
        {
            Log.d("actualizarPaciente","fail");
        }

        baseDatos.getWritableDatabase().close();

    }


    public void insertarIdPaciente(Paciente paciente, Long id)
    {

        int result =baseDatos.getWritableDatabase().update(
                PacientesContract.PacienteEntry.TABLE_NAME,
                paciente.toContentValueId(),
                PacientesContract.PacienteEntry._ID+ " = ?",
                new String[]{Long.toString(id)}
        );
        if(result>=0)
        {
            Log.d("insertarIdPaciente","ok");
        }
        else
        {
            Log.d("insertarIdPaciente","fail");
        }

        baseDatos.getWritableDatabase().close();

    }



    //Devuelve todos los Pacientes de la BD
    public Cursor getAllPacientes() {
        return baseDatos.getReadableDatabase()
                .query(
                        PacientesContract.PacienteEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }



    //eliminar Terapeuta
    public int deletePaciente(Long id) {
        return baseDatos.getWritableDatabase().delete(
                PacientesContract.PacienteEntry.TABLE_NAME,
                PacientesContract.PacienteEntry._ID + " LIKE ?",
                new String[]{Long.toString(id)});
    }







    //**************************************************************************************************
    //**************************************************************************************************



    /*otra fora de recorrer

            if(cursor.moveToFirst()){

            txtPacientesEmpty.setVisibility(View.INVISIBLE);
            do{
                ItemMisPacientes itemMisPacientes = new ItemMisPacientes();
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String apellido = cursor.getString(cursor.getColumnIndex("apellido"));
                Log.d("paciente-i", nombre);

                itemMisPacientes.setNombre(nombre);
                itemMisPacientes.setApellido(apellido);
                //Añado el item a la lista
                arrayListItemMisPacientes.add(itemMisPacientes);

            }while (cursor.moveToNext());

     */


}
