package com.siestasystemheadpod.headpodv10.data.modelo;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.siestasystemheadpod.headpodv10.data.contrato.PacientesContract;

import java.util.Calendar;

/**
 * Created by Michael on 14/11/2016.
 * Clase que representa el paciente
 *
 *
 */
public class Paciente implements Parcelable {


    private long _id;
    private String nombre;
    private String apellido;
    private int dia, mes, ano; //fecha de nacimiento
    //private Integer fecha_nacimiento;
    private String fecha_nacimiento;
    private int edad;
    private float peso;
    private String diagnostico_control_cefalico;
    private String diagnostico_tono_muscular;
    private String dir_imagen;
    private String sexo;//hombre= h, //mujer = m

    private int contMedicionCHP;
    private int contMedicionSHP;




    private int seleccionado; //0 = No seleccionado, 1= Seleccionado


    //Métodos del parcelable
    //**********************************************************************************************
    @Override
    public void writeToParcel(Parcel write, int flags) {
        write.writeLong(_id);
        write.writeString(nombre);
        write.writeString(apellido);
        write.writeInt(dia);
        write.writeInt(mes);
        write.writeInt(ano);
        write.writeString(fecha_nacimiento);
        write.writeInt(edad);
        write.writeFloat(peso);
        write.writeString(diagnostico_control_cefalico);
        write.writeString(diagnostico_tono_muscular);
        write.writeString(dir_imagen);
        write.writeString(sexo);

        write.writeInt(contMedicionCHP);
        write.writeInt(contMedicionSHP);

        write.writeInt(seleccionado);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    private Paciente(Parcel read) {
        _id = read.readLong();
        nombre=read.readString();
        apellido=read.readString();
        dia=read.readInt();
        mes=read.readInt();
        ano=read.readInt();
        fecha_nacimiento=read.readString();
        edad=read.readInt();
        peso=read.readFloat();
        diagnostico_control_cefalico=read.readString();
        diagnostico_tono_muscular=read.readString();
        dir_imagen=read.readString();
        sexo=read.readString();

        contMedicionCHP=read.readInt();
        contMedicionSHP=read.readInt();

        seleccionado=read.readInt();

    }


    public static final Parcelable.Creator<Paciente> CREATOR
            = new Parcelable.Creator<Paciente>() {
        public Paciente createFromParcel(Parcel in) {
            return new Paciente(in);
        }

        public Paciente[] newArray(int size) {
            return new Paciente[size];
        }
    };

    //************************************************************************************************


    public Paciente()
    {

    }


    public Paciente(String nombre, String apellido,int dia, int mes, int ano, float peso, String sexo, String diagnostico_control_cefalico, String diagnostico_tono_muscular, int seleccionado)
    {
        //this.id = UUID.randomUUID().toString();
        this.nombre=nombre;
        this.apellido=apellido;
        this.dia=dia;
        this.mes=mes;
        this.ano=ano;
        this.peso=peso;
        this.diagnostico_control_cefalico=diagnostico_control_cefalico;
        this.diagnostico_tono_muscular=diagnostico_tono_muscular;
        this.contMedicionSHP=0;
        this.contMedicionCHP=0;
        this.sexo=sexo;

        this.seleccionado=seleccionado;

        fecha_nacimiento=dia+"/"+mes+"/"+ano;
    }

    public Paciente(String nombre, String apellido,String fecha_nacimiento, float peso, String diagnostico_control_cefalico, String diagnostico_tono_muscular)
    {
        //this.id = UUID.randomUUID().toString();
        this.nombre=nombre;
        this.apellido=apellido;
        this.fecha_nacimiento=fecha_nacimiento;
        this.peso=peso;
        this.diagnostico_control_cefalico=diagnostico_control_cefalico;
        this.diagnostico_tono_muscular=diagnostico_tono_muscular;
        this.contMedicionSHP=0;
        this.contMedicionCHP=0;


    }







    public Paciente(String nombre, String apellido,String fecha_nacimiento, float peso, String diagnostico_control_cefalico, String diagnostico_tono_muscular, String sexo)
    {
        //this.id = UUID.randomUUID().toString();
        this.nombre=nombre;
        this.apellido=apellido;
        this.fecha_nacimiento=fecha_nacimiento;
        this.peso=peso;
        this.diagnostico_control_cefalico=diagnostico_control_cefalico;
        this.diagnostico_tono_muscular=diagnostico_tono_muscular;
        this.sexo = sexo;
    }


    public Paciente(String nombre, String apellido,String fecha_nacimiento, float peso, String diagnostico_control_cefalico, String diagnostico_tono_muscular, String sexo,
                    String ultima_mac,int nConvulsiones,int contIntervalo1, int contIntervalo2, int contIntervalo3 , int contIntervalo4, int t_medicion, float vma
                    )
    {
        //this.id = UUID.randomUUID().toString();
        this.nombre=nombre;
        this.apellido=apellido;
        this.fecha_nacimiento=fecha_nacimiento;
        this.peso=peso;
        this.diagnostico_control_cefalico=diagnostico_control_cefalico;
        this.diagnostico_tono_muscular=diagnostico_tono_muscular;
        this.sexo = sexo;

    }


    public Paciente(Cursor cursor) {
        //id = cursor.getString(cursor.getColumnIndex(TerapeutaEntry.ID));
        nombre = cursor.getString(cursor.getColumnIndex(PacientesContract.PacienteEntry.NOMBRE));
        apellido = cursor.getString(cursor.getColumnIndex(PacientesContract.PacienteEntry.APELLIDO));
        dia=cursor.getInt(cursor.getColumnIndex(PacientesContract.PacienteEntry.DIA));
        mes=cursor.getInt(cursor.getColumnIndex(PacientesContract.PacienteEntry.MES));
        ano=cursor.getInt(cursor.getColumnIndex(PacientesContract.PacienteEntry.ANO));
       // fecha_nacimiento = cursor.getString(cursor.getColumnIndex(PacientesContract.PacienteEntry.FECHA_NACIMIENTO));
        diagnostico_control_cefalico=cursor.getString(cursor.getColumnIndex(PacientesContract.PacienteEntry.DIAGNOSTICO_CONTROL_CEFALICO));
        diagnostico_tono_muscular = cursor.getString(cursor.getColumnIndex(PacientesContract.PacienteEntry.DIAGNOSTICO_TONO_MUSCULAR));
        peso=cursor.getFloat(cursor.getColumnIndex(PacientesContract.PacienteEntry.PESO));
        sexo=cursor.getString(cursor.getColumnIndex(PacientesContract.PacienteEntry.SEXO));

        seleccionado=cursor.getInt(cursor.getColumnIndex(PacientesContract.PacienteEntry.SELECCIONADO));


    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        // values.put(TerapeutaEntry.ID, id);
      // values.put(PacientesContract.PacienteEntry._ID,_id);//Esto no se coloca porque es autoincrementable de lo contrario siempre dara el mismo id//*****NOTA IMPORTANTE*******************

        values.put(PacientesContract.PacienteEntry.NOMBRE, nombre);
        values.put(PacientesContract.PacienteEntry.APELLIDO, apellido);

        values.put(PacientesContract.PacienteEntry.DIR_IMAGEN,dir_imagen);

        values.put(PacientesContract.PacienteEntry.DIA,dia);
        values.put(PacientesContract.PacienteEntry.MES,mes);
        values.put(PacientesContract.PacienteEntry.ANO,ano);
        //values.put(PacientesContract.PacienteEntry.FECHA_NACIMIENTO,fecha_nacimiento);


        values.put(PacientesContract.PacienteEntry.PESO,peso);
        values.put(PacientesContract.PacienteEntry.SEXO,sexo);

        values.put(PacientesContract.PacienteEntry.DIAGNOSTICO_CONTROL_CEFALICO,diagnostico_control_cefalico);
        values.put(PacientesContract.PacienteEntry.DIAGNOSTICO_TONO_MUSCULAR,diagnostico_tono_muscular);

        values.put(PacientesContract.PacienteEntry.SELECCIONADO,seleccionado);


        return values;
    }


    public ContentValues toContentValueId() {
        ContentValues value = new ContentValues();
        value.put(PacientesContract.PacienteEntry._ID,_id);
        return  value;
    }


    public void setId(long _id)
    {
        this._id=_id;
    }
    public long getId()
    {
        return _id;
    }

    public void setNombre(String nombre)
    {
        this.nombre=nombre;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setApellido(String apellido)
    {
        this.apellido=apellido;
    }
    public String getApellido()
    {
        return apellido;
    }

    public void setSexo(String sexo)
    {
        this.sexo=sexo;
    }
    public String getSexo()
    {
        return sexo;
    }

    public void setPeso(float peso)
    {
        this.peso=peso;
    }
    public float getPeso()
    {
        return peso;
    }


    public void setDiagnostico_control_cefalico(String diagnostico_control_cefalico)
    {
        this.diagnostico_control_cefalico=diagnostico_control_cefalico;
    }

    public String getDiagnostico_control_cefalico()
    {
        return diagnostico_control_cefalico;
    }

    public void setDiagnostico_tono_muscular(String diagnostico_tono_muscular)
    {
        this.diagnostico_tono_muscular=diagnostico_tono_muscular;
    }

    public String getDiagnostico_tono_muscular()
    {
        return diagnostico_tono_muscular;
    }


    public void setDia(int dia)
    {
        this.dia=dia;
    }

    public int getDia()
    {
        return dia;
    }

    public void setMes(int mes)
    {
        this.mes=mes;
    }
    public int getMes()
    {
        return mes;
    }
    public void setAno(int ano)
    {
        this.ano=ano;
    }
    public int getAno()
    {
        return ano;
    }

    public void setFecha_nacimiento(String fecha_nacimiento)
    {
        this.fecha_nacimiento=fecha_nacimiento;

    }

    public String getFecha_nacimiento()
    {
        return fecha_nacimiento;
    }

    public void setDir_imagen(String dir_imagen)
    {
        this.dir_imagen=dir_imagen;
    }
    public String getDir_imagen()
    {
        return dir_imagen;
    }

    public void setEdad(int edad)
    {
        this.edad=edad;
    }

    public int getEdad()
    {
        //obtener fecha actual
        Calendar c = Calendar.getInstance();
        int ano_actual = c.get(Calendar.YEAR);
        int mes_actual = c.get(Calendar.MONTH);
        int dia_actual = c.get(Calendar.DAY_OF_MONTH);

        //Calculamos la edad.
        edad = ano_actual - ano;
        if (mes_actual < mes|| (mes_actual == mes && dia_actual < dia)) {
            edad--;
        }

        return edad;
    }


    public int getSeleccionado()
    {
        return seleccionado;
    }

    public void setSeleccionado(int seleccionado)
    {
        this.seleccionado=seleccionado;
    }






       /* private String mac_ultimo_dispositivo_asociado;
    private int contConvulsiones;
    private int contIntervalo1;
    private int contIntervalo2;
    private int contIntervalo3;
    private int contIntervalo4;
    private int tiempoMedicion;
    private float vma;// velocidad media aritmetica


           mac_ultimo_dispositivo_asociado=ultima_mac;
        contConvulsiones=nConvulsiones;
        this.contIntervalo1=contIntervalo1;
        this.contIntervalo2=contIntervalo2;
        this.contIntervalo3=contIntervalo3;
        this.contIntervalo4 = contIntervalo4;
        tiempoMedicion=t_medicion;
        this.vma = vma;



               contIntervalo1=cursor.getInt(cursor.getColumnIndex(PacientesContract.PacienteEntry.CONT_INTERVARLO1));
        contIntervalo2=cursor.getInt(cursor.getColumnIndex(PacientesContract.PacienteEntry.CONT_INTERVARLO2));
        contIntervalo3=cursor.getInt(cursor.getColumnIndex(PacientesContract.PacienteEntry.CONT_INTERVARLO3));
        contIntervalo4=cursor.getInt(cursor.getColumnIndex(PacientesContract.PacienteEntry.CONT_INTERVARLO4));



        tiempoMedicion=cursor.getInt(cursor.getColumnIndex(PacientesContract.PacienteEntry.TIEMPO_MEDICION));

        contConvulsiones=cursor.getInt(cursor.getColumnIndex(PacientesContract.PacienteEntry.CONT_CONVULSIONES));
        vma=cursor.getFloat(cursor.getColumnIndex(PacientesContract.PacienteEntry.VELOCIDAD_MEDIA));

        mac_ultimo_dispositivo_asociado=cursor.getString(cursor.getColumnIndex(PacientesContract.PacienteEntry.ULTIMA_MAC));



        //values.put(PacientesContract.PacienteEntry.CONT_INTERVARLO1,contIntervalo1);
        //values.put(PacientesContract.PacienteEntry.CONT_INTERVARLO2,contIntervalo2);
        //values.put(PacientesContract.PacienteEntry.CONT_INTERVARLO3,contIntervalo3);
        //values.put(PacientesContract.PacienteEntry.CONT_INTERVARLO4,contIntervalo4);

        //values.put(PacientesContract.PacienteEntry.CONT_CONVULSIONES,contConvulsiones);
        //values.put(PacientesContract.PacienteEntry.VELOCIDAD_MEDIA,vma);

        //values.put(PacientesContract.PacienteEntry.TIEMPO_MEDICION,tiempoMedicion);

        //values.put(PacientesContract.PacienteEntry.ULTIMA_MAC,mac_ultimo_dispositivo_asociado);


*/



}
