package com.sna_deportivo.utils;

import java.util.ArrayList;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.sna_deportivo.pojo.JsonObject;

public class Utils {
	
	private static String PATH = "transaction/commit";

	public static boolean servidorActivo() {

		ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
		ResteasyProviderFactory.pushContext(javax.ws.rs.ext.Providers.class,
				factory);

		ResteasyClient client = new ResteasyClientBuilder().build();
		WebTarget target = client.target(Constantes.SERVER_ROOT_URI).path("/");
		Response result = target.request().accept(MediaType.TEXT_PLAIN)
				.get(Response.class);

		return result.getStatus() == 200;
	}

	public static ResteasyClient obtenerCliente() {
		ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
		ResteasyProviderFactory.pushContext(javax.ws.rs.ext.Providers.class,
				factory);

		return new ResteasyClientBuilder().build();
	}
	
	public static Object[] EjecutarQuery(String query) throws BDException{
		String stringRespuesta;
		if (!Utils.servidorActivo())
			throw new BDException();
		else {
			String payload = "{\"statements\":[{\"statement\":\"" + query
					+ "\"}]}";
			ResteasyClient cliente = Utils.obtenerCliente();
			WebTarget target = cliente.target(Constantes.SERVER_ROOT_URI).path(
					PATH);
			Response result = target
					.request()
					.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8")
					.post(Entity.entity(payload, MediaType.APPLICATION_JSON),
							Response.class);
			if (result.getStatus() == 200) {
				stringRespuesta = result.readEntity(String.class);
				JsonObject resultado = JsonStringToObject(stringRespuesta);
				JsonObject results = (JsonObject) resultado.getPropiedades().get("results")[0];
				JsonObject arregloData = (JsonObject) results.getPropiedades().get("arreglo")[0];
				JsonObject data = (JsonObject) arregloData.getPropiedades().get("data")[0];
				Object[] contenidoData = data.getPropiedades().get("arreglo");
				return contenidoData;
			} else
				throw new BDException();
		}
	}
	
	public static String EjecutarPost(String ruta, String extras, String metodo) throws BDException{
		if (!Utils.servidorActivo())
			throw new BDException();
		else {
			ResteasyClient cliente = Utils.obtenerCliente();
			WebTarget target = cliente.target(Constantes.SERVER_ROOT_URI).path(
					ruta);
			Builder resultBuilder = target
					.request()
					.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8");
			if(extras.equals(""))
				extras = null;
			Response result;
			if(metodo.toLowerCase().equals("post"))
				result = resultBuilder.post(extras != null ? Entity.entity(extras, MediaType.APPLICATION_JSON):null,Response.class);
			else
				result = resultBuilder.put(extras != null ? Entity.entity(extras, MediaType.APPLICATION_JSON):null,Response.class);
			if (result.getStatus() == 201) {
				return result.getHeaderString("Location");
			} else
				return result.getStatus() + "";
		}
	}

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
}
