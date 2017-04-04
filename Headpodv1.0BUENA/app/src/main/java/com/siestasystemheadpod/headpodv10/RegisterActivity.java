package com.siestasystemheadpod.headpodv10;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.adicionales.EfectoTxtClick;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.modelo.Terapeuta;
import com.siestasystemheadpod.headpodv10.mail.Mail;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

public class RegisterActivity extends AppCompatActivity {

    //para la fuente de la letra
    private static final String CALIBRI_BOLD="fonts/Calibri Bold.ttf";
    private TextView txtTitulo;
    private TextView txtSubtitulo;
    private TextView txtEmail;
    private TextView txtPassword;
    private TextView txtRememberPassword;
    private TextView txtRegistro;
    private EditText editEmail;
    private EditText editPassword;
    private EditText editPassword2;
    private TextView txtAvisoEmail;
    private TextView txtAvisoPassword1;
    private TextView txtAvisoPassword2;

    private CrudDbHeadpod mTerapeutaDbHelper;

    //Clase para recordar el usuario si ya ha iniciado sesión.
    private SharedPreferSession sharedPreferSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        sharedPreferSession = new SharedPreferSession(this);

        txtTitulo= (TextView) findViewById(R.id.tituloInicialRegistro);
        txtSubtitulo=(TextView) findViewById(R.id.subtitulo_registro);

        txtRegistro= (TextView) findViewById(R.id.registrar);
        txtAvisoEmail = (TextView) findViewById(R.id.aviso_email);
        txtAvisoPassword1 = (TextView) findViewById(R.id.aviso_password1);
        txtAvisoPassword2 = (TextView) findViewById(R.id.aviso_password2);

        //Datos y avisos del registro:
        editEmail= (EditText) findViewById(R.id.email);
        editPassword= (EditText) findViewById(R.id.password);
        editPassword2= (EditText) findViewById(R.id.password2);
        txtAvisoEmail = (TextView) findViewById(R.id.aviso_email);
        txtAvisoPassword1 = (TextView) findViewById(R.id.aviso_password1);
        txtAvisoPassword2 = (TextView) findViewById(R.id.aviso_password2);

        //Fuente
        Typeface myFont= Typeface.createFromAsset(getAssets(), CALIBRI_BOLD);
        txtTitulo.setTypeface(myFont);
        txtSubtitulo.setTypeface(myFont);
        editEmail.setTypeface(myFont);
        editPassword.setTypeface(myFont);
        editPassword2.setTypeface(myFont);
        txtRegistro.setTypeface(myFont);




        editPassword2.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here
                String password = editPassword.getText().toString().trim();
                String password2= editPassword2.getText().toString().trim();

                comprobarPassword(password, password2);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        editPassword.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here
                String password = editPassword.getText().toString().trim();
                String password2= editPassword2.getText().toString().trim();
                comprobarPassword(password, password2);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }




    public void registrarTerapeuta(View v)
    {

        int exito=0;
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String password2= editPassword2.getText().toString().trim();

        exito=comprobarDatos(email, password, password2);

        if(exito==1)
        {
          // mTerapeutaDbHelper = new DbHeadpod(this);
            mTerapeutaDbHelper = CrudDbHeadpod.obtenerInstancia(this);
           addTerapeuta(email,password);

           // Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
           //startActivity(intent);
        }

    }

    public void addTerapeuta(String email, String password){

        password=md5(password);
        //Terapeuta terapeuta = new Terapeuta(email,password,0);//0 no validado





       Terapeuta terapeuta = new Terapeuta(email,password,getResources().getString(R.string.sin_alias),(float)30,null,1);//0 no validado,1//validado
        new AddTerapeutaTask().execute(terapeuta);//Comprobamos si no existe y si correo success se almacena

    }


    public int comprobarDatos(String email, String password, String password2)
    {

        new EfectoTxtClick(getBaseContext(),txtRegistro);


        int okcorreo=0;
        int okpass=0;

        txtAvisoEmail.setVisibility(View.INVISIBLE);
        txtAvisoEmail.setText("");
        txtAvisoPassword1.setVisibility(View.INVISIBLE);
        txtAvisoPassword1.setText("");
        txtAvisoPassword2.setVisibility(View.INVISIBLE);
        txtAvisoPassword2.setText("");

        /*
             if (editEmail.isEmpty(email)) {
            txtAvisoEmail.setError(getString(R.string.email_vacio));
            error = true;
        }

         */

        if(email.length()==0)
        {
            //txtAvisoEmail.setVisibility(View.VISIBLE);
            editEmail.setError(getResources().getString(R.string.email_fail));

            //txtAvisoEmail.setText(getResources().getString(R.string.email_vacio));
            okcorreo=0;
        }
        else
        {
            if (!isEmailValid(email))
            {
                //txtAvisoEmail.setVisibility(View.VISIBLE);
                editEmail.setError(getResources().getString(R.string.email_fail));
                //txtAvisoEmail.setText(getResources().getString(R.string.email_fail));
                okcorreo=0;
            }
            else
            {
                okcorreo=1;
            }
        }

        if( password.length()>0  )
        {
            if(password2.length()>0)
            {

                if(!matchPassword(password,password2))
                {
                   // txtAvisoPassword2.setVisibility(View.VISIBLE);
                    editPassword2.setError(getResources().getString(R.string.password_distintas));
                    editPassword.setError(getResources().getString(R.string.password_distintas));
                   // txtAvisoPassword2.setText(getResources().getString(R.string.password_distintas));
                    okpass=0;

                }
                else
                {
                    okpass=1;
                }
            }
        }
        else
        {
            //aviso password1_vacia
           // txtAvisoPassword1.setVisibility(View.VISIBLE);
            editPassword.setError(getResources().getString(R.string.password1_vacia));
           // txtAvisoPassword1.setText(getResources().getString(R.string.password1_vacia));
            okpass=0;
        }


        if(password2.length()>0)
        {

            if(password.length()>0)
            {
                if(!matchPassword(password,password2))
                {

                   // txtAvisoPassword2.setVisibility(View.VISIBLE);
                    editPassword2.setError(getResources().getString(R.string.password_distintas));
                    editPassword.setError(getResources().getString(R.string.password_distintas));

                   // txtAvisoPassword2.setText(getResources().getString(R.string.password_distintas));
                    okpass=0;
                }
                else
                {
                    okpass=1;
                }
            }
        }
        else
        {
            //aviso password2_vacia
            //txtAvisoPassword2.setVisibility(View.VISIBLE);
            editPassword2.setError(getResources().getString(R.string.password2_vacia));
            //txtAvisoPassword2.setText(getResources().getString(R.string.password2_vacia));
            okpass=0;
        }

        if(okcorreo==1 && okpass==1)
        {

            editEmail.setError(null);
            editPassword.setError(null);
            editPassword2.setError(null);
            return 1;



        }
        else
        {

            if(okcorreo==1 && okpass==0)
            {
                editPassword2.requestFocus();
            }

            else
            {
                editEmail.requestFocus();
            }

            return 0;

        }


    }


    public int comprobarPassword(String password, String password2)
    {


        int okpass=0;
        editPassword.setError(null);
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
                        editPassword.setError(getResources().getString(R.string.password_distintas));
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
        }




        if(okpass==1)
        {
            editPassword.setError(null);
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

    private class AddTerapeutaTask extends AsyncTask<Terapeuta, String, Long> {

        /*
        Este método será el encargado de realizar la tarea en segundo plano.
        Como vemos, recibe un número cualquiera de parámetros del tipo Params,
        así que debemos tratar a terapeutas como un array.

         */


         @Override
        protected void onPreExecute() {

            Toast.makeText(RegisterActivity.this, getString(R.string.wait_please), Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Long doInBackground(Terapeuta... terapeutas) {

            long exito;

            //Mail m = new Mail("aotdevelopments@gmail.com", "aot123456");
            //
            //Mail m = new Mail("webmaster@kinesiopod.com", "kinesiopod_Email2017");


            Mail m = new Mail("webmaster@kinesiopod.com", "aot14141414");


            String[] toArr = {terapeutas[0].getEmail()};// se puede enviar a varios en el array.
            m.setTo(toArr);
           // m.setFrom(terapeutas[0].getEmail());// es este correo webmaster@kinesiopod.com
            m.setSubject(getString(R.string.email_subject_registro));


            //Hay que meter enlaces, por tanto no vale solo con pasar el string
            String body = getString(R.string.email_body_registro);
            String email = terapeutas[0].getEmail();

            String url= "http://www.kinesiopod.com/activar/";
            String enlace_activacion="<p><a href='"+url+email+"'>"+getString(R.string.activar_cuenta)+"</a></p>";
            m.setBody("<b>"+body+"</b>"+enlace_activacion);//Enlace de activación
            m.setBody(m.addColor(m.getBody(),getBaseContext(),R.color.colorPrimary));



            try {
                // m.addAttachment("/sdcard/filelocation");//

                //Comprobamos si existe el usuario
                if(!mTerapeutaDbHelper.existeTerapeuta(terapeutas[0]))
                {
                    if(m.send()) {
                        publishProgress(getString(R.string.send_email_success));
                        exito=  mTerapeutaDbHelper.saveTerapeuta(terapeutas[0]);

                        //almacenamos la sesion
                        sharedPreferSession.setUserEmail(terapeutas[0].getEmail());
                        sharedPreferSession.setUserPassword(terapeutas[0].getPassword());
                        sharedPreferSession.setSession("iniciada");


                    } else {
                        publishProgress(getString(R.string.send_email_fail));
                        exito = -3;

                    }

                }
                else
                {
                    exito=-1;

                }

            }
            catch(MessagingException e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("RegisterActivity", "Could not send email to "+terapeutas[0].getEmail(), e);
                publishProgress(getString(R.string.send_email_fail));
                exito= -4;

            }
            /*
            catch (AuthenticationFailedException e) {
                publishProgress(getString(R.string.send_email_fail));
            } catch (AddressException e) {
                publishProgress(getString(R.string.send_email_fail));
            } catch (SendFailedException e) {
                publishProgress(getString(R.string.send_email_fail));
            } catch (MessagingException e) {
                publishProgress(getString(R.string.send_email_fail));
            }*/

            return exito;

        }


        @Override

        protected void onProgressUpdate (String... valores) {

            Toast.makeText(RegisterActivity.this, valores[0], Toast.LENGTH_SHORT).show();
        }


        /*
        Aquí puedes publicar todos los resultados retornados por doInBackground()
        hacia el hilo principal.
         */
        @Override
        protected void onPostExecute(Long result) {

            if (result < 0) {

                if(result == -1)//ya existe correo
                {
                   // showAddEditError(getString(R.string.email_exist));
                    editEmail.setError(getString(R.string.email_exist));
                    editEmail.requestFocus();
                }
               if (result == -3)
                {
                    showToastLonger(getString(R.string.register_fail),10000);
                }

               if (result == -4)
               {
                   showToastLonger(getString(R.string.register_fail_total),10000);
               }
                if (result == -2)//-2
               {
                   // showAddEditError(getString(R.string.error_bd));
                   // txtAvisoPassword2.setVisibility(View.VISIBLE);
                   // txtAvisoPassword2.setText(getResources().getString(R.string.error_bd));
                   // txtAvisoPassword2.setTextColor(getResources().getColor(R.color.rojo_ocuro));
                   showToastLonger(getString(R.string.error_bd),10000);
               }


              // getParent().setResult(Activity.RESULT_CANCELED);
              //  getParent().finish();
                //finish();
            } else {
               // txtAvisoPassword2.setVisibility(View.VISIBLE);
                //txtAvisoPassword2.setTextColor(getResources().getColor(R.color.colorPrimary));
              //  txtAvisoPassword2.setText("Registro completo");
                showToastLonger(getString(R.string.register_success),15000);

                Intent intent = new Intent (RegisterActivity.this, HomeActivity.class);
                intent.putExtra("fragment", "fragment_home");
                startActivity(intent);


            }
                //getParent().setResult(Activity.RESULT_OK);
                 //getParent().finish();
            }


        //getParent().finish(();
            //getActivity().finish();




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
