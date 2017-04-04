package com.siestasystemheadpod.headpodv10;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.siestasystemheadpod.headpodv10.adicionales.ble.DeviceScanFragment;
import com.siestasystemheadpod.headpodv10.adicionales.cargando.CargandoFragment;
import com.siestasystemheadpod.headpodv10.fragments.AllAvatarFragment;
import com.siestasystemheadpod.headpodv10.fragments.AllAvatarMedicionFragment;
import com.siestasystemheadpod.headpodv10.fragments.DatosEnTRealFragment;
import com.siestasystemheadpod.headpodv10.fragments.HomeFragment;
import com.siestasystemheadpod.headpodv10.fragments.InformeComparativoFragment;
import com.siestasystemheadpod.headpodv10.fragments.InformeFragment;
import com.siestasystemheadpod.headpodv10.fragments.MedicionFragment;
import com.siestasystemheadpod.headpodv10.fragments.MedicionPlantillaPasosFragment;
import com.siestasystemheadpod.headpodv10.fragments.PerfilFragment;
import com.siestasystemheadpod.headpodv10.fragments.PruebaGraficosFragment;
import com.siestasystemheadpod.headpodv10.fragments.informe.ResultInclinationFragment;
import com.siestasystemheadpod.headpodv10.fragments.pacientes.AnadirPacienteFragment;
import com.siestasystemheadpod.headpodv10.fragments.pacientes.MisPacientesFragment;

/**
 * Created by Javier on 28/03/2017.
 * Esta clase se utilizará para adaptar los diferentes fragmentos a la activity Home
 * tendra un metodo general paar todos ellos y uno especifico para diferenciar unos de otros
 * Tambien mediante otro metodo se podra comprobar si existe o no para asi poder crearlo
 */

public class FragmentsAdapter{

    private FragmentManager fm;
    private FragmentTransaction ft;
    private String nameFragmentActual;
    private HomeActivity homeActivity;


    public FragmentsAdapter(FragmentManager fragmentManager,HomeActivity homeActivity){
        fm = fragmentManager;
        ft = fragmentManager.beginTransaction();
        this.homeActivity=homeActivity;
    }









    public void adjuntarFragmentoGeneral(Fragment frag, String tag){
        ft = fm.beginTransaction();
        if (tag.equals("home_fragment")){

            homeActivity.setNameFragmentAcutal(tag);
            ft.replace(R.id.contenedorHome, frag, tag);
            ft.commit();
        }else{
            homeActivity.setNameFragmentAcutal(tag);
            ft.replace(R.id.contenedorHome, frag, tag);
            ft.addToBackStack(tag);
            homeActivity.pilaAuxFragments.add(tag);
            ft.commit();
        }


        // super.getSupportFragmentManager().beginTransaction().replace(R.id.contenedorHome, frag, tag).commit();
    }









    //Método que avisa si existe el framento y en caso de existir de volver a ejecutarlo sin crear uno nuevo
    public Boolean existFragment(String tagFrament)
    {
        Boolean exito= false;
        FragmentTransaction fragmentTransaction;
        FragmentManager fragmentManager;

        Fragment fragment = fm.findFragmentByTag(tagFrament);
        Log.d("fragmento", " y TagFragment: "+tagFrament);

        if(fragment!=null){
            Fragment fragment_actual =  fm.findFragmentByTag(tagFrament);

            if(tagFrament.equals("vincular_desvincular_sensor") )
            {
                ft = fm.beginTransaction();
                ft.remove(fragment_actual);
                ft.replace(R.id.contenedorHome, fragment_actual);
                ft.commit();
            }else{
                ft = fm.beginTransaction();
                ft.replace(R.id.contenedorHome, fragment_actual);
                if(!homeActivity.pilaAuxFragments.contains(tagFrament) && !tagFrament.equals("fragment_home")){
                    homeActivity.pilaAuxFragments.add(tagFrament);
                }
                ft.commit();
            }
            exito = true;
        }
        return  exito;
    }









    public void colocarFragmentoEspecifico(String tagFragment) {

        homeActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        switch (tagFragment) {
            case "fragment_home":
                ft = fm.beginTransaction();
                homeActivity.setNameFragmentAcutal(tagFragment);
                homeActivity.vaciarPilaNavegacion();
                homeActivity.vaciarPilaTransicionFragments();

                HomeFragment home_fragment = new HomeFragment();
                adjuntarFragmentoGeneral(home_fragment, tagFragment);

                break;



            case "fragment_perfil":
                if(!existFragment(tagFragment))
                {
                    PerfilFragment perfil_fragment = new PerfilFragment();
                    adjuntarFragmentoGeneral(perfil_fragment, tagFragment);
                }
                break;



            case "fragment_medicion":
                if(!existFragment(tagFragment))
                {
                    MedicionFragment medicion_fragment = new MedicionFragment();
                    adjuntarFragmentoGeneral(medicion_fragment, tagFragment);
                }
                break;



            case "fragment_medicion_sin_headpod":
                if(!existFragment(tagFragment))
                {
                    MedicionPlantillaPasosFragment medicion_sin_hp_fragment =  MedicionPlantillaPasosFragment.newInstance("fragment_medicion_sin_headpod", "");
                    adjuntarFragmentoGeneral(medicion_sin_hp_fragment, tagFragment);
                }
                break;



            case "fragment_medicion_con_headpod":
                if(!existFragment(tagFragment))
                {
                    MedicionPlantillaPasosFragment medicion_con_hp_fragment =  MedicionPlantillaPasosFragment.newInstance("fragment_medicion_con_headpod", "");
                    adjuntarFragmentoGeneral(medicion_con_hp_fragment, tagFragment);
                }
                break;



            case  "fragment_datos_ent_real"://avatar3d//Datos en tiempo real.
                if(!existFragment(tagFragment))
                {
                    DatosEnTRealFragment datosEnTRealFragment = new DatosEnTRealFragment();
                    adjuntarFragmentoGeneral(datosEnTRealFragment, tagFragment);
                }
                break;



            case "fragment_all_avatar"://avatar3d//Datos en tiempo real.
                if(!existFragment(tagFragment))
                {
                    AllAvatarFragment all_fragments_avatar = new AllAvatarFragment();
                    adjuntarFragmentoGeneral(all_fragments_avatar, tagFragment);
                }
                break;



            case "fragment_cargando"://fragmento de espera para cargar la caras en 2d y 3D
                if(!existFragment(tagFragment))
                {
                    CargandoFragment cargandoFragment = new CargandoFragment();
                    adjuntarFragmentoGeneral(cargandoFragment, tagFragment);
                }
                break;



            case "fragment_medicion_sin_headpod_iniciar"://avatar3d//Datos en tiempo real.//nombre de fragmento inventado.
                if(!existFragment(tagFragment))
                {
                    AllAvatarMedicionFragment all_fragments_avatar_medicion1 = AllAvatarMedicionFragment.newInstance("medicion_sin_hp","");
                    adjuntarFragmentoGeneral(all_fragments_avatar_medicion1, tagFragment);
                }
                break;



            case "fragment_medicion_con_headpod_iniciar"://avatar3d//Datos en tiempo real.//nombre de fragmento inventado.
                if(!existFragment(tagFragment))
                {
                    AllAvatarMedicionFragment all_fragments_avatar_medicion2 = AllAvatarMedicionFragment.newInstance("medicion_con_hp","");
                    adjuntarFragmentoGeneral(all_fragments_avatar_medicion2, tagFragment);
                }
                break;



            case "fragment_mis_pacientes":
                if(!existFragment(tagFragment))
                {
                    MisPacientesFragment misPacientesFragment = new MisPacientesFragment();
                    adjuntarFragmentoGeneral(misPacientesFragment, tagFragment);
                }
                break;



            case "fragment_anadir_paciente":
                if(!existFragment(tagFragment))
                {
                    AnadirPacienteFragment anadirPacienteFragment = new AnadirPacienteFragment();
                    adjuntarFragmentoGeneral(anadirPacienteFragment, tagFragment);
                }
                break;



            case "vincular_desvincular_sensor":
                if(!existFragment(tagFragment))
                {
                    if(homeActivity.estadoSensor==0) {
                        DeviceScanFragment deviceScanFragment = new DeviceScanFragment();
                        adjuntarFragmentoGeneral(deviceScanFragment, tagFragment);
                    }
                    else
                    {
                        homeActivity.desvincularSensor();
                    }
                }
                break;



            case "fragment_prueba_graficos":
                if(!existFragment(tagFragment))
                {
                    PruebaGraficosFragment fragment_prueva_graficos = new PruebaGraficosFragment();
                    adjuntarFragmentoGeneral(fragment_prueva_graficos, tagFragment);
                }
                break;



            case "fragment_informe":
                if(!existFragment(tagFragment))
                {
                    InformeFragment fragment_informe = new InformeFragment();
                    adjuntarFragmentoGeneral(fragment_informe, tagFragment);
                }
                break;



            case "fragment_informe_comparativo":
                if(!existFragment(tagFragment))
                {
                    InformeComparativoFragment fragment_informe_comparativo = new InformeComparativoFragment();
                    adjuntarFragmentoGeneral(fragment_informe_comparativo, tagFragment);
                }
                break;



            case "fragment_result_inclination":
                if(!existFragment(tagFragment))
                {
                    ResultInclinationFragment fragment_inclination_result = new ResultInclinationFragment();
                    adjuntarFragmentoGeneral(fragment_inclination_result, tagFragment);
                }
                break;
        }
        homeActivity.nameFramgentAnterior=tagFragment;
    }

}