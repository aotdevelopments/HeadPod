package com.siestasystemheadpod.headpodv10.adicionales.ble.taskSensor;

/**
 * Created by Michael on 04/01/2017.
 * Estados de la bateria_sensor(imagenes)
 */
public class EstadosBateriaSensorResource {

    //Raiz de resource
    public final String RAIZ_DRAWABLE = "com.siestasystemheadpod.headpodv10:drawable/";

    //Estados de la bateria cuando no esta vinculada
    public final String imgNoVinculada[][]={
            { //ESPAÑOL [0][0]
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_novinculado"
            },
            {//INGLES [1][0]
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_novinculado_en",
            }
    };


    //Estados generales de la bateria.
    public final String imgVinculada[][] = {
            {//ESPAÑOL [0][X]
                    RAIZ_DRAWABLE + "bateria_sensor_sin_bateria_animacion",//<5 //animacion rojo y sin bateria
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_0",//5-15
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_1",//15-25
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_2",//25-35
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_3",//35-45
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_4",//45-65
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_5"//>65
            },
            {//INGLES [1][X]
                    RAIZ_DRAWABLE + "bateria_sensor_sin_bateria_animacion_en",
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_0_en",//5-15
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_1_en",
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_2_en",
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_3_en",
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_4_en",
                    RAIZ_DRAWABLE + "bateria_mini_con_sensor_5_en"


            }
    };
    //*********************************************************************************************
}
