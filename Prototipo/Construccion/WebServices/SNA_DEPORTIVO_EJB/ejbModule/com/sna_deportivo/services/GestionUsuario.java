package com.sna_deportivo.services;

import com.sna_deportivo.pojo.JsonObject;
import com.sna_deportivo.pojo.ResponseGenerico;
import com.sna_deportivo.pojo.Rol;
import com.sna_deportivo.pojo.Usuario;
import com.sna_deportivo.utils.BDException;
import com.sna_deportivo.utils.CredentialsException;
import com.sna_deportivo.utils.Utils;

public class GestionUsuario {

	public ResponseGenerico crearUsuario(Usuario user) {
		ResponseGenerico response = null;
		return response;
	}


	public ResponseGenerico verificarUsuario(String usuario, String pass)
			throws BDException, CredentialsException {
		ResponseGenerico response = null;
		String query = "MATCH (u:E_Usuario {usuario:'" + usuario
				+ "'}) return u.contrasena";
		Object[] data = Utils.EjecutarQuery(query);
		JsonObject row = (JsonObject) ((JsonObject) data[0]).getPropiedades().get("row")[0];
		String password = (String) row.getPropiedades().get("arreglo")[0];
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
		String query = "MATCH (n:E_Rol) RETURN n ORDER BY n.consecutivoRol";
		Object[] data = Utils.EjecutarQuery(query);
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

}
