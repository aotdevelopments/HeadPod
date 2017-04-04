package com.siestasystemheadpod.headpodv10;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.adicionales.EfectoTxtClick;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.modelo.Terapeuta;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{ //implements LoaderCallbacks<Cursor> {




    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.

    private AutoCompleteTextView mEmailView;
    private EditText email;
    private EditText password;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    //para la fuente de la letra
    private static final String CALIBRI_BOLD="fonts/Calibri Bold.ttf";
    private TextView txtTitulo;
    private TextView txtEmail;
    private TextView txtPassword;
    private TextView txtSesion;
    private TextView txtOlvidaste;

    private TextView txtNoUsuario;
    private TextView avisos;

    private CrudDbHeadpod terapeutadb;


    private SharedPreferSession sharedPreferSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Config clase donde guardaremos el último inicio de sesión.


        //Para la fuente de letra
        //**************************
        txtTitulo= (TextView) findViewById(R.id.tituloInicial);
       // txtEmail= (TextView) findViewById(R.id.email);
        //txtPassword= (TextView) findViewById(R.id.password);
        txtSesion= (TextView) findViewById(R.id.sesion_login);
        txtOlvidaste= (TextView) findViewById(R.id.olvidaste);
        txtNoUsuario = (TextView) findViewById(R.id.nuevo_registro);
        avisos= (TextView) findViewById(R.id.avisos);


        Typeface myFont= Typeface.createFromAsset(getAssets(), CALIBRI_BOLD);
        txtTitulo.setTypeface(myFont);
       // txtEmail.setTypeface(myFont);
        //txtPassword.setTypeface(myFont);
        txtSesion.setTypeface(myFont);
        txtOlvidaste.setTypeface(myFont);
        txtNoUsuario.setTypeface(myFont);
        avisos.setTypeface(myFont);

        //***************************
        //Para la interacción
        email= (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        email.setTypeface(myFont);
        password.setTypeface(myFont);



        sharedPreferSession = new SharedPreferSession(this);



        if(sharedPreferSession.getSession()==null) {
           if (sharedPreferSession.getUserEmail()!=null )
            {
                email.setText(sharedPreferSession.getUserEmail());
                password.requestFocus();
            }
        }
        //se ejecuta cuando se accede a la aplicación desde el enlace de correo
        onNewIntent(getIntent());
        String correo = getIntent().getExtras().getString("parametro");

        if (correo != null) {
            if (!correo.equals("NO_CORREO")) {
                email.setText(correo);
                password.requestFocus();
            }
        }




        // Set up the login form.

        /*
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
        */
    }

    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        Uri data = intent.getData();
        String url=intent.getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {

            String correo=data.getLastPathSegment();
            /*
             String todo= data.getPath();
            String[] separado=todo.split("/");
            String email= separado[2];

            showToastError("tengo que activar la cuenta "+data.getPath());
            showToastError("La url es  "+url);
             */
            //terapeutadb= new DbHeadpod(this);//**********************
            terapeutadb = CrudDbHeadpod.obtenerInstancia(this);
            terapeutadb.actualizarValidarTerapeuta(correo,1);//validamos cuenta.
            Toast.makeText(this,
                    getString(R.string.cuenta_activada), Toast.LENGTH_LONG).show();
            email.setText(correo);
            password.requestFocus();


            /*
            Cursor result=terapeutadb.getAllTerapeutas();
            result.moveToFirst();
            while(!result.isAfterLast()) {

                String email = result.getString(1);// se empieza de 1 no de 0
                String password= result.getString(2);
                int value = result.getInt(5);

                Toast.makeText(this,
                        "correo es "+email, Toast.LENGTH_LONG).show();
                Toast.makeText(this,
                        "password es "+password, Toast.LENGTH_LONG).show();
                Toast.makeText(this,
                        "validado es "+value, Toast.LENGTH_LONG).show();


                result.moveToNext();
            }*/


        }
    }

    public void viewRegister(View v)
    {



        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);

    }

    public void viewRememberPassword (View v)
    {
        Intent intent = new Intent(LoginActivity.this,RememberPasswordActivity.class);
        startActivity(intent);


    }

    /*
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();

        txtRegistrate= (TextView) findViewById(R.id.nuevo_registro);

        // txtRegistrate.setBackgroundResource(R.color.colorPrimary);
        txtRegistrate.setTextColor(getResources().getColor(R.color.ColorSecundadrio));
        startActivity(getIntent());
    }*/



    /**
     * Callback received when a permissions request has been completed.
     */

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

*/
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin( View v) {

        new EfectoTxtClick(getBaseContext(),txtSesion);

        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
            //mEmailView.setError(null);
            //mPasswordView.setError(null);

        email.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
            //String email = mEmailView.getText().toString();
            //String password = mPasswordView.getText().toString();
        String correo = email.getText().toString().trim();
        String contrasena = password.getText().toString().trim();



        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(contrasena)){// && !isPasswordValid(contrasena)) {
            password.setError(getString(R.string.password1_vacia));
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(correo)) {
            email.setError(getString(R.string.email_vacio));
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(correo)) {
            email.setError(getString(R.string.email_fail));
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);

            //terapeutadb = new DbHeadpod(this);//****************************************
            terapeutadb = CrudDbHeadpod.obtenerInstancia(this);
            mAuthTask = new UserLoginTask(correo, contrasena);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
       // return email.contains("@");
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

   /* private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }*/

    /**
     * Shows the progress UI and hides the login form.
     */
    /*
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }
*/
    /*
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }
*/
    /*
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
    */
    /*

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }
*/
        /*

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
*/
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mEmail;
        private final String mPassword;



        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = md5(password);



        }

        @Override
        protected Integer doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            //0->correo no existe;
            //1-> password incorrecta;
            //2-> login correcto

            Terapeuta terapeutaaux = terapeutadb.getTerapeutaByEmail(mEmail);

            if(terapeutaaux!=null)
            {

                String password = terapeutaaux.getPassword();
                int validado= terapeutaaux.getValidado();

                if(matchPassword(password,mPassword))
                {

                    if(validado==0)
                    {
                        return 3;
                    }
                    return 2;//exito
                }
                else
                {
                    return 1;
                }

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

        @Override
        protected void onPostExecute(Integer success) {
            mAuthTask = null;
            //showProgress(false);

            if (success==0) {

                avisos.setText(getString(R.string.error_email_no_existe));
                email.setError(getString(R.string.error_email_no_existe));
                showToastError(getString(R.string.error_email_no_existe));
                email.requestFocus();

                //finish();
            } else if (success==1){
                password.setError(getString(R.string.error_password_incorrecta));
                avisos.setText(getString(R.string.error_password_incorrecta));
                showToastError(getString(R.string.error_password_incorrecta));
                password.requestFocus();

            }
            else if(success==3)
            {

                showToastLonger(getString(R.string.cuenta_no_validada),3000);
            }
            else
            {
                avisos.setText("login correcto");
                showToastError("login correcto");
                //finish();


                Log.d("email_ok",mEmail);
                Log.d("pass_md5",mPassword);

                sharedPreferSession.setUserEmail(mEmail);
                sharedPreferSession.setUserPassword(mPassword);
                sharedPreferSession.setSession("iniciada");



                Intent intent = new Intent (LoginActivity.this, HomeActivity.class);
                intent.putExtra("fragment", "fragment_home");
                startActivity(intent);





            }

        }

        /*
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }*/



    }

    private void showToastError(String error) {
        Toast.makeText(getApplicationContext(),
                error, Toast.LENGTH_SHORT).show();
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

