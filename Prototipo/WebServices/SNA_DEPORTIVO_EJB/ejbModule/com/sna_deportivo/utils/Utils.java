package com.sna_deportivo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class Utils {

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

	@SuppressWarnings("unchecked")
	public static Map<String, Object> JSONStringToObject(String jsonString) {
		Map<String, Object> objetoJson = new HashMap<String, Object>();
		int posicionDivision;
		String llave;
		String valor;
		String[] propiedades = obtenerPropiedades(jsonString);
		String[] objetosArreglo;
		for (String propiedad : propiedades) {
			posicionDivision = propiedad.indexOf(':');
			llave = propiedad.substring(0, posicionDivision);
			valor = propiedad.substring(++posicionDivision, propiedad.length());
			if (llave.startsWith("\"")) {
				llave = llave.substring(1, llave.length() - 1);
			}
			// Es un objeto
			if (valor.startsWith("{")) {
				valor = valor.substring(1, valor.length() - 1);
				objetoJson.put(llave, JSONStringToObject(valor));
			}
			// Es un arreglo
			if (valor.startsWith("[")) {
				boolean arregloDeValores = true;
				valor = valor.substring(1, valor.length() - 1);
				if (valor.startsWith("{")) {// No es un arreglo de valores
					valor = valor.substring(1, valor.length() - 1);
					arregloDeValores = false;
				}
				objetosArreglo = obtenerPropiedades(valor);
				Map<String, Object>[] arregloObjetos = (Map<String, Object>[]) new Map[objetosArreglo.length];
				int i = 0;
				for (String objeto : objetosArreglo) {
					if (arregloDeValores){
						Map<String, Object> primitivo = new HashMap<String, Object>();
						if(objeto.startsWith("\""))
							primitivo.put("valor", objeto.substring(1, objeto.length()-1));
						else
							primitivo.put("valor", objeto);
						arregloObjetos[i] = primitivo;
						}
					else
						arregloObjetos[i] = JSONStringToObject(objeto);
				}
				objetoJson.put(llave, arregloObjetos);
			} else {
				objetoJson.put(llave, valor);
			}
		}
		return objetoJson;
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
