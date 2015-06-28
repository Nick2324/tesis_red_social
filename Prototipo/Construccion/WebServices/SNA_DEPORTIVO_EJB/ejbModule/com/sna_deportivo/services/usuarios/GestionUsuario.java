package com.sna_deportivo.services.usuarios;

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
		//Crear el nodo
		String nodo = BDUtils.crearNodo();
		//Asignar propiedades al nodo
		if(!BDUtils.adicionarPropiedades(nodo, user.toString()))
			throw new BDException();
		//Asignar label a nodo
		if(!BDUtils.adicionarLabel(nodo, "\"" + Entidades.USUARIO + "\""))
			throw new BDException();
		//Crear relacion con nodo Rol
		//Obtener id de rol seleccionado
		String queryIdRol = "MATCH (n:" + Entidades.ROL + "{nombre:'" + user.getTipoUsuario() + "'}) RETURN id(n)";
		Object[] data = BDUtils.ejecutarQuery(queryIdRol);
		JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		String idRol = (String) row.getPropiedades().get("arreglo")[0];
		StringBuilder propiedades = new StringBuilder("{");
		propiedades.append("\"to\":\"" + Constantes.SERVER_ROOT_URI + "/node/" + idRol + "\",");
		propiedades.append("\"type\":\"" + Relaciones.ASUMEROL + "\"");
		propiedades.append("}");
		//Invocar Creación de la relación
		if(!BDUtils.crearRelacion(nodo, propiedades.toString()))
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
				+ "',contrasena:'"+ pass +"'}) return u";
		Object[] data = BDUtils.ejecutarQuery(query);
		JsonObject user;
		System.out.println(data[0].getClass());
		if(data[0].getClass() == JsonObject.class){
			JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
			user =  (JsonObject) row.getPropiedades().get("arreglo")[0];
			}
		else
			user = (JsonObject) data[0];
		if (user != null){
			
			response = new ResponseGenerico();
			response.setCaracterAceptacion("B");
			response.setMensajeRespuesta("Credenciales correctas");
			response.setDatosExtra(new Usuario(user).toString());
			System.out.println(response.getDatosExtra());
		}
		else
			throw new CredentialsException();
		return response;
	}


	public Rol[] obtenerRoles() throws BDException{
		Rol[] roles = null;
		String query = "MATCH (n:" + Entidades.ROL + ") RETURN n ORDER BY n.consecutivoRol";
		Object[] data = BDUtils.ejecutarQuery(query);
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
		Object[] data = BDUtils.ejecutarQuery(query);
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


	public Rol obtenerRolUsuario(String userName) {
		String query = "MATCH (:" + Entidades.USUARIO + " {usuario:'" + userName
				+ "'}) -[:R_AsumeRol]->(n:E_Rol) return n";
		Object[] data = BDUtils.ejecutarQuery(query);
		Rol rol;
		JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		JsonObject arregloRow = (JsonObject) row.getPropiedades().get("arreglo")[0];
		rol = new Rol(arregloRow.getPropiedades());
		return rol;
	}

}
