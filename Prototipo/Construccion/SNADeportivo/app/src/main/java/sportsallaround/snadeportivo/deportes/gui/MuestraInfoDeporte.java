package sportsallaround.snadeportivo.deportes.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.deportes.FactoryDeporte;
import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;

import org.json.JSONObject;

import sportsallaround.snadeportivo.R;
import sportsallaround.utils.generales.Constants;
import sportsallaround.utils.gui.KeyValueItem;
import sportsallaround.utils.gui.SpinnerDesdeBD;
import sportsallaround.utils.gui.TituloActividad;

public class MuestraInfoDeporte extends Activity implements
        TituloActividad.InitializerTituloActividad,
        SpinnerDesdeBD.InitializerSpinnerBD{

    private Deporte deporteSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_info_deporte);
    }

    @Override
    public FactoryObjectSNSDeportivo getFactoryObjetosSNS() {
        return new FactoryDeporte();
    }

    @Override
    public String getServicio() {
        return Constants.SERVICES_PATH_DEPORTES;
    }

    @Override
    public String getMetodo() {
        return "GET";
    }

    @Override
    public JSONObject getParam() {
        return new JSONObject();
    }

    @Override
    public String getAtributoMostradoSpinner() {
        return "nombre";
    }

    @Override
    public String getTituloSpinner() {
        return getString(R.string.titulo_spinner_deporte);
    }

    @Override
    public void onPostExcecute() {}

    @Override
    public void onItemSelectedSpinnerBD(KeyValueItem seleccionado, String tagFragmento) {
        this.setUpObjetos((Deporte)seleccionado.getValue());
        this.setUpDatosActividad();
    }

    @Override
    public void onNothingSelectedSpinnerBD(String tagFragmento) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getIdTituloActividad(String tagFragmento) {
        return getString(R.string.titulo_descripcion_deporte);
    }

    private void setUpObjetos(Deporte deporte){
        this.deporteSeleccionado = deporte;
    }

    private void setUpDatosActividad(){
        if(this.deporteSeleccionado != null){
            EditText nombre = (EditText)findViewById(R.id.edittext_nombre_deporte);
            nombre.setText(this.deporteSeleccionado.getNombre());

            EditText descripcion = (EditText)findViewById(R.id.edittext_descripcion_deporte);
            descripcion.setText(this.deporteSeleccionado.getDescripcion());

            EditText historia = (EditText)findViewById(R.id.edittext_historia_deporte);
            historia.setText(this.deporteSeleccionado.getHistoria());

            EditText olimpico = (EditText)findViewById(R.id.edittext_olimpico_deporte);
            olimpico.setText(this.deporteSeleccionado.getEsOlimpico()?
                             getString(R.string.TRUE):getString(R.string.FALSE));

            EditText fechaCreacion = (EditText)findViewById(R.id.edittext_fecha_creacion_deporte);
            fechaCreacion.setText(this.deporteSeleccionado.getFechaCreacion());
        }
    }

}
