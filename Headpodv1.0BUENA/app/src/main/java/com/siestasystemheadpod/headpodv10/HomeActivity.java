package com.siestasystemheadpod.headpodv10;


import android.app.Activity;
import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import android.app.AlertDialog;
import android.content.DialogInterface;

import com.siestasystemheadpod.headpodv10.adicionales.TamanoPantalla;
import com.siestasystemheadpod.headpodv10.adicionales.ble.DeviceControlFragment;
import com.siestasystemheadpod.headpodv10.adicionales.ble.DeviceScanFragment;
import com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor.EstadosBateriaSensorResource;
import com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor.TaskSensorBle;
import com.siestasystemheadpod.headpodv10.adicionales.cargando.CargandoFragment;
import com.siestasystemheadpod.headpodv10.adicionales.cargando.CargandoTaskDatosTReal;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.modelo.Paciente;
import com.siestasystemheadpod.headpodv10.data.modelo.Terapeuta;
import com.siestasystemheadpod.headpodv10.fragments.AllAvatarFragment;
import com.siestasystemheadpod.headpodv10.fragments.AllAvatarMedicionFragment;
import com.siestasystemheadpod.headpodv10.fragments.DatosEnTRealFragment;
import com.siestasystemheadpod.headpodv10.fragments.InformeComparativoFragment;
import com.siestasystemheadpod.headpodv10.fragments.InformeFragment;
import com.siestasystemheadpod.headpodv10.fragments.LeyendaFragment;
import com.siestasystemheadpod.headpodv10.fragments.informe.ResultInclinationFragment;
import com.siestasystemheadpod.headpodv10.fragments.PruebaGraficosFragment;
import com.siestasystemheadpod.headpodv10.fragments.ResultadoFlexionFragment;
import com.siestasystemheadpod.headpodv10.fragments.ResultadoInclinacionFragment;
import com.siestasystemheadpod.headpodv10.fragments.ResultadoRotacionFragment;
import com.siestasystemheadpod.headpodv10.fragments.pacientes.AnadirPacienteFragment;
import com.siestasystemheadpod.headpodv10.fragments.AvatarFragment;
import com.siestasystemheadpod.headpodv10.fragments.BarraProgresoTemporalFragment;
import com.siestasystemheadpod.headpodv10.fragments.BotonBelowPestanaFragment;
import com.siestasystemheadpod.headpodv10.fragments.BotonPestanaFragment;
import com.siestasystemheadpod.headpodv10.fragments.BotonesAvatarFragment;
import com.siestasystemheadpod.headpodv10.fragments.Cara3DFragment;
import com.siestasystemheadpod.headpodv10.fragments.CaraFlexionFragment;
import com.siestasystemheadpod.headpodv10.fragments.CaraInclinacionFragment;
import com.siestasystemheadpod.headpodv10.fragments.CaraRotacionFragment;
import com.siestasystemheadpod.headpodv10.fragments.HomeFragment;
import com.siestasystemheadpod.headpodv10.fragments.MedicionFragment;
import com.siestasystemheadpod.headpodv10.fragments.MedicionPlantillaPasosFragment;
import com.siestasystemheadpod.headpodv10.fragments.pacientes.MisMedicionesDePacienteFragment;
import com.siestasystemheadpod.headpodv10.fragments.pacientes.MisPacientesFragment;
import com.siestasystemheadpod.headpodv10.fragments.PerfilFragment;
import com.siestasystemheadpod.headpodv10.fragments.pacientes.PacienteEditFragment;

import java.util.ArrayList;
import java.util.Locale;



public  class  HomeActivity extends AppCompatActivity//FragmentActivity

        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        PerfilFragment.OnFragmentInteractionListener,
        MedicionFragment.OnFragmentInteractionListener,
        MedicionPlantillaPasosFragment.OnFragmentInteractionListener,
        AvatarFragment.OnFragmentInteractionListener,//Borrar más adelante
        BotonesAvatarFragment.OnFragmentInteractionListener,
        BotonPestanaFragment.OnFragmentInteractionListener,
        BotonBelowPestanaFragment.OnFragmentInteractionListener,
        Cara3DFragment.OnFragmentInteractionListener,//Elegir cara3dFragment, o AllAvatarFragment
        CaraFlexionFragment.OnFragmentInteractionListener,
        CaraRotacionFragment.OnFragmentInteractionListener,
        CaraInclinacionFragment.OnFragmentInteractionListener,
        BarraProgresoTemporalFragment.OnFragmentInteractionListener,
        AllAvatarMedicionFragment.OnFragmentInteractionListener,
        MisPacientesFragment.OnFragmentInteractionListener,
        AnadirPacienteFragment.OnFragmentInteractionListener,
        PacienteEditFragment.OnFragmentInteractionListener,
        DeviceScanFragment.OnFragmentInteractionListener,
        DeviceControlFragment.OnFragmentInteractionListener,
        CargandoFragment.OnFragmentInteractionListener,
        MisMedicionesDePacienteFragment.OnFragmentInteractionListener,



        DatosEnTRealFragment.OnFragmentInteractionListener,




        LeyendaFragment.OnFragmentInteractionListener,


        InformeFragment.OnFragmentInteractionListener,
        InformeComparativoFragment.OnFragmentInteractionListener,
        ResultInclinationFragment.OnFragmentInteractionListener,

        ResultadoInclinacionFragment.OnFragmentInteractionListener,
        ResultadoFlexionFragment.OnFragmentInteractionListener,
        ResultadoRotacionFragment.OnFragmentInteractionListener,

        PruebaGraficosFragment.OnFragmentInteractionListener,

        View.OnClickListener,

        AllAvatarFragment.OnFragmentInteractionListener //vista inflada para la vista de avatar3d en tiempo real

        {


    public static final String CALIBRI_BOLD="fonts/Calibri Bold.ttf";
    private TextView txtAliasMenu;
    private TextView txtEmailMenu;
    private TextView txtSensor;

    private TextView tituloToolbar;


    private Locale locale;
    private Configuration config = new Configuration();

    public Fragment fragmentActual =null;

    public String nameFragmentActual=null;
    public String nameFramgentAnterior;

    //Tamanño de la pantalla del dispositivo
    private TamanoPantalla tamanoPantalla;

    public float  heightPixels;
    float widthPixels;
    float densityDpi;
    //Pulgadas de la pantalla
    public double screenInchesPulgadas;


   //Sensor
   public int estadoSensor=0; //0 desvinculado, //1 vinculado
   private int estadoAcelerometro=0;//1 habilitado, //0 deshabilitado.
   private int notificacionesAcelerometro=0;//1 hablitado,//0 deshabilitado.
   public  DeviceControlFragment controladorServiceBLE;

   private EstadosBateriaSensorResource estadosBateriaSensorResource;


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FragmenstAdapter
    private FragmentsAdapter adaptadorFrag;
    private FragmentManager fragmentManager;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Chivatos
     public boolean transicion_a_DatosTiempoReal =false;

   //menu superior Sensor_bateria
  public ImageView imgSensorSincronizando;


  //Pila puntero auxiliar de fragmentos (almacenamos solo el String)
  public ArrayList<String> pilaAuxFragments = new ArrayList<String>();

  //Para comprobar el estado de la batería del sensor cada x tiempo en ms
  private final static int INTERVAL_BAT = 60000 ;//1000 * 60 * 2; //2 minutes//300000
  public Handler mHandlerBat = new Handler();

  //Activity
  public Activity activity;


  //Header menu
  private  NavigationView navigationView;

  //Para recorrdar el inicio de sesion
  private SharedPreferSession sharedPreferSession;

  //Para acceder a la base de datos local
  private  CrudDbHeadpod crudDbHeadpod;

    //intervarlo de mensaje de aviso para salir al pulsar dos veces atrás
    //********************************************************************
    private static final int INTERVALO = 1000; //2 segundos para salir
    private long tiempoPrimerClick;
    //********************************************************************


   //Para controlar las opciones del sensor (BORRAR MAS ADELANTE)
   //**************************************************
   int cont_periodo_accel=0;
   int cont_near_field=0;
    int cont_enable_accel=0;
    int cont_noti_accel=0;


    //*********************************************************


    public TamanoPantalla getTamanoPantalla()
    {
        return tamanoPantalla;
    }

    public Locale getLocaleActivity()
   {
       return locale;
   }

   public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imgSensorSincronizando:
                //Log.d("click","hola soy click");
                if(getEstadoSensor()==0) {
                    setFragment("vincular_desvincular_sensor");

                }
                else
                {
                    imgSensorSincronizando.setEnabled(false);
                }
                break;
        }
   }


   public int obtenerIdiomaUsado()
            {
                //0=es=español
                //1=en=ingles.
                if(getLocaleActivity()!=null) {
                    Log.d("idioma", getLocaleActivity().getLanguage());
                    if (getLocaleActivity().getLanguage().equals("es")) {
                        return 0;
                    } else {
                        return 1;
                    }

                }
                else{
                    Log.d("idioma_default", Locale.getDefault().getLanguage());
                    if(Locale.getDefault().getLanguage().equals("es"))
                    {
                        return 0;
                    }
                    else
                    {
                        return 1;
                    }
                }
   }


            @Override
    public void asignarTituloToolbar(String titulo)
    {
        TextView tituloToolbar= (TextView) findViewById(R.id.toolbar_title);
        tituloToolbar.setText(titulo);
    }


    @Override
    public void onFragmentInteraction(String uri){
        //you can leave it empty
        setFragment(uri);

    }




   //Método usado en MispacientesFragment al hacer click en un item de la lista de pacientes.
   //El objetivo es cargar los datos del paciente en el fragmento "fragment_edit_paciente"
   @Override
   public void  onMispacientesFragmentAdapter(Paciente paciente , String tagFragment )
   {

       setFragment(paciente,tagFragment);
   }




     public NavigationView getMenuNavigation()
     {
         return navigationView;
     }

     public void setEstadoSensor(int estado)
     {

         Log.d("activity","estado es " +String.valueOf(estado));
         estadoSensor=estado;
         if(estado==1)
         {
             //Se encarga de comprobar el estado de la bateria
             //y colocar la imagen correspondiente
             //int id = getResources().getIdentifier("com.siestasystemheadpod.headpodv10:drawable/" + "bateria_mini_con_sensor_5", null, null);
             //imgSensorSincronizando.setImageResource(id);

             //stopComprobarNivelBat();
             //startComprobarNivelBat();





         }
         else
         {
             //Colocamos la imagen de la bateria_sensor a no vinculado.
             //De esta forma evitamos que el boton funcione cuando este conectado.
             imgSensorSincronizando.setEnabled(true);
             setImgSensorBatNoVinculado();

             stopComprobarNivelBat();
         }

     }

     public int getEstadoSensor()
     {
         return estadoSensor;
     }

     public void setEstadoAcelerometro(int estado)
     {
         estadoAcelerometro=estado;
     }

     public int getEstadoAcelerometro()
     {
         return  estadoAcelerometro;

     }

     public void setNotificacionesAcelerometro(int estado)
     {
         notificacionesAcelerometro=estado;
     }

     public int getNotificacionesAcelerometro()
     {

         return notificacionesAcelerometro;
     }


    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //int orientation = this.getResources().getConfiguration().orientation;

*/
        /*
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Log.d("Entered to change as Portrait ","PPPPPPPPPPPPPPPPP");
            setContentView(R.layout.activity_home);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Log.d("Entered to change as LandScape ","LLLLLLLLLLLLLLLLLLLL");
            setContentView(R.layout.activity_home);
        }
        */
/*
        setContentView(R.layout.activity_home);
        setContenidoView();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ////////////////////////////////////////////////////////
        fragmentManager = getSupportFragmentManager();
        adaptadorFrag = new FragmentsAdapter(fragmentManager,this);
        /////////////////////////////////////////////////////////////

        setContenidoView();
        Log.d("VM HEAP", String.valueOf(Runtime.getRuntime().maxMemory()/1024/1024));//capacodad de la memoria virtual del dispositivo
    }

    private void setContenidoView()

    {


        //obtengo la actividad
        activity=this;

        //Para el control de inicio de sesión
        sharedPreferSession = new SharedPreferSession(this);

        //Obtengo las rutas de los recursos para la bateria y sensor de la barra superior
        estadosBateriaSensorResource=new EstadosBateriaSensorResource();


        //Obtengo las dimensiones de la pantalla.

        /*
        DisplayMetrics metrics = new DisplayMetrics();
getWindowManager().getDefaultDisplay().getMetrics(metrics);

int height = metrics.heightPixels;
int width = metrics.widthPixels;

         */



        tamanoPantalla = new TamanoPantalla(activity);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

        tituloToolbar = (TextView) findViewById(R.id.toolbar_title);

        tituloToolbar.getLayoutParams().width= (int) (tamanoPantalla.getWidthPixels()*0.30);
        Typeface myFont= Typeface.createFromAsset(getAssets(),CALIBRI_BOLD);
        tituloToolbar.setTypeface(myFont);



        //para mostrar el sobre del mensaje lateral derecho inferior
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        //Instanciamos la base de datos local
        crudDbHeadpod = CrudDbHeadpod.obtenerInstancia(this);

        //NavigationView (menu despegable)
        //****************************************************************************************

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

       // MenuItem settingsItem = navigationView.getMenu().findItem(R.id.vincular_desvincularSensor);
        //settingsItem.setIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.sensor));



        //navigationView.getMenu().getItem(1).setIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.sensor));
                //navigationView.getMenu().getItem(1).getSubMenu().getItem(0).setTitle(getResources().getString(R.string.desvincular_sensor));
        actualizarHeaderNavigationView();

        //*****************************************************************************************

        //Escala el logo de la empresa en la barra superior (action bar, toolbar)
        ImageView logo = (ImageView) findViewById(R.id.logo_cabecera);
        //scaleLogoAppBar(logo);

        //***********************************************************************************************************************************

        txtAliasMenu = (TextView) findViewById(R.id.txt_alias_menu);
        txtEmailMenu = (TextView) findViewById(R.id.txt_email_menu);

        //**************************************************************
        getSupportActionBar().setDisplayShowTitleEnabled(false); //optional

        //Imagen para la barra superior
        imgSensorSincronizando = (ImageView) findViewById(R.id.imgSensorSincronizando);//

        //Colocamos la imagen del bateria y sensor no vinculada en la barra superior
        setImgSensorBatNoVinculado();

        imgSensorSincronizando.setOnClickListener(this);

        //Recogemos el parametro de la otra actividad
        String fr = getIntent().getExtras().getString("fragment");//home
        //Lanzamos el fragmento fragment_home
        setFragment(fr);




    }


   private void scaleLogoAppBar(ImageView logo)
   {

       int widthFinal; //=(int)(widthPixels*0.25);
       //Colocaer Logo redimensionado tamaño manteniendo la proporcion


       if (getTamanoPantalla().getWidthPixels() > 320 && getTamanoPantalla().getWidthPixels() < 720) {
           widthFinal = 140;//110
       } else if (getTamanoPantalla().getWidthPixels() <= 320) {
           widthFinal = 105;//75
       } else {
           widthFinal = 205;//175
       }

       Bitmap imagenOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.logo_headpod_sin_leyenda);

       // Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal,887,250,false);
       // Dividimos el ancho final por el ancho de la imagen original
       //***********************************************************************************************************************
       float proporcion = widthFinal / (float) imagenOriginal.getWidth();

       int heightFinal = (int) (imagenOriginal.getHeight() * proporcion);
       Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal, widthFinal, heightFinal, false);


       logo.setImageBitmap(imagenFinal);

   }



   //Método para colocar la imagen de la bateria del sensor de la barra superior cuaando no esta vinculada.
   private void  setImgSensorBatNoVinculado()
   {
       int id = getResources().getIdentifier(estadosBateriaSensorResource.imgNoVinculada[obtenerIdiomaUsado()][0], null, null);
       //colocamos la imagen.
       imgSensorSincronizando.setImageResource(id);
       //int id = getResources().getIdentifier("com.siestasystemheadpod.headpodv10:drawable/" + "bateria_mini_con_sensor_novinculado", null, null);
   }




    public float getWidthPixels()
    {
        return widthPixels;
    }


    public float getHeightPixels()
    {
       return heightPixels;
    }

    public void viewPerfil(View v)
    {
        setFragment("fragment_perfil");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


    public void cerrarSesion(View v)
    {

        sharedPreferSession.setSession(null);

        desvincularSensor();
        //Matamos todas las actividadades
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //Se supone que elimina todas las actividades superiores en la pila a la actividad del intent (MainActivity)
        //intent.putExtra("parametro","NO_CORREO");//Esto lo mandamos para que no pete de otro intent de otra actividad.
        startActivity(intent);
        finish();
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

          //  int contFragments =getSupportFragmentManager().getBackStackEntryCount();
            //Log.d("N_count_back_home-",String.valueOf(contFragments));

            FragmentTransaction fragmentTransaction;
            FragmentManager fragmentManager;

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();

            Log.d("tamanoAuxPila",String.valueOf(pilaAuxFragments.size()));
                //Eliminamos la foto del paciente si no se ha guardadao y se ha pulsado atrás
                //***************************************************************************************************************************
                //***************************************************************************************************************************
            if(pilaAuxFragments.size()>0) {
                 int n = pilaAuxFragments.size() - 1;

                if (pilaAuxFragments.get(n).equals("fragment_anadir_paciente")) {

                    AnadirPacienteFragment fragment = (AnadirPacienteFragment) fragmentManager.findFragmentByTag("fragment_anadir_paciente");
                    if (fragment.takePicturePaciente != null)
                        fragment.takePicturePaciente.borrarImagePaciente(sharedPreferSession.getUserEmail());

                }
                //***************************************************************************************************************************
                //***************************************************************************************************************************


                pilaAuxFragments.remove(n);
                Log.d("tamanoAuxPila",String.valueOf(pilaAuxFragments.size()));
            }
                if(pilaAuxFragments.size()>0) {
                    int i=0;
                    for (; i < pilaAuxFragments.size(); i++) {
                        Log.d("fragmento_pila_aux", pilaAuxFragments.get(i));

                    }

                    Fragment popFragment = fragmentManager.findFragmentByTag(pilaAuxFragments.get(i-1));

                    if (popFragment != null) {

                        Log.d("fragmento_tag_pila",popFragment.getTag());
                        super.onBackPressed();
                    }
                }
                else
                {

                    //Limpiar pila de transición.
                    clearBackStack();

                    //Cerrar con doble click
                    //*********************************************************************************************************
                    if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
                        super.onBackPressed();
                        //Con esta si que salimos...
                        finishAffinity();

                    }else {

                        Toast.makeText(this, getString(R.string.salir_pulsa_dos_veces), Toast.LENGTH_SHORT).show();
                    }
                    tiempoPrimerClick = System.currentTimeMillis();

                    //************************************************************************************************************
                    HomeFragment home_fragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.contenedorHome, home_fragment);
                    // fragmentTransaction.addToBackStack("fragment_home");//No lo añadimos para que no se quede en blanco al volver atrás.
                    fragmentTransaction.commit();

                }
       }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showToastLonger("sere idiomas",3000);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //Menu lateral
    //***************************************************************************
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.cambiar_terapeuta)
        {

            cerrarSesion(null);
        }


        if (id==R.id.homeInicio)
        {
            setFragment("fragment_home");
        }
        if(id==R.id.cambiar_idioma)
        {
            showDialog();

        }
        if (id == R.id.avatar) {

            cargarDatosEnTReal();

       // } else if (id == R.id.nav_gallery) {

        //} else if (id == R.id.nav_slideshow) {

       // } else if (id == R.id.nav_manage) {

        } if (id == R.id.vincular_desvincularSensor) {


            setFragment("vincular_desvincular_sensor");


        //} else if (id == R.id.nav_send) {

        }

        if(id==R.id.nuevo_paciente)
        {
            setFragment("fragment_anadir_paciente");
        }

        if(id==R.id.mis_pacientes)
        {
            setFragment("fragment_mis_pacientes");
        }


        //BORRAR POSTERIORMENTE
        //*************************************************************************************************************

        if(id==R.id.niv_bat)
        {
            startComprobarNivelBat();
            stopComprobarNivelBat();
        }


        if(id==R.id.periodo_accel)
        {

            if(cont_periodo_accel>3)
            {
                cont_periodo_accel=0;
            }

            //Actulizamos imagen_sensor_bateria
            TaskSensorBle taskSensorBle = new TaskSensorBle((FragmentActivity) activity,obtenerIdiomaUsado());
            taskSensorBle.cambiarPeriodoAccel(cont_periodo_accel);
            cont_periodo_accel++;

        }


        if(id==R.id.cambiar_near_field)
        {

            if(cont_near_field>1)
            {
                cont_near_field=0;
            }

            //Actulizamos imagen_sensor_bateria
            TaskSensorBle taskSensorBle = new TaskSensorBle((FragmentActivity) activity,obtenerIdiomaUsado());
            if(cont_near_field==0)
            {
                taskSensorBle.desHabilitarNearField();
            }
            else
            {
                taskSensorBle.habilitarNearField();
            }

            cont_near_field++;

        }

        if(id==R.id.cambiar_enable_accel)
        {

            if(cont_enable_accel>1)
            {
                cont_enable_accel=0;
            }

            //Actulizamos imagen_sensor_bateria
            TaskSensorBle taskSensorBle = new TaskSensorBle((FragmentActivity) activity,obtenerIdiomaUsado());
            if(cont_enable_accel==0)
            {
                taskSensorBle.desHabilitarAcelerometro();
            }
            else
            {
                taskSensorBle.habilitarAcelerometro();
            }

            cont_enable_accel++;

        }

        if(id==R.id.cambiar_noti_accel)
        {
            if(cont_noti_accel>1)
            {
                cont_noti_accel=0;
            }

            //Actulizamos imagen_sensor_bateria
            TaskSensorBle taskSensorBle = new TaskSensorBle((FragmentActivity) activity,obtenerIdiomaUsado());
            if(cont_noti_accel==0)
            {
                taskSensorBle.habilitarNotificacionesAccel();
            }
            else
            {
                taskSensorBle.desHabilitarNotificacionesAccel();
            }
            cont_noti_accel++;
        }


        if(id==R.id.crear_log_bat)
        {
            if(getEstadoSensor()==1)
            {
                //leer
                TaskSensorBle taskSensorBle = new TaskSensorBle((FragmentActivity) activity,obtenerIdiomaUsado());
                //taskSensorBle.habilitarNearField();

                if (controladorServiceBLE!=null)
                {
                    //controladorServiceBLE.enableAccel="si";
                   // controladorServiceBLE.AccelPeriodo="1000";
                   // controladorServiceBLE.enableNearField="No";
                    //  AccelPeriodo="200";
                    //enableAccel="No";
                    //enableNearField="Si";


                    //taskSensorBle.habilitarAcelerometro();

                    //taskSensorBle.cambiarPeriodoAccel(3);

                    //taskSensorBle.desHabilitarNearField();


                    stopComprobarNivelBat();
                    startComprobarNivelBat();
                }

            }

        }
        //*************************************************************************************************************

         if(id==R.id.cerrar_app)
        {
            if(getEstadoSensor()==1)
            {
                desvincularSensor();
            }
            this.finishAffinity();//a partir de la api 16
            /*
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Exit me", true);
            startActivity(intent);
            finish();
            */

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void cargarDatosEnTReal()
    {
        if(getEstadoSensor()==1)
        {
            new CargandoTaskDatosTReal(this).execute();
        }
        else
        {
            transicion_a_DatosTiempoReal=true;
            vincularSensor();
        }
    }


    //Método para mostrar mensaje en pantalla
    public void showToastLonger(String mensaje, int ms) {

        //Toast.makeText(getApplicationContext(),error, Toast.LENGTH_LONG).show();
        final Toast tag = Toast.makeText(getBaseContext(), mensaje,Toast.LENGTH_SHORT);

        tag.show();

        new CountDownTimer(ms, 1000)
        {

            public void onTick(long millisUntilFinished) {tag.show();}
            public void onFinish() {tag.show();}

        }.start();
    }


    public void vaciarPilaNavegacion()
    {

       // Log.d("tamanoAuxPilaAntesVac",String.valueOf(pilaAuxFragments.size()));
        if(pilaAuxFragments.size()>0)
        {
                pilaAuxFragments.clear();
        }
        //Log.d("tamanoAuxPilaDespuesVa",String.valueOf(pilaAuxFragments.size()));
    }

     public void vaciarPilaTransicionFragments()
     {
         //Este código elimina toda la pila de transiciones de fragmento
         FragmentManager manager = getSupportFragmentManager();
         if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
         }
     }

     public void eliminarFragmentoDePilaTransicion(String tag)
     {

         FragmentManager manager = getSupportFragmentManager();
         if (manager.getBackStackEntryCount() > 0) {
             FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
             if(first.getName().equals(tag))
                 manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);//eliminamos
             else
                 manager.popBackStack();//recorremos
         }

     }

     /*
     Se encargara de reiniciar la vista del relativeLayoutInterior con id contenedorHome del
      layout content_home
     */

      private void clearBackStack() {
                FragmentManager manager = getSupportFragmentManager();
                int n=manager.getBackStackEntryCount();
                if (n > 0) {

                    for(int i=0 ; i<n;i++)
                    {
                        FragmentManager.BackStackEntry fragment = manager.getBackStackEntryAt(i);
                        manager.popBackStack(fragment.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }

                }
            }

    //Método para parceable la clase paciente y poner el fragmento
    public void setFragment ( Paciente paciente, String tagFragment) {


        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        switch (tagFragment) {


            case "fragment_edit_paciente":
                setNameFragmentAcutal("fragment_edit_paciente");
                Log.d("fragmento: ", getNameFragmentActual());
                if (!adaptadorFrag.existFragment("fragment_edit_paciente")) {
                    //Paso 1: Obtener la instancia del administrador de fragmentos
                    fragmentManager = getSupportFragmentManager();
                    //Paso 2: Crear una nueva transacción
                    fragmentTransaction = fragmentManager.beginTransaction();

                    //Paso 3: Crear un nuevo fragmento y añadirlo
                    //Creamos el fragmento contenedor de todos los fragmentos de la vista avatar.
                    PacienteEditFragment fragment = PacienteEditFragment.newInstance(paciente);
                    fragmentTransaction.replace(R.id.contenedorHome, fragment,"fragment_edit_paciente");//R.id.contenedorHome
                    fragmentTransaction.addToBackStack("fragment_edit_paciente");

                    fragmentTransaction.commit();
                    pilaAuxFragments.add("fragment_edit_paciente");


                }
                break;



            case "fragment_mis_mediciones":
                setNameFragmentAcutal("fragment_mis_mediciones");
                Log.d("fragmento: ",getNameFragmentActual());
                if(!adaptadorFrag.existFragment("fragment_mis_mediciones"))
                {
                    //Paso 1: Obtener la instancia del administrador de fragmentos
                    fragmentManager = getSupportFragmentManager();
                    //Paso 2: Crear una nueva transacción
                    fragmentTransaction = fragmentManager.beginTransaction();

                    //Paso 3: Crear un nuevo fragmento y añadirlo
                    //Creamos el fragmento contenedor de todos los fragmentos de la vista avatar.
                    MisMedicionesDePacienteFragment fragment = MisMedicionesDePacienteFragment.newInstance(paciente);
                    fragmentTransaction.replace(R.id.contenedorHome, fragment ,"fragment_mis_mediciones");//R.id.contenedorHome
                    fragmentTransaction.addToBackStack("fragment_mis_mediciones");
                    //fragmentTransaction.commitAllowingStateLoss();
                    pilaAuxFragments.add("fragment_mis_mediciones");

                    fragmentTransaction.commit();
                }
                break;



        }


        nameFramgentAnterior=tagFragment;
        fragmentManager = getSupportFragmentManager();
        Log.d("fragmento", "Num_Fragmentos: " + Integer.toString(fragmentManager.getBackStackEntryCount()));


    }


    public void setFragment(String tagFragment) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        adaptadorFrag.colocarFragmentoEspecifico(tagFragment);
    }


        // We allow the Sensor to be used in all instances by default
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);





    /**
     * Muestra una ventana de dialogo para elegir el nuevo idioma de la aplicacion
     * Cuando se hace clic en uno de los idiomas, se cambia el idioma de la aplicacion
     * y se recarga la actividad para ver los cambios
     * */
    public void showDialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(getResources().getString(R.string.cambiar_idioma));
        //obtiene los idiomas del array de string.xml
        String[] types = getResources().getStringArray(R.array.languages);
        b.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch(which){
                    case 0:
                        locale = new Locale("en");
                        config.locale =locale;
                        break;
                    case 1:
                        locale = new Locale("es");
                        config.locale =locale;
                        break;

                }


                cambiarIdioma();

            }
            //***************************************************************************************************

        });

        b.show();
    }


    public void cambiarIdioma()
    {
        // config.getLocales().
        getResources().updateConfiguration(config, null);

        //Actualizamos solo el menú

        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_home_drawer);

        //Actualizamos el valor del alias del header del menu
        actualizarHeaderNavigationView();

        //Para controlar el string de vincular o desvincular sensor.
        if(estadoSensor==1)
        {
            navigationView.getMenu().getItem(1).getSubMenu().getItem(0).setTitle(getResources().getString(R.string.desvincular_sensor));
            //Actulizamos imagen_sensor_bateria
            TaskSensorBle taskSensorBle = new TaskSensorBle((FragmentActivity) activity,obtenerIdiomaUsado());
            taskSensorBle.leerBateria(getApplicationContext());

        }
        else
        {
            navigationView.getMenu().getItem(1).getSubMenu().getItem(0).setTitle(getResources().getString(R.string.vincular_sensor));
            //ponemos la imagen del senor_Bateria_no actualizado.
            setImgSensorBatNoVinculado();
        }

        //Actualizamos la pantalla de cada fragmento de la siguiente manera
        //********************************************************************************************************
        // Log.d("tam_pil_aux",String.valueOf(pilaAuxFragments.size()));
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(pilaAuxFragments.size()>0)
        {
            String popFragment = pilaAuxFragments.get(pilaAuxFragments.size()-1);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(popFragment);
            if (fragment!=null)
            {
                // fragmentManager.beginTransaction().replace(R.id.contenedorHome, fragment).commit();
                fragmentManager.beginTransaction().detach(fragment).commit();
                fragmentManager.beginTransaction().attach(fragment).commit();
            }
        }
        else
        {
            FragmentTransaction fragmentTransaction;
            fragmentTransaction = fragmentManager.beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            fragmentTransaction.replace(R.id.contenedorHome,homeFragment);
            fragmentTransaction.commit();

        }


    }



    public void actualizarHeaderNavigationView()
    {

        Terapeuta terapeuta = crudDbHeadpod.getTerapeutaByEmail(sharedPreferSession.getUserEmail());

        if(terapeuta!=null)//si existe terapeuta
        {
            //Actualizamos el valor del alias del header del menu

            View header=navigationView.getHeaderView(0);

            //Obtenemos los valoes del header del peril de usuario
            ImageView fotoPerfil = (ImageView) header.findViewById(R.id.imageViewPerfil);
            TextView correoMenu = (TextView) header.findViewById(R.id.txt_email_menu);
            TextView aliasMenu = (TextView) header.findViewById(R.id.txt_alias_menu);


            // TextView alias = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txt_alias_menu);
            aliasMenu.setText(terapeuta.getAlias());
            correoMenu.setText(terapeuta.getEmail());

            //Colocamos la foto de perfil
            setImgRedondeada(fotoPerfil,terapeuta.getRuta_imagen());



        }
    }


            //Metodo para colocar y redimensionar la foto al tamaño del diseño de la pantalla
            //atributos imagen y ruta de la imagen.
            private void setImgRedondeada(ImageView fotoPerfil, String mCurrentPhotoPath) {
                // Get the dimensions of the View


                if(mCurrentPhotoPath!=null) {

                    Uri path = Uri.parse(mCurrentPhotoPath);

                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap imagenFinal = BitmapFactory.decodeFile(path.toString(),bmOptions);

                    imagenFinal = Bitmap.createScaledBitmap(imagenFinal, 125,125 , false);

                    //creamos el drawable redondeado
                    RoundedBitmapDrawable roundedDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), imagenFinal);
                    //asignamos el CornerRadius
                    roundedDrawable.setCornerRadius(imagenFinal.getHeight());

                    fotoPerfil.setImageDrawable(roundedDrawable);
                }
                else {
                    Bitmap imagenOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.monigote_verde_perfil);
                    Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal, 125,125 , false);
                    //creamos el drawable redondeado
                    RoundedBitmapDrawable roundedDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), imagenFinal);
                    //asignamos el CornerRadius
                    roundedDrawable.setCornerRadius(imagenFinal.getHeight());
                    fotoPerfil.setImageDrawable(roundedDrawable);
                }







          //      Drawable drawable = ContextCompat.getDrawable(this, R.drawable.monigote_verde_perfil);

        //        int targetW =drawable.getIntrinsicWidth();
         //       int targetH = drawable.getIntrinsicHeight();
/*
        //int targetW = img.getWidth();
        //int targetH = img.getHeight();
        //Log.d("width",String.valueOf(w));//98
        //Log.d("height",String.valueOf(h));//78
        //int targetW = 98;
        //int targetH = 78;

        int valueInPixels = (int) getResources().getDimension(R.dimen.nav_header_height);//160dp

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = 25;
        // bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

*/

                //Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
               // Bitmap imagenFinal = Bitmap.createScaledBitmap(bitmap, targetW,targetH , false);
               // img.setImageBitmap(imagenFinal);
            }


            private void setImg(ImageView fotoPerfil, String mCurrentPhotoPath)
            {
                if(mCurrentPhotoPath!=null) {

                    Uri path = Uri.parse(mCurrentPhotoPath);
                    fotoPerfil.setImageURI(path);
                }
                else {
                    Bitmap imagenOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.monigote_verde_perfil);
                    Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal, 98,133 , false);
                    fotoPerfil.setImageBitmap(imagenFinal);
                }

            }

    public Fragment getFragmentActual()
    {
        return  this.fragmentActual;
    }
    public void setFragmentActual(Fragment fr)
    {
        this.fragmentActual=fr;
    }

    public String getNameFragmentActual()
    {
        return  this.nameFragmentActual;
    }
    public void setNameFragmentAcutal(String nameFragment)
    {
        this.nameFragmentActual=nameFragment;
    }




    public void vincularSensor() {
                FragmentManager fragmentManager;
                FragmentTransaction fragmentTransaction;

                if (!adaptadorFrag.existFragment("vincular_desvincular_sensor")) {
                    //Paso 1: Obtener la instancia del administrador de fragmentos
                    fragmentManager = getSupportFragmentManager();
                    //Paso 2: Crear una nueva transacción
                    fragmentTransaction = fragmentManager.beginTransaction();

                    //Paso 3: Crear un nuevo fragmento y añadirlo
                    //Creamos el fragmento contenedor de todos los fragmentos de la vista avatar.
                    DeviceScanFragment deviceScanFragment = new DeviceScanFragment();
                    fragmentTransaction.replace(R.id.contenedorHome, deviceScanFragment, "vincular_desvincular_sensor");//R.id.contenedorHome

                    //NO INTERESA QUE EL USUARIO PUEDA VOLVER A LA PARTE DE CONEXION
                    //*************************************************************************
                    fragmentTransaction.addToBackStack("vincular_desvincular_sensor");
                    //((HomeActivity)getActivity()).pilaAuxFragments.add("vincular_desvincular_sensor");
                    //fragmentTransaction.commitAllowingStateLoss();
                    fragmentTransaction.commit();
                }
    }


    public void desvincularServicio()
    {

        if(controladorServiceBLE.getmServiceConnection()!=null) {
            this.unbindService(controladorServiceBLE.getmServiceConnection());
            this.unregisterReceiver(controladorServiceBLE.getmGattUpdateReceiver());
        }
    }

    public void vincularServicio()
            {
                //connectServidorBle
                if(controladorServiceBLE.getmServiceConnection()!=null) {
                    controladorServiceBLE.connectServidorBle();
                }
            }



    public void desvincularSensor()
    {

        transicion_a_DatosTiempoReal=false;
        if(controladorServiceBLE!=null)
        {
            estadoSensor=0;
            setEstadoSensor(0);
            transicion_a_DatosTiempoReal=false;

            Log.d("exito","desconectando");
            if(controladorServiceBLE.getmServiceConnection()!=null) {
                this.unbindService(controladorServiceBLE.getmServiceConnection());
                this.unregisterReceiver(controladorServiceBLE.getmGattUpdateReceiver());
            }

            navigationView.getMenu().getItem(1).getSubMenu().getItem(0).setTitle(getResources().getString(R.string.vincular_sensor));

            // controladorServiceBLE.onDestroy();
        }
        else
        {
            navigationView.getMenu().getItem(1).getSubMenu().getItem(0).setTitle(getResources().getString(R.string.vincular_sensor));
            Log.d("error","no se puede desconectar");
        }
    }


    public void desconectarRecepcionDatos()
    {
        if(controladorServiceBLE!=null)
        {
            //estadoSensor=0;
            Log.d("exito","desconectando");
           // this.unbindService(controladorServiceBLE.getmServiceConnection());
            this.unregisterReceiver(controladorServiceBLE.getmGattUpdateReceiver());
            //navigationView.getMenu().getItem(1).getSubMenu().getItem(0).setTitle(getResources().getString(R.string.vincular_sensor));
            // controladorServiceBLE.onDestroy();
        }
        else
        {
            Log.d("error","no se puede desconectar");
        }


    }

  //PARA COMPPORBAR EL ESTADO DE LA BATERIA
  //**********************************************************************************************
    Runnable mHandlerTaskNivelBat = new Runnable()
    {

        @Override
        public void run() {
            TaskSensorBle taskSensorBle = new TaskSensorBle((FragmentActivity) activity,obtenerIdiomaUsado());
            taskSensorBle.leerBateria(getApplicationContext());
            mHandlerBat.postDelayed(mHandlerTaskNivelBat, INTERVAL_BAT);
         }

    };

    void startComprobarNivelBat()
    {
     mHandlerTaskNivelBat.run();
       // Log.d("nivel_bat2",controladorServiceBLE.getNivelBat2());
    }

    void stopComprobarNivelBat()
    {
     mHandlerBat.removeCallbacks(mHandlerTaskNivelBat);
    }
//**************************************************************************************************

}//FIN HOME ACTIVITY


/*
List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
// You might have to access all the fragments by their tag,
// in which case just follow the line below to remove the fragment
                    if (fragmentList != null) {
                        // code that handles no existing fragments


                        for (Fragment frag : fragmentList) {
                            // To save any of the fragments, add this check
                            // a tag can be added as a third parameter to the fragment when you commit it
                            // if (frag.getTag().equals("<tag-name")) {
                            //   continue;
                            // }

                            getSupportFragmentManager().beginTransaction().remove(frag).commit();
                        }
}*/