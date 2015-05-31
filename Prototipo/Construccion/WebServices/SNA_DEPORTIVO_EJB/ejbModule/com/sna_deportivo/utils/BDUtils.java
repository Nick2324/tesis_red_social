package com.sna_deportivo.utils;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.sna_deportivo.pojo.JsonObject;

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

	public static Object[] EjecutarQuery(String query) throws BDException {
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

	public static String CrearNodo() throws BDException {
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

	public static boolean AdicionarPropiedades(String rutaNodo,
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

	public static boolean AdicionarLabel(String rutaNodo, String label)
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

	public static boolean CrearRelacion(String rutaNodo, String propiedades) throws BDException {
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

}
