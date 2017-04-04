package com.siestasystemheadpod.headpodv10.data.modelo;

import android.content.ContentValues;
import android.database.Cursor;

import com.siestasystemheadpod.headpodv10.data.contrato.TerapeutasContract.TerapeutaEntry;

/**
 * Created by Michael on 01/09/2016.
 */
public class Terapeuta {

    private String id;
    private String email;
    private String password;
    private String alias;
    private String ruta_imagen;
    //private Boolean bol_caidas;
    //private Float tiempo_sesion;
    private float tiempo_sesion;//Tiempo para la medicion
    private int validado; //0= No validado, 1=Validado


    /*public Terapeuta(String email, String password, Boolean bol_caidas, Float tiempo_sesion)
    {

        this.email=email;
        this.password=password;
        this.bol_caidas=bol_caidas;
        this.tiempo_sesion=tiempo_sesion;

    }*/



    public Terapeuta()
    {

    }

    public Terapeuta(String email, String password, int validado)
    {
        //this.id = UUID.randomUUID().toString();
        this.alias="Terapeuta";
        this.email=email;
        this.password=password;
        this.validado=validado;

    }


    public Terapeuta(String email, String password, float tiempo_sesion, String ruta_imagen, int validado)
    {
        //this.id = UUID.randomUUID().toString();
        this.alias="Terapeuta";
        this.email=email;
        this.password=password;
        this.tiempo_sesion=tiempo_sesion;
        this.ruta_imagen=ruta_imagen;
        this.validado=validado;
    }


    public Terapeuta(String email, String password,String alias, float tiempo_sesion, String ruta_imagen, int validado)// el usado
    {
        //this.id = UUID.randomUUID().toString();
        this.alias=alias;
        this.email=email;
        this.password=password;
        this.tiempo_sesion=tiempo_sesion;
        this.ruta_imagen=ruta_imagen;
        this.validado=validado;
    }


    public Terapeuta(Cursor cursor) {

        //id = cursor.getString(cursor.getColumnIndex(TerapeutaEntry.ID));
        email = cursor.getString(cursor.getColumnIndex(TerapeutaEntry.EMAIL));
        password = cursor.getString(cursor.getColumnIndex(TerapeutaEntry.PASSWORD));
        tiempo_sesion = cursor.getFloat(cursor.getColumnIndex(TerapeutaEntry.TIEMPO_SESION));
        validado = cursor.getInt(cursor.getColumnIndex(TerapeutaEntry.VALIDADO));


    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
       // values.put(TerapeutaEntry.ID, id);
        values.put(TerapeutaEntry.EMAIL, email);
        values.put(TerapeutaEntry.ALIAS,alias);
        values.put(TerapeutaEntry.PASSWORD, password);
        values.put(TerapeutaEntry.RUTA_IMAGEN,ruta_imagen);
        values.put(TerapeutaEntry.TIEMPO_SESION, tiempo_sesion);
        values.put(TerapeutaEntry.VALIDADO, validado);
        return values;
    }


    public  String getId() {return  id;}
    public String getEmail()
    {
        return  email;
    }

    public void setEmail(String email)
    {
        this.email=email;
    }

    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password=password;
    }


    public void setAlias(String alias){

        this.alias=alias;

    }
    public String getAlias()
    {
        return this.alias;
    }

    public int getValidado(){return validado;}

    public void setValidado( int v){ this.validado=v; }


    public float getTiempoSession()
    {
        return tiempo_sesion;
    }

    public void setTiempoSession(float t_s)
    {
        tiempo_sesion=t_s;
    }


    public String getRuta_imagen()
    {
        return ruta_imagen;
    }
    public void setRuta_imagen(String ruta_imagen)
    {
        this.ruta_imagen=ruta_imagen;
    }






}
