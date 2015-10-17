package com.sna_deportivo.utils.json;

import java.util.ArrayList;

import com.sna_deportivo.utils.gr.FactoryObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ObjectSNSDeportivo;
import com.sna_deportivo.utils.gr.ProductorSNSDeportivo;
import com.sna_deportivo.utils.gr.StringUtils;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class JsonUtils {

	public static JsonObject JsonStringToObject(String json) {
		JsonObject resultado = new JsonObject();
		json = json.replace("\n", "").trim();
		
		if(json.startsWith("{")){//es un objeto
			json = json.substring(1,json.length()-1);//eliminar corchete inicial y final del objeto
			String[] propiedadesObjeto = obtenerPropiedades(json);
			if(propiedadesObjeto != null){
				String llave, valor;
				for (String propiedad : propiedadesObjeto){
					llave = propiedad.substring(0, propiedad.indexOf(":")).trim();
					valor = propiedad.substring(propiedad.indexOf(":")+1).trim();
					/*System.out.println("************************************************");
					System.out.println("Llave ====== "+llave);
					System.out.println("Valor ====== "+valor);
					System.out.println("************************************************");*/
					if(llave.startsWith("\"") || llave.startsWith("'"))
						llave = llave.substring(1,llave.length()-1);//eliminar comilla inicial y final del nombre de la llave
					if(valor.startsWith("["))//es un arreglo
						resultado.setPropiedad(llave, JsonStringToObject(valor));
					else if (valor.startsWith("{"))//es un objeto
						resultado.setPropiedad(llave, JsonStringToObject(valor));
					else{ //es un valor
						if(valor.startsWith("\"") || valor.startsWith("'"))
							valor = valor.substring(1,valor.length()-1);//eliminar comilla inicial y final del valor
						resultado.setPropiedad(llave, new String[]{valor});
						}
				}
			}else{
				resultado.setPropiedad("", "");
			}
		}
		else if(json.startsWith("[")){//es un arreglo
			json = json.substring(1,json.length()-1).trim();//eliminar corchete inicial y final del objeto
			String[] elementosArreglo = obtenerPropiedades(json);
			if(elementosArreglo == null){
				elementosArreglo = new String[1];
				elementosArreglo[0] = "";
				}
			int posicion = 0;
			Object[] arreglo = new Object[elementosArreglo.length];
			for (String elemento : elementosArreglo){
				elemento = elemento.trim();
				if(elemento.startsWith("{")){//es un objeto
					arreglo[posicion++] = JsonStringToObject(elemento);
				}else if (elemento.startsWith("[")){//es un arreglo
					arreglo[posicion++] = JsonStringToObject(elemento);
				}else{//es un valor
					if(elemento.startsWith("\"") || elemento.startsWith("'"))
						elemento = elemento.substring(1,elemento.length()-1);//eliminar comilla inicial y final del valor
					arreglo[posicion++] = elemento;
				}
			}
			resultado.setPropiedad("arreglo", arreglo);
		}
		else{//es un valor
			/*System.out.println("************************************************");
			System.out.println("Json ====== "+json);
			System.out.println("************************************************");*/
			json = json.substring(1,json.length()-1).trim();//eliminar comilla inicial y final del objeto
			resultado.setPropiedad("0", json);
		}
		return resultado;
	}

	private static String[] obtenerPropiedades(String jsonString) {
		jsonString = jsonString.trim();
		if(jsonString.equals(""))
			return null;
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
	
	private static boolean ponerComa(Object[] anterior,int indice){
		if(anterior != null && indice >= 0){
			for(int i = indice - 1; i >= 0; i--){
				if(anterior[i] != null && 
				   !anterior[i].toString().equals("null")){
					return true;
				}
			}
		}
		return false;
	}
	
	public static String propiedadNula(String propiedad,Object obj,Object[] anterior,int indice){
		String retorno = "";
		if(obj != null && 
		   obj.toString() != null && 
		   !obj.toString().equals("null") &&
		   propiedad != null &&
		   !propiedad.equals("null")){
			String coma = "";
			if(JsonUtils.ponerComa(anterior, indice)){
				coma = ",";
			}
			retorno =  coma +
					propiedad + ":" + 
				   "'"+StringUtils.corregirDobleBackslash(StringUtils.decodificar(obj.toString()))+"'";
		}
		return retorno;
	}
	
	public static String propiedadNulaSinDecodificar(String propiedad,Object obj,Object[] anterior,int indice){
		String retorno = "";
		if(obj != null && 
		   obj.toString() != null && 
		   !obj.toString().equals("null") &&
		   propiedad != null &&
		   !propiedad.equals("null")){
			String coma = "";
			if(JsonUtils.ponerComa(anterior, indice)){
				coma = ",";
			}
			retorno =  coma +
					propiedad + ":" + 
				   "'"+StringUtils.corregirDobleBackslash(obj.toString())+"'";
		}
		return retorno;
	}
	
	public static String propiedadNulaTDPrimitivo(String propiedad,Object obj,Object[] anterior,int indice){
		String retorno = "";
		if(obj != null && 
		   obj.toString() != null && 
		   !obj.toString().equals("null") &&
		   propiedad != null &&
		   !propiedad.equals("null")){
			String coma = "";
			if(JsonUtils.ponerComa(anterior, indice)){
				coma = ",";
			}
			retorno =  coma +
					propiedad + ":" + 
				   obj.toString();
		}
		return retorno;
	}
	
	public static String propiedadNulaTodoTipo(String propiedad,String valor, Class<?> tipoValor)
		throws ExcepcionJsonDeserializacion{
		if(tipoValor != null && propiedad != null && 
		   !propiedad.equals("null") && valor != null &&
		   !valor.equals("null")){
			if(tipoValor.equals(String.class)){
				return propiedadNula(propiedad, valor, null, -1);
			}else{
				return propiedadNulaTDPrimitivo(propiedad, 
							valor, null, -1);
			}
		}
		throw new ExcepcionJsonDeserializacion();
	}
	
	public static String propiedadNulaBackwards(Object obj){
		if(obj == null || obj.toString() == null || obj.toString().equals("null"))
			return null;
		else{
			return StringUtils.decodificar(obj.toString());
		}
	}
	
	public static Object propiedadNulaBackwardsTDPrimi(Object obj,Class<?> claseTDP){
		if(obj == null || obj.toString() == null || obj.toString().equals("null"))
			return null;
		else{
			if(claseTDP.getSimpleName().equals("Integer")){
				return Integer.parseInt(obj.toString());
			}else if(claseTDP.getSimpleName().equals("Boolean")){
				return Boolean.parseBoolean(obj.toString());
			}else{
				//implementar para los demas
				return obj.toString() ;
			}
		}
	}
	
	public static String arrayObjectSNSToStringJson(ObjectSNSDeportivo[] arreglo){
		StringBuilder arrayJson = new StringBuilder("[");
		if(arreglo != null){
			for(ObjectSNSDeportivo obj:arreglo){
				arrayJson.append(obj.stringJson());
				arrayJson.append(',');
			}
			if(arrayJson.length() > 1)
				arrayJson = arrayJson.delete(arrayJson.length()-1, arrayJson.length());
		}
		arrayJson.append(']');
		return arrayJson.toString();
	}
	
	public static ArrayList<ObjectSNSDeportivo> convertirMensajeJsonAObjectSNS(String jsonString,
																			   String objetoDeseadoMensaje,
																			   ProductorSNSDeportivo pcsnsd)
				throws ExcepcionJsonDeserializacion,ProductorFactoryExcepcion{
		ArrayList<ObjectSNSDeportivo> convertidos = new ArrayList<ObjectSNSDeportivo>();
		if(jsonString != null){
			JsonObject objetoJson = JsonUtils.JsonStringToObject(jsonString);
			JsonObject objetoMensaje = null;
			if(objetoJson.getPropiedades().get(objetoDeseadoMensaje) != null &&
				objetoJson.getPropiedades().get(objetoDeseadoMensaje).length > 0){
				objetoMensaje = (JsonObject)objetoJson.getPropiedades().get(objetoDeseadoMensaje)[0];
			}
			if(objetoMensaje != null){
				JsonObject objetosDelTipo = null;
				FactoryObjectSNSDeportivo factory = null;
				ObjectSNSDeportivo objeto;
				for(String tipoObjeto:objetoMensaje.getPropiedades().keySet()){
					if(tipoObjeto != null && tipoObjeto != ""){
						factory = pcsnsd.producirFacObjetoSNS(tipoObjeto);
						objeto = factory.getObjetoSNS();
						objetosDelTipo = 
								(JsonObject)objetoMensaje.
								getPropiedades().
								get(tipoObjeto)[0];
						for(Object aDeserializar:
							objetosDelTipo.getPropiedades().get("arreglo")){
							try {
								objeto.deserializarJson((JsonObject)aDeserializar);
								convertidos.add(objeto);
							} catch (ExcepcionJsonDeserializacion e) {
								e.printStackTrace();
								System.out.println("Mensaje malformado "+
										((JsonObject)aDeserializar).toString());
								throw e;
							}
						}
					}
				}
			}
		}
		
		return convertidos;
		
	}
	
}
