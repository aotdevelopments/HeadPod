package com.siestasystemheadpod.headpodv10.data.modelo;

import android.content.ContentValues;
import android.database.Cursor;

import com.siestasystemheadpod.headpodv10.data.contrato.TerapeutasTienePacienteContract;

/**
 * Created by Michael on 01/09/2016.
 * Esta clase representara la relacion n a n entre terapeuta y paciente
 */
public class TerapeutaTienePaciente {

    private long id;
    private String fk_email;
   // private long fk_terapeuta;
    private long fk_id_paciente;


    public TerapeutaTienePaciente(String email, long id_paciente)
    {
        //this.id = UUID.randomUUID();
        fk_email=email;
        fk_id_paciente=id_paciente;
    }

    /*public TerapeutaTienePaciente(Integer id_terapeuta, Integer id_paciente)
    {
        //this.id = UUID.randomUUID();
        fk_terapeuta=id_terapeuta;
        fk_id_paciente=id_paciente;
    }*/


    public TerapeutaTienePaciente(Cursor cursor) {
        //id = cursor.getString(cursor.getColumnIndex(TerapeutaEntry.ID));
        fk_email = cursor.getString(cursor.getColumnIndex(TerapeutasTienePacienteContract.TerapeutaTienePacienteEntry.FK_EMAIL));
        fk_id_paciente = cursor.getLong(cursor.getColumnIndex(TerapeutasTienePacienteContract.TerapeutaTienePacienteEntry.FK_ID_PACIENTE));

    }


    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
       // values.put(TerapeutaEntry.ID, id);
        values.put(TerapeutasTienePacienteContract.TerapeutaTienePacienteEntry.FK_EMAIL, fk_email);
        values.put(TerapeutasTienePacienteContract.TerapeutaTienePacienteEntry.FK_ID_PACIENTE, fk_id_paciente);
        return values;
    }


    //public  String getId() {return  id;}
    public String getEmailTerapeuta()
    {
        return  fk_email;
    }

    public long getIdPaciente()
    {
        return fk_id_paciente;
    }


}
