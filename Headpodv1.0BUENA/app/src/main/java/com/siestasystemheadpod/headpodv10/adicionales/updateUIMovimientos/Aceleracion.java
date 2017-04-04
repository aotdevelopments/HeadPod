package com.siestasystemheadpod.headpodv10.adicionales.updateUIMovimientos;

/**
 * Created by Michael on 27/03/2017.
 *
 *Esta clase almancera los valores de las aceleraciones y tambien hallara sus g's.
 *
 */

public class Aceleracion {

    private int accelLsbX;
    private int accekLsbY;
    private int accelLsbZ;

    final private static double factorResolucion = 0.03125;//1/32Rango de resolución en +-16G (31.25 mg/LSB = 1/32)



    public Aceleracion( int ax, int ay, int az)
    {
        accelLsbX=ax;
        accekLsbY=ay;
        accelLsbZ=az;
    }


    public void setAccelLsbX(int ax)
    {
        accelLsbX=ax;
    }


    public void setAccekLsbY(int ay)
    {
        accekLsbY=ay;
    }


    public void setAccelLsbZ(int az)
    {
        accelLsbZ=az;
    }



    public int getAccelLsbX()
    {
        return accelLsbX;
    }

    public int getAccekLsbY()
    {
        return accekLsbY;
    }


    public int getAccelLsbZ()
    {
        return accelLsbZ;
    }




    public double getmaxPosGy(double gmaxPosAnt)
    {
        if(getGy()>0)
        {
            if(getGy()>gmaxPosAnt)
            {
                return getGy();
            }
            else
            {
                return gmaxPosAnt;
            }
        }
        else
        {
            return gmaxPosAnt;
        }


    }


    public double getmaxNegGy(double gmaxNegAnt)
    {
        if(getGy()<0)
        {
            if(getGy()<gmaxNegAnt)
            {
                return getGy();
            }
            else
            {
                return gmaxNegAnt;
            }
        }else
        {
            return gmaxNegAnt;
        }
    }





    public double getmaxPosGz(double gmaxPosAnt)
    {
        if(getGz()>0)
        {
            if(getGz()>gmaxPosAnt)
            {
                return getGz();
            }
            else
            {
                return gmaxPosAnt;
            }
        }
        else
        {
            return gmaxPosAnt;
        }


    }


    public double getmaxNegGz(double gmaxNegAnt)
    {
        if(getGz()<0)
        {
            if(getGz()<gmaxNegAnt)
            {
                return getGz();
            }
            else
            {
                return gmaxNegAnt;
            }
        }else
        {
            return gmaxNegAnt;
        }
    }



    public double getmaxPosGx(double gmaxPosAnt)
    {
        if(getGx()>0)
        {
            if(getGx()>gmaxPosAnt)
            {
                return getGx();
            }
            else
            {
                return gmaxPosAnt;
            }
        }
        else
        {
            return gmaxPosAnt;
        }


    }


    public double getmaxNegGx(double gmaxNegAnt)
    {
        if(getGx()<0)
        {
            if(getGx()<gmaxNegAnt)
            {
                return getGx();
            }
            else
            {
                return gmaxNegAnt;
            }
        }else
        {
            return gmaxNegAnt;
        }
    }








    //Devuelve las g's correspondientes a la gravedad en el ejex (vector x)
    public double getGx()
    {
        return  accelLsbX*factorResolucion;
    }

    //Devuelve las g's correspondientes a la gravedad en el ejey(vector y)
    public double getGy()
    {
        return  accekLsbY*factorResolucion;
    }
    //Devuelve las g's correspondientes a la gravedad en el ejez (vector z)
    public double getGz()
    {
        return accelLsbZ*factorResolucion;
    }



    /*public double getValorAbsoluto()
    {





    }*/


}
