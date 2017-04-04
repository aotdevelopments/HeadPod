package com.siestasystemheadpod.headpodv10.adicionales.extra_mis_pacientes;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.siestasystemheadpod.headpodv10.data.modelo.Paciente;

/**
 * Created by Michael on 11/01/2017.
 * Clase para obtener cada Item de la lista de pacientes
 *
 */

public class ItemMisPacientes {

        private Paciente paciente;

        private CheckBox checkBox;



        public ItemMisPacientes() {
            super();
        }

        public void setPaciente (Paciente paciente)
        {
            this.paciente=paciente;
        }
        public Paciente getPaciente()
        {
            return paciente;
        }


        public void setCheckBox (CheckBox checkBox)
        {

            this.checkBox=checkBox;
        }

        public CheckBox getCheckBox()
        {
            return checkBox;
        }





}
