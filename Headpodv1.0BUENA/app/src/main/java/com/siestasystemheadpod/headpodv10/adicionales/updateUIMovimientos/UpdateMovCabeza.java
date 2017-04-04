package com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos;

import com.siestasystemheadpod.headpodv10.fragments.Cara3DFragment;
import com.siestasystemheadpod.headpodv10.fragments.CaraFlexionFragment;
import com.siestasystemheadpod.headpodv10.fragments.CaraInclinacionFragment;
import com.siestasystemheadpod.headpodv10.fragments.CaraRotacionFragment;
import com.siestasystemheadpod.headpodv10.interfaces.IUpdateMovimiento2D;
import com.siestasystemheadpod.headpodv10.interfaces.IUpdateMovimiento3D;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Michael on 29/03/2017.
 * Esta clase se encargara del movimiento de la cabeza en 3D y en 2D
 * para la cabeza en 3D
 */

public class UpdateMovCabeza implements IUpdateMovimiento3D,IUpdateMovimiento2D {

    private Cara3DFragment cara3DFragment;
    private CaraFlexionFragment caraFlexionFragment;
    private CaraInclinacionFragment caraInclinacionFragment;
    private CaraRotacionFragment caraRotacionFragment;
    private Aceleracion aceleracion;//Valor de cada eje de la aceleración



    private int anguloFlexion;
    private int anguloInclinacion;


    private double gmaxYNeg;
    private double gmaxYPos;
    private double gmaxZNeg;
    private double gmaxZPos;
    private double gmaxXNeg;
    private double gmaxXPos;




    

    public UpdateMovCabeza(Aceleracion aceleracion,Cara3DFragment c3D, CaraFlexionFragment c2D_F, CaraInclinacionFragment c2D_I,
                           CaraRotacionFragment c2D_R,DesfaseAnguloYZ desfaseAnguloYZ,
                           double gmaxYNeg,double gmaxYPos, double gmaxZNeg, double gmaxZPos, double gmaxXPos, double gmaxXNeg)
    {
        this.aceleracion=aceleracion;
        cara3DFragment=c3D;
        caraFlexionFragment=c2D_F;
        caraInclinacionFragment=c2D_I;
        caraRotacionFragment=c2D_R;
        this.gmaxYNeg=gmaxYNeg;
        this.gmaxYPos=gmaxYPos;
        this.gmaxZNeg=gmaxZNeg;
        this.gmaxZPos=gmaxZPos;
        this.gmaxXPos= gmaxXPos;
        this.gmaxXNeg = gmaxXNeg;

        anguloFlexion=new AnguloY().getAnguloY(aceleracion.getAccekLsbY())+desfaseAnguloYZ.getDesfaseY();
        anguloInclinacion = new AnguloZ().getAnguloZ(aceleracion.getAccelLsbZ())+desfaseAnguloYZ.getDesfaseZ();


        updateFlexion3D();
        updateFlexion2D();
        updateInclinacion3D();
        updateInclinacion2D();
        updateRotacion2D();

        //float angF= cara_fragment_3D.getobjModel().rotation().x;
        //float angI = cara_fragment_3D.getobjModel().rotation().z;
    }







    @Override
    public void updateFlexion3D() {

        if(cara3DFragment!=null)
        {
            cara3DFragment.flexionar(anguloFlexion);
        }

    }


    @Override
    public void updateInclinacion3D() {

        if(cara3DFragment!=null)
        {
            cara3DFragment.inclinar(anguloInclinacion,aceleracion.getAccelLsbZ(),aceleracion.getAccelLsbX());
        }
    }

    @Override
    public void updateFlexion2D(){

        if(caraFlexionFragment!=null)
        {

            caraFlexionFragment.rotarCara(anguloFlexion);
            caraFlexionFragment.pintarCaraSegunAngulo(anguloFlexion);

            NumberFormat formatter4 = new DecimalFormat("#.###");

            caraFlexionFragment.setTxtAnguloDesfasado(String.valueOf(anguloFlexion));//"Θ="
            caraFlexionFragment.setTxtGY(String.valueOf(formatter4.format(aceleracion.getGy())));//α=
            caraFlexionFragment.setTxtGYMaxPos(String.valueOf(formatter4.format(gmaxYPos)));
            caraFlexionFragment.setTxtGYMaxNeg(String.valueOf(formatter4.format(gmaxYNeg)));
        }


    }


    @Override
    public void updateInclinacion2D() {

        if(caraInclinacionFragment!=null)
        {


            caraInclinacionFragment.rotarCara(anguloInclinacion);
            caraInclinacionFragment.pintarCaraSegunAngulo(anguloInclinacion);

            NumberFormat formatter4 = new DecimalFormat("#.###");

            caraInclinacionFragment.setTxtAnguloDesfasado(String.valueOf(anguloInclinacion));

            caraInclinacionFragment.setTxtGZ(String.valueOf(formatter4.format(aceleracion.getGz())));
            caraInclinacionFragment.setTxtGZMaxPos(String.valueOf(formatter4.format(gmaxZPos)));
            caraInclinacionFragment.setTxtGZMaxNeg(String.valueOf(formatter4.format(gmaxZNeg)));


            caraInclinacionFragment.setTxtGX(String.valueOf(formatter4.format(aceleracion.getGx())));
            caraInclinacionFragment.setTxtGXMaxPos(String.valueOf(formatter4.format(gmaxXPos)));
            caraInclinacionFragment.setTxtGXMaxNeg(String.valueOf(formatter4.format(gmaxXNeg)));


        }

    }

    @Override
    public void updateRotacion2D()
    {
        if(caraRotacionFragment!=null)
        {
            NumberFormat formatter2 = new DecimalFormat("#.#");
            NumberFormat formatter4 = new DecimalFormat("#.###");

            //caraRotacionFragment.setTxtA_x(String.valueOf(formatter2.format(smma_gx)));
           /* caraRotacionFragment.setTxtA_x(String.valueOf(formatter2.format(aceleracion.getAccelLsbX())));
            caraRotacionFragment.setTxtG_x(String.valueOf(formatter4.format(aceleracion.getGx())));

            //float anguloTilt = (float) Math.atan(val  or_gz / Math.sqrt(Math.pow(smma_gx, 2) + Math.pow(valor_gy, 2)+Math.pow(valor_gz, 2) )) * (float) (180.0 / 3.14);
            float anguloTilt = (float) Math.atan(aceleracion.getAccelLsbZ() / Math.sqrt(Math.pow(aceleracion.getAccelLsbX(), 2) + Math.pow(aceleracion.getAccekLsbY(), 2)+Math.pow(aceleracion.getAccelLsbZ(), 2) )) * (float) (180.0 / 3.14);

            final float ang_x = (float) Math.atan(aceleracion.getAccelLsbX() / Math.sqrt(Math.pow(aceleracion.getAccekLsbY(), 2) + Math.pow(aceleracion.getAccelLsbZ(), 2))) * (float) (180.0 / 3.14);



            caraRotacionFragment.setTxtAnguloCabeza(String.valueOf(formatter2.format(ang_x)));
            caraRotacionFragment.setTxtAnguloSensorZ(String.valueOf(formatter2.format(anguloTilt)));*/


            caraRotacionFragment.setTxtAnguloSensorZ(String.valueOf(formatter4.format(aceleracion.getGx())));
            caraRotacionFragment.setTxtG_x(String.valueOf(formatter4.format(aceleracion.getGy())));
            caraRotacionFragment.setTxtA_x(String.valueOf(formatter4.format(aceleracion.getGz())));




        }


    }


    public int getAnguloFlexion()
    {
        return anguloFlexion;
    }

    public int getAnguloInclinacion()
    {
        return anguloInclinacion;
    }

}







