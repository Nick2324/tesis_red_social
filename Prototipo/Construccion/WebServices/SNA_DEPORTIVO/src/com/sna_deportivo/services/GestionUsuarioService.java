package com.sna_deportivo.services;

import javax.security.auth.login.CredentialException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sna_deportivo.pojo.ResponseGenerico;
import com.sna_deportivo.pojo.Usuario;
import com.sna_deportivo.utils.BDException;

@Path("GestionUsuarioService/")
public class GestionUsuarioService {
	
	private GestionUsuario servicio;
	
	public GestionUsuarioService() {
		servicio = new GestionUsuario();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("crearUsuario")
	public String crearUsuario(@FormParam("user") String userName,@FormParam("password") String password){
		//return servicio.crearUsuario();
		return "Usuario: " + userName + " Contraseña: " + password;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("verificarUsuario")
	public ResponseGenerico verificarUsuario(@QueryParam("user") String usuario, @QueryParam("pass") String pass){
		ResponseGenerico response = new ResponseGenerico();
		try {
			response = servicio.verificarUsuario(usuario, pass);
		} catch (CredentialException e) {
			response.setCaracterAceptacion("M");
			response.setMensajeRespuesta("Credenciales incorrectas");
		} catch (BDException e) {
			response.setCaracterAceptacion("M");
			response.setMensajeRespuesta("Credenciales incorrectas");
		}
		return response;
	}
	
	@GET
	@Path("{user}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario datosUsuario(@PathParam("user") String user){
		return servicio.datosUsuario(user);
	}

}
