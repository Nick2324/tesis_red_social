package com.sna_deportivo.services.ubicaciones;

import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.deportes.DeportePracticadoUbicacion;
import com.sna_deportivo.pojo.evento.Evento;
import com.sna_deportivo.pojo.ubicacion.Ciudad;
import com.sna_deportivo.pojo.ubicacion.Pais;
import com.sna_deportivo.pojo.ubicacion.Ubicacion;
import com.sna_deportivo.pojo.ubicacion.UbicacionDAO;
import com.sna_deportivo.utils.gr.ResponseGenerico;
import com.sna_deportivo.utils.gr.excepciones.ProductorFactoryExcepcion;
import com.sna_deportivo.pojo.ubicacion.LugarPractica;
import com.sna_deportivo.utils.gr.Constantes;
import com.sna_deportivo.utils.bd.BDUtils;
import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.Relaciones;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.json.JsonObject;
import com.sna_deportivo.utils.json.excepciones.ExcepcionJsonDeserializacion;

public class GestionUbicacion {

	public Pais[] obtenerPaises() {
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

	public Ciudad[] obtenerCiudadesPais(Pais pais) {
		Ciudad[] ciudades = null;
		String query = "MATCH (c:" + Entidades.CIUDAD + ")<-[:" + Relaciones.PAISCIUDAD + "]-(p:" + Entidades.PAIS
				+ " {id:" + pais.getId() + "}) RETURN c ORDER BY c.id";
		Object[] data = BDUtils.ejecutarQuery(query);
		ciudades = new Ciudad[data.length];
		if (!data[0].equals(""))
			for (int i = 0; i < data.length; i++) {
				JsonObject row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
				JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
				ciudades[i] = new Ciudad(arregloRow.getPropiedades());
			}
		return ciudades;
	}

	public LugarPractica[] obtenerLugaresPractica(Ciudad ciudad) {
		LugarPractica[] lugares = null;
		String query = "MATCH (c:" + Entidades.CIUDAD + "  {id:" + ciudad.getId() + "})" + "-[:"
				+ Relaciones.COMPLETAUBICACION + "]->(u:" + Entidades.UBICACION + ")" + "<-[:"
				+ Relaciones.COMPLETAUBICACION + "]-(l:" + Entidades.LUGARPRACTICA + ") RETURN l ORDER BY l.nombre";
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

	public LugarPractica[] obtenerLugaresPractica() {
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

	public Deporte[] obtenerDeportesPracticadosLugar(LugarPractica lugar) {
		Deporte[] deportes = null;

		String query = "MATCH (l:" + Entidades.LUGARPRACTICA + " {id:" + lugar.getId() + "}) -[:"
				+ Relaciones.COMPLETAUBICACION + "]->" + "(u:" + Entidades.UBICACION + ") -[:" + Relaciones.PRACTICADOEN
				+ "]->" + "(ud:" + Entidades.UBICACIONDEPORTE + ")," + "(d:" + Entidades.DEPORTE + ") -[:"
				+ Relaciones.PRACTICADOEN + "]-> (ud) RETURN d";

		Object[] data = BDUtils.ejecutarQuery(query);
		deportes = new Deporte[data.length];
		if (!data[0].equals(""))
			for (int i = 0; i < data.length; i++) {
				JsonObject row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
				JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
				deportes[i] = new Deporte(arregloRow);
			}
		return deportes;
	}

	public LugarPractica obtenerUbicacionEvento(Evento evento) {
		LugarPractica lugares = null;
		String query = "MATCH (l:" + Entidades.LUGARPRACTICA + ") -[:" + Relaciones.COMPLETAUBICACION + "]->" + "(u:"
				+ Entidades.UBICACION + ") -[:" + Relaciones.DESCRIPCIONEVENTO + "]->(de:" + Entidades.DEPORTEEVENTO
				+ ") <-[:" + Relaciones.DESCRIPCIONEVENTO + "]-" + "(ud:" + Entidades.EVENTODEPORTIVO + " {id:'"
				+ evento.getId() + "'})" + "  RETURN l";
		Object[] data = BDUtils.ejecutarQuery(query);
		lugares = new LugarPractica();
		if (!data[0].equals("")) {
			JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
			JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
			lugares = new LugarPractica(arregloRow.getPropiedades());
		}
		return lugares;
	}

	public ResponseGenerico crearLugarPractica(Pais pais, Ciudad ciudad, LugarPractica lugar) {

		String nodoUbicacion;
		String nodoLugarPractica;
		String idPais;
		String idCiudad;
		String query;

		StringBuilder propiedades;

		Object[] data;
		JsonObject row;

		ResponseGenerico response = new ResponseGenerico();

		// Crear nodo E_Ubicacion
		nodoUbicacion = BDUtils.crearNodo();
		// Asignar label a nodo
		if (!BDUtils.adicionarLabel(nodoUbicacion, "\"" + Entidades.UBICACION + "\""))
			throw new BDException();
		// Crear nodo E_LugarPratica
		nodoLugarPractica = BDUtils.crearNodo();
		// Asignar label a nodo
		if (!BDUtils.adicionarLabel(nodoLugarPractica, "\"" + Entidades.LUGARPRACTICA + "\""))
			throw new BDException();
		// Asignar propiedades a nodo
		if (!BDUtils.adicionarPropiedades(nodoLugarPractica, lugar.toString()))
			throw new BDException();
		// Obtener ID Pais
		query = "MATCH (n:" + Entidades.PAIS + "{id:" + pais.getId() + "}) RETURN id(n)";
		data = BDUtils.ejecutarQuery(query);
		row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		idPais = (String) row.getPropiedades().get("arreglo")[0];
		// Obtener ID Ciudad
		query = "MATCH (n:" + Entidades.CIUDAD + "{id:" + pais.getId() + "}) RETURN id(n)";
		data = BDUtils.ejecutarQuery(query);
		row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		idCiudad = (String) row.getPropiedades().get("arreglo")[0];
		// Crear relacion entre E_Pais, E_Ciudad y E_LugarPractica con
		// E_Ubicacion
		propiedades = new StringBuilder("{");
		propiedades.append("\"to\":\"" + Constantes.SERVER_ROOT_URI + "/node/" + nodoUbicacion + "\",");
		propiedades.append("\"type\":\"" + Relaciones.COMPLETAUBICACION + "\"");
		propiedades.append("}");

		if (!BDUtils.crearRelacion(Constantes.SERVER_ROOT_URI + "/node/" + idPais, propiedades.toString()))
			throw new BDException();
		if (!BDUtils.crearRelacion(Constantes.SERVER_ROOT_URI + "/node/" + idCiudad, propiedades.toString()))
			throw new BDException();
		if (!BDUtils.crearRelacion(nodoLugarPractica, propiedades.toString()))
			throw new BDException();

		// Actualizar ID de E_LugarPractica y E_Ubicacion
		String[] nodoLugarDesc = nodoLugarPractica.split("/");
		String[] nodoUbicacionDesc = nodoUbicacion.split("/");
		query = "MERGE (s:E_Secuencia {nombre:'SQ_LugarPractica'}) " + "ON CREATE set s.actual = 1 "
				+ "ON MATCH set s.actual = s.actual + 1 " + "WITH s.actual AS consecutivo " + "MATCH (n) where id(n) = "
				+ nodoLugarDesc[nodoLugarDesc.length - 1] + " set n.id=consecutivo";

		BDUtils.ejecutarQuery(query);

		query = "MERGE (s:E_Secuencia {nombre:'SQ_Ubicacion'}) " + "ON CREATE set s.actual = 1 "
				+ "ON MATCH set s.actual = s.actual + 1 " + "WITH s.actual AS consecutivo " + "MATCH (n) where id(n) = "
				+ nodoUbicacionDesc[nodoUbicacionDesc.length - 1] + " set n.id=consecutivo";

		BDUtils.ejecutarQuery(query);

		response.setCaracterAceptacion("B");
		response.setMensajeRespuesta("Lugar de práctica creado exitosamente");
		response.setDatosExtra(nodoLugarPractica);
		return response;
	}

	public ResponseGenerico asociarDeporteLugar(DeportePracticadoUbicacion deporte) {
		String nodoUbicacionDeporte;
		String idDeporte;
		String idUbicacion;
		String query;

		StringBuilder propiedades;

		int idLugar;

		Object[] data;
		JsonObject row;

		ResponseGenerico response = new ResponseGenerico();

		// Crear nodo UbicacionDeporte
		nodoUbicacionDeporte = BDUtils.crearNodo();
		// Asignar Label al nodo
		if (!BDUtils.adicionarLabel(nodoUbicacionDeporte, "\"" + Entidades.UBICACIONDEPORTE + "\""))
			throw new BDException();

		propiedades = new StringBuilder("{");
		propiedades.append("\"to\":\"" + Constantes.SERVER_ROOT_URI + "/node/" + nodoUbicacionDeporte + "\",");
		propiedades.append("\"type\":\"" + Relaciones.PRACTICADOEN + "\"");
		propiedades.append("}");

		// Crear relacion entre nodo Deporte y nodo UbicacionDeporte
		// obtener ID nodo Deporte
		query = "MATCH (n:" + Entidades.DEPORTE + "{id:" + deporte.getDeporte().getId() + "}) RETURN id(n)";
		data = BDUtils.ejecutarQuery(query);
		row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		idDeporte = (String) row.getPropiedades().get("arreglo")[0];

		if (!BDUtils.crearRelacion(Constantes.SERVER_ROOT_URI + "/node/" + idDeporte, propiedades.toString()))
			throw new BDException();

		// Crear relacion entre nodo Ubicacon y nodo UbicacionDeporte
		// obtener ID nodo Ubicacion
		idLugar = deporte.getUbicacion().getLugar().getId();
		if (idLugar < 0) {
			query = "MATCH (n:" + Entidades.LUGARPRACTICA + ") where id(n)=" + (idLugar * -1) + " RETURN n.id";
			data = BDUtils.ejecutarQuery(query);
			row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
			idLugar = Integer.valueOf((String) row.getPropiedades().get("arreglo")[0]);
		}
		query = "MATCH (n:" + Entidades.UBICACION + ")<-[:" + Relaciones.COMPLETAUBICACION + "]-(p:" + Entidades.PAIS
				+ " {id:" + deporte.getUbicacion().getPais().getId() + "})," + "(n)<-[:" + Relaciones.COMPLETAUBICACION
				+ "]-(c:" + Entidades.CIUDAD + "{id:" + deporte.getUbicacion().getCiudad().getId() + "})," + "(n)<-[:"
				+ Relaciones.COMPLETAUBICACION + "]-(l:" + Entidades.LUGARPRACTICA + " {id:" + idLugar
				+ "}) RETURN id(n)";
		data = BDUtils.ejecutarQuery(query);
		row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		idUbicacion = (String) row.getPropiedades().get("arreglo")[0];

		if (!BDUtils.crearRelacion(Constantes.SERVER_ROOT_URI + "/node/" + idUbicacion, propiedades.toString()))
			throw new BDException();

		response.setCaracterAceptacion("B");
		response.setMensajeRespuesta("Deporte asociado exitosamente");
		response.setDatosExtra(nodoUbicacionDeporte);

		return response;
	}

	public Evento[] getEventosDB() {
		Evento[] eventos = null;
		String query = "MATCH (e:" + Entidades.EVENTODEPORTIVO + ") RETURN e";
		try {
			Object[] data = BDUtils.ejecutarQuery(query.toString());
			eventos = new Evento[data.length];

			Evento evento;

			if (!data[0].equals("")) {
				for (int i = 0; i < data.length; i++) {
					JsonObject row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
					JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
					evento = new Evento();
					eventos[i].deserializarJson(arregloRow);
				}
			}
		} catch (BDException e) {
			eventos = null;
		} catch (ExcepcionJsonDeserializacion e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			eventos = null;
		}
		return eventos;
	}

	public Ubicacion[] obtenerUbicaciones(Ubicacion body)
			throws ProductorFactoryExcepcion, ExcepcionJsonDeserializacion {
		UbicacionDAO du = new UbicacionDAO();
		if (body.getCiudad() == null) {
			body.setCiudad(new Ciudad());
		}
		if (body.getPais() == null) {
			body.setPais(new Pais());
		}
		if (body.getLugar() == null) {
			body.setLugar(new LugarPractica());
		}
		du.setObjetcSNSDeportivo(body);
		return (Ubicacion[]) du.getUbicaciones();

	}

}
