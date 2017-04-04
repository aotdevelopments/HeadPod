package com.siestasystemheadpod.headpodv10;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.adicionales.EfectoTxtClick;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.mail.Mail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

public class RememberPasswordActivity extends AppCompatActivity {

    //para la fuente de la letra
    private static final String CALIBRI_BOLD="fonts/Calibri Bold.ttf";
    private TextView txtTitulo;
    private TextView txtEmail;
    private TextView txtEnviar;

    private EditText editEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_password);


        txtTitulo= (TextView) findViewById(R.id.tituloInicial);
        txtEmail= (TextView) findViewById(R.id.email_remember);
        txtEnviar= (TextView) findViewById(R.id.enviar_remember);

        editEmail= (EditText) findViewById(R.id.email_remember);

        Typeface myFont= Typeface.createFromAsset(getAssets(), CALIBRI_BOLD);
        txtTitulo.setTypeface(myFont);
        txtEmail.setTypeface(myFont);
        txtEnviar.setTypeface(myFont);




    }

    public void doSendEmail (View v)
    {

        new EfectoTxtClick(getBaseContext(),txtEnviar);


        String correo=editEmail.getText().toString().trim();

        if(comprobarDatos(correo)==1) {

           CrudDbHeadpod mTerapeutaDbHelper = CrudDbHeadpod.obtenerInstancia(this);//***************************

            if (mTerapeutaDbHelper.existeTerapeuta(correo)) {

                    new EnviarCorreo().execute(correo);

            } else {
                    showToastLonger(getResources().getString(R.string.error_email_no_existe), 4000);


            }

        }

    }


    private class EnviarCorreo extends AsyncTask<String, String, Long> {

        /*
        Este método será el encargado de realizar la tarea en segundo plano.
        Como vemos, recibe un número cualquiera de parámetros del tipo Params,
        así que debemos tratar a terapeutas como un array.

         */

        @Override
        protected void onPreExecute() {

            Toast.makeText(RememberPasswordActivity.this, getString(R.string.wait_please), Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Long doInBackground(String... correo) {

            long exito;

            Mail m = new Mail();

            String[] toArr = {correo[0]};// se puede enviar a varios en el array.
            m.setTo(toArr);
            //m.setFrom(correo[0]);
            m.setSubject(getString(R.string.email_subject_restablecer_password));

            //Hay que meter enlaces, por tanto no vale solo con pasar el string
            String body = getString(R.string.email_body_restablecer_password);

            String url = "http://www.kinesiopod.com/restablecer/";
            String enlace = "<a href='" + url + correo[0] + "'>" +getString(R.string.restabler_password)+ " </a>";
            m.setBody(body + enlace);//Enlace para restablecer

            try {
                // m.addAttachment("/sdcard/filelocation");//

                //Comprobamos si existe el usuario

                    if(m.send()) {
                        publishProgress(getString(R.string.send_email_success));
                        exito=1;

                    } else {
                        publishProgress(getString(R.string.send_email_fail));
                        exito = -1;
                    }
            }
            catch(MessagingException e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("RegisterActivity", "Could not send email to "+correo, e);
                publishProgress(getString(R.string.send_email_fail));
                exito= -1;

            }

            return exito;

        }


        @Override

        protected void onProgressUpdate (String... valores) {

            Toast.makeText(RememberPasswordActivity.this, valores[0], Toast.LENGTH_SHORT).show();
        }


        /*
        Aquí puedes publicar todos los resultados retornados por doInBackground()
        hacia el hilo principal.
         */
        @Override
        protected void onPostExecute(Long result) {

            if (result < 0) {

                showToastLonger(getString(R.string.fail_restablecer), 4000);

            } else {
                    showToastLonger(getString(R.string.success_restablecer), 6000);
            }

        }


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

    public int comprobarDatos(String email) {

        int okcorreo = 0;

        if (email.length() == 0) {
            //txtAvisoEmail.setVisibility(View.VISIBLE);
            editEmail.setError(getResources().getString(R.string.email_fail));

            //txtAvisoEmail.setText(getResources().getString(R.string.email_vacio));
            okcorreo = 0;
        } else {
            if (!isEmailValid(email)) {
                //txtAvisoEmail.setVisibility(View.VISIBLE);
                editEmail.setError(getResources().getString(R.string.email_fail));
                //txtAvisoEmail.setText(getResources().getString(R.string.email_fail));
                okcorreo = 0;
            } else {
                okcorreo = 1;
            }
        }
        return okcorreo;
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
