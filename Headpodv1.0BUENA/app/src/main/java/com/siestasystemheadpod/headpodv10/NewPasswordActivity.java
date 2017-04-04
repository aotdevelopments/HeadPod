package com.siestasystemheadpod.headpodv10;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.bd.DbHeadpod;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class NewPasswordActivity extends AppCompatActivity {

    private static final String CALIBRI_BOLD="fonts/Calibri Bold.ttf";
    private EditText editPassword1;
    private EditText editPassword2;
    private TextView restablecer;
    private TextView titulo;
    private CrudDbHeadpod mTerapeutadb;
    private String correo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        editPassword1= (EditText) findViewById(R.id.password);

        editPassword2= (EditText) findViewById(R.id.password2);

        restablecer = (TextView) findViewById(R.id.restablecer);

        titulo = (TextView) findViewById(R.id.titulo);


        Typeface myFont= Typeface.createFromAsset(getAssets(), CALIBRI_BOLD);
        editPassword1.setTypeface(myFont);
        editPassword2.setTypeface(myFont);
        restablecer.setTypeface(myFont);
        titulo.setTypeface(myFont);


        //se ejecuta cuando se accede a la aplicación desde el enlace de correo de restablecer
        onNewIntent(getIntent());
    }

    public void cambiarPassword(View v)
    {

        String p1=editPassword1.getText().toString().trim();
        String p2=editPassword2.getText().toString().trim();


        if(comprobarPassword(p1,p2)==1)
        {
            //mTerapeutadb= new DbHeadpod(this);//**************************
            mTerapeutadb = CrudDbHeadpod.obtenerInstancia(this);

            if(mTerapeutadb.cambiarPassword(correo,md5(p1))>0)
            {
                showToastLonger(getString(R.string.success_changed_password),5000);

                Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                intent.putExtra("parametro", correo);//pasamos el correo.
                startActivity(intent);
            }
            else
            {
                showToastLonger("error..., intentelo más tarde",5000);
            }

        }

    }


    public int comprobarPassword(String password, String password2)
    {


        int okpass=0;
        editPassword1.setError(null);
        editPassword2.setError(null);


        if( password.length()>0  )
        {
            if(password2.length()>0)
            {
                if(password.length()==password2.length())
                {
                    if(!matchPassword(password,password2))
                    {
                        // txtAvisoPassword2.setVisibility(View.VISIBLE);
                        editPassword2.setError(getResources().getString(R.string.password_distintas));
                        editPassword1.setError(getResources().getString(R.string.password_distintas));
                        // txtAvisoPassword2.setText(getResources().getString(R.string.password_distintas));
                        okpass=0;

                    }
                    else
                    {
                        okpass=1;
                    }


                }
                else
                {
                    editPassword2.setError(getResources().getString(R.string.password_distintas));

                    okpass=0;
                }

            }
            else
            {
                editPassword2.setError(getResources().getString(R.string.repeat_new_empty));
                editPassword2.requestFocus();

            }
        }
        else
        {
            editPassword1.setError(getResources().getString(R.string.password1_vacia));
            editPassword1.requestFocus();
        }




        if(okpass==1)
        {
            editPassword1.setError(null);
            editPassword2.setError(null);
            return 1;

        }
        else
        {
            return 0;

        }

    }

    public boolean matchPassword(String p1, String p2)
    {
        return p1.equals(p2);
    }


    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        Uri data = intent.getData();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {

             correo=data.getLastPathSegment();

        }
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



    private void showToastLonger(String mensaje, int ms) {

        //Toast.makeText(getApplicationContext(),error, Toast.LENGTH_LONG).show();
        final Toast tag = Toast.makeText(getBaseContext(), mensaje,Toast.LENGTH_SHORT);

        tag.show();

        new CountDownTimer(ms, 1000)
        {

            public void onTick(long millisUntilFinished) {tag.show();}
            public void onFinish() {tag.show();}

        }.start();
    }



}
