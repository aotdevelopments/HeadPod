package com.siestasystemheadpod.headpodv10.adicionales.ble;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.siestasystemheadpod.headpodv10.adicionales.GrafinaLinealEnTiempoReal;
import com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor.EstadosBateriaSensorResource;
import com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos.Aceleracion;
import com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos.AnguloY;
import com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos.AnguloZ;
import com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos.DesfaseAnguloYZ;
import com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos.UpdateMovCabeza;
import com.siestasystemheadpod.headpodv10.fragments.Cara3DFragment;
import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.fragments.CaraFlexionFragment;
import com.siestasystemheadpod.headpodv10.fragments.CaraInclinacionFragment;
import com.siestasystemheadpod.headpodv10.fragments.CaraRotacionFragment;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeviceControlFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeviceControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 *
 *
 * Este fragmento muestra la imagen de sincronización, a la vez que hara de controlador entre
 * el servidor BLE y el cliente que en este caso sera la activdad de HomeActivity y por tanto todos los fragmentos que interactueen con él.
 */
public class DeviceControlFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "deviceName";
    private static final String ARG_PARAM2 = "deviceAddress";

    // TODO: Rename and change types of parameters

    //3 nuevo sensor
    //2 anterior sensor.
    private int posArrayServices=2;


    private int contGy_flexion=0;
    private int contGz_inclinacion=0;

    public double gy_flexion_max_pos=0;
    public double gy_flexion_max_neg=0;
    public double gz_inclinacion_max_pos=0;
    public double gz_inclinacion_max_neg=0;

    public double gx_max_pos=0;
    public double gx_max_neg=0;



    private String mdeviceName;
    private String mdeviceAddress;

    private TextView txtSincronizando;
    private TextView txtNameDevice;

    private TextView txtWaitPlease;
    private ImageView img;
    private AnimationDrawable frameAnimationSensor;


    private ExpandableListView mGattServicesList;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;


    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    private Context contextLlamada;



    //Para acceder a la imagen de la bateria_sensor
    //************************************************
    private Resources resources;
    private FragmentActivity fragmentActivity;


    //Servicios del dispositivo Bluetooth
    //**************************************************
    private BluetoothGattCharacteristic characAccelEnable;
    private BluetoothGattCharacteristic characAccelPeriodo;
    private BluetoothGattCharacteristic characAccelValue;
    private BluetoothGattCharacteristic characBateria;
    private BluetoothGattCharacteristic characNearField;



    //Cara en 3D
    public Cara3DFragment cara_fragment_3D;


    //Caras en 2D
    private CaraFlexionFragment caraFlexionFragment;
    private CaraInclinacionFragment caraInclinacionFragment;
    private CaraRotacionFragment caraRotacionFragment;



    public GrafinaLinealEnTiempoReal grafinaLinealEnTiempoReal;
    public long i=0;
    public ArrayList<Entry> xVals1 = new ArrayList<Entry>();
    public ArrayList<Entry> yVals1 = new ArrayList<Entry>();
    public ArrayList<Entry> zVals1 = new ArrayList<Entry>();




    public  Aceleracion aceleracion;//En esta clase almacenaremos las aceleraciones de los tres ejes y hallara sus g's
   // public int valor_gx;
    //public int valor_gy;
    //public int valor_gz;
    //public float accelLsb_x;
    //public float accelLsb_y;
    //public float accelLsb_z;



    public UpdateMovCabeza updateMovCabeza;//Para llevar el control del movimiento de la cabeza en función de los ejes de las aceleraciones.
    public DesfaseAnguloYZ desfaseAnguloYZ = new DesfaseAnguloYZ(); //Para calibrar el angulo en yz







    //Para comprobar el estado de la batería del sensor cada x tiempo
    private final static int INTERVAL_LECTURA = 1000 ;// cada sgundo
    public Handler mHandlerLectura = new Handler();




    private double J=0.33;//Factor de peso
    private double Q=0.33;//Factor de peso
    private double K=0.33;//Factor de peso
    private float valor_gx_ant2=0;
    private float valor_gx_ant1=0;
    private float valor_gx_actual;
    //Media movil suavizada
    //Smoothed Moving Average
    private float smma_i;
    private float smma_i_ant=0;





    //nivel de bateria:
    //*********************************************************************************************
    private int nivelBat;
    public int idioma=-1; //0=es , 1=en
    //Estado de alerta de la bateria. (bateria baja)
    private AnimationDrawable frameAnimationBatBaja;//<5%
   //Clase donde se almacenan los nombres y direccions de los recursos de la imagenes Sensor_bateria
    private EstadosBateriaSensorResource estadosBateriaSensorResource;
    //*********************************************************************************************

    public int cont=-10;

    public int chiv=0;


    //Borrar mas adelante cuando se decida quitar los logs
    //***************************************************************************************

    public long pasadas=0;//Variable para el log de acelerometro

    public long pasadas2=0;//Variable para el log de bateria

    public String AccelPeriodo;//Variables para el log de bateria

    public String enableAccel;//Variable para el log de batreria

    public String enableNearField;//variable para el log de bateria

    //******************************************************************************************
    String nombre_fichero;



    //PARA COMPPORBAR EL ESTADO DE LA BATERIA
    //**********************************************************************************************
    Runnable mHandlerTaskLecturaAccel = new Runnable()
    {

        @Override
        public void run() {

            mHandlerLectura.postDelayed(mHandlerTaskLecturaAccel, INTERVAL_LECTURA);
        }

    };

    void startComprobarNivelBat()
    {
        mHandlerTaskLecturaAccel.run();
        // Log.d("nivel_bat2",controladorServiceBLE.getNivelBat2());
    }

    void stopComprobarNivelBat()
    {
        mHandlerLectura.removeCallbacks(mHandlerTaskLecturaAccel);
    }
//**************************************************************************************************











    // Code to manage Service lifecycle.
    private  ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.d("connection","antes de la connecion al servicio");
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e("Error", "Unable to initialize Bluetooth");
                getActivity().finish();
                //txtSincronizando.setText(getResources().getString(R.string.sensor_sincronizado_fail));
                //connectionOk=false;
            }

                //connectionOk=true;
                // Automatically connects to the device upon successful start-up initialization.
                mBluetoothLeService.connect(mdeviceAddress);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    public ServiceConnection getmServiceConnection()
    {
        return mServiceConnection;
    }



    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.

    //ACTION_DATA_BATERIA, ACTION_DATA_ENABLE_ACCEL, ACTION_DATA_ACCEL,ACTION_DATA_NEAR_FIELD, ACTION_DATA_PERIODO_ACCEL,
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d("action-x",intent.getAction());


            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;


                Log.d("control","conectado");
                ((HomeActivity) fragmentActivity).setEstadoSensor(1);//1= Viculado
                //((HomeActivity) fragmentActivity).getMenuNavigation().getMenu().getItem(1).getSubMenu().getItem(0).setTitle(R.string.desvincular_sensor);


                //Para fragmentos no usar getResourse
                String aviso_exito= resources.getString(R.string.sensor_sincronizado);
                int posframe = 3;

                 if(((HomeActivity)getActivity()).transicion_a_DatosTiempoReal)
                {
                   new SimularSincronizandoTransTiempoReal(txtWaitPlease, img, frameAnimationSensor, getActivity(), aviso_exito, posframe, txtSincronizando).execute();

                }
                else {
                    new SimularSincronizando(txtWaitPlease, img, frameAnimationSensor, getActivity(), aviso_exito, posframe, txtSincronizando).execute();
                }


                //invalidateOptionsMenu();
            }

            if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                //updateConnectionState(R.strin.sensor_sincronizado_fail);

                //getActivity().finish();


               // if(((HomeActivity) getActivity()).getEstadoSensor()==1)
                    ((HomeActivity)fragmentActivity).desvincularSensor();


                showToastAviso(fragmentActivity.getBaseContext(),resources.getString(R.string.conexion_perdida),20000);

                ((HomeActivity)fragmentActivity).setFragment("fragment_home");



                //Para fragmentos no usar getResourse
               // String aviso_fail= getResources().getString(R.string.sensor_sincronizado_fail);

               // int posframe = 0;
               // new SimularSincronizando(txtWaitPlease,img,frameAnimationSensor,getActivity(),aviso_fail,posframe,txtSincronizando).execute();//Comprobamos si no existe y si correo success se almacena


                //invalidateOptionsMenu();
                //clearUI();
            }
            if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                // displayGattServices(mBluetoothLeService.getSupportedGattServices());
                obtainGattServices(mBluetoothLeService.getSupportedGattServices());

                //DESPUES DE VINCULAR EL SENSOR OBTENEMOS EL VALOR DE LA BATERIA PARA MOSTRARLO.
                //*******************************************************************************************************************
                //Obtenemos el valor de la bateria para actualizar la imagen de la bateria_sensor la barra supeior
                getValorBateria(fragmentActivity.getBaseContext(), ((HomeActivity) fragmentActivity).obtenerIdiomaUsado());
                //*******************************************************************************************************************

            }

            if (BluetoothLeService.ACTION_DATA_BATERIA.equals(action)) {

                String val= intent.getStringExtra(BluetoothLeService.EXTRA_BATERIA);
                if(val!=null) {
                    Log.d("valor_bateriaaaaa: ", val);
                }

                Log.d("bateria", "controllllll");
                String valorBat = intent.getStringExtra(BluetoothLeService.EXTRA_BATERIA);

                if (valorBat != null) {
                    if (valorBat.length() <= 3) {
                        String numdec = hexDec(valorBat);
                        setNivelBat(Integer.valueOf(numdec));

                        //Log.d("valor_bat: ",numHex);
                        setImageBatSensor(((HomeActivity) fragmentActivity).imgSensorSincronizando, obtenerRecursoImg(getNivelBat()));
                        //showToastLonger(numdec+"%",3000);
                        showToastAviso(fragmentActivity.getBaseContext(), "LogBat + nivel bateria = " + numdec + "%", 3000);//BORRAR MAS ADELANTE.//50000


                        //Para generar un archivo con información sobre el valor de bateria etc
                        //********************************************************************************************
                        //if (pasadas2 == 0) {
                          //  escribirBateria(true, numdec, enableAccel, AccelPeriodo, enableNearField);
                        //} else {
                        //    escribirBateria(false, numdec, enableAccel, AccelPeriodo, enableNearField);
                        //}
                       // pasadas2++;
                        //********************************************************************************************
                    }
                }


            }

            if(BluetoothLeService.ACTION_DATA_ENABLE_ACCEL.equals(action)){

                Log.d("accel_enable", "controllllll");
                String accel_enable= intent.getStringExtra(BluetoothLeService.EXTRA_ENABLE_ACCEL);

                if(accel_enable!=null)
                {
                    Log.d("acen_enable",accel_enable);
                    switch (accel_enable)
                    {
                        case "00":
                            accel_enable="Deshabilitado";
                            enableAccel="DESHABILITADO";
                            break;

                        case "01":

                            accel_enable="Habilitado";
                            enableAccel="HABILITADO";
                            break;
                    }
                        showToastAviso(fragmentActivity.getBaseContext(), "Accel = "+accel_enable, 2000);//BORRAR MAS ADELANTE.//50000
                }



            }
            if(BluetoothLeService.ACTION_DATA_ACCEL.equals(action)) {

                Log.d("accel_value", "controllllll");


                float data_accel[] = intent.getFloatArrayExtra(BluetoothLeService.EXTRA_DATA_ACELEROMETRO);
                //String valores = intent.getStringExtra(BluetoothLeService.EXTRA_DATA_ACELEROMETRO);
               // if (valores != null ) {//&& valores.length() > 3  //Log.d("longuitud: ", String.valueOf(valores.length()));
                //    Log.d("valor_accel: ", valores);

                if (data_accel!=null){

                    //El servidor ya se encarga de convertir los datos usando la forma 2 de la documentación propia
                    //(drive de aotdevelopments/acelerómetro (importante)/ obtención de los datos de aceleración del acelerómetro.

                    //No obstante dejamos los métodos y la forma 1 de convertir los datos en caso de recibir el valor en hexadecimal

                    //Ej de recepción de datos CCFF88FF8007

                    //Forma 1 de obtener los datos del acelerómetro
                    //************************************************************************************************************************
                    //Paso1
                    //convierto a MSB_LSB
                    //String valor_x = valores.substring(3, 5) + valores.substring(0, 2);
                    //String valor_y = valores.substring(9, 11) + valores.substring(6, 8);
                    //String valor_z = valores.substring(15, 17) + valores.substring(12, 14);

                    //Pasos restantes
                    //valor_gx = obtaintDecimaldeCa2_10bitsIzquierdaDerecha(valor_x);
                    //valor_gy = obtaintDecimaldeCa2_10bitsIzquierdaDerecha(valor_y);
                    //valor_gz = obtaintDecimaldeCa2_10bitsIzquierdaDerecha(valor_z);
                    //***************************************************************************************************************************

                    int accelLsb_x = (int)data_accel[0];
                    int accelLsb_y = (int)data_accel[1];
                    int accelLsb_z = (int)data_accel[2];

                    aceleracion = new Aceleracion(accelLsb_x,accelLsb_y,accelLsb_z);

                    gy_flexion_max_neg=aceleracion.getmaxNegGy(gy_flexion_max_neg);
                    gy_flexion_max_pos=aceleracion.getmaxPosGy(gy_flexion_max_pos);

                    gz_inclinacion_max_neg=aceleracion.getmaxNegGz(gz_inclinacion_max_neg);
                    gz_inclinacion_max_pos=aceleracion.getmaxPosGz(gz_inclinacion_max_pos);

                    gx_max_pos= aceleracion.getmaxPosGx(gx_max_pos);
                    gx_max_neg = aceleracion.getmaxNegGx(gx_max_neg);


                    float ang_x = (float) Math.atan(aceleracion.getAccelLsbX() / Math.sqrt(Math.pow(aceleracion.getAccekLsbY(), 2) + Math.pow(aceleracion.getAccelLsbZ(), 2))) * (float) (180.0 / 3.14);

                    updateMovCabeza = new UpdateMovCabeza(aceleracion,cara_fragment_3D,caraFlexionFragment,caraInclinacionFragment,caraRotacionFragment,desfaseAnguloYZ,gy_flexion_max_neg,gy_flexion_max_pos,gz_inclinacion_max_neg,gz_inclinacion_max_pos,gx_max_neg,gx_max_pos);



                    Log.d("desfaseY",String.valueOf(desfaseAnguloYZ.getDesfaseY()));
                    Log.d("desfaseZ",String.valueOf(desfaseAnguloYZ.getDesfaseZ()));

                   int anguloFlexion=new AnguloY().getAnguloY(aceleracion.getAccekLsbY());
                   int  anguloInclinacion = new AnguloZ().getAnguloZ(aceleracion.getAccelLsbZ());


                    Log.d("angulox",String.valueOf(ang_x));
                    Log.d("anguloy",String.valueOf(anguloFlexion));
                    Log.d("anguloz",String.valueOf(anguloInclinacion));
                    Log.d("gx",String.valueOf(aceleracion.getGx()));
                    Log.d("gy",String.valueOf(aceleracion.getGy()));
                    Log.d("gz",String.valueOf(aceleracion.getGz()));



                    if(grafinaLinealEnTiempoReal !=null) {

                        grafinaLinealEnTiempoReal.addEntryY(new AnguloY().getAnguloY(aceleracion.getAccekLsbY())+desfaseAnguloYZ.getDesfaseY(),new AnguloZ().getAnguloZ(aceleracion.getAccelLsbZ())+desfaseAnguloYZ.getDesfaseZ(),(float)aceleracion.getGy());
                    }

                    //Para generar un archivo con información con los valores del acelerometro, etc.
                   // if (pasadas == 0) {
                    //    escribirFichero(true, Float.toString(valor_gx), Float.toString(valor_gy), Float.toString(valor_gz), String.valueOf(ang_x), String.valueOf(ang_y), String.valueOf(ang_z));
                   // } else {
                    //    escribirFichero(false, Float.toString(valor_gx), Float.toString(valor_gy), Float.toString(valor_gz), String.valueOf(ang_x), String.valueOf(ang_y), String.valueOf(ang_z));
                   // }
                   // pasadas++;
                }


            }
            if (BluetoothLeService.ACTION_DATA_PERIODO_ACCEL.equals(action)) {
                Log.d("periodo_accel", "controllllll");
                String periodo_accel_value= intent.getStringExtra(BluetoothLeService.EXTRA_DATA_PERIODOD_ACCEL);
                if(periodo_accel_value!=null) {

                    Log.d("periodo_Accel",periodo_accel_value);
                    String periodo= hexDec(periodo_accel_value);

                    switch (Integer.valueOf(periodo))
                    {
                        case 1:
                            periodo="100 ms";
                            AccelPeriodo="100";
                            break;
                        case 2:
                            periodo="200 ms";
                            AccelPeriodo = "200";
                            break;
                        case 3:
                            periodo="500 ms";
                            AccelPeriodo = "500";
                            break;
                        case 4:
                            periodo="1000 ms";
                            AccelPeriodo = "1000";
                            break;
                    }
                    showToastAviso(fragmentActivity.getBaseContext(),"periodoAccelValue= "+periodo,2000);//BORRAR MAS ADELANTE.
                }
                else
                {
                    showToastAviso(fragmentActivity.getBaseContext(), "No hay periodo",3000);//BORRAR MAS ADELANTE.//50000
                }

            }

            if (BluetoothLeService.ACTION_DATA_NEAR_FIELD.equals(action)) {

                Log.d("nearfiled", "antes controllllll");
                String near_field_value= intent.getStringExtra(BluetoothLeService.EXTRA_DATA_NEAR_FIELD);
                if(near_field_value!=null) {
                    Log.d("nearfiled", "controllllll");
                    Log.d("near_field_value",near_field_value);
                    String numdec_near_field= hexDec(near_field_value);


                    switch (numdec_near_field)
                    {
                        case "0":
                            numdec_near_field="Deshabilitado";
                            enableNearField="Deshabilitado";
                            break;

                        case "1":

                            numdec_near_field="Habilitado";
                            enableNearField="Habilitado";
                            break;
                    }

                    showToastAviso(fragmentActivity.getBaseContext(),"near_field= "+numdec_near_field,2000);//BORRAR MAS ADELANTE.
                }
                else
                {
                    showToastAviso(fragmentActivity.getBaseContext(), "No hay near_field",2000);//BORRAR MAS ADELANTE.//50000
                }

            }
        }
    };










    public BroadcastReceiver getmGattUpdateReceiver()
    {
        return mGattUpdateReceiver;
    }



    private OnFragmentInteractionListener mListener;





    public DeviceControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param deviceName Parameter 1.
     * @param deviceAddress Parameter 2.
     * @return A new instance of fragment DeviceControlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeviceControlFragment newInstance(String deviceName, String deviceAddress) {
        DeviceControlFragment fragment = new DeviceControlFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, deviceName);
        args.putString(ARG_PARAM2, deviceAddress);
        fragment.setArguments(args);
        return fragment;
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mdeviceName = getArguments().getString(ARG_PARAM1);
            mdeviceAddress = getArguments().getString(ARG_PARAM2);
        }

        //Necesarios para poder interactuar en algunos "métodos" de esta clase
        resources=getResources();
        fragmentActivity=getActivity();

        //Realizamos la conección con el servidor.
        connectServidorBle();
        //Inicializamos las constantes de los recursos de la imagen SensorBateria.
        estadosBateriaSensorResource=new EstadosBateriaSensorResource();




    }
    public void connectServidorBle()
    {

        //Creamos el servicio de la coneccion
        //Intent gattServiceIntent = new Intent(getActivity().getApplicationContext(), BluetoothLeService.class);
        //Intent gattServiceIntent = new Intent(getActivity().getBaseContext(), BluetoothLeService.class);
        Intent gattServiceIntent = new Intent(fragmentActivity, BluetoothLeService.class);
        //Iniciar el servicio
        //getActivity().startService(gattServiceIntent);




        //Atar servicio a la actividad
        fragmentActivity.bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_control, container, false);


        txtSincronizando = (TextView) view.findViewById(R.id.txtSincronizando);
        txtNameDevice = (TextView) view.findViewById(R.id.txtNameDevice);
        txtWaitPlease = (TextView) view.findViewById(R.id.txtWaitPlease);


        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), HomeActivity.CALIBRI_BOLD);

        txtSincronizando.setTypeface(myFont);
        txtNameDevice.setTypeface(myFont);
        txtWaitPlease.setTypeface(myFont);

        img = (ImageView) view.findViewById(R.id.imgSensorSincronizando);




        if(mdeviceAddress!=null && mdeviceName!=null)
        {
            if(mdeviceName.contains("DIALOG"))
            {
                String valor = "Headpod - "+mdeviceAddress;
                txtNameDevice.setText(valor);
            }
            else
            {
                String valor = mdeviceName+" - "+mdeviceAddress;
                txtNameDevice.setText(valor);
            }

        }

        frameAnimationSensor = (AnimationDrawable) img.getDrawable();
        frameAnimationSensor.start();
        String mensaje = getResources().getString(R.string.wait_please) + "...";
        txtWaitPlease.setText(mensaje);
        txtWaitPlease.setVisibility(View.VISIBLE);

        return  view;
    }


    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        //intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_BATERIA);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_ENABLE_ACCEL);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_ACCEL);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_PERIODO_ACCEL);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_NEAR_FIELD);
        return intentFilter;
    }
/*
    //Actualiza txt de sincroinizacion fail o success
    private void updateConnectionState(final int resourceId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtSincronizando.setText(resourceId);
            }
        });
    }
*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        //getActivity().unbindService(mServiceConnection);
       // mBluetoothLeService = null;
       // mListener = null;
    }




    private void obtainGattServices(List<BluetoothGattService> gattServices)
    {

        if (gattServices == null) return;
        String uuid = null;

        String unknownServiceString = "unknown_service";
        String unknownCharaString = "unknown_characteristic";

        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();

        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();

        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        for (BluetoothGattService gattService : gattServices) {

            HashMap<String, String> currentServiceData = new HashMap<String, String>();

            uuid = gattService.getUuid().toString();//UUID Servicios
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();

            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();//Cacracterisigtcas de los servicios


            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {


                //Asignamos los atributos disponibles de los servicios
                if(SampleGattAttributes.ACCEL_ENABLE.equals(gattCharacteristic.getUuid().toString()))
                {
                    characAccelEnable=gattCharacteristic;
                }

                if(SampleGattAttributes.ACCEL_PERIOD.equals(gattCharacteristic.getUuid().toString()))
                {
                    characAccelPeriodo=gattCharacteristic;
                }

                if(SampleGattAttributes.ACCEL_VALUE.equals(gattCharacteristic.getUuid().toString()))
                {
                    characAccelValue=gattCharacteristic;
                }

                if(SampleGattAttributes.BAT_VALUE.equals(gattCharacteristic.getUuid().toString()))
                {
                    characBateria=gattCharacteristic;
                }

                if(SampleGattAttributes.NEAR_FIELD_VALUE.equals(gattCharacteristic.getUuid().toString()))
                {
                    characNearField=gattCharacteristic;
                }

                charas.add(gattCharacteristic);

                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }



            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }


    }


    public  ArrayList<ArrayList<BluetoothGattCharacteristic>> getmGattCharacteristics()
    {
        return mGattCharacteristics;
    }

    public void getValorBateria(Context context,int idioma)
    {

        this.idioma=idioma;

       // Log.d("UUID",mGattCharacteristics.get(posArrayServices).get(3).getUuid().toString());
        //BluetoothGattCharacteristic charaux= mGattCharacteristics.get(posArrayServices).get(3);
        contextLlamada=context;
        if(characBateria!=null)
        {
            final int charaProp = characBateria.getProperties();


            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                // If there is an active notification on a characteristic, clear
                // it first so it doesn't update the data field on the user interface.
             /*if (mNotifyCharacteristic != null) {
               mBluetoothLeService.setCharacteristicNotification(
                     mNotifyCharacteristic, false);
             mNotifyCharacteristic = null;
            }*/
                mBluetoothLeService.readCharacteristic(characBateria);
            }
        }
    }

    public void habilitarAcelerometro()
    {


       // ((HomeActivity) getActivity()).setEstadoAcelerometro(1);//Habilitado
        //Log.d("metodoHabilitar","enttro en habilitarAcelerometro");
       // BluetoothGattCharacteristic charaux= mGattCharacteristics.get(posArrayServices).get(0);

        if(characAccelEnable!=null) {
            final int charaProp = characAccelEnable.getProperties();

            if ((charaProp | (BluetoothGattCharacteristic.PROPERTY_WRITE)) > 0) {

                // If there is an active notification on a characteristic, clear
                // it first so it doesn't update the data field on the user interface.
             /*if (mNotifyCharacteristic != null) {
               mBluetoothLeService.setCharacteristicNotification(
                     mNotifyCharacteristic, false);
             mNotifyCharacteristic = null;
            }*/

                Log.d("metodoHabilitar", "entro en leer");
                //mBluetoothLeService.readCharacteristic(charaux);
                // mBluetoothLeService.writeCharacteristic(charaux);
                mBluetoothLeService.writeCharacteristicAccel(characAccelEnable, 1);//1 habilita;
            }
        }
    }


    public void desHabilitarAcelerometro()
    {

        // ((HomeActivity) getActivity()).setEstadoAcelerometro(1);//Habilitado
        Log.d("metodoHabilitar","enttro en habilitarAcelerometro");
        //BluetoothGattCharacteristic charaux= mGattCharacteristics.get(posArrayServices).get(0);
        if(characAccelEnable!=null) {
            final int charaProp = characAccelEnable.getProperties();

            if ((charaProp | (BluetoothGattCharacteristic.PROPERTY_WRITE)) > 0) {
                Log.d("metodoHabilitar", "entro en leer");
                mBluetoothLeService.writeCharacteristicAccel(characAccelEnable, 0);//0 deshabilita;
            }
        }
    }

    public void cambiarPeriodoAcelerometro(int periodo)
    {
        // ((HomeActivity) getActivity()).setEstadoAcelerometro(1);//Habilitado
        Log.d("metodoHabilitar","enttro en habilitarAcelerometro");
        //BluetoothGattCharacteristic charaux= mGattCharacteristics.get(posArrayServices).get(1);
        if(characAccelPeriodo!=null) {
            final int charaProp = characAccelPeriodo.getProperties();

            if ((charaProp | (BluetoothGattCharacteristic.PROPERTY_WRITE)) > 0) {
                Log.d("metodoPeriodo", "entro");
                mBluetoothLeService.writeCharacteristiPeriodAccel(characAccelPeriodo, periodo);


            }
        }
    }

    public void getPeriodoAcelerometro(Context context)
    {

       // BluetoothGattCharacteristic charaux= mGattCharacteristics.get(posArrayServices).get(1);

        contextLlamada=context;
        final int charaProp = characAccelPeriodo.getProperties();
        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
            // If there is an active notification on a characteristic, clear
            // it first so it doesn't update the data field on the user interface.
             /*if (mNotifyCharacteristic != null) {
               mBluetoothLeService.setCharacteristicNotification(
                     mNotifyCharacteristic, false);
             mNotifyCharacteristic = null;
            }*/
            mBluetoothLeService.readCharacteristic(characAccelPeriodo);
        }

    }






    public void habilitarNotificacionesAcelerometro()
    {

        // ((HomeActivity) getActivity()).setNotificacionesAcelerometro(1);//Habilitado
        // BluetoothGattCharacteristic characteristic= mGattCharacteristics.get(posArrayServices).get(2);
        if(characAccelValue!=null) {
            final int charaProp = characAccelValue.getProperties();

            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {

                mNotifyCharacteristic = characAccelValue;
                mBluetoothLeService.setCharacteristicNotification(
                        characAccelValue, true);

            }
        }
    }

    public void desHabilitarNotificacionesAcelerometro()
    {
        // ((HomeActivity) getActivity()).setNotificacionesAcelerometro(1);//Habilitado
        // BluetoothGattCharacteristic characteristic= mGattCharacteristics.get(posArrayServices).get(2);
        if(characAccelValue!=null) {
            final int charaProp = characAccelValue.getProperties();

            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {

                mNotifyCharacteristic = characAccelValue;
                mBluetoothLeService.setCharacteristicNotificationDes(
                        characAccelValue, false);
            }
        }
    }


    public void habilitarNearField()
    {
        // ((HomeActivity) getActivity()).setEstadoAcelerometro(1);//Habilitado
        Log.d("metodoNearField","enttro en habilitarNearField");
        //BluetoothGattCharacteristic charaux= mGattCharacteristics.get(posArrayServices).get(4);
        if(characNearField!=null) {
            final int charaProp = characNearField.getProperties();
            if ((charaProp | (BluetoothGattCharacteristic.PROPERTY_WRITE)) > 0) {
                Log.d("metodoNearField", "entro en leer");
                mBluetoothLeService.writeCharacteristicNearField(characNearField, 1);//1 habilita;
            }
        }

    }


    public void desHabilitarNearField()
    {
        // ((HomeActivity) getActivity()).setEstadoAcelerometro(1);//Habilitado
        Log.d("metodoNearField","enttro en habilitarNearField");
        //BluetoothGattCharacteristic charaux= mGattCharacteristics.get(posArrayServices).get(4);


        if(characNearField!=null) {
            final int charaProp = characNearField.getProperties();

            if ((charaProp | (BluetoothGattCharacteristic.PROPERTY_WRITE)) > 0) {
                Log.d("metodoNearField", "entro en leer");
                mBluetoothLeService.writeCharacteristicNearField(characNearField, 0);//1 habilita;
            }
        }

    }




    //Métodos que actuaizan las instancias de dichas clases para utilizarlas cada vez que se lee datos del acelerómetro
    //********************************************************************************************************************************

    public void inicializarCara3D(Cara3DFragment cara3D)
    {
        cara_fragment_3D=cara3D;
    }

    public void inicializarCaras2D(CaraFlexionFragment cf, CaraInclinacionFragment ci, CaraRotacionFragment cr)
    {
        caraFlexionFragment = cf;
        caraInclinacionFragment = ci;
        caraRotacionFragment = cr;

    }

    public void incializarGraficaLineal(GrafinaLinealEnTiempoReal grafinaLinealEnTiempoReal)
    {

        this.grafinaLinealEnTiempoReal = grafinaLinealEnTiempoReal;
    }

    public void calibrarMovFlexion(int desfaseAnguloY)
    {
        desfaseAnguloYZ.setDesfaseY(desfaseAnguloYZ.valorAbsolucto(desfaseAnguloY));
    }

    public void calibrarMovInclinacion(int desfaseAnguloZ)
    {
        desfaseAnguloYZ.setDesfaseZ(desfaseAnguloYZ.valorAbsolucto(desfaseAnguloZ));
    }
    //********************************************************************************************************************************
    public void leerDatosAcelerometro(Cara3DFragment cara3D)
    {


        //getChildFragmentManager();
        //Obtengo la cara_en_3D

        /*
        FragmentManager fragmentManager;
        fragmentManager = ((HomeActivity)getActivity()).getSupportFragmentManager();

        cara_fragment_3D  =  ((AllAvatarFragment)fragmentManager.findFragmentByTag("fragment_all_avatar")).cara_fragment_3D;
*/
        cara_fragment_3D=cara3D;



        BluetoothGattCharacteristic characteristic= mGattCharacteristics.get(posArrayServices).get(2);

        final int charaProp = characteristic.getProperties();

        if ((charaProp | (BluetoothGattCharacteristic.PROPERTY_READ ))  > 0 ) {

            // If there is an active notification on a characteristic, clear
            // it first so it doesn't update the data field on the user interface.

            //if (mNotifyCharacteristic != null) {
            //   mBluetoothLeService.setCharacteristicNotification(
            //         mNotifyCharacteristic, false);
           //  mNotifyCharacteristic = null;
           // }

            // mBluetoothLeService.notificaciones_accel(characteristic);
            mBluetoothLeService.readCharacteristic(characteristic);
        }



    }




    public void showToastLonger(String mensaje, int ms) {

        //Toast.makeText(getApplicationContext(),error, Toast.LENGTH_LONG).show();

        if(contextLlamada!=null) {
            final Toast tag = Toast.makeText(contextLlamada, mensaje, Toast.LENGTH_SHORT);


            //final Toast tag = Toast.makeText(getActivity().getSupportFragmentManager().findFragmentByTag("fragment_all_avatar").getContext(), mensaje,Toast.LENGTH_SHORT);

            tag.show();

            new CountDownTimer(ms, 1000) {

                public void onTick(long millisUntilFinished) {
                    tag.show();
                }

                public void onFinish() {
                    tag.show();
                }

            }.start();
        }
    }



    public void showToastAviso(Context context,String mensaje, int ms) {

        //Toast.makeText(getApplicationContext(),error, Toast.LENGTH_LONG).show();
            final Toast tag = Toast.makeText(context, mensaje, Toast.LENGTH_SHORT);
            tag.show();
            new CountDownTimer(ms, 1000) {

                public void onTick(long millisUntilFinished) {
                    tag.show();
                }

                public void onFinish() {
                    tag.show();
                }

            }.start();

    }
    //Métodos para la bateria
    //***************************************************************************************
    public int getNivelBat()
    {
        return nivelBat;
    }

    public void setNivelBat(int nivelBatActual)
    {
        nivelBat=nivelBatActual;
    }



    public String obtenerRecursoImg(int nivelBat)
    {
        String idResource="null";
        if(idioma>=0) {
            switch (getNivelIntervarloBat(nivelBat)) {
                case 0:
                    idResource = estadosBateriaSensorResource.imgVinculada[idioma][0];//
                    break;

                case 1:
                    idResource = estadosBateriaSensorResource.imgVinculada[idioma][1];
                    break;

                case 2:
                    idResource = estadosBateriaSensorResource.imgVinculada[idioma][2];
                    break;


                case 3:
                    idResource = estadosBateriaSensorResource.imgVinculada[idioma][3];
                    break;

                case 4:
                    idResource = estadosBateriaSensorResource.imgVinculada[idioma][4];
                    break;
                case 5:
                    idResource = estadosBateriaSensorResource.imgVinculada[idioma][5];
                    break;

                case 6:
                    idResource = estadosBateriaSensorResource.imgVinculada[idioma][6];
                    break;
            }
        }

        return idResource;

    }


    public int getNivelIntervarloBat(int nivelBat)
    {

        if(nivelBat<5)
        {
            return 0;
        }

        else if(nivelBat>5 && nivelBat<=15)
        {
            return 1;
        }

        else if(nivelBat>15 && nivelBat<=25)
        {
            return 2;
        }


        else if(nivelBat>25 && nivelBat<=35)
        {
            return 3;
        }


        else if(nivelBat>35 && nivelBat<=45)
        {
            return 4;
        }


        else if(nivelBat>45 && nivelBat<=65)
        {
            return 5;
        }

        else //>65 && <85
        {
            return 6;
        }

    }


    public void setImageBatSensor(ImageView img, String idResourse)
    {
        if (getNivelBat()<5)
        {
            //imgAnimacionBatBaja
            int id = resources.getIdentifier(idResourse, null, null);
            img.setImageResource(id);
            frameAnimationBatBaja = (AnimationDrawable) img.getDrawable();
            frameAnimationBatBaja.start();
        }
        else {
            if(frameAnimationBatBaja!=null && frameAnimationBatBaja.isRunning())
            {
                frameAnimationBatBaja.stop();
            }
            //obtenemos el id de la imagen
            int id = resources.getIdentifier(idResourse, null, null);
            //Colocamos la imagen
            img.setImageResource(id);
        }

    }

    //***************************************************************************************

    public int obtaintDecimaldeCa2_10bitsIzquierdaDerecha(String valor)
    {


        //transformamos el strin a valor en hexadecimal

        int numHex = Integer.parseInt(valor, 16);

        //convertimos a binario el hexadecimal
        String binary = Integer.toBinaryString(numHex);

        //Comprobamos tamaño del binario, sino añadimos ceros a la izquierda
        String aux="";
        if(binary.length()<16)
        {
            for (int i=0; i<16-binary.length();i++)
            {
                aux+='0';
            }

            binary=aux+binary;
        }

        //Cogemos los 10 bits (derecha a izquierda)
        String ca2 = binary.substring(1,10);



        //transformamos a decimal

        String binario= "";
        //Convertimos binario a decimal
        if(ca2.charAt(0)=='1')
        {

            int longitud = ca2.length();

            for(int i = 0;i < longitud;i++){
                // recorremos la cadena de numeros
                if(ca2.charAt(i) == '1'){
                    //resultado += Math.pow(2,potencia);
                    binario+=0;
                }
                else
                {
                    binario+=1;
                }
            }


            return (convertBinarioaDecimal(binario)+1)*-1;
        }
        else
        {

            return convertBinarioaDecimal(ca2);

        }
    }






    //De derecha a izquierda
    public int obtaintDecimaldeCa2_10bits(String valor)
    {


        //transformamos el strin a valor en hexadecimal

        int numHex = Integer.parseInt(valor, 16);

        //convertimos a vinario el hexadecimal
        String binary = Integer.toBinaryString(numHex);

        //Comprobamos tamaño del binario, sino añadimos ceros a la izquierda
        String aux="";
        if(binary.length()<16)
        {
            for (int i=0; i<16-binary.length();i++)
            {
                aux+='0';
            }

            binary=aux+binary;
        }

        //Cogemos los 10 bits (derecha a izquierda)
        String ca2 = binary.substring(6,16);



        //transformamos a decimal

        String binario= "";
        //Convertimos binario a decimal
        if(ca2.charAt(0)=='1')
        {

            int longitud = ca2.length();

            for(int i = 0;i < longitud;i++){
                // recorremos la cadena de numeros
                if(ca2.charAt(i) == '1'){
                    //resultado += Math.pow(2,potencia);
                    binario+=0;
                }
                else
                {
                    binario+=1;
                }
            }


            return (convertBinarioaDecimal(binario)+1)*-1;
        }
        else
        {

            return convertBinarioaDecimal(ca2);

        }
    }




    public int convertBinarioaDecimal(String ca2)
    {

        int resultado = 0;
        int longitud = ca2.length();
        //Numero de digitos que tiene nuestro binario
        // Aqui almacenaremos nuestra respuesta
        int potencia = longitud - 1;
        for(int i = 0;i < longitud;i++){
            // recorremos la cadena de numeros
            if(ca2.charAt(i) == '1'){
                resultado += Math.pow(2,potencia);
            }
            potencia --;//drecremantamos la potencia
        }
        return resultado;
    }



    public float valorAbsolutoAngulo(float angulo)
    {
        if(angulo<0)
        {
            angulo=angulo*-1;
        }

        return angulo;


    }


    public String hexDec(String num)
    {
        int sum=0;
        int newnum = 0;
        num=num.trim();//quitamos elementos vacios.
        String digit = num.toUpperCase();
        for(int i=0;i<digit.length();i++)
        {
            char c = digit.charAt(digit.length()-i-1);

            if(c=='A')
            {
                newnum = 10;
            }
            else if(c=='B')
            {
                newnum = 11;
            }
            else if(c=='C')
            {
                newnum = 12;
            }
            else if(c=='D')
            {
                newnum = 13;
            }
            else if(c=='E')
            {
                newnum = 14;
            }
            else if(c=='F')
            {
                newnum = 15;
            }
            else
            {
                newnum = Character.getNumericValue(c);
            }
            sum = (int) (sum + newnum*Math.pow(16,i));
        }
        return (String.valueOf(sum));
    }




    //No borrar, puede servir para crear logs.
    //*************************************************************************************************************************************

    //Método de prueba para ver el correcto funcionamiento
    public void incrementar_decrementarBateria()
    {
        Log.d("contador",String.valueOf(cont));
        if(cont<65 && chiv==0) {
            setNivelBat(cont += 10);
            chiv=0;
        }
        else
        {
            chiv=1;
        }

        if(chiv==1)
        {

            setNivelBat(cont -= 10);

            if(cont<=-10)
            {
                chiv=0;
            }
        }
    }

    //Método para generar un archivo con la información del acelerómetro
    public void escribirFichero(boolean nuevaMedicion,String gx, String gy, String gz, String angx, String angy,String angz)
    {


        boolean mExternalStorageAvailable,mExternalStorageWriteable;


        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
            // Toast.makeText(getActivity().getApplicationContext(), "se puede leer y escribir en la microSD", Toast.LENGTH_SHORT).show();
            Log.d("FICHERO","se puede leer y escribir en microSD");

        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
            //Toast.makeText(getActivity().getApplicationContext(), "SÓLO ES POSIBLE leer la microSD", Toast.LENGTH_SHORT).show();
            Log.d("FICHERO","solo se puede leer microSD");
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
            //Toast.makeText(getActivity().getApplicationContext(), "NO SE PUEDE TRABAJAR CON LA microSD", Toast.LENGTH_SHORT).show();
            Log.d("FICHERO","No se puede trabajar con la microSd");
        }


        File root = android.os.Environment.getExternalStorageDirectory();

        File dir = new File (root.getAbsolutePath() + "/Headpod");
        // dir = new File ("/storage/extSdCard/Proyecto");
        if(!dir.exists()) {
            boolean aaux= dir.mkdirs();
            Log.d("DIRECTORIO:",String.valueOf(aaux));
        }
        else
        {
            Log.d("DIRECTORIO:","ya existe");
        }


        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        int hora24h= calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        // calendar.get(Calendar.HOUR);        // gets hour in 12h format

        int min=calendar.get(Calendar.MINUTE);
        int second=calendar.get(Calendar.SECOND);
        int ms= calendar.get(Calendar.MILLISECOND);


        if(nuevaMedicion) {
            nombre_fichero="MedicionSession-"+date.toString()+".txt";

            File f = new File(root.getAbsolutePath()+"/Headpod",nombre_fichero );

            int cont=1;
            while (f.exists()) {
                nombre_fichero = "MedicionSession-" + date.toString() + "-" + String.valueOf(cont) + ".txt";
                f = new File(root.getAbsolutePath() + "/Headpod", nombre_fichero);
                cont = cont + 1;
            }

            try{

                OutputStreamWriter fout =
                        new OutputStreamWriter(
                                new FileOutputStream(f,true));//true para sobr
                //fout.write("x= 5"+"\n"+" y=6"+"\n"+ " z=30"+"\n");
                fout.write("\n");
                fout.write("\n");
                fout.write("*******************************************************************************************************************************************");
                fout.write("\n");
                fout.write("HORA"+":"+"MINUTOS"+":"+"SEGUNDOS"+":"+"MILISEGUNDOS"+" , "+"G_X"+" , "+"G_Y"+" , "+"G_Z"+" , "+"ANGULO_X"+" , "+"ANGULO_Y"+" , "+"ANGULO_Z");
                fout.write("\n");
                fout.write("*******************************************************************************************************************************************");
                fout.write("\n");
                fout.write("\n");
                fout.write(hora24h+":"+min+":"+second+":"+ms+" , "+gx+" , "+gy+" , "+gz+" , "+angx+" , "+angy+" , "+angz );
                fout.write("\n");
                fout.close();
            }
            catch (Exception ex)
            {
                Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
            }

        }
        else {
            File f = new File(root.getAbsolutePath() + "/Headpod", nombre_fichero);
            try{

                OutputStreamWriter fout =
                        new OutputStreamWriter(
                                new FileOutputStream(f,true));//true para sobr
                //fout.write("x= 5"+"\n"+" y=6"+"\n"+ " z=30"+"\n");

                fout.write(hora24h+":"+min+":"+second+":"+ms+" , "+gx+" , "+gy+" , "+gz+" , "+angx+" , "+angy+" , "+angz );
                fout.write("\n");
                fout.close();
            }
            catch (Exception ex)
            {
                Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
            }

        }

/*

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF8"));

        out.write("una linea de texto");
        out.write("\n");
        out.close();
*/


    }//fin funcion

    //Método para generar un archivo con información de la bateria
    public void escribirBateria(boolean nuevaMedicion,String nivelBat, String enableAccel, String periodoAccel, String nearField)
    {


        boolean mExternalStorageAvailable,mExternalStorageWriteable;


        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
            // Toast.makeText(getActivity().getApplicationContext(), "se puede leer y escribir en la microSD", Toast.LENGTH_SHORT).show();
            Log.d("FICHERO","se puede leer y escribir en microSD");

        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
            //Toast.makeText(getActivity().getApplicationContext(), "SÓLO ES POSIBLE leer la microSD", Toast.LENGTH_SHORT).show();
            Log.d("FICHERO","solo se puede leer microSD");
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
            //Toast.makeText(getActivity().getApplicationContext(), "NO SE PUEDE TRABAJAR CON LA microSD", Toast.LENGTH_SHORT).show();
            Log.d("FICHERO","No se puede trabajar con la microSd");
        }


        File root = android.os.Environment.getExternalStorageDirectory();

        File dir = new File (root.getAbsolutePath() + "/Headpod/Bateria");
        // dir = new File ("/storage/extSdCard/Proyecto");
        if(!dir.exists()) {
            boolean aaux= dir.mkdirs();
            Log.d("DIRECTORIO:",String.valueOf(aaux));
        }
        else
        {
            Log.d("DIRECTORIO:","ya existe");
        }


        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        int hora24h= calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        // calendar.get(Calendar.HOUR);        // gets hour in 12h format

        int min=calendar.get(Calendar.MINUTE);
        int second=calendar.get(Calendar.SECOND);
        int ms= calendar.get(Calendar.MILLISECOND);


        if(nuevaMedicion) {
            nombre_fichero="Bat-"+date.toString()+".txt";

            File f = new File(root.getAbsolutePath()+"/Headpod/Bateria",nombre_fichero );

            int cont=1;
            while (f.exists()) {
                nombre_fichero = "Bat-" + date.toString() + "-" + String.valueOf(cont) + ".txt";
                f = new File(root.getAbsolutePath() + "/Headpod/Bateria", nombre_fichero);
                cont = cont + 1;
            }

            try{

                OutputStreamWriter fout =
                        new OutputStreamWriter(
                                new FileOutputStream(f,true));//true para sobr
                //fout.write("x= 5"+"\n"+" y=6"+"\n"+ " z=30"+"\n");
                fout.write("\n");
                fout.write("\n");
                fout.write("*******************************************************************************************************************************************");
                fout.write("\n");
                fout.write("nivel de bateria"+":"+"accel_Enable"+":"+"periodo_accel (ms)"+":"+"near_field");
                fout.write("\n");
                fout.write("*******************************************************************************************************************************************");
                fout.write("\n");
                fout.write("\n");
                fout.write(hora24h+":"+min+":"+second+":"+ms+" , "+nivelBat+" , "+enableAccel+" , "+periodoAccel+" , "+nearField);
                fout.write("\n");
                fout.close();
            }
            catch (Exception ex)
            {
                Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
            }

        }
        else {
            File f = new File(root.getAbsolutePath() + "/Headpod/Bateria", nombre_fichero);
            try{

                OutputStreamWriter fout =
                        new OutputStreamWriter(
                                new FileOutputStream(f,true));//true para sobr
                //fout.write("x= 5"+"\n"+" y=6"+"\n"+ " z=30"+"\n");

                fout.write(hora24h+":"+min+":"+second+":"+ms+" , "+nivelBat+" , "+enableAccel+" , "+periodoAccel+" , "+nearField);
                fout.write("\n");
                fout.close();
            }
            catch (Exception ex)
            {
                Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
            }

        }

/*

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF8"));

        out.write("una linea de texto");
        out.write("\n");
        out.close();
*/


    }//fin funcion
    //*************************************************************************************************************************************




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mdeviceAddress);
            Log.d("Error", "Connect request result=" + result);
        }


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);

    }
}

/*

    public int obtaintDecimaldeCa2_10bits(String valor)
    {


        //transformamos el strin a valor en hexadecimal

        int numHex = Integer.parseInt(valor, 16);

        //convertimos a vinario el hexadecimal
        String binary = Integer.toBinaryString(numHex);

        //Comprobamos tamaño del binario, sino añadimos ceros a la izquierda
        String aux="";
        if(binary.length()<16)
        {
            for (int i=0; i<16-binary.length();i++)
            {
                aux+='0';
            }

            binary=aux+binary;
        }

        //Cogemos los 10 bits (derecha a izquierda)
        String ca2 = binary.substring(6,16);



        //transformamos a decimal

        String binario= "";
        //Convertimos binario a decimal
        if(ca2.charAt(0)=='1')
        {

            int longitud = ca2.length();

            for(int i = 0;i < longitud;i++){
                // recorremos la cadena de numeros
                if(ca2.charAt(i) == '1'){
                    //resultado += Math.pow(2,potencia);
                    binario+=0;
                }
                else
                {
                    binario+=1;
                }
            }


            return (convertBinarioaDecimal(binario)+1)*-1;
        }
        else
        {

            return convertBinarioaDecimal(ca2);

        }
    }


 */