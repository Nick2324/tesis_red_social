package com.sna_deportivo.services;

import java.util.Map;

import javax.security.auth.login.CredentialException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;

import com.sna_deportivo.pojo.ResponseGenerico;
import com.sna_deportivo.pojo.Usuario;
import com.sna_deportivo.utils.BDException;
import com.sna_deportivo.utils.Constantes;
import com.sna_deportivo.utils.Entidades;
import com.sna_deportivo.utils.Utils;

public class GestionUsuario {

	private String PATH = "transaction/commit";

	public ResponseGenerico crearUsuario() {
		ResponseGenerico response = new ResponseGenerico();
		return response;
	}

	@SuppressWarnings("unchecked")
	public ResponseGenerico verificarUsuario(String usuario, String pass)
			throws BDException, CredentialException {
		String stringRespuesta;
		ResponseGenerico response = new ResponseGenerico();
		if (!Utils.servidorActivo())
			throw new BDException();
		String query = "MATCH (u:" + Entidades.USUARIO + " {usuario:'" + usuario
				+ "'}) return u.contrasena";
		String payload = "{\"statements\":[{\"statement\":\"" + query + "\"}]}";
		ResteasyClient cliente = Utils.obtenerCliente();
		WebTarget target = cliente.target(Constantes.SERVER_ROOT_URI)
				.path(PATH);
		Response result = target
				.request()
				.accept(MediaType.APPLICATION_JSON + "; charset=UTF-8")
				.post(Entity.entity(payload, MediaType.APPLICATION_JSON),
						Response.class);
		if (result.getStatus() == 200) {
			stringRespuesta = result.readEntity(String.class);
			if (stringRespuesta.startsWith("{"))
				stringRespuesta = stringRespuesta.substring(1,
						stringRespuesta.length() - 1);
			Map<String, Object> objRespuesta = Utils
					.JSONStringToObject(stringRespuesta);
			Map<String, Object>[] results = (Map<String, Object>[]) objRespuesta
					.get("results");
			Map<String, Object>[] data = (Map<String, Object>[]) results[0]
					.get("data");
			Map<String, Object>[] encontrados = (Map<String, Object>[]) data[0]
					.get("row");
			String contrasena;
			try {
				contrasena = (String) encontrados[0].get("valor");
			} catch (NullPointerException e) {
				throw new CredentialException();
			}
			if (contrasena.equals(pass)) {
				response.setCaracterAceptacion("B");
				response.setMensajeRespuesta("Credenciales correctas");
			} else
				throw new CredentialException();
		} else
			throw new BDException();
		return response;
	}
	
	public boolean existeUsuario(String user){
		return true;
	}

	public Usuario datosUsuario(String user) {
		return new Usuario();
	}

}
