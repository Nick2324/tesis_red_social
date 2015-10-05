package com.sna_deportivo.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sna_deportivo.pojo.deportes.Deporte;
import com.sna_deportivo.pojo.deportes.DeportePracticado;
import com.sna_deportivo.pojo.deportes.DeportePracticadoUsuario;
import com.sna_deportivo.pojo.deportes.PosicionDeporte;
import com.sna_deportivo.pojo.usuarios.Credenciales;
import com.sna_deportivo.pojo.usuarios.Permiso;
import com.sna_deportivo.pojo.usuarios.ResponseGenerico;
import com.sna_deportivo.pojo.usuarios.Rol;
import com.sna_deportivo.pojo.usuarios.Usuario;
import com.sna_deportivo.pojo.usuarios.excepciones.CredentialsException;
import com.sna_deportivo.services.usuarios.GestionUsuario;
import com.sna_deportivo.utils.bd.BDUtils;
import com.sna_deportivo.utils.bd.Entidades;
import com.sna_deportivo.utils.bd.Relaciones;
import com.sna_deportivo.utils.bd.excepciones.BDException;
import com.sna_deportivo.utils.json.JsonObject;

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
	public ResponseGenerico crearUsuario(Usuario user) {
		ResponseGenerico response = new ResponseGenerico();
		try {
			if (!servicio.existeUsuario(user)) {
				user.setEstado(true);
				user.setFechaRegistro(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				response = servicio.crearUsuario(user);
			} else {
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
	public ResponseGenerico verificarUsuario(Credenciales credenciales) {
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
	public Usuario datosUsuario(@PathParam("user") String user) {
		return servicio.datosUsuario(user);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerRoles")
	public Rol[] obtenerRoles() {
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
	@Path("obtenerPermisos")
	public Rol[] obtenerPermisos() {
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
	@Path("obtenerRolUsuario")
	public Rol obtenerRolUsuario(@QueryParam("userName") String userName) {
		Rol resultado;
		try {
			resultado = servicio.obtenerRolUsuario(userName);
		} catch (BDException e) {
			resultado = null;
		}
		return resultado;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("obtenerPermisosRol")
	public Permiso[] obtenerPermisosRol(Rol rol) {
		Permiso[] resultado;
		try {
			resultado = servicio.obtenerPermisosRol(rol);
		} catch (BDException e) {
			resultado = null;
		}
		return resultado;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("obtenerPermisosAsociados")
	public Permiso[] obtenerPermisosAsociados(Permiso permiso) {
		Permiso[] resultado;
		try {
			resultado = servicio.obtenerPermisosAsociados(permiso);
		} catch (BDException e) {
			resultado = null;
		}
		return resultado;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("actualizarDatosUsuario")
	public ResponseGenerico actualizarDatosUsuario(Usuario user) {
		ResponseGenerico response;
		try {
			response = servicio.acualizarDatosUsuario(user);
		} catch (BDException e) {
			response = new ResponseGenerico();
			response.setCaracterAceptacion("M");
			response.setMensajeRespuesta("Ha ocurrido un error mientras se actualizaban los datos del usuario");
		}
		return response;
	}

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerDeportesPracticados")
	public Deporte[] obtenerDeportesPracticados(@QueryParam("user") String userName) {
		Deporte[] resultado;
		try {
			resultado = servicio.obtenerDeportesPracticados(userName);
		} catch (BDException e) {
			resultado = null;
		}
		return resultado;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerDeportes")
	public Deporte[] obtenerDeportes() {
		Deporte[] deportes;
		try {
			deportes = servicio.obtenerDeportes();
		} catch (BDException e) {
			deportes = null;
		}
		return deportes;
	}

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerPosicionesDeporte")
	public PosicionDeporte[] obtenerPosicionesDeporte(@QueryParam("sportId") int idDeporte) {
		PosicionDeporte[] posiciones;
		try {
			posiciones = servicio.obtenerPosicionesDeporte(idDeporte);
		} catch (BDException e) {
			posiciones = null;
		}
		return posiciones;
	}

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerPosicionesDeportePracticado")
	public PosicionDeporte[] obtenerPosicionesDeportePracticado(@QueryParam("sportId") int idDeporte,
			@QueryParam("user") String userName) {
		PosicionDeporte[] posiciones;
		try {
			posiciones = servicio.obtenerPosicionesDeportePracticado(idDeporte, userName);

		} catch (BDException e) {
			posiciones = null;
		}
		return posiciones;
	}
	
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerDeportesPracticadosFull")
	public DeportePracticado[] obtenerDeportesPracticadosFull(@QueryParam("user") String userName) {
		DeportePracticado[] deportesPracticados = null;
		Deporte[] deportes;
		try {
			deportes = servicio.obtenerDeportesPracticados(userName);
			if(deportes != null){
				deportesPracticados = new DeportePracticado[deportes.length];
				for(int i=0;i<deportes.length;i++){
					deportesPracticados[i] = new DeportePracticado();
					deportesPracticados[i].setDeporte(deportes[i]);
					deportesPracticados[i].setPosiciones(servicio.obtenerPosicionesDeportePracticado(deportes[i].getId(), userName));
					deportesPracticados[i].setNivel("Principiante");
				}
			}
		} catch (BDException e) {
			deportesPracticados = null;
		}
		return deportesPracticados;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("adicionarDeportePracticado")
	public ResponseGenerico adicionarDeportePracticado(DeportePracticadoUsuario deportePracticadoUsuario){
		DeportePracticado deportePracticado;
		deportePracticado = deportePracticadoUsuario.getDeportePracticado();
		String usuario = deportePracticadoUsuario.getUser();
		
		ResponseGenerico response;
		try {
			response = servicio.adicionarDeportePracticado(deportePracticado, usuario);
		} catch (BDException e) {
			response = new ResponseGenerico();
			response.setCaracterAceptacion("M");
			response.setCaracterAceptacion("Ha ocurrido un error con la base de datos. Intente nuevamente mas tarde.");
		}
		return response;
	}
	
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminarDeportePracticado")
	public ResponseGenerico eliminarDeportePracticado(DeportePracticadoUsuario deportePracticadoUsuario){
		DeportePracticado deportePracticado;
		deportePracticado = deportePracticadoUsuario.getDeportePracticado();
		String usuario = deportePracticadoUsuario.getUser();
		
		ResponseGenerico response;
		try {
			response = servicio.eliminarDeportePracticado(deportePracticado, usuario);
		} catch (BDException e) {
			response = new ResponseGenerico();
			response.setCaracterAceptacion("M");
			response.setCaracterAceptacion("Ha ocurrido un error con la base de datos. Intente nuevamente mas tarde.");
		}
		return response;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("actualizarDeportePracticado")
	public ResponseGenerico actualizarDeportePracticado(DeportePracticadoUsuario deportePracticadoUsuario){
		DeportePracticado deportePracticado;
		deportePracticado = deportePracticadoUsuario.getDeportePracticado();
		String usuario = deportePracticadoUsuario.getUser();
		
		ResponseGenerico response;
		try {
			response = servicio.eliminarDeportePracticado(deportePracticado, usuario);
			if(response.getCaracterAceptacion().equals("B"))
				response = servicio.adicionarDeportePracticado(deportePracticado, usuario);
		} catch (BDException e) {
			response = new ResponseGenerico();
			response.setCaracterAceptacion("M");
			response.setCaracterAceptacion("Ha ocurrido un error con la base de datos. Intente nuevamente mas tarde.");
		}
		return response;
	}
}
