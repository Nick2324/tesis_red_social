package com.sna_deportivo.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sna_deportivo.pojo.Credenciales;
import com.sna_deportivo.pojo.ResponseGenerico;
import com.sna_deportivo.pojo.Rol;
import com.sna_deportivo.pojo.Usuario;
import com.sna_deportivo.services.usuarios.GestionUsuario;
import com.sna_deportivo.utils.CredentialsException;
import com.sna_deportivo.utils.excepciones.BDException;

@Path("GestionUsuarioService/")
public class GestionUsuarioService {
	
	private GestionUsuario servicio;
	
	public GestionUsuarioService() {
		servicio = new GestionUsuario();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("crearUsuario")
	public ResponseGenerico crearUsuario(Usuario user){
		ResponseGenerico response = new ResponseGenerico();
		try {
			if(!servicio.existeUsuario(user)){
				user.setEstado(true);
				user.setFechaRegistro(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				response = servicio.crearUsuario(user);
				}
			else{
				response.setCaracterAceptacion("M");
				response.setMensajeRespuesta("Usuario ya existe");
			}
		} catch (BDException e) {
			response.setCaracterAceptacion("M");
			response.setMensajeRespuesta("Ha ocurrido un error con la base de datos.");
		}
		return response;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("verificarUsuario")
	public ResponseGenerico verificarUsuario(Credenciales credenciales){
		ResponseGenerico response = new ResponseGenerico();
		try {
			response = servicio.verificarUsuario(credenciales.getUser(), credenciales.getPassword());
		} catch (CredentialsException e) {
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerRoles")
	public Rol[] obtenerRoles(){
		Rol[] resultado;
		try {
			resultado = servicio.obtenerRoles();
		} catch (BDException e) {
			resultado = null;
		}
		return resultado;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("prueba")
	public Usuario getUnUsuario(Usuario usuario){
		return usuario;
	}
	
}
