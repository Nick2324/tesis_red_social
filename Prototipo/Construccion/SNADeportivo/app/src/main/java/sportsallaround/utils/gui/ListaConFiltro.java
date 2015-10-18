package sportsallaround.utils.gui;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import sportsallaround.snadeportivo.R;

public class ListaConFiltro extends Fragment {

    private CallBackListaConFiltro actividad;

    public interface CallBackListaConFiltro {

        public void realizarAccionAlClick(KeyValueItem item, String identificadorFragmento);
        public ArrayList<KeyValueItem> generarAdapter(String identificadorFragmento);
        public ArrayList<KeyValueItem> regenerarAdapter(String identificadorFragmento);
        public void realizarAccionLongClick(KeyValueItem item, String identificadorFragmento);

    }

    public ListaConFiltro() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_con_filtro, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.actividad = (CallBackListaConFiltro) activity;
        } catch (Exception e) {
            Log.e("Nick:ListaFil", "No soporta CallBackListaConFiltro");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.setUpViews();
    }

    private void generarAdapter(){
        ArrayList<KeyValueItem> listaElementos =
                this.actividad.generarAdapter(this.getTag());
        if(listaElementos != null){
            ArrayAdapter<KeyValueItem> adapter =
                    new ArrayAdapter<KeyValueItem>(getActivity(),
                            android.R.layout.simple_selectable_list_item,
                            listaElementos);
            ((ListView)getView().findViewById(R.id.listview_lista_con_filtro)).setAdapter(adapter);
        }
    }

    private void setUpViews() {
        this.generarAdapter();
        ListView lista = (ListView) getView().findViewById(R.id.listview_lista_con_filtro);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView lista = (ListView) parent;
                actividad.realizarAccionAlClick((KeyValueItem) lista.getAdapter().getItem(position), getTag());
            }
        });
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ListView lista = (ListView) parent;
                actividad.realizarAccionLongClick((KeyValueItem) lista.getAdapter().getItem(position), getTag());
                return false;
            }
        });
        EditText texto = (EditText) getView().findViewById(R.id.edittext_busqueda_filtro_lista);
        texto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ListView lista = (ListView) getView().findViewById(R.id.listview_lista_con_filtro);
                if(lista.getAdapter()!=null)
                    ((ArrayAdapter) lista.getAdapter()).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }

    public void llenarLista(){
        this.limpiarLista();
        ListView lista = (ListView)getView().findViewById(R.id.listview_lista_con_filtro);
        lista.setAdapter(null);
        ArrayList<KeyValueItem> arrayLista =
                this.actividad.regenerarAdapter(getTag());
        ArrayAdapter<KeyValueItem> datosListaEventos =
                new ArrayAdapter<KeyValueItem>(getActivity(),
                        android.R.layout.simple_selectable_list_item,
                        arrayLista);
        lista.setAdapter(datosListaEventos);
    }


    public void limpiarLista(){
        ListView lista = (ListView) getView().findViewById(R.id.listview_lista_con_filtro);
        if(lista.getAdapter() != null){
            ((ArrayAdapter)lista.getAdapter()).clear();
        }
    }

    public void  eliminarElemento(KeyValueItem aBorrar){
        ListView lista = (ListView)getView().findViewById(R.id.listview_lista_con_filtro);
        ((ArrayAdapter)lista.getAdapter()).remove(aBorrar);
        ((ArrayAdapter)lista.getAdapter()).notifyDataSetChanged();
    }

}
