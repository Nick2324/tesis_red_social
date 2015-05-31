package com.sna_deportivo.services;

import com.sna_deportivo.pojo.JsonObject;
import com.sna_deportivo.pojo.ResponseGenerico;
import com.sna_deportivo.pojo.Rol;
import com.sna_deportivo.pojo.Usuario;
import com.sna_deportivo.utils.BDException;
import com.sna_deportivo.utils.BDUtils;
import com.sna_deportivo.utils.Constantes;
import com.sna_deportivo.utils.CredentialsException;
import com.sna_deportivo.utils.Entidades;
import com.sna_deportivo.utils.Relaciones;

public class GestionUsuario {

	public ResponseGenerico crearUsuario(Usuario user) throws BDException {
		ResponseGenerico response = new ResponseGenerico();
		//Crear el nodo
		String nodo = BDUtils.CrearNodo();
		//Asignar propiedades al nodo
		if(!BDUtils.AdicionarPropiedades(nodo, user.toString()))
			throw new BDException();
		//Asignar label a nodo
		if(!BDUtils.AdicionarLabel(nodo, "\"" + Entidades.USUARIO + "\""))
			throw new BDException();
		//Crear relacion con nodo Rol
		//Obtener id de rol seleccionado
		String queryIdRol = "MATCH (n:" + Entidades.ROL + "{nombre:'" + user.getTipoUsuario() + "'}) RETURN id(n)";
		Object[] data = BDUtils.EjecutarQuery(queryIdRol);
		JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		String idRol = (String) row.getPropiedades().get("arreglo")[0];
		StringBuilder propiedades = new StringBuilder("{");
		propiedades.append("\"to\":\"" + Constantes.SERVER_ROOT_URI + "/node/" + idRol + "\",");
		propiedades.append("\"type\":\"" + Relaciones.ASUMEROL + "\"");
		propiedades.append("}");
		//Invocar Creación de la relación
		if(!BDUtils.CrearRelacion(nodo, propiedades.toString()))
			throw new BDException();
		response.setCaracterAceptacion("B");
		response.setMensajeRespuesta("Usuario creado exitosamente");
		response.setDatosExtra(nodo);
		return response;
	}


	public ResponseGenerico verificarUsuario(String usuario, String pass)
			throws BDException, CredentialsException {
		ResponseGenerico response = null;
		String query = "MATCH (u:" + Entidades.USUARIO + " {usuario:'" + usuario
				+ "'}) return u.contrasena";
		Object[] data = BDUtils.EjecutarQuery(query);
		String password = null;;
		if(data[0].getClass() == JsonObject.class){
			JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
			password = (String) row.getPropiedades().get("arreglo")[0];
			}
		else
			password = (String) data[0];
		if (pass.equals(password)){
			response = new ResponseGenerico();
			response.setCaracterAceptacion("B");
			response.setMensajeRespuesta("Credenciales correctas");
		}
		else
			throw new CredentialsException();
		return response;
	}


	public Rol[] obtenerRoles() throws BDException{
		Rol[] roles = null;
		String query = "MATCH (n:" + Entidades.ROL + ") RETURN n ORDER BY n.consecutivoRol";
		Object[] data = BDUtils.EjecutarQuery(query);
		roles = new Rol[data.length];
		for(int i=0;i<data.length;i++){
			JsonObject row = (JsonObject) ((JsonObject) data[i]).getPropiedades().get("row")[0];
			JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
			roles[i] = new Rol(arregloRow.getPropiedades());
		}
		return roles;
	}
	
	public Usuario datosUsuario(String user) {
		return new Usuario();
	}


	public boolean existeUsuario(Usuario user) throws BDException {
		String query = "MATCH (u:" + Entidades.USUARIO + " {usuario:'" + user.getUsuario()
				+ "'}) return u.contrasena";
		Object[] data = BDUtils.EjecutarQuery(query);
		String usuario = null;
		if(data[0].getClass() == JsonObject.class){
			JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
			usuario = (String) row.getPropiedades().get("arreglo")[0];
			}
		else
			usuario = (String) data[0];
		if(usuario.equals(""))
			return false;
		return true;
	}

}
