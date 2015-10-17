package com.sna_deportivo.services.ubicaciones;

import com.sna_deportivo.pojo.ubicacion.Ciudad;
import com.sna_deportivo.pojo.ubicacion.Pais;
import com.sna_deportivo.utils.gr.ResponseGenerico;
import com.sna_deportivo.pojo.ubicacion.LugarPractica;
import com.sna_deportivo.utils.gr.Constantes;
import com.sna_deportivo.utils.bd.BDUtils;
import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.Relaciones;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.json.JsonObject;

public class GestionUbicacion {
	
	public Pais[] obtenerPaises(){
		Pais[] paises = null;
		String query = "MATCH (n:" + Entidades.PAIS + ") RETURN n ORDER BY n.id";
		Object[] data = BDUtils.ejecutarQuery(query);
		paises = new Pais[data.length];
		if (!data[0].equals(""))
			for (int i = 0; i < data.length; i++) {
				JsonObject row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
				JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
				paises[i] = new Pais(arregloRow.getPropiedades());
			}
		return paises;
	}
	
	public Ciudad[] obtenerCiudadesPais(Pais pais){
		Ciudad[] ciudades = null;
		String query = "MATCH (c:" + Entidades.CIUDAD + ")<-[:" + Relaciones.PAISCIUDAD + "]-(p:" + Entidades.PAIS + " {id:" + pais.getId() + "}) RETURN c ORDER BY c.id";
		Object[] data = BDUtils.ejecutarQuery(query);
		System.out.println(data.length);
		ciudades = new Ciudad[data.length];
		if (!data[0].equals(""))
			for (int i = 0; i < data.length; i++) {
				JsonObject row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
				JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
				ciudades[i] = new Ciudad(arregloRow.getPropiedades());
		}
		return ciudades;
	}
	
	public LugarPractica[] obtenerLugaresPractica(Ciudad ciudad){
		LugarPractica[] lugares = null;
		String query = "MATCH (c:" + Entidades.CIUDAD + "  {id:" + ciudad.getId() + "})"
				+ "-[:" + Relaciones.COMPLETAUBICACION + "]->(u:" + Entidades.UBICACION + ")"
						+ "<-[:" + Relaciones.COMPLETAUBICACION + "]-(l:" + Entidades.LUGARPRACTICA + ") RETURN l ORDER BY l.nombre";
		Object[] data = BDUtils.ejecutarQuery(query);
		lugares = new LugarPractica[data.length];
		if (!data[0].equals(""))
			for (int i = 0; i < data.length; i++) {
				JsonObject row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
				JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
				lugares[i] = new LugarPractica(arregloRow.getPropiedades());
			}
		return lugares;
	}
	
	public LugarPractica[] obtenerLugaresPractica(){
		LugarPractica[] lugares = null;
		String query = "MATCH (l:" + Entidades.LUGARPRACTICA + ") RETURN l ORDER BY l.nombre";
		Object[] data = BDUtils.ejecutarQuery(query);
		lugares = new LugarPractica[data.length];
		if (!data[0].equals(""))
			for (int i = 0; i < data.length; i++) {
				JsonObject row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
				JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
				lugares[i] = new LugarPractica(arregloRow.getPropiedades());
			}
		return lugares;
	}
	
	public ResponseGenerico crearLugarPractica(Pais pais, Ciudad ciudad, LugarPractica lugar){
		
		String nodoUbicacion;
		String nodoLugarPractica;
		String idPais;
		String idCiudad;
		String query;
		
		StringBuilder propiedades;
		
		Object[] data;
		JsonObject row;
		
		ResponseGenerico response = new ResponseGenerico();
		
		//Crear nodo E_Ubicacion
		nodoUbicacion = BDUtils.crearNodo();
		// Asignar label a nodo
		if (!BDUtils.adicionarLabel(nodoUbicacion, "\"" + Entidades.UBICACION + "\""))
			throw new BDException();
		//Crear nodo E_LugarPratica
		nodoLugarPractica = BDUtils.crearNodo();
		// Asignar label a nodo
		if (!BDUtils.adicionarLabel(nodoLugarPractica, "\"" + Entidades.LUGARPRACTICA + "\""))
			throw new BDException();
		//Asignar propiedades a nodo
		if (!BDUtils.adicionarPropiedades(nodoLugarPractica, lugar.toString()))
			throw new BDException();
		//Obtener ID Pais
		query = "MATCH (n:" + Entidades.PAIS + "{id:" + pais.getId() + "}) RETURN id(n)";
		data = BDUtils.ejecutarQuery(query);
		row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		idPais = (String) row.getPropiedades().get("arreglo")[0];
		//Obtener ID Ciudad
		query = "MATCH (n:" + Entidades.CIUDAD + "{id:" + pais.getId() + "}) RETURN id(n)";
		data = BDUtils.ejecutarQuery(query);
		row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		idCiudad = (String) row.getPropiedades().get("arreglo")[0];
		//Crear relacion entre E_Pais, E_Ciudad y E_LugarPractica con E_Ubicacion
		propiedades = new StringBuilder("{");
		propiedades.append("\"to\":\"" + Constantes.SERVER_ROOT_URI + "/node/" + nodoUbicacion + "\",");
		propiedades.append("\"type\":\"" + Relaciones.COMPLETAUBICACION + "\"");
		propiedades.append("}");
		
		if(!BDUtils.crearRelacion(Constantes.SERVER_ROOT_URI + "/node/" + idPais, propiedades.toString()))
			throw new BDException();
		if(!BDUtils.crearRelacion(Constantes.SERVER_ROOT_URI + "/node/" + idCiudad, propiedades.toString()))
			throw new BDException();
		if(!BDUtils.crearRelacion(nodoLugarPractica, propiedades.toString()))
			throw new BDException();
		
		//Actualizar ID de E_LugarPractica y E_Ubicacion
		String[] nodoLugarDesc = nodoLugarPractica.split("/");
		String[] nodoUbicacionDesc = nodoUbicacion.split("/");
		query = "MERGE (s:E_Secuencia {nombre:'SQ_LugarPractica'}) "
				+ "ON CREATE set s.actual = 1 "
				+ "ON MATCH set s.actual = s.actual + 1 "
				+ "WITH s.actual AS consecutivo "
				+ "MATCH (n) where id(n) = " + nodoLugarDesc[nodoLugarDesc.length-1] + " set n.id=consecutivo";
		
		BDUtils.ejecutarQuery(query);
		
		query = "MERGE (s:E_Secuencia {nombre:'SQ_Ubicacion'}) "
				+ "ON CREATE set s.actual = 1 "
				+ "ON MATCH set s.actual = s.actual + 1 "
				+ "WITH s.actual AS consecutivo "
				+ "MATCH (n) where id(n) = " + nodoUbicacionDesc[nodoUbicacionDesc.length-1] + " set n.id=consecutivo";
		
		BDUtils.ejecutarQuery(query);
		
		response.setCaracterAceptacion("B");
		response.setMensajeRespuesta("Lugar de práctica creado exitosamente");
		response.setDatosExtra(nodoLugarPractica);
		return response;
	}

}
