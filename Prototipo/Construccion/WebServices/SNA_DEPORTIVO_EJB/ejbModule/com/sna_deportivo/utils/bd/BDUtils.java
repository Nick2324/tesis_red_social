package com.sna_deportivo.utils.bd;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.util.Base64;

import com.sna_deportivo.utils.Constantes;
import com.sna_deportivo.utils.ObjectSNSDeportivo;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.JsonUtils;

public class BDUtils {

	private static String PATH = "transaction/commit";

	public static boolean servidorActivo() {
		ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
		ResteasyProviderFactory.pushContext(javax.ws.rs.ext.Providers.class,
				factory);
		ResteasyClient client = new ResteasyClientBuilder().build();
		WebTarget target = client.target(Constantes.SERVER_ROOT_URI).path("/");
		Response result = target.request()
				//nicolas2324:mifamilia
				//neo4j:21316789
				.header("Authorization", "Basic " + Base64.encodeBytes("nicolas2324:mifamilia".getBytes()))
				.accept(MediaType.TEXT_PLAIN)
				.get(Response.class);
		System.out.println("Status BD: "+result.getStatus());
		return result.getStatus() == 200;
	}

	public static ResteasyClient obtenerCliente() {
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
		ResteasyProviderFactory.pushContext(javax.ws.rs.ext.Providers.class,
				factory);

		return new ResteasyClientBuilder().build();
	}
	
	private static Object[] abstractoQuery(String query,
										 String payload
										 )throws BDException{
		String stringRespuesta;
		if (!BDUtils.servidorActivo())
			throw new BDException();
		else {
			System.out.println("Query ejecutado: " + query);
			ResteasyClient cliente = BDUtils.obtenerCliente();
			WebTarget target = cliente.target(Constantes.SERVER_ROOT_URI).path(
					PATH);
			Response result = target
					.request()
					.header("Authorization", "Basic " + Base64.encodeBytes("neo4j:21316789".getBytes()))
					.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8")
					.post(Entity.entity(payload, MediaType.APPLICATION_JSON),
							Response.class);
			System.out.println("Codigo de retorno: " + result.getStatus());
			if (result.getStatus() == 200) {
				stringRespuesta = result.readEntity(String.class);
				System.out.println("Resultado: " + stringRespuesta);
				JsonObject resultado = JsonUtils
						.JsonStringToObject(stringRespuesta);
				JsonObject results = (JsonObject) resultado.getPropiedades()
						.get("results")[0];
				JsonObject arregloData = (JsonObject) results.getPropiedades()
						.get("arreglo")[0];
				JsonObject data;
					data = (JsonObject) arregloData.getPropiedades()
						.get("data")[0];
				Object[] contenidoData = data.getPropiedades().get("arreglo");

				return contenidoData;
			} else{
				throw new BDException();
			}
		}
	}
	
	public static Object[] ejecutarQueryREST(String query) throws BDException{
		return BDUtils.abstractoQuery(query,
				"{\"statements\": "+
					"[{\"statement\" :\"" + query + "\","+
					  "\"resultDataContents\":[\"REST\"]"+
					 "}"+
					"]"+
				"}");
	}
	
	public static Object[] ejecutarQuery(String query) throws BDException {
		return BDUtils.abstractoQuery(query,
					"{\"statements\":[{\"statement\":\"" + query
					+ "\"}]}");
	}

	public static String crearNodo() throws BDException {
		if (!BDUtils.servidorActivo())
			throw new BDException();

		ResteasyClient cliente = BDUtils.obtenerCliente();
		WebTarget target = cliente.target(Constantes.SERVER_ROOT_URI).path(
				"/node");
		Builder resultBuilder = target.request()
				.header("Authorization", "Basic " + Base64.encodeBytes("neo4j:21316789".getBytes()))
				.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8");
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
		Builder resultBuilder = target.request()
				.header("Authorization", "Basic " + Base64.encodeBytes("neo4j:21316789".getBytes()))
				.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8");
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
		Builder resultBuilder = target.request()
				.header("Authorization", "Basic " + Base64.encodeBytes("neo4j:21316789".getBytes()))
				.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8");
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
		Builder resultBuilder = target.request()
				.header("Authorization", "Basic " + Base64.encodeBytes("neo4j:21316789".getBytes()))
				.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8");
		Response result;
		result = resultBuilder.post(
				Entity.entity(propiedades, MediaType.APPLICATION_JSON),
				Response.class);

		System.out.println(rutaNodo + "/relationships");
		return result.getStatus() == 201;
	}

	public static boolean eliminarRelacion(String relationshipId){
		if (!BDUtils.servidorActivo())
			throw new BDException();
		ResteasyClient cliente = BDUtils.obtenerCliente();
		WebTarget target = cliente.target(Constantes.SERVER_ROOT_URI).path("/relationship/" + relationshipId);
		Builder resultBuilder = target.request()
				.header("Authorization", "Basic " + Base64.encodeBytes("neo4j:21316789".getBytes()))
				.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8");
		Response result;
		result = resultBuilder.delete(
				Response.class);
		return result.getStatus() == 204;
	}
	public static boolean eliminarNodo(String nodeId){
		if (!BDUtils.servidorActivo())
			throw new BDException();
		ResteasyClient cliente = BDUtils.obtenerCliente();
		WebTarget target = cliente.target(Constantes.SERVER_ROOT_URI).path("/node/" + nodeId);
		Builder resultBuilder = target.request()
				.header("Authorization", "Basic " + Base64.encodeBytes("neo4j:21316789".getBytes()))
				.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8");
		Response result;
		result = resultBuilder.delete(
				Response.class);
		return result.getStatus() == 204;
	}
	
	public static String[] obtenerRelacionesNodo(String nodeId){
		if (!BDUtils.servidorActivo())
			throw new BDException();
		ResteasyClient cliente = BDUtils.obtenerCliente();
		WebTarget target = cliente.target(Constantes.SERVER_ROOT_URI).path("/node/" + nodeId + "/relationships/all");
		Builder resultBuilder = target.request()
				.header("Authorization", "Basic " + Base64.encodeBytes("neo4j:21316789".getBytes()))
				.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8");
		Response result;
		result = resultBuilder.get(
				Response.class);
		String stringRespuesta;
		stringRespuesta = result.readEntity(String.class);
		System.out.println("Resultado: " + stringRespuesta);
		JsonObject resultado = JsonUtils
				.JsonStringToObject(stringRespuesta);
		Object[] results = (Object[]) resultado.getPropiedades()
				.get("arreglo");
		JsonObject relacion;
		String relations[] = new String[results.length];
		for(int i=0;i<results.length;i++){
			relacion = (JsonObject)results[i];
			relations[i] =  (String) ((JsonObject)relacion.getPropiedades().get("metadata")[0]).getPropiedades().get("id")[0];
			
		}
		return relations;
	}
	
	//SE PODRIA AGREGAR UN IGNORE
	public static String condicionWhere(ObjectSNSDeportivo objetoRedSocial,
										String identificador){
		StringBuilder retorno = new StringBuilder(" WHERE ");
		if(objetoRedSocial != null && identificador != null){
			JsonObject objetoJson = JsonUtils.JsonStringToObject(objetoRedSocial.stringJson());
			for(String propiedad:objetoJson.getPropiedades().keySet()){
				if(!(((String)objetoJson.getPropiedades().get(propiedad)[0]).equals("null"))){
					retorno.append(identificador);
					retorno.append(".");
					retorno.append(propiedad);
					retorno.append(" = ");
					retorno.append(objetoJson.getPropiedades().get(propiedad)[0]);
					retorno.append(" AND ");
				}
			}
		}
		if(retorno.length() > 7)
			retorno.delete(retorno.length() - 4, retorno.length());
		if(!retorno.toString().equals(" WHERE "))
			return retorno.toString();
		else
			return null;
	}
	
	//SE PODRIA TENER UN ARREGLO DE IGNORE
	public static String producirSET(ObjectSNSDeportivo objetoRedSocial,
								     String identificador){
		StringBuilder retorno = new StringBuilder("SET ");
		retorno.append(identificador);
		retorno.append("+=");
		retorno.append("{");
		
		JsonObject objetoJson = JsonUtils.JsonStringToObject(objetoRedSocial.stringJson());
		for(String propiedad:objetoJson.getPropiedades().keySet()){
			retorno.append(propiedad);
			retorno.append(":");
			retorno.append(objetoJson.getPropiedades().get(propiedad)[0]);
			retorno.append(",");
		}
		retorno.delete(retorno.length() - 1, retorno.length());
		retorno.append("}");
		
		return retorno.toString();
	}
	
	public static JsonObject obtenerRestRegistro(Object registroResultadoRest){
		JsonObject registro = (JsonObject)registroResultadoRest;
		JsonObject rest = (JsonObject)registro.getPropiedades().get("rest")[0];
		JsonObject restArreglo = (JsonObject)rest.getPropiedades().get("arreglo")[0];
		return restArreglo;
	}
	
}
