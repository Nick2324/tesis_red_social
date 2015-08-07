package com.sna_deportivo.services.usuarios;

import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.deportes.DeportePracticado;
import com.sna_deportivo.pojo.deportes.PosicionDeporte;
import com.sna_deportivo.pojo.usuarios.Permiso;
import com.sna_deportivo.pojo.usuarios.ResponseGenerico;
import com.sna_deportivo.pojo.usuarios.Rol;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.pojo.usuarios.excepciones.CredentialsException;
import com.sna_deportivo.utils.Constantes;
import com.sna_deportivo.utils.bd.BDUtils;
import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.Relaciones;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.json.JsonObject;

public class GestionUsuario {

	public ResponseGenerico crearUsuario(Usuario user) throws BDException {
		ResponseGenerico response = new ResponseGenerico();
		// Crear el nodo
		String nodo = BDUtils.crearNodo();
		// Asignar propiedades al nodo
		if (!BDUtils.adicionarPropiedades(nodo, user.toString()))
			throw new BDException();
		// Asignar label a nodo
		if (!BDUtils.adicionarLabel(nodo, "\"" + Entidades.USUARIO + "\""))
			throw new BDException();
		// Crear relacion con nodo Rol
		// Obtener id de rol seleccionado
		String queryIdRol = "MATCH (n:" + Entidades.ROL + "{nombre:'" + user.getTipoUsuario() + "'}) RETURN id(n)";
		Object[] data = BDUtils.ejecutarQuery(queryIdRol);
		JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		String idRol = (String) row.getPropiedades().get("arreglo")[0];
		StringBuilder propiedades = new StringBuilder("{");
		propiedades.append("\"to\":\"" + Constantes.SERVER_ROOT_URI + "/node/" + idRol + "\",");
		propiedades.append("\"type\":\"" + Relaciones.ASUMEROL + "\"");
		propiedades.append("}");
		// Invocar Creación de la relación
		if (!BDUtils.crearRelacion(nodo, propiedades.toString()))
			throw new BDException();
		response.setCaracterAceptacion("B");
		response.setMensajeRespuesta("Usuario creado exitosamente");
		response.setDatosExtra(nodo);
		return response;
	}

	public ResponseGenerico acualizarDatosUsuario(Usuario user) throws BDException {
		ResponseGenerico response = new ResponseGenerico();
		StringBuilder query = new StringBuilder();
		StringBuilder propiedades;
		Object[] data;
		String rolActual;
		String relationshipId;
		String newRoleId;
		String userId;
		
		//obtener id usuario
		query = new StringBuilder();
		query.append("MATCH (u:");
		query.append(Entidades.USUARIO);
		query.append(" {usuario:'");
		query.append(user.getUsuario());
		query.append("'}) RETURN id(u)");
		
		data = BDUtils.ejecutarQuery(query.toString());
		userId = (String) ((JsonObject)(((JsonObject)data[0]).getPropiedades().get("row")[0])).getPropiedades().get("arreglo")[0];
		
		// Verificar si el usuario cambió de rol
		query = new StringBuilder();
		query.append("MATCH (u:");
		query.append(Entidades.USUARIO);
		query.append(" {usuario:'");
		query.append(user.getUsuario());
		query.append("'}) -[:");
		query.append(Relaciones.ASUMEROL);
		query.append("]->(r:");
		query.append(Entidades.ROL);
		query.append(") return r.nombre");
		
		data = BDUtils.ejecutarQuery(query.toString());
		rolActual = (String) ((JsonObject)(((JsonObject)data[0]).getPropiedades().get("row")[0])).getPropiedades().get("arreglo")[0];
		if(!user.getTipoUsuario().equals(rolActual)){
			query = new StringBuilder();
			query.append("MATCH (u:");
			query.append(Entidades.USUARIO);
			query.append(" {usuario:'");
			query.append(user.getUsuario());
			query.append("'}) -[r:");
			query.append(Relaciones.ASUMEROL);
			query.append("]->(:");
			query.append(Entidades.ROL);
			query.append(") return id(r)");
			
			data = BDUtils.ejecutarQuery(query.toString());
			relationshipId = (String) ((JsonObject)(((JsonObject)data[0]).getPropiedades().get("row")[0])).getPropiedades().get("arreglo")[0];
			
			// Eliminar la relación con el rol anterior

			BDUtils.eliminarRelacion(relationshipId);
			
			
			//obtener id nuevo rol
			query = new StringBuilder();
			query.append("MATCH (r:");
			query.append(Entidades.ROL);
			query.append(" {nombre:'");
			query.append(user.getTipoUsuario());
			query.append("'}) return id(r)");
			
			data = BDUtils.ejecutarQuery(query.toString());
			newRoleId = (String) ((JsonObject)(((JsonObject)data[0]).getPropiedades().get("row")[0])).getPropiedades().get("arreglo")[0];
			
			// Crear la relacion con el nuevo rol
			
			propiedades = new StringBuilder("{");
			propiedades.append("\"to\":\"" + Constantes.SERVER_ROOT_URI + "/node/" + newRoleId + "\",");
			propiedades.append("\"type\":\"" + Relaciones.ASUMEROL + "\"");
			propiedades.append("}");
			
			
			BDUtils.crearRelacion(Constantes.SERVER_ROOT_URI + "/node/" + userId, propiedades.toString());
			
		}
		// Actualizar los datos del usuario
		
		BDUtils.adicionarPropiedades(Constantes.SERVER_ROOT_URI + "/node/" + userId, user.toString());

		response.setCaracterAceptacion("B");
		response.setMensajeRespuesta("Usuario actualizado exitosamente");
		return response;
	}

	public ResponseGenerico verificarUsuario(String usuario, String pass) throws BDException, CredentialsException {
		ResponseGenerico response = null;
		String query = "MATCH (u:" + Entidades.USUARIO + " {usuario:'" + usuario + "',contrasena:'" + pass
				+ "'}) return u";
		Object[] data = BDUtils.ejecutarQuery(query);
		JsonObject user;
		System.out.println(data[0].getClass());
		if (data[0].getClass() == JsonObject.class) {
			JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
			user = (JsonObject) row.getPropiedades().get("arreglo")[0];
		} else
			user = (JsonObject) data[0];
		if (user != null) {

			response = new ResponseGenerico();
			response.setCaracterAceptacion("B");
			response.setMensajeRespuesta("Credenciales correctas");
			response.setDatosExtra(new Usuario(user).toString());
			System.out.println(response.getDatosExtra());
		} else
			throw new CredentialsException();
		return response;
	}

	public Rol[] obtenerRoles() throws BDException {
		Rol[] roles = null;
		String query = "MATCH (n:" + Entidades.ROL + ") RETURN n ORDER BY n.consecutivoRol";
		Object[] data = BDUtils.ejecutarQuery(query);
		roles = new Rol[data.length];
		for (int i = 0; i < data.length; i++) {
			JsonObject row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
			JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
			roles[i] = new Rol(arregloRow.getPropiedades());
		}
		return roles;
	}

	public Usuario datosUsuario(String user) {
		String query = "MATCH (u:" + Entidades.USUARIO + " {usuario:'" + user + "'}) return u";
		Object[] data = BDUtils.ejecutarQuery(query);
		Usuario usuario;
		JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
		usuario = new Usuario(arregloRow);
		return usuario;
	}

	public boolean existeUsuario(Usuario user) throws BDException {
		String query = "MATCH (u:" + Entidades.USUARIO + " {usuario:'" + user.getUsuario() + "'}) return u.contrasena";
		Object[] data = BDUtils.ejecutarQuery(query);
		String usuario = null;
		if (data[0].getClass() == JsonObject.class) {
			JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
			usuario = (String) row.getPropiedades().get("arreglo")[0];
		} else
			usuario = (String) data[0];
		if (usuario.equals(""))
			return false;
		return true;
	}

	public Rol obtenerRolUsuario(String userName) {
		String query = "MATCH (:" + Entidades.USUARIO + " {usuario:'" + userName + "'}) -[:R_AsumeRol]->(n:"
				+ Entidades.ROL + ") return n";
		Object[] data = BDUtils.ejecutarQuery(query);
		Rol rol;
		JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
		rol = new Rol(arregloRow.getPropiedades());
		return rol;
	}

	public Permiso[] obtenerPermisosRol(Rol rol) {
		String query = "MATCH (n:" + Entidades.PERMISO + ") -[:R_Permiso]->(:" + Entidades.ROL + " {consecutivoRol:" + rol.getConsecutivoRol() + "}) return n";
		Object[] data = BDUtils.ejecutarQuery(query);
		Permiso[] permisos;
		permisos = new Permiso[data.length];
		JsonObject row;
		JsonObject arregloRow;
		for (int i = 0; i < data.length; i++) {
			if(!data[0].getClass().equals(String.class)){
				row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
				arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
				permisos[i] = new Permiso(arregloRow);
			}
		}
		return permisos;
	}

	public Permiso[] obtenerPermisosAsociados(Permiso permiso) {
		String query = "MATCH (n:" + Entidades.PERMISO + ") -[:R_Permiso]->(:" + Entidades.PERMISO
				+ " {consecutivoPermiso:" + permiso.getConsecutivoPermiso()
				+ "}) return n order by n.consecutivoPermiso asc";
		Object[] data = BDUtils.ejecutarQuery(query);
		Permiso[] permisos;
		permisos = new Permiso[data.length];
		JsonObject row;
		JsonObject arregloRow;
		for (int i = 0; i < data.length; i++) {
			row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
			arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
			permisos[i] = new Permiso(arregloRow);
		}
		return permisos;
	}

	public Deporte[] obtenerDeportesPracticados(String userName) {
		String query = "MATCH (n:" + Entidades.DEPORTE + ")<-[:" + Relaciones.PRACTICADEPORTE + "]-(:" + Entidades.USUARIO
				+ " {usuario:'" + userName
				+ "'}) return n order by n.id asc";
		Object[] data = BDUtils.ejecutarQuery(query);
		Deporte[] deportes;
		if(data[0].getClass() != String.class){
			deportes = new Deporte[data.length];
			JsonObject row;
			JsonObject arregloRow;
			for (int i = 0; i < data.length; i++) {
				row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
				arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
				deportes[i] = new Deporte(arregloRow);
			}
		}
		else
			deportes = null;
		return deportes;
	}
	
	public Deporte[] obtenerDeportes() {
		String query = "MATCH (n:" + Entidades.DEPORTE + ") return n order by n.id asc";
		Object[] data = BDUtils.ejecutarQuery(query);
		Deporte[] deportes;
		if(data[0].getClass() != String.class){
			deportes = new Deporte[data.length];
			JsonObject row;
			JsonObject arregloRow;
			for (int i = 0; i < data.length; i++) {
				row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
				arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
				deportes[i] = new Deporte(arregloRow);
			}
		}
		else
			deportes = null;
		return deportes;
	}
	
	public PosicionDeporte[] obtenerPosicionesDeporte(int idDeporte) {
		String query = "MATCH (n:" + Entidades.DEPORTE + "{id:" + idDeporte + "})-[:" + Relaciones.POSICIONDEPORTE + "]->(m:" + Entidades.POSICION + ") return m order by m.id asc";
		Object[] data = BDUtils.ejecutarQuery(query);
		PosicionDeporte[] posiciones;
		if(data[0].getClass() != String.class){
			posiciones = new PosicionDeporte[data.length];
			JsonObject row;
			JsonObject arregloRow;
			for (int i = 0; i < data.length; i++) {
				row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
				arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
				posiciones[i] = new PosicionDeporte(arregloRow);
			}
		}
		else
			posiciones = null;
		return posiciones;
	}
	
	public PosicionDeporte[] obtenerPosicionesDeportePracticado(int idDeporte, String userName) {
		String query = "MATCH (n:" + Entidades.DEPORTE + " {id:" + idDeporte + "})-[:" + Relaciones.POSICIONDEPORTE + "]->(m:" + Entidades.POSICION + "),"
						+ "(u:" + Entidades.USUARIO + " {usuario:'" + userName + "'})-[:" + Relaciones.PRACTICADEPORTE + "]->(n),"
						+ "(u)-[:" + Relaciones.USUARIOPOSICIONDEPORTE + "]->(pdp:" + Entidades.POSICIONUSUARIODEPORTE + "),"
						+ "(m)-[:" + Relaciones.USUARIOPOSICIONDEPORTE + "]->(pdp) return m order by m.id asc";
		Object[] data = BDUtils.ejecutarQuery(query);
		PosicionDeporte[] posiciones;
		if(data[0].getClass() != String.class){
			posiciones = new PosicionDeporte[data.length];
			JsonObject row;
			JsonObject arregloRow;
			for (int i = 0; i < data.length; i++) {
				row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
				arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
				posiciones[i] = new PosicionDeporte(arregloRow);
			}
		}
		else
			posiciones = null;
		return posiciones;
	}

	public ResponseGenerico adicionarDeportePracticado(DeportePracticado deportePracticado, String usuario) {
		ResponseGenerico response;
		//Verificar si el deporte no es practicado por el usuario
		String query = "MATCH (u:" + Entidades.USUARIO + " {usuario:'" + usuario + "'}) -[:" + Relaciones.PRACTICADEPORTE + "]->(d:" + Entidades.DEPORTE + " {id:" + deportePracticado.getDeporte().getId() + "}) return d.id";
		Object[] data = BDUtils.ejecutarQuery(query);
		if(data[0].getClass() == String.class){
			String idUsuario;
			String idDeporte;
			String nodoPosicionusuarioDeporte;
			String propiedades;
			
			JsonObject row;
			
			//Obtener nodo usuario
			query = "MATCH (u:" + Entidades.USUARIO + " {usuario:'" + usuario + "'}) RETURN id(u)";
			data = BDUtils.ejecutarQuery(query);
			row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
			idUsuario = (String) row.getPropiedades().get("arreglo")[0];
			//Obtener nodo deporte
			query = "MATCH (u:" + Entidades.DEPORTE + " {id:" + deportePracticado.getDeporte().getId() + "}) RETURN id(u)";
			data = BDUtils.ejecutarQuery(query);
			row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
			idDeporte = (String) row.getPropiedades().get("arreglo")[0];
			//Crear nodo PosicionUsuarioDeporte
			nodoPosicionusuarioDeporte = BDUtils.crearNodo();
			BDUtils.adicionarLabel(nodoPosicionusuarioDeporte, "\"" + Entidades.POSICIONUSUARIODEPORTE + "\"");
			//Crear relacion entre usuario y deporte
			propiedades = "{\"to\":\"" + Constantes.SERVER_ROOT_URI + "/node/" + idDeporte + "\"," + 
					      "\"type\":\"" + Relaciones.PRACTICADEPORTE + "\"}";
			BDUtils.crearRelacion(Constantes.SERVER_ROOT_URI + "/node/" + idUsuario,propiedades);
			//Crear relacion entre usuario y nodo PosicionUsuarioDeporte
			propiedades = "{\"to\":\"" + nodoPosicionusuarioDeporte + "\"," + 
						  "\"type\":\"" + Relaciones.USUARIOPOSICIONDEPORTE + "\"}";
			BDUtils.crearRelacion(Constantes.SERVER_ROOT_URI + "/node/" + idUsuario,propiedades);
			//Crear relacion entre deporte y nodo PosicionUsuarioDeporte
			propiedades = "{\"to\":\"" + nodoPosicionusuarioDeporte + "\"," + 
						  "\"type\":\"" + Relaciones.USUARIOPOSICIONDEPORTE + "\"}";
			BDUtils.crearRelacion(Constantes.SERVER_ROOT_URI + "/node/" + idDeporte,propiedades);
			//Crear relacion entre las posiciones y el nodo PosicionUsuarioDeporte
			PosicionDeporte[] posiciones = deportePracticado.getPosiciones();
			String idPosicion;			
			for(int i=0;i<posiciones.length;i++){
				//Obtener ID de posicion
				query = "MATCH (u:" + Entidades.POSICION + " {id:" + posiciones[i].getId() + "}) RETURN id(u)";
				data = BDUtils.ejecutarQuery(query);
				row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
				idPosicion = (String) row.getPropiedades().get("arreglo")[0];
				//Crear relacion entre posicion y nodo PosicionUsuarioDeporte
				propiedades = "{\"to\":\"" + nodoPosicionusuarioDeporte + "\"," + 
							  "\"type\":\"" + Relaciones.USUARIOPOSICIONDEPORTE + "\"}";
				BDUtils.crearRelacion(Constantes.SERVER_ROOT_URI + "/node/" + idPosicion,propiedades);
			}
			response = new ResponseGenerico();
			response.setCaracterAceptacion("B");
			response.setMensajeRespuesta("Deporte practicado agregado con exito.");
		}else{
			response = new ResponseGenerico();
			response.setCaracterAceptacion("M");
			response.setMensajeRespuesta("Este deporte se encuentra en los deportes practicados actualmente.");
		}
		return response;
	}
	
	public ResponseGenerico eliminarDeportePracticado(DeportePracticado deportePracticado, String usuario) {
		ResponseGenerico response;
		//Verificar si el deporte es practicado por el usuario
		String query = "MATCH (u:" + Entidades.USUARIO + " {usuario:'" + usuario + "'}) -[:" + Relaciones.PRACTICADEPORTE + "]->(d:" + Entidades.DEPORTE + " {id:" + deportePracticado.getDeporte().getId() + "}) return d.id";
		Object[] data = BDUtils.ejecutarQuery(query);
		if(data[0].getClass() != String.class){
			String nodeId;
			String relationId;
			
			
			JsonObject row;
			//Obtener nodo PosicionUsuarioDeporte
			query = "MATCH (u:" + Entidades.USUARIO + " {usuario:'" + usuario + "'}) -[:" + Relaciones.PRACTICADEPORTE + "]->(d:" + Entidades.DEPORTE + " {id:" + deportePracticado.getDeporte().getId() + "}),"
					+ "(u)-[:" + Relaciones.USUARIOPOSICIONDEPORTE +"]->(pud:" + Entidades.POSICIONUSUARIODEPORTE + "), (d)-[:" + Relaciones.USUARIOPOSICIONDEPORTE +"]->(pud) return id(pud)";
			data = BDUtils.ejecutarQuery(query);
			row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
			nodeId = (String) row.getPropiedades().get("arreglo")[0];
			//Obtener relaciones con nodo PosicionUsuarioDeporte
			String[] relations = BDUtils.obtenerRelacionesNodo(nodeId);
			
			//Eliminar relaciones entre nodos a nodo PosicionUsuarioDeporte
			for(int i=0;i<relations.length;i++)
				BDUtils.eliminarRelacion(relations[i]);
			
			//Eliminar nodo PosicionUsuarioDeporte
			BDUtils.eliminarNodo(nodeId);
			
			//Eliminar relación entre deporte y usuario
			//obtener ID Relacion
			query = "MATCH (u:" + Entidades.USUARIO + " {usuario:'" + usuario + "'}) -[r:" + Relaciones.PRACTICADEPORTE + "]->(d:" + Entidades.DEPORTE + " {id:" + deportePracticado.getDeporte().getId() + "})"
					+ " return id(r)";
			data = BDUtils.ejecutarQuery(query);
			row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
			relationId = (String) row.getPropiedades().get("arreglo")[0];
			
			BDUtils.eliminarRelacion(relationId);
			
			response = new ResponseGenerico();
			response.setCaracterAceptacion("B");
			response.setMensajeRespuesta("Deporte practicado eliminado con exito.");
		}else{
			response = new ResponseGenerico();
			response.setCaracterAceptacion("M");
			response.setMensajeRespuesta("Este deporte no se encuentra en los deportes practicados actualmente.");
		}
		return response;
	}

}
