package sportsallaround.utils.gui;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.json.JsonSerializable;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.Peticion;

public class SpinnerDesdeBD extends Fragment {

    private InitializerSpinnerBD mListener;

    public interface InitializerSpinnerBD {

        public FactoryObjectSNSDeportivo getFactoryObjetosSNS();
        public String getServicio();
        public String getMetodo();
        public JSONObject getParam();
        public String getAtributoMostradoSpinner();
        public String getTituloSpinner();
        public void onItemSelectedSpinnerBD(KeyValueItem seleccionado);
        public void onNothingSelectedSpinnerBD();

    }

    private class PeticionSpinner extends Peticion {

        private InitializerSpinnerBD actividad;

        public PeticionSpinner(InitializerSpinnerBD actividad){
            this.actividad = actividad;
        }

        @Override
        public void calcularMetodo() {
            this.metodo = actividad.getMetodo();
        }

        @Override
        public void calcularServicio() {
            this.servicio = actividad.getServicio();
        }

        @Override
        public void calcularParams() {
            this.params = actividad.getParam();
        }

        @Override
        public void doInBackground() {}

        @Override
        public void onPostExcecute(String resultadoPeticion) {
            if (resultadoPeticion != null && !((String)resultadoPeticion).equals("")) {
                try {
                    JSONArray responseJson = new JSONArray((String) resultadoPeticion);
                    ArrayList<KeyValueItem> listaObjetosSNS = new ArrayList<KeyValueItem>();
                    for (int i = 0; i < responseJson.length(); i++) {
                        ObjectSNSDeportivo objSNS = this.actividad.getFactoryObjetosSNS().getObjetoSNS();
                        objSNS.retornoToString(this.actividad.getAtributoMostradoSpinner());
                        ((JsonSerializable)objSNS).deserializarJson(
                                JsonUtils.JsonStringToObject(responseJson.getString(i)));
                        listaObjetosSNS.add(new KeyValueItem(new Integer(i),objSNS));
                    }
                    Spinner spinner = (Spinner)getView().findViewById(R.id.spinner_bd);
                    spinner.setAdapter(new ArrayAdapter<KeyValueItem>(
                            getView().getContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            listaObjetosSNS));

                } catch (JSONException e){
                    Log.w("SNA:JSONException", e.getMessage());
                } catch (ExcepcionJsonDeserializacion e) {
                    Log.w("SNA:ExcepcionJsonDes", e.getMessage());
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_spinner_desde_bd, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (InitializerSpinnerBD) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new ClassCastException(activity.toString()
                    + " must implement InitializerSpinnerBD");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart(){
        super.onStart();
        this.setUpNombreSpinner();
        this.setUpListeners();
        this.setUpKeyValue();
    }

    public ObjectSNSDeportivo getSeleccionado(){
        Spinner spinner = (Spinner)getView().findViewById(R.id.spinner_bd);
        return (ObjectSNSDeportivo)((KeyValueItem)spinner.getSelectedItem()).getValue();
    }

    public void setUpNombreSpinner(){
        TextView titulo = (TextView)getView().findViewById(R.id.textview_spinner_bd);
        titulo.setText(mListener.getTituloSpinner());
    }

    public void setUpListeners(){
        Spinner spinner = (Spinner)getView().findViewById(R.id.spinner_bd);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mListener.onItemSelectedSpinnerBD((KeyValueItem) parent.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mListener.onNothingSelectedSpinnerBD();
            }

        });

    }

    public void setUpKeyValue(){
        new PeticionSpinner(mListener).ejecutarPeticion();
    }

}
