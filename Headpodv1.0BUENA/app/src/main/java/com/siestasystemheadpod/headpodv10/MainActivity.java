package com.siestasystemheadpod.headpodv10;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.adicionales.EfectoTxtClick;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.modelo.Terapeuta;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private static final String CALIBRI_BOLD="fonts/Calibri Bold.ttf";
    private TextView txtTitulo;
    private TextView txtSesion;
   // private TextView txtMedicion;
   // private TextView txtIniciarSesion;
    private String usuarioPrueba="a@a.com";
    private String passPrueba="a";



    private CrudDbHeadpod mTerapeutaDbHelper;

   // private int colorPrimary=55c5cf;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTitulo= (TextView) findViewById(R.id.tituloInicial);
        txtSesion= (TextView) findViewById(R.id.textIniciarSesion);
        //txtMedicion= (TextView) findViewById(R.id.textMedicion);

        Typeface myFont= Typeface.createFromAsset(getAssets(), CALIBRI_BOLD);
        txtTitulo.setTypeface(myFont);
        txtSesion.setTypeface(myFont);
        //txtMedicion.setTypeface(myFont);


        //Obtenemos una instancia de la base de datos
        mTerapeutaDbHelper = CrudDbHeadpod.obtenerInstancia(this);
        //Creamos usuario de prueba en caso de no existir
        Terapeuta terapeuta = new Terapeuta(usuarioPrueba,md5(passPrueba),getResources().getString(R.string.sin_alias),(float)30,null,1);//0 no validado,1//validado//Necesario añadir alias
        //Comprobamos si existe el usuario
        if(!mTerapeutaDbHelper.existeTerapeuta(terapeuta))
        {
           if(mTerapeutaDbHelper.saveTerapeuta(terapeuta)>=0)
           {
               Log.d("usuario","creado");
           }
            else
           {
               Log.d("usuario","no_creado");
           }
        }



        /*
        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }*/


        /*
        Bitmap bitmapImage = BitmapFactory.decodeFile(ruta);
        int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
        your_imageview.setImageBitmap(scaled);
        */


    }

    public void viewLogin(View v){

       /* txtIniciarSesion= (TextView) findViewById(R.id.textIniciarSesion);

        txtIniciarSesion.setBackgroundResource(R.color.colorPrimary);
        txtIniciarSesion.setTextColor(getResources().getColor(R.color.blanco));
        txtIniciarSesion.setBackgroundResource(R.color.blanco);
        txtIniciarSesion.setTextColor(getResources().getColor(R.color.colorPrimary));
    */
        //txtIniciarSesion.setBackground();

        new EfectoTxtClick(getBaseContext(),txtSesion);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("parametro", "NO_CORREO");
        startActivity(intent);


    }


    public void viewHome(View v)
    {
        //Para dar el efecto del botón
       // new EfectoTxtClick(getBaseContext(),txtMedicion);
        Intent intent = new Intent (MainActivity.this, HomeActivity.class);
        intent.putExtra("fragment", "fragment_home");
        startActivity(intent);
    }




    //codificar password
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }






}
