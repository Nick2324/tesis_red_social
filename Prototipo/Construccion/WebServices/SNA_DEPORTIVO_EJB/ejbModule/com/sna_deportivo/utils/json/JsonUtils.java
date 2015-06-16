package com.sna_deportivo.utils.json;

import java.util.ArrayList;

import com.sna_deportivo.utils.ObjectSNSDeportivo;

public class JsonUtils {

	public static JsonObject JsonStringToObject(String json) {
		JsonObject resultado = new JsonObject();
		
		if(json.startsWith("{")){//es un objeto
			json = json.substring(1,json.length()-1);//eliminar corchete inicial y final del objeto
			String[] propiedadesObjeto = obtenerPropiedades(json);
			String llave, valor;
			for (String propiedad : propiedadesObjeto){
				llave = propiedad.substring(0, propiedad.indexOf(":"));
				valor = propiedad.substring(propiedad.indexOf(":")+1);
				if(llave.startsWith("\""))
					llave = llave.substring(1,llave.length()-1);//eliminar comilla inicial y final del nombre de la llave
				if(valor.startsWith("["))//es un arreglo
					resultado.setPropiedad(llave, JsonStringToObject(valor));
				else if (valor.startsWith("{"))//es un objeto
					resultado.setPropiedad(llave, JsonStringToObject(valor));
				else{ //es un valor
					if(valor.startsWith("\""))
						valor = valor.substring(1,valor.length()-1);//eliminar comilla inicial y final del valor
					resultado.setPropiedad(llave, new String[]{valor});
					}
			}
		}
		else if(json.startsWith("[")){//es un arreglo
			json = json.substring(1,json.length()-1);//eliminar corchete inicial y final del objeto
			String[] elementosArreglo = obtenerPropiedades(json);
			int posicion = 0;
			Object[] arreglo = new Object[elementosArreglo.length];
			for (String elemento : elementosArreglo){
				if(elemento.startsWith("{")){//es un objeto
					arreglo[posicion++] = JsonStringToObject(elemento);
				}else if (elemento.startsWith("[")){//es un arreglo
					arreglo[posicion++] = JsonStringToObject(elemento);
				}else{//es un valor
					if(elemento.startsWith("\""))
						elemento = elemento.substring(1,elemento.length()-1);//eliminar comilla inicial y final del valor
					arreglo[posicion++] = elemento;
				}
			}
			resultado.setPropiedad("arreglo", arreglo);
		}
		else{//es un valor
			json = json.substring(1,json.length()-1);//eliminar comilla inicial y final del objeto
			resultado.setPropiedad("0", json);
		}
		return resultado;
	}

	private static String[] obtenerPropiedades(String jsonString) {
		ArrayList<String> propiedades = new ArrayList<String>();
		short llavesCuadradasAbiertas = 0;
		short corchetesAbiertos = 0;
		int ultimaPosicionComa = 0;
		for (int i = 0; i < jsonString.length(); i++) {
			if (jsonString.charAt(i) == '{')
				corchetesAbiertos++;
			if (jsonString.charAt(i) == '[')
				llavesCuadradasAbiertas++;
			if (jsonString.charAt(i) == '}')
				corchetesAbiertos--;
			if (jsonString.charAt(i) == ']')
				llavesCuadradasAbiertas--;
			if (llavesCuadradasAbiertas == 0 && corchetesAbiertos == 0
					&& jsonString.charAt(i) == ',') {
				propiedades.add(jsonString.substring(ultimaPosicionComa, i));
				ultimaPosicionComa = i + 1;
			}
		}
		propiedades.add(jsonString.substring(ultimaPosicionComa,
				jsonString.length()));
		return propiedades.toArray(new String[propiedades.size()]);
	}
	
	public static String propiedadNula(Object obj){
		if(obj == null)
			return "null";
		else
			return "'"+obj.toString()+"'";
	}
	
	public static String arrayObjectSNSToStringJson(ObjectSNSDeportivo[] arreglo){
		StringBuilder arrayEventosJson = new StringBuilder("[");
		for(ObjectSNSDeportivo obj:arreglo){
			arrayEventosJson.append(obj.stringJson());
			arrayEventosJson.append(',');
		}
		if(arrayEventosJson.length() > 1)
			arrayEventosJson = arrayEventosJson.delete(arrayEventosJson.length()-1, arrayEventosJson.length());
		arrayEventosJson.append(']');
		return arrayEventosJson.toString();
	}
	
}
