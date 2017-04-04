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

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    // DISPOSITIVO 3 <!--MAC: 80:EA:CA:00:00:03-->
    //DISPOSITIVO 2  <!--MAC: 00:00:CA:00:00:03-->
    //DISPOSITIVO 1


    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static String CHARACTERISTIC_USER_DESCRIPTION= "00002901-0000-1000-8000-00805f9b34fb";

    public static String ACCEL_ENABLE= "2d86686a-53dc-25b3-0c4a-f0e10c8dee20";
    public static String ACCEL_PERIOD = "5a87b4ef-3bfa-76a8-e642-92933c31434f";
    public static String ACCEL_VALUE = "15005991-b131-3396-014c-664c9867b917";
    public static String BAT_VALUE = "6eb675ab-8bd1-1b9a-7444-621e52ec6823";
    public static String NEAR_FIELD_VALUE = "6c290d2e-1c03-aca1-ab48-a9b908bae79e";


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
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");

        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");

        //attributes.put(BAT_VALUE, "Battery Value");
        attributes.put(BAT_VALUE, "Nivel de bateria");//"BAT_VALUE"
        attributes.put(ACCEL_PERIOD,"ACCEL_PERIOD");
        attributes.put(ACCEL_ENABLE,"ACCEL_ENABLE");
        attributes.put(ACCEL_VALUE,"ACCEL_VALUE");
        attributes.put(NEAR_FIELD_VALUE,"NEAR_FIELD_VALUE");


    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
