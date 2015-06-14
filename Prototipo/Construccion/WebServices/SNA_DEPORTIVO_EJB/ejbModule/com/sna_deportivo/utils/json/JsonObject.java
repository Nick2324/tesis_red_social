package com.sna_deportivo.utils.json;

import java.util.HashMap;
import java.util.Map;


public class JsonObject {
	
	private Map<String,Object[]> propiedades;
	
	public JsonObject() {
		propiedades = new HashMap<String,Object[]>();
	}
	
	public Map<String, Object[]> getPropiedades() {
		return propiedades;
	}
	
	public void setPropiedad(String llave, Object[] valor) {
		propiedades.put(llave, valor);
	}
	
	public void setPropiedad(String llave, Object valor) {
		propiedades.put(llave, new Object[]{valor});
	}

	
}
