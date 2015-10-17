package sportsallaround.utils.generales;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.json.JsonUtils;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import sportsallaround.utils.gui.KeyValueItem;

/**
 * Created by nicolas on 30/08/15.
 */
public final class ConstructorArrObjSNS {

    private ConstructorArrObjSNS(){}

    public static ArrayList<ObjectSNSDeportivo> producirArrayObjetoSNS(FactoryObjectSNSDeportivo fabrica,
                                                                       JSONArray arrayJson){
        if(fabrica != null && arrayJson != null) {
            ArrayList<ObjectSNSDeportivo> retorno = new ArrayList<ObjectSNSDeportivo>();
            try {
                for(int i = 0;i < arrayJson.length(); i++){
                    retorno.add(i,fabrica.getObjetoSNS());
                    retorno.get(i).deserializarJson(JsonUtils.JsonStringToObject(arrayJson.getString(i)));
                }
            } catch (ExcepcionJsonDeserializacion excepcionJsonDeserializacion) {
                excepcionJsonDeserializacion.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retorno;
        }

        return null;
    }

    public static ArrayList<KeyValueItem> producirArrayAdapterObjSNS(ArrayList<ObjectSNSDeportivo> objetos,
                                                                     String[] atributoAMostrarAdapter){
        ArrayList<KeyValueItem> adapter = new ArrayList<KeyValueItem>();
        Integer i = 0;
        for(ObjectSNSDeportivo obj:objetos){
            obj.retornoToString(atributoAMostrarAdapter);
            adapter.add(new KeyValueItem(i++,obj));
        }
        return adapter;
    }

    public static ArrayList<KeyValueItem> producirArrayAdapterObjSNS(ArrayList<ObjectSNSDeportivo> objetos,
                                                                     String[] atributoAMostrarAdapter,
                                                                     String[] separadoresAtributos) throws Exception{
        ArrayList<KeyValueItem> adapter = new ArrayList<KeyValueItem>();
        Integer i = 0;
        for(ObjectSNSDeportivo obj:objetos){
            obj.retornoToStringSeparadores(atributoAMostrarAdapter,separadoresAtributos);
            adapter.add(new KeyValueItem(i++,obj));
        }
        return adapter;
    }

}
