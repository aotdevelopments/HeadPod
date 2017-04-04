package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siestasystemheadpod.headpodv10.HomeActivity;

import java.util.Random;

import min3d.Shared;
import min3d.core.Object3dContainer;
import min3d.core.Renderer;
import min3d.core.RendererFragment;
import min3d.core.Scene;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;
import min3d.vos.Number3d;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Cara3DFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Cara3DFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cara3DFragment extends RendererFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Object3dContainer objModel;

    public Number3d tres_ejes;
    //x-flexion
    //z-inclinacion
    //y-rotacion

    int cont;
    float gradosx;
    float gradosy;
    float gradosz;
    int sentidox = 1;//1derecha//-1 izquierda
    int sentidoy = 1;
    int sentidoz = 1;
    private boolean detener = true;






    private static final long SPLASH_SCREEN_DELAY = 2000;//tiempo en ms

    public Cara3DFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cara3DFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Cara3DFragment newInstance(String param1, String param2) {
        Cara3DFragment fragment = new Cara3DFragment();
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

        //NECESARIO PARA CREAR LA CARA 3D
        //************************************************************************************
        _initSceneHander = new Handler();
        _updateSceneHander = new Handler();






        //Esto es necesario para poder cargar la escena 3D
        //*****************************************************************************************
        //******************************************************************************************
        // These 4 lines are important.
        //

        Shared.context(((HomeActivity) getActivity()).getBaseContext());
        scene = new Scene(this);


        Renderer r = new Renderer(scene);
        Shared.renderer(r);



        _glSurfaceView = new GLSurfaceView(((HomeActivity) getActivity()).getBaseContext());

        glSurfaceViewConfig();
        _glSurfaceView.setRenderer(r);
        _glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        // _glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);//deja quieta la cara

    }


   // public void stop()
    //{
    //    super.stopRunnable();
    //}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_cara3_d, container, false);

        //Esto es necesario para poder cargar la escena 3D
        //*****************************************************************************************
        //******************************************************************************************
        // These 4 lines are important.
        //




        return glSurfaceView();
        //*****************************************************************************************
        //******************************************************************************************
    }





/*
    @Override
    public void onResume()
    {
        super.onResume();
        _glSurfaceView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        _glSurfaceView.onPause();
    }

*/

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
    public void initScene() {

		/*
		scene.lights().add(new Light());

		IParser parser = Parser.createParser(Parser.Type.OBJ,
				getResources(), "min3d.sampleProject1:raw/face_obj", true);
		parser.parse();

		objModel = parser.getParsedObject();
		objModel.scale().x = objModel.scale().y = objModel.scale().z = .7f;
		scene.addChild(objModel);
		*/


        //Para añadir mayor Luz al objeto
        //***************************************
        scene.lights().add(new Light());
        scene.lights().add(new Light());
        scene.lights().add(new Light());
        scene.lights().add(new Light());
        scene.lights().add(new Light());
        scene.lights().add(new Light());
        scene.lights().add(new Light());
        //******************************************

        Light myLight = new Light();
        myLight.position.setZ(150);
        scene.lights().add(myLight);

        scene.backgroundColor().setAll(0xFFFFFF);

// Create the scene root for the scenes in this app
        //RelativeLayout mSceneRoot = (RelativeLayout) findViewById(R.id.escena3d);
        // Scene mAScene = Scene.getSceneForLayout(mSceneRoot, R.layout.fragment_avatar3_d, this);


        IParser myParser = Parser.createParser(Parser.Type.OBJ, getResources(), "com.siestasystemheadpod.headpodv10:raw/face_obj", true);
        myParser.parse();

        objModel = myParser.getParsedObject();
        //objModel.position().x = objModel.position().y = objModel.position().z = 0;

        objModel.position().x = -0.08f; //- izquierda, + derecha

        objModel.position().y = 0.20f;//- abajo, + arriba 0.40f

        objModel.position().z = 0;
        //objModel.scale().x = objModel.scale().y = objModel.scale().z = 0.009f;
// Depending on the model you will need to change the scale
        objModel.scale().x = objModel.scale().y = objModel.scale().z = 0.0119f;

        scene.addChild(objModel);

        cont = 0;
        gradosx = 0;
        gradosy = 0;
        gradosz = 0;

        //tres_ejes = new Number3d();


        // objModel.rotation().y+=45;
        // objModel.rotation().x-=25;
        //objModel

          tres_ejes = new Number3d();
    }



    @Override
    public void updateScene() {


        //aleatorio = Math.floor(Math.random()*(listado.length));
        //seleccion = listado[aleatorio]
        //trace(seleccion)


        Random x = new Random();


        Random rnd_x = new Random();
        Random rnd_y = new Random();
        Random rnd_z = new Random();

/*
        int [] lista  =  {-45,-44,-43,-42,-41,-40,-39,-38,-37,-36,-35,-34,-33,-32,-31,-30,-29,-28,-27,-26,-25,-24,-23,-22,-21,-20,-19,-18,-17,-16,-15,
                -14,-13,-12,-11,-10,-9,-8,-7,-6,-5,-4,-3,-2,1,0,0,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,
                31,32,33,34,35,36,37,38,39,40,41,42,43,44,45};
*/
        int[] lista = {-9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        int angulo_lista_x = (int) (rnd_x.nextFloat() * lista.length);
        int angulo_lista_y = (int) (rnd_y.nextFloat() * lista.length);
        int angulo_lista_z = (int) (rnd_z.nextFloat() * lista.length);


        // float angulo_x =(float) (rnd_x.nextFloat()*(50-1))+1;

        //(max-min) + min;
        //float angulo_y =(float) (rnd_y.nextFloat()*(50-1))+1;

        //float angulo_z = (float) (rnd_z.nextFloat()*(50-1))+1;



        //tres_ejes.x=tres_ejes.x+lista[angulo_lista_x];
        //tres_ejes.y=tres_ejes.y+lista[angulo_lista_y];
        //tres_ejes.z=tres_ejes.z+lista[angulo_lista_z];


       if (!detener) {

           /*

           tres_ejes = new Number3d();
           tres_ejes.x = tres_ejes.x + gradosx;//y=flexion frontal
           tres_ejes.z = tres_ejes.z + gradosz;//z=inclinacion lateral
           tres_ejes.y = tres_ejes.y + gradosy;//y=rotacion


          // tres_ejes.z = 0;
          // tres_ejes.y = 0;


           objModel.rotation().x = gradosx;
           objModel.rotation().z = gradosz;
           objModel.rotation().y = gradosy;

          // //objModel.rotation().y = tres_ejes.y;
           ////objModel.rotation().x=0;
          // objModel.rotation().y = 0;
          // objModel.rotation().z = 0;


           //flexion
           if (sentidox == 1) {
               gradosx++;
           } else {
               gradosx--;
           }

           if (gradosx > 90) {
               sentidox = -1;
           }
           if (gradosx < -90) {
               sentidox = 1;
           }

           //inclinacion
           if (sentidoz == 1) {
               gradosz++;
           } else {
               gradosz--;
           }

           if (gradosz > 45) {
               sentidoz = -1;
           }
           if (gradosz < -45) {
               sentidoz = 1;
           }

           //rotacion
           if (sentidoy == 1) {
               gradosy++;
           } else {
               gradosy--;
           }

           if (gradosy > 90) {
               sentidoy = -1;
           }
           if (gradosy < -90) {
               sentidoy = 1;
           }

            */
       }


/*
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                objModel.rotation().x = 0;
                objModel.rotation().z = 0;
                objModel.rotation().y=tres_ejes.y;

            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
*/


/*
        objModel.rotation().x++;
        objModel.rotation().z++;
        objModel.rotation().y++;

*/

    }


    public void rotar_y(float angulo) {

        if (!detener) {
            objModel.rotation().y = angulo;
            // objModel.rotation().z++;

            //objModel.rotation().y++;
        }
    }



    //x-flexion
    //z-inclinacion
    //y-rotacion


    public void flexionar(float anguloy)
    {

        if (!detener) {

            objModel.rotation().x = anguloy;
        }

    }

    public void inclinar(float anguloz, int accelz, int accelx)
    {
        if (!detener) {
            if(accelz!=0 && accelx!=0) {
                if (anguloz >= 0) {

                   // angulo1 = 90 - angulo1;

                }
                if (anguloz < 0) {
                    anguloz = anguloz * -1;


                    //angulo1 = 90 - angulo1;


                   // angulo1 = angulo1 * -1;
                }


                if (accelz >= 0 && accelx >= 0) {
                    objModel.rotation().z = anguloz;//objModel.rotation().x += angulo;
                }
                else if(accelz<=0 && accelx <=0)
                {
                    objModel.rotation().z = anguloz;//objModel.rotation().x += angulo;
                }

                else {
                    objModel.rotation().z = anguloz * -1;//objModel.rotation().x += angulo;
                }
            }


        }


    }





       public void rotar_x(float angulo1, int z,int x) {
        if (!detener) {


            if(z!=0 && x!=0) {
                if (angulo1 >= 0) {

                    angulo1 = 90 - angulo1;

                }
                if (angulo1 < -0) {
                    angulo1 = angulo1 * -1;


                    angulo1 = 90 - angulo1;


                    angulo1 = angulo1 * -1;
                }


                if (z >= 0 && x >= 0) {
                    objModel.rotation().x = angulo1;//objModel.rotation().x += angulo;
                }
                else if(z<=0 && x <=0)
                {
                    objModel.rotation().x = angulo1;//objModel.rotation().x += angulo;
                }

                else {
                    objModel.rotation().x = angulo1 * -1;//objModel.rotation().x += angulo;
                }
            }


        }

    }

    public void rotar_z(float angulo) {
        if (!detener) {

            objModel.rotation().z = angulo;
        }
    }






    public  Object3dContainer getobjModel()
    {
        return objModel;
    }

    public void setDetener(Boolean t_f)
    {
        detener=t_f;
    }

    public boolean getDetener()
    {
        return detener;
    }

    public float getGradosx(){

        return gradosx;
    }

    public float getGradosy()
    {

        return gradosy;
    }

    public float getGradosz()
    {
        return gradosz;
    }

    public void setGradosx(float gx)
    {
        gradosx=gx;
    }

    public void setGradosy(float gy)
    {
        gradosy=gy;
    }

    public void setGradosz(float gz)
    {
        gradosz=gz;
    }


    public Object3dContainer getFace()
    {
        return objModel;
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
