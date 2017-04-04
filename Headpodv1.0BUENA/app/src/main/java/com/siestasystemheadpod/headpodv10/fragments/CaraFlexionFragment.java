package com.siestasystemheadpod.headpodv10.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.adicionales.Util;
import com.siestasystemheadpod.headpodv10.adicionales.cara2D.Cara2D;
import com.siestasystemheadpod.headpodv10.adicionales.cara2D.CargaChangeColorCara2D;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CaraFlexionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CaraFlexionFragment#newInstance} factory method to
 * create an instance of this fragment.
 ***********************************************************
 * La cara de flexión se corresponde al eje Y del sensor.  *
 ***********************************************************
 */
public class CaraFlexionFragment extends Fragment implements Cara2D{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ImageView cara;//Esta sera la imagen de la cara para la flexión
    private TextView anguloDesfasado;//Este será el angulo para el movimiento de la cabeza

    private TextView gy;//Borrar más adelante
    private TextView gyMaxPos;//borrar más adelante
    private TextView gyMaxNeg;//borrar más adelante
    //Clase donde convertirmeos el drwable a dus corrspondietes bitmap en segundo plano
    private CargaChangeColorCara2D cargaChangeColorCara2D;




    public CaraFlexionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CaraFlexionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CaraFlexionFragment newInstance(String param1, String param2) {
        CaraFlexionFragment fragment = new CaraFlexionFragment();
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




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cara_flexion, container, false);
        cara = (ImageView) view.findViewById(R.id.cara_flexion);
        anguloDesfasado = (TextView) view.findViewById(R.id.angulo);
        gy = (TextView) view.findViewById(R.id.gy);
        gyMaxPos = (TextView) view.findViewById(R.id.gyMaxPos);
        gyMaxNeg = (TextView) view.findViewById(R.id.gyMaxNeg);
        return view;
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
    public void onResume()
    {
        Drawable caraDrawable=cara.getDrawable();
        cargaChangeColorCara2D = new CargaChangeColorCara2D(caraDrawable,getContext());
        cargaChangeColorCara2D.execute();
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }



    public void setTxtAnguloDesfasado(String valor)
    {
        anguloDesfasado.setText(valor);
    }

    public void setTxtGY(String valor)
    {
        gy.setText(valor);
    }

    public void setTxtGYMaxPos(String valor)
    {
        gyMaxPos.setText(valor);
    }

    public void setTxtGYMaxNeg(String valor)
    {
        gyMaxNeg.setText(valor);
    }

    public ImageView getImgCara()
    {
        return cara;
    }


    public boolean asynTaskFinish(CargaChangeColorCara2D cargaChangeColorCara2D) {

        if(cargaChangeColorCara2D.getStatus()== AsyncTask.Status.FINISHED){
            // My AsyncTask is done and onPostExecute was called
            return true;
        }
        else {
            return false;
        }

    }



    @Override//MÉTODO DE LA INTERFAZ CARA2D
    public void rotarCara(float angulo)
    {
        cara.setRotation(angulo);
    }

    @Override //MÉTODO DE LA INTERFAZ CARA2D
    public void pintarCaraSegunAngulo(float angulo)
    {
        if(asynTaskFinish(cargaChangeColorCara2D)) {

            if ((angulo > LIMITE_INICIAL && angulo <= LIMITE_VERDE) || (angulo > LIMITE_VERDE * -1 && angulo <= LIMITE_INICIAL)) {
                cara.setImageBitmap(cargaChangeColorCara2D.getCabezaBlancaBitmap());
            } else if ((angulo > LIMITE_VERDE && angulo <= LIMITE_AMARAILLO) || (angulo > LIMITE_AMARAILLO * -1 && angulo <= -LIMITE_VERDE)) {
                cara.setImageBitmap(cargaChangeColorCara2D.getCabezaVerdeBitmap());
            } else if ((angulo > LIMITE_AMARAILLO && angulo <= LIMITE_NARANJA) || (angulo > LIMITE_NARANJA * -1 && angulo <= -LIMITE_AMARAILLO)) {
                cara.setImageBitmap(cargaChangeColorCara2D.getCabezaAmarrillaBitmap());
            } else if ((angulo > LIMITE_NARANJA && angulo <= LIMITE_ROJO) || (angulo > LIMITE_ROJO * -1 && angulo <= -LIMITE_NARANJA)) {
                cara.setImageBitmap(cargaChangeColorCara2D.getCabezaNaranjaBitmap());
            } else {
                cara.setImageBitmap(cargaChangeColorCara2D.getCabezaRojaBitmap());
            }
        }

    }
}
