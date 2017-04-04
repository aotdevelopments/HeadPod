package com.siestasystemheadpod.headpodv10.adicionales.ble;

import java.util.HashMap;

/**
 * Created by Michael on 24/11/2016.
 */
public class DeviceGattAttributes {


    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static String CHARACTERISTIC_USER_DESCRIPTION= "00002901-0000-1000-8000-00805f9b34fb";

    public static String BAT_VALUE = "6eb675ab-8bd1-1b9a-7444-621e52ec6823";
    public static String ACCEL_ENABLE= "2d86686a-53dc-25b3-0c4a-f0e10c8dee20";//0X01 (HABILITA) , 0X00 (DESHABILITA)
    public static String ACCEL_VALUE = "15005991-b131-3396-014c-664c9867b917";
    public static String ACCEL_PERIOD = "5a87b4ef-3bfa-76a8-e642-92933c31434f";//0X01 (100ms), 0X02 (200ms),0X03 (500 ms), 0X04 (1000 ms)


    //Servicios

    public static String GENERIC_ACCESS = "00001800-0000-1000-8000-00805f9b34fb";
    public static String GENERIC_ATTRIBUTE = "00001801-0000-1000-8000-00805f9b34fb";
    public static String CUSTOM_SERVICES= "edfec62e-9910-0bac-5241-d8bda6932a2f";

    //00002902-0000-1000-8000-00805f9b34fb

    //public static final UUID d = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");


    //6eb675ab-8bd1-1b9a-7444-621e52ec6823
    //   public static String BAT_VALUE = "2368ec52-1e62-4474-9a1b-d18bab75b66e";
    //[0000180a-0000-1000-8000-00805f9b34fb, edfec62e-9910-0bac-5241-d8bda6932a2f]


    static {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        attributes.put(GENERIC_ACCESS,"Generic access");
        attributes.put(GENERIC_ATTRIBUTE, "Generic attribute");
        attributes.put(CUSTOM_SERVICES,"Custom services");

        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");

        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");

        //attributes.put(BAT_VALUE, "Battery Value");
        attributes.put(BAT_VALUE, "BAT_VALUE");
        attributes.put(ACCEL_ENABLE,"ACCEL_ENABLE");
        attributes.put(ACCEL_VALUE,"ACCEL_VALUE");
        attributes.put(ACCEL_PERIOD,"ACCEL_PERIODO");

        //attributes.put("2368ec52-1e62-4474-9a1b-d18bab75B66e","Battery Value");
        //attributes.put("2368EC52-1E62-4474-9A1B-D18BAB75B66E","Battery Value");


    }


    //Devuelve el nombre del atributo en función del UUID.
    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}

