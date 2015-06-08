package com.sna_deportivo.utils;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.sna_deportivo.pojo.general.ObjectSNSDeportivo;
import com.sna_deportivo.pojo.json.JsonObject;
import com.sna_deportivo.utils.excepciones.BDException;

public class BDUtils {

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
	
	//PODRIA DEVOLVER TAMBIEN EL/LOS RECURSOS DE LA LLAMADA?
	public static Object[] ejecutarQuery(String query) throws BDException {
		String stringRespuesta;
		if (!BDUtils.servidorActivo())
			throw new BDException();
		else {
			String payload = "{\"statements\":[{\"statement\":\"" + query
					+ "\"}]}";
			ResteasyClient cliente = BDUtils.obtenerCliente();
			WebTarget target = cliente.target(Constantes.SERVER_ROOT_URI).path(
					PATH);
			Response result = target
					.request()
					.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8")
					.post(Entity.entity(payload, MediaType.APPLICATION_JSON),
							Response.class);
			if (result.getStatus() == 200) {
				stringRespuesta = result.readEntity(String.class);
				JsonObject resultado = Utils
						.JsonStringToObject(stringRespuesta);
				JsonObject results = (JsonObject) resultado.getPropiedades()
						.get("results")[0];
				JsonObject arregloData = (JsonObject) results.getPropiedades()
						.get("arreglo")[0];
				JsonObject data = (JsonObject) arregloData.getPropiedades()
						.get("data")[0];
				Object[] contenidoData = data.getPropiedades().get("arreglo");
				return contenidoData;
			} else
				throw new BDException();
		}
	}

	public static String crearNodo() throws BDException {
		if (!BDUtils.servidorActivo())
			throw new BDException();

		ResteasyClient cliente = BDUtils.obtenerCliente();
		WebTarget target = cliente.target(Constantes.SERVER_ROOT_URI).path(
				"/node");
		Builder resultBuilder = target.request().accept(
				MediaType.APPLICATION_JSON + "; charset=UTF-8");
		Response result;
		result = resultBuilder.post(null, Response.class);
		if (result.getStatus() == 201) {
			return result.getHeaderString("Location");
		} else
			return result.getStatus() + "";
	}

	public static boolean adicionarPropiedades(String rutaNodo,
			String propiedades) throws BDException {
		if (!BDUtils.servidorActivo())
			throw new BDException();

		ResteasyClient cliente = BDUtils.obtenerCliente();
		WebTarget target = cliente.target(rutaNodo).path("/properties");
		Builder resultBuilder = target.request().accept(
				MediaType.APPLICATION_JSON + "; charset=UTF-8");
		Response result;
		result = resultBuilder.put(
				Entity.entity(propiedades, MediaType.APPLICATION_JSON),
				Response.class);
		return result.getStatus() == 204;

	}

	public static boolean adicionarLabel(String rutaNodo, String label)
			throws BDException {
		if (!BDUtils.servidorActivo())
			throw new BDException();
		ResteasyClient cliente = BDUtils.obtenerCliente();
		WebTarget target = cliente.target(rutaNodo).path("/labels");
		Builder resultBuilder = target.request().accept(
				MediaType.APPLICATION_JSON + "; charset=UTF-8");
		Response result;
		result = resultBuilder.post(
				Entity.entity(label, MediaType.APPLICATION_JSON),
				Response.class);
		return result.getStatus() == 204;
	}

	public static boolean crearRelacion(String rutaNodo, String propiedades) throws BDException {
		if (!BDUtils.servidorActivo())
			throw new BDException();

		ResteasyClient cliente = BDUtils.obtenerCliente();
		WebTarget target = cliente.target(rutaNodo)
				.path("/relationships");
		Builder resultBuilder = target.request().accept(
				MediaType.APPLICATION_JSON + "; charset=UTF-8");
		Response result;
		result = resultBuilder.post(
				Entity.entity(propiedades, MediaType.APPLICATION_JSON),
				Response.class);
		return result.getStatus() == 201;
	}

	//SE PODRIA AGREGAR UN IGNORE
	public static String condicionWhere(ObjectSNSDeportivo objetoRedSocial,
										String identificador){
		StringBuilder retorno = new StringBuilder("");
		JsonObject objetoJson = Utils.JsonStringToObject(objetoRedSocial.stringJson());
		for(String propiedad:objetoJson.getPropiedades().keySet()){
			retorno.append(identificador);
			retorno.append(".");
			retorno.append(propiedad);
			retorno.append(" = ");
			retorno.append(objetoJson.getPropiedades().get(propiedad));
			retorno.append(" AND ");
		}
		
		if(retorno.length() > 4)
			retorno.delete(retorno.length() - 4, retorno.length() - 1);
		
		return retorno.toString();
	}
	
	//SE PODRIA TENER UN ARREGLO DE IGNORE
	public static String producirSET(ObjectSNSDeportivo objetoRedSocial,
								     String identificador){
		StringBuilder retorno = new StringBuilder("SET ");
		retorno.append(identificador);
		retorno.append("+=");
		retorno.append("{");
		JsonObject objetoJson = Utils.JsonStringToObject(objetoRedSocial.stringJson());
		for(String propiedad:objetoJson.getPropiedades().keySet()){
			retorno.append(propiedad);
			retorno.append(":");
			retorno.append(objetoJson.getPropiedades().get(propiedad));
			retorno.append(",");
		}
		retorno.delete(retorno.length() - 2, retorno.length() - 1);
		retorno.append("}");
		return retorno.toString();
	}
	
}
