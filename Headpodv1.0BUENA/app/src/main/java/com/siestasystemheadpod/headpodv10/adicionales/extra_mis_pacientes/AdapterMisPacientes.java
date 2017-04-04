package com.siestasystemheadpod.headpodv10.adicionales.extra_mis_pacientes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siestasystemheadpod.headpodv10.HomeActivity;
import com.siestasystemheadpod.headpodv10.R;
import com.siestasystemheadpod.headpodv10.data.bd.CrudDbHeadpod;
import com.siestasystemheadpod.headpodv10.data.modelo.Paciente;

import java.util.ArrayList;

/**
 * Created by Michael on 11/01/2017.
 *
 * Adapter para el uso del listview en el fragmentos de mis pacientes
 */

public class AdapterMisPacientes extends BaseAdapter {

    protected FragmentActivity fragmentActivity;
    private ArrayList<ItemMisPacientes> items;
    private ArrayList<ItemMisPacientes> itemsAux;//Se usa un auxiliar para el filtro
    private CrudDbHeadpod crudDbHeadpod;



    public AdapterMisPacientes(FragmentActivity fragmentActivity, ArrayList<ItemMisPacientes> items, CrudDbHeadpod crudDbHeadpod) {
        this.fragmentActivity = fragmentActivity;
        this.items = items;
        this.crudDbHeadpod=crudDbHeadpod;
    }


    public void setItemsAux(ArrayList<ItemMisPacientes> itemsaux)
    {
        this.itemsAux=itemsaux;
    }

    public void setItems(ArrayList<ItemMisPacientes> items)
    {
        this.items=items;
    }





    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }




    public void addAll(ArrayList<ItemMisPacientes> misPacientes) {
        for (int i = 0; i < misPacientes.size(); i++) {
            items.add(misPacientes.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {


        return items.get(arg0);
    }

    @Override
    public long getItemId(int position)
    {



        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) fragmentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_mis_paciente, null);//R.layout...,null
        }


        //Obtenemos el item de la posición
        //**********************************************************
        ItemMisPacientes dir = items.get(position);



        //Obtenemos los recursos del layout
        //******************************************************************************************



        CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        ImageView imgPaciente = (ImageView) v.findViewById(R.id.imgPaciente);
        TextView nombre = (TextView) v.findViewById(R.id.txtNombre);
        TextView apellido = (TextView) v.findViewById(R.id.txtApellido);

        //*************************************************************************************************************




        //

        //***********************************************************************************************************************************




        //Nota: interesante para la otra parte de escoger varios.
        /*    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                }
                }
            );*/

        //Almacenamos los checbox de cada paciente
        //********************************************************
        dir.setCheckBox(checkBox);


        //Activamos el checkbox que nos muestre la Base de datos.
        //*****************************************************************************************
        if(dir.getPaciente().getSeleccionado()==1)
        {
            checkBox.setChecked(true);
        }
        else
        {
            checkBox.setChecked(false);
        }
        //******************************************************************************************


        //Escribimos el nombre y el apellido del paciente
        //**************************************************************************************
        nombre.setText(dir.getPaciente().getNombre());
        apellido.setText(dir.getPaciente().getApellido());
        //*************************************************************************************






        //NOTA: FALTA POR ESCRIBIR EL NUMERO DE MEDICIONES QUE TIENE CADA PACIENTE
        //*********************************************************************************
        /////////
        /////
        ////
        //*************************************************************************************


        //Escuchamos el checbox
        //******************************************************************************************
        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(((CheckBox)(v)).isChecked())
                {

                   // Log.d("click","checbox_is_checked_true");
                    //desactivamos todos los checbox restantes
                    desactivarAllChecboxMenosselect(((CheckBox)(v)));

                    //Desleccionamos todos los pacientes
                    //******************************************
                    deseleccionarAllPacientes();

                    //Seleccionamos un pacitente de la lista.
                    //***************************************************
                    //Log.d("posicionItemes",String.valueOf(position));
                    seleccionarPaciente(items.get(position).getPaciente());


                }else{
                    // array[pos]=false;
                    Log.d("click","checbox_is_checked_false");

                    //Desleccionamos todos los pacientes.
                    deseleccionarAllPacientes();

                }

            }
        });
        //******************************************************************************************

       // Log.d("dir_pathPaciente", dir.getPaciente().getDir_imagen());

        // ((HomeActivity)fragmentActivity).setImgRedondeada(imgPaciente,dir.getPaciente().getDir_imagen());


        //Colocamos la imagen redondeandola
        //*****************************************************************************************
        setImgRedondeada(imgPaciente,dir.getPaciente().getDir_imagen());

        return v;
    }


    private void setImgRedondeada(ImageView fotoPerfil, String mCurrentPhotoPath) {
        // Get the dimensions of the View


        if(mCurrentPhotoPath!=null) {

            Uri path = Uri.parse(mCurrentPhotoPath);

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap imagenFinal = BitmapFactory.decodeFile(path.toString(),bmOptions);

            imagenFinal = Bitmap.createScaledBitmap(imagenFinal, 125,125 , false);

            //creamos el drawable redondeado
            RoundedBitmapDrawable roundedDrawable =
                    RoundedBitmapDrawableFactory.create(fragmentActivity.getResources(), imagenFinal);
            //asignamos el CornerRadius
            roundedDrawable.setCornerRadius(imagenFinal.getHeight());

            fotoPerfil.setImageDrawable(roundedDrawable);
        }
        else {
            Bitmap imagenOriginal = BitmapFactory.decodeResource(fragmentActivity.getResources(), R.drawable.monigote_verde_perfil);
            Bitmap imagenFinal = Bitmap.createScaledBitmap(imagenOriginal, 125,125 , false);
            //creamos el drawable redondeado
            RoundedBitmapDrawable roundedDrawable =
                    RoundedBitmapDrawableFactory.create(fragmentActivity.getResources(), imagenFinal);
            //asignamos el CornerRadius
            roundedDrawable.setCornerRadius(imagenFinal.getHeight());
            fotoPerfil.setImageDrawable(roundedDrawable);
        }

    }


    private void deseleccionarAllPacientes()
    {

        for (int i=0 ; i<items.size(); i++)
        {

            Log.d("tamano",Integer.toString(items.size()));

            Paciente paciente = items.get(i).getPaciente();

            paciente.setSeleccionado(0);
            Log.d("adapter-deselect-id",Long.toString(paciente.getId()));
            //Actualizamos la base de datos
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            crudDbHeadpod.updatePacienteById(paciente,paciente.getId());
            //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        }

        if(itemsAux!=null && !itemsAux.isEmpty())
        {


            for (int i=0 ; i<itemsAux.size(); i++)
            {

                Paciente paciente = itemsAux.get(i).getPaciente();

                paciente.setSeleccionado(0);
                Log.d("adapteraux-deselect-id",Long.toString(paciente.getId()));
                //Actualizamos la base de datos
                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                crudDbHeadpod.updatePacienteById(paciente,paciente.getId());
                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            }


        }

    }

    private void seleccionarPaciente(Paciente paciente)
    {


        Log.d("adapter-select-nombre",paciente.getNombre());
        Log.d("adapter-select-id",Long.toString(paciente.getId()));

        paciente.setSeleccionado(1);
        //Actualizamos la base de datos
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        crudDbHeadpod.updatePacienteById(paciente,paciente.getId());
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    }

    private void  desactivarAllChecboxMenosselect(CheckBox checkBox)
    {

        for (int i=0 ; i<items.size(); i++)
        {
            if(items.get(i).getCheckBox()!=checkBox)
            {
                Log.d("idCheckBox",String.valueOf(items.get(i).getCheckBox().getId()));
                Log.d("check","distitnos");

                items.get(i).getCheckBox().setChecked(false);
            }
            else
            {
                Log.d("check","igualessss");
                //items.get(i).getCheckBox().setChecked(true);
            }

        }


    }

}