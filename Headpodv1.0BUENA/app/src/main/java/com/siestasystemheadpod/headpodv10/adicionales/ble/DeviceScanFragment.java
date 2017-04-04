package com.siestasystemheadpod.headpodv10.adicionales.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.HttpAuthHandler;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
//import com.siestasystemheadpod.headpodv10.adicionales.ble.ListViewDevicesAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


//Libreria para usar la accion de los dedos de refrescar haciendo scroll hacia abajo.
//https://github.com/chrisbanes/Android-PullToRefresh

//http://guides.codepath.com/android/implementing-pull-to-refresh-guide//->esta


//Error BLE en tablet del trabajo.Posible solucion.
//http://stackoverflow.com/questions/35376682/how-to-fix-android-ble-scan-failed-application-registration-failed-error

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeviceScanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeviceScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceScanFragment extends Fragment implements ListView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AnimationDrawable frameAnimationSensor;
    private ImageView img;//Imagen de las ondas (movil- ondas - sensor)
    private final static int TIME_VISIBILITY_BT=300;//SEGUNDOS
    private final static int REQUEST_DISCOVERABLE_ENABLE_BT = 1;
    private final static int REQUEST_DISCOVERABLE_BT = 2;
    private static final String CALIBRI_BOLD="fonts/Calibri Bold.ttf";
    private TextView txtPrincipal;
    private TextView txtSecundario;
    private ListView listViewDevices;

    private BluetoothLeScanner mLEScanner;

    private ScanSettings settings;
    private List<ScanFilter> filters;


    //Device BLE
    //**************************************************************
    private BluetoothAdapter bluetooth;
    private ListViewDevicesAdapter mlistDeviceAdapter;
    private final static int REQUEST_ENABLE_BT = 1;//nº > 0
    private boolean mScanning;//Para saber si esta buscando sensores
    private Handler mHandler;
    // Stops scanning after x seconds.
    private static final long SCAN_PERIOD_BLE_OFF = 15000;//Tiempo de busqueda (devices) si el bluetooth se acaba de activar. (OJO: Tiene que ser mayor, minimo 15s)
    private static final long SCAN_PERIOD_BLE_ON = 5000;//Tiempo de busquedad (devics) cuando el bluetooth ya estaba activado de antes.
    private long SCAN_PERIOD = SCAN_PERIOD_BLE_ON;//uno de los dos tiempps anteriores
    //**********************************************************************
    private Runnable scanTask;//tarea que se encargara de buscar los devices durante x tiempo ( 10 segundos)


    private ScanCallback scanCallback;


    //Lo nuevo:
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private int permissionCheck;

    View view;


    private SwipeRefreshLayout swipeContainer;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    //Para la configuración de la orientación. Con esto respetamos el diseño landScape
    private FrameLayout frameLayout;

    public DeviceScanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeviceScanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeviceScanFragment newInstance(String param1, String param2) {
        DeviceScanFragment fragment = new DeviceScanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        mHandler = new Handler();
        //Comprobamos si BLE es sopportado
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(getContext(), R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        bluetooth = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (bluetooth == null) {
            Toast.makeText(getContext(), R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;

        }



    }



    //http://stackoverflow.com/questions/17116602/using-onconfigurationchanged-in-a-fragment
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        ((HomeActivity)getActivity()).cambiarIdioma();

        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_scan_devices, null);

        setContenidoVista(view);

        frameLayout .addView(view);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        frameLayout = new FrameLayout(getActivity());
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_scan_devices, container, false);
        view = inflater.inflate(R.layout.fragment_scan_devices, container, false);


        setContenidoVista(view);

        frameLayout .addView(view);
        //return view;
        return frameLayout;
    }

    public void setContenidoVista(View view)
    {

        txtPrincipal = (TextView) view.findViewById(R.id.txtPrincipal);
        txtSecundario= (TextView) view.findViewById(R.id.txtSecundario);

        listViewDevices = (ListView) view.findViewById(R.id.listDevices);

        Typeface myFont= Typeface.createFromAsset(getActivity().getAssets(), CALIBRI_BOLD);
        txtPrincipal.setTypeface(myFont);
        txtSecundario.setTypeface(myFont);

        img = (ImageView) view.findViewById(R.id.buscando_sensor_ondas);
        img.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.buscando_sensor));
        frameAnimationSensor = (AnimationDrawable) img.getDrawable();
        frameAnimationSensor.stop();

        //Esto no lo uso porque se oculta detrás una imagen mas pequeña y empieza a aparecer y desaparecer.
        //img.setBackgroundResource(R.drawable.buscando_sensor);
        //frameAnimationSensor = (AnimationDrawable) img.getBackground();

        /*
        ((ImageView)view.findViewById(R.id.buscando_sensor_ondas)).post(new Runnable() {
            @Override
            public void run() {

                ((ImageView)view.findViewById(R.id.buscando_sensor_ondas)).setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.buscando_sensor));
                AnimationDrawable drawable = (AnimationDrawable) ((ImageView)view.findViewById(R.id.buscando_sensor_ondas)).getDrawable();
                frameAnimationSensor=drawable;
                drawable.start();
            }
        });
        */



    }




    /*
    onResume(): Se llama cuando la actividad va a comenzar a interactuar con el usuario. Es un buen lugar para lanzar las animaciones y la música.
     */
    @Override
    public void onResume() {
        Log.d("Resume","vuelvo al fragmento");
        super.onResume();
        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.

            if (!bluetooth.isEnabled()) {
                if (!bluetooth.isEnabled()) {

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }



            }
        else {//Aquí se mete siempre que este activado el BLE, inclusive habiendo sido activado despues de no haberlo estado
                //(Al aceptar el dialog de permitir activar BLE).
                //El else es necesario para controlar que la UI funcione bien.
                //Log.d("bluetooth", "Entro solo si estoy activado");


                if(soportaBLE())
                {
                    Toast.makeText(getContext(), R.string.ble_supported, Toast.LENGTH_SHORT).show();
                    // Initializes list view adapter.
                    mlistDeviceAdapter = new ListViewDevicesAdapter(getActivity());

                    listViewDevices.setAdapter(mlistDeviceAdapter);

                    listViewDevices.setOnItemClickListener(this);

                    frameAnimationSensor.start();
                    //Escaneamos devices
                    scanLeDevice(true);

                }
                else
                {
                    txtPrincipal.setText(R.string.imposible_buscar_sensor);
                    txtSecundario.setText(R.string.ble_not_supported);
                }

            }
    }

    public boolean soportaBLE()
    {
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        //showToastLonger("click en "+Integer.toString(position),1000);


        final BluetoothDevice device = mlistDeviceAdapter.getDevice(position);
        if (device == null) return;
      //  final Intent intent = new Intent(this, DeviceControlActivity.class);
        //intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
        //intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
        if (mScanning) {



            //bluetooth.stopLeScan(mLeScanCallback);
            stopScanDevice();
            mScanning = false;
        }
        //startActivity(intent);



        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        //Paso 1: Obtener la instancia del administrador de fragmentos
        fragmentManager = getActivity().getSupportFragmentManager();
        //Paso 2: Crear una nueva transacción
        fragmentTransaction = fragmentManager.beginTransaction();
        //Paso 3: Crear un nuevo fragmento y añadirlo

        DeviceControlFragment sincronizando_sensor_fragment = DeviceControlFragment.newInstance(device.getName(),device.getAddress());


        fragmentTransaction.replace(R.id.contenedorHome, sincronizando_sensor_fragment, "fragment_sincronizando_sensor");
        fragmentTransaction.addToBackStack("fragment_sincronizando_sensor");
        //((HomeActivity) getActivity()).pilaAuxFragments.add("fragment_sincronizando_sensor");
        fragmentTransaction.commit();
        Log.d("arranco_fragme","devicecontrol_arrancado");


       // sincronizando_sensor_fragment.getValorBateria();



    }




    //Entra en este método cuando abre el dialog de permiso de bluetooth
    //o cuando se ha cambiado de fragmento.
    @Override
    public void onPause() {
        super.onPause();

        Log.d("exitPause","has saldio del fragmento, paro");
        if(bluetooth !=null && bluetooth.isEnabled()) {
            scanLeDevice(false);
        }

        if(mHandler!=null && scanTask!=null)
        {
            stopScanDevicesTask();
        }

        if(mlistDeviceAdapter!=null)
        {
            mlistDeviceAdapter.clear();
        }

       // frameAnimationSensor.stop();


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if ( requestCode==REQUEST_ENABLE_BT ) {
           // frameAnimationSensor.stop();
            switch (resultCode)
            {
                case Activity.RESULT_CANCELED:
                    //Retrocedemos al anterior fragmento.
                    getActivity().getSupportFragmentManager().popBackStack();
                    //Mostramos mensaje de aviso de no permitir activar sensor
                    showToastLonger(getResources().getString(R.string.sensor_no_habilitado),4000);
                    //return;
                    break;

                case Activity.RESULT_OK:
                    SCAN_PERIOD=SCAN_PERIOD_BLE_OFF;//Le asignamos un mayor tiempo por haber estado deshabilitado
                    //Log.d("bluetooth","Permitido");
                    frameAnimationSensor.start();
                    break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }





    void startScanDevicesTask()
    {
        scanTask.run();

    }

    //Método necesario para detener el hilo de la tarea que espera rellenar los huecos.
    void stopScanDevicesTask()
    {
        mHandler.removeCallbacks(scanTask);
    }




    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.

           // Log.d("enable","TRUE");



            /*
                mHandler.postDelayed(scanTask = new Runnable() {//Es necasiro matar el hilo
                    @Override
                    public void run() {

                        // Log.d("run","activado");
                        mScanning = false;

                        if (mlistDeviceAdapter != null) {
                            txtPrincipal.setText(getResources().getQuantityText(R.plurals.sensores_encontrado,
                                    mlistDeviceAdapter.getCount()));

                            if (mlistDeviceAdapter.getCount() == 0) {
                                txtSecundario.setText(getResources().getString(R.string.no_found_sensor));
                                img.setImageDrawable(frameAnimationSensor.getFrame(0));
                                frameAnimationSensor.stop();
                            } else {
                                img.setImageDrawable(frameAnimationSensor.getFrame(3));
                                frameAnimationSensor.stop();
                                txtSecundario.setText(getResources().getQuantityText(R.plurals.lista_sensores, mlistDeviceAdapter.getCount()));
                            }
                        }

                        bluetooth.stopLeScan(mLeScanCallback);
                        //invalidateOptionsMenu();

                    }
                }, SCAN_PERIOD);//Esto se ejecutara transcurrido el periodo de tiempo.
*/



                // Log.d("bluetooth","iniciar_escaneo");
                mScanning = true;

            startScanDevice();

            /*
                // Check before call the function
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Build.VERSION.SDK_INT >= 21
                    //bluetooth.getBluetoothLeScanner().startScan(filterList, scanSetting, scanCallback);
                    Log.d("version", "api>21");
                    bluetoothScanStart();
                } else {
                    Log.d("version", "api<21");
                    bluetooth.startLeScan(mLeScanCallback);
                }
                */

                //bluetooth.startLeScan(mLeScanCallback);
                txtPrincipal.setText(getResources().getString(R.string.sensor_buscando));




        } else {
            //Log.d("enable","FALSE");
            mScanning = false;


            stopScanDevice();

            /*
            // Check before call the function
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Build.VERSION.SDK_INT >= 21
                //bluetooth.getBluetoothLeScanner().startScan(filterList, scanSetting, scanCallback);

                BluetoothLeScanner scanner = bluetooth.getBluetoothLeScanner();

                if(scanCallback!=null) {
                    scanner.stopScan(scanCallback);
                }

            } else {
                bluetooth.stopLeScan(mLeScanCallback);
            }
            */




            ////bluetooth.stopLeScan(mLeScanCallback);
            //img.setImageDrawable(frameAnimationSensor.getFrame(0));
            //frameAnimationSensor.stop();

        }
        //invalidateOptionsMenu();
    }

    // Device scan callback  Para avis menores que 21
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (device != null && device.getName() != null && device.getAddress() != null) {
                                Log.d("Dispositivo encontrado", "Nombre=" + device.getName() + " DirecciónMac=" + device.getAddress());
                                if (device.getName().contains("AOT") || device.getName().contains("HEADPOD") || device.getName().contains("DIALOG")) {
                                    mlistDeviceAdapter.addDevice(device);
                                    mlistDeviceAdapter.notifyDataSetChanged();
                                    //adaptamos el tamaño del listview
                                    setListViewHeightBasedOnChildren(listViewDevices);
                                }
                            }
                        }
                    });
                }
            };

    //Para versiones android Marshmallow
  public void bluetoothScanStart()
  {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          checkLocationPermission();
          BluetoothLeScanner scanner = bluetooth.getBluetoothLeScanner();
          // scan for devices
          scanner.startScan(scanCallback = new ScanCallback() {
              @Override
              public void onScanResult(int callbackType, ScanResult result) {
                  // get the discovered device as you wish
                  // this will trigger each time a new device is found

                  //BluetoothDevice device = result.getDevice();
                  if (Build.VERSION.SDK_INT >= 21) {

                      //Log.i("callbackType", String.valueOf(callbackType));
                      //Log.i("result", result.toString());
                      //Log.i("Device Name: ", result.getDevice().getName());
                         if (result!=null && result.getDevice()!=null && result.getDevice().getName()!=null && result.getDevice().getAddress()!=null){
                             Log.d("Dispositivo encontrado", "Nombre="+result.getDevice().getName()+" DirecciónMac="+result.getDevice().getAddress());


                                 if(result.getDevice().getName().contains("AOT") || result.getDevice().getName().contains("DIALOG") || result.getDevice().getName().contains("HEADPOD")){
                                  mlistDeviceAdapter.addDevice(result.getDevice());
                                  mlistDeviceAdapter.notifyDataSetChanged();
                                  //adaptamos el tamaño del listview
                                  setListViewHeightBasedOnChildren(listViewDevices);
                              }
                          }

                  }

              }

              @Override
              public void onBatchScanResults(List<ScanResult> results) {
                  //System.out.println("BLE// onBatchScanResults");
                  for (ScanResult sr : results) {

                      if (Build.VERSION.SDK_INT >= 21)
                          Log.i("ScanResult - Results", sr.toString());
                  }
              }

              @Override
              public void onScanFailed(int errorCode) {
                  //System.out.println("BLE// onScanFailed");
                  Log.e("Scan Failed", "Error Code: " + String.valueOf(errorCode));

                  showToastLonger("SCAN FAILED, ERROR CODE:"+ String.valueOf(errorCode),5000);

                  frameAnimationSensor.stop();

                  txtSecundario.setText(getResources().getString(R.string.error_ble_sensor));

                 /* BluetoothAdapter.getDefaultAdapter().disable();
                  Handler handler= new Handler();

                  handler.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          Log.d("habilitando", "bluetooth adapter try to enable");
                          BluetoothAdapter.getDefaultAdapter().enable();
                      }}, 500);*/

              }


          });

      }
  }

    public void checkLocationPermission(){

        permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION);

        switch(permissionCheck){
            case PackageManager.PERMISSION_GRANTED:
                break;

            case PackageManager.PERMISSION_DENIED:
                if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)){
                    //Show an explanation to user *asynchronouselly* -- don't block
                    //this thread waiting for the user's response! After user sees the explanation, try again to request the permission
                    Snackbar.make(view, getResources().getString(R.string.api_mayor_21_permisos_localizacion),
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }

                else{
                    //No explanation needed, we can request the permission
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                }
                break;
        }

    }



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
        Log.d("exitDetach","salgo del framgento");

        if(scanTask!=null)
        {
            stopScanDevicesTask();//Importantee! (Detener la tarea para evitar bugs de navegación)
        }
        //stopScanDevicesTask();//Importantee! (Detener la tarea para evitar bugs de navegación)
        scanLeDevice(false);
        if(mlistDeviceAdapter!=null)
        {
            mlistDeviceAdapter.clear();
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }



    private void showToastLonger(String mensaje, int ms) {

        //Toast.makeText(getApplicationContext(),error, Toast.LENGTH_LONG).show();
        final Toast tag = Toast.makeText(getContext(), mensaje,Toast.LENGTH_SHORT);

        tag.show();

        new CountDownTimer(ms, 1000)
        {

            public void onTick(long millisUntilFinished) {tag.show();}
            public void onFinish() {tag.show();}

        }.start();
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




    private class ListViewDevicesAdapter extends BaseAdapter {


        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;

        public ListViewDevicesAdapter(Activity activity) {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            //  mInflator = DeviceScanActivity.this.getLayoutInflater();
            mInflator = activity.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0) {


                if (deviceName.contains("AOT") ||deviceName.contains("HEADPOD") ||deviceName.contains("DIALOG") )
                {
                    if(deviceName.contains("AOT")) {
                        viewHolder.deviceName.setText(R.string.headpod_v3);
                    }
                    else if(deviceName.contains("HEADPOD"))
                    {
                        viewHolder.deviceName.setText(R.string.headpod_v2);
                    }
                    else
                    {
                        viewHolder.deviceName.setText(R.string.headpod_v1);
                    }
                }
                //else
                 //   viewHolder.deviceName.setText(deviceName);
            }
            else
                viewHolder.deviceName.setText(R.string.unknown_device);
            viewHolder.deviceAddress.setText(device.getAddress());

            return view;
        }

    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }


    public void stopScanDevice()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Build.VERSION.SDK_INT >= 21
            //bluetooth.getBluetoothLeScanner().startScan(filterList, scanSetting, scanCallback);

            BluetoothLeScanner scanner = bluetooth.getBluetoothLeScanner();

            if(scanCallback!=null && scanner !=null) {


                try
                {
                    scanner.stopScan(scanCallback);

                }
                catch (NullPointerException e)//Error en android 5.0.1, da problemas
                {
                    Log.e("erroNullPointer",e.toString());
                   // bluetooth.getBluetoothLeScanner().stopScan(scanCallback);
                }


            }

        } else {
            if(mLeScanCallback!=null )

                bluetooth.stopLeScan(mLeScanCallback);



        }


    }

    public void startScanDevice()
    {

        // Check before call the function
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Build.VERSION.SDK_INT >= 21
            //bluetooth.getBluetoothLeScanner().startScan(filterList, scanSetting, scanCallback);
            Log.d("version", "api>21");
            bluetoothScanStart();
        } else {
            Log.d("version", "api<21");
            if(mLeScanCallback!=null)
            bluetooth.startLeScan(mLeScanCallback);
        }
    }


}



