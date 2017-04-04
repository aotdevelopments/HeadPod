/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.siestasystemheadpod.headpodv10.adicionales.ble;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.adicionales.ble.SampleGattAttributes;

import java.util.List;
import java.util.UUID;

/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
public class BluetoothLeService extends Service {
    private final static String TAG = BluetoothLeService.class.getSimpleName();

    public boolean acel_habilitado = false;

    //Estados de los atributos
    //********************************************************************
    //Periodo del acelerometro
    private byte [] periodo1 = {0x01}; //0x01 = 100 ms
    private byte [] periodo2 = {0x02}; //0x02 = 200 ms
    private byte [] periodo3 = {0x03}; //0x03 = 500 ms
    private byte [] periodo4 = {0x04}; //0x04 = 1000 ms
    private final byte [] ACCEL_PERIOD [] = {periodo1,periodo2,periodo3,periodo4};

    private byte []deshabilitadoAccel = {0x00}; //0x00 = deshabilitado
    private byte[]habilitadoAccel = {0x01};     //0x01 = habilitado

    private final byte[] ACCEL_ENABLE [] = {deshabilitadoAccel,habilitadoAccel};


    //ahorro de energia, creo que tiene que ver con el RSSI (indicador de fuerza de señal recibida)

    private byte [] deshabilitadonearField = {0x00}; // 0x00 = desactrivar
    private byte [] habilitadonearFiel = {0x01};//0x01 = activar

    private final byte[] NEAR_FIELD_ENABLE [] = {deshabilitadonearField,habilitadonearFiel};





    //*********************************************************************
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    //ACCIONES DEL SERVIDOR
    //******************************************************************
    public final static String ACTION_GATT_CONNECTED =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.ACTION_GATT_SERVICES_DISCOVERED";

    public final static String ACTION_DATA_BATERIA =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.ACTION_DATA_BATERIA";

    public final static String ACTION_DATA_ENABLE_ACCEL =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.ACTION_ENABLE_ACCEL";

    public final static String ACTION_DATA_ACCEL =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.ACTION_DATA_ACCEL";

    public final static String ACTION_DATA_NEAR_FIELD =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.ACTION_DATA_NEAR_FIELD";

    public final static String ACTION_DATA_PERIODO_ACCEL =
            "com.siestasystemheadpod.headpodv10.adicionales.bleA.CTION_DATA_PERIODO_ACCEL";
    //***********************************************************************
    public final static String ACTION_DATA_AVAILABLE =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.EXTRA_DATA";

    //VALORES QUE SE OBTENDRAN EN LA ACTION_DATA_X
    //******************************************************************************
    public final static String EXTRA_BATERIA =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.EXTRA_BATERIA";

    public final static String EXTRA_ENABLE_ACCEL =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.EXTRA_ENABLE_ACCEL";

    public final static String EXTRA_DATA_ACELEROMETRO =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.EXTRA_DATA_ACELEROMETRO";

    public final static String EXTRA_DATA_NEAR_FIELD=
            "com.siestasystemheadpod.headpodv10.adicionales.ble.EXTRA_ENABLE_NEAR_FIELD";

    public final static String EXTRA_DATA_PERIODOD_ACCEL =
            "com.siestasystemheadpod.headpodv10.adicionales.ble.EXTRA_DATA_PERIODO_ACCEL";

    //******************************************************************************
    public final static UUID UUID_HEART_RATE_MEASUREMENT =
            UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);

    public final static UUID UUID_ACCEL_ENABLE = UUID.fromString(SampleGattAttributes.ACCEL_ENABLE);
    public final static UUID UUID_ACCEL_PERIOD = UUID.fromString(SampleGattAttributes.ACCEL_PERIOD);
    public final static UUID UUID_ACCEL_VALUE = UUID.fromString(SampleGattAttributes.ACCEL_VALUE);
    public final static UUID UUID_BAT_VALUE = UUID.fromString(SampleGattAttributes.BAT_VALUE);
    public final static  UUID UUID_NEAR_FIELD_VALUE = UUID.fromString(SampleGattAttributes.NEAR_FIELD_VALUE);


    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            Log.d("onCharactRead","meteodo onCharacteristicRead");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d("onCharactRead","ok_para_leer");

                broadcastUpdateBateria(characteristic);

                switch (getActionData(characteristic))
                {
                    case ACTION_DATA_BATERIA:
                        broadcastUpdateBateria(characteristic);
                        break;

                    case ACTION_DATA_ENABLE_ACCEL:
                        broadcastUpdateEnabledAccel(characteristic);
                        break;

                    case ACTION_DATA_ACCEL:
                        broadcastUpdateAccel(characteristic);
                        break;

                    case ACTION_DATA_PERIODO_ACCEL:
                        broadcastUpdatePeriodoAccel(characteristic);
                        break;

                    case ACTION_DATA_NEAR_FIELD:

                        broadcastUpdateNearField(characteristic);
                        break;
                }

               // broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic, int status) {
            Log.d("onWriteCharacWrite","ok_para_escribir");
            System.out.println("writestatus:" + status);
            System.out.println(BluetoothGatt.GATT_SUCCESS);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d("onWriteCharacWrite","succcessssss");
               // broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
                switch (getActionData(characteristic))
                {
                    case ACTION_DATA_BATERIA:
                        broadcastUpdateBateria(characteristic);
                        break;

                    case ACTION_DATA_ENABLE_ACCEL:
                        broadcastUpdateEnabledAccel(characteristic);
                        break;

                    case ACTION_DATA_ACCEL:
                        broadcastUpdateAccel(characteristic);
                        break;

                    case ACTION_DATA_PERIODO_ACCEL:
                        broadcastUpdatePeriodoAccel(characteristic);
                        break;

                    case ACTION_DATA_NEAR_FIELD:

                        broadcastUpdateNearField(characteristic);
                        break;
                }



            }
        }

        @Override
        // Characteristic notification
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            Log.d("onWriteCharaChanged","ok_para_cambiar");
            //broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            switch (getActionData(characteristic))
            {
                case ACTION_DATA_BATERIA:
                    broadcastUpdateBateria(characteristic);
                    break;

                case ACTION_DATA_ENABLE_ACCEL:
                    broadcastUpdateEnabledAccel(characteristic);
                    break;

                case ACTION_DATA_ACCEL:
                    broadcastUpdateAccel(characteristic);
                    break;

                case ACTION_DATA_PERIODO_ACCEL:
                    broadcastUpdatePeriodoAccel(characteristic);
                    break;

                case ACTION_DATA_NEAR_FIELD:

                    broadcastUpdateNearField(characteristic);
                    break;
            }



        }

        /*public void onCharacteristicChanged(BluetoothGatt paramBluetoothGatt, BluetoothGattCharacteristic paramBluetoothGattCharacteristic)
        {
            super.onCharacteristicChanged(paramBluetoothGatt, paramBluetoothGattCharacteristic);
            paramBluetoothGattCharacteristic.getService().getUuid();
            this.a.a(paramBluetoothGattCharacteristic, paramBluetoothGattCharacteristic.getService().getUuid(), paramBluetoothGattCharacteristic.getUuid());
        }*/



    };


    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);

        // This is special handling for the Heart Rate Measurement profile.  Data parsing is
        // carried out as per profile specifications:
        // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
        if(UUID_BAT_VALUE.equals(characteristic.getUuid()))
        {
            sendBroadcast(getValueBatService(characteristic,intent));
        }

        if(UUID_ACCEL_ENABLE.equals((characteristic.getUuid())))
        {
            sendBroadcast(getValueAccelEnabledService(characteristic,intent));
        }

        if(UUID_ACCEL_VALUE.equals((characteristic.getUuid())))
        {
            sendBroadcast(getValueAccelService(characteristic,intent));
        }

        if(UUID_NEAR_FIELD_VALUE.equals((characteristic.getUuid())))
        {
            sendBroadcast(getValueNearFieldService(characteristic,intent));
        }

        /*if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d(TAG, "Heart rate format UINT8.");
            }
            final int heartRate = characteristic.getIntValue(format, 1);
            Log.d(TAG, String.format("Received heart rate: %d", heartRate));
            intent.putExtra(EXTRA_DATA, String.valueOf(heartRate));
        } else {
            // For all other profiles, writes the data formatted in HEX.
            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(data.length);
                for(byte byteChar : data)
                    stringBuilder.append(String.format("%02X ", byteChar));
                intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
            }
            sendBroadcast(intent);
        }*/


    }



    private void broadcastUpdateBateria(final BluetoothGattCharacteristic characteristic)
    {
        final Intent intent = new Intent(ACTION_DATA_BATERIA);
        sendBroadcast(getValueBatService(characteristic,intent));
    }

    private void broadcastUpdateEnabledAccel(final BluetoothGattCharacteristic characteristic)
    {
        final Intent intent = new Intent(ACTION_DATA_ENABLE_ACCEL);
        sendBroadcast(getValueAccelEnabledService(characteristic,intent));
    }

    private void broadcastUpdateAccel(final BluetoothGattCharacteristic characteristic)
    {
        final Intent intent = new Intent(ACTION_DATA_ACCEL);
        sendBroadcast(getValueAccelService(characteristic,intent));
    }

    private void broadcastUpdatePeriodoAccel(final BluetoothGattCharacteristic characteristic)
    {
        final Intent intent = new Intent(ACTION_DATA_PERIODO_ACCEL);
        sendBroadcast(getValuePeriodoAccelService(characteristic,intent));

    }


    private void broadcastUpdateNearField(final BluetoothGattCharacteristic characteristic)
    {
        final Intent intent = new Intent(ACTION_DATA_NEAR_FIELD);
        sendBroadcast(getValueNearFieldService(characteristic,intent));
    }



    //Método para obtener la accion (ACTION_DATA_X) ha realizar mediante su UUID
    public String getActionData(final BluetoothGattCharacteristic characteristic)
    {

        if(UUID_BAT_VALUE.equals(characteristic.getUuid()))
        {
            return ACTION_DATA_BATERIA;
        }

        else if(UUID_ACCEL_ENABLE.equals((characteristic.getUuid())))
        {
            return ACTION_DATA_ENABLE_ACCEL;
        }

        else if(UUID_ACCEL_VALUE.equals((characteristic.getUuid())))
        {
            return  ACTION_DATA_ACCEL;
        }

        else if(UUID_NEAR_FIELD_VALUE.equals((characteristic.getUuid())))
        {
            return ACTION_DATA_NEAR_FIELD;
        }
        else {
            return ACTION_DATA_PERIODO_ACCEL;
        }

    }



    //Método usado en broadcastUpdate() para obtener el valor de la bateria
    public Intent getValueBatService(BluetoothGattCharacteristic characteristic, Intent intent)
    {

        int flag = characteristic.getProperties();

        Log.d("flagBat: ",String.valueOf(flag));
        int format = -1;
        if ((flag & 0x01) != 0) {
            format = BluetoothGattCharacteristic.FORMAT_UINT16;
            Log.d(TAG, "SERVICE_BATERIA  format UINT16.");
        } else {
            format = BluetoothGattCharacteristic.FORMAT_UINT8;
            Log.d(TAG, "SERVICE_BATERIA format UINT8.");
        }
        //final int nivelBat = characteristic.getIntValue(format, 1);
        //Log.d(TAG, String.format("Received bat value: %d", nivelBat));
        //intent.putExtra(EXTRA_DATA, String.valueOf(nivelBat));


        final byte[] data = characteristic.getValue();
        if (data != null && data.length > 0) {
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for(byte byteChar : data)
                stringBuilder.append(String.format("%02X ", byteChar));
            //intent.putExtra(EXTRA_DATA, new String(data) + "\n" +
            //      stringBuilder.toString());

            intent.putExtra(EXTRA_BATERIA,stringBuilder.toString());

            //byte valor [] = data;

            // String nivel_bat = new String(data);

            Log.d("SERVICE_BATERIA", stringBuilder.toString());
        }

        return intent;

    }


    //Método usado en broadcastUpdate() para obtener el valor de habilitado o no del acelerómetro
    public Intent getValueAccelEnabledService(BluetoothGattCharacteristic characteristic, Intent intent)
    {


        int flag = characteristic.getProperties();

        Log.d("SERV_FLAG_ENABLE_ACCEL:",String.valueOf(flag));
        int format = -1;
        if ((flag & 0x01) != 0) {
            format = BluetoothGattCharacteristic.FORMAT_UINT16;
            Log.d(TAG, "SERVICE_ENABLED_ACCEL format UINT16.");
        } else {
            format = BluetoothGattCharacteristic.FORMAT_UINT8;
            Log.d(TAG, "SERVICE_ENABLED_ACCEL format UINT8.");//Norlamente se mete aquí
        }

        final byte[] data = characteristic.getValue();

        //String nivel_bat = new String(data);
        // Log.d("acel_habil_1",nivel_bat);

        if (data != null && data.length > 0) {

            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for(byte byteChar : data)
                stringBuilder.append(String.format("%02X ", byteChar));
            //intent.putExtra(EXTRA_DATA, new String(data) + "\n" +
            //      stringBuilder.toString());

            Log.d("SERVICE_ENABLED_ACCEL", stringBuilder.toString());


            intent.putExtra(EXTRA_ENABLE_ACCEL,stringBuilder.toString());

            //byte valor [] = data;

            // String nivel_bat = new String(data);


        }
        else
        {
            Log.d("SERVICE_ENABLED_ACCEL", "valor_nulo");
        }

        return intent;

    }


    //Método usado en broadcastUpdate() para obtener el valor del acelerómetro
    public Intent getValueAccelService(BluetoothGattCharacteristic characteristic, Intent intent)
    {
        int flag = characteristic.getProperties();
        Log.d("SERVICE_flagAcelValue: ",String.valueOf(flag));//18
        int format = -1;
        if ((flag & 0x01) != 0) {
            format = BluetoothGattCharacteristic.FORMAT_UINT16;
            Log.d(TAG, "SERVICE_ACCELvalue format UINT16.");
        } else {
            format = BluetoothGattCharacteristic.FORMAT_UINT8;
            Log.d(TAG, "SERVICE_ACCELvalue format UINT8.");//UINT8, 1 BYTE POR ELEMENTO.
        }

        final byte[] data = characteristic.getValue();

        if (data != null && data.length > 0) {

            //byte valor [] = data;
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for(byte byteChar : data)
                stringBuilder.append(String.format("%02X ",byteChar));

            Log.d("SERVICE_ACCEL",stringBuilder.toString());




           // intent.putExtra(EXTRA_DATA_ACELEROMETRO, stringBuilder.toString());
            //Esto es util, no borrar
            //*******************************************************************************
            //*******************************************************************************
               // Log.d("data_0",Byte.toString(data[0]));
               // Log.d("data_1",Byte.toString(data[1]));
               // Log.d("data_2",Byte.toString(data[2]));
               // Log.d("data_3",Byte.toString(data[3]));
               // Log.d("data_4",Byte.toString(data[4]));
               // Log.d("data_5",Byte.toString(data[5]));
            //    It's not a comparison with 0xc0, it's a logical AND operation with 0xc0.
            // The bit mask 0xc0 is 11 00 00 00 so what the AND is doing is extracting only the top two bits:
            //            ab cd ef gh
            //        AND 11 00 00 00
            //            -- -- -- --
            //          = ab 00 00 00
                float xAccl = (((data[1] & 0xFF) * 256) + (data[0] & 0xC0)) / 64;
                if(xAccl > 511)
                {
                    xAccl -= 1024;
                }

                float yAccl = (((data[3] & 0xFF) * 256) + (data[2] & 0xC0)) / 64;
                if(yAccl > 511)
                {
                    yAccl -= 1024;
                }
                float zAccl = (((data[5] & 0xFF) * 256) + (data[4] & 0xC0)) / 64;
                if(zAccl > 511)
                {
                    zAccl -= 1024;
                }

                float data_accel []= {xAccl,yAccl,zAccl};

                Log.d("MAT_acel_x",String.valueOf((float) xAccl));
                Log.d("MAT_acel_y",String.valueOf((float) yAccl));
                Log.d("mATacel_z",String.valueOf((float) zAccl));

            //*******************************************************************************
            //*******************************************************************************
            intent.putExtra(EXTRA_DATA_ACELEROMETRO, data_accel);



            //borrar despues
              /*String str = "C0FF80FF8007";
                byte[] bytes = new byte[str.length()/2];
                for (int i = 0; i < str.length(); ++i) {
                    if ((i+1)*2 <= str.length()) {
                        bytes[i] = (byte) Integer.parseInt(str.substring(i*2, (i+1)*2), 16);
                    }
                }
                for (byte b: bytes) {
                    System.out.format("%02x\n", b);
                }*/


            //stringBuilder.append(String.format("%02X ", byteChar));

            //String.format("%02X",

            //new Object[] { Byte.valueOf(localObject3[i2])


            // intent.putExtra(EXTRA_DATA, new String(data) + "\n" +
            //      stringBuilder.toString());

            // Log.d("acel_valor_xyz", stringBuilder.toString());

            // Log.d("acel_val_xyz_int",new String(data) + "\n" +
            //         stringBuilder.toString());
        }
        else
        {
            Log.d("SERVICE_ACCEL", "no recibo valor");
        }

        return intent;
    }


    //Método usado en broadcastUpdate() para obtener el valor del perido de adquisición del acelerómetro
    public Intent getValuePeriodoAccelService(BluetoothGattCharacteristic characteristic, Intent intent)
    {
        int flag = characteristic.getProperties();
        Log.d("SERVflagPeriodoAcel:",String.valueOf(flag));

        int format = -1;
        if ((flag & 0x01) != 0) {
            format = BluetoothGattCharacteristic.FORMAT_UINT16;
            Log.d(TAG, "SERVICE_PERIODO_ACCEL VALUE format UINT16.");
        } else {
            format = BluetoothGattCharacteristic.FORMAT_UINT8;
            Log.d(TAG, "SERVICE_PERIODO_ACCELvalue format UINT8.");
        }

        final byte[] data = characteristic.getValue();

        if (data != null && data.length > 0) {

            //byte valor [] = data;
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for(byte byteChar : data)
                stringBuilder.append(String.format("%02X ",byteChar));

            Log.d("SERVICE_PERIODO_ACCEL",stringBuilder.toString());
            intent.putExtra(EXTRA_DATA_PERIODOD_ACCEL, stringBuilder.toString());

        }
        else
        {
            Log.d("SERVICE_PERIODO_ACCEL", "no recibo valor");
        }

        return intent;
    }

    //Método usado en broadcastUpdate() para obtener el valor de NearField
    public Intent getValueNearFieldService(BluetoothGattCharacteristic characteristic, Intent intent){


        int flag = characteristic.getProperties();

        Log.d("flagNEAR_FIELD_VALUE: ",String.valueOf(flag));
        int format = -1;
        if ((flag & 0x01) != 0) {
            format = BluetoothGattCharacteristic.FORMAT_UINT16;
            Log.d(TAG, "SERVICE_NEAR_FIELD value format UINT16.");
        } else {
            format = BluetoothGattCharacteristic.FORMAT_UINT8;
            Log.d(TAG, "SERVICE_NEAR_FIELD value format UINT8.");


        }

        final byte[] data = characteristic.getValue();

        if (data != null && data.length > 0) {

            //byte valor [] = data;
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for(byte byteChar : data)
                stringBuilder.append(String.format("%02X ",byteChar));

            Log.d("SERVICE_NEAR_FIELD",stringBuilder.toString());

            intent.putExtra(EXTRA_DATA_NEAR_FIELD, stringBuilder.toString());

        }
        else
        {
            Log.d("SERVICE_NEAR_FIELD", "no recibo valor");
        }

        return intent;
    }




    public class LocalBinder extends Binder {
        BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     *
     * @return Return true if the connection is initiated successfully. The connection result
     *         is reported asynchronously through the
     *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     *         callback.
     */
    public boolean connect(final String address) {

        Log.d("connection","preparando la coneccion");

        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection xD.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        Log.d("readCharacteristic","entro en metodo readCharacteristic del servidor");
       mBluetoothGatt.readCharacteristic(characteristic);
            //Log.d("onCharactRead","ok_para_leer");
            //broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }


        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        if (UUID_ACCEL_VALUE.equals(characteristic.getUuid())) {
                // This is specific to Heart Rate Measurement.
                BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                        UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mBluetoothGatt.writeDescriptor(descriptor);
        }
    }

    public void setCharacteristicNotificationDes(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }


        //mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);


        if (UUID_ACCEL_VALUE.equals(characteristic.getUuid())) {
            // This is specific to Heart Rate Measurement.
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }



    }


    public void setCharacteristicNotificationAccel(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }


        if (UUID_ACCEL_VALUE.equals(characteristic.getUuid())) {



        if(enabled) {
            // This is specific to Heart Rate Measurement.
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
        else
        {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
            // characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CHARACTERISTIC_USER_DESCRIPTION)).notify();
        }

    }


    public void notificaciones_accel(BluetoothGattCharacteristic characteristic)
    {
            if (UUID_ACCEL_VALUE.equals(characteristic.getUuid())) {

                // This is specific to Heart Rate Measurement.
                BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                        UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                mBluetoothGatt.writeDescriptor(descriptor);

                //Log.d("vALOR_ACEL","ok");

                // characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CHARACTERISTIC_USER_DESCRIPTION)).notify();

                // return;
            }
    }


    //Método usado en DeviceControlFragment para activar o desactivar el acelerométro.
    public void writeCharacteristicAccel(BluetoothGattCharacteristic characteristic,int estado) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        if (UUID_ACCEL_ENABLE.equals(characteristic.getUuid())) {

            Log.d("activando_accel_a",String.valueOf(ACCEL_ENABLE[estado][0]));
            characteristic.setValue(ACCEL_ENABLE[estado]);
            mBluetoothGatt.writeCharacteristic(characteristic);
            acel_habilitado=true;

        }

    }


    public void writeCharacteristicNearField(BluetoothGattCharacteristic characteristic,int estado) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        if (UUID_NEAR_FIELD_VALUE.equals(characteristic.getUuid())) {

            Log.d("activando_nearField_a",String.valueOf(NEAR_FIELD_ENABLE[estado][0]));
            characteristic.setValue(NEAR_FIELD_ENABLE[estado]);
            mBluetoothGatt.writeCharacteristic(characteristic);

        }

    }



    public void writeCharacteristiPeriodAccel(BluetoothGattCharacteristic characteristic,int periodo) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        if (UUID_ACCEL_PERIOD.equals(characteristic.getUuid())) {

            Log.d("activando_periodo_a",String.valueOf(ACCEL_PERIOD[periodo][0]));
            characteristic.setValue(ACCEL_PERIOD[periodo]);
            mBluetoothGatt.writeCharacteristic(characteristic);
        }

    }







    public void setCharacteristicIndication(BluetoothGattCharacteristic characteristic,
                                            boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }

        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
        mBluetoothGatt.writeDescriptor(descriptor);

        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
    }



    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }



}




/*
public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                          boolean enabled) {
    if (mBluetoothAdapter == null || mBluetoothGatt == null) {
        Log.w(TAG, "BluetoothAdapter not initialized");
        return;
    }

    mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

    // This is specific to Heart Rate Measurement.
       // if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            //BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
              //      UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            //descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
          //  mBluetoothGatt.writeDescriptor(descriptor);
        //}




        //if (UUID_ACCEL_ENABLE.equals(characteristic.getUuid())) {

          //  Log.d("Acel_ENABLE","HABILITANDO ACELEROMETRO");

            //return;

            // Code to convert byte arr to str:
            //byte[] by_original = {0,1,-2,3,-4,-5,6};
            //String str1 = new String(by_original);
            //System.out.println("str1 >> "+str1);

            // Code to convert str to byte arr:
            //String str1 = "1";
            //byte[] by_new = str1.getBytes();
            //for(int i=0;i<by_new.length;i++)
            //  System.out.println("by1["+i+"] >> "+str1);
//        }



        //if(UUID_ACCEL_VALUE.equals(characteristic.getUuid()))
        //{

            // This is specific to Heart Rate Measurement.

          //  BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
            //        UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            //descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            //mBluetoothGatt.writeDescriptor(descriptor);

            //Log.d("vALOR_ACEL","ok");

            // characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CHARACTERISTIC_USER_DESCRIPTION)).notify();

           // return;
        //}

     //


    // mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

//}



 */





   /* public void writeCharacteristic(BluetoothGattCharacteristic characteristic, byte[] value) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        characteristic.setValue(value);
        mBluetoothGatt.writeCharacteristic(characteristic);
    }*/


   /* public void writeCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        if (UUID_ACCEL_ENABLE.equals(characteristic.getUuid())) {

            StringBuilder data = new StringBuilder();


            //data.append("01");
            //byte[] dataByte = data.toString().getBytes();



            byte[] dataByte = {0x01};

            Log.d("activando_a",String.valueOf(dataByte[0]));
            characteristic.setValue(dataByte);
            mBluetoothGatt.writeCharacteristic(characteristic);
        }

    }*/
